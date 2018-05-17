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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

@SuppressWarnings("serial")
public class Archivio implements Serializable {
	/**
	 * @invariant invariante()
	 */
	
	private static final String NOMEFILECATEGORIE = "Categorie.dat";
	private static final int DURATA_PRESTITO_LIBRI = 30;
	private static final int DURATA_PROROGA_LIBRI = 30;
	private static final int TERMINE_PROROGA_LIBRI = 3;
	private static final int MAX_RISORSE_PER_LIBRI= 3;
	private static final int DURATA_PRESTITO_FILM = 15;
	private static final int DURATA_PROROGA_FILM = 15;
	private static final int TERMINE_PROROGA_FILM = 2;
	private static final int MAX_RISORSE_PER_FILM= 2;
	private static final int ID_FILM= 1; //per identificare la categoria film
	private static final int ID_LIBRI= 0; //per identificare la categoria libri
	static final String NUOVO_PRESTITO = "NUOVO PRESTITO CONCESSO A : ";
	static final String PROROGA_PRESTITO = "PROROGA DEL PRESTITO A : ";
	
	private static Storico storico = new Storico();
	
	private static ArrayList<CategoriaPrimoLivello<? extends Risorsa>> categorie;
	
	private int attributoScelto;
	private ArrayList<Integer> match;
	
	/**
	 * istanzia la classe archivio con due categorie e due sottocategorie
	 * 
	 */
	public Archivio(){
		categorie = new ArrayList<CategoriaPrimoLivello<? extends Risorsa>>();
		aggiungiCategoria("libreria","Libri", DURATA_PRESTITO_LIBRI, DURATA_PROROGA_LIBRI, TERMINE_PROROGA_LIBRI, MAX_RISORSE_PER_LIBRI, ID_LIBRI); //inizializzazione di default
		aggiungiCategoria("videoteca","Film", DURATA_PRESTITO_FILM, DURATA_PROROGA_FILM, TERMINE_PROROGA_FILM, MAX_RISORSE_PER_FILM, ID_FILM); //inizializzazione di default
		
		categorie.get(0).aggiungiSottoCategoria("Giallo");
		categorie.get(0).aggiungiSottoCategoria("Romanzo");
		assert invariante();
	}
	/**
	 * verifica che le invarianti di classe siano mantenute
	 * @pre true
	 *@post @nochange
	 * @return true se sono verificate tutte le condizioni contemporaneamente
	 */
	protected boolean invariante() {
	
	if(	categorie!=null && categorie.size()>0 && storico!=null ) return true;
		
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
				
				for(CategoriaPrimoLivello c : categorie) {
					
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
		 

			CategoriaPrimoLivello categoriaPrimoLivello = categorie.get(scelta);

		
			return categoriaPrimoLivello.hasRisorse();
	 }
	 
	/* private void gestioneRisorse(int scelta) {


			CategoriaPrimoLivello categoriaPrimoLivello = categorie.get(scelta);

			categoriaPrimoLivello.gestioneRisorse();

		}*/
	 
	 public boolean categoriaHaSottoCategoria(int scelta) {

			CategoriaPrimoLivello categoriaPrimoLivello = categorie.get(scelta);

		 return categoriaPrimoLivello.hasSottoCategoria();
		}
	 
		public String[] elencoSottoCategorie(int scelta) {

			CategoriaPrimoLivello categoriaPrimoLivello = categorie.get(scelta);

			 return categoriaPrimoLivello.elencoSottoCategorie();
		}
		
	/*	public void usaSottoCategoria(int categoria,int sottocategoria) {

			CategoriaPrimoLivello categoriaPrimoLivello = categorie.get(categoria);

			categoriaPrimoLivello.usaSottoCategoria(sottocategoria);
			
		}*/


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
 * propaga il metodo azione di ricerca alle categorie dell'archivio
 * @param id 
 * @param eliMod
 * @pre id>=0 && eliMod>=0 
 * @post categorieSize()==categorieSize()@pre
 */
	/**
	 * usa showRisorsa(id) modifica(id) o rimuoviRisorsa(id) in base al parametro in ingresso 
	 * @param id parametro da usare
	 * @param eli_o_mod determina quale metodo usare con id come parametro
	 * 
	 */
	private void azioneDaRicerca(int id, int eliMod) {							
		assert invariante() && id>=0 && eliMod>=0;
		int categoriePre=categorie.size();
		
		
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
	ArrayList<Integer> filtraRisorse(int attributoScelto,String chiaveDiRicerca,int numDiRicerca) {
		assert invariante() &&attributoScelto>=0 && attributoScelto<=10 && chiaveDiRicerca!=null & numDiRicerca>=0 ;
		Archivio archivioPre = this;

		ArrayList<Integer> risultato = new ArrayList<Integer>();
	
		for(CategoriaPrimoLivello c : categorie) {
			
			if(c.hasRisorse()) {
				ArrayList<Integer> risorseDiCategoria = c.filtraRisorse(attributoScelto,chiaveDiRicerca,numDiRicerca);
				for(int ris : risorseDiCategoria)
					risultato.add(ris);
			}
			else  {
				
				if (c.hasSottoCategoria()) {
					ArrayList<Integer> risorseDiSottoCategoria = c.filtraSottoCategorie(attributoScelto,chiaveDiRicerca,numDiRicerca);
					for(int ris : risorseDiSottoCategoria) 
						risultato.add(ris);
					
				}	
			}
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
	public void aggiungiCategoria(String tipo,String nome, int durataMassimaPrestito, int durataProrogaLibri, int termineProroga, int maxRisorsePerLibri, int id) {
	assert  nome!= null && durataMassimaPrestito>=0 && durataProrogaLibri>=0 && termineProroga>=0 && maxRisorsePerLibri>0 && id>=0;
	int categoriePre = categorie.size();
	
	CategoriaPrimoLivello<?> nuovo = null;
	
	if(tipo.equals("libreria"))
		nuovo = new LibreriaContenitore(nome, durataMassimaPrestito, durataProrogaLibri, termineProroga, maxRisorsePerLibri, id);
	
	if(tipo.equals("videoteca"))
		nuovo = new VideotecaContenitore(nome, durataMassimaPrestito, durataProrogaLibri, termineProroga, maxRisorsePerLibri, id);

	categorie.add(nuovo);
		
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
		 ArrayList<CategoriaPrimoLivello<? extends Risorsa>> a = ( ArrayList<CategoriaPrimoLivello<? extends Risorsa>>)ServizioFile.caricaSingoloOggetto(f);
		
		if( a==null ) {
			assert invariante();
			return;
		}
		else 
			categorie=a; 
		
		storico.importaDati();
		
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
		
		File f = new File(NOMEFILECATEGORIE);
		ServizioFile.salvaSingoloOggetto(f, categorie);
		
		storico.salvaDati();
		
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
	private CategoriaPrimoLivello trovaCategoria(int risorsaScelta) {	
		assert invariante() && risorsaScelta>=0 ;
		Archivio archivioPre = this;
		
		int pos = -1;
		for (int i = 0; i < categorie.size(); i++) {
			if (categorie.get(i).cercaRisorsa(risorsaScelta))
				pos = i;
		}
		
		assert invariante() && archivioPre==this;
		
		if (pos != -1) {
			return categorie.get(pos);
		} else {
			return null;
		}
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
	 * @post storicoNoChange()
	 */
	public void idCorrente() {
		assert invariante() ;
		Storico storicoPre = storico ;
		
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
 * triggera la generazione evento di iscrizione nuovo fruitore nello storico
 * @param username del fruitore protagonista dell'evento
 * @pre username != null
 * @post storicoSize() == storicoSize()@pre +1
 */
	public void storiaIscrizioneFruitore(String username) {
		assert invariante() &&  username != null ;
		int storicoPre = Storico.size();
		
		storico.iscrizioneFruitore(username);	
		
		assert invariante() && storicoPre==Storico.size()-1 ;
	}
	/**
	 * triggera la generazione evento di decadimento di un fruitore nello storico
	 * @param username del fruitore protagonista dell'evento
	 * @pre username != null
	 * @post storicoSize() == storicoSize()@pre +1
	 */
	public void storiaFruitoreDecaduto(String username) {
		assert invariante() &&  username != null ;
		int storicoPre = Storico.size();
		
		storico.FruitoreDecaduto(username);	
	
		assert invariante() && storicoPre==Storico.size()-1 ;
	}
	/**
	 * triggera la generazione evento di rinnovo iscrizione nello storico
	 * @param username del fruitore protagonista dell'evento
	 * @pre username != null
	 * @post storicoSize() == storicoSize()@pre +1
	 */
	public void storiaRinnovoIscrizioneFruitore(String username) {
		assert invariante() &&  username != null ;
		int storicoPre = Storico.size();
		
		storico.RinnovoIscrizioneFruitore(username);		
		
		assert invariante() && storicoPre==Storico.size()-1 ;
	}
	/**
	 * ritorna la descrizione dello storico completo
	 * @return la descrizione
	 * @pre true
	 * @post @nochange && @return!=null
	 */
	public String getDescrizioneStorico() {
		assert invariante() ;
		Archivio archivioPre = this ;
		
		String risultato = storico.toString();
		
		assert invariante() && archivioPre == this && risultato!= null; 
		return risultato ;
	}
	/**
	 * triggera la generazione evento di nuovo prestito nello storico
	 * @param risorsaScelta risorsa prestata(id)
	 * @param numeroLicenzeRisorsa numero licenze rimaste della risorsa prestata
	 * @param username fruitore protagonista 
	 * @pre username != null && risorsaScelta>=0 && numeroLicenzeRisorsa
	 * @post (size() == size()@pre + 1 || size() == size()@pre +2)
	 */
	public void storiaNuovoPrestito(int risorsaScelta, int numeroLicenzeRisorsa, String username) {
		assert invariante() && username != null && risorsaScelta>=0 && numeroLicenzeRisorsa>=0 ;
		int storicoPre = Storico.size();
		
		storico.nuovoPrestito(risorsaScelta,numeroLicenzeRisorsa,username);	
		
		assert invariante() && (Storico.size() == storicoPre + 1 || Storico.size() == storicoPre +2);
	}
	/**
	 * triggera la generazione evento di proroga prestito nello storico
	 * @param username del fruitore protagonista dell'evento
	 * @param integer numero di proroga
	 * @pre username != null && integer >= 0
	 * @post storicoSize() == storicoSize()@pre +1
	 */
	public void prorogaPrestito(String username, Integer integer) {
		assert invariante() && username != null && integer >= 0;
		int storicoPre = Storico.size();
		
		storico.prorogaPrestito(username,integer);		
		
		assert invariante() && storicoPre==Storico.size()-1 ;
	}
	/**
	 * triggera la generazione evento di risorsa disponibile nello storico
	 * @param integer id della risorsa prestata
	 * @pre integer>=0
	 * @post storicoSize() == storicoSize()@pre +1
	 */
	public void risorsaDisponibile(Integer integer) {
		assert invariante() && integer>=0 ;
		int storicoPre = Storico.size() ;
		
		storico.risorsaDisponibile(integer);
		
		assert invariante()  && storicoPre+1 == Storico.size();
	}
/**
 * stampa a video il risultato della statistica riguardo ad un evento voluto 
 * @param evento sselezionato
 * @param descrizione stringa di descrizione dell'occorrenza
 * @pre eventoValido(nomeEvento) && descrizione!= null
 * @post @nochange
 */
	public String numEventoAnnoSolare(String evento ,String descrizione) {

		return storico.numEventoAnnoSolare(evento,descrizione);		
		
	}
	/**
	 * stampa a video il risultato della statistica riguardo alla risorsa più prestata
	 * @pre true
	 * @post @nochange
	 */
	public String risorsaPiuPrestata() {

		return storico.risorsaPiuPrestata();
		
	}
	/**
	 * stampa a video il risultato della statistica riguardo ai prestiti per fruitore per anno solare
	 * @pre true
	 * @post @nochange
	 */
	public String prestitiFruitoriAnnoSolare() {

		return storico.prestitiFruitoriAnnoSolare();
		

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
	public void modifica(int id, int c) {


		categorie.get(c).modifica(id);
	}
	public void rimuoviRisorsa(int id, int c) {


		categorie.get(c).rimuoviRisorsa(id);
	}
	public int numeroSottocategorie(int c) {

		return categorie.get(c).numeroSottoCategorie();
	}
	public String showRisorsa(int id, int c, int s) {
		return categorie.get(c).showRisorsa(id,s);
	}
	public void modifica(int id, int c, int s) {

		categorie.get(c).modifica(id,s);		
	}
	public void rimuoviRisorsa(int id, int c, int s) {

		categorie.get(c).rimuoviRisorsa(id,s);		
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
	/*public void gestioneRisorseCategoria(int scelta) {


		categorie.get(scelta).gestioneRisorse();
		

	
	}*/
	public void aggiungiRisorsa(int categoria) {


		categorie.get(categoria).aggiungiRisorsa();
	}
	public int getId( int pos, int categoria) {

		return categorie.get(categoria).getId(pos);
	}
	public void modificaRisorsa(int id, int categoria) {


		categorie.get(categoria).modifica(id);
	}
	public void aggiungiRisorsa(int categoria, int sottocategoria) {

		categorie.get(categoria).aggiungiRisorsaSottoCategoria(sottocategoria);
	}
	public int getId(int pos, int categoria, int sottocategoria) {

		return categorie.get(categoria).getId(pos,sottocategoria);
		
	}
	public void modificaRisorsa(int id, int categoria, int sottocategoria) {

		categorie.get(categoria).modifica(id, sottocategoria);
	}

	
	
}
