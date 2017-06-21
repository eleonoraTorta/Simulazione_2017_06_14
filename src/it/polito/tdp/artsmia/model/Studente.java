package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

public class Studente implements Comparable <Studente> {
	
	private int id;
	private List <ArtObject> opere = new ArrayList <ArtObject>();
	
	public Studente(int id) {
		super();
		this.id = id;
		this.opere = new ArrayList <ArtObject>();
	}

	public int getId() {
		return id;
	}
	
	public void aggiungiOpere( List <ArtObject> nuove){
		for( ArtObject o : nuove){
			if(! opere.contains(o)){
				opere.add(o);
			}
		}
		
	}

	public List<ArtObject> getOpere() {
		return opere;
	}
	
	public int getNumeroOpere(){
		return opere.size();
	}

	@Override
	public int compareTo(Studente altro) {
		
		return ( this.getNumeroOpere() - altro.getNumeroOpere());
	}

	@Override
	public String toString() {
		return  id + "" ;
	}
	
	
	
	

}
