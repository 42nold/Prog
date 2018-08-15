package it.unibs.ing.mylib;



public class BelleStringhe {
	
	private final static String CORNICE = "---------------------------------------------------";
	private final static String SPAZIO = " ";
	private final static String ACAPO = "\n";

	
	/**
	 * Mette una stringa di testo in una cornice
	 * @param s La stringa da incorniciare.
	 * @return La stringa incornicciata.
	 */
	public static String incornicia(String s) {
		
		StringBuffer res = new StringBuffer();
		res.append(CORNICE+ACAPO);
		res.append(s+ACAPO);
		res.append(CORNICE+ACAPO);
		
		return res.toString();
	}

 
	/**
	 * Incolonna una stringa di testo.
	 * @param s La stringa da incolonnare.
	 * @param larghezza La larghezza della riga in numero di caratteri.
	 * @return La stringa incolonnata.
	 */
	public static String incolonna(String s, int larghezza) {
		
		StringBuffer res = new StringBuffer(larghezza);
		int numCharDaStampare = Math.min(larghezza,s.length());
		res.append(s.substring(0, numCharDaStampare));
		for (int i=s.length()+1; i<=larghezza; i++)
			res.append(SPAZIO);
		
		return res.toString();
	}
	
	
	/**
	 * Mette una stringa di testo al centro di una riga di una certa larghezza.
	 * @param s La stringa di testo da centrare.
	 * @param larghezza La larghezza della riga in numero di caratteri.
	 * @return La stringa centrata.
	 */
	public static String centrata (String s, int larghezza) {
		
		StringBuffer res = new StringBuffer(larghezza);
		if (larghezza <= s.length())
			res.append(s.substring(larghezza));
		else {
			int spaziPrima = (larghezza - s.length())/2;
			int spaziDopo = larghezza - spaziPrima - s.length();
			for (int i=1; i<=spaziPrima; i++)
				res.append(SPAZIO);
			
			res.append(s);
		
			for (int i=1; i<=spaziDopo; i++)
				res.append(SPAZIO);
		}
		
	 	return res.toString();
	}

	
	/**
	 * Ripete un carattere un certo numero di volte.
	 * @param elemento Il carattere da ripetere.
	 * @param larghezza Il numero di volte da ripetere il carattere.
	 * @return Una stringa contenente il carattere ripetuto.
	 */
	public static String ripetiChar (char elemento, int larghezza) {
		
		StringBuffer result = new StringBuffer(larghezza);
		for (int i = 0; i < larghezza; i++) {
			 result.append(elemento);
		}
		
		return result.toString();
	 }

	
	/**
	 * Isola spazialmente una stringa.
	 * @param daIsolare La stringa da isolare.
	 * @return La stringa isolata.
	 */
	public static String rigaIsolata(String daIsolare) {
		
		StringBuffer result = new StringBuffer();
		result.append(ACAPO);
		result.append(daIsolare);
		result.append(ACAPO);
		
		return result.toString();
	 }
 
}

