package dafault;

import java.util.ArrayList;
import java.util.Calendar;

@SuppressWarnings("serial")

public class Operatore extends Utente<Operatore> {
	/**
	 * istanzia la classe operatore col costruttore di Utente
	 * @param _nome
	 * @param _cognome
	 * @param _eta
	 * @param _username
	 * @param _password
	 */
	public Operatore(String _nome, String _cognome, int _eta, String _username, String _password) {
		super(_nome, _cognome, _eta, _username, _password);
	}
	/**
	 * ritorna la stringa descrittiva dell'operatore
	 * @pre true
	 * @post @nochange
	 * @return la descrizione
	 */
	public String toString() {
		assert invarianteU();
		Operatore operatorePre = this;
		
		StringBuffer descrizione = new StringBuffer();
		descrizione.append(super.toString());
		String risultato = descrizione.toString();
		
		assert invarianteU() && operatorePre == this;
		return risultato;
	}
	@Override
	public boolean iscrizioneScaduta() {
		return false;
	}
	@Override
	public ArrayList<Integer> restituisciRisorseInPrestito() {
		return null;
	}
	@Override
	public ArrayList<Integer> eliminaPrestitiScaduti() {
		return null;
	}
	@Override
	public Calendar getDataScadenza() {
		return null;
	}
	@Override
	public void aggiornaDataScadenza() {
		assert false;
	}
	@Override
	public void setIdPrestito() {
		assert false;
	}
	@Override
	public String visualizzaPrestitiFruitore() {
		assert false;
		return null;
	}
	@Override
	public String getDescrizionePrestito(Integer idPrestito) {
		assert false;
		return null;
	}
	@Override
	public void richiediPrestito(int idRisorsa, String descrizioneRisorsa, Calendar calendar, Calendar fine,
			int durataProroga, int termineProroga) {
		assert false;
		
	}
	@Override
	public ArrayList<Integer> inScadenza() {
		assert false;
		return null;
	}
	@Override
	public void rinnova(Integer integer) {
		assert false;
	}
	@Override
	public ArrayList<Integer> getPrestiti() {
		assert false;
		return null;
	}
	@Override
	public boolean giaPresente(int risorsa) {
		assert false;
		return false;
	}
	@Override
	public String visualizzaTutto() {
		assert false;
		return null;
	}		
	}
	
