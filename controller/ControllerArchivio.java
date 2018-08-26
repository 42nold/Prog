package controller;

import java.util.ArrayList;

import model.Model;
import view.BibliotecaView;
import view.MyView;

public class ControllerArchivio {

	private static final String TITOLO_MENU_GESTIONERISORSA= "Opzioni disponibili";
	private static final String[] OPZIONI = {"visualizza risorse","aggiungi risorsa","elimina risorsa","modifica risorsa"} ;
	
	private static final String ERROREINPUT="\n\nErrore nell'input";
	
	private Model model;
	private MyView view;
	
	public ControllerArchivio(Model model , MyView view) {
		this.model = model ;
		this.view = view ;
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


		if(model.categoriaHaRisorse(categoria)) 
		{
			switch (eliMod) {
			case 1:
				view.notify(model.showRisorsa(id,categoria,-1));
				break;
			case 2:
				modifica(id,categoria);
				break;
			case 3:
				model.rimuoviRisorsa(id,categoria,-1);
				break;
			default:
				break;
		}	
		}
		else 
		{
			
				
			switch (eliMod) {
			case 1:
				view.notify(model.showRisorsa(id,categoria,s));
				break;
			case 2:
				modifica(id,c);
				break;
			case 3:
				model.rimuoviRisorsa(id,categoria,s);
				break;
			default:
				break;
		}	 

			}
	}
	assert invariante() ;
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

}
