import java.io.Serializable;

import it.unibs.ing.mylib.InputDati;

@SuppressWarnings("serial")
public abstract class Risorsa implements Serializable{
	/**
	 * classe astratta che definisce le operazioni esseziali eseguibili su ogni tipo di risorsa contenuta in archivio
	 * @invariant invariante()
	 */
	protected int id;
	protected String nome;	
	protected String genere;
	protected String lingua;	
	protected int numeroLicenze;
	protected int anno;
/**
 * costruisce un istanza di risorsa con i parametri comni a tutti i tpi di risorsa
 * @param nome nome della risorsa
 * @param gen genere 
 * @param lin lingua
 * @param an anno di pubblicazione
 * @param id identificativo della risorsa
 * @param numLicenze numero di licenze attrivuito alla risorsa
 */
	public Risorsa(String nome,String gen,String lin, int an, int id,int numLicenze) {
		this.nome = nome;
		genere=gen;
		lingua=lin;
		numeroLicenze=numLicenze;
		anno=an;
		this.id = id;
		
		assert this.invarianteR();
	}
	
/**
 * restituisce una stringa di descrizione completa della risorsa
 * @pre true
 * @post @nochange
 */
	public String toString() {
		assert invarianteR();
		Risorsa risorsaPre = this ;
		
		String risultato = descrizionePrestito() + "   numero di licenze: "+numeroLicenze + "\n" ;
		
		assert risorsaPre==this ;
		assert invarianteR();
		
		return risultato ;
	}
/**
 * restituisce una stringa che descrive parzialmente la risorsa
 * @pre true
 * @post @nochange	
 * @return stringa descrittiva
 */
	public String descrizionePrestito() {
		assert invarianteR();
		Risorsa risorsaPre = this;
		
		StringBuffer des = new StringBuffer();
		
		des.append( "   id: "+id + " \n");
		des.append("   titolo: "+nome + "\n" );		
		des.append("   genere: "+genere + "\n") ;
		des.append("   lingua: "+lingua + "\n") ;	
		des.append("   anno uscita: "+ anno + "\n" );
		
		assert risorsaPre==this ;
		assert invarianteR();

		return des.toString();
	}
	/**
	 * diminuisce di 1 il numero di licenze della risorsa
	 * @pre getNumLicenze()>0
	 * @post getNumLicenze() == getNumLicenze()@pre - 1
	 */
	public void decrementaLicenze() {
		assert invarianteR();
		assert numeroLicenze>0;
		int licenzePre = numeroLicenze;
		
		numeroLicenze--;	
		
		assert numeroLicenze==licenzePre-1 ;
		assert invarianteR();

	}
/**
 * incremente di 1 le licenze della risorsa
 * @pre true
 * @post true	
 */
	public void incrementaLicenze() {
		assert invarianteR();

		numeroLicenze++;	
		
		assert invarianteR();
	}
/**
 * modifica il nome della risorsa	
 * @param nuovoNome nuovo nome da attribuire alla risorsa
 * @pre true
 * @post true
 */
	public void modifica(String nuovoNome) {
		assert invarianteR();

		nome=nuovoNome;		

		assert invarianteR();
	}
/**
 * controlla se una stringa data come parametro è contenuta nel nome della risorsa (no case sensitive)
 * @param chiaveDiRicerca stringa da controllare
 * @pre chiaveDiRicerca!=null
 * @post @nochange
 * @return true se la stringa è contenuta
 */
	public boolean matchNome(String chiaveDiRicerca) {
		assert invarianteR();
		assert chiaveDiRicerca!=null;
		Risorsa risorsaPre = this;
		
		boolean match =nome.toLowerCase().contains(chiaveDiRicerca.toLowerCase());
		
		assert risorsaPre==this ;
		assert invarianteR();

		if(match)return true;
		return false;
	}
/**
 * controlla se una stringa data come parametro è contenuta nell'attributo genere della risorsa	
 * @param chiaveDiRicerca stringa da confrontare
 * @pre chiaveDiRicerca!=null
 * @post @nochange
 * @return true se la stringa è contenuta
 */
	public boolean matchGenere(String chiaveDiRicerca) {
		assert invarianteR();
		assert chiaveDiRicerca!=null;
		Risorsa risorsaPre = this;
		
		boolean match =genere.toLowerCase().contains(chiaveDiRicerca.toLowerCase());
		
		assert risorsaPre==this ;
		assert invarianteR();
		
		if(match) return true;
		return false;
	}
	/**
	 * controlla se una stringa data come parametro è contenuta nell'attributo lingua della risorsa	
	 * @param chiaveDiRicerca stringa da confrontare
	 * @pre chiaveDiRicerca != null
	 * @post @nochange
	 * @return true se la stringa è contenuta
	 */
	
	public boolean matchLingua(String chiaveDiRicerca) {
		assert invarianteR();
		assert chiaveDiRicerca!=null;
		Risorsa risorsaPre = this;
		
		boolean match = lingua.toLowerCase().contains(chiaveDiRicerca.toLowerCase()) ;
				
		assert risorsaPre==this ;
		assert invarianteR();
		
		if(match)return true;
		return false;
	}
/**
 * verifica se l'intero dato in ingresso è uguale all'attributo anno della risorsa	
 * @param numDiRicerca numero da confrontare
 * @pre true
 * @post @nochange
 * @return true se il numero è uguale all'attributo anno
 */
	public boolean matchAnno(int numDiRicerca) {
		assert invarianteR();
		Risorsa risorsaPre = this;

		boolean match = anno==numDiRicerca ;
		
		assert risorsaPre==this ;
		assert invarianteR();

		if(match) return true;
		return false;
	}
/**
 * restituisce il nome della risorsa
 * @pre true
 * @post @nochange	
 * @return stringa contenente il nome
 */
	public String getNome() {
		return nome;
	}
	/**
	 * restituisce l'id della risorsa
	 * @pre true
	 * @post @nochange
	 * @return intero relativo all'id della risorsa
	 */
	public int getId() {
		return id;
	}
	/**
	 * restituisce il numero delle licenze della risorsa
	 * @pre true
	 * @post @nochange
	 * @return l'intero relativo al numero di licenze rimaste
	 */
	public int getNumLicenze() {
		return numeroLicenze;
	}
	/**
	 * chiede all'utente quali campi della risorsa vuole modificare ed i rispettivi nuovi  valori , poi esegue le modifiche
	 * @pre true
	 * @post true
	 */
	public void modifica() {
		assert invarianteR();
	
		String domanda = "vuoi modificare ";
		
		char modificaNome = InputDati.leggiChar(domanda+"il nome ? (s/n)");
		
		if(modificaNome=='s'||modificaNome=='S') {
		
			String nomeNuovo= InputDati.leggiStringaNonVuota("inserisci il nuovo nome della risorsa");
		
			nome=nomeNuovo;
		}			
		
		char modificaGenere = InputDati.leggiChar(domanda+"il genere ? (s/n)");
		
		if(modificaGenere=='s'||modificaGenere=='S') {

			String genNuovo= InputDati.leggiStringaNonVuota("inserisci il nuovo genere della risorsa");
			genere=genNuovo;
		}
		
		char modificaLingua = InputDati.leggiChar(domanda+"la lingua ? (s/n)");
		
		if(modificaLingua=='s'||modificaLingua=='S') {

			String linNuovo= InputDati.leggiStringaNonVuota("inserisci la nuova lingua della risorsa");
			lingua=linNuovo;
		}
		
		char modificaAnno = InputDati.leggiChar(domanda+"l'anno di uscita ? (s/n)");
		
		if(modificaAnno=='s'||modificaAnno=='S') {

			int annoNuovo = InputDati.leggiInteroPositivo("inserisci il nuovo anno di uscita");
			anno=annoNuovo;
		}
		
		char modificaLicenze = InputDati.leggiChar(domanda+"il numero di licenze ? (s/n)");
		
		if(modificaLicenze=='s'||modificaLicenze=='S') {

			int licenzeNuovo = InputDati.leggiInteroPositivo("inserisci il nuovo numero di licenze");		
			numeroLicenze=licenzeNuovo;
		}
		
		assert invarianteR();
	}
/**
 * verifica che le proprietà invarianti della classe siano rispettate
 * @pre true
 * @post @nochange
 * @return true se i getters restituiscano i reali valori degli attributi, che le stringhe non siano nulle e che i valori numerici non siano negativi	
 */
	protected boolean invarianteR() {
		Risorsa risorsaPre = this;
		
		boolean invariante = false;
		if(getId()==id && getNome()==nome && getNumLicenze()==numeroLicenze && id>=0 && nome!=null && genere!=null && lingua!=null && numeroLicenze>=0 && anno>=0 ) invariante =true;
		
		assert risorsaPre==this;
		
		return invariante;
	}
}
