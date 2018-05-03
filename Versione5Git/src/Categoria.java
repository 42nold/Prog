import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;

@SuppressWarnings("serial")
public abstract class Categoria implements Serializable {

	/**
	 * @invariant invariante()
	 */
	private static final String TITOLO_RISORSE = "Scegli la risorsa da selezionare";
	private static final String TITOLO_MENU_GESTIONERISORSA= "Opzioni disponibili";
	private static final String[] OPZIONI = {"visualizza risorse","aggiungi risorsa","elimina risorsa","modifica risorsa"} ;
	
	protected String nome;
	protected ArrayList<Libro> libri;
	protected ArrayList<Film> film;
	static int idRisorsa;														
	protected int idCategoria;
	
	/**
	 * definisce le azioni generali di un costruttore Categoria
	 * @param param nome della categoria
	 * @pre param != null
	 * @post libri!=null && film!=null
	 * 
	 */
	public Categoria(String param) {
		assert param!=null;
		
		nome= param;
		libri= new ArrayList<Libro>();
		film= new ArrayList<Film>();
		
		assert invarianteC() && (libri!=null && film!=null) ;
	}
/**
 * 	cerca l'id risorsa pi� alto tra le risorse della categoria
 * @pre true
 * @post @nochange
 * @return il valore dell'id trovato
 */
	protected int idMax() {
		assert invarianteC();
		Categoria categoriaPre = this;
		
		int idLibro, idFilm;
		idLibro=idMaxLibro();
		idFilm=idMaxFilm();
		
		assert invarianteC() && categoriaPre==this;
		
		if(idLibro>idFilm)
			return idLibro;
		
		if(idFilm>=idLibro)
			return idFilm;
		
		return -1;
	}
	/**
	 * cerca nelle risorse libro l'indice di risorsa massimo
	 * @pre true
	 * @post @nochange
	 * @return il valore dell'id risorsa pi� alto trovato oppure -1
	 */
	private int idMaxLibro() {	

		assert invarianteC();
		Categoria categoriaPre = this;
		
		
		if (libri.size() > 0) {
			int max=libri.get(0).getId();
			
			for(int i=1; i<libri.size();i++)
				
				if(libri.get(i).getId()>max) {
					max=libri.get(i).getId();
				}
			
			assert invarianteC() && categoriaPre==this;
			return max;
		}
		
		assert invarianteC() && categoriaPre==this;
		return -1;			
	}
/**
 * 	cerca nelle risorse film l'indice di risorsa massimo
 * @pre true
 * @post @nochange
 * @return il valore dell'id risorsa pi� alto trovato
 */
	private int idMaxFilm() {
		assert invarianteC();
		Categoria categoriaPre = this;
		
		if (film.size() > 0) {
			int max=film.get(0).getId();
			
			for(int i=1; i<film.size();i++)
				if(film.get(i).getId()>max) {
					max=film.get(i).getId();
				}

			assert invarianteC() && categoriaPre==this;
			return max;
		}
		
		assert invarianteC() && categoriaPre==this;
		return 0;			
	}
/**
 * chiede all'utente i dati necessari alla creazione di una risorsa e la crea
 * @pre true
 * @post (sizeLibri()==sizeLibri()@pre+1 || sizeFilm()==sizeFilm()@pre+1) && idRisorse()==idRisorse()@pre+1 
 * 	
 */
	public void aggiungiRisorsa() {		
		assert invarianteC();
		int libriPre = libri.size();
		int filmPre = film.size();
		int idPre = idRisorsa;
		
		String nome= InputDati.leggiStringaNonVuota("inserisci il nome della risorsa");
		String gen= InputDati.leggiStringaNonVuota("inserisci il genere della risorsa");
		String lin= InputDati.leggiStringaNonVuota("inserisci la lingua della risorsa");
		int anno = InputDati.leggiInteroPositivo("inserisci l'anno di uscita");
		int numLicenze = InputDati.leggiInteroPositivo("inserisci il numero di licenze");
		
		switch (idCategoria) {
			case 0:
				aggiungiLibro(nome, gen, lin, anno, numLicenze);
				break;
			case 1:
				aggiungiFilm(nome, gen, lin, anno, numLicenze);
				break;
			default:
				break;
		}
		
		assert invarianteC() && (libri.size()==libriPre+1 || film.size()==filmPre+1) && idRisorsa==idPre+1 ;
	}
/**
 * aggiungi una risorsa libro alla categoria 	
 * @param nome nome del libro
 * @param gen genere
 * @param lin lingua
 * @param anno anno di pubblicazione
 * @param numLicenze numero di licenze del libro
 * @pre nome!=null && gen!=null && lin!=null && anno>=0 && numLicenze>=0
 * @post idRisorse()==idRisorse()@pre+1 && sizeLibri()==sizeLibri()@pre+1 
 */
	protected void aggiungiLibro(String nome, String gen, String lin,int anno, int numLicenze) {	
		assert invarianteC() && nome!=null && gen!=null && lin!=null && anno>=0 && numLicenze>=0;
		int idPre = idRisorsa;
		int libriSizePre = libri.size();
		
		String aut= InputDati.leggiStringaNonVuota("inserisci l'autore del libro");
		String casa= InputDati.leggiStringaNonVuota("inserisci la casa editrice del libro");
		int pagine = InputDati.leggiInteroPositivo("inserisci il numero di pagine");
		int id = idRisorsa;
		idRisorsa++;
	
		libri.add(new Libro(nome,aut,casa,gen,lin,anno,pagine,id,numLicenze));
		Storico.risorsaAggiunta(id);
		
		assert invarianteC() && idPre+1==idRisorsa && libri.size()==libriSizePre+1;	
	}
/**
 * aggiunge un film alla categoria	
 * @param nome
 * @param gen genere
 * @param lin lingua
 * @param anno anno di uscita
 * @param numLicenze numero di licenze
 * @pre nome!=null && gen!=null && lin!=null && anno>=0 && numLicenze>=0
 * @post idRisorse()==idRisorse()@pre+1 && numFilm()==numFilm()@pre+1 
 */
	protected void aggiungiFilm(String nome, String gen, String lin, int anno,int numLicenze) {	
		assert invarianteC() && nome!=null && gen!=null && lin!=null && anno>=0 && numLicenze>=0 ;
		int idPre = idRisorsa;
		int filmSizePre = film.size();
		
		String regista= InputDati.leggiStringaNonVuota("inserisci il regista del film");
		String casa= InputDati.leggiStringaNonVuota("inserisci la casa di produzione del film");
		int durata = InputDati.leggiInteroPositivo("inserisci la durata del film in minuti");
		int id = idRisorsa;
		idRisorsa++ ;
		
		film.add(new Film(nome,regista,casa,gen,lin,anno,durata,id,numLicenze));
		Storico.risorsaAggiunta(id);
		
		assert invarianteC() && idPre+1==idRisorsa && film.size()==filmSizePre+1;
	}
	
/**ritorna un elenco dei nomi delle risorse presenti nella categoria
 * @pre true
 * @post @nochange && @return != null
 * @return elenco di nomi
 */
	public String[] elencoRisorse() {	
		assert invarianteC();
		Categoria thisPre = this ;
		
		switch (idCategoria) {
			case 0:
				String [] elencoLibri = elencoLibri();
				assert invarianteC() && thisPre==this && elencoLibri!=null;
				return elencoLibri;
				
			case 1:
				String[] elencoFilm = elencoFilm();
				assert invarianteC() && thisPre==this && elencoFilm!=null;
				return elencoFilm ;
				
			default:
				assert false ;
				return null;
				
		}
	}
/**
 * 	restituisce l'elenco dei nomi dei libri nella categoria
 * @pre true
 * @post @nochange && @return != null
 * @return elenco di nomi 
 */
	protected String[] elencoLibri() {	
		assert invarianteC();
		Categoria thisPre = this ;
		String[] risultato = new String[0];
		
		if (libri.size()<1) 
			return risultato;	
		else {
			String[] elenco = new String[libri.size()];
			
			int i=0;			
			for(Libro l : libri) {
				elenco[i]=l.toString();
				i++;					
			}
			assert invarianteC() && thisPre==this ;
			return elenco;
		}
	}
	/**
	 * 	restituisce l'elenco dei nomi dei film nella categoria
	 * @pre true
	 * @post @nochange && @return!= null
	 * @return elenco di nomi 
	 */
	protected String[] elencoFilm() {		
		assert invarianteC();
		Categoria thisPre = this ;
		
		String[] risultato = new String[0];
		if (film.size()<1) 
			return risultato;	
		else {
			String[] elenco = new String[film.size()];
			
			int i=0;			
			for(Film f : film) {
				elenco[i]=f.toString();
				i++;					
			}
			assert invarianteC() && thisPre==this && elenco!=null ;
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
 * usa showRisorsa(id) modifica(id) o rimuoviRisorsa(id) in base al parametro in ingresso 
 * @param id parametro da usare
 * @param eli_o_mod determina quale metodo usare con id come parametro
 * 
 */
	public void azioneDaRicerca(int id, int eli_o_mod) {	
		assert invarianteC();
		
		switch (eli_o_mod) {
			case 1:
				showRisorsa(id);
				assert invarianteC();
				break;
			case 2:
				modifica(id);
				assert invarianteC();
				break;
			case 3:
				rimuoviRisorsa(id);
				assert invarianteC();
				break;
			default:
				break;
		}	
	}
/**	 
 * stampa su console la risorsa corrispondente all'id immesso come parametro
 * @pre true
 * @post @nochange
* @param id della risorsa da visualizzare
*/
	protected void showRisorsa(int id) {
		assert invarianteC();
		Categoria thisPre = this;
		
				if(libri.size()>0)
					for(Libro l : libri) 
						if(l.getId()==id) System.out.println(l.toString());
			
				if(film.size()>0)
					for(Film f : film) 
						if(f.getId()==id) System.out.println(f.toString());
		
		assert invarianteC() && thisPre==this;
	}
/**
 * chiama modifica() sulla risorsa che corrisponde all'id immesso come parametro	
 * @param idRisorsaDaModificare
 * @pre true
 * @post sizeLibri()==sizeLibri()@pre && sizeFilm()==sizeFilm()@pre
 */
	protected void modifica(int idRisorsaDaModificare) {	
		assert invarianteC();
		int libriPre = libri.size();
		int filmPre = film.size();
		
				if(libri.size()>0)
					for(Libro l : libri)
						if (l.getId() == idRisorsaDaModificare) 
							l.modifica();
			
				if(film.size()>0)
					for(Film f : film)
						if (f.getId() == idRisorsaDaModificare) 
							f.modifica();
				
		assert invarianteC() && libriPre==libri.size() && filmPre==film.size();
	}
/**
 * rimuove la risorsa che possiede l'id specificato dal parametro in ingresso	
 * @param indice dell'id
 * @true true 
 * @post librisize()+filmsize()==librisize()@pre+filmsize()@pre-1 || @nochange
 */
	protected void rimuoviRisorsa(int indice) {		
		assert invarianteC();
		Categoria thisPre = this;
		int libriPre = libri.size();
		int filmPre = film.size();
		
		switch (idCategoria) {
			case 0:
				for(int i=0; i <libri.size();i++)
					if (libri.get(i).getId() == indice) {
						Storico.risorsaEliminata(libri.get(i).getId());
						libri.remove(i);
					}
				assert invarianteC() && (libriPre+filmPre==libri.size()+film.size()-1 || thisPre==this);
				break;
			case 1:
				for(int i=0; i <film.size();i++)
					if (film.get(i).getId() == indice) {
						Storico.risorsaEliminata(film.get(i).getId());
						film.remove(i);
						}
				assert invarianteC() && (libriPre+filmPre==libri.size()+film.size()-1 || thisPre==this);
				break;
			default:
				assert false;
				break;
		}
	}
	
	/**
	 * chiede all'utente di Selezionare una risorsa e ritorna l'id di essa
	 * @return l'id della risorsa selezionata oppure -1
	 * @pre true
	 * @post @nochange
	 */
 	public int scegliRisorsa() {
 		assert invarianteC();
 		Categoria thisPre = this;
 		
		MyMenu menuRisorse = new MyMenu(TITOLO_RISORSE, this.elencoRisorse());
		int risorsaSelezionata = menuRisorse.scegli();
		if (risorsaSelezionata==0)
			return -1;
		
			switch(idCategoria) {
			case 0:
				int risultatoLibri=  libri.get(risorsaSelezionata-1).getId() ;
				assert invarianteC() && thisPre==this;
				return risultatoLibri;
			case 1:
				int risultatoFilm = film.get(risorsaSelezionata-1).getId();
				assert invarianteC() && thisPre==this;
				return risultatoFilm;	
			default:
				assert false;
			}
		assert invarianteC() && thisPre==this;
		return -1;
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
 		Categoria thisPre = this;
 		
		boolean trovato = false;
		
		for (int i = 0; i < libri.size(); i++) {
			if (libri.get(i).getId()==risorsaScelta)
				trovato = true;
		}
		
		for ( int i = 0; i < film.size(); i++) {
			if (film.get(i).getId()==risorsaScelta) 
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
 		Categoria thisPre = this;
 		
		if(libri.size()>0) {
			
			for(Libro l : libri) {
				if(l.getId()==idRisorsa) {
					String descrizioneLibro = l.descrizionePrestito();	
					assert invarianteC() && thisPre==this;
					return 	descrizioneLibro;
				}
			}
		}			
		if(film.size()>0) {
			for(Film f : film) {
				if(f.getId()==idRisorsa) {
					String descrizioneFilm = f.descrizionePrestito();
					assert invarianteC() && thisPre==this;
					return descrizioneFilm;
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
 		Categoria thisPre = this;
 		
		if(libri.size()>0) {
			for(Libro l : libri) {
				if(l.getId()==id) {
					String descrizionelibro = l.getNome();
					assert invarianteC() && thisPre==this;
					return descrizionelibro;
					}
			}
		}				

		if(film.size()>0) {
			for(Film f : film) {
				if(f.getId()==id) {
					String descrizioneFilm = f.getNome();
			     	assert invarianteC() && thisPre==this;
					return descrizioneFilm;
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
 		Categoria thisPre = this;
 		
				for(Libro l: libri)
					if(l.getId()==risorsaScelta) {
						int licenzeLibro = l.getNumLicenze();
						assert invarianteC() && thisPre==this;
						return licenzeLibro;
					}

			
				for(Film f: film)
					if(f.getId()==risorsaScelta) {
						int licenzeFilm = f.getNumLicenze();
						assert invarianteC() && thisPre==this;
						return licenzeFilm;
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
				for(Libro l: libri)
					if(l.getId()==risorsaScelta)
						if (flag==0)
							l.decrementaLicenze();
						else
							l.incrementaLicenze();
			
				for(Film f: film)
					if(f.getId()==risorsaScelta)
						if (flag==0)
							f.decrementaLicenze();
						else
							f.incrementaLicenze();
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
		Categoria thisPre = this;
		
				ArrayList<Integer> r = new ArrayList<Integer>();
				r= filtraLibri(attributoScelto, chiaveDiRicerca, numDiRicerca);
				r.addAll(filtraFilm(attributoScelto, chiaveDiRicerca, numDiRicerca));
				
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
	protected ArrayList<Integer> filtraLibri(int attributoScelto, String chiaveDiRicerca,int numDiRicerca) {		
		assert invarianteC() && chiaveDiRicerca!=null && attributoScelto>=1 && attributoScelto<=8;
		Categoria thisPre = this;
		
		ArrayList<Integer> risultato = new ArrayList<Integer>();
		
		if (libri.size()>0)
			switch(attributoScelto) {
		
				default : break;
				
				case 1:
					for(Libro l: libri)
						if(l.matchNome(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 2:
					for(Libro l: libri)
						if(l.matchAutore(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 3:
					for(Libro l: libri)
						if(l.matchCasaEditrice(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 4:
					for(Libro l: libri)
						if(l.matchGenere(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 5:
					for(Libro l: libri)
						if(l.matchLingua(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 6:
					for(Libro l: libri)
						if(l.matchAnno(numDiRicerca)) risultato.add(l.getId());
					break;
				case 7:
					for(Libro l: libri)
						if(l.matchPagine(numDiRicerca)) risultato.add(l.getId());
					break;		
				}	
			assert invarianteC() && thisPre==this;
			return risultato;
	}	
	/**
	 * 	raggruppa tutti i film che fanno match con i requisiti richiesti
	 * @param attributoScelto attributo da confrontare
	 * @param chiaveDiRicerca stringa da confrontare con l'attributo
	 * @param numDiRicerca intero da cofrontare con l'attributo
	 * @return lista di identificativi relativi ai match positivi
	 * @pre chiaveDiRicerca!=null && attributoScelto>=1 && attributoScelto<=8
	 * @post @nochange
	 */
	protected ArrayList<Integer> filtraFilm(int attributoScelto, String chiaveDiRicerca,int numDiRicerca) {	
		assert invarianteC() && chiaveDiRicerca!=null && attributoScelto>=1 && attributoScelto<=8&& (!chiaveDiRicerca.equals("") || numDiRicerca>0);
		Categoria thisPre = this;
		
		ArrayList<Integer> risultato = new ArrayList<Integer>();
		
		if(film.size()>0)
			switch(attributoScelto) {
		
				default : break;
				
				case 1:
					for(Film f: film)
						if(f.matchNome(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 2:
					for(Film f: film)
						if(f.matchRegista(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 3:
					for(Film f: film)
						if(f.matchCasaProduzione(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 4:
					for(Film f: film)
						if(f.matchGenere(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 5:
					for(Film f: film)
						if(f.matchLingua(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 6:
					for(Film f: film)
						if(f.matchAnno(numDiRicerca)) risultato.add(f.getId());
					break;
				case 8:
					for(Film f: film)
						if(f.matchDurata(numDiRicerca)) risultato.add(f.getId());
					break;		
			}
		assert invarianteC() && thisPre==this;	
		return risultato;
	}	
/**
 * restituisce l'id della risorsa indicata	
 * @param posArray posizione della risorsa nell'array di categoria
 * @return id della risorsa
 * @pre (idCategoria()==0 && posArray>=0 && posArray<=librisize() )|| (idCategoria()==1 && posArray>=0 && posArray<=filmsize() )
 * @post @nochange
 */
	protected int getId(int posArray) {
		assert invarianteC() && (idCategoria==0 && posArray>=0 && posArray<=libri.size()) || (idCategoria==1 && posArray>=0 && posArray<=film.size())  ;
		Categoria thisPre = this;
		
		switch(idCategoria) {
			case 0:
				int idLibro = libri.get(posArray).getId();
				assert invarianteC() && thisPre==this;
				return idLibro;
			case 1:
				int idFilm = film.get(posArray).getId();
				assert invarianteC() && thisPre==this;
				return idFilm;	
			default:
				assert false;
				return -1;
		}
	}
	
/**
 * chiede all'utente e attua le opzioni disponibili per le risorse
 * @pre true 
 * @post true
 */
		public void gestioneRisorse() {																
			assert invarianteC();
			MyMenu menu_risorse = new MyMenu(TITOLO_MENU_GESTIONERISORSA, OPZIONI);
		
			boolean esciMenu = false;
		
			while(!esciMenu) {
		
				int scelta = menu_risorse.scegli();
			
				if (scelta == 0 ) esciMenu=true;		
				
				String[] elenco = elencoRisorse();
				if(elenco==null && scelta!=2) {
					System.out.println("Nessuna risorsa presente"); 
					
					assert invarianteC();
					return; 			
				}
				
				switch(scelta) {
				
					case 1:
						
						System.out.println("Risorse contenute in " + nome + " :" );				
						
						for (int i=0; i<elenco.length; i++) {
							
							System.out.println( (i+1) + ") " +elenco[i]);
						}
						
						System.out.println();		
						break;
						
					case 2:
						
						aggiungiRisorsa();
						break;
					
					case 3:
						MyMenu menu_eliminaRisorse = new MyMenu("Scegli la risorsa da eliminare", elenco);
						
					    int risorsaDaEliminare = menu_eliminaRisorse.scegli();
						
						if (risorsaDaEliminare==0) break;
						
						//risorsaDaEliminare -1 rappresenta l'attuale posizione nell'array della risorsa
						rimuoviRisorsa(getId(risorsaDaEliminare-1));		
						break;
						
					case 4:
						
						MyMenu menu_modificaRisorse = new MyMenu("Scegli la risorsa da modificare", elenco);
					
						int risorsaDaModificare = menu_modificaRisorse.scegli();
							
						if (risorsaDaModificare==0) break;
						
						//risorsaDaModificare -1 rappresenta l'attuale posizione nell'array della risorsa
						modifica(getId(risorsaDaModificare-1));
						
						break;
						
					default:
						break;				
				}
			}
			assert invarianteC();
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
		Categoria thisPre = this;
		
		if(libri.size()>0) {
			for(Libro l : libri) {
				if (l.getId() == idRisorsa) {
					assert invarianteC() && thisPre==this;
					return 0;
					}
			}
		}
		if(film.size()>0) {
			for(Film f : film) {
				if (f.getId() == idRisorsa) {
					assert invarianteC() && thisPre==this;
					return 1;
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
		Categoria categoriaPre = this;
		
		boolean invariante=false;
		if(getNome()==nome && nome!=null && idRisorsa>=0 && (idCategoria==0 || idCategoria==1) && (libri.size()==0||film.size()==0) && libri!=null && film!=null )invariante=true;
		
		assert categoriaPre==this;
		
		return invariante;
	}
}

