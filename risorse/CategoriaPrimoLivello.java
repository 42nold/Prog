package risorse;

import java.io.Serializable;
import java.util.ArrayList;


@SuppressWarnings("serial")
public abstract class CategoriaPrimoLivello<T extends Risorsa> extends Categoria<T> implements Serializable {

	protected ArrayList<Categoria<T>> sottocategorie;	
	
 	public CategoriaPrimoLivello(String nome, int durataMassimaPrestito, int durataMassimaProroga, int termineProroga, int maxRisorse, int id) {
		
		super (nome, durataMassimaPrestito, durataMassimaProroga, termineProroga, maxRisorse, id);
		sottocategorie = new ArrayList<Categoria<T>>();
		
		//assert invariante();
	}
 
/**
 * aggiungi nuova sottocategoria
 * @param nome della sottocategoria
 * @pre true
 * @post sizeSottoCategorie()=sizeSottoCategorie()@pre+1
 * 
 */
 	public abstract void aggiungiSottoCategoria(String nome) ;
/**
 * rimuovi la sottocategoria scelta	
 * @param indice relativo alla posizione della sottocategoria
 * @pre indice>=0 && indice<sottocategorieSize()
 * @post sottocategorieSize()==sottocategorieSize()@pre-1
 */
	public void rimuoviSottocategoria(int indice) {
		//assert invariante() &&  indice>=0 && indice<sottocategorie.size() ;
		//int lungPre = sottocategorie.size();
		
		sottocategorie.remove(indice);
		
		//assert invariante() && lungPre-1==sottocategorie.size() ;
	}
	
	/**
	 * torna un vettore coi nomi  delle sottocategorie
	 * @return array di stringhe 
	 * @pre true
	 * @post @nochange && @return != null
	 */
	public String[] elencoSottoCategorie() {
	//assert invariante();
		//CategoriaPrimoLivello<T> thisPre = this;
		
		String[] elenco = new String[sottocategorie.size()];
		String[] vuoto = new String [0] ;
		if (sottocategorie.size()<1) return vuoto;
		
		else {
				int i=0;
				
				for(Categoria<T> s : sottocategorie) {
					
					elenco[i]=s.getNome();
					i++;
					
				}
		}
		//assert invariante() && thisPre==this && elenco!=null;
		return elenco;
	}
	
	
	/**
	 * verifica se ci sono sottoactegorie i questa categoria
	 * @return true se c'è almeno una sottocategoria
	 * @pre true
	 * @post @nochange
	 */
	public boolean hasSottoCategoria() {
		//assert invariante() ;

		if(sottocategorie.size()>0) return true;
		
		return false;
	}
	
	/**
	 * verifica quante sottocategorie ci sono nella categoria 
	 * @return il numero di sottocategorie in questa categoria 
	 * @pre true
	 * @post @nochange
	 */
	public int numeroSottoCategorie() {
		//assert invariante();
		
		return sottocategorie.size();
	}
/**
 * ritorna sottocategorie della categoria in questione
 * @return array di sottocategorie
 * @pre true
 * @post @nochange
 */
	public ArrayList<Categoria<T>> getSottoCategorie() {
		
		return sottocategorie;
	}
	
	/**
	 * per ogni sottocategoria cerco le risorse che soddisfano i parametri di ricerca 
	 * @param attributoScelto attributo da confrontare 
	 * @param chiaveDiRicerca stringa da confrontare
	 * @param numDiRicerca numero da confrontare
	 * @return elenco di identificarivi delle risorse che rispecchiano i requisiti
	 * @pre attributoScelto!=null && (chiaveDiRicerca!=null || numDiRicerca!=null)
	 * @post @nochange
	 */
	public ArrayList<Integer> filtraRisorse(int attributoScelto, Object parametroRicerca) throws ClassCastException{		
		//assert invariante() && attributoScelto>=0 && (chiaveDiRicerca!=null || numDiRicerca>=0);
		//CategoriaPrimoLivello<T> thisPre = this;
		
		 ArrayList<Integer> risultato = new  ArrayList<Integer>();
		
		 if (hasSottoCategoria())
			 for(Categoria<T> s : sottocategorie) {
				
				ArrayList<Integer> risorseDiSottocategoria = s.filtraRisorse(attributoScelto, parametroRicerca);
		
				for( int sotto : risorseDiSottocategoria) 
					risultato.add(sotto);
			}
		
		// assert invariante() && thisPre == this ;
		return risultato;
	}

/**
 * cerca la risorsa voluta all'interno della categoria
 * @param risorsaScelta 
 * @return true se la risorsa è presente nella categoria
 * @pre	true
 * @post @nochange
 */
	public boolean cercaRisorsa(int risorsaScelta) {
		//assert invariante();
		//CategoriaPrimoLivello<T> thisPre = this;
		
		if(hasSottoCategoria())
			for (int i = 0; i < sottocategorie.size(); i++) {
				if (sottocategorie.get(i).cercaRisorsa(risorsaScelta)) 
					return true;
			}
		//assert invariante() && thisPre==this;
		return super.cercaRisorsa(risorsaScelta);
	
	}


	/**
	 * ritorna la descrizione della risorsa dato l'id oppure null se non la trova neanche nelle sue sottocategorie
	 * @param idRisorsa id della risorsa cercata
	 * @return stringa descrittiva o null
	 * @pre true
	 * @post @nochange
	 */
	public String getDescrizioneRisorsa(int idRisorsa) {
		//assert invariante();
		//CategoriaPrimoLivello<T> thisPre = this;
		
		String descrizione = null;
		String varAppoggio = null;
		
		if(hasSottoCategoria()) {
			for(Categoria<T> sottocategoria : sottocategorie) {

				varAppoggio = sottocategoria.getDescrizioneRisorsa(idRisorsa);
				if (varAppoggio!=null)  {
					descrizione=varAppoggio;
				}
			}
		}
		//assert invariante() && thisPre==this;
		return descrizione;
	}
	

/**
 * cerca il nome della risorsa desiderata
 * @param risorsaScelta id della risorsa desiderata
 * @return stringa contenente il nome oppure null
 * @pre true
 * @post @nochange
 */
	public String getNomeRisorsa(int risorsaScelta) {
		//assert invariante();
		//CategoriaPrimoLivello<T> thisPre = this;
		
		String risultato = null;
		if(hasSottoCategoria()) {
			
			for(Categoria<T> sottocategoria : sottocategorie) {
				risultato=sottocategoria.getNomeRisorsa(risorsaScelta);

				if(risultato!=null) {
					//assert invariante() && thisPre==this;
					return risultato;
				}
			}		
		}
		return risultato;
	}
/**
 * cerca in quale sottocategoria è contenuta la risorsa voluta	
 * @param risorsaScelta id della risorsa voluta
 * @return sottocategoria contenente oppure null
 * @pre true
 * @post @nochange
 */
	private Categoria trovaSottoCategoria(int risorsaScelta) {					
		//assert invariante();
		//CategoriaPrimoLivello<T> thisPre = this;
		
		int pos = -1;
		for (int i = 0; i < sottocategorie.size(); i++) {
			if (sottocategorie.get(i).cercaRisorsa(risorsaScelta)) pos = i;
		}
		
		if (pos != -1) {
			//assert invariante() && thisPre==this;
			return sottocategorie.get(pos);
		} 
		else {
			//assert invariante() && thisPre==this;
			return null;
		}
	}
	
	/**
	 * restituisce il numero di licenze della risorsa cercata
	 * @param risorsaScelta id della risorsa cercata
	 * @return numero di licenze oppure -1
	 * @pre true
	 * @post @nochange
	 */
	public int numeroLicenzeRisorsa(int risorsaScelta) {
		//assert invariante();
		//CategoriaPrimoLivello<T> thisPre = this;
		
		if(hasSottoCategoria()) {
			Categoria<T> sottoc= trovaSottoCategoria(risorsaScelta);
				if(sottoc != null) {
					int risultato = sottoc.numeroLicenzeRisorsa(risorsaScelta);
					
					//assert invariante() && thisPre==this;
					return risultato;
				}
		}
		
		return -1;		
	}
	/**
	 * incrementa o decrementa le licenze di una risorsa desiderata
	 * @param risorsaScelta id della risorsa selezionata
	 * @param flag 0 per decrementare o 1 per incrementare
	 * @pre flag==0 && numeroLicenzeRisorsa(risorsaScelta)>0 || flag==0 && numeroLicenzeRisorsa(risorsaScelta)=-1 || flag!=0
	 * @post true
	 */
	public void aggiornaLicenze(int risorsaScelta, int flag) {
		//assert invariante() && (flag==0 && numeroLicenzeRisorsa(risorsaScelta)>0 || flag==0 && numeroLicenzeRisorsa(risorsaScelta)==-1 || flag!=0 )  ;
		if(hasSottoCategoria()) {
			Categoria<T> sottoc= trovaSottoCategoria(risorsaScelta);
				if(sottoc != null)
					sottoc.aggiornaLicenze(risorsaScelta, flag);
		
		}
	
		//assert invariante();
	}
	
	
	public ArrayList<Categoria<T>> getSottocategorie() {

		return sottocategorie;
	}

	public String showRisorsa(int id) {
	
		return trovaSottoCategoria(id).showRisorsa(id);
		
	}

	public void modifica(int id, Object[] nuoviAttributi) throws ClassCastException{
	
		if(hasSottoCategoria()) {
			Categoria <T> c = trovaSottoCategoria(id);
			if (c!=null)
				c.modifica(id,nuoviAttributi);
		}
	}


	public boolean rimuoviRisorsa(int id) {
		
		if(hasSottoCategoria()) {
			Categoria c = trovaSottoCategoria(id);
			if (c!=null)
			if(c.rimuoviRisorsa(id)) return true;
			else return false;
		}
		return super.rimuoviRisorsa(id);
	}
	
	public String[] elencoRisorse(int s) {

		return sottocategorie.get(s).elencoRisorse();
	}
	
	public int scegliRisorsa(int s, int risorsaSelezionata) {

		return sottocategorie.get(s).scegliRisorsa(risorsaSelezionata);
	}
	
	public int getId(int pos, int sottocategoria) {

		return sottocategorie.get(sottocategoria).getIdRisorsa(pos);
	}
		
	public void aggiungiRisorsaEAggiornaStorico(ArrayList<Object> nuoviAttributi, int sottocategoria) throws ClassCastException {

		sottocategorie.get(sottocategoria).aggiungiRisorsa(nuoviAttributi);	
	}
	
	/**
		 * verifica che le proprietà invarianti della classe siano verificate
		 * @pre true
		 * @post @nochange
		 * @return true se gli attributi assumono valori validi e l'invariante della superclasse è verificata e i getters ritornano i giusti valori
		 */
	/*protected boolean invariante() {
		CategoriaPrimoLivello<T> thisPre =	this;
		
		boolean invariante = false;
		if( super.invarianteC() && durataMassimaPrestito>0 && durataMassimaProroga>0 && termineProroga>=0 && maxRisorse>0 && sottocategorie!=null && getDurataMassimaPrestito()==durataMassimaPrestito && getDurataMassimaProroga()== durataMassimaProroga && getMaxRisorse()==maxRisorse && getSottoCategorie()==sottocategorie && getTermineProroga()==termineProroga) invariante = true;
	
		assert this==thisPre;
		return invariante;
	}*/
	
	public int getIdCategoria(int idRisorsa) {
		if(hasSottoCategoria()) 
			for (Categoria c: sottocategorie) {
				int id=c.getIdCategoria(idRisorsa);
				if (id!=-1) 
					return id;
			}	
		return super.getIdCategoria(idRisorsa);
	}


}