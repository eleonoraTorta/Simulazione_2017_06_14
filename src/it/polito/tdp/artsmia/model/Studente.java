package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

public class Studente {
	
	private int id;
	private List <ArtObject> opere = new ArrayList <ArtObject>();
	
	public Studente(int id) {
		super();
		this.id = id;
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
	
	
	
	

}
