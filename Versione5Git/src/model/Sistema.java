package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.BibliotecaView;
import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.ServizioFile;
import it.unibs.ing.mylib.Stampa;

@SuppressWarnings("serial")

public class Sistema  implements Serializable{
	/**
	 * @invariant invariante() 
	 */
	private static final String NOMEFILEFRUITORI = "Fruitori.dat";
	private static final String NOMEFILEOPERATORI = "Operatori.dat";
	
	private static final String TITOLO_MENU_FRUITORE = "Menu Fruitore";
	private static final String[] vociMenuFruitore = {"Visualizza Prestiti", "Richiedi prestito", "Rinnova prestito", "Rinnovo iscrizione"};
	
	private static final String TITOLO_SELEZIONA_ATTRIBUTO = "Scegli attributo con cui filtrare la ricerca";
	private static final String[] vociMenuSelezionaAttributo = {"Nome","Autore/Regista","Casa editrice/Casa Di produzione","Genere","Lingua","Anno di pubblicazione","Numero di pagine", "Durata"};
	
	private static final String TITOLO_MENU_PRESTITO = "Scegli come desideri cercare la risorsa da aggiungere.";
	private static final String[] opzioniDiricerca = {"Ricerca per attributo", "Ricerca navigando l'archivio"};
	
	private static final String MESSAGGIO_ERRORE = "Scelta non valida";
	private static final String TITOLO_MENU_OPERATORE = "Menu Operatore.";
	private static final String[] VOCI_MENU_OPERATORE = {"Visualizza dati fruitori", "Visualizza dati e prestiti dei fruitori", "Apri archivio","visualizza storico"};
	private static final String[] OPZIONI_MENU_RICERCA = {"Esplora archivio", "Ricerca per attributo"};
	private static final String MESSAGGIO_PRESTITO_NON_CONCESSO = "Non si puï¿½ ottenere questa risorsa in prestito";
	private static final String NO_IN_SCADENZA = "Non ci sono prestiti in scadenza";
	private static final String TITOLO_IN_SCADENZA = "Ecco i prestiti in scadenza";
	private static final String BUON_FINE = "Operazione eseguita correttamente";
	private static final String TITOLO_MENU_STORICO = "scegli l'opzione desiderata";
	private static final String[] vociMenuStorico = {"visualizza storico completo","visualizza numero prestiti per anno solare","visualizza numero proroghe per anno solare","visualizza risorsa prestata piï¿½ volte per anno solare","visualizza i prestiti per fruitore per anno solare"};
	private static final String TITOLO_ELI_O_MOD = "seleziona l'azione desiderata";
	private static final String[] OPZIONI_ELI_O_MOD = {"visualizza", "modifica", "elimina"};
	
	
	private static Archivio archivio = new Archivio();//questo inizializza l'archivio con i dati di default che verranno poi sovrascritti dal caricamento da file
	
	private static ArrayList<Operatore> operatori;
	private static ArrayList<Fruitore> fruitori;
	private MyView view ;
	
		/**
		 * verifica che le invarianti di classe siano verificate
		 * @pre true
		 * @post @nochange
		 * @return true se sono verificate tutte le ivnarianti
		 */
	private static boolean invariante() {
		boolean invariante = false ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;

		if( operatori.size()>0 && fruitori!= null && operatori!= null && archivio!=null) invariante = true ;
	
		assert operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;
		return invariante ;
	}
           
	
	// metodi relativi ai fruitori-----------------------------------------------------------------------------------------------------
	
	
	/**
	 * verifica se il fruitore relativo a username e password ï¿½ iscritto
	 * @param username del fruitore
	 * @param password del fruitore
	 * @return true se il fruitore ï¿½ iscritto
	 * @pre username!=null && password!=null 
	 * @post @nochange
	 */
	public static boolean cercaFruitore(String username, String password) {
		assert invariante() && username!=null && password!=null  ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		int numFruitore;
		
		if((numFruitore = posizioneFruitore(username)) != -1 ) {									
			
			if(fruitori.get(numFruitore).getPassword().equals(password)) {
				
				assert invariante() && operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;
				return true;
			}
		}
		assert invariante() && operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;
		return false;
	}
	
	/**
	 * cerca fruitore tramite username e restituisce posizione nell'array dei fruitori        						
	 * @param username del fruitore
	 * @return posizione del fruitore
	 * @pre username != null 
	 * @post @nochange
	 */
	private static int posizioneFruitore(String username) {
		assert invariante() && username!= null ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		
		for (int i=0; i<fruitori.size(); i++) 
			if(fruitori.get(i).getUsername().equals(username)) {
				
				assert invariante() && operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;
				return i;
				}
		assert invariante() && operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;		
		return -1;
	}
	/**
	 * iscrive nuovo fruitore se maggiorenne chiedendo dati necessari all'utente
	 * @pre true 
	 * @post @return!= null &&(@nochange || fruitori.size()@pre +1==fruitori.size() )
	 * @return l'username del fruitore iscritto oppure una stringa vuota
	 */
	public static String iscrizioneFruitore() {
		assert invariante() ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		int fruitoriLun = fruitori.size() ;
		
		String nome, cognome, password, username;
		int eta;
		Calendar data_iscrizione, data_scadenza;
		boolean finito=false;
		
		nome=InputDati.leggiStringaNonVuota("Inserisci nome\n");
		cognome=InputDati.leggiStringaNonVuota("Inserisci cognome\n");
		eta=InputDati.leggiIntero("inserisci la tua etï¿½\n");
		
		if(eta<18) {
			System.out.println("Servizio riservato ai soli cittadini maggiorenni\n");
			assert invariante() && ((operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio)|| fruitori.size() == fruitoriLun);		
			return "";
		}	
		
		do {
			username=InputDati.leggiStringaNonVuota("Inserisci username\n");
			if (posizioneFruitore(username) != -1)
				System.out.println("username giï¿½ presente \n");
			else
				finito=true;
		}while(!finito);
		
		password=InputDati.leggiStringaNonVuota("Inserisci password\n");		
		
		data_iscrizione=Calendar.getInstance();
		data_scadenza=Calendar.getInstance();
		data_scadenza.add(Calendar.YEAR, 5);
		
		fruitori.add(new Fruitore(nome, cognome, eta, username, password, data_iscrizione, data_scadenza));
		archivio.storiaIscrizioneFruitore(username) ; //crea evento nell'archivio storico 
		
		assert invariante() && ((operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio)|| fruitori.size() == fruitoriLun) && username != null;		
		return username;
	}
	
	/**
	 * elimino fruitori decaduti e aggiorno il numero di licenze delle risorse liberate
	 * @pre true
	 * @post operatoriNoChange()
	 */
	public static void eliminaDecaduti() {
		assert invariante();
		ArrayList<Operatore> operatoriPre = operatori ;
		
		if(fruitori!=null) {
			for (int i=0; i<fruitori.size(); i++) 
				if(fruitori.get(i).getDataScadenza().before(Calendar.getInstance())) {
					
					//array contenente gli id delle risorse relative a prestiti da restituire
					ArrayList<Integer> prestitiDaRestituire = new ArrayList<Integer>();
					
					int catRisorsa;
					prestitiDaRestituire=fruitori.get(i).restituisciRisorseInPrestito();
					if(prestitiDaRestituire!=null)
						for (int j=0; j<prestitiDaRestituire.size(); j++) {
							catRisorsa=archivio.trovaIdCategoria(prestitiDaRestituire.get(i));
							if(catRisorsa==0 || catRisorsa==1)
								archivio.aggiornaLicenze(prestitiDaRestituire.get(j), 1); //1 ï¿½ il flag per incrementare numeroLicenze
						}

					archivio.storiaFruitoreDecaduto(fruitori.get(i).getUsername());

					fruitori.remove(i);
				}
			assert invariante() && operatoriPre == operatori ;
		}
	}
/**
 * elimina i prestiti scaduti per oni fruitore iscritto e aggiorna le licenze alle rispettive risorse liberate
 * @pre true
 * @post operatoriNoChange()	
 */
	public static void eliminaPrestitiScaduti() {
		assert invariante();
		ArrayList<Operatore> operatoriPre = operatori ;
		
		if(fruitori!=null) {
			for (int i=0; i<fruitori.size(); i++) {
				//array contenente gli id delle risorse relative a prestiti scaduti
				ArrayList<Integer> prestitiScaduti = new ArrayList<Integer>();
				
				prestitiScaduti=fruitori.get(i).eliminaPrestitiScaduti();
				int catRisorsa;
				
				if(prestitiScaduti!=null)
					for (int j=0; j<prestitiScaduti.size(); j++) {
						catRisorsa=archivio.trovaIdCategoria(prestitiScaduti.get(i));
						if(catRisorsa==0 || catRisorsa==1)
							archivio.aggiornaLicenze(prestitiScaduti.get(j), 1); //1 ï¿½ il mio flag per incrementare numeroLicenze
						if(archivio.numeroLicenzeRisorsa(prestitiScaduti.get(j))==1) archivio.risorsaDisponibile(prestitiScaduti.get(j));
					}
			}
			assert invariante() && operatoriPre == operatori ; 
		}
	}
	/**
	 * rinnova l'iscrizione del fruitore selezionato
	 * @param numFruitore posizione del fruitore nell'array
	 * @pre numFruitore>=0 && numFruitore<fruitori.size() 
	 * @post operatoriNoChange()
	 */
	private static void rinnovoIscrizioneFruitore(int numFruitore) {
		assert invariante() && numFruitore>=0 && numFruitore<fruitori.size() ;
		ArrayList<Operatore> operatoriPre = operatori ;
		
		Calendar data_scadenza=fruitori.get(numFruitore).getDataScadenza();
		Calendar data_odierna=Calendar.getInstance();
		
		//calcolo della differenza di giorni tra data scadenza e data odierna 
		long diff=(data_scadenza.getTimeInMillis()-data_odierna.getTimeInMillis())/ 86400000;
		
		if (diff>10) {
			System.out.println("mancano ancora "+ diff+" giorni alla scadenza, impossibile soddisfare richiesta\n");

		}else {
			fruitori.get(numFruitore).aggiornaDataScadenza();
			archivio.storiaRinnovoIscrizioneFruitore(fruitori.get(numFruitore).getUsername());
		}
		assert invariante() && operatoriPre == operatori ; 
	}
/**
 * ritorna la descrizione di tutti i fruitori iscritti	
 * @return stringa descrittiva 
 * @pre true
 * @post @nochange && @return!= null 
 */
	public static String elencoFruitori() {
		assert invariante() ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;

		if(fruitori==null || fruitori.size()==0) System.out.println("elenco fruitori vuoto");
		
		StringBuffer descrizione = new StringBuffer();
		for(Fruitore f: fruitori) 
			descrizione.append(f.visualizzaDatiFruitore()+"\n\n");
		String risultato = descrizione.toString();	
	
		assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio && risultato != null;
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
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;

		if(fruitori==null || fruitori.size()==0) System.out.println("elenco fruitori vuoto");
		
		StringBuffer descrizione = new StringBuffer();
		for(Fruitore f: fruitori) 
			descrizione.append(f.toString()+"\n\n");
		String risultato = descrizione.toString();	
		
		assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio && risultato != null;
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
		ArrayList<Operatore> operatoriPre = operatori ;
		
		MyMenu menu_fruitore = new MyMenu(TITOLO_MENU_FRUITORE, vociMenuFruitore);
		int scelta, numFruitore;
		
		numFruitore = posizioneFruitore(username);//numero fruitore nell'array
		
		
		//se non lo trovo esco
		if (numFruitore==-1) {
			assert invariante() && operatoriPre==operatori;
			return;
			}
		
		fruitori.get(numFruitore).setIdPrestito();
				
		do {
			scelta = menu_fruitore.scegli();
					
			switch (scelta) {
			
				case 1:
					Stampa.aVideo(BelleStringhe.incornicia(fruitori.get(numFruitore).visualizzaPrestitiFruitore()));
					break;
					
				case 2:
					int risorsaScelta = selezionaRisorsa();
					if (risorsaScelta==-1) {
						System.out.println("Nessuna corrispondenza");
						break;
					}
					
					boolean rispettaPrerequisiti = verificaPrerequisitiPrestito(fruitori.get(numFruitore), risorsaScelta);
				
					if (rispettaPrerequisiti) {
						archivio.aggiornaLicenze(risorsaScelta, 0); // flag per diminuire numeroLicenze
						String descrizioneRisorsa = archivio.getDescrizioneRisorsa(risorsaScelta);
						
						int durataPrestito = archivio.durataPrestitoDataUnaRisorsa(risorsaScelta);
						int durataProroga = archivio.durataProrogaDataUnaRisorsa(risorsaScelta);
						int termineProroga = archivio.termineProrogaDataUnaRisorsa(risorsaScelta);
						
						
						
						Calendar inizio = Calendar.getInstance();
						Calendar fine = Calendar.getInstance();
						fine.add(Calendar.DAY_OF_YEAR, durataPrestito);
						
						fruitori.get(numFruitore).richiediPrestito(risorsaScelta, descrizioneRisorsa, inizio, fine, durataProroga, termineProroga);
						Stampa.aVideo(BelleStringhe.rigaIsolata(BUON_FINE));
						archivio.storiaNuovoPrestito(risorsaScelta,archivio.numeroLicenzeRisorsa(risorsaScelta),fruitori.get(numFruitore).getUsername());
					} else {
						Stampa.aVideo(MESSAGGIO_PRESTITO_NON_CONCESSO);
					}
					break;
					
				case 3:
					
					ArrayList<Integer> inScadenza = fruitori.get(numFruitore).inScadenza();
					
					if (inScadenza != null) {
						String[] inScadenzaString = new String[inScadenza.size()];
						
						for (int i = 0; i < inScadenzaString.length; i++) {
							inScadenzaString[i] = fruitori.get(numFruitore).getDescrizionePrestito(inScadenza.get(i));
						}
						
						MyMenu menuInScadenza = new MyMenu(TITOLO_IN_SCADENZA, inScadenzaString);
						int scegliDaRinnovare = menuInScadenza.scegli();
						
						switch (scegliDaRinnovare) {
						case 0:
							break;

						default:
							fruitori.get(numFruitore).rinnova(inScadenza.get(scegliDaRinnovare-1));
							archivio.prorogaPrestito(fruitori.get(numFruitore).getUsername(),inScadenza.get(scegliDaRinnovare-1));
							break;
						}
					} else {
						Stampa.aVideo(NO_IN_SCADENZA);
					}
					break;
					
				case 4:
					rinnovoIscrizioneFruitore(numFruitore);
					break;
			}
		}while(scelta!=0);
		assert invariante() && operatoriPre==operatori;
		return;
	}
/**
 * 	verifica se ï¿½ possibile dare la risorsa scelta in prestito al fruitore scelto
 * @param fruitore scelto
 * @param risorsaScelta id della risorsa
 * @return true se il prestito desiderato soddisfa tutti i requisiti
 * @pre id>=0 fruitore!=null 
 * @post @nochange
 */
	private static boolean verificaPrerequisitiPrestito(Fruitore fruitore, int risorsaScelta) {
		assert invariante() && risorsaScelta>=0 && fruitore!= null;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;

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
			
			assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
			return true;	
			}
		assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
		return false;
	}

	
			
	//inizio metodi relativi  operatori------------------------------------------------------------------------------------------------------
			
	
	
	/**
	 *  metodo principale per la gestione delle operazioni degli operatori
	 * @param username dell'operatore loggato
	 * @pre true
	 * @post fruitoriNoChange
	 */
	public  void usaOperatore(String username){
		assert invariante() ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		
		MyMenu menu_operatore= new MyMenu(TITOLO_MENU_OPERATORE, VOCI_MENU_OPERATORE);
		int numOperatore, scelta;
		
		numOperatore=posizioneOperatore(username);        
		
		if (numOperatore==-1) {                          
		assert invariante() && fruitoriPre == fruitori ;
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
					if(archivio.vuoto()) {System.out.println("archivio vuoto");
					break;
			}
					MyMenu menu_ricerca = new MyMenu("Scegli il metodo di ricerca delle risorse :", OPZIONI_MENU_RICERCA);
						
							boolean ricercaValida = false;
							int ricercaScelta = 0;
							
							while(!ricercaValida) {
							
								 ricercaScelta = menu_ricerca.scegli();
								if(ricercaScelta>=0 && ricercaScelta<=OPZIONI_MENU_RICERCA.length) ricercaValida=true;
									}
							switch(ricercaScelta) {
							
							default : break;
							
							case 1:
							
								MyMenu menu_archivio= new MyMenu("Categorie",archivio.elencoCategorie());
						
								boolean sceltaValida = false;
							
								while(!sceltaValida) {
								
									int categoriaScelta=menu_archivio.scegli();
									
									if(categoriaScelta>0 && categoriaScelta<=archivio.size()) {
									
										usaCategoria(categoriaScelta-1);
									
										sceltaValida=true;
										}
									if(categoriaScelta==0)break;
									}
									break;
							
							case 2:
								
								MyMenu menu_attributi= new MyMenu(TITOLO_SELEZIONA_ATTRIBUTO, vociMenuSelezionaAttributo);
							
								int attributoScelto = menu_attributi.scegli();
																	
								if(attributoScelto!=0)
									cercaPerAttributoOmode(attributoScelto);
								
								break;
								
							}
							break;
				case 4: 	
					
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
					
					
				}while(scelta!=0);
		
			assert invariante() && fruitoriPre == fruitori ;
			return;
			}
	
	private void usaCategoria(int scelta) {
	
		
		
		if (archivio.haRisorseEsottocategorie(scelta))
			{
			view.notify(("errore! questa categoria ha sia risorse che sottocategorie!")); 
			return ;
			}

		if(archivio.categoriaHaRisorse(scelta)) archivio.gestioneRisorse(scelta);
		
		else {
			
			if( !archivio.categoriaHaSottoCategoria(scelta) ) { 
				view.notify("non ci sono sottocategorie nè risorse in questa categoria"); 
				archivio.gestioneRisorse(scelta); 
			}
			else {
				
		
				boolean sceltaValida = false ;
				
				while (!sceltaValida) {
					int sottoCategoriaScelta=view.scelta("Sottocategorie", archivio.elencoSottoCategorie(scelta));
;
					
					if(sottoCategoriaScelta>0 ) {
						archivio.usaSottoCategoria(scelta,sottoCategoriaScelta-1);
											
					}

					 sceltaValida=true;
				}
			}
		}		
	}


	/**
	 * metodo per la ricerca di risorse tramite attributo , richiesta all'utente su ricerca da eseguire e su azione da compiere sulla risorsa	
	 * @param attributoScelto attributo da confrontare col parametro di ricerca
	 * @pre attributoScelto>=0
	 * @post true
	 */
		
private  void cercaPerAttributoOmode(int attributoScelto) {

	
	String chiaveDiRicerca = null;
	int numDiRicerca = 0;
	if(attributoScelto<6)  chiaveDiRicerca = view.StringaNonVuota("inserisci la stringa da cercare nell'attributo selezionato");
	else numDiRicerca = view.InteroNonNegativo("inserisci il valore da cercare per l'attributo selezionato");

    ArrayList<Integer> match = archivio.filtraRisorse(attributoScelto,chiaveDiRicerca,numDiRicerca);
	
	if(match.size()<1) return;
	
	String[] opzioniEsiti= new String[match.size()];
	int i=0;
	for(int r : match) { 
		if(archivio.getNomeRisorsa(r)!=null) {	opzioniEsiti[i]= archivio.getNomeRisorsa(r); i++; } 
		}
	
	int risorsaScelta = view.scelta("ecco l'esito della ricerca :", opzioniEsiti);
	
	if(risorsaScelta!=0) {
		int eliMod;
		
		do{	
			 eliMod = view.scelta(TITOLO_ELI_O_MOD, OPZIONI_ELI_O_MOD);
	
			 if(eliMod==0) return;
			 
			archivio.azioneDaRicerca(match.get(risorsaScelta-1),eliMod);
			 
		}while (eliMod==1);
	
	}
	}


/**
 * verifica se l'username e la password in ingresso corrispondono ad un operatore registrato
 * @param username dell'operatore
 * @param password dell'operatore
 * @return true se esiste un operatore corrispondente
 * @pre username!= null && password!= null && password != "" && username != ""
 * @post @nochange
 */
	public static boolean cercaOperatore(String username, String password) {
		assert invariante() && username!= null && password!= null && password != "" && username != "" ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
				
		int numOperatore;
		
		if((numOperatore=posizioneOperatore(username)) != -1) { 												
			
			if(operatori.get(numOperatore).getPassword().equals(password))
			assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
			return true;
		}
		assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
		return false;
	}
			
	/**
	 *  cerca l'operatore scelto e restituisco la sua posizione nell'array
	 * @param username dell'operatore scelto
	 * @return il valore della sua posizione nell'array
	 * @pre username!= null && username!= ""
	 * @post @nochange
	 */
	private static int posizioneOperatore(String username) {	
		assert invariante() && username!= null && username!= "" ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		
		for (int i=0; i<operatori.size(); i++) 
			if(operatori.get(i).getUsername().equals(username)) {
				
				assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
				return i;
			}
		assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
		return -1;
	}
/**
 * ritorna la descrizione di tutti gli operatori esistenti
 * @pre true
 * @post @return != null && @nochange			
 * @return la stringa descrittiva
 */
	public String elencoOperatori() {
		assert invariante() ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		
				StringBuffer descrizione = new StringBuffer();
				for(Operatore o: operatori) 
					descrizione.append(o.toString()+"\n\n");
				
				String risultato = descrizione.toString();
				assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio && risultato!= null;
				return 	risultato ;	
			}
			

			
/**
 * salvare fruitori e operatori a fine sessione su file 
 * @pre true
 * @post @nochange
 */
	public static void salvaFruitoriOperatori() {
			assert	invariante() ;	
			ArrayList<Operatore> operatoriPre = operatori ;
			ArrayList<Fruitore> fruitoriPre = fruitori ;
			Archivio archivioPre = archivio ;
			
			
				File g = new File(NOMEFILEOPERATORI);
				ServizioFile.salvaSingoloOggetto(g, operatori);
					
				File f = new File(NOMEFILEFRUITORI);
				ServizioFile.salvaSingoloOggetto(f, fruitori);
			
				
				assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
			}
			
	
			
/**
 *  carica da file i fruitori e operatori
 *  @pre true
 *  @post true
 */
	public static void importaFruitoriOperatori() {
				
				File f = new File(NOMEFILEFRUITORI);
				@SuppressWarnings("unchecked")
				 ArrayList<Fruitore> a = ( ArrayList<Fruitore>)ServizioFile.caricaSingoloOggetto(f);
				if(a==null) {fruitori = new ArrayList<Fruitore>();}
				else {fruitori=a; }
				
				File g = new File(NOMEFILEOPERATORI);
				@SuppressWarnings("unchecked")
				 ArrayList<Operatore> b = ( ArrayList<Operatore>)ServizioFile.caricaSingoloOggetto(g);
			
				if(b==null) {operatori  = new ArrayList<Operatore>();}
				
				else {operatori=b; }
				
				if(operatori.size()==0) {operatori.add(new Operatore("admin", "admin", 18, "admin", "admin")) ; }     //inizializzazione di default 
				
				
				
				assert invariante() ;				  
			 }
/**
 * carica i dati dell'archivio  e dello storico da file
 * @pre true
 * @post fruritoriOperatoriNoChange()
 */
	public  void importaArchivio() {
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		
		archivio.importaDati();
		
		view = new BibliotecaView();

		assert invariante() && fruitoriPre == fruitori && operatoriPre == operatori;
	}
/** 
 * salva dati dell'archivio e dello storico su file
 * @pre true 
 * @post @nochange
 */
	public static void salvaArchivio() {//e storico
		assert invariante();
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		
		archivio.salvaDati();	
		
		assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
	}
	
	/**
	 * Permette di selezionare una risorsa nell'archivio.
	 * @return risorsa selezionata.
	 * @pre true 
	 * @post @return>=-1 && @nochange
	 */
	public static int selezionaRisorsa() {
		assert invariante() ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		
		int risorsaScelta = -1;
		
		MyMenu menuPrestito = new MyMenu(TITOLO_MENU_PRESTITO, opzioniDiricerca);
		int scelta;
		
				scelta = menuPrestito.scegli();
			
			switch (scelta) {
				case 1:
					MyMenu menu_attributi= new MyMenu(TITOLO_SELEZIONA_ATTRIBUTO, vociMenuSelezionaAttributo);
					int attributoScelto = menu_attributi.scegli();
					if(attributoScelto!=0 ) risorsaScelta = archivio.cercaPerAttributoFmode(attributoScelto);//attributoScelto dovra essere compreso tra 0 e #attributi
				break;
			
				case 2:
					risorsaScelta = archivio.selezionaCategoria();
					break;

			default:
				BelleStringhe.incornicia(MESSAGGIO_ERRORE);
				break;
			}
			
			assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio && risorsaScelta>=-1;		
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
}
