package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.List;

public class Exhibition {
	
	private int id;
	private String nome;
	private int inizio;
	private int fine;
	private List <ArtObject> opere =new ArrayList <ArtObject>() ;
	
	public Exhibition(int id, String nome, int inizio, int fine) {
		super();
		this.id = id;
		this.nome = nome;
		this.inizio = inizio;
		this.fine = fine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getInizio() {
		return inizio;
	}

	public void setInizio(int inizio) {
		this.inizio = inizio;
	}

	public int getFine() {
		return fine;
	}

	public void setFine(int fine) {
		this.fine = fine;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Exhibition other = (Exhibition) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Exhibition [id=" + id + ", nome=" + nome + ", inizio=" + inizio + ", fine=" + fine + "]";
	}

	public void addOpera(ArtObject ogg) {
		if( !opere.contains(ogg)){
			opere.add(ogg);
		}
	}
	
	public List <ArtObject> getListaOggetti(){
		return opere;
	}
	
	
	

}
