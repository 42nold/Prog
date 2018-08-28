package utenti;

import java.io.Serializable;

@SuppressWarnings("serial")

public abstract class Utente implements Serializable {

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
	public String getNome() {
		return nome;
	}
	public String getCognome() {
		return cognome;
	}
	public int getEta() {
		return eta;
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
}
