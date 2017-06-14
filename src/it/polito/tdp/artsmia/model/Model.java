package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.w3c.dom.events.Event;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;


public class Model {
	private ArtsmiaDAO dao ;
	private List <Exhibition> mostre;
	private List <ArtObject> opere;
	private Map <Integer, Exhibition> mappa = new TreeMap <Integer, Exhibition>();
	private Map <Integer, ArtObject> map = new TreeMap <Integer, ArtObject>();
	private DirectedGraph <Exhibition, DefaultEdge> grafo;
	
	// VARIABILI PER LA SIMULAZIONE
	// Variabili simulazione
	
	
	// Variabili globali ( del mondo)
	
	// Variabili di interesse
	 // numero di opere viste da ogni studente
	
	// Lista degli event
	PriorityQueue <Evento> queue;
	
	public Model(){
		this.dao = new ArtsmiaDAO();
	}
	
	public List <Exhibition> getMostre(int anno){
		if( mostre == null){
			this.mostre = dao.getAllExhibitionsFromYear(anno, mappa) ;
		}
		return mostre;
		
	}
	
	public List <ArtObject> getOpere(){
		if( opere == null){
			this.opere = dao.listObject(map) ;
		}
		return opere;
		
	}
	
	public Map<Integer, Exhibition> getMappaMostre() {
		return mappa;
	}

	public Map<Integer, ArtObject> getMapOpere() {
		return map;
	}

	public List <Integer> getAnni(){
		return dao.getYears();
	}

	public void creaGrafo(int anno) {
		
		this.grafo = new SimpleDirectedGraph <>(DefaultEdge.class);
		
		// Aggiungo i vertici
		Graphs.addAllVertices(grafo, this.getMostre(anno));
		
		// Aggiungo gli archi
		for( Exhibition e1 : this.grafo.vertexSet()){
			for( Exhibition e2 : this.grafo.vertexSet()){
				if( !e1.equals(e2)){
					if( e2.getInizio() > e1.getInizio() && e2.getInizio() < e1.getFine()){
						if(! grafo.containsEdge(grafo.getEdge(e1, e2))){
							Graphs.addEdgeWithVertices(grafo, e1, e2);
						}
					}
				}
			}
		}
		
	//	System.out.print(grafo);
	
	}
	
	public boolean fortementeConnesso(){
		ConnectivityInspector <Exhibition, DefaultEdge> inspector = new ConnectivityInspector <Exhibition, DefaultEdge>(this.grafo);
		if ( inspector.isGraphConnected() == true){
			return true;
		}
		return false;	
	}
	
	public Exhibition getMostraPiuPiena(){
		int max = Integer.MIN_VALUE;
		Exhibition mostra = null;
		
		// Aggiungo le opere alle mostre
		this.getOpere(); // prima mi assicuro di aver riempito la mappa di opere
		for( Exhibition e : this.grafo.vertexSet()){
			dao.addOpereAMostra(e, map);
		}
		
		for( Exhibition e : this.grafo.vertexSet()){
			if( e.getListaOggetti().size() > max){
				max = e.getListaOggetti().size();
				mostra = e;
			}
		}
		return mostra;	
	}

	public DirectedGraph<Exhibition, DefaultEdge> getGrafo() {
		return grafo;
	}

	// Aggiunge i primi eventi alla coda 
	public void simula(int n, int anno) {
		
		List < Studente> studenti = new ArrayList <Studente>();
		for( int i = 1 ; i <= n ; i++){
			Studente s = new Studente ( i);
			studenti.add(s);
		}
		
	
	}
	

	// Metodo per scegliere la mostra di partenza uguale per tutti
	public Exhibition scegliMostraIniziale(int anno){
	
		// Prendo tutte le mostre iniziate nell'anno selezionato dallo studente
		List < Exhibition> candidate = new ArrayList <Exhibition>();
		for( Exhibition e : this.getMostre(anno)){
			if( e.getInizio() == anno){
				candidate.add( e);
			}
		}
		
		// Genero un numero random corrispondente alla posizione nella lista  della mostra da cui inizio
		int dim = candidate.size();
		int random = (int) (Math.random() * dim ) ;

		Exhibition partenza = candidate.get(random);
		return partenza;
	}
	
	
	// Metodo per scegliere ogni volta una nuova mostra
	public Exhibition scegliNextMostra( Exhibition e){
		
		List <Exhibition> raggiungibili = new ArrayList <Exhibition>();
		
		for ( DefaultEdge arco : grafo.outgoingEdgesOf(e)){
			raggiungibili.add( grafo.getEdgeTarget(arco));
		}
		
		int dim = raggiungibili.size();
		int random = (int) (Math.random() * dim ) ;

		Exhibition next = raggiungibili.get(random);
		return next;
	}
}
