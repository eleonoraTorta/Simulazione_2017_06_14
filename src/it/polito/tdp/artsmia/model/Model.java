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
import org.jgrapht.alg.KosarajuStrongConnectivityInspector;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.w3c.dom.events.Event;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;


public class Model {
	private ArtsmiaDAO dao ;
	private List <Exhibition> mostre;
	private List <ArtObject> opere;
	private Map <Integer, Exhibition> mappa;
	private Map <Integer, ArtObject> map;
     DirectedGraph <Exhibition, DefaultEdge> grafo;
	
	
	public Model(){
		this.dao = new ArtsmiaDAO();
		this.mappa = new TreeMap <Integer, Exhibition>();
		this.map = new TreeMap <Integer, ArtObject>();
		
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
		
		this.grafo = new SimpleDirectedGraph <Exhibition, DefaultEdge>(DefaultEdge.class);
		
		// Aggiungo i vertici
		this.getMostre(anno);
		Graphs.addAllVertices(grafo, mostre);
		
		// Aggiungo gli archi
		for( Exhibition e1 : grafo.vertexSet()){
			for( Exhibition e2 : grafo.vertexSet()){
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
	
	// Stabilire se il grafo � FORTEMENTE CONNESSO
	
	//OPZIONE 1
	public boolean fortementeConnesso(){
		ConnectivityInspector <Exhibition, DefaultEdge> inspector = new ConnectivityInspector <Exhibition, DefaultEdge>(this.grafo);
		if ( inspector.isGraphConnected() == true){
			return true;
		}
		return false;	
	}
	
	// OPZIONE 2
	public boolean fortementeConnesso2(){	
		KosarajuStrongConnectivityInspector<Exhibition, DefaultEdge> ksci = new KosarajuStrongConnectivityInspector<Exhibition, DefaultEdge>(grafo);
		return ksci.isStronglyConnected();
	}
	
	
	// Valuto quale mostra contenga piu opere
	
	// METODO 1 (apparentemente sbagliato)
	
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
	
	
	// METODO 2 ( non sto aggiungendo le opere )
	public Exhibition getMostraPiuPiena2(){
		int max = Integer.MIN_VALUE;
		Exhibition mostra = null;
		
		for( Exhibition e : this.grafo.vertexSet()){
			if(dao.getNumeroOpere(e.getId()) > max){
				max = dao.getNumeroOpere(e.getId());
				mostra = e;
			}	
		}
		return mostra;
	}
	
	public int getNumeroOpere( Exhibition e){
		return dao.getNumeroOpere(e.getId());
	}

	public DirectedGraph<Exhibition, DefaultEdge> getGrafo() {
		return grafo;
	}

	
}
