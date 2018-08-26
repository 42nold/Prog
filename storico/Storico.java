package storico;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import it.unibs.ing.mylib.ServizioFile;
import risorse.Categoria;
import utility.Load;
import utility.Save;

@SuppressWarnings("serial")

public class Storico implements Serializable{
	

	/**
	 * rappresentazione di una linea del tempo composta da eventi
	 * @invariant invariante()
	 */
	private static final String NOMEFILESTORICO = "storico.dat";

	private static final String ISCRIZIONE_FRUITORE = "ISCRIZIONE DEL FRUITORE: ";
	
	private static final String FRUITORE_DECADUTO = "E' DECADUTO IL FRUITORE: ";
	
	private static final String RINNOVO_ISCRIZIONE = "ISCRIZIONE RINNOVATA PER IL FRUITORE: ";
	
	private static final String RISORSA_AGGIUNTA = "AGGIUNTA LA RISORSA : ";

	private static final String RISORSA_ELIMINATA = "E' STATA ELIMINATA LA RISORSA : ";

	private static final String NUOVO_PRESTITO = "NUOVO PRESTITO CONCESSO A : ";

	private static final String TERMINE_DISPONIBILITA = "LA RISORSA NON E' PIU' DISPONIBILE AL PRESTITO";

	private static final String PROROGA_PRESTITO = "PROROGA DEL PRESTITO A : ";

	private static final String RISORSA_DISPONIBILE = "LA RISORSA E' NUOVAMENTE DISPONIBILE AL PRESTITO : ";

	
	private static ArrayList<Evento> storia;
	private Save save;
	private Load load;
	

	 /**
	  * inizializza l'array storia con un array vuoto
	  */
	public Storico () {
		storia  = new ArrayList<Evento>() ;
		assert invariante();
	}

	/**
	 * aggiungi evento di iscrizione nuovo fruitore all'array storia
	 * @param username fruitore che si è iscritto
	 * @pre true
	 * @post size() == size()@pre + 1
	 */
	public void iscrizioneFruitore(String username) {
		int sizePrima = size();
		assert invariante();
		
		storia.add(new Evento(ISCRIZIONE_FRUITORE+username, -1));
		
		assert(size() == sizePrima+1) ;
		assert invariante() ;
		}
	/**
	 * aggiungi evento di eliminazione risorsa dall'array storia
	 * @param id id risorsa eliminata
	 * @pre true
	 * @post size() == size()@pre + 1
	 */
	public static void risorsaEliminata(int id) {
		int sizePrima = size();
		assert invariante();
		
		storia.add(new Evento(RISORSA_ELIMINATA,id));
		
		
		assert (size() == sizePrima+1) ;
		assert invariante();
	}
	
	/**
	 * aggiungi evento di decadimento fruitore all'array storia
	 * @param username fruitore che è decaduto
	 * @pre true
	 * @post size() == size()@pre + 1
	 */
	public void FruitoreDecaduto(String username) {
		int sizePrima = size();
		assert invariante() ;

		storia.add(new Evento(FRUITORE_DECADUTO+username, -1));
		
		assert (size() == sizePrima+1) ;
		assert invariante() ;

	}
	/**
	 * aggiungi evento di rinnovo iscrizione di un fruitore all'array storia
	 * @param username fruitore che ha rinnovato l'iscrizione
	 * @pre true
	 * @post size() == size()@pre + 1
	 */
	public void RinnovoIscrizioneFruitore(String username) {
		int sizePrima = size();
		assert invariante() ;

		storia.add(new Evento(RINNOVO_ISCRIZIONE+username, -1));

		assert(size() == sizePrima+1) ;
		assert invariante() ;

	}
	/**
	 * aggiungi evento di aggiunta risorsa all'array storia
	 * @param id id risorsa aggiunta
	 * @pre true
	 * @post size() == size()@pre + 1
	 */

	public static void risorsaAggiunta(int id) {
		int sizePrima = size();
		assert invariante() ;

		storia.add(new Evento(RISORSA_AGGIUNTA,id));
		
		assert(size() == sizePrima+1) ;
		assert invariante() ;
	}
	/**
	 * restituisce una stringa con la descrizione di ogni evento nell'array storia
	 * @pre true
	 * @post @nochange
	 * @return stringa con elenco di eventi 
	 */
	public String toString() {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		StringBuffer a = new StringBuffer();
		for(Evento evento : storia) {
			a.append(evento.toString()+"\n");
			
		}
		
		assert(storiaPre.equals(storia))	;
		assert invariante() ;

		
		return a.toString() ;
	}
	/**
	 * salva l'array storia su file esterno
	 * @pre true
	 * @post @nochange
	 */
	public void salvaDati() {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		save.salvaDatiSuFile(NOMEFILESTORICO, storia);
	
		assert storiaPre==storia;
		assert invariante() ;

	}
	/**
	 * carica l'array storia da file esterno
	 * @pre true
	 * @post invariante()
	 */
	public void importaDati() {
		assert invariante() ;

		ArrayList<Evento> b = (ArrayList<Evento>) load.importaDatiDaFile(NOMEFILESTORICO);
		
		if(b==null) 
			storia = new ArrayList<Evento>();
		else 
			storia=b;
		
		assert invariante();
	}
	/**
	 * verifica che l'array storia non sia nullo
	 * @pre true
	 * @post @nochange
	 * @return true se l'array storia non è nullo 
	 */
	private static boolean invariante() {
		ArrayList<Evento> storiaPre = storia;
	
		boolean invariante = false ;
		if (storia!=null) invariante=true ;
		
		assert storiaPre==storia;

		return invariante;
	}

	/**
	 * aggiungi evento di nuovo prestito all'array storia e in caso di esaurimento licenze della risorsa prestata , aggiunta di un evento di termine disponibilità risorsa
	 * @param risorsaScelta id risorsa prestata
	 * @param numeroLicenzaRisorsa numero di licenze rimaste alla risorsa prestata
	 * @param username username del fruitore 
	 * @pre true
	 * @post (size() == size()@pre + 1 || size() == size()@pre +2)
	 */
	public void nuovoPrestito(int risorsaScelta, int numeroLicenzeRisorsa, String username) {
		int sizePrima = size();
		assert invariante() ;

		
		storia.add(new Evento(NUOVO_PRESTITO+username,risorsaScelta));
		if(numeroLicenzeRisorsa==0)	storia.add(new Evento(TERMINE_DISPONIBILITA,risorsaScelta));
		
	
		assert (size() == sizePrima+1 || size() == sizePrima+2) ;
		assert invariante();
		 
	}
	/**
	 * aggiungi evento di proroga prestito all'array storia
	 * @param username username del fruitore richiedente
	 * @param integer idrisorsa prestata
	 * @pre true
	 * @post size() == size()@pre + 1
	 */
	public void prorogaPrestito(String username, Integer integer) {
		assert invariante() ;
		int sizePrima = size();

		storia.add(new Evento(PROROGA_PRESTITO+username,integer));
		
		
		assert (size() == sizePrima+1) ;
		assert invariante() ;
	}
	/**
	 * aggiungi evento di risorsa disponibile all'array storia
	 * @param  id idrisorsa prestata
	 * @pre true
	 * @post size() == size()@pre + 1
	 */
	public void risorsaDisponibile(Integer id) {
		assert invariante() ;
		int sizePrima = size();

		storia.add(new Evento(RISORSA_DISPONIBILE,id));	
		
		
		assert (size() == sizePrima+1) ;
	}
	
	 /** restituisce una stringa con le occorrenze per anno solare dell'evento selezionato
	 * @param nomeEvento evento selezionato
	 * @param descrizione stringa di descrizione dell'occorrenza
	 * @pre eventoValido(nomeEvento)
	 * @post @nochange
	 * @return stringa col risultato della statistica
	 */
	
	public String numEventoAnnoSolare(String nomeEvento, String descrizione) {
		assert invariante() ;
		assert eventoValido(nomeEvento);
		ArrayList<Evento> storiaPre = storia;
		
		
		StringBuffer statistica = new StringBuffer();
		int annoIndice ;
		int count = 0 ;
		int i = 0 ;
		if(storia.size()>0) {
			annoIndice=storia.get(0).getData().get(Calendar.YEAR);
				for(Evento evento :storia) {
					int anno = evento.getData().get(Calendar.YEAR);
					if(evento.getDescrizione().contains(nomeEvento)&&anno==annoIndice) count++;
					if(evento.getDescrizione().contains(nomeEvento)&&anno>annoIndice) {
						statistica.append(annoIndice+" : "+count+descrizione+"\n");
						annoIndice=anno;
						count=1;
					}
					if(i==storia.size()-1) {
						statistica.append(annoIndice+" : "+count+" "+descrizione+"\n");
					}
					i++;
					}
			}
		
		assert storiaPre==storia;
		assert invariante() ;
		
		return statistica.toString();
	}
	/**
	 * restituisce una stringa con l'id delle risorse più prestate per anno solare
	 * @pre true
	 * @post @nochange
	 * @return stringa col risultato della statistica
	 */
	public String risorsaPiuPrestata() {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		StringBuffer statistica=new StringBuffer();
		
	    ArrayList<Integer> prestitiAnno = new ArrayList<Integer>();
	    
	    if (storia.size()>0){
	    	
	    	int anno = storia.get(0).getData().get(Calendar.YEAR);
	    	
	    	for( int annoIndice=anno ; annoIndice<=Calendar.getInstance().get(Calendar.YEAR); annoIndice++) {
	    		for(Evento evento : storia) {
	    			if(evento.getDescrizione().contains(NUOVO_PRESTITO)&&annoIndice==evento.getData().get(Calendar.YEAR)) prestitiAnno.add(evento.getValore()) ;
	    		}
	    		if (prestitiAnno.size()>0) {
	    			ArrayList<String> pariMerito = new ArrayList<String>() ;
	    			int risorsaMax=-1;
	    			int risorsaAtt=prestitiAnno.get(0);
	    			int countMax=0;
	    			int countAtt=0;
	    
	    			do {
	    	
	    				for(int i =0 ; i<prestitiAnno.size();i++) {
	    		
	    					if(prestitiAnno.get(i)==risorsaAtt) {
	    						countAtt++;
	    						prestitiAnno.remove(i);
	    						i--;
	    					}	
	    				}
	    				if(countAtt==countMax) pariMerito.add(""+risorsaAtt);
	    				if(countAtt>countMax) {risorsaMax=risorsaAtt; countMax=countAtt ; pariMerito = new ArrayList<String>();}
	    				if(prestitiAnno.size()>0) risorsaAtt=prestitiAnno.get(0);
	    				countAtt=0;
	    			}while(prestitiAnno.size()>0) ;
	    			if(pariMerito.size()>0) {
	    				statistica.append( anno+" : risorse più prestate a pari merito : "+risorsaMax );
	    			for(String ris : pariMerito)
	    				statistica.append(" , "+ris);
	    			}
	    			else statistica.append(anno+" : risorsa più prestata : "+risorsaMax);
	    		}
	    	}
	    }	    
	   
		assert storiaPre==storia;
		assert invariante() ;
		
	    return statistica.toString();
	
	}
	/**
	 * restituisce una stringa con i prestiti ottenuti per fruitore per anno solare
	 * @pre true
	 * @post @nochange
	 * @return stringa col risultato della statistica
	 */
	public String prestitiFruitoriAnnoSolare() {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		StringBuffer statistica = new StringBuffer();
		if(storia.size()>0) {
			
			int annoIndice =storia.get(0).getData().get(Calendar.YEAR); 
		
			for( int i=annoIndice ; i <= Calendar.getInstance().get(Calendar.YEAR) ; i++){
		
				ArrayList<String> eventiAnnoIndice = new ArrayList<String>();

				for(Evento evento : storia) {
			
					if(evento.getDescrizione().contains(NUOVO_PRESTITO)&&evento.getData().get(Calendar.YEAR)==i) eventiAnnoIndice.add(evento.getDescrizione().substring(NUOVO_PRESTITO.length()));
				}
					while(eventiAnnoIndice.size()>0) {
						String utenteCorrente = eventiAnnoIndice.get(0);
						int count=0;
						for(int j=0 ; j<eventiAnnoIndice.size() ; j++){
							if(eventiAnnoIndice.get(j).equals(utenteCorrente)) {
								count++;
								eventiAnnoIndice.remove(j);
								j--;
							}
						}
						statistica.append(annoIndice+" : utente : "+utenteCorrente+" -> "+count+" prestiti \n");
						
					}					
			}
		
		}
	   
			assert storiaPre.equals(storia);
			assert invariante() ;
			
		return statistica.toString();
	}
	/**
	 * ritorna la lunghezza dell'array storia
	 * @pre true
	 * @post @nochange
	 * @return lunghezza dell'array
	 */
	public static int size() {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;

		int size = storia.size();
		
		assert storiaPre==storia;
		assert invariante() ;
			
		return size;	   
	}
	/**
	 * verifica se il parametro immesso è una costante esistente che identifica un evento
	 * @param nomeEvento stringa da verificare
	 * @pre true
	 * @post @change
	 * @return true se il parametro è valido
	 */
	static boolean eventoValido(String nomeEvento) {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		boolean valido =(nomeEvento.equals(ISCRIZIONE_FRUITORE) ||nomeEvento.equals(FRUITORE_DECADUTO) ||nomeEvento.equals(RINNOVO_ISCRIZIONE) || nomeEvento.equals(RISORSA_AGGIUNTA) || nomeEvento.equals(RISORSA_ELIMINATA) ||nomeEvento.equals(NUOVO_PRESTITO) ||nomeEvento.equals(TERMINE_DISPONIBILITA) ||nomeEvento.equals(PROROGA_PRESTITO) ||nomeEvento.equals(RISORSA_DISPONIBILE)  );
		
		assert storiaPre==storia;
		assert invariante() ;
		
		if(valido)return true ;
		else return false;
	}

}
