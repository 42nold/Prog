package controller;

import java.util.ArrayList;

import model.Model;
import utility.Load;
import utility.Save;
import view.MyView;

public class ControllerOperatore {
	private static final String NOMEFILEOPERATORI = "Operatori.dat";
	private static final String TITOLO_MENU_STORICO = "scegli l'opzione desiderata";
	private static final String[] VOCIMENUSTORICO = {"visualizza storico completo","visualizza numero prestiti per anno solare","visualizza numero proroghe per anno solare","visualizza risorsa prestata pi� volte per anno solare","visualizza i prestiti per fruitore per anno solare"};
	private static final String TITOLO_MENU_OPERATORE = "Menu Operatore.";
	private static final String[] VOCI_MENU_OPERATORE = {"Visualizza dati fruitori", "Visualizza dati e prestiti dei fruitori", "Apri archivio","visualizza storico"};
	private static final String[] OPZIONI_MENU_RICERCA = {"Esplora archivio", "Ricerca per attributo"};
	private static final String ERROREINPUT="\n\nErrore nell'input";
	private static final String TITOLO_ELI_O_MOD = "seleziona l'azione desiderata";
	private static final String[] OPZIONI_ELI_O_MOD = {"visualizza", "modifica", "elimina"};
	
	/**
	 *  metodo principale per la gestione delle operazioni degli operatori
	 * @param username dell'operatore loggato
	 * @pre true
	 * @post fruitoriNoChange
	 */
	
	private Model model;
	private MyView view;
	private ControllerArchivio controllerArchivio;
	
	public ControllerOperatore(Model model , MyView view, ControllerArchivio controllerArchivio){
		this.model = model ;
		this.view = view ;
		this.controllerArchivio = controllerArchivio;
	}
	
	public  void usaOperatore(String username){
		
		
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
				cercaPerAttributoOmode();
				break;				
		}
	}
	
	private void esploraArchivio() {
		boolean sceltaValida = false;
		
		while(!sceltaValida) {
		
			int categoriaScelta=view.scelta("Categorie",model.elencoCategorie());
			
			if(categoriaScelta>0 && categoriaScelta<=model.sizeArchivio()) {
			
				controllerArchivio.usaCategoria(categoriaScelta-1);
			
				sceltaValida=true;
			}
			if(categoriaScelta==0)break;
		}
	}
	
	
	private  void cercaPerAttributoOmode() {
		try {
				
			int idRisorsaScelta = controllerArchivio.ricercaPerAttributo();
			
			if(idRisorsaScelta!=-1) {
				int eliMod;
				
				do{	
					 eliMod = view.scelta(TITOLO_ELI_O_MOD, OPZIONI_ELI_O_MOD);
			
					 if(eliMod==0) 
						 return;
					 
					int categoria = controllerArchivio.trovaPosCategoriaInArray(idRisorsaScelta);
					int sottocategoria = controllerArchivio.trovaPosSottoCategoriaInArray(categoria);
					controllerArchivio.azioneDaRicerca(idRisorsaScelta, eliMod, categoria, sottocategoria);
					 
				}while (eliMod==1);			
			}
		}catch(ClassCastException e) {
			view.notify(ERROREINPUT);
		}
	}
	
	private void opzioniStorico() {
		int sceltaMenuStorico = view.scelta(TITOLO_MENU_STORICO, VOCIMENUSTORICO);
		
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
	public  boolean cercaOperatore(String username, String password) {
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

	public String elencoOperatori() {
		return model.elencoOperatori();
	}
				
	public  void salvaOperatori(Save save) {
		model.salvaOperatori(save);
	}	
			
	public  void importaOperatori(Load load) {
		model.importaOperatori(load);					  
	 }

	private  String elencoFruitori() {
		
		if(model.hasFruitori()) view.notify("elenco fruitori vuoto");

		return model.elencoFruitori();
	}
	
	private  String elencoFruitoriFull() {
		
		if(model.hasFruitori()) view.notify("elenco fruitori vuoto");
		
		return model.elencoFruitoriFull();
	}

}
