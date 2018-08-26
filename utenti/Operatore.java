package utenti;


@SuppressWarnings("serial")

public class Operatore extends Utente {
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
}
