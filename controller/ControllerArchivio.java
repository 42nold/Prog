package controller;

import java.util.ArrayList;

import model.Model;
import utility.Load;
import utility.Save;
import view.BibliotecaView;
import view.MyView;

public class ControllerArchivio {

	private static final String TITOLO_MENU_GESTIONERISORSA= "Opzioni disponibili";
	private static final String[] OPZIONI = {"visualizza risorse","aggiungi risorsa","elimina risorsa","modifica risorsa"} ;
	private static final String TITOLO_SELEZIONA_ATTRIBUTO = "Scegli attributo con cui filtrare la ricerca";	
	private static final String ERROREINPUT="\n\nErrore nell'input";
	private static final String TITOLO_RISORSE = "Scegli la risorsa da selezionare";
	private static final String TITOLO_CATEGORIA = "Scegli la categoria";
	private static final String TITOLO_SOTTOCATEGORIA = "Scegli la sottocategoria";
	private Model model;
	private MyView view;
	
	public ControllerArchivio(Model model , MyView view) {
		this.model = model ;
		this.view = view ;
	}

	public void usaCategoria(int categoria) {
	
		if (model.haRisorseEsottocategorie(categoria))
			{
			view.notify(("errore! questa categoria ha sia risorse che sottocategorie!")); 
			return ;
			}

		if(model.categoriaHaRisorse(categoria)) gestioneRisorse(categoria, -1);
		
		else {
			
			if( !model.categoriaHaSottoCategoria(categoria) ) { 
				view.notify("non ci sono sottocategorie n� risorse in questa categoria"); 
				gestioneRisorse(categoria, -1); 
			}
			else {
				
		
				boolean sceltaValida = false ;
				
				while (!sceltaValida) {
					int sottoCategoriaScelta=view.scelta("Sottocategorie", model.elencoSottoCategorie(categoria));
;
					
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
					model.rimuoviRisorsa(model.getId(risorsaDaEliminare-1,categoria,sottocategoria),categoria, sottocategoria);		
					break;
					
				case 4:
					
				
					int risorsaDaModificare = view.scelta("Scegli la risorsa da modificare", elenco);

						
					if (risorsaDaModificare==0) break;
					
					//risorsaDaModificare -1 rappresenta l'attuale posizione nell'array della risorsa
					modifica(model.getId(risorsaDaModificare-1,categoria,sottocategoria),categoria, sottocategoria);
					
					break;
					
				default:
					break;				
			}
		}
	}

	
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
		public  void importaArchivio(Load load) {
			
			model.importaDati(load);
	
		}
/** 
 * salva dati dell'archivio e dello storico su file
 * @pre true 
 * @post @nochange
 */
	public  void salvaArchivio(Save save) {//e storico
		
		model.salvaArchivio(save);		
	}
	
	public  void idCorrente() {
		model.idCorrente();
	}
	
	public void azioneDaRicerca(int id, int eliMod, int categoria, int sottocategoria) {

		switch (eliMod) {
			case 1:
				view.notify(model.showRisorsa(id,categoria));
				break;
			case 2:
				modifica(id,categoria, sottocategoria);
				break;
			case 3:
				model.rimuoviRisorsa(id,categoria, sottocategoria);
				break;
			default:
				break;
		}	
		
	}
	

	private void modifica(int id , int categoria, int sottocategoria) {
		try {
			ArrayList<String> campiRisorsa = model.getDescrizioneCampi(categoria);
			
			Object[] nuoviAttributi = new Object[campiRisorsa.size()];
			
			//id non modificabile
			for(int i=1; i<campiRisorsa.size();i++) {
				
				char modificaCampo = view.Char("vuoi modificare "+campiRisorsa.get(i)+" ? (s/n)");
				
				if(modificaCampo=='s'||modificaCampo=='S') {
				
					Object campoNuovo= view.leggiInput("inserisci il nuovo valore");
				
					nuoviAttributi[i]=campoNuovo;
				}	
				
				else nuoviAttributi[i]= null;	
			}
		
			model.modificaRisorsa(id,categoria,sottocategoria,nuoviAttributi);
		}catch(ClassCastException e) {
			view.notify(ERROREINPUT);
		}
	}
	

	
	/**
	 * metodo per la ricerca di risorse tramite attributo , richiesta all'utente su ricerca da eseguire e su azione da compiere sulla risorsa	
	 * @param attributoScelto attributo da confrontare col parametro di ricerca
	 * @pre attributoScelto>=0
	 * @post true
	 */
		
	private String getNomeRisorsa(int id) {
		return model.getNomeRisorsa(id);
	}
	
	public ArrayList<Integer> filtraRisorse(int attributoScelto, Object parametro) {
		return model.filtraRisorse(attributoScelto, parametro);
	}
	
	public int ricercaPerAttributo() throws ClassCastException{
		int categoriaScelta=view.scelta("Categorie",model.elencoCategorie());
		
		int attributoScelto = view.scelta(TITOLO_SELEZIONA_ATTRIBUTO, getDescrizioneCampiRisorsa(categoriaScelta));
								
		Object parametro;
		parametro = view.leggiInput("inserisci il parametro da cercare per l'attributo selezionato");
		ArrayList<Integer> match = filtraRisorse(attributoScelto,parametro);
		
		if(match.size()>1) {
	
			String[] opzioniEsiti= new String[match.size()];
			int i=0;
			for(int r : match) { 
				if(getNomeRisorsa(r)!=null) {	opzioniEsiti[i]= getNomeRisorsa(r); i++; } 
			}	
			int risorsaScelta = view.scelta("ecco l'esito della ricerca :", opzioniEsiti);
			
			if(risorsaScelta!=0 ) 
				return match.get(risorsaScelta-1);
	
		}
		
		return -1;
		
	}
	
	public int trovaPosCategoriaInArray(int idRisorsaScelta) {
		return model.trovaPosCategoriaInArray(idRisorsaScelta);
	}
	
	public int trovaPosSottoCategoriaInArray(int posCategoria) {
		return model.trovaPosCategoriaInArray(posCategoria);
	}
	
	public int esploraArchivio() {
		int risorsaScelta;
		int categoriaScelta =view.scelta(TITOLO_CATEGORIA, model.elencoCategorie())-1;

		if(categoriaScelta==-1) {return -1;}
		
		if (model.categoriaHaSottoCategoria(categoriaScelta)) {
			
			int sottoCategoriaScelta =view.scelta(TITOLO_SOTTOCATEGORIA, model.elencoSottoCategorie(categoriaScelta))-1;
			if(sottoCategoriaScelta==-1) {return -1;}
			
		int	risorsaSelezionata = view.scelta(TITOLO_RISORSE,model.elencoRisorse(categoriaScelta,sottoCategoriaScelta))-1;

		if(risorsaSelezionata==-1) { return -1;}
			
		risorsaScelta =model.scegliRisorsa(categoriaScelta,sottoCategoriaScelta,risorsaSelezionata);
									
			
		} 
		else {
			if(model.categoriaHaRisorse(categoriaScelta)) {
			
				int risultato = view.scelta(TITOLO_RISORSE,model.elencoRisorse(categoriaScelta,-1))-1;
				if(risultato==-1) { return -1;}

				risorsaScelta =  model.scegliRisorsa(categoriaScelta,-1,risultato);
			}
			assert false;
			risorsaScelta = -1;
			
		}
	
		return risorsaScelta;
	}
}
