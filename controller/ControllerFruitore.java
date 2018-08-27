package controller;

import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.text.View;

import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.Stampa;
import model.Model;
import utility.Load;
import utility.Save;
import view.MyView;

public class ControllerFruitore {
	private static final String NOMEFILEFRUITORI = "Fruitori.dat";
	private static final String TITOLO_MENU_PRESTITO = "Scegli come desideri cercare la risorsa da aggiungere.";
	private static final String BUON_FINE = "Operazione eseguita correttamente";
	private static final String MESSAGGIO_PRESTITO_NON_CONCESSO = "Non si può ottenere questa risorsa in prestito";
	private static final String TITOLO_IN_SCADENZA = "Ecco i prestiti in scadenza";
	private static final String NO_IN_SCADENZA = "Non ci sono prestiti in scadenza";
	private static final String TITOLO_MENU_FRUITORE = "Menu Fruitore";
	private static final String[] VOCIMENUFRUITORE = {"Visualizza Prestiti", "Richiedi prestito", "Rinnova prestito", "Rinnovo iscrizione"};
	private static final String MESSAGGIO_ERRORE = "Scelta non valida";
	private static final String ERROREINPUT="\n\nErrore nell'input";
	private static final String[] OPZIONI_MENU_RICERCA = {"Esplora archivio", "Ricerca per attributo"};
	private Model model;
	private MyView view;
	private ControllerArchivio controllerArchivio;
	
	
	public ControllerFruitore(Model model , MyView view, ControllerArchivio controllerArchivio) {
		this.model = model ;
		this.view = view ;
		this.controllerArchivio = controllerArchivio;
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
		
	public  String iscrizioneFruitore() {
				
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
		
		
		MyMenu menuPrestito = new MyMenu(TITOLO_MENU_PRESTITO, OPZIONI_MENU_RICERCA);
		int scelta;
		
				scelta = menuPrestito.scegli();
			
			switch (scelta) {
				case 2:
					try {
						
						return  controllerArchivio.ricercaPerAttributo();
						
					}catch(ClassCastException e) {
						view.notify(ERROREINPUT);
					}
					
				break;
			
				case 1:
					return  controllerArchivio.esploraArchivio();
				

			default:
				view.incornicia(MESSAGGIO_ERRORE);
				break;
			}
				
			return -1;
	}
	
	
	public  void salvaFruitori(Save save) {
		model.salvaFruitori(save);
	}
			
	public  void importaFruitori(Load load) {
		model.importaFruitori(load);					  
	}
}
