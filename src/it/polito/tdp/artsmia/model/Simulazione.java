package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Simulazione {
	
	private Model model;
	
	// Variabili simulazione
	private int numeroStudenti;
			
	// Variabili globali ( del mondo)
	Map <Integer, ArtObject> map;
	ArtsmiaDAO dao ;
		
	// Variabili di interesse
	private List <Studente> studenti;
		
	// Lista degli eventi
	private PriorityQueue <Evento> queue;
	
	
	public Simulazione(Model model){
		this.model = model;
		this.queue = new PriorityQueue<Evento>();
		this.studenti = new ArrayList <Studente>();
		
		this.map = new TreeMap <Integer, ArtObject>();
		this.dao  = new ArtsmiaDAO();
			
	}
	
	// Aggiunge i primi eventi alla coda e avvia la simulazione
	
	public void simula(int n, int anno) {
		this.numeroStudenti = n;
		
		model.getOpere(); // prima mi assicuro di aver riempito la mappa di opere
		for( Exhibition e : model.grafo.vertexSet()){
			if( e.getListaOggetti().size() == 0)
				dao.addOpereAMostra(e, map);
		}

		// Stabilisco la mostra da cui far partire la simulazione
		Exhibition iniziale = this.scegliMostraIniziale(anno);
	
		// Creo N studenti e aggiungo gli eventi iniziali alla coda
		for( int i = 1 ; i <= numeroStudenti ; i++){
			Studente s = new Studente ( i);
			studenti.add(s);
			Evento e = new Evento ( 0, s, iniziale);
			queue.add(e);
		}
		
		if( model.grafo.outDegreeOf(iniziale)<1){
			System.out.println("La mostra iniziale non ha archi uscenti. stop simulazione : iniziale = " + iniziale.getId() +"\n");
			return;
		}
		
		// avvio la simulazione
		this.run();
		
	}
	  
	 public void run(){
		 
		 while(! queue.isEmpty()){
			 Evento evento = queue.poll();
			 // Aggiungo allo studente le opere della mostra
		//	 evento.getStudente().aggiungiOpere(evento.getMostra().getListaOggetti());
			 // scelgo la prossima mostra randomicamente
			 Exhibition next = this.scegliNextMostra(evento.getMostra());
			 // controllo se da questa mostra possono scaturire nuovi eventi
			 if( next != null){
				 // creo nuovo evento
				 Evento nuovo = new Evento ( evento.getTime()+1, evento.getStudente(), next);
				 queue.add(nuovo);
			 }
		 }
		  
	 }

	  
	// Metodo per scegliere la mostra di partenza uguale per tutti
	public Exhibition scegliMostraIniziale(int anno){
			
			// Prendo tutte le mostre iniziate nell'anno selezionato dallo studente
			List < Exhibition> candidate = new ArrayList <Exhibition>();
			for( Exhibition e : model.grafo.vertexSet()){
				if( e.getInizio() == anno){
					candidate.add( e);
				}
			}
				
			// Genero un numero random corrispondente alla posizione nella lista  della mostra da cui inizio
			Random random = new Random();
			int randomInt = random.nextInt(candidate.size());  //genera un intero random compreso tra 0 (INCLUSO) 
															   //e il numero passato come parametro (ESCLUSO)
			Exhibition partenza = candidate.get(randomInt);
			
			return partenza;
	}
		
		// Metodo per scegliere ogni volta una nuova mostra
		public Exhibition scegliNextMostra( Exhibition e){
			
			if( model.grafo.outDegreeOf(e) <= 0){
				return null;
			}
			
			List <Exhibition> raggiungibili = new ArrayList <Exhibition>();
			
			for ( DefaultEdge arco : model.grafo.outgoingEdgesOf(e)){
				raggiungibili.add( model.grafo.getEdgeTarget(arco));
			}
			
			// Oppure
//			raggiungibili.addAll(Graphs.successorListOf(model.grafo, e));
			
			Random random = new Random();
			int randomInt = random.nextInt(model.grafo.outDegreeOf(e));;

			Exhibition next = raggiungibili.get(randomInt);
			return next;
		}
		
	// Ottengo la classifica
	public List <String> getClassifica(){

		Collections.sort(studenti, Comparator.reverseOrder());
		List <String> classifica = new LinkedList <String>();
		for( Studente s : this.getStudenti()){
			String risultato = "Studente " + s.getId() + " : " + s.getNumeroOpere() + " opere visitate\n";
			classifica.add(risultato);
		}
		return classifica;
	}


	public List<Studente> getStudenti() {
		return studenti;
	}
	
}
