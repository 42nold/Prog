package utenti;

import java.io.*;
import java.util.*;

@SuppressWarnings("serial")
public class Fruitore extends Utente implements Serializable{
	/**
	 * @invariant invariante()
	 */
	private static int prestitoAttuale;
	private Calendar data_iscrizione;
	private Calendar data_scadenza;
	private ArrayList<Prestito> prestiti;
	
	/**
	 * istanzia la classe Fruitore
	 * @param _nome
	 * @param _cognome
	 * @param _eta
	 * @param _username
	 * @param _password
	 * @param _data_iscrizione
	 * @param _data_scadenza
	 */
	public Fruitore(String _nome, String _cognome, int _eta, String _username, String _password, Calendar _data_iscrizione, Calendar _data_scadenza) {
		
		super(_nome, _cognome, _eta, _username, _password);
		
		data_iscrizione=_data_iscrizione;
		data_scadenza=_data_scadenza;
		prestiti = new ArrayList<Prestito>();
		
		if (prestiti.size() > 0) {
			prestitoAttuale = prestiti.size(); 
		} else {
			prestitoAttuale = 0;
		}
		assert invariante();
	}
	
	/**
	 * verifica che le invarianti di classe siano verificate
	 * @pre  true
	 * @post @nochange
	 * @return true se le invarianti sono verificate
	 */
	private boolean invariante() {
		Fruitore fruitorePre = this;
		
		boolean invariante = false;
		if(super.invarianteU() && prestitoAttuale>=0 && data_iscrizione!=null && data_scadenza!=null && data_iscrizione.before(data_scadenza) && prestiti!=null && getDataIscrizione()==data_iscrizione && getDataScadenza()==data_scadenza && getPassword()==super.getPassword() &&  getUsername()==super.getUsername() )invariante= true ;
		
		assert fruitorePre == this;
		return invariante;
	}

/**
 * aggiorna il conteggio degli id dei prestiti attivi
 * @pre true
 * @post true
 */
	public void setIdPrestito() {
		assert invariante();
		
		int id=idPrestitoMax();
		if(id!=-1) {
			prestitoAttuale=id;
		//inizia dal primo codice disponibile successivo
			prestitoAttuale++;
		}else
			prestitoAttuale=0;
		
		assert invariante();
	}
	/**
	 * cerca il massimo id prestito tra quelli esistenti
	 * @pre true
	 * @post @nochange
	 * @return il valore del'id massimo oppure -1
	 */
	private int idPrestitoMax() {
		assert invariante();
		Fruitore fruitorePre = this;
		
		if(prestiti.size()>0) {
			int max = prestiti.get(0).getIdPrestito();
			
			for(int i=1;i < prestiti.size();i++)
				if(prestiti.get(i).getIdPrestito()>max)
					max=prestiti.get(i).getIdPrestito();
		
			assert invariante() && fruitorePre == this;
			
			return max;
		}
		assert invariante() && fruitorePre == this;
		
		return -1;
	}
	
	/**
	 * verifica se la risorsa in ingresso � gi� stata presa in prestito
	 * @pre risorsa>=0
	 * @post @nochange	
	 * @param risorsa id della risorsa 
	 * @return true se la risorsa � gi� tra i prestiti attivi 
	 */
	public boolean giaPresente(int risorsa) {
		assert invariante() && risorsa>=0 ;
		Fruitore fruitorePre = this;
		
		for (Prestito prestito : prestiti) {
			if (prestito.getIdRisorsa()==risorsa) {
				
				assert invariante() && fruitorePre == this;
				return true;
			}
		}
		
		assert invariante() && fruitorePre == this;
		return false;
	}
/**
 * ritorna la data di iscrizione del fruitore
 * @pre true
 * @post @nochange
 * @return data di iscrizione
 */
	private Calendar getDataIscrizione() {
	     return data_iscrizione ;
	}
	/**
	 * ritorna la data di scadenza dell'iscrizione del fruitore
	 * @pre true
	 * @post @nochange
	 * @return la data di scadenza
	 */
	public Calendar getDataScadenza() {
		return data_scadenza;
	}
	
	/**
	 * aggiorna la data di scadenza a +5 anni
	 * @pre true
	 * @post
	 */
	public void aggiornaDataScadenza(){
		assert invariante();
		
		Calendar d=Calendar.getInstance();
		d.add(Calendar.YEAR, 5);
		data_scadenza=d;
		
		assert invariante() ;
	}
	
	/**
	 * Crea e aggiunge un prestito dopo il controllo dei vincoli.
	 * @param risorsa la risorsa richiesta in prestito.
	 * @param descrizioneRisorsa 
	 * @param termineProroga 
	 * @param durataProroga 
	 * @param fine 
	 * @param calendar 
	 * @pre idRisorsa>=0 && descrizioneRisorsa!=null && calendar!=null && fine!=null && durataProroga>=0 && termineProroga>=0
	 * @post prestitiSize() == prestitiSize()@pre+1
	 */
	public void richiediPrestito(int idRisorsa, String descrizioneRisorsa, Calendar calendar, Calendar fine, int durataProroga, int termineProroga) {
		assert invariante() && idRisorsa>=0 && descrizioneRisorsa!=null && calendar!=null && fine!=null && durataProroga>=0 && termineProroga>=0 ;
		int prestitiPre = prestiti.size() ;
		
		int idPrestito = prestitoAttuale;
		prestitoAttuale++;
		prestiti.add(new Prestito(idPrestito, idRisorsa, descrizioneRisorsa, calendar, fine, durataProroga, termineProroga));
	
		assert invariante() && prestitiPre+1 == prestiti.size() ;
	}
	
	/**
	 * ritorna la stringa descrittiva del fruitore
	 * @pre true
	 * @post @return!= null && @nochange
	 * @return la descrizione
	 */
	public String visualizzaDatiFruitore() {
		assert invariante() ;
		Fruitore fruitorePre = this ;
	
		StringBuffer descrizione = new StringBuffer();
		descrizione.append(super.toString());
		descrizione.append("Data iscrizione: "+data_iscrizione.get(Calendar.DAY_OF_MONTH)+"/"+(data_iscrizione.get(Calendar.MONTH)+1)+"/"+data_iscrizione.get(Calendar.YEAR)+"\n");
		descrizione.append("Data scadenza: "+data_scadenza.get(Calendar.DAY_OF_MONTH)+"/"+(data_scadenza.get(Calendar.MONTH)+1)+"/"+data_scadenza.get(Calendar.YEAR)+"\n");
		String risultato = descrizione.toString();
		
		assert invariante() && risultato!=null && fruitorePre==this;
		return risultato ;
	}
/**
 * ritorna una stringa descrittiva dei prestiti attivi del fruitore
 * @pre true
 * @post @return!= null && @nochange
 * @return la descrizione dei prestiti
 */
	public String  visualizzaPrestitiFruitore() {
		assert invariante() ;
		Fruitore fruitorePre = this;
		
		StringBuffer descrizione = new StringBuffer();
		int i = 1;
		for (Prestito prestito : prestiti) {
			
			descrizione.append(i+" < "+prestito.toString()+" >\n");
			i++;
		}		
		String risultato = descrizione.toString();	
		
		assert invariante() && risultato!= null && fruitorePre == this;
		return risultato;
	}
/**
 * ritorna la stringa di descrizione del fruitore e dei suoi prestiti
 * @pre true
 * @post @return!= null && @nochange
 * @return la stringa descrittiva	
 */
	public String  toString() {
		assert invariante() ;
		Fruitore fruitorePre = this;
		
		StringBuffer descrizione = new StringBuffer();
		descrizione.append(visualizzaDatiFruitore());
		descrizione.append(visualizzaPrestitiFruitore());
		String risultato = descrizione.toString();	
		
		assert invariante() && risultato!= null && fruitorePre == this;
		return risultato;
	}	
/**
 * setta il vettore dei prestiti con quello in ingresso
 * @param prestiti nuovo vettore
 * @pre prestiti!=null
 * @post true
 */
	public void setPrestiti(ArrayList<Prestito> prestiti) {
		assert invariante() && prestiti!=null;
		
		this.prestiti = prestiti;
		
		assert invariante();
	}
	
	public int getNumeroPrestiti() {
		return prestiti.size();
	}

	/**
	 * ritorna una lista dei prestiti in scadenza
	 * @pre true
	 * @post @return !=null && @nochange
	 * @return la lista di prestiti(id)
	 */
	public ArrayList<Integer> inScadenza() {
		assert invariante();
		Fruitore fruitorePre = this;
		
		ArrayList<Integer> prestitiInScadenza = new ArrayList<>();
		for (Prestito prestito : prestiti) {
			if (prestito.inScadenza()) {
				prestitiInScadenza.add(prestito.getIdPrestito());
			}
		}
		assert invariante() && fruitorePre == this && prestitiInScadenza!= null ;
		return prestitiInScadenza;
	}

	public void rinnova(Integer integer) {
		for (Prestito prestito : prestiti) {
			if (prestito.getIdPrestito() == integer && !prestito.isRinnovato()) {
				prestito.proroga();
			}
		}
	}
	
	/**
	 *  restituisce array contenente gli id delle risorse relative a prestiti scaduti e li elimina
	 *  @pre true
	 *  @post @return!=null
	 *  @return lista degli id risorsa dei prestiti eliminati
	 */
	public ArrayList <Integer> eliminaPrestitiScaduti() {
		assert invariante() ;
		
		ArrayList<Integer> prestitiScaduti = new ArrayList<Integer>();
		
		for (Prestito prestito : prestiti) {
			if (prestito.scaduto()) {
				prestitiScaduti.add(prestito.getIdRisorsa());
				prestiti.remove(prestito);
			}
		}
		assert invariante() && prestitiScaduti!=null ;
		return prestitiScaduti;
	}
	
	/**
	 *  restituisce array contenente gli id delle risorse da restituire ed elimina tutti i prestiti
	 * @return lista di id delle risorse inerenti ai prestti attivi
	 * @pre true
	 * @post @return != null  
	 */
		public ArrayList <Integer> restituisciRisorseInPrestito() {
			assert invariante() ;
			
			ArrayList<Integer> prestitiDaRestituire= new ArrayList<Integer>();
			
			for (Prestito prestito : prestiti) {
					prestitiDaRestituire.add(prestito.getIdRisorsa());
					prestiti.remove(prestito);
				}
			assert invariante() && prestitiDaRestituire!=null ;
			return prestitiDaRestituire;
		}
/**
 * ritorna la descrizione del prestito scelto 
 * @param idPrestito posizione del prestito nell'array
 * @return la stringa descrittiva
 * @pre idPrestito< prestitiSize() && idPrestito>=0 
 * @post @nochange
 */
	public String getDescrizionePrestito(Integer idPrestito) {
		assert invariante() && idPrestito< prestiti.size() && idPrestito>=0 ; 
		Fruitore fruitorePre = this;
		
		String risultato =  prestiti.get((int)idPrestito).toString();
		
		assert invariante() && fruitorePre == this;
		return risultato;
	}

	/**
	 * setta la data di iscrizione del fruitore
	 * @param data_iscrizione valore da settare
	 * @pre data_iscrizione!=null 
	 * @post true
	 */
	public void setData_iscrizione(Calendar data_iscrizione) {
		assert invariante() && data_iscrizione!=null ;
		
		this.data_iscrizione = data_iscrizione;
	
		assert invariante() ;
	}
	
	/**
	 * setta la data di scadenza dell'iscrizione  del fruitore
	 * @param data_scadenza valore da settare
	 * @pre data_scadenza!=null 
	 * @post true
	 */
	public void setData_scadenza(Calendar data_scadenza) {
		assert invariante() && data_scadenza != null ;
	
		this.data_scadenza = data_scadenza;
	
		assert invariante() ;
	}

	/**
	 * ritorna la lista delle risorse date in prestito al fruitore
	 * @pre true
	 * @post @return != null && @nochange
	 * @return la lista di id delle risorse prestate
	 */
	public ArrayList<Integer> getPrestiti() {
		assert invariante();
		Fruitore fruitorePre = this;
		
		ArrayList<Integer> listaId = new ArrayList<>();
		
		for (Prestito prestito : prestiti) {
			listaId.add(prestito.getIdRisorsa());
		}
		assert invariante() && listaId != null && fruitorePre == this;
		return listaId;
	}
	
}