package utenti;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author Arnold Caka
 *
 */
@SuppressWarnings("serial")
public class Prestito implements Serializable{
	/**
	 * @invariant invariante()
	 */
	private static final int UNO = 1;
	private int idPrestito;
	private int idRisorsa;
	private String descrizione;
	private Calendar inizio;
	private Calendar fine;
	private boolean rinnovato;
	private int proroga;
	private int termineProroga;
	
	
 	public Prestito(int idPrestito, int idRisorsa, String descrizione, Calendar inizio, Calendar fine, int proroga, int termineProroga) {
 		this.idPrestito = idPrestito;
		this.idRisorsa = idRisorsa;
		this.descrizione = descrizione;
		rinnovato = false; // inzializzazione di default, se diventa true non si puo più rinnovare il prestito.
		this.inizio = inizio;
		this.fine = fine;
		this.proroga = proroga;
		this.termineProroga = termineProroga;	
	}
/**
 * verifica che tutte le invarianti di classe siano soddisfatte
 * @pre true
 * @post @nochange
 * @return true se tutte le invarianti sono soddisfatte
 */
 	private boolean invariante() {
	Prestito prestitoPre = this;
	
	boolean invariante=false;
	if(idPrestito>=0 && idRisorsa>=0 && descrizione!=null && inizio!=null && fine!=null && inizio.before(fine) && proroga>=0 && termineProroga>=0 && getIdRisorsa()==idRisorsa && getDescrizione()==descrizione && getIdPrestito()==idPrestito && getFine()==fine && getInizio()==inizio && getProroga()==proroga && getTermineProroga()==termineProroga) invariante=true;
	
	assert prestitoPre==this;
	return invariante ;
}

	/**
	 * ritorna la data di inizio prestito
	 * @pre true
	 * @post @nochange
	 * @return la data
	 */
	public Calendar getInizio() {
		return inizio;
	}
/**
 * setta il la data di inizio prestito
 * @pre inizio!=null
 * @post true
 * @param inizio nuova data di inizio
 */
	public void setInizio(Calendar inizio) {
		assert inizio!=null ;
		this.inizio = inizio;
	}
	/**
	 * ritorna la data di fine prestito
	 * @pre true
	 * @post @nochange
	 * @return la data di fine prestito
	 */

	public Calendar getFine() {
		return fine;
	}
/**
 * setta la data di fine prestito
 * @pre fine!=null
 * @post true
 * @param la data di fine prestito
 */
	public void setFine(Calendar fine) {
		assert fine!=null;
		this.fine = fine;
	}
	/**
	 * verifica se il prestito � scaduto
	 * @pre true
	 * @post @nochange
	 * @return true se il prestito � scaduto
	 */
	public boolean scaduto() {
		if (Calendar.getInstance().after(fine)) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * verifica se il prestito � in scadenza
	 * @pre true
	 * @post @nochange
	 * @return true se il prestito � in scadenza
	 */
	public boolean inScadenza() {
		assert invariante();
		Prestito prestitoPre = this;
		
		Calendar ceiling = Calendar.getInstance();
		Calendar floor = Calendar.getInstance();
		
		ceiling.set(fine.get(Calendar.YEAR), fine.get(Calendar.MONTH), fine.get(Calendar.DAY_OF_MONTH));
		floor.set(fine.get(Calendar.YEAR), fine.get(Calendar.MONTH), fine.get(Calendar.DAY_OF_MONTH));
		
		ceiling.add(Calendar.DAY_OF_YEAR, UNO); // 1 per fare il >= essendoci solo after che corrisponde a >
		floor.add(Calendar.DAY_OF_YEAR, -(termineProroga + UNO));
		
		/*
		if (Calendar.getInstance().after(floor) && Calendar.getInstance().before(ceiling) && !rinnovato) {
			return true;
		} else {
			return false;
		}
		*/
		
		//test scadenze senza dover aspettare 1 mese
				Calendar dataPerProve = Calendar.getInstance(); 
				
				dataPerProve.set(2018, 4, 14);
		
		if (dataPerProve.after(floor) && dataPerProve.before(ceiling) && !rinnovato) {
			assert invariante() && prestitoPre==this;
			return true;
		} else {
			assert invariante() && prestitoPre==this;
			return false;
		}
	}
/**
 * verifica se il prestito � gi� stato rinnovato
 * @pre true
 * @post @nochange
 * @return true se il prestito � gi� stato rinnovato
 */
	public boolean isRinnovato() {
		return rinnovato;
	}
/**
 * imposta il valore del rinnovo del prestito
 * @pre true
 * @post true
 * @param rinnovato nuovo valore
 */
	public void setRinnovato(boolean rinnovato) {
		this.rinnovato = rinnovato;
	}
/**
 * ritorna il valore della proroga disponibile per il prestito
 * @pre true
 * @post @nochange
 * @return il valore della proroga
 */
	public int getProroga() {
		return proroga;
	}
/**
 * setta il valore della proroga disponibile per il prestito
 * @pre proroga>=0
 * @post true
 * @param proroga
 */
	public void setProroga(int proroga) {
		assert proroga>=0 ;
		this.proroga = proroga;
	}
/**
 * ritorna il tempo entro cui � possibile prorogare il prestito
 * @pre true
 * @post @nochange
 * @return giorni dalla scadenza del prestito
 */
	public int getTermineProroga() {
		return termineProroga;
	}
/**
 * setta il numero di giorni dalla scadenza del prestito entro ci � possibile chiedere la proroga dello steso
 * @pre terminaProroga>=0
 * @post true
 * @param termineProroga
 */
	public void setTermineProroga(int termineProroga) {
		this.termineProroga = termineProroga;
	}
/**
 * proroga il prestito in questione 
 * @pre nonRinnovato()
 * @post true
 */
	public void proroga() {
		assert invariante() && this.rinnovato==false ;
		rinnovato = true;
		fine.add(Calendar.DAY_OF_YEAR, proroga);
		assert invariante();
	}
	/**
	 * ritorna la stringa descrittiva del prestito
	 * @pre true
	 * @post @nochange && @return!=null
	 * @return la stringa descrittiva
	 */
	public String toString() {
		assert invariante() ;
		Prestito prestitoPre = this;
		
		StringBuffer descrizione = new StringBuffer();
		
		descrizione.append("ID prestito: " + idPrestito + "\nRisorsa[" + this.descrizione +"]");
		descrizione.append(" Inizio: "+inizio.get(Calendar.DAY_OF_MONTH)+"/"+(1+inizio.get(Calendar.MONTH))+"/"+inizio.get(Calendar.YEAR));
		descrizione.append(" Scadenza: "+fine.get(Calendar.DAY_OF_MONTH)+"/"+(1+fine.get(Calendar.MONTH))+"/"+fine.get(Calendar.YEAR));
		String risultato = descrizione.toString();	
		
		assert invariante() && risultato!=null && prestitoPre==this ;
		return 	risultato;
	}

	/**
	 * ritorna il valore dell'id della risorsa prestata
	 * @pre true
	 * @post @nochange
	 * @return  l'id della risorsa
	 */
	public int getIdRisorsa() {
		return idRisorsa;
	}

	/**
	 * setta il valore dell'id della risorsa prestata
	 * @pre idRisorsa>=0
	 * @post true
	 * @param il valore da settare
	 */
	public void setIdRisorsa(int idRisorsa) {
		assert idRisorsa>=0 ;
		
		this.idRisorsa = idRisorsa;
	}

	/**
	 * ritorna la stringa descrittiva del prestito
	 * @pre true
	 * @post @nochange
	 * @return la descrizione
	 */
	public String getDescrizione() {
		return descrizione;
	}

	/**
	 * setta il valore della descrizione del prestito
	 * @pre descrizione!=null
	 * @post true
	 * @param la descrizione da settare
	 */
	public void setDescrizione(String descrizione) {
		assert descrizione!=null ;
		
		this.descrizione = descrizione;
	}

	/**
	 * ritorna il valore dell'id del prestito in questione
	 * @pre true
	 * @post @nochange
	 * @return l'id del Prestito
	 */
	public int getIdPrestito() {
		return idPrestito;
	}

	/**
	 * setta il valore dell'id del prestito in questione
	 * @pre idPrestito>=0
	 * @post true
	 * @param idPrestito the idPrestito to set
	 */
	public void setIdPrestito(int idPrestito) {
		assert idPrestito>=0 ;
		
		this.idPrestito = idPrestito;
	}
}