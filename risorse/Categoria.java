package risorse;

import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;
import storico.Storico;

@SuppressWarnings("serial")
public abstract class Categoria<T extends Risorsa> implements Serializable {

	/**
	 * @invariant invariante()
	 */
	
	protected static final String ID="Id";
	protected static final String TITOLO="Titolo";
	protected static final String ANNO="Anno";
	protected static final String NUMEROLICENZE="Numero licenze";	
	
	protected ArrayList<String> descrizioneCampi;
	protected String nome;
	protected ArrayList<T> risorse;
	protected static int idRisorsa=0;														
	protected int idCategoria;
	private int durataMassimaPrestito;
	private int durataMassimaProroga;
	private int termineProroga;
	private int maxRisorse;
	
	/**
	 * definisce le azioni generali di un costruttore Categoria
	 * @param param nome della categoria
	 * @pre param != null
	 * @post libri!=null && film!=null
	 * 
	 */
	public  Categoria(String nome, int durataMassimaPrestito, int durataMassimaProroga, int termineProroga, int maxRisorse, int id) {		
		
		risorse = new ArrayList<T>();
		
		descrizioneCampi=new ArrayList<String>();
		
		this.nome= nome;
		this.durataMassimaPrestito = durataMassimaPrestito;
		this.durataMassimaProroga = durataMassimaProroga;
		this.termineProroga = termineProroga;
		this.maxRisorse = maxRisorse;
		this.idCategoria=id;
		//assert invarianteC() && (risorse!=null ) ;
		
		//idRisorsa=idMax();	
	}

	public Categoria (String nome) {
		this.nome=nome;
		descrizioneCampi=new ArrayList<String>();
		risorse = new ArrayList<T>();
	}
	/**
	 * cerca nelle risorse libro l'indice di risorsa massimo
	 * @pre true
	 * @post @nochange
	 * @return il valore dell'id risorsa piï¿½ alto trovato oppure -1
	 */
	protected int idMax() {	

	/*	assert invarianteC();
		Categoria categoriaPre = this;*/
		
	/*	
		if (risorse.size() > 0) {
			int max=((Integer)risorse.get(0).getValue(ID));
			
			for(int i=1; i<risorse.size();i++)
				
				if(((Integer)risorse.get(i).getValue(ID))>max) {
					max=((Integer)risorse.get(i).getValue(ID));
				}
			
			//assert invarianteC() && categoriaPre==this;
			return max;
		}*/
		
		//assert invarianteC() && categoriaPre==this;
		return -1;			
	}
	
	/**
	 * verifica se ci sono risorse nelle sottocategorie, se esistono
	 * @return true se c'è almeno una risorsa
	 * @pre true
	 * @post @nochange
	 */
	public boolean hasRisorse() {
		//assert invariante();
		
		if(risorse.size()>0) return true; 
		
		return false;
	}
	

	/**
	 * chiede all'utente i dati necessari alla creazione di una risorsa e la crea
	 * @pre true
	 * @post (sizeLibri()==sizeLibri()@pre+1 || sizeFilm()==sizeFilm()@pre+1) && idRisorse()==idRisorse()@pre+1 
	 * 	
	 */
	public abstract void aggiungiRisorsa(ArrayList<Object> attributiNuovaRisorsa) throws ClassCastException;


	public void aggiungiRisorsaEAggiornaStorico(ArrayList<Object> attributiNuovaRisorsa) throws ClassCastException{
		aggiungiRisorsa(attributiNuovaRisorsa);

	}
	
/**ritorna un elenco dei nomi delle risorse presenti nella categoria
 * @pre true
 * @post @nochange && @return != null
 * @return elenco di nomi
 */
	public String[] elencoRisorse() {	
		/*assert invarianteC();
		Categoria<T> thisPre = this ;*/
		
		String[] risultato = new String[0];
		
		if (risorse.size()<1) 
			return risultato;	
		else
		{
			String[] elenco = new String[risorse.size()];
			
			int i=0;			
			for(Risorsa r : risorse) {
				elenco[i]=r.toString();
				i++;					
			}
			//assert invarianteC() && thisPre==this ;
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
	
	public String showRisorsa(int id) {
		/*assert invarianteC();
		Categoria<T> thisPre = this;*/
		
		if(risorse.size()>0)
			for(Risorsa r : risorse) 
				if((Integer)r.getValue(ID)==id) return r.toString();
	
		//assert invarianteC() && thisPre==this;
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
	public void modifica(int idRisorsaDaModificare, Object[] nuoviAttributi) throws ClassCastException{	
		/*assert invarianteC();
		int libriPre = risorse.size();*/
		
		if(risorse.size()>0)
			for(Risorsa r : risorse)
				if (((Integer)r.getValue(ID) )== idRisorsaDaModificare) 
					{
					
					r.modifica(nuoviAttributi);
					
					}
			
			
				
		//assert invarianteC() && libriPre==risorse.size() ;
	}
/**
 * rimuove la risorsa che possiede l'id specificato dal parametro in ingresso	
 * @param id dell'id
 * @true true 
 * @post librisize()+filmsize()==librisize()@pre+filmsize()@pre-1 || @nochange
 */
	public void rimuoviRisorsa(int id) {		
	/*	assert invarianteC();
		Categoria<T> thisPre = this;
		int libriPre = risorse.size();*/
		
		if(risorse.size()>0)
			for(int i=0; i <risorse.size();i++)
				if (((Integer)risorse.get(i).getValue(ID)) == id) {
					risorse.remove(i);
				}
		
	//	assert invarianteC() && (libriPre==risorse.size() || thisPre==this);

	}
	
	/**
	 * chiede all'utente di Selezionare una risorsa e ritorna l'id di essa
	 * @return l'id della risorsa selezionata oppure -1
	 * @pre true
	 * @post @nochange
	 */
 	public int scegliRisorsa(int risorsaSelezionata) {	

 		return 	 (Integer) risorse.get(risorsaSelezionata).getValue(ID) ;		
	}
/**
 * cerca se una risorsa nella categoria ï¿½ presente
 * @param risorsaScelta id della risorsa da cercare
 * @pre true
 * @post @nochange
 * @return true se la risorsa ï¿½ stata trovata
 */
	public boolean cercaRisorsa(int risorsaScelta) {	
		/*assert invarianteC();
 		Categoria<T> thisPre = this;*/
 		
		boolean trovato = false;
		
		for (int i = 0; i < risorse.size(); i++) {
			if (((Integer)risorse.get(i).getValue(ID))==risorsaScelta)
				trovato = true;
		}
	
		//assert invarianteC() && thisPre==this;
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
		/*assert invarianteC();
 		Categoria<T> thisPre = this;*/
 		
		if(risorse.size()>0) {
			
			for(Risorsa r : risorse) {
				if(((Integer)r.getValue(ID))==idRisorsa) {
					String descrizioneLibro = r.descrizionePrestito();	
					//assert invarianteC() && thisPre==this;
					return 	descrizioneLibro;
				}
			}
		}			
		
		//assert invarianteC() && thisPre==this;
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
		/*assert invarianteC();
 		Categoria<T> thisPre = this;*/
 		
		if(risorse.size()>0) {
			for(Risorsa r : risorse) {
				if(((Integer)r.getValue(ID))==id) {
					String descrizionelibro = ((String)r.getValue(TITOLO));
					//assert invarianteC() && thisPre==this;
					return descrizionelibro;
					}
			}
		}				

		
		//assert invarianteC() && thisPre==this;
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
		/*assert invarianteC();
 		Categoria<T> thisPre = this;*/
 		
		for(Risorsa r: risorse)
			if(((Integer)r.getValue(ID))==risorsaScelta) {
				int licenzeLibro = ((Integer)r.getValue(NUMEROLICENZE));
				//assert invarianteC() && thisPre==this;
				return licenzeLibro;
			}
		
     	//assert invarianteC() && thisPre==this;
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
	//	assert invarianteC() && ((flag==0 && numeroLicenzeRisorsa(risorsaScelta)>0) || (flag==0 && numeroLicenzeRisorsa(risorsaScelta)==-1) || flag!=0) ;
		
		for(Risorsa r: risorse)
			if(((Integer)r.getValue(ID))==risorsaScelta)
				if (flag==0)
					r.decrementaLicenze();
				else
					r.incrementaLicenze();			
				
		//	assert invarianteC();
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
	public ArrayList<Integer> filtraRisorse(int attributoScelto, Object parametroRicerca) throws ClassCastException{			
		//assert invarianteC() && chiaveDiRicerca!=null && (!chiaveDiRicerca.equals("") || numDiRicerca >0);
		//Categoria<T> thisPre = this;
		
		ArrayList<Integer> r = new ArrayList<Integer>();
		r = filtra(attributoScelto, parametroRicerca);
				
		//assert invarianteC() && thisPre==this;
		return r;		
	}
/**
 * 
 * 	raggruppa tutti i libri che fanno match con i requisiti richiesti
 * @param attributoScelto attributo da confrontare
 * @param chiaveDiRicerca stringa da confrontare con l'attributo
 * @param numDiRicerca intero da cofrontare con l'attributo
 * @return lista di identificativi relativi ai match positivi
 * @pre chiaveDiRicerca!=null && attributoScelto>=1 && attributoScelto<=7
 * @post @nochange
 */
	private ArrayList<Integer> filtra(int attributoScelto, Object parametroDiRicerca) throws ClassCastException {
		
		ArrayList<Integer> risultato = new ArrayList<Integer>();
		
		String campo;
		if (risorse.size()>0) {
			campo = getDescrizioneCampo(attributoScelto);
			
			for (Risorsa r: risorse)
				if(r.match(parametroDiRicerca,campo))
					risultato.add(((Integer)r.getValue(ID)));
		}
		
		return risultato;
		
	}
	

/**
 * restituisce l'id della risorsa indicata	
 * @param posArray posizione della risorsa nell'array di categoria
 * @return id della risorsa
 * @pre (idCategoria()==0 && posArray>=0 && posArray<=librisize() )|| (idCategoria()==1 && posArray>=0 && posArray<=filmsize() )
 * @post @nochange
 */
	public int getId(int posArray) {
		//assert invarianteC() && (idCategoria==0 && posArray>=0 && posArray<=risorse.size()) || (idCategoria==1 && posArray>=0 )  ;
		//Categoria<T> thisPre = this;
		
		int id = (Integer) risorse.get(posArray).getValue(ID);
		//assert invarianteC() && thisPre==this;
		return id;	
	}
	

/**
 * setta l'id max delle risorse al valore immesso		
 * @param id nuovo valore dell'id max
 * @pre id>=0
 * @post idRisorsa()==id
 */
	public static void setId (int id) {
	//	assert  id>=0 ;
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
		if(risorse.size()>0) 
			for(Risorsa r : risorse) 
				if ((r.getValue(ID)).equals(idRisorsa)) 
					return idCategoria;
				
		return -1;
	}
/**
 * verifica che le proprietï¿½ invarianti della classe siano mantenute 
 * @pre true
 * @post @nochange
 * @return true se i getNome() torna il valore corretto &&  gli attributi assumono una  combinazione di valori ammessi

 */
	/*protected  boolean invarianteC() {
		Categoria<T> categoriaPre = this;
		
		boolean invariante=false;
		if(getNome()==nome && nome!=null && idRisorsa>=0 && (idCategoria==0 || idCategoria==1) && (risorse.size()==0) && risorse!=null  )invariante=true;
		
		assert categoriaPre==this;
		
		return invariante;
	}*/

	protected abstract void setDescrizioneCampi();
	
	public ArrayList<String> getDescrizioneCampi(){
		return descrizioneCampi;
	}
		
	public String getDescrizioneCampo(int attributoScelto) {
		return descrizioneCampi.get(attributoScelto-1);
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
	
		//assert invariante() && durataMassimaPrestito>0;
		
		this.durataMassimaPrestito = durataMassimaPrestito;
		
		//assert invariante();
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
		//assert invariante() && durataMassimaProroga>0;
		
		this.durataMassimaProroga = durataMassimaProroga;
	
		//assert invariante();
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
	//	assert invariante()&& termineProroga>=0;
		
		this.termineProroga = termineProroga;
		
	//	assert invariante();
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
		//assert invariante() && maxRisorse>0;
		
		this.maxRisorse = maxRisorse;
		
		//assert invariante();
	}

	public static int getIdRisorsaCorrente() {
		return idRisorsa;
	}
}

