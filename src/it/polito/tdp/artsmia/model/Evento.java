package it.polito.tdp.artsmia.model;

public class Evento implements Comparable <Evento> {
	
	private int time;
	private Studente studente;
	private Exhibition mostra;
	
	public Evento( int time, Studente studente, Exhibition mostra) {
		super();
		this.time= time;
		this.studente = studente;
		this.mostra = mostra;
		
		// aggiungo automaticamente allo studente le opere che visita
		this.studente.aggiungiOpere(mostra.getListaOggetti());
	}
	
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
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


	@Override
	public String toString() {
		return "Evento [ studente=" + studente + ", mostra=" + mostra + "]\n";
	}


	@Override
	public int compareTo(Evento altro) {
		return this.time - altro.time;
	}
	
	

}
