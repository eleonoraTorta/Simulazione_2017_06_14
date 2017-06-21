package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.TreeMap;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class TestModel {
	
	public static void main(String[] args) {
		
		Random r= new Random();
		System.out.println(r.nextInt(6));
		
		ArtsmiaDAO dao = new ArtsmiaDAO();
		Map <Integer, ArtObject> map = new TreeMap <Integer, ArtObject>();
		
		Model model = new Model();
		Simulazione simulazione = new Simulazione(model);

		model.creaGrafo(1995);
		
		model.getOpere(); // prima mi assicuro di aver riempito la mappa di opere
		for( Exhibition e : model.grafo.vertexSet()){
		//	if( e.getListaOggetti().size() == 0)
				dao.addOpereAMostra(e, map);
		}
		
		
		
		for( Exhibition e : model.grafo.vertexSet()){
			System.out.println( e.getId() + " " + e.getListaOggetti().size() + "\n");
		}
		
		System.out.println(model.getMostraPiuPiena());
	//	System.out.println(model.grafo);
//		for( Exhibition e : model.grafo.vertexSet()){
//			System.out.println(e);
//		}
//		for( DefaultEdge arco : model.grafo.edgeSet()){
//			System.out.println(arco);
//		}
//		System.out.print("FINE\n");
//		Exhibition iniziale = simulazione.scegliMostraIniziale(2012);
//		Exhibition iniziale = model.getMappaMostre().get(1261);
//		System.out.println(iniziale);
//		System.out.println(model.grafo.outDegreeOf(iniziale));
//		System.out.println(model.grafo.outgoingEdgesOf(iniziale));
//		System.out.println(Graphs.neighborListOf(model.grafo, iniziale));
//		System.out.println(Graphs.successorListOf(model.grafo, iniziale));
		
//		List <Studente> studenti = new ArrayList <Studente>();
//		PriorityQueue <Evento> queue = new PriorityQueue();
//		Exhibition iniziale = simulazione.scegliMostraIniziale(2017);
//		System.out.println(iniziale);
//
//		for( int i = 1 ; i <= 3 ; i++){
//			Studente s = new Studente ( i);
//			if( s.getId() == 2){
//				System.out.println("Studente 2\n");
//			}
//			studenti.add(s);
//			Evento e = new Evento ( s, iniziale);
//			System.out.println(e);
//			queue.add(e);
//		
//		}
		
		
//		System.out.println(model.getGrafo(2012));
//		System.out.println(model.getGrafo(2012).outgoingEdgesOf(iniziale));
//		Studente s = new Studente ( 1);
//		Evento e = new Evento ( s, iniziale);
//		Exhibition next =simulazione.scegliNextMostra(e.getMostra());
//		System.out.println(next);
		
	}
}
