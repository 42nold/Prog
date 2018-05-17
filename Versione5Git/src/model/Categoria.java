package model;

import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;

@SuppressWarnings("serial")
public abstract class Categoria<T extends Risorsa> implements Serializable {

	/**
	 * @invariant invariante()
	 */
	
	
	protected String nome;

	protected ArrayList<T> risorse;
	static int idRisorsa;														
	protected int idCategoria;
	
	/**
	 * definisce le azioni generali di un costruttore Categoria
	 * @param param nome della categoria
	 * @pre param != null
	 * @post libri!=null && film!=null
	 * 
	 */
	public  Categoria(String param ) {
		assert param!=null;
		
		nome= param;
	
		risorse=  new ArrayList<T>() ;
		
		assert invarianteC() && (risorse!=null ) ;
	}

	/**
	 * cerca nelle risorse libro l'indice di risorsa massimo
	 * @pre true
	 * @post @nochange
	 * @return il valore dell'id risorsa pi� alto trovato oppure -1
	 */
	protected int idMax() {	

		assert invarianteC();
		Categoria categoriaPre = this;
		
		
		if (risorse.size() > 0) {
			int max=risorse.get(0).getId();
			
			for(int i=1; i<risorse.size();i++)
				
				if(risorse.get(i).getId()>max) {
					max=risorse.get(i).getId();
				}
			
			assert invarianteC() && categoriaPre==this;
			return max;
		}
		
		assert invarianteC() && categoriaPre==this;
		return -1;			
	}

/**
 * chiede all'utente i dati necessari alla creazione di una risorsa e la crea
 * @pre true
 * @post (sizeLibri()==sizeLibri()@pre+1 || sizeFilm()==sizeFilm()@pre+1) && idRisorse()==idRisorse()@pre+1 
 * 	
 */
	public abstract void aggiungiRisorsa() ;


	
/**ritorna un elenco dei nomi delle risorse presenti nella categoria
 * @pre true
 * @post @nochange && @return != null
 * @return elenco di nomi
 */
	public String[] elencoRisorse() {	
		assert invarianteC();
		Categoria<T> thisPre = this ;
		
		String[] risultato = new String[0];
		
		if (risorse.size()<1) 
			return risultato;	
		else {
			String[] elenco = new String[risorse.size()];
			
			int i=0;			
			for(Risorsa l : risorse) {
				elenco[i]=l.toString();
				i++;					
			}
			assert invarianteC() && thisPre==this ;
			return elenco;
		}
	}


	/**
	 * restituisce il nome della classe
	 * @pre true
	 * @post @nochange
	 * @return il nome
	 */
	public String getNome() {	
		
		return nome;
	}




/**	 
 * stampa su console la risorsa corrispondente all'id immesso come parametro
 * @pre true
 * @post @nochange
* @param id della risorsa da visualizzare
*/
	
	protected String showRisorsa(int id) {
		assert invarianteC();
		Categoria<T> thisPre = this;
		
				if(risorse.size()>0)
					for(T l : risorse) 
						if(l.getId()==id) return l.toString();
			
					
		
		assert invarianteC() && thisPre==this;
		return "";
	}
/**
 * chiama modifica() sulla risorsa che corrisponde all'id immesso come parametro	
 * @param idRisorsaDaModificare
 * @param nuoviNumeri 
 * @param nuoveStringhe 
 * @pre true
 * @post sizeLibri()==sizeLibri()@pre && sizeFilm()==sizeFilm()@pre
 */
	public void modifica(int idRisorsaDaModificare, String[] nuoveStringhe, int[] nuoviNumeri) {	
		assert invarianteC();
		int libriPre = risorse.size();
		
		if(risorse.size()>0)
			for(T l : risorse)
				if (l.getId() == idRisorsaDaModificare) 
					{
					
					l.modifica(nuoveStringhe,nuoviNumeri);
					
					}
			
			
				
		assert invarianteC() && libriPre==risorse.size() ;
	}
/**
 * rimuove la risorsa che possiede l'id specificato dal parametro in ingresso	
 * @param id dell'id
 * @true true 
 * @post librisize()+filmsize()==librisize()@pre+filmsize()@pre-1 || @nochange
 */
	protected void rimuoviRisorsa(int id) {		
		assert invarianteC();
		Categoria<T> thisPre = this;
		int libriPre = risorse.size();
		
		
				for(int i=0; i <risorse.size();i++)
					if (risorse.get(i).getId() == id) {
						Storico.risorsaEliminata(risorse.get(i).getId());
						risorse.remove(i);
					}
				assert invarianteC() && (libriPre==risorse.size() || thisPre==this);
			
	}
	
	/**
	 * chiede all'utente di Selezionare una risorsa e ritorna l'id di essa
	 * @return l'id della risorsa selezionata oppure -1
	 * @pre true
	 * @post @nochange
	 */
 	public int scegliRisorsa(int risorsaSelezionata) {	
		
		
 		return 	  risorse.get(risorsaSelezionata).getId() ;
			
			
		
	}
/**
 * cerca se una risorsa nella categoria � presente
 * @param risorsaScelta id della risorsa da cercare
 * @pre true
 * @post @nochange
 * @return true se la risorsa � stata trovata
 */
	public boolean cercaRisorsa(int risorsaScelta) {	
		assert invarianteC();
 		Categoria<T> thisPre = this;
 		
		boolean trovato = false;
		
		for (int i = 0; i < risorse.size(); i++) {
			if (risorse.get(i).getId()==risorsaScelta)
				trovato = true;
		}
		
		
		assert invarianteC() && thisPre==this;
		return trovato;

	}
/**
 * restituisce la stringa descrittiva della risorsa cercata
 * @param idRisorsa id della risorsa cercata
 * @return stringa contenente la descrizione
 * @pre true
 * @post @nochange
 */
	public String getDescrizioneRisorsa(int idRisorsa) {							
		assert invarianteC();
 		Categoria<T> thisPre = this;
 		
		if(risorse.size()>0) {
			
			for(T l : risorse) {
				if(l.getId()==idRisorsa) {
					String descrizioneLibro = l.descrizionePrestito();	
					assert invarianteC() && thisPre==this;
					return 	descrizioneLibro;
				}
			}
		}			
		
		assert invarianteC() && thisPre==this;
		return null;
	}
/**
 * restituisce il nome della risorsa cercata	
 * @param id della risorsa cercata
 * @return stringa contenente il nome
 * @pre true
 * @post @nochange
 */
	public String getNomeRisorsa(int id) {											
		assert invarianteC();
 		Categoria<T> thisPre = this;
 		
		if(risorse.size()>0) {
			for(T l : risorse) {
				if(l.getId()==id) {
					String descrizionelibro = l.getNome();
					assert invarianteC() && thisPre==this;
					return descrizionelibro;
					}
			}
		}				

		
		assert invarianteC() && thisPre==this;
		return null;
		
	}
/**
 * 	restituisce il numero di licenze della risorsa cercata
 * @param risorsaScelta id della risorsa cercata
 * @return numero di licenze rimaste o -1
 * @pre true
 * @post post @nochange
 */
	public int numeroLicenzeRisorsa(int risorsaScelta) {							
		assert invarianteC();
 		Categoria<T> thisPre = this;
 		
				for(T l: risorse)
					if(l.getId()==risorsaScelta) {
						int licenzeLibro = l.getNumLicenze();
						assert invarianteC() && thisPre==this;
						return licenzeLibro;
					}

			
				

		     	assert invarianteC() && thisPre==this;
				return -1;		
	}
	/**
	 * incrementa o decrementa le licenze della risorsa prescelta
	 * @param risorsaScelta id della risorsa prescelta
	 * @param flag 0 per decrementare 1 per incrementare
	 * @pre flag==0 && numeroLicenzeRisorsa(risorsaScelta)>0 || flag==0 && numeroLicenzeRisorsa(risorsaScelta)=-1 || flag!=0
	 * @post true
	 */
	public void aggiornaLicenze(int risorsaScelta, int flag) {						
		assert invarianteC() && ((flag==0 && numeroLicenzeRisorsa(risorsaScelta)>0) || (flag==0 && numeroLicenzeRisorsa(risorsaScelta)==-1) || flag!=0) ;
				for(T l: risorse)
					if(l.getId()==risorsaScelta)
						if (flag==0)
							l.decrementaLicenze();
						else
							l.incrementaLicenze();
			
				
			assert invarianteC();
	}
/**
 * 	raggruppa tutte le risorse che fanno match con i requisiti richiesti
 * @param attributoScelto attributo nel quale si verificano i requisiti
 * @param chiaveDiRicerca stringa da confrontare con l'attributo
 * @param numDiRicerca intero da confrontare con lattributo
 * @return lista di id risorse che fanno match
 * @pre chiaveDiRicerca!=null && (!chiaveDiRicerca.equals("") || numDiRicerca >0)
 * @post @nochange
 */
	public ArrayList<Integer> filtraRisorse(int attributoScelto, String chiaveDiRicerca,int numDiRicerca) {			
		assert invarianteC() && chiaveDiRicerca!=null && (!chiaveDiRicerca.equals("") || numDiRicerca >0);
		Categoria<T> thisPre = this;
		
				ArrayList<Integer> r = new ArrayList<Integer>();
				r= filtra(attributoScelto, chiaveDiRicerca, numDiRicerca);
				
		assert invarianteC() && thisPre==this;
		return r;		
	}
/**
 * 	raggruppa tutti i libri che fanno match con i requisiti richiesti
 * @param attributoScelto attributo da confrontare
 * @param chiaveDiRicerca stringa da confrontare con l'attributo
 * @param numDiRicerca intero da cofrontare con l'attributo
 * @return lista di identificativi relativi ai match positivi
 * @pre chiaveDiRicerca!=null && attributoScelto>=1 && attributoScelto<=7
 * @post @nochange
 */
	protected  abstract ArrayList<Integer> filtra(int attributoScelto, String chiaveDiRicerca,int numDiRicerca) ;

/**
 * restituisce l'id della risorsa indicata	
 * @param posArray posizione della risorsa nell'array di categoria
 * @return id della risorsa
 * @pre (idCategoria()==0 && posArray>=0 && posArray<=librisize() )|| (idCategoria()==1 && posArray>=0 && posArray<=filmsize() )
 * @post @nochange
 */
	protected int getId(int posArray) {
		assert invarianteC() && (idCategoria==0 && posArray>=0 && posArray<=risorse.size()) || (idCategoria==1 && posArray>=0 )  ;
		Categoria<T> thisPre = this;
		
	
				int id = risorse.get(posArray).getId();
				assert invarianteC() && thisPre==this;
				return id;
		
	}
	

/**
 * setta l'id max delle risorse al valore immesso		
 * @param id nuovo valore dell'id max
 * @pre id>=0
 * @post idRisorsa()==id
 */
	public static void setId (int id) {
		assert  id>=0 ;
		idRisorsa=id;
	}
/**
 * restituisce l'id della categoria della risorsa data come parametro
 * @param idRisorsa 
 * @return id della categoria in questione o -1
 * @pre true
 * @post @nochange
 */
	public int getIdCategoria(int idRisorsa) {
		assert invarianteC();
		Categoria<T> thisPre = this;
		
		if(risorse.size()>0) {
			for(T l : risorse) {
				if (l.getId() == idRisorsa) {
					assert invarianteC() && thisPre==this;
					if(l.getClass()==new Libro("","","","","",1,1,1,1).getClass())        {return 0;}
					if(l.getClass()==new Film("", "", "", "", "", 1, 1, 1, 1).getClass()) {return 1;}
					}
			}
		}
	
		assert invarianteC() && thisPre==this;
		return -1;
	}
/**
 * verifica che le propriet� invarianti della classe siano mantenute 
 * @pre true
 * @post @nochange
 * @return true se i getNome() torna il valore corretto &&  gli attributi assumono una  combinazione di valori ammessi

 */
	protected  boolean invarianteC() {
		Categoria<T> categoriaPre = this;
		
		boolean invariante=false;
		if(getNome()==nome && nome!=null && idRisorsa>=0 && (idCategoria==0 || idCategoria==1) && (risorse.size()==0) && risorse!=null  )invariante=true;
		
		assert categoriaPre==this;
		
		return invariante;
	}

public String[] getAttributiStringa(int id) {

	if(cercaRisorsa(id)) return risorse.get(0).getAttributiStringa();
	
	return null;
}

public String[] getAttributiNumerici(int id) {

	if(cercaRisorsa(id)) return risorse.get(0).getAttributiNumerici();

	return null;
}
}

