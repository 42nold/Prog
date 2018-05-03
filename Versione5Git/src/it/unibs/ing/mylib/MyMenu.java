package it.unibs.ing.mylib;



public class MyMenu {
	
	final private static String CORNICE = "-------------------------------------------";
	final private static String VOCE_USCITA = "0\tEsci";
	final private static String RICHIESTA_INSERIMENTO = "Digita il numero dell'opzione desiderata -> ";
	
	private String titolo;
	private String [] voci;
	
	
	
	/**
	 * Crea un menu.
	 * @param titolo Il titolo del menu.
	 * @param voci Le voci che avra il menu.
	 */
	public MyMenu (String titolo, String [] voci) {
		
		this.titolo = titolo;
		this.voci = voci;
	}
	
	
	/**
	 * Acquisisce la scelta dell'utente dopo aver stampato il menu.
	 * @return L'intero rappresentate la scelta dell'utente.
	 */
	public int scegli () {
		
		stampaMenu();
		
		return InputDati.leggiIntero(RICHIESTA_INSERIMENTO, 0, voci.length);
	}
	
	
	
	private void stampaMenu () {
		
		System.out.println();
		System.out.println(CORNICE);
		System.out.println(titolo);
		System.out.println(CORNICE);
		
		
		for (int i=0; i<voci.length; i++) {
			
			System.out.println( (i+1) + "\t" + voci[i]);
		}
		
		System.out.println();
		System.out.println(VOCE_USCITA);
		System.out.println();
	}		
}

