package dafault;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import it.unibs.ing.mylib.*;

@SuppressWarnings("serial")

public class Sistema  implements Serializable{
	/**
	 * @invariant invariante() 
	 */
	private static final String NOMEFILEUTENTI = "Utenti.dat";
	
	static final String TITOLO_MENU_FRUITORE = "Menu Fruitore";
	static final String[] vociMenuFruitore = {"Visualizza Prestiti", "Richiedi prestito", "Rinnova prestito", "Rinnovo iscrizione"};
	
	private static final String TITOLO_SELEZIONA_ATTRIBUTO = "Scegli attributo con cui filtrare la ricerca";
	private static final String[] vociMenuSelezionaAttributo = {"Nome","Autore/Regista","Casa editrice/Casa Di produzione","Genere","Lingua","Anno di pubblicazione","Numero di pagine", "Durata"};
	
	private static final String TITOLO_MENU_PRESTITO = "Scegli come desideri cercare la risorsa da aggiungere.";
	private static final String[] opzioniDiricerca = {"Ricerca per attributo", "Ricerca navigando l'archivio"};
	
	private static final String MESSAGGIO_ERRORE = "Scelta non valida";
	private static final String TITOLO_MENU_OPERATORE = "Menu Operatore.";
	private static final String[] VOCI_MENU_OPERATORE = {"Visualizza dati fruitori", "Visualizza dati e prestiti dei fruitori", "Apri archivio","visualizza storico"};
	private static final String[] OPZIONI_MENU_RICERCA = {"Esplora archivio", "Ricerca per attributo"};
	private static final String MESSAGGIO_PRESTITO_NON_CONCESSO = "Non si pu� ottenere questa risorsa in prestito";
	private static final String NO_IN_SCADENZA = "Non ci sono prestiti in scadenza";
	private static final String TITOLO_IN_SCADENZA = "Ecco i prestiti in scadenza";
	private static final String BUON_FINE = "Operazione eseguita correttamente";
	private static final String TITOLO_MENU_STORICO = "scegli l'opzione desiderata";
	private static final String[] vociMenuStorico = {"visualizza storico completo","visualizza numero prestiti per anno solare","visualizza numero proroghe per anno solare","visualizza risorsa prestata pi� volte per anno solare","visualizza i prestiti per fruitore per anno solare"};
	private static final int LIMITE_ABILITAZIONE_RINNOVO_ISCRIZIONE=10;
	
	private static Archivio archivio = new Archivio();
	
	private static ArrayList<Utente<? extends Utente>> utenti;
	

	
		/**
		 * verifica che le invarianti di classe siano verificate
		 * @pre true
		 * @post @nochange
		 * @return true se sono verificate tutte le ivnarianti
		 */
	private static boolean invariante() {
		boolean invariante = false ;
		ArrayList<Utente<? extends Utente>> operatoriPre = utenti ;
		Archivio archivioPre = archivio ;

		if( utenti.size()>0  && utenti!= null && archivio!=null) invariante = true ;
	
		assert operatoriPre==utenti && archivioPre == archivio;
		return invariante ;
	}
           
	
	
	
	/**
	 * verifica se l'utente relativo a username e password � iscritto (l'usernam e degli utenti deve essere univoco)
	 * @param username del fruitore
	 * @param password del fruitore
	 * @return true se il fruitore � iscritto
	 * @pre username!=null && password!=null 
	 * @post @nochange
	 */
	public static boolean cercaUtente(String username, String password) {
		assert invariante() && username!=null && password!=null  ;
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		Archivio archivioPre = archivio ;
		int numFruitore;
		
		if((numFruitore = posizioneUtente(username)) != -1 ) {									
			
			if(utenti.get(numFruitore).getPassword().equals(password)) {
				
				assert invariante()&& fruitoriPre == utenti && archivioPre == archivio;
				return true;
			}
		}
		assert invariante()&& fruitoriPre == utenti && archivioPre == archivio;
		return false;
	}
	
	/**
	 *  cerca l'operatore scelto e restituisco la sua posizione nell'array
	 * @param username dell'operatore scelto
	 * @return il valore della sua posizione nell'array
	 * @pre username!= null && username!= ""
	 * @post @nochange
	 */
	static int posizioneUtente(String username) {	
		assert invariante() && username!= null && username!= "" ;
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		Archivio archivioPre = archivio ;
		
		for (int i=0; i<utenti.size(); i++) 
			if(utenti.get(i).getUsername().equals(username)) {
				
				assert invariante() &&  fruitoriPre == utenti && archivioPre == archivio ;
				return i;
			}
		assert invariante()&& fruitoriPre == utenti && archivioPre == archivio ;
		return -1;
	}
	
	/**
	 * iscrive nuovo fruitore se maggiorenne chiedendo dati necessari all'utente
	 * @pre true 
	 * @post @return!= null &&(@nochange || fruitori.size()@pre +1==fruitori.size() )
	 * @return l'username del fruitore iscritto oppure una stringa vuota
	 */
	public static  String iscrizioneFruitore() {
		assert invariante() ;
		ArrayList<Utente<? extends Utente>> utentiPre = utenti ;
		Archivio archivioPre = archivio ;
		int utentiLun = utenti.size() ;
		
		String username;
		boolean finito=false;
		
		do {
			username=InputDati.leggiStringaNonVuota("Inserisci username\n");
			if (posizioneUtente(username) != -1)
				System.out.println("username gi� presente \n");
			else
				finito=true;
		}while(!finito);
		
	
		utenti.add(	Utente.registrati(username));
		rimuoviUtentiNulli();
		
		archivio.storiaIscrizioneFruitore(username) ; //crea evento nell'archivio storico 
		
		assert invariante() && (( utentiPre == utenti && archivioPre == archivio)|| utenti.size() == utentiLun) && username != null;		
		return username;
	}
	
	private static void rimuoviUtentiNulli() {

		for(int i=0;i<utenti.size();i++)
	
			if(utenti.get(i)==null)
			
				utenti.remove(i);
	}




	/**
	 * elimino fruitori decaduti e aggiorno il numero di licenze delle risorse liberate
	 * @pre true
	 * @post operatoriNoChange()
	 */
	public static void eliminaDecaduti() {
		assert invariante();
		
		if(utenti!=null) {
			for (int i=0; i<utenti.size(); i++) 
				if(utenti.get(i).iscrizioneScaduta()) {
					
					//array contenente gli id delle risorse relative a prestiti da restituire
					ArrayList<Integer> prestitiDaRestituire = utenti.get(i).restituisciRisorseInPrestito();
					
					int catRisorsa;
					
					if(prestitiDaRestituire!=null)
						for (int j=0; j<prestitiDaRestituire.size(); j++) {
							catRisorsa=archivio.trovaIdCategoria(prestitiDaRestituire.get(i));
							if(catRisorsa==0 || catRisorsa==1)
								archivio.aggiornaLicenze(prestitiDaRestituire.get(j), 1); 
						}

					archivio.storiaFruitoreDecaduto(utenti.get(i).getUsername());

					utenti.remove(i);
				}
			assert invariante()  ;
		}
	}
/**
 * elimina i prestiti scaduti per oni fruitore iscritto e aggiorna le licenze alle rispettive risorse liberate
 * @pre true
 * @post operatoriNoChange()	
 */
	public static void eliminaPrestitiScaduti() {
		assert invariante();
		
		if(utenti!=null) {
			for (int i=0; i<utenti.size(); i++) {
				
				//array contenente gli id delle risorse relative a prestiti scaduti
				ArrayList<Integer> prestitiScaduti = utenti.get(i).eliminaPrestitiScaduti();
				
				int catRisorsa;
				
				if(prestitiScaduti!=null)
					for (int j=0; j<prestitiScaduti.size(); j++) {
						catRisorsa=archivio.trovaIdCategoria(prestitiScaduti.get(i));
						if(catRisorsa==0 || catRisorsa==1)
							archivio.aggiornaLicenze(prestitiScaduti.get(j), 1); //1 � il mio flag per incrementare numeroLicenze
						if(archivio.numeroLicenzeRisorsa(prestitiScaduti.get(j))==1) archivio.risorsaDisponibile(prestitiScaduti.get(j));
					}
			}
		}
	}
	/**
	 * rinnova l'iscrizione del fruitore selezionato
	 * @param numUtente posizione del fruitore nell'array
	 * @pre numFruitore>=0 && numFruitore<fruitori.size() 
	 * @post operatoriNoChange()
	 */
	private static void rinnovoIscrizioneFruitore(int numUtente) {
		assert invariante() && numUtente>=0 && numUtente<utenti.size() ;
		
		Calendar data_scadenza=utenti.get(numUtente).getDataScadenza();
		Calendar data_odierna=Calendar.getInstance();
	
		if(data_scadenza!=null) {
		//calcolo della differenza di giorni tra data scadenza e data odierna 
		long diff=(data_scadenza.getTimeInMillis()-data_odierna.getTimeInMillis())/ 86400000;
		
		if (diff > LIMITE_ABILITAZIONE_RINNOVO_ISCRIZIONE) {
			Stampa.aVideo("mancano ancora "+ diff+" giorni alla scadenza, impossibile soddisfare richiesta\n");

		}else {
			utenti.get(numUtente).aggiornaDataScadenza();
			archivio.storiaRinnovoIscrizioneFruitore(utenti.get(numUtente).getUsername());
		}
		}
	}
/**
 * ritorna la descrizione di tutti i fruitori iscritti	
 * @return stringa descrittiva 
 * @pre true
 * @post @nochange && @return!= null 
 */
	public static String elencoFruitori() {
		assert invariante() ;
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		Archivio archivioPre = archivio ;

		if(utenti==null || utenti.size()==0) Stampa.aVideo("elenco fruitori vuoto");
		
		StringBuffer descrizione = new StringBuffer();
		for(Utente<?> f: utenti) {
			if(f.isFruitore())
			descrizione.append(f.toString()+"\n\n");
		}
		String risultato = descrizione.toString();	
	
		assert invariante() && fruitoriPre == utenti && archivioPre == archivio && risultato != null;
		return risultato ;
	}
	
/**
 * ritorna la descrizione di tutti i fruitori completa di prestiti per ognuno di essi 
 * @pre true
 * @post @nochange && @return!= null
 * @return
 */
	public static String elencoFruitoriFull() {
		assert invariante() ;
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		Archivio archivioPre = archivio ;

		if(utenti==null || utenti.size()==0 ) Stampa.aVideo("elenco fruitori vuoto");
		
		StringBuffer descrizione = new StringBuffer();
		for(Utente<? extends Utente> f: utenti)
			if(f.isFruitore())
			descrizione.append(f.visualizzaTutto()+"\n\n");
		String risultato = descrizione.toString();	
		
		assert invariante()  && fruitoriPre == utenti && archivioPre == archivio && risultato != null;
		if(risultato==null) return "";
		return risultato;
	}
	
	/**
	 * Gestisce le operazioni del fruitore.
	 * @param username del fruitore.
	 * @pre username!=null
	 * @post operatoriNoChange()
	 */
	public static void usaFruitore(String username) {
		assert invariante() && username!= null;
		
		MyMenu menu_fruitore = new MyMenu(TITOLO_MENU_FRUITORE, vociMenuFruitore);
		int scelta, numFruitore;
		
		numFruitore = posizioneUtente(username);//numero fruitore nell'array
		
		
		if (numFruitore==-1 || !utenti.get(numFruitore).isFruitore()) {
			assert invariante();
			return;
			}
		
		utenti.get(numFruitore).setIdPrestito();
				
		do {
			scelta = menu_fruitore.scegli();
					
			switch (scelta) {
			
				case 1:
					Stampa.aVideo(BelleStringhe.incornicia(utenti.get(numFruitore).visualizzaPrestitiFruitore()));
					break;
					
				case 2:
					richiediPrestito(numFruitore);
					break;
					
				case 3:
					rinnovaPrestito(numFruitore);
					
					break;
					
				case 4:
					rinnovoIscrizioneFruitore(numFruitore);
					break;
			}
		}while(scelta!=0);
		assert invariante() ;
		return;
	}

	
	
	private static void rinnovaPrestito(int numFruitore) {


		ArrayList<Integer> inScadenza = utenti.get(numFruitore).inScadenza();
		
		if (inScadenza != null) {
			String[] inScadenzaString = new String[inScadenza.size()];
			
			for (int i = 0; i < inScadenzaString.length; i++) {
				inScadenzaString[i] = utenti.get(numFruitore).getDescrizionePrestito(inScadenza.get(i));
			}
			
			MyMenu menuInScadenza = new MyMenu(TITOLO_IN_SCADENZA, inScadenzaString);
			int scegliDaRinnovare = menuInScadenza.scegli();
			
			switch (scegliDaRinnovare) {
			case 0:
				break;

			default:
				utenti.get(numFruitore).rinnova(inScadenza.get(scegliDaRinnovare-1));
				archivio.prorogaPrestito(utenti.get(numFruitore).getUsername(),inScadenza.get(scegliDaRinnovare-1));
				break;
			}
		} else {
			Stampa.aVideo(NO_IN_SCADENZA);
		}
	}




	private static void richiediPrestito(int numFruitore) {

	int risorsaScelta = selezionaRisorsa();
	if (risorsaScelta==-1) {
		Stampa.aVideo("Nessuna corrispondenza");
		return ;
		}
	

	if (verificaPrerequisitiPrestito(utenti.get(numFruitore), risorsaScelta)) {
		
		archivio.aggiornaLicenze(risorsaScelta, 0); // flag per diminuire numeroLicenze
		
		Calendar fine = Calendar.getInstance();
		fine.add(Calendar.DAY_OF_YEAR,  archivio.durataPrestitoDataUnaRisorsa(risorsaScelta));
		
		utenti.get(numFruitore).richiediPrestito(risorsaScelta, archivio.getDescrizioneRisorsa(risorsaScelta), Calendar.getInstance(), fine, archivio.durataProrogaDataUnaRisorsa(risorsaScelta), archivio.termineProrogaDataUnaRisorsa(risorsaScelta));
		
		Stampa.aVideo(BelleStringhe.rigaIsolata(BUON_FINE));
		archivio.storiaNuovoPrestito(risorsaScelta,archivio.numeroLicenzeRisorsa(risorsaScelta),utenti.get(numFruitore).getUsername());
	}
	
	else {
		Stampa.aVideo(MESSAGGIO_PRESTITO_NON_CONCESSO);
	}
	}




/**
 * 	verifica se � possibile dare la risorsa scelta in prestito al fruitore scelto
 * @param fruitore scelto
 * @param risorsaScelta id della risorsa
 * @return true se il prestito desiderato soddisfa tutti i requisiti
 * @pre id>=0 fruitore!=null 
 * @post @nochange
 */
	private static boolean verificaPrerequisitiPrestito(Utente<? extends Utente> fruitore, int risorsaScelta) {
		assert invariante() && risorsaScelta>=0 && fruitore!= null;
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		Archivio archivioPre = archivio ;

		if(!fruitore.isFruitore())return false;
		
		int maxRisorsePerCategoria = archivio.maxRisorsePerCategoriaDataUnaRisorsa(risorsaScelta);
		
		ArrayList<Integer> idRisorseGiaPossedute = fruitore.getPrestiti();
		
		int match=0;
		
		int catRisorsa=archivio.trovaIdCategoria(risorsaScelta);
		int catPosseduta;
		
		for (Integer idInEsame : idRisorseGiaPossedute) {
			catPosseduta=archivio.trovaIdCategoria(idInEsame);
		
			if(catRisorsa==catPosseduta)
				match++;
		}
		
		
		int numLicenze=archivio.numeroLicenzeRisorsa(risorsaScelta);
		
		if( !fruitore.giaPresente(risorsaScelta) && match < maxRisorsePerCategoria && numLicenze>0 ) {
			
			assert invariante()  && fruitoriPre == utenti && archivioPre == archivio ;
			return true;	
			}
		assert invariante() && fruitoriPre == utenti && archivioPre == archivio ;
		return false;
	}

				
	
	
	/**
	 *  metodo principale per la gestione delle operazioni degli operatori
	 * @param username dell'operatore loggato
	 * @pre true
	 * @post fruitoriNoChange
	 */
	public static void usaOperatore(String username){
		assert invariante() ;
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		
		MyMenu menu_operatore= new MyMenu(TITOLO_MENU_OPERATORE, VOCI_MENU_OPERATORE);
		int numOperatore, scelta;
		
		
		numOperatore=posizioneUtente(username);        
		
		if (numOperatore==-1 || utenti.get(numOperatore).isFruitore()) {                          
		assert invariante() && fruitoriPre == utenti ;
			return;
		}
				
		do {
			scelta = menu_operatore.scegli();
			
			switch (scelta) {
				case 1:
					Stampa.aVideo(elencoFruitori());
					break;
					
				case 2:
					Stampa.aVideo(elencoFruitoriFull());
					break;	
					
				case 3:
					if(archivio.vuoto()) {
						System.out.println("archivio vuoto");
						break;
						}
					cercaRisorse();
					break;
				case 4: 	
					operazioniStorico();
					break;
					}
					
					
				}while(scelta!=0);
		
			assert invariante() && fruitoriPre == utenti ;
			return;
			}

			
	





private static void operazioniStorico() {

	MyMenu menu_storico= new MyMenu(TITOLO_MENU_STORICO, vociMenuStorico);
	int sceltaMenuStorico = menu_storico.scegli();
	
	switch(sceltaMenuStorico) {
	
	case 1: 
		Stampa.aVideo(archivio.getDescrizioneStorico());
		break;
	case 2:
		archivio.numEventoAnnoSolare(Archivio.NUOVO_PRESTITO,"prestiti");
		break;
	case 3:
		archivio.numEventoAnnoSolare(Archivio.PROROGA_PRESTITO,"proroghe");
		break;
	case 4:
		archivio.risorsaPiuPrestata();
		break;
	case 5:
		archivio.prestitiFruitoriAnnoSolare();
		break;
	default : break;					

	}
	}




private static void cercaRisorse() {


	MyMenu menu_ricerca = new MyMenu("Scegli il metodo di ricerca delle risorse :", OPZIONI_MENU_RICERCA);
		
	
			switch(menu_ricerca.scegli()) {
			
			default : break;
			
			case 1:
			
				    MyMenu menu_archivio= new MyMenu("Categorie",archivio.elencoCategorie());
						
					int categoriaScelta=menu_archivio.scegli();
					
					if(categoriaScelta==0) break;
					
					archivio.usaCategoria(categoriaScelta-1);
									
					break;
			
			case 2:
				
				MyMenu menu_attributi= new MyMenu(TITOLO_SELEZIONA_ATTRIBUTO, vociMenuSelezionaAttributo);
			
				int attributoScelto = menu_attributi.scegli();
													
				if(attributoScelto!=0)
					archivio.cercaPerAttributoOmode(attributoScelto);
				
				break;
				
			}
	}




/**
 * ritorna la descrizione di tutti gli operatori esistenti
 * @pre true
 * @post @return != null && @nochange			
 * @return la stringa descrittiva
 */
	public String elencoOperatori() {
		assert invariante() ;
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		Archivio archivioPre = archivio ;
		
				StringBuffer descrizione = new StringBuffer();
				for(Utente<? extends Utente> o: utenti) 
					if(!o.isFruitore())
					descrizione.append(o.toString()+"\n\n");
				
				String risultato = descrizione.toString();
				assert invariante() && fruitoriPre == utenti && archivioPre == archivio && risultato!= null;
				return 	risultato ;	
			}
			

			
/**
 * salvare fruitori e operatori a fine sessione su file 
 * @pre true
 * @post @nochange
 */
	public static void salvaFruitoriOperatori() {
			assert	invariante() ;	
			ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
			Archivio archivioPre = archivio ;
			
			
				
					
				File f = new File(NOMEFILEUTENTI);
				ServizioFile.salvaSingoloOggetto(f, utenti);
			
				
				assert invariante()  && fruitoriPre == utenti && archivioPre == archivio ;
			}
			
	
			
/**
 *  carica da file i fruitori e operatori
 *  @pre true
 *  @post true
 */
	public static void importaFruitoriOperatori() {
				
				File f = new File(NOMEFILEUTENTI);
				@SuppressWarnings("unchecked")
				 ArrayList<Utente<? extends Utente>> a = ( ArrayList<Utente<? extends Utente>>)ServizioFile.caricaSingoloOggetto(f);
				if(a==null) {utenti = new ArrayList<Utente<? extends Utente>>();}
				else {utenti=a; }
				
			
				
				if(utenti.size()==0) {utenti.add(new Operatore("admin", "admin", 18, "admin", "admin")) ; }     //inizializzazione di default 
				
				
				
				assert invariante() ;				  
			 }
/**
 * carica i dati dell'archivio  e dello storico da file
 * @pre true
 * @post fruritoriOperatoriNoChange()
 */
	public static void importaArchivio() {
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		
		archivio.importaDati();

		assert invariante() && fruitoriPre == utenti ;
	}
/** 
 * salva dati dell'archivio e dello storico su file
 * @pre true 
 * @post @nochange
 */
	public static void salvaArchivio() {//e storico
		assert invariante();
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		Archivio archivioPre = archivio ;
		
		archivio.salvaDati();	
		
		assert invariante()  && fruitoriPre == utenti && archivioPre == archivio ;
	}
	
	/**
	 * Permette di selezionare una risorsa nell'archivio.
	 * @return risorsa selezionata.
	 * @pre true 
	 * @post @return>=-1 && @nochange
	 */
	public static int selezionaRisorsa() {
		assert invariante() ;
		ArrayList<Utente<? extends Utente>> fruitoriPre = utenti ;
		Archivio archivioPre = archivio ;
		
		int risorsaScelta = -1;
		
		MyMenu menuPrestito = new MyMenu(TITOLO_MENU_PRESTITO, opzioniDiricerca);
		int scelta;
		
				scelta = menuPrestito.scegli();
			
			switch (scelta) {
				case 1:
					MyMenu menu_attributi= new MyMenu(TITOLO_SELEZIONA_ATTRIBUTO, vociMenuSelezionaAttributo);
					int attributoScelto = menu_attributi.scegli();
					if(attributoScelto!=0 ) risorsaScelta = archivio.cercaPerAttributoFmode(attributoScelto);
				break;
			
				case 2:
					risorsaScelta = archivio.selezionaCategoria();
					break;

			default:
				BelleStringhe.incornicia(MESSAGGIO_ERRORE);
				break;
			}
			
			assert invariante()  && fruitoriPre == utenti && archivioPre == archivio && risorsaScelta>=-1;		
			return risorsaScelta;
	}
/**
 * triggera il metodo che aggiorna il contatore di id prestiti dell'archivio
 * @pre true
 * @post true	
 */
	public static void idCorrente() {
		archivio.idCorrente();
	}


public static boolean isFruitore(int posizioneUtente) {

	return utenti.get(posizioneUtente).isFruitore();
}
}
