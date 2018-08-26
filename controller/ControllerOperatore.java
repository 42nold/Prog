package controller;

import java.util.ArrayList;

import model.Model;
import view.MyView;

public class ControllerOperatore {
	private static final String NOMEFILEOPERATORI = "Operatori.dat";
	
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
				
	public  void salvaOperatori() {
		model.salvaFruitoriOperatori();
	}	
			
	public  void importaOperatori() {
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
				 int categoria = model.trovaCategoria(id);
				azioneDaRicerca(id,eliMod, categoria);
				
					 
				}while (eliMod==1);
			
			}
		}catch(ClassCastException e) {
			view.notify(ERROREINPUT);
		}
	}



}
