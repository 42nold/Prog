package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import view.*;
import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.Stampa;
import model.Model;
import view.MyView;

@SuppressWarnings("serial")

public class Controller  implements Serializable{
	/**
	 * @invariant invariante() 
	 */
	
	
	
	
	
	private static final String TITOLO_SELEZIONA_ATTRIBUTO = "Scegli attributo con cui filtrare la ricerca";
	private static final String[] vociMenuSelezionaAttributo = {"Nome","Autore/Regista","Casa editrice/Casa Di produzione","Genere","Lingua","Anno di pubblicazione","Numero di pagine", "Durata"};
	
	
	private static final String[] opzioniDiricerca = {"Ricerca per attributo", "Ricerca navigando l'archivio"};
	private static final String TITOLO_CATEGORIA = "Scegli la categoria";
	private static final String TITOLO_SOTTOCATEGORIA = "Scegli la sottocategoria";
	

	private static final String MESSAGGIO_ERRORE = "Scelta non valida";
	private static final String TITOLO_MENU_OPERATORE = "Menu Operatore.";
	private static final String[] VOCI_MENU_OPERATORE = {"Visualizza dati fruitori", "Visualizza dati e prestiti dei fruitori", "Apri archivio","visualizza storico"};
	private static final String[] OPZIONI_MENU_RICERCA = {"Esplora archivio", "Ricerca per attributo"};
	
	
	private static final String ERROREINPUT="\n\nErrore nell'input";
	private static final String TITOLO_MENU_GESTIONERISORSA= "Opzioni disponibili";
	private static final String[] OPZIONI = {"visualizza risorse","aggiungi risorsa","elimina risorsa","modifica risorsa"} ;
	
	
	private static final String TITOLO_MENU_STORICO = "scegli l'opzione desiderata";
	private static final String[] vociMenuStorico = {"visualizza storico completo","visualizza numero prestiti per anno solare","visualizza numero proroghe per anno solare","visualizza risorsa prestata piï¿½ volte per anno solare","visualizza i prestiti per fruitore per anno solare"};
	private static final String TITOLO_ELI_O_MOD = "seleziona l'azione desiderata";
	private static final String[] OPZIONI_ELI_O_MOD = {"visualizza", "modifica", "elimina"};
	private static final String INPUT_USERNAME = "Inserire username -> ";
	private static final String INPUT_PASSWORD = "Inserire password -> ";
	private static final String ERRORE = "Username o password sabliata!!!";
	private static final String RIPROVA = "Voi riprovare il login?";
	private static final String TITOLO = "Scegli tra le seguenti opzioni";
	private static final String[] VOCI = {"Login","Registrati"};
	private static final String ARRIVEDERCI = "Arrivederci";
	
	private static final String NOMEFILEFRUITORI = "Fruitori.dat";
	private static final String TITOLO_MENU_PRESTITO = "Scegli come desideri cercare la risorsa da aggiungere.";
	private static final String BUON_FINE = "Operazione eseguita correttamente";
	private static final String MESSAGGIO_PRESTITO_NON_CONCESSO = "Non si può ottenere questa risorsa in prestito";
	private static final String TITOLO_IN_SCADENZA = "Ecco i prestiti in scadenza";
	private static final String NO_IN_SCADENZA = "Non ci sono prestiti in scadenza";
	private static final String TITOLO_MENU_FRUITORE = "Menu Fruitore";
	private static final String[] VOCIMENUFRUITORE = {"Visualizza Prestiti", "Richiedi prestito", "Rinnova prestito", "Rinnovo iscrizione"};
	private static final String TITOLO_RISORSE = "Scegli la risorsa da selezionare";

	private static final String NOMEFILEOPERATORI = "Operatori.dat";

	private Model model;
	private MyView view ;

	
	public Controller(Model model , MyView view) {
		this.model = model ;
		this.view = view ;
	
		
	}
	
	
	protected void start() {

		importaFruitori();
		importaOperatori();//caricare fruitori e operatori da file a inizio sessione
		importaArchivio();//importa archivio da file 
		eliminaDecaduti();//cerco decaduti a inizio di ogni sessione
		eliminaPrestitiScaduti();
		idCorrente();		
		
		view.notify("Benvenuto  ");
		
		int scelta;
		String username;
		
		do {
			scelta = view.scelta(TITOLO, VOCI);
			
			
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
		
		
		
		salvaFruitori();
		salvaOperatori();//salvare fruitori e Operatori a fine sessione su file
		salvaArchivio();        //salvaArchivio su file
		
		view.notify(BelleStringhe.incornicia(ARRIVEDERCI));
	}
	
	private  void login() {
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

	private void usaCategoria(int categoria) {
	
		if (model.haRisorseEsottocategorie(categoria))
			{
			view.notify(("errore! questa categoria ha sia risorse che sottocategorie!")); 
			return ;
			}

		if(model.categoriaHaRisorse(categoria)) gestioneRisorse(categoria,-1);
		
		else {
			
			if( !model.categoriaHaSottoCategoria(categoria) ) { 
				view.notify("non ci sono sottocategorie nï¿½ risorse in questa categoria"); 
				gestioneRisorse(categoria,-1); 
			}
			else {
				
		
				boolean sceltaValida = false ;
				
				while (!sceltaValida) {
					int sottoCategoriaScelta=view.scelta("Sottocategorie", model.elencoSottoCategorie(categoria));

					
					if(sottoCategoriaScelta>0 ) {
						gestioneRisorse(categoria,sottoCategoriaScelta-1);
											
					}

					 sceltaValida=true;
				}
			}
		}		
	}

	private void gestioneRisorse(int categoria, int sottocategoria) {

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
					model.rimuoviRisorsa(model.getId(risorsaDaEliminare-1,categoria,sottocategoria),categoria);		
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

	/*private void aggiungiRisorsaCategoria(int categoria,int sottocategoria) {

		String[] attributiStringa = acquisisciAttributiStringaCategoria(categoria);
		int[] attributiNumerici = acquisisciAttributiNumericiCategoria(categoria);
		
		model.aggiungiRisorsa(attributiStringa, attributiNumerici,categoria,sottocategoria);		
		
		
	}*/
	
	private void aggiungiRisorsaCategoria(int categoria,int sottocategoria) {

	//	String[] attributiStringa = acquisisciAttributiStringaCategoria(categoria);
	//	int[] attributiNumerici = acquisisciAttributiNumericiCategoria(categoria);
		
		ArrayList<Object> attributi = acquisisciAttributiCategoria(categoria);
		try {
			model.aggiungiRisorsa(attributi,categoria,sottocategoria);		
		}catch(ClassCastException e) {
			view.notify(ERROREINPUT);
		}
		
		
	}
	
	
	private ArrayList<Object> acquisisciAttributiCategoria(int categoria) {

		ArrayList<String> descrizioneCampiRisorsa= model.getDescrizioneCampi(categoria);
	
		ArrayList<Object> nuoviAttributi = new ArrayList<Object>();
		
		for(int i = 1 ; i<descrizioneCampiRisorsa.size(); i++) {
			nuoviAttributi.add( view.leggiInput("inserire "+descrizioneCampiRisorsa.get(i)+" :"));
			
		}
		
		return nuoviAttributi;
	}
	
	private String[] getDescrizioneCampiRisorsa(int categoriaScelta) {
		ArrayList<String> campiRisorsaList =new ArrayList();
		campiRisorsaList=model.getDescrizioneCampiRisorsa(categoriaScelta);
		
		String [] s = new String [campiRisorsaList.size()];
		
		for(int i=0; i<campiRisorsaList.size();i++)
			s[i]=campiRisorsaList.get(i);
		
		return s;
	}
	/**
	 * carica i dati dell'archivio  e dello storico da file
	 * @pre true
	 * @post fruritoriOperatoriNoChange()
	 */
		public  void importaArchivio() {
			
			
			model.importaDati();
			
			view = new BibliotecaView();

			
		}
	/** 
	 * salva dati dell'archivio e dello storico su file
	 * @pre true 
	 * @post @nochange
	 */
		public  void salvaArchivio() {//e storico
			
			model.salvaArchivio();	
		}
		
		public  void idCorrente() {
			model.idCorrente();
		}
		

private void azioneDaRicerca(int id, int eliMod, int categoria) {

			switch (eliMod) {
			case 1:
				view.notify(model.showRisorsa(id,categoria));
				break;
			case 2:
				modifica(id,categoria);
				break;
			case 3:
				model.rimuoviRisorsa(id,categoria);
				break;
			default:
				break;
		}	
	}
	

	private void modifica(int id , int categoria) {
		try {
			ArrayList<String> campiRisorsa = model.getDescrizioneCampi(categoria);
			
			Object[] nuoviAttributi = new Object[campiRisorsa.size()];
			
			//id non modificabile
			for(int i=1; i<campiRisorsa.size();i++) {
				
				char modificaNome = view.Char("vuoi modificare "+campiRisorsa.get(i)+" ? (s/n)");
				
				if(modificaNome=='s'||modificaNome=='S') {
				
					Object nomeNuovo= view.leggiInput("inserisci il nuovo valore");
				
					nuoviAttributi[i]=nomeNuovo;
				}	
				
				else nuoviAttributi[i]= null;	
			}
		
			model.modificaRisorsa(id,categoria,nuoviAttributi);
		}catch(ClassCastException e) {
			view.notify(ERROREINPUT);
		}
	}

    /**
	 * verifica se il fruitore relativo a username e password ï¿½ iscritto
	 * @param username del fruitore
	 * @param password del fruitore
	 * @return true se il fruitore ï¿½ iscritto
	 * @pre username!=null && password!=null 
	 * @post @nochange
	 */   
	public  boolean cercaFruitore(String username, String password) {
		return model.cercaFruitore(username,password);
	}
		
	private  String iscrizioneFruitore() {
				
		String nome, cognome, password, username;
		int eta;
		Calendar data_iscrizione, data_scadenza;
		boolean finito=false;
		
		nome=view.StringaNonVuota("Inserisci nome\n");
		cognome=view.StringaNonVuota("Inserisci cognome\n");
		eta=view.InteroNonNegativo("inserisci la tua etï¿½\n");
		
		if(eta<18) {
			view.notify("Servizio riservato ai soli cittadini maggiorenni\n");
					
			return "";
		}	
		
		do {
			username=view.StringaNonVuota("Inserisci username\n");
			if (model.posizioneFruitore(username) != -1)
				view.notify("username già presente \n");
			else
				finito=true;
		}while(!finito);
		
		password=view.StringaNonVuota("Inserisci password\n");		
		
		data_iscrizione=Calendar.getInstance();
		data_scadenza=Calendar.getInstance();
		data_scadenza.add(Calendar.YEAR, 5);
		
		model.addFruitore(nome, cognome, eta, username, password, data_iscrizione, data_scadenza);
			
		return username;	
	}
	
	
	private  void eliminaDecaduti() {
		model.eliminaDecaduti();
	}

	private  void eliminaPrestitiScaduti() {
		model.eliminaPrestitiScaduti();
	}
	
	/**
	 * rinnova l'iscrizione del fruitore selezionato
	 * @param numFruitore posizione del fruitore nell'array
	 * @pre numFruitore>=0 && numFruitore<fruitori.size() 
	 * @post operatoriNoChange()
	 */
	private  void rinnovoIscrizioneFruitore(int numFruitore) {
		assert numFruitore>=0  ;
		
		Calendar data_scadenza=model.getDataScadenzaFruitore(numFruitore);
		Calendar data_odierna=Calendar.getInstance();
		
		//calcolo della differenza di giorni tra data scadenza e data odierna 
		long diff=(data_scadenza.getTimeInMillis()-data_odierna.getTimeInMillis())/ 86400000;
		
		if (diff>10) {
			view.notify("mancano ancora "+ diff+" giorni alla scadenza, impossibile soddisfare richiesta\n");

		}else {
			model.aggiornaDataScadenzaFruitore(numFruitore);
			
		}
		 
	}

	private  String elencoFruitori() {
		
		if(model.hasFruitori()) view.notify("elenco fruitori vuoto");

		return model.elencoFruitori();
	}
	

	private  String elencoFruitoriFull() {
		
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
		assert username!= null;
		
		int scelta, numFruitore;
		
		numFruitore = model.posizioneFruitore(username);//numero fruitore nell'array
		
		
		//se non lo trovo esco
		if (numFruitore==-1) {
			
			return;
			}
		
		model.setIdPrestitoFruitore(numFruitore);
				
		do {
			scelta = view.scelta(TITOLO_MENU_FRUITORE,  VOCIMENUFRUITORE);
					
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
					
					nuovoPrestito(numFruitore, risorsaScelta);
					
					break;
					
				case 3:
					
					ArrayList<Integer> inScadenza = model.getInScadenzafruitore(numFruitore);
					String[] inScadenzaString = prestitiFruitoreInScadenza(numFruitore, inScadenza); 
					
					if(inScadenzaString!=null) {
						rinnovoPrestitoFruitore(numFruitore, inScadenzaString, inScadenza);
					} else {
						view.notify(NO_IN_SCADENZA);
					}						
					
					break;
					
				case 4:
					rinnovoIscrizioneFruitore(numFruitore);
					break;
			}
		}while(scelta!=0);
		
		
		return;
	}

	private String[] prestitiFruitoreInScadenza(int numFruitore, ArrayList<Integer> inScadenza) {
		
		if (inScadenza != null) {
			String[] inScadenzaString = new String[inScadenza.size()];
			
			for (int i = 0; i < inScadenzaString.length; i++) {
				inScadenzaString[i] = model.getDescrizionePrestitoFruitore(numFruitore,inScadenza,i);
			}
			
			return inScadenzaString;
		} 
		
		return null;
	}
	
	private void rinnovoPrestitoFruitore(int numFruitore, String[] inScadenzaString, ArrayList<Integer> inScadenza) {
		int scegliDaRinnovare = view.scelta(TITOLO_IN_SCADENZA, inScadenzaString);
		
		switch (scegliDaRinnovare) {
		case 0:
			break;

		default:
			model.rinnovaPrestitoFruitore(numFruitore,inScadenza,scegliDaRinnovare);
			break;
		}
	}
	
	private void nuovoPrestito(int numFruitore, int risorsaScelta) {
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
		} else {
			Stampa.aVideo(MESSAGGIO_PRESTITO_NON_CONCESSO);
		}
	}
	
	/**
	 * Permette di selezionare una risorsa nell'archivio.
	 * @return risorsa selezionata.
	 * @pre true 
	 * @post @return>=-1 && @nochange
	 */
	private  int selezionaRisorsa() {
		
		
		int risorsaScelta = -1;
		
		MyMenu menuPrestito = new MyMenu(TITOLO_MENU_PRESTITO, opzioniDiricerca);
		int scelta;
		
				scelta = menuPrestito.scegli();
			
			switch (scelta) {
				case 1:
					try {
						int attributoScelto = view.scelta(TITOLO_SELEZIONA_ATTRIBUTO, vociMenuSelezionaAttributo);
					
						if(attributoScelto!=0 ) {
							Object parametro;
							parametro = view.leggiInput("inserisci il parametro da cercare per l'attributo selezionato");
									
							ArrayList<Integer> match = model.filtraRisorse(attributoScelto,parametro);
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
					}catch(ClassCastException e) {
						view.notify(ERROREINPUT);
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
			
			assert  risorsaScelta>=-1;		
			return risorsaScelta;
	}
	
	
	private  void salvaFruitori() {
		model.salvaFruitoriOperatori();
	}
			
	private  void importaFruitori() {
		model.importaFruitoriOperatori();					  
	}

	private  void usaOperatore(String username){
		
		
		int numOperatore, scelta;
		
		numOperatore=posizioneOperatore(username);        
		
		if (numOperatore==-1) {                          
		
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
					if(model.ArchivioVuoto()) {
						view.notify("archivio vuoto");
						break;
					}
						
					boolean ricercaValida = false;
					int ricercaScelta = 0;
					
					while(!ricercaValida) {
					
						ricercaScelta = view.scelta("Scegli il metodo di ricerca delle risorse :", OPZIONI_MENU_RICERCA);
						if(ricercaScelta>=0 && ricercaScelta<=OPZIONI_MENU_RICERCA.length) ricercaValida=true;
					}
					
					ricercaRisorsa(ricercaScelta);
					
					break;
					
				case 4: 	
					
					opzioniStorico();
					break;
			}
					
					
		}while(scelta!=0);
		
		
		return;	
	
	}
	
	private void ricercaRisorsa(int ricercaScelta) {
		switch(ricercaScelta) {
		
			default : break;
			
			case 1:
				esploraArchivio();			
				break;
			
			case 2:
				ricercaPerAttributo();
				break;				
		}
	}
	
	private void ricercaPerAttributo() {
		int categoriaScelta=view.scelta("Categorie",model.elencoCategorie());
		
		int attributoScelto = view.scelta(TITOLO_SELEZIONA_ATTRIBUTO, getDescrizioneCampiRisorsa(categoriaScelta));
											
		if(attributoScelto!=0)
			cercaPerAttributoOmode(attributoScelto);
	}
	
	private void esploraArchivio() {
		boolean sceltaValida = false;
		
		while(!sceltaValida) {
		
			int categoriaScelta=view.scelta("Categorie",model.elencoCategorie());
			
			if(categoriaScelta>0 && categoriaScelta<=model.sizeArchivio()) {
			
				usaCategoria(categoriaScelta-1);
			
				sceltaValida=true;
				}
			if(categoriaScelta==0)break;
			}
	}
	
	private void opzioniStorico() {
		int sceltaMenuStorico = view.scelta(TITOLO_MENU_STORICO, vociMenuStorico);
		
		switch(sceltaMenuStorico) {
		
		case 1: 
			view.notify(model.getDescrizioneStorico());
			break;
		case 2:
			view.notify(model.numEventoAnnoSolare(Model.NUOVO_PRESTITO,"prestiti"));
			break;
		case 3:
			view.notify(model.numEventoAnnoSolare(Model.PROROGA_PRESTITO,"proroghe"));
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
	
	/**
	 * verifica se l'username e la password in ingresso corrispondono ad un operatore registrato
	 * @param username dell'operatore
	 * @param password dell'operatore
	 * @return true se esiste un operatore corrispondente
	 * @pre username!= null && password!= null && password != "" && username != ""
	 * @post @nochange
	 */
	private  boolean cercaOperatore(String username, String password) {
			assert  username!= null && password!= null && password != "" && username != "" ;
		
					
			int numOperatore;
			
			if((numOperatore=posizioneOperatore(username)) != -1) { 												
				
				if(model.verificaPasswordOperatore(numOperatore,password))
				
				return true;
			}
			
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
			assert  username!= null && username!= "" ;
			
			
			for (int i=0; i<model.sizeOperatori(); i++) 
				if(model.verificaUsernameOperatore(i,username)) {
					
					
					return i;
				}
			
			return -1;
		}

		private String elencoOperatori() {
		return model.elencoOperatori();
	}
				
		private  void salvaOperatori() {
		model.salvaFruitoriOperatori();
	}	
			
		private  void importaOperatori() {
		model.importaFruitoriOperatori();					  
	 }

	/**
	 * metodo per la ricerca di risorse tramite attributo , richiesta all'utente su ricerca da eseguire e su azione da compiere sulla risorsa	
	 * @param attributoScelto attributo da confrontare col parametro di ricerca
	 * @pre attributoScelto>=0
	 * @post true
	 */
		
	private  void cercaPerAttributoOmode(int attributoScelto) {
		try {
			Object parametro;
			parametro = view.leggiInput("inserisci il parametro da cercare per l'attributo selezionato");
			ArrayList<Integer> match = model.filtraRisorse(attributoScelto,parametro);
			
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
				 int categoria = model.trovaPosizioneCategoria(id);
				azioneDaRicerca(id,eliMod, categoria);
				
					 
				}while (eliMod==1);
			
			}
		}catch(ClassCastException e) {
			view.notify(ERROREINPUT);
		}
	}



}
