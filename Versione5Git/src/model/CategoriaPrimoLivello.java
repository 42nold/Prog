package model;

import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.ing.mylib.MyMenu;

@SuppressWarnings("serial")
public abstract class CategoriaPrimoLivello<T extends Risorsa> extends Categoria<T> implements Serializable {
	/**
	 * @invariant invariante()
	 */
	protected ArrayList<Categoria<T>> sottocategorie;
	private int durataMassimaPrestito;
	private int durataMassimaProroga;
	private int termineProroga;
	private int maxRisorse;
	
	
 	public CategoriaPrimoLivello(String nome, int durataMassimaPrestito, int durataMassimaProroga, int termineProroga, int maxRisorse, int id) {
		super(nome); 
		this.durataMassimaPrestito = durataMassimaPrestito;
		this.durataMassimaProroga = durataMassimaProroga;
		this.termineProroga = termineProroga;
		this.maxRisorse = maxRisorse;
		this.idCategoria=id;
		sottocategorie = new ArrayList<Categoria<T>>();
		
		idRisorsa=idMax();		
		
		assert invariante();
	}
 		/**
 		 * verifica che le proprietà invarianti della classe siano verificate
 		 * @pre true
 		 * @post @nochange
 		 * @return true se gli attributi assumono valori validi e l'invariante della superclasse è verificata e i getters ritornano i giusti valori
 		 */
 	protected boolean invariante() {
 		CategoriaPrimoLivello<T> thisPre =	this;
 		
 		boolean invariante = false;
 		if( super.invarianteC() && durataMassimaPrestito>0 && durataMassimaProroga>0 && termineProroga>=0 && maxRisorse>0 && sottocategorie!=null && getDurataMassimaPrestito()==durataMassimaPrestito && getDurataMassimaProroga()== durataMassimaProroga && getMaxRisorse()==maxRisorse && getSottoCategorie()==sottocategorie && getTermineProroga()==termineProroga) invariante = true;
 	
 		assert this==thisPre;
 		return invariante;
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
		assert invariante() &&  indice>=0 && indice<sottocategorie.size() ;
		int lungPre = sottocategorie.size();
		
		sottocategorie.remove(indice);
		
		assert invariante() && lungPre-1==sottocategorie.size() ;
	}
	
	/**
	 * torna un vettore coi nomi  delle sottocategorie
	 * @return array di stringhe 
	 * @pre true
	 * @post @nochange && @return != null
	 */
	public String[] elencoSottoCategorie() {
		assert invariante();
		CategoriaPrimoLivello<T> thisPre = this;
		
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
		assert invariante() && thisPre==this && elenco!=null;
		return elenco;
	}
	
	/**
	 * verifica se ci sono risorse nelle sottocategorie, se esistono
	 * @return true se c'è almeno una risorsa
	 * @pre true
	 * @post @nochange
	 */
	public boolean hasRisorse() {
		assert invariante();
		
		
				if(risorse.size()>0) return true; 
		
		return false;
	}
	
	/**
	 * gestisci opzioni per le risorse di una sottocategoria scelta
	 * @param scelta sottocategoria su cui si vuole operare
	 * @pre scelta>=0 && scelta<sottocategorieSize()
	 * @post true
	 */
	/*public void usaSottoCategoria(int scelta) {													
		assert invariante() &&  scelta>=0 && scelta<sottocategorie.size() ;
		
		Categoria<T> sottocategoria = sottocategorie.get(scelta);		
		sottocategoria.gestioneRisorse();
		
		assert invariante();		
	}*/
	
	/**
	 * verifica se ci sono sottoactegorie i questa categoria
	 * @return true se c'è almeno una sottocategoria
	 * @pre true
	 * @post @nochange
	 */
	public boolean hasSottoCategoria() {
		assert invariante() ;

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
		assert invariante();
		
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
 * esegui l'operazione scelta sulla risorsa scelta 
 * @param id della risorsa scelta
 * @param eliMod operazione da eseguire
 * @pre true
 * @post true
 */

	
	/**
	 * per ogni sottocategoria cerco le risorse che soddisfano i parametri di ricerca 
	 * @param attributoScelto attributo da confrontare 
	 * @param chiaveDiRicerca stringa da confrontare
	 * @param numDiRicerca numero da confrontare
	 * @return elenco di identificarivi delle risorse che rispecchiano i requisiti
	 * @pre attributoScelto!=null && (chiaveDiRicerca!=null || numDiRicerca!=null)
	 * @post @nochange
	 */
	public ArrayList<Integer> filtraSottoCategorie(int attributoScelto, String chiaveDiRicerca, int numDiRicerca) {		
		assert invariante() && attributoScelto>=0 && (chiaveDiRicerca!=null || numDiRicerca>=0);
		CategoriaPrimoLivello<T> thisPre = this;
		
		 ArrayList<Integer> risultato = new  ArrayList<Integer>();
		
		 for(Categoria<T> s : sottocategorie) {
			
			ArrayList<Integer> risorseDiSottocategoria = s.filtraRisorse(attributoScelto,chiaveDiRicerca,numDiRicerca);
	
			for( int sotto : risorseDiSottocategoria) 
				risultato.add(sotto);
		}
		
		 assert invariante() && thisPre == this ;
		return risultato;
	}
/**
 * chiede all'utente di scegliere una risorsa all'interno di una sottocategoria
 * @return l'id della risorsa scelta o -1
 * @pre true
 * @post @nochange
 */
	/*int scegliRisorsaSottoCategoria(int i) {
		assert invariante();
		
		
	
		return sottocategorie.get(i).scegliRisorsa();
	}*/
/**
 * cerca la risorsa voluta all'interno della categoria
 * @param risorsaScelta 
 * @return true se la risorsa è presente nella categoria
 * @pre	true
 * @post @nochange
 */
	public boolean cercaRisorsa(int risorsaScelta) {
		assert invariante();
		CategoriaPrimoLivello<T> thisPre = this;
		
		boolean trovato = false;
		if (hasRisorse()) {
			trovato=super.cercaRisorsa(risorsaScelta);
		} else {
			for (int i = 0; i < sottocategorie.size(); i++) {
				if (sottocategorie.get(i).cercaRisorsa(risorsaScelta)) 
					trovato = true;
			}
		}
		assert invariante() && thisPre==this;
		return trovato;
	}
/**
 * getter
 * @return durata massima del prestito
 * @pre true
 * @post @nochange
 */
	public int getDurataMassimaPrestito() {
		return durataMassimaPrestito;
	}
/**
 * setter
 * @param durataMassimaPrestito nuovo valore dell'attributo
 * @pre durataMassimaPrestito>0
 * @post true
 */
	public void setDurataMassimaPrestito(int durataMassimaPrestito) {
	
		assert invariante() && durataMassimaPrestito>0;
		
		this.durataMassimaPrestito = durataMassimaPrestito;
		
		assert invariante();
	}
	/**
	 * getter
	 * @return durata massima della proroga per questa categoria
	 * @pre true
	 * @post @nochange
	 */
	public int getDurataMassimaProroga() {
		
		return durataMassimaProroga;
		}
	/**
	 * setter
	 * @param durataMassimaProroga nuovo valore dell'attributo
	 * @pre durataMassimaProroga>0
	 * @post true
	 */
	public void setDurataMassimaProroga(int durataMassimaProroga) {
		assert invariante() && durataMassimaProroga>0;
		
		this.durataMassimaProroga = durataMassimaProroga;
	
		assert invariante();
	}
	/**
	 * getter
	 * @return termine proroga
	 * @pre true
	 * @post @nochange
	 */
	public int getTermineProroga() {
		
		return termineProroga;
	}
	/**
	 * setter
	 * @param setTermineProroga nuovo valore dell'attributo
	 * @pre termineProroga>=0
	 * @post true
	 */
	public void setTermineProroga(int termineProroga) {
		assert invariante()&& termineProroga>=0;
		
		this.termineProroga = termineProroga;
		
		assert invariante();
	}
	/**
	 * getter
	 * @return numero massimo di risorse prestabili contemporaneamente
	 * @pre true
	 * @post @nochange
	 */
	public int getMaxRisorse() {
		
		return maxRisorse;
	}
	/**
	 * setter
	 * @param maxRisorse nuovo valore dell'attributo
	 * @pre maxRisorse>0
	 * @post true
	 */
	public void setMaxRisorse(int maxRisorse) {		
		assert invariante() && maxRisorse>0;
		
		this.maxRisorse = maxRisorse;
		
		assert invariante();
	}

	/**
	 * ritorna la descrizione della risorsa dato l'id oppure null se non la trova neanche nelle sue sottocategorie
	 * @param idRisorsa id della risorsa cercata
	 * @return stringa descrittiva o null
	 * @pre true
	 * @post @nochange
	 */
	public String getDescrizioneRisorsa(int idRisorsa) {
		assert invariante();
		CategoriaPrimoLivello<T> thisPre = this;
		
		String descrizione = null;
		String varAppoggio = null;
		
		if(hasSottoCategoria()) {
			for(Categoria<T> sottocategoria : sottocategorie) {

				varAppoggio = sottocategoria.getDescrizioneRisorsa(idRisorsa);
				if (varAppoggio!=null)  {
					descrizione=varAppoggio;
				}
			}
		} else {
			descrizione = super.getDescrizioneRisorsa(idRisorsa);
		}
		assert invariante() && thisPre==this;
		return descrizione;
	}
	
/**
 * chiedi all'utente di scegliere una risorsa all'interno della categoria
 * @return l'id della risorsa scelta
 * @pre true
 * @post @nochange
 */
	/*public int scegliRisorsa() {
		assert invariante();
		CategoriaPrimoLivello<T> thisPre = this;
		
		
	}/*
/**
 * cerca il nome della risorsa desiderata
 * @param risorsaScelta id della risorsa desiderata
 * @return stringa contenente il nome oppure null
 * @pre true
 * @post @nochange
 */
	public String getNomeRisorsa(int risorsaScelta) {
		assert invariante();
		CategoriaPrimoLivello<T> thisPre = this;
		
		String risultato = null;
		if(hasSottoCategoria()) {
			
			for(Categoria<T> sottocategoria : sottocategorie) {
				risultato=sottocategoria.getNomeRisorsa(risorsaScelta);

				if(risultato!=null) {
					assert invariante() && thisPre==this;
					return risultato;
				}
			}		
		}
		return super.getNomeRisorsa(risorsaScelta);
	}
/**
 * cerca in quale sottocategoria è contenuta la risorsa voluta	
 * @param risorsaScelta id della risorsa voluta
 * @return sottocategoria contenente oppure null
 * @pre true
 * @post @nochange
 */
	private Categoria trovaSottoCategoria(int risorsaScelta) {					
		assert invariante();
		CategoriaPrimoLivello<T> thisPre = this;
		
		int pos = -1;
		for (int i = 0; i < sottocategorie.size(); i++) {
			if (sottocategorie.get(i).cercaRisorsa(risorsaScelta)) pos = i;
		}
		
		if (pos != -1) {
			assert invariante() && thisPre==this;
			return sottocategorie.get(pos);
		} 
		else {
			assert invariante() && thisPre==this;
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
		assert invariante();
		CategoriaPrimoLivello<T> thisPre = this;
		
		if(hasSottoCategoria()) {
			Categoria<T> sottoc= trovaSottoCategoria(risorsaScelta);
				if(sottoc != null) {
					int risultato = sottoc.numeroLicenzeRisorsa(risorsaScelta);
					
					assert invariante() && thisPre==this;
					return risultato;
				}
		}else {
			int risultato = super.numeroLicenzeRisorsa(risorsaScelta);
			
			assert invariante() && thisPre==this;
			return risultato;
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
		assert invariante() && (flag==0 && numeroLicenzeRisorsa(risorsaScelta)>0 || flag==0 && numeroLicenzeRisorsa(risorsaScelta)==-1 || flag!=0 )  ;
		if(hasSottoCategoria()) {
			Categoria<T> sottoc= trovaSottoCategoria(risorsaScelta);
				if(sottoc != null)
					sottoc.aggiornaLicenze(risorsaScelta, flag);
		
		}else
			 super.aggiornaLicenze(risorsaScelta, flag);
	
		assert invariante();
	}
	/**
	 * ritorna l'indice massimo di risorsa presente nella categoria
	 * @pre true
	 * @post @nochange && @return>=-1
	 * @return risultato il valore dell'id più alto presente
	 */
	public int idMax() {
		assert invariante();
		CategoriaPrimoLivello<T> categoriaPre = this;
		
		int risultato=-1;
		
		if(this.hasSottoCategoria()) {
			for(Categoria<T> sottocategoria : sottocategorie) {
				int var = sottocategoria.idMax();
				if(var>risultato) risultato=var ;
			}
		}
		else risultato = super.idMax() ;
		
		assert invariante() && categoriaPre==this && risultato>=-1 ;
		return risultato;		
	}
	
	public ArrayList<Categoria<T>> getSottocategorie() {

		return sottocategorie;
	}

	
	public String showRisorsa(int id , int sottocategoria) {
	
	return sottocategorie.get(sottocategoria).showRisorsa(id);
}



	public void modifica(int id,String[] nuoveStringhe, int[] nuoviNumeri) {

		if(hasRisorse() && cercaRisorsa(id)) super.modifica(id,nuoveStringhe,nuoviNumeri);
	
		if(hasSottoCategoria()) trovaSottoCategoria(id).modifica(id,nuoveStringhe,nuoviNumeri);
}



	public void rimuoviRisorsa(int id, int sottocategoria) {

	sottocategorie.get(sottocategoria).rimuoviRisorsa(id);
	
}
	public String[] elencoRisorse(int s) {

		return sottocategorie.get(s).elencoRisorse();
	}
	public int scegliRisorsa(int s, int risorsaSelezionata) {

		return sottocategorie.get(s).scegliRisorsa(risorsaSelezionata);
	}
	public void aggiungiRisorsaSottoCategoria(int sottocategoria) {


		sottocategorie.get(sottocategoria).aggiungiRisorsa();
	}
	public int getId(int pos, int sottocategoria) {

		return sottocategorie.get(sottocategoria).getId(pos);
	}
	
	/**
	 * ritorna una lista degli attributi delle risorse contenute nella categoria
	 * @param id risorsa scelta
	 * @return lista dei nomi degli attributi
	 */
	public String[] getAttributiStringa(int id) {

		if(hasRisorse()) return super.getAttributiStringa(id);

		return trovaSottoCategoria(id).getAttributiStringa(id);
	}
	
	
	
	public String[] getAttributiNumerici(int id) {

		if(hasRisorse()) return super.getAttributiNumerici(id);		
		return trovaSottoCategoria(id).getAttributiNumerici(id);
	}
	


}