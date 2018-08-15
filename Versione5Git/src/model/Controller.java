package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.ServizioFile;
import it.unibs.ing.mylib.Stampa;
import view.BibliotecaView;

@SuppressWarnings("serial")

public class Controller  implements Serializable{
	/**
	 * @invariant invariante() 
	 */
	private static final String NOMEFILEFRUITORI = "Fruitori.dat";
	private static final String NOMEFILEOPERATORI = "Operatori.dat";
	
	private static final String TITOLO_MENU_GESTIONERISORSA= "Opzioni disponibili";
	private static final String[] OPZIONI = {"visualizza risorse","aggiungi risorsa","elimina risorsa","modifica risorsa"} ;
	
	private static final String TITOLO_MENU_FRUITORE = "Menu Fruitore";
	private static final String[] vociMenuFruitore = {"Visualizza Prestiti", "Richiedi prestito", "Rinnova prestito", "Rinnovo iscrizione"};
	
	private static final String TITOLO_SELEZIONA_ATTRIBUTO = "Scegli attributo con cui filtrare la ricerca";
	private static final String[] vociMenuSelezionaAttributo = {"Nome","Autore/Regista","Casa editrice/Casa Di produzione","Genere","Lingua","Anno di pubblicazione","Numero di pagine", "Durata"};
	
	private static final String TITOLO_MENU_PRESTITO = "Scegli come desideri cercare la risorsa da aggiungere.";
	private static final String[] opzioniDiricerca = {"Ricerca per attributo", "Ricerca navigando l'archivio"};
	private static final String TITOLO_CATEGORIA = "Scegli la categoria";
	private static final String TITOLO_SOTTOCATEGORIA = "Scegli la sottocategoria";
	private static final String TITOLO_RISORSE = "Scegli la risorsa da selezionare";

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
	private static final String TITOLO_ELI_O_MOD = "seleziona l'azione desiderata";
	private static final String[] OPZIONI_ELI_O_MOD = {"visualizza", "modifica", "elimina"};
	private static final String INPUT_USERNAME = "Inserire username -> ";
	private static final String INPUT_PASSWORD = "Inserire password -> ";
	private static final String ERRORE = "Username o password sabliata!!!";
	private static final String RIPROVA = "Voi riprovare il login?";
	private static final String TITOLO = "Scegli tra le seguenti opzioni";
	private static final String[] voci = {"Login","Registrati"};
	private static final String ARRIVEDERCI = "Arrivederci";
	
	

	private Model model;
	private MyView view ;
	
	
	public Controller(Model model , MyView view) {
		this.model = model ;
		this.view = view ;
	}
		/**
		 * verifica che le invarianti di classe siano verificate
		 * @pre true
		 * @post @nochange
		 * @return true se sono verificate tutte le ivnarianti
		 */
	private  boolean invariante() {
	return true;
	}
           
	
	// metodi relativi ai fruitori-----------------------------------------------------------------------------------------------------
	
	
	/**
	 * verifica se il fruitore relativo a username e password � iscritto
	 * @param username del fruitore
	 * @param password del fruitore
	 * @return true se il fruitore � iscritto
	 * @pre username!=null && password!=null 
	 * @post @nochange
	 */
	public  boolean cercaFruitore(String username, String password) {
		return model.cercaFruitore(username,password);
	}
	
	
	
	public  String iscrizioneFruitore() {
		assert invariante() ;
	
		
		String nome, cognome, password, username;
		int eta;
		Calendar data_iscrizione, data_scadenza;
		boolean finito=false;
		
		nome=view.StringaNonVuota("Inserisci nome\n");
		cognome=view.StringaNonVuota("Inserisci cognome\n");
		eta=view.InteroNonNegativo("inserisci la tua et�\n");
		
		if(eta<18) {
			view.notify("Servizio riservato ai soli cittadini maggiorenni\n");
			assert invariante() ;		
			return "";
		}	
		
		do {
			username=view.StringaNonVuota("Inserisci username\n");
			if (model.posizioneFruitore(username) != -1)
				view.notify("username gi� presente \n");
			else
				finito=true;
		}while(!finito);
		
		password=view.StringaNonVuota("Inserisci password\n");		
		
		data_iscrizione=Calendar.getInstance();
		data_scadenza=Calendar.getInstance();
		data_scadenza.add(Calendar.YEAR, 5);
		
		model.addFruitore(nome, cognome, eta, username, password, data_iscrizione, data_scadenza);
		model.storiaIscrizioneFruitore(username) ; //crea evento nell'archivio storico 
		
		assert invariante() ;		
		return username;	}
	
	
	public  void eliminaDecaduti() {
		model.eliminaDecaduti();
	}

	public  void eliminaPrestitiScaduti() {
		model.eliminaPrestitiScaduti();
	}
	
	/**
	 * rinnova l'iscrizione del fruitore selezionato
	 * @param numFruitore posizione del fruitore nell'array
	 * @pre numFruitore>=0 && numFruitore<fruitori.size() 
	 * @post operatoriNoChange()
	 */
	private  void rinnovoIscrizioneFruitore(int numFruitore) {
		assert invariante() && numFruitore>=0  ;
		
		Calendar data_scadenza=model.getDataScadenzaFruitore(numFruitore);
		Calendar data_odierna=Calendar.getInstance();
		
		//calcolo della differenza di giorni tra data scadenza e data odierna 
		long diff=(data_scadenza.getTimeInMillis()-data_odierna.getTimeInMillis())/ 86400000;
		
		if (diff>10) {
			view.notify("mancano ancora "+ diff+" giorni alla scadenza, impossibile soddisfare richiesta\n");

		}else {
			model.aggiornaDataScadenzaFruitore(numFruitore);
			model.storiaRinnovoIscrizioneFruitore(numFruitore);
		}
		assert invariante()  ; 
	}

	public  String elencoFruitori() {
		
		if(model.hasFruitori()) view.notify("elenco fruitori vuoto");

		return model.elencoFruitori();
	}
	

	public  String elencoFruitoriFull() {
		
		if(model.hasFruitori()) view.notify("elenco fruitori vuoto");
		
		return model.elencoFruitoriFull();
	}
	
	/**
	 * Gestisce le operazioni del fruitore.
	 * @param username del fruitore.
	 * @pre username!=null
	 * @post operatoriNoChange()
	 */
	public  void usaFruitore(String username) {
		assert invariante() && username!= null;
		
		int scelta, numFruitore;
		
		numFruitore = model.posizioneFruitore(username);//numero fruitore nell'array
		
		
		//se non lo trovo esco
		if (numFruitore==-1) {
			assert invariante() ;
			return;
			}
		
		model.setIdPrestitoFruitore(numFruitore);
				
		do {
			scelta = view.scelta(TITOLO_MENU_FRUITORE, vociMenuFruitore);
					
			switch (scelta) {
			
				case 1:
					view.notify(view.incornicia(model.visualizzaPrestitiFruitore(numFruitore)));
					break;
					
				case 2:
					int risorsaScelta = selezionaRisorsa();
					if (risorsaScelta==-1) {
						view.notify("Nessuna corrispondenza");
						break;
					}
					
					boolean rispettaPrerequisiti = model.verificaPrerequisitiPrestito(numFruitore, risorsaScelta);
				
					if (rispettaPrerequisiti) {
						model.aggiornaLicenze(risorsaScelta, 0);// flag per diminuire numeroLicenze
						String descrizioneRisorsa = model.getDescrizioneRisorsa(risorsaScelta);
						
						int durataPrestito = model.durataPrestitoDataUnaRisorsa(risorsaScelta);
						int durataProroga = model.durataProrogaDataUnaRisorsa(risorsaScelta);
						int termineProroga = model.termineProrogaDataUnaRisorsa(risorsaScelta);
						
						
						
						Calendar inizio = Calendar.getInstance();
						Calendar fine = Calendar.getInstance();
						fine.add(Calendar.DAY_OF_YEAR, durataPrestito);
						
						model.richiediPrestitoFruitore(numFruitore,risorsaScelta, descrizioneRisorsa, inizio, fine, durataProroga, termineProroga);
						view.notify(BelleStringhe.rigaIsolata(BUON_FINE));
						model.storiaNuovoPrestito(risorsaScelta,numFruitore);
					} else {
						Stampa.aVideo(MESSAGGIO_PRESTITO_NON_CONCESSO);
					}
					break;
					
				case 3:
					
					ArrayList<Integer> inScadenza = model.getInScadenzafruitore(numFruitore);
					
					if (inScadenza != null) {
						String[] inScadenzaString = new String[inScadenza.size()];
						
						for (int i = 0; i < inScadenzaString.length; i++) {
							inScadenzaString[i] = model.getDescrizionePrestitoFruitore(numFruitore,inScadenza,i);
						}
						
						int scegliDaRinnovare = view.scelta(TITOLO_IN_SCADENZA, inScadenzaString);
						
						switch (scegliDaRinnovare) {
						case 0:
							break;

						default:
							model.rinnovaPrestitoFruitore(numFruitore,inScadenza,scegliDaRinnovare);
							model.prorogaPrestitoFruitore(numFruitore,inScadenza,scegliDaRinnovare);
							break;
						}
					} else {
						view.notify(NO_IN_SCADENZA);
					}
					break;
					
				case 4:
					rinnovoIscrizioneFruitore(numFruitore);
					break;
			}
		}while(scelta!=0);
		assert invariante() ;
		return;
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
		
		int numOperatore, scelta;
		
		numOperatore=posizioneOperatore(username);        
		
		if (numOperatore==-1) {                          
		assert invariante() ;
			return;
		}
				
		do {
			scelta = view.scelta(TITOLO_MENU_OPERATORE, VOCI_MENU_OPERATORE);
			
			switch (scelta) {
				case 1:
					view.notify(elencoFruitori());
					break;
					
				case 2:
					view.notify(elencoFruitoriFull());
					break;	
					
				case 3:
					if(model.ArchivioVuoto()) {view.notify("archivio vuoto");
					break;
			}
						
							boolean ricercaValida = false;
							int ricercaScelta = 0;
							
							while(!ricercaValida) {
							
								 ricercaScelta = view.scelta("Scegli il metodo di ricerca delle risorse :", OPZIONI_MENU_RICERCA);
								if(ricercaScelta>=0 && ricercaScelta<=OPZIONI_MENU_RICERCA.length) ricercaValida=true;
									}
							switch(ricercaScelta) {
							
							default : break;
							
							case 1:
							
						
								boolean sceltaValida = false;
							
								while(!sceltaValida) {
								
									int categoriaScelta=view.scelta("Categorie",model.elencoCategorie());
									
									if(categoriaScelta>0 && categoriaScelta<=model.sizeArchivio()) {
									
										usaCategoria(categoriaScelta-1);
									
										sceltaValida=true;
										}
									if(categoriaScelta==0)break;
									}
									break;
							
							case 2:
								
							
								int attributoScelto = view.scelta(TITOLO_SELEZIONA_ATTRIBUTO, vociMenuSelezionaAttributo);
																	
								if(attributoScelto!=0)
									cercaPerAttributoOmode(attributoScelto);
								
								break;
								
							}
							break;
				case 4: 	
					
					int sceltaMenuStorico = view.scelta(TITOLO_MENU_STORICO, vociMenuStorico);
					
					switch(sceltaMenuStorico) {
					
					case 1: 
						view.notify(model.getDescrizioneStorico());
						break;
					case 2:
						view.notify(model.numEventoAnnoSolare(model.getEventoNuovoPrestito(),"prestiti"));
						break;
					case 3:
						view.notify(model.numEventoAnnoSolare(model.getEventoProrogaPrestito(),"proroghe"));
						break;
					case 4:
						view.notify(model.risorsaPiuPrestata());
						break;
					case 5:
						view.notify(model.prestitiFruitoriAnnoSolare());
						break;
					default : break;					

					}
					
					}
					
					
				}while(scelta!=0);
		
			assert invariante() ;
			return;
			}
	
	private void usaCategoria(int categoria) {
	
		
		
		if (model.haRisorseEsottocategorie(categoria))
			{
			view.notify(("errore! questa categoria ha sia risorse che sottocategorie!")); 
			return ;
			}

		if(model.categoriaHaRisorse(categoria)) gestioneRisorseCategoria(categoria);
		
		else {
			
			if( !model.categoriaHaSottoCategoria(categoria) ) { 
				view.notify("non ci sono sottocategorie n� risorse in questa categoria"); 
				gestioneRisorseCategoria(categoria); 
			}
			else {
				
		
				boolean sceltaValida = false ;
				
				while (!sceltaValida) {
					int sottoCategoriaScelta=view.scelta("Sottocategorie", model.elencoSottoCategorie(categoria));
;
					
					if(sottoCategoriaScelta>0 ) {
						gestioneRisorseSottocategoria(categoria,sottoCategoriaScelta-1);
											
					}

					 sceltaValida=true;
				}
			}
		}		
	}

	private void gestioneRisorseSottocategoria(int categoria, int sottocategoria) {

		


		
		boolean esciMenu = false;
	
		while(!esciMenu) {
	
			int scelta =view.scelta(TITOLO_MENU_GESTIONERISORSA, OPZIONI);

		
			if (scelta == 0 ) esciMenu=true;		
			
			String[] elenco = model.elencoRisorse(categoria,sottocategoria);
			if(elenco==null && scelta!=2) {
				view.notify("Nessuna risorsa presente"); 
				
				return; 			
			}
			
			switch(scelta) {
			
				case 1:
					
					view.notify("Risorse contenute in " + model.elencoSottoCategorie(categoria)[sottocategoria] + " :" );				
					
					for (int i=0; i<elenco.length; i++) {
						
						view.notify( (i+1) + ") " +elenco[i]);
					}
					
					view.notify("\n");		
					break;
					
				case 2:
					
					aggiungiRisorsaCategoria(categoria,sottocategoria);
					break;
				
				case 3:
					
				    int risorsaDaEliminare = view.scelta("Scegli la risorsa da eliminare", elenco);

					
					if (risorsaDaEliminare==0) break;
					
					//risorsaDaEliminare -1 rappresenta l'attuale posizione nell'array della risorsa
					model.rimuoviRisorsa(model.getId(risorsaDaEliminare-1,categoria,sottocategoria),categoria,sottocategoria);		
					break;
					
				case 4:
					
				
					int risorsaDaModificare = view.scelta("Scegli la risorsa da modificare", elenco);

						
					if (risorsaDaModificare==0) break;
					
					//risorsaDaModificare -1 rappresenta l'attuale posizione nell'array della risorsa
					modifica(model.getId(risorsaDaModificare-1,categoria,sottocategoria),categoria);
					
					break;
					
				default:
					break;				
			}
		}
	}


	/**
	 * chiede all'utente e attua le opzioni disponibili per le risorse
	 * @pre true 
	 * @post true
	 */

	private void gestioneRisorseCategoria(int categoria) {


		
		boolean esciMenu = false;
	
		while(!esciMenu) {
	
			int scelta =view.scelta(TITOLO_MENU_GESTIONERISORSA, OPZIONI);

		
			if (scelta == 0 ) esciMenu=true;		
			
			String[] elenco = model.elencoRisorse(categoria, -1);
			if(elenco==null && scelta!=2) {
				view.notify("Nessuna risorsa presente"); 
				
				return; 			
			}
			
			switch(scelta) {
			
				case 1:
					
					view.notify("Risorse contenute in " + model.elencoCategorie()[categoria] + " :" );				
					
					for (int i=0; i<elenco.length; i++) {
						
						view.notify( (i+1) + ") " +elenco[i]);
					}
					
					view.notify("\n");		
					break;
					
				case 2:
					
					aggiungiRisorsaCategoria(categoria,-1);
					break;
				
				case 3:
					
				    int risorsaDaEliminare = view.scelta("Scegli la risorsa da eliminare", elenco);

					
					if (risorsaDaEliminare==0) break;
					
					//risorsaDaEliminare -1 rappresenta l'attuale posizione nell'array della risorsa
					model.rimuoviRisorsa(model.getId(risorsaDaEliminare-1,categoria,-1),categoria,-1);		
					break;
					
				case 4:
					
				
					int risorsaDaModificare = view.scelta("Scegli la risorsa da modificare", elenco);

						
					if (risorsaDaModificare==0) break;
					
					//risorsaDaModificare -1 rappresenta l'attuale posizione nell'array della risorsa
					modifica(model.getId(risorsaDaModificare-1,categoria,-1),categoria);
					
					break;
					
				default:
					break;				
			}
		}
	}


	private void aggiungiRisorsaCategoria(int categoria,int sottocategoria) {

		String[] attributiStringa = acquisisciAttributiStringaCategoria(categoria);
		int[] attributinumerici = acquisisciAttributiNumericiCategoria(categoria);
		
		
		
			model.aggiungiRisorsa(attributiStringa,attributinumerici,categoria,sottocategoria);		
		
		
	}
	private int[] acquisisciAttributiNumericiCategoria(int categoria) {


				String[] attributiNumerici = model.getAttributiNumericiRisorse( categoria);

				int[] attributiNumericiFinali = new int[attributiNumerici.length+1];

				for(int i = 0 ; i<attributiNumerici.length; i++) {
					attributiNumericiFinali[i] = view.InteroNonNegativo("inserire "+attributiNumerici[i]+" :");
					
				}
				attributiNumericiFinali[attributiNumerici.length] = view.InteroNonNegativo("inserisci il numero di licenze");
				
				return attributiNumericiFinali;

		
	}
	
	private String[] acquisisciAttributiStringaCategoria(int categoria) {

	
			
							

				String[] attributiStringa = model.getAttributiStringaRisorse( categoria);

				String[] attributiStringaFinali= new String[attributiStringa.length];

				for(int i = 0 ; i<attributiStringa.length; i++) {
					attributiStringaFinali[i] = view.StringaNonVuota("inserire "+attributiStringa[i]+" :");
					
			

		}
				return attributiStringaFinali;

	
		
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

    ArrayList<Integer> match = model.filtraRisorse(attributoScelto,chiaveDiRicerca,numDiRicerca);
	
	if(match.size()<1) return;
	
	String[] opzioniEsiti= new String[match.size()];
	int i=0;
	for(int r : match) { 
		if(model.getNomeRisorsa(r)!=null) {	opzioniEsiti[i]= model.getNomeRisorsa(r); i++; } 
		}
	
	int risorsaScelta = view.scelta("ecco l'esito della ricerca :", opzioniEsiti);
	
	if(risorsaScelta!=0) {
		int eliMod;
		
		do{	
			 eliMod = view.scelta(TITOLO_ELI_O_MOD, OPZIONI_ELI_O_MOD);
	
			 if(eliMod==0) return;
			 
		int id = match.get(risorsaScelta-1) ;
		
		azioneDaRicerca(id,eliMod);
		
			 
		}while (eliMod==1);
	
	}
	}

private void modifica(int id , int categoria) {
	

	String[]  attributiStringa = model.getAttributiStringaRisorse(categoria);
	String[]  attributiNumerici = model.getAttributiNumericiRisorse(categoria);
	
	String[] nuoviAttributiStringa = new String[attributiStringa.length];
	int[] nuoviAttributiNumerici = new int[+attributiNumerici.length];
	
	int i=0;
	
	for(String s : attributiStringa) {
		
		char modificaNome = view.Char("vuoi modificare "+s+" ? (s/n)");
		
		if(modificaNome=='s'||modificaNome=='S') {
		
			String nomeNuovo= view.StringaNonVuota("inserisci il nuovo valore");
		
			nuoviAttributiStringa[i]=nomeNuovo;
		}	
		
		else nuoviAttributiStringa[i]= null;

		i++;
	}
	
	i=0;
	
for(String s : attributiNumerici) {
		
		char modificaNome = view.Char("vuoi modificare "+s+" ? (s/n)");
		
		if(modificaNome=='s'||modificaNome=='S') {
		
			int valoreNuovo= view.InteroNonNegativo("inserisci il nuovo valore");
		
			nuoviAttributiNumerici[i]=valoreNuovo;
		}	
		else 			nuoviAttributiNumerici[i]= -1;

		i++;
	}

	model.modificaRisorsa(id,categoria,nuoviAttributiStringa,nuoviAttributiNumerici);
	
}



private void azioneDaRicerca(int id, int eliMod) {


	for(int c = 0; c<model.sizeArchivio();c++)  {

		if(model.categoriaHaRisorse(c)) 
		{
			switch (eliMod) {
			case 1:
				view.notify(model.showRisorsa(id,c,-1));
				break;
			case 2:
				modifica(id,c);
				break;
			case 3:
				model.rimuoviRisorsa(id,c,-1);
				break;
			default:
				break;
		}	
		}
		else 
		{
			for(int s= 0; s< model.elencoSottoCategorie(c).length; s++) {
				
			switch (eliMod) {
			case 1:
				view.notify(model.showRisorsa(id,c,s));
				break;
			case 2:
				modifica(id,c);
				break;
			case 3:
				model.rimuoviRisorsa(id,c,s);
				break;
			default:
				break;
		}	 

			}
	}
	assert invariante() ;
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
	public  boolean cercaOperatore(String username, String password) {
		assert invariante() && username!= null && password!= null && password != "" && username != "" ;
	
				
		int numOperatore;
		
		if((numOperatore=posizioneOperatore(username)) != -1) { 												
			
			if(model.verificaPasswordOperatore(numOperatore,password))
			assert invariante()  ;
			return true;
		}
		assert invariante()  ;
		return false;
	}
			
	/**
	 *  cerca l'operatore scelto e restituisco la sua posizione nell'array
	 * @param username dell'operatore scelto
	 * @return il valore della sua posizione nell'array
	 * @pre username!= null && username!= ""
	 * @post @nochange
	 */
	private  int posizioneOperatore(String username) {	
		assert invariante() && username!= null && username!= "" ;
		
		
		for (int i=0; i<model.sizeOperatori(); i++) 
			if(model.verificaUsernameOperatore(i,username)) {
				
				assert invariante()  ;
				return i;
			}
		assert invariante()  ;
		return -1;
	}

	public String elencoOperatori() {
		return model.elencoOperatori();
	}

			
	public  void salvaFruitoriOperatori() {
		model.salvaFruitoriOperatori();
	}
			
	
			
	public  void importaFruitoriOperatori() {
				model.importaFruitoriOperatori();
					  
			 }
/**
 * carica i dati dell'archivio  e dello storico da file
 * @pre true
 * @post fruritoriOperatoriNoChange()
 */
	public  void importaArchivio() {
		
		
		model.importaDati();
		
		view = new BibliotecaView();

		assert invariante() ;
	}
/** 
 * salva dati dell'archivio e dello storico su file
 * @pre true 
 * @post @nochange
 */
	public  void salvaArchivio() {//e storico
		assert invariante();
		model.salvaArchivio();	
		
		assert invariante();
	}
	
	/**
	 * Permette di selezionare una risorsa nell'archivio.
	 * @return risorsa selezionata.
	 * @pre true 
	 * @post @return>=-1 && @nochange
	 */
	public  int selezionaRisorsa() {
		assert invariante() ;
		
		int risorsaScelta = -1;
		
		MyMenu menuPrestito = new MyMenu(TITOLO_MENU_PRESTITO, opzioniDiricerca);
		int scelta;
		
				scelta = menuPrestito.scegli();
			
			switch (scelta) {
				case 1:
					int attributoScelto = view.scelta(TITOLO_SELEZIONA_ATTRIBUTO, vociMenuSelezionaAttributo);
				
					if(attributoScelto!=0 ) {
						String chiaveDiRicerca = "";
						int numDiRicerca = 0;
						if(attributoScelto<6)  chiaveDiRicerca = view.StringaNonVuota("inserisci la stringa da cercare nell'attributo selezionato");
						else numDiRicerca = view.InteroNonNegativo("inserisci il valore da cercare per l'attributo selezionato");
						
						
						ArrayList<Integer> match = model.filtraRisorse(attributoScelto,chiaveDiRicerca,numDiRicerca);
						if(match.size()<1) 	{risorsaScelta =  -1; break;}
						

						String[] opzioniEsiti= new String[match.size()];
						int i=0;
						for(int r : match) { opzioniEsiti[i]= model.getNomeRisorsa(r); i++;}
												
						  risorsaScelta = view.scelta("ecco l'esito della ricerca :", opzioniEsiti);

						if(risorsaScelta==0) 
							return -1;
							
						else 
							return match.get(risorsaScelta-1);
					
					
					}
				break;
			
				case 2:
					
					int categoriaScelta =view.scelta(TITOLO_CATEGORIA, model.elencoCategorie())-1;

					if(categoriaScelta==-1) {risorsaScelta=-1;  break;}
					
					if (model.categoriaHaSottoCategoria(categoriaScelta)) {
						
						int sottoCategoriaScelta =view.scelta(TITOLO_SOTTOCATEGORIA, model.elencoSottoCategorie(categoriaScelta))-1;
						if(sottoCategoriaScelta==-1) {risorsaScelta = -1; break;}
						
					int	risorsaSelezionata = view.scelta(TITOLO_RISORSE,model.elencoRisorse(categoriaScelta,sottoCategoriaScelta))-1;

					if(risorsaSelezionata==-1) { risorsaScelta=-1 ; break;}
						
					risorsaScelta =model.scegliRisorsa(categoriaScelta,sottoCategoriaScelta,risorsaSelezionata);
												
						
					} 
					else {
						if(model.categoriaHaRisorse(categoriaScelta)) {
						
							int risultato = view.scelta(TITOLO_RISORSE,model.elencoRisorse(categoriaScelta,-1))-1;
							if(risultato==-1) { risorsaScelta=-1 ; break;}

							risorsaScelta =  model.scegliRisorsa(categoriaScelta,-1,risultato);
						}
						assert false;
						risorsaScelta = -1;
						break;
					}
				
					break;

			default:
				view.incornicia(MESSAGGIO_ERRORE);
				break;
			}
			
			assert invariante()  && risorsaScelta>=-1;		
			return risorsaScelta;
	}

	public  void idCorrente() {
		model.idCorrente();
	}
	public void start() {

		importaFruitoriOperatori();//caricare fruitori e operatori da file a inizio sessione
		importaArchivio();//importa archivio da file 
		eliminaDecaduti();//cerco decaduti a inizio di ogni sessione
		eliminaPrestitiScaduti();
		idCorrente();		
		
		view.notify("Benvenuto  ");
		
		int scelta;
		String username;
		
		do {
			scelta = view.scelta(TITOLO, voci);
			
			
			switch (scelta) {
				case 0:
					break;
					
				case 1:
					login();
					break;
				
				case 2:
					username = iscrizioneFruitore();
					 if (username != "") {
						usaFruitore(username);
					}
					break;
					
				default:
					view.notify(MESSAGGIO_ERRORE);
					break;
			}
			
		} while (scelta != 0);
		
		
		
		salvaFruitoriOperatori();//salvare fruitori e Operatori a fine sessione su file
		salvaArchivio();        //salvaArchivio su file
		
		view.notify(BelleStringhe.incornicia(ARRIVEDERCI));
	}
	
	public  void login() {
		boolean finito = false;
		boolean continua = true;
		String username, password;
		
		do {
			username = view.StringaNonVuota(INPUT_USERNAME);
			password = view.StringaNonVuota(INPUT_PASSWORD);
			
			if (cercaFruitore(username, password)) {
				usaFruitore(username);
				finito = true;
			}else {
				if(cercaOperatore(username, password)) {
					usaOperatore(username);
					finito = true;
				}else {
					view.notify(BelleStringhe.rigaIsolata(ERRORE));
					continua = view.yesOrNo(RIPROVA);
					if (!continua)
						return;
				}
			}
		}while(!finito);		
	}
}
