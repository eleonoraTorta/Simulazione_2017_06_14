package it.polito.tdp.artsmia.model;

public class Evento {
	
	private Studente studente;
	private Exhibition mostra;
	
	public Evento(Studente studente, Exhibition mostra) {
		super();
		this.studente = studente;
		this.mostra = mostra;
	}
	public Studente getStudente() {
		return studente;
	}
	public void setStudente(Studente studente) {
		this.studente = studente;
	}
	public Exhibition getMostra() {
		return mostra;
	}
	public void setMostra(Exhibition mostra) {
		this.mostra = mostra;
	}
	
	

}
