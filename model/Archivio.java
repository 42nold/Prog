package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observer;
import java.util.Vector;

import javax.swing.text.View;

import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.ServizioFile;
import it.unibs.ing.mylib.Stampa;
import risorse.Categoria;
import risorse.CategoriaPrimoLivello;
import risorse.Libreria;
import risorse.LibreriaContenitore;

import risorse.VideotecaContenitore;
import storico.Storico;
import utility.Load;
import utility.Save;


@SuppressWarnings("serial")
public class Archivio implements Serializable {
	/**
	 * @invariant invariante()
	 */
	
	private static final String NOMEFILECATEGORIE = "Categorie.dat";	
	
	private static final int ID_FILM= 1; //per identificare la categoria film
	private static final int ID_LIBRI= 0; //per identificare la categoria libri
	static final String NUOVO_PRESTITO = "NUOVO PRESTITO CONCESSO A : ";
	static final String PROROGA_PRESTITO = "PROROGA DEL PRESTITO A : ";
	
	
	private static ArrayList<CategoriaPrimoLivello> categorie;
	
	private int attributoScelto;
	private ArrayList<Integer> match;
	private Save save;
	private Load load;
	
	/**
	 * istanzia la classe archivio con due categorie e due sottocategorie
	 * 
	 */
	public Archivio(){
		categorie = new ArrayList<CategoriaPrimoLivello>();
		categorie.add(new LibreriaContenitore("libreria", ID_LIBRI));//inizializzazione di default
		categorie.add(new VideotecaContenitore("videoteca", ID_FILM)); 

		//idCorrente();
		assert invariante();
	}
	/**
	 * verifica che le invarianti di classe siano mantenute
	 * @pre true
	 *@post @nochange
	 * @return true se sono verificate tutte le condizioni contemporaneamente
	 */
	protected boolean invariante() {
	
	if(	categorie!=null && categorie.size()>0 ) return true;
		
	return false;
	}
	/**
	 * ritorna un  vettore di stringhe con i nomi delle categorie
	 * @pre true
	 * @post @nochange 
	 * @return vettore contenente una stringa per ogni categoria
	 */
	public String[] elencoCategorie() {
		assert invariante();
		Archivio archivioPre = this;
		String[] elenco = new String[categorie.size()];
		
		if (categorie.size()<1) return null;
		
		else {
				int i=0;
				
				for(Categoria c : categorie) {
					
					elenco[i]=c.getNome();
					
					i++;
				}
		}
		assert invariante() && archivioPre==this;
		return elenco;
	}
	
	/**
	 * gestisce opzioni disponibili per una categoria: verifica se ci sono risorse o sottocategorie e ne mostra il relativo menu
	 * @param scelta la categoria scelta
	 * @pre scelta>=0 && scelta<categorieSize()
	 * @post categorieSize()@pre==categorieSize()
	 */
	 boolean haRisorseEsottocategorie(int scelta) {
		assert invariante() && scelta>=0 && scelta<categorie.size();
		
		int categoriePre = size();

		CategoriaPrimoLivello categoriaPrimoLivello = categorie.get(scelta);

		if (categoriaPrimoLivello.hasRisorse() && categoriaPrimoLivello.hasSottoCategoria())
		{
		assert invariante() && categoriePre==categorie.size();
		return true;
		}
		assert invariante() && categoriePre==categorie.size();
		return false;
	}

	 public boolean categoriaHaRisorse(int scelta) {
		 

			Categoria categoria = categorie.get(scelta);

		
			return categoria.hasRisorse();
	 }
	 
	 
	 public boolean categoriaHaSottoCategoria(int scelta) {

			CategoriaPrimoLivello categoriaPrimoLivello = categorie.get(scelta);

		 return categoriaPrimoLivello.hasSottoCategoria();
		}
	 
		public String[] elencoSottoCategorie(int scelta) {

			CategoriaPrimoLivello categoriaPrimoLivello = categorie.get(scelta);

			 return categoriaPrimoLivello.elencoSottoCategorie();
		}
		

/**
 * ricerca il nome di una risorsa avendo in ingresso l'id di essa in  tutto l'archivio
 * @param r l'id della risorsa
 * @return nome della risorsa
 * @pre id>=0
 * @post @nochange
 */
	String getNomeRisorsa(int r) {	
		assert invariante() && r>=0; 
		Archivio archivioPre = this;
		
		String risultato=null;
		for(CategoriaPrimoLivello categoriaPrimoLivello : categorie) {
			risultato=categoriaPrimoLivello.getNomeRisorsa(r);

			if (risultato!=null) return risultato;
		}
		
		assert invariante() && archivioPre==this;
		return risultato;
	}


	/**
	 * filtra tutte le risorse che rispecchiano i parametri immessi in un unico array : può ritornare null!
	 * @param attributoScelto attributo da confrontare
	 * @param chiaveDiRicerca stringa da confrontare
	 * @param numDiRicerca intero da confrontare
	 * @return lista di identificativi delle risorse che fanno match
	 * @pre attributoScelto>=0 && attributoScelto<=attributiSize() && chiaveDiricerca!=null & numDiRicerca!=null
	 * @post @nochange
	 */
	ArrayList<Integer> filtraRisorse(Object parametroDiRicerca,int attributoScelto) throws ClassCastException{
		//assert invariante() &&attributoScelto>=0 && attributoScelto<=10 && chiaveDiRicerca!=null & numDiRicerca>=0 ;
		Archivio archivioPre = this;

		ArrayList<Integer> risultato = new ArrayList<Integer>();
	
		for(Categoria c : categorie) {
		
			ArrayList<Integer> risorse = c.filtraRisorse(attributoScelto,parametroDiRicerca);
			for(int ris : risorse)
				risultato.add(ris);
			
		}
		
		assert invariante() && archivioPre==this;
		return risultato;
	}
/**
 * invoca il costruttore di CategoriaPrimoLivello usando i parametri in ingresso e la aggiunge all'archivio
 * @param nome
 * @param durataMassimaPrestito
 * @param durataProrogaLibri
 * @param termineProroga
 * @param maxRisorsePerLibri
 * @param id
 * @pre nome!= null && durataMassimaPrestito>=0 && durataProrogaLibri>=0 && termineProroga>=0 && maxiRisorseLibri>0 && id>=0
 * @post categorieSize() == categorieSize()@pre+1
 */
	public void aggiungiCategoriaPrimoLivello(String tipo,String nome, int id) {
		assert  nome!= null &&  id>=0;
		int categoriePre = categorie.size();
	
		
		CategoriaPrimoLivello<?> nuovo = null;
		
		if(tipo.equals("libreria"))
			 nuovo = new LibreriaContenitore(nome, id);
		
		if(tipo.equals("videoteca"))
			nuovo = new VideotecaContenitore(nome, id);

		if(nuovo!=null)	categorie.add(nuovo);
		
		assert invariante() && categoriePre==categorie.size()-1;
	}
/**
 * rimuovi dall'archivio la categoria selezionata	
 * @param indice della categoria da rimuovere
 * @pre categorieSize()>1
 * @post categorieSize() == categorieSize()@pre+1
 */
	public void eliminaCategoria(int indice) {
		assert invariante() && categorie.size()>1 ;
		int categoriePre = categorie.size();
		
		categorie.remove(indice);	
		
		assert invariante() && categoriePre == categorie.size()+1;
	}
	
	/**
	 * verifica se l'archivio è vuoto
	 * @return true se è vuoto
	 * @pre true
	 * @post @nochange
	 */
	public boolean vuoto() {
		assert invariante();

		if(categorie.size()<1) 
			return true;
		
				return false;
	}
	
	/**
	 * ritorna il numero di categorie in archivio
	 * @return  numero di categorie in archivio
	 * @pre true
	 * @post @nochange
	 */
	public int size() {
		assert invariante();
		return categorie.size();
	}
	
	/**
	 * importa le categorie da file e lo storico
	 * @pre true
	 * @post true
	 */
	public void importaDati() {
		assert invariante();
		
		File f = new File(NOMEFILECATEGORIE);
		@SuppressWarnings("unchecked")
		 ArrayList<CategoriaPrimoLivello> a = ( ArrayList<CategoriaPrimoLivello>)ServizioFile.caricaSingoloOggetto(f);

		
		if( a==null ) {
			assert invariante();
			return;
		}
		else 
			categorie=a; 
				
		assert invariante();
	}
	
	/**
	 * salva categorie e storico su file
	 * @pre true
	 * @post @nochange
	 */
	public void salvaDati() {
		assert invariante();
		Archivio archivioPre = this;
		
		save.salvaDatiSuFile(NOMEFILECATEGORIE, categorie);
		
		
		assert invariante() && archivioPre == this;
	}
	
		
	/**
	 * Cerca la durata del prestito di una risorsa di una categoria.
	 * @param risorsa id della risorsa cercata
	 * @return Il numero di giorni del prestito.
	 * @pre risorsa>=0
	 * @post @nochange
	 */
	public int durataPrestitoDataUnaRisorsa(int risorsa) {			
		assert invariante() && risorsa>=0 ;
		Archivio archivioPre = this ;
		
		CategoriaPrimoLivello categoriaDellaRisorsa = trovaCategoria(risorsa);
		if (categoriaDellaRisorsa != null) {
			int risultato = categoriaDellaRisorsa.getDurataMassimaPrestito();
			
			assert invariante() && archivioPre == this;
			return risultato;
		} 
		else {
			assert invariante() && archivioPre == this;
			return -1;
		}
	}
/**
 * Cerca la durata massima della proroga di una risorsa scelta
 * @param risorsa id della risorsa cercata
 * @return numero della durata massima della proroga relativa
 * @pre risorsa>=0 
 * @post @nochange
 */
	public int durataProrogaDataUnaRisorsa(int risorsa) {		
		assert invariante() && risorsa>=0 ;
		Archivio archivioPre= this ;
		
		CategoriaPrimoLivello categoriaDellaRisorsa = trovaCategoria(risorsa);
		if (categoriaDellaRisorsa != null) {
			int risultato =  categoriaDellaRisorsa.getDurataMassimaProroga();
			
			assert invariante() &&  archivioPre == this ;
			return risultato ;
		}
		else {
			assert invariante() &&  archivioPre == this ;
			return -1;
		}
	}
/**
 * Cerca il termine della proroga di una risorsa scelta
 * @param risorsa id della risorsa cercata
 * @return il valore dell'attributo termina proroga
 * @true risorsa>=0 
 * @post @nochange
 */
	public int termineProrogaDataUnaRisorsa(int risorsa) {
		assert invariante() && risorsa>=0 ;
		Archivio archivioPre= this ;
		
		CategoriaPrimoLivello categoriaDellaRisorsa = trovaCategoria(risorsa);
		if (categoriaDellaRisorsa != null) {
			int risultato = categoriaDellaRisorsa.getTermineProroga();
			assert invariante() &&  archivioPre == this ;
			return risultato ;
		}
		else {
			assert invariante() &&  archivioPre == this ;
			return -1;
		}
	}

	/**
	 * Cerca il numero massimo di risorse di una categoria ottenibili in prestito.
	 * @param risorsaScelta La risorsa.
	 * @return Il numero massimo di risorse ottenibili in prestito.
	 * @pre risorsaScelta>=0 
	 * @post @nochange @return>=-1
	 */
	public int maxRisorsePerCategoriaDataUnaRisorsa(int risorsaScelta) {		
		assert invariante() && risorsaScelta>=0 ;
		Archivio archivioPre= this ;
		
		CategoriaPrimoLivello categoriaDellaRisorsa = trovaCategoria(risorsaScelta);
		if (categoriaDellaRisorsa != null) {
			int risultato = categoriaDellaRisorsa.getMaxRisorse();
			assert invariante() &&  archivioPre == this ;
			return risultato;
		} 
		else {
			assert invariante() &&  archivioPre == this ;
			return -1;
		}
	}
	
	/**
	 * Data una risorsa ne localizza la categoria.
	 * @param risorsaScelta La risorsa
	 * @return La categoria se la risorsa viene trovata oppure null se la risorsa non viene trovata.
	 * @pre risorsaScelta>=0 
	 * @post @nochange
	 */
	CategoriaPrimoLivello trovaCategoria(int risorsaScelta) {	
		assert invariante() && risorsaScelta>=0 ;
		
		int pos = trovaPosizioneCategoria(risorsaScelta);
		
		if (pos != -1) {
			return categorie.get(pos);
		} else {
			return null;
		}
	}
	
	/**
	 * cerca la posizione della categoria che contiene la risorsa indicata dall'id
	 * @param idRisorsa
	 * @return il numero della posizione della categoria oppure -1
	 */
	int trovaPosizioneCategoria(int idRisorsa) {
	 
		int pos = -1;
		for (int i = 0; i < categorie.size(); i++) {
			if (categorie.get(i).cercaRisorsa(idRisorsa))
				pos = i;
		}		
			return pos;
		}
	
/**
 * cerca la risorsa Scelta nell'archivio e ne ritorna la descrizione
 * @param risorsaScelta id della risorsa
 * @return descrizione della risorsa oppure null
 * @pre risorsaScelta>=0
 * @post @nochange
 */
	public String getDescrizioneRisorsa(int risorsaScelta) {	
		assert invariante() && risorsaScelta>=0 ;
		Archivio archivioPre = this;
		
		String descrizione =null;
		String varAppoggio=null;
		
	    for(CategoriaPrimoLivello categoriaPrimoLivello : categorie) {
			 varAppoggio=categoriaPrimoLivello.getDescrizioneRisorsa(risorsaScelta); 
			 if(varAppoggio!=null) {
				 descrizione = varAppoggio;
			 }
	    }

	    assert invariante() && archivioPre==this;
		return descrizione;
	}
/**
 * ritorna il nome della categoria contenente la risorsa in ingresso come parametro
 * @param risorsaScelta id della risorsa
 * @return il nome della categoria o null
 * @pre risorsaScelta>=0 
 * @post @nochange
 */
	public String trovaNomeCategoria(int risorsaScelta) {	
		assert invariante() && risorsaScelta>=0 ;
		Archivio archivioPre = this;
		
		CategoriaPrimoLivello c = trovaCategoria(risorsaScelta);
	
	    assert invariante() && archivioPre==this;

		if(c!=null) 
			return c.getNome();	
		
		return null;
	}
/**
 * 	cerca in archivio la risorsa scelta e ne restituisce il numero di licenze
 * @param risorsaScelta id della risorsa
 * @return numero di licenze oppure -1 se la risorsa non è trovata
 * @pre risorsaScelta>=0 
 * @post @nochange 
 */
	public int numeroLicenzeRisorsa(int risorsaScelta) {	
		assert invariante() && risorsaScelta>=0 ;
		Archivio archivioPre = this;
		
		CategoriaPrimoLivello c = trovaCategoria(risorsaScelta);
		if (c!=null) {
			int risultato = c.numeroLicenzeRisorsa(risorsaScelta);
		
			assert invariante() && archivioPre==this;
			return risultato ;
		}
	    assert invariante() && archivioPre==this;
		return -1;
	}
/**
 * aumenta o decrementa licenze della risorsa scelta	
 * @param risorsaScelta id della risorsa
 * @param flag o per decrementare 1 per aumentare
 * @pre flag==0 && numeroLicenzeRisorsa(risorsaScelta)>0 || flag==0 && numeroLicenzeRisorsa(risorsaScelta)=-1 || flag==1
 * @post size() == size()@pre
 */
	public void aggiornaLicenze(int risorsaScelta, int flag) {
		assert invariante() && ( flag==0 && numeroLicenzeRisorsa(risorsaScelta)>0 || flag==0 && numeroLicenzeRisorsa(risorsaScelta)==-1 || flag==1) ;
		int categoriePre = size() ;
		
		CategoriaPrimoLivello c = trovaCategoria(risorsaScelta);
		if (c!=null)
			c.aggiornaLicenze(risorsaScelta, flag);
	
	assert  invariante() && categoriePre == size() ;
	}
	/**
	 * cerca il massimo valore id tra le risorse dell'archivio e aggiorna il contatore di id
	 * @pre true
	 */
	/*public void setIdCorrente() {
		assert invariante() ;
		
		int maxIdCorrente;
		int maxIdNext;
		
		if(categorie.size()>0) {
			
			maxIdCorrente=categorie.get(0).idMax();
			for(int i=1; i< categorie.size(); i++) {
				maxIdNext=categorie.get(i).idMax();
				if (maxIdNext>maxIdCorrente)
					maxIdCorrente=maxIdNext;
			}	
			
			Categoria.setId(maxIdCorrente+1);  
		}

		assert invariante() && storicoPre == storico;
	}*/
	public void setIdCorrente() {
		Categoria.setId(0);
	}
	
	public int getIdCorrente() {
		return Categoria.getIdRisorsaCorrente();
	}
/**
 * 	cerca l'id della categoria che contiene la risorsa in ingresso
 * @param idRisorsa
 * @return id categoria
 * @pre idRisorsa>=0 
 * @post( @return==-1||@return==0||@return==1 )  && @nochange
 */
	public int trovaIdCategoria(int idRisorsa) {
		assert invariante() && idRisorsa>=0;
		Archivio archivioPre = this ;
		
		int idCat;
		for (CategoriaPrimoLivello c: categorie) {
			idCat=c.getIdCategoria(idRisorsa);
			if(idCat!=-1) {
				
				assert invariante() && archivioPre == this && ( idCat==0||idCat==1 );
				return idCat;
			}
		}
		assert invariante() && archivioPre== this;
		return -1;
	}


	/**
	 * chiama il metodo scegli risorsa della categoria voluta nell'archivio
	 * @param posizione della categoria
	 * @param posizione della sottocategoria
	 * @return id della risorsa selezionata
	 */
	int scegliRisorsa(int c, int risorsaSelezionata) {
		
		return categorie.get(c).scegliRisorsa(risorsaSelezionata);
	}
	
	
	/**
	 * chiama il metodo scegli risorsa della categoria voluta nell'archivio
	 * @param posizione della categoria
	 * @return id della risorsa selezionata
	 */
	/*public int scegliRisorsa(int i) {

		return 	categorie.get(i).scegliRisorsa();

	}*/
	public String showRisorsa(int id, int c) {
		return categorie.get(c).showRisorsa(id);
	}
/**
 * cerca la risorsa selezionata da id e categoria e se la trova la elimina ritornando true altrimenti false
 * @param id della risorsa
 * @param categoria della risorsa
 * @return true se la risorsa è stata eliminata
 */
	public boolean rimuoviRisorsa(int id, int categoria) {

		return categorie.get(categoria).rimuoviRisorsa(id);
			
	}
	public int numeroSottocategorie(int c) {

		return categorie.get(c).numeroSottoCategorie();
	}
	

	public String[] elencoRisorse(int c, int s) {

		return categorie.get(c).elencoRisorse(s);
	}
	public int scegliRisorsa(int c, int s, int risorsaSelezionata) {
		return categorie.get(c).scegliRisorsa(s,risorsaSelezionata);
	}
	public String[] elencoRisorse(int c) {

		return categorie.get(c).elencoRisorse();
	}
	
	protected void aggiungiRisorsa(ArrayList<Object> nuoviAttributi, int categoria) throws ClassCastException {


		//assert attributiStringaFinali!=null && attributiNumericiFinali != null && categoria >= 0;
		
		categorie.get(categoria).aggiungiRisorsaEAggiornaStorico(nuoviAttributi);
	}
	
	
	public int getId( int pos, int categoria) {

		return categorie.get(categoria).getIdRisorsa(pos);
	}
	
	
	public void modificaRisorsa(int id, int categoria, Object[] nuoviAttributi) throws ClassCastException{


		categorie.get(categoria).modifica(id, nuoviAttributi);
	}

	
	
	public int getId(int pos, int categoria, int sottocategoria) {

		return categorie.get(categoria).getId(pos,sottocategoria);
		
	}
	
	public ArrayList<String> getDescrizioneCampi(int categoria){
		return categorie.get(categoria).getDescrizioneCampi();
	}
	
	public void aggiungiRisorsa(ArrayList<Object> nuoviAttributi, int categoria, int sottocategoria) throws ClassCastException{

		categorie.get(categoria).aggiungiRisorsaEAggiornaStorico(nuoviAttributi,sottocategoria);
	}


	public ArrayList<String> getDescrizioneCampiRisorsa(int categoriaScelta){
		return categorie.get(categoriaScelta-1).getDescrizioneCampi();
	}

	
}
