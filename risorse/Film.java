package risorse;


import it.unibs.ing.mylib.InputDati;

@SuppressWarnings("serial")
public class Film extends Risorsa {
	
	/**
	 * @invariant invariante()
	 */
	private final static int NUM_ATTRIBUTI_NUMERICI = 1;
	private final static int NUM_ATTRIBUTI_STRINGA = 3;
	private String regista;
	private String casaProduzione;
	private String dataUscita; 
	private int durata;
	/**
	 * costruisce istanza di Film con i parametri immessi
	 * @param nome
	 * @param reg regista
	 * @param casaP casa produzione
	 * @param gen genere 
	 * @param lin lingua
	 * @param anno
	 * @param dur durata
	 * @param id
	 * @param numLicenze
	 */
	public Film(String nome,String reg,String casaP,String gen,String lin,int anno,int dur, int id,int numLicenze) {
		super(nome,gen,lin,anno,id,numLicenze);
		
		regista=reg;
		casaProduzione=casaP;
		durata=dur;	
		dataUscita="";
		
		assert invariante();
	}
	/**
	 * restituisce una stringa descrittiva della risorsa Film
	 * @pre true
	 * @post @nochange
	 * @return stringa descrittiva
	 */
	public String descrizionePrestito() {
		assert invariante();
		Film filmPre = this ;
		
		StringBuffer des = new StringBuffer();
		
		des.append(super.descrizionePrestito());		
		des.append("   regista: "+regista + "\n");
		des.append("   casa di produzione: "+casaProduzione + "\n") ;
		des.append("   durata: "+ durata + " min.\n" );
		
		assert invariante()&&filmPre==this;
		
		return des.toString();
	}
	/**
	 * verifica se la stringa data come parametro è contenuta nel nome del regista della risorsa	
	 * @param chiaveDiRicerca stringa da confrontare
	 * @pre chiaveDiRicerca != null
	 * @post @nochange
	 * @return true se la stringa è contenuta (no case sensitive)
	 */	
	public boolean matchRegista(String chiaveDiRicerca) {
		assert invariante() && chiaveDiRicerca!=null ;
		Film filmPre = this ;
		
		boolean match=false;
		if(regista.toLowerCase().contains(chiaveDiRicerca.toLowerCase()))match = true;
		
		assert invariante()&&filmPre==this;

		return match;
	}
	/**
	 * verifica se la stringa data come parametro è contenuta nel nome della casa di produzione della risorsa	
	 * @param chiaveDiRicerca stringa da confrontare
	 * @pre chiaveDiRicerca != null
	 * @post @nochange
	 * @return true se la stringa è contenuta (no case sensitive)
	 */	
	public boolean matchCasaProduzione(String chiaveDiRicerca) {
		assert invariante() && chiaveDiRicerca!=null ;
		Film filmPre = this ;
		
		boolean match = false;
		if(casaProduzione.toLowerCase().contains(chiaveDiRicerca.toLowerCase())) match = true;
		
		assert invariante()&&filmPre==this;

		return match;
	}
	/**
	 * verifica se l'intero dato come parametro è uguale alla durata in minuti del film	
	 * @param numDiRicerca intero da confrontare
	 * @pre numDiRicerca > 0
	 * @post @nochange
	 * @return true l'intero è uguale alla durata del film
	 */	
	public boolean matchDurata(int numDiRicerca) {
		assert invariante() && numDiRicerca > 0 ;
		Film filmPre = this ;
		
		boolean match = false ;
		if(durata==numDiRicerca) match = true;
		
		assert invariante()&&filmPre==this;

		return match;
	}
	/**
	 * chiede all'utente quali campi devono essere modificati ed esegue le modifiche chiedendo i nuovi valori
	 * @pre true
	 * @post true	
	 */	
	public void modifica(String[] nuoveStringhe, int[] nuoviNumeri) {
		assert invariante() && nuoveStringhe.length>=NUM_ATTRIBUTI_STRINGA && nuoviNumeri.length>=NUM_ATTRIBUTI_NUMERICI;
		if(nuoveStringhe[nuoveStringhe.length-1] != null)
		dataUscita= nuoveStringhe[nuoveStringhe.length-1];
		
		if(nuoveStringhe[nuoveStringhe.length-2] != null)
		casaProduzione= nuoveStringhe[nuoveStringhe.length-2];	
		
		if(nuoveStringhe[nuoveStringhe.length-3] != null)
		regista= nuoveStringhe[nuoveStringhe.length-3];
		
		String[] stringheBase = new String[nuoveStringhe.length-NUM_ATTRIBUTI_STRINGA];
		for(int i=0;i<stringheBase.length ; i++) {
			stringheBase[i]=nuoveStringhe[i];
		}
	
		if(nuoviNumeri[nuoviNumeri.length-1] >= 0)
		durata=nuoviNumeri[nuoviNumeri.length-1];
		
		int[] numeriBase = new int[nuoviNumeri.length-NUM_ATTRIBUTI_NUMERICI];
		for(int i=0;i<numeriBase.length ; i++) {
			numeriBase[i]=nuoviNumeri[i];
		}
		
		
		super.modifica(stringheBase,numeriBase);
		assert invariante();
	}
	/**
	 * verifica che le proprietà invarianti della classe Film siano rispettate
	 * @pre true
	 * @post @nochange
	 * @return true se gli attributi assumono valori validi && super.invariante()
	 */
	protected boolean invariante() {
		Film filmPre = this ;
		
		boolean invariante=false;
		if(super.invarianteR() && regista!=null && casaProduzione!=null && durata>0 && dataUscita!=null) invariante=true ;

		assert filmPre==this;
		;
		return invariante;
	}
	public static String[] getAttributiStringa() {
		String[] attributiBase = Risorsa.getAttributiStringa();
	
		int numAttributiBase = attributiBase.length;
		
		String[] attributi = new String[numAttributiBase+NUM_ATTRIBUTI_STRINGA];
		
		for(int i = 0 ; i < numAttributiBase ; i++) {
			attributi[i]= attributiBase[i];
		}
		
		attributi[numAttributiBase]="regista";
		attributi[numAttributiBase+1]="casa di Produzione";
		attributi[numAttributiBase+2]="data di Uscita";

		return attributi;
	}
	
public static String[] getAttributiNumerici() {
		
		String[] attributiBase = Risorsa.getAttributiNumerici();
		
		int numAttributiBase = attributiBase.length;
		
		String[] attributi = new String[numAttributiBase+NUM_ATTRIBUTI_NUMERICI];
		
		for(int i = 0 ; i < numAttributiBase ; i++) {
			attributi[i]= attributiBase[i];
		}
		
		attributi[numAttributiBase]="durata film";

		return attributi;
	}
}
