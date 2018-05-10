package dafault;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import it.unibs.ing.mylib.InputDati;

@SuppressWarnings("serial")

public abstract class Utente<T extends Utente> implements Serializable {

	/**
	 * @invariant invarianteU()
	 */
	private String nome, cognome, username, password;
	private int eta;
	
	/**
	 * istanzia un generico utente
	 * @param _nome
	 * @param _cognome
	 * @param _eta
	 * @param _username
	 * @param _password
	 */
	public Utente(String _nome, String _cognome, int _eta, String _username, String _password) {
		nome=_nome;
		cognome=_cognome;
		eta=_eta;
		username=_username;
		password=_password;		
		assert invarianteU();
	}
	/**
	 * verifica che le invarianti di classe siano rispettate
	 * @pre true
	 * @post @nochange
	 * @return true se sono verificate tutte le invarianti
	 */
	protected boolean invarianteU() {
		boolean invariante=false;
		Utente utentePre = this;
		
		if(nome!=null && cognome!=null && eta>=18 && username!=null && username!= ""&& password!=""&& password!=null && getUsername()==username && getPassword()==password) invariante=true;
		
		assert utentePre == this ;
		return invariante;
	}
/**
 * getter del valore username
 * @pre true
 * @post @nochange
 * @return l'username dell'utente
 */
	public String getUsername() {
		return username;
	}
	/**
	 * getter del valore password
	 * @pre true
	 * @post @nochange
	 * @return la password dell'utente
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * ritorna una stringa con la descrizione completa dell'utente
	 * @pre true
	 * @post @nochange
	 * @return la descrizione dell'utente
	 */
	public String toString() {
		assert invarianteU();
		Utente utentePre = this;
		
		StringBuffer descrizione = new StringBuffer();
		descrizione.append("Nome: "+nome+"\n");
		descrizione.append("Cogome: "+cognome+"\n");
		descrizione.append("Eta': "+eta+"\n");
		descrizione.append("Username: "+username+"\n");
		String risultato =  descrizione.toString();	
		assert invarianteU() && utentePre== this ;
		
		return	risultato;
	}
	
public static Fruitore registrati(String _username) {
		
		int eta=InputDati.leggiIntero("inserisci la tua et�\n");
		if(eta<18) {
			System.out.println("Servizio riservato ai soli cittadini maggiorenni\n");
			return null ;
		}	
		
		String username=_username;
		String nome=InputDati.leggiStringaNonVuota("Inserisci nome\n");
		String cognome=InputDati.leggiStringaNonVuota("Inserisci cognome\n");
		String password=InputDati.leggiStringaNonVuota("Inserisci password\n");		
		Calendar data_iscrizione=Calendar.getInstance();
		
		Calendar data_scadenza= Calendar.getInstance();
		data_scadenza.add(Calendar.YEAR, 5);
	
		
		return new Fruitore(nome,cognome,eta,username,password,data_iscrizione,data_scadenza);
		
	}
	public abstract boolean iscrizioneScaduta();
	
	public abstract ArrayList<Integer> restituisciRisorseInPrestito();
	
	public abstract ArrayList<Integer>  eliminaPrestitiScaduti();
	
	public abstract Calendar getDataScadenza();
	
	public abstract void aggiornaDataScadenza();
	
	public abstract void setIdPrestito() ;
	
	public abstract String  visualizzaPrestitiFruitore();
	
	public abstract String getDescrizionePrestito(Integer idPrestito);
	
	public abstract void richiediPrestito(int idRisorsa, String descrizioneRisorsa, Calendar calendar, Calendar fine, int durataProroga, int termineProroga);
	
	public abstract ArrayList<Integer> inScadenza();
	
	public abstract void rinnova(Integer integer);
	
	public abstract ArrayList<Integer> getPrestiti() ;
	
	public abstract boolean giaPresente(int risorsa);
	
	public boolean isFruitore() {
		if(this.getClass()==new Fruitore("","",18,"","",Calendar.getInstance(),Calendar.getInstance()).getClass()) return true;
		return false;
	}
	public abstract String visualizzaTutto() ;
	
	
}
