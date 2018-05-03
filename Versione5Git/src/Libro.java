import it.unibs.ing.mylib.InputDati;

@SuppressWarnings("serial")
public class Libro extends Risorsa {
	
	/**
	 * @invariant invariante()
	 */
	private String autore;
	private String casaEditrice;
	private int numeroPagine;
	/**
	 * costruisce un istanza di Libro con i parametri immessi
	 * @param nome
	 * @param aut
	 * @param casaeditrice
	 * @param gen
	 * @param lin
	 * @param anno
	 * @param pagine
	 * @param id
	 * @param numLicenze
	 * 
	 */
	public Libro(String nome,String aut,String casaeditrice,String gen,String lin,int anno,int pagine, int id,int numLicenze) {
		super(nome,gen,lin,anno,id,numLicenze);
		
		autore=aut;
		casaEditrice=casaeditrice;
		numeroPagine=pagine;
		
		assert invariante();
	}
	/**
	 * restituisce una stringa descrittiva della risorsa Libro
	 * @pre true
	 * @post @nochange
	 * @return stringa descrittiva
	 */
	public String descrizionePrestito() {
		assert invariante();
		Libro libroPre = this ;

		StringBuffer des = new StringBuffer();
		
		des.append(super.descrizionePrestito());		
		des.append("   autore: "+autore + "\n");
		des.append("   casa editrice: "+casaEditrice + "\n") ;
		des.append("   numero di pagine: "+ numeroPagine + "\n" );
		
		assert libroPre==this;
		assert invariante();
		
		return des.toString();
	}
/**
 * verifica se la stringa data come parametro è contenuta nel nome dell'autore della risorsa	
 * @param chiaveDiRicerca stringa da confrontare
 * @pre chiaveDiRicerca != null
 * @post @nochange
 * @return true se la stringa è contenuta (no case sensitive)
 */
	public boolean matchAutore(String chiaveDiRicerca) {
		assert invariante() && chiaveDiRicerca!=null;
		Libro libroPre = this ;

		boolean match = false ;
		if(autore.toLowerCase().contains(chiaveDiRicerca.toLowerCase())) match =  true;

		assert invariante() &&  libroPre==this;

		return match;
	}
	/**
	 * verifica se la stringa data come parametro è contenuta nella case editrice della risorsa	
	 * @param chiaveDiRicerca stringa da confrontare
	 * @pre chiaveDiRicerca != null
	 * @post @nochange
	 * @return true se la stringa è contenuta (no case sensitive)
	 */
	public boolean matchCasaEditrice(String chiaveDiRicerca) {
		assert invariante() && chiaveDiRicerca!=null;
		Libro libroPre = this ;
		
		boolean match = false;
		if(casaEditrice.toLowerCase().contains(chiaveDiRicerca.toLowerCase())) match = true;
		
		assert invariante() &&  libroPre==this;;
		
		return match;
	}
	/**
	 * verifica se l'intero dato come parametro è uguale al numero di pagine della risorsa	
	 * @param numDiRicerca numero da confrontare
	 * @pre true
	 * @post @nochange
	 * @return true se il numero è uguale
	 */
	public boolean matchPagine(int numDiRicerca) {
		assert invariante() ;
		Libro libroPre = this ;
		
		boolean match = false;
		if(numeroPagine==numDiRicerca) match =  true;
		
		assert invariante() &&  libroPre==this;

		return match;
	}
/**
 * chiede all'utente quali campi devono essere modificati ed esegue le modifiche chiedendo i nuovi valori
 * @pre true
 * @post true	
 */
	public void modifica() {
		assert invariante() ;

		super.modifica();
		
		String domanda = "vuoi modificare ";
		
		char modificaAutore = InputDati.leggiChar(domanda+"l'autore ? (s/n)");
		
		if(modificaAutore=='s'||modificaAutore=='S') {

			
			String autNuovo= InputDati.leggiStringaNonVuota("inserisci il nuovo autore del libro");
		  
			autore=autNuovo;

		}
		
		char modificaCasa = InputDati.leggiChar(domanda+"la casa editrice ? (s/n)");
		
		if(modificaCasa=='s'||modificaCasa=='S') {

			String casaNuovo= InputDati.leggiStringaNonVuota("inserisci la nuova casa editrice del libro");
		
			casaEditrice=casaNuovo;

		}
		
		char modificaPagine = InputDati.leggiChar(domanda+"il numero di pagine ? (s/n)");
		
		if(modificaPagine=='s'||modificaPagine=='S') {

			int pagineNuovo = InputDati.leggiInteroPositivo("inserisci il nuovo numero di pagine");		
			numeroPagine=pagineNuovo;
		}
		
		assert invariante();
	}
	/**
	 * verifica che le proprietà invarianti della classe Libro siano rispettate
	 * @pre true
	 * @post @nochange
	 * @return true se gli attributi assumono valori validi && super.invariante()
	 */
	protected boolean invariante() {
		Libro libroPre = this ;
		
		boolean invariante=false;
		if(super.invarianteR() && autore!=null && casaEditrice!=null && numeroPagine>0) invariante=true ;

		assert libroPre==this;
		
		return invariante;
	}
}
