package it.unibs.ing.mylib;



import java.util.*;



public class InputDati {
	
	private final static String ERRORE_FORMATO = "Attenzione: il dato inserito non e' nel formato corretto";
	private final static String ERRORE_MINIMO= "Attenzione: e' richiesto un valore maggiore o uguale a ";
	private final static String ERRORE_STRINGA_VUOTA= "Attenzione: non hai inserito alcun carattere";
	private final static String ERRORE_MASSIMO= "Attenzione: e' richiesto un valore minore o uguale a ";
	private final static String MESSAGGIO_AMMISSIBILI= "Attenzione: i caratteri ammissibili sono: ";
	
	private final static char RISPOSTA_SI='S';
	private final static char RISPOSTA_NO='N';
	
	
	
	private static Scanner lettore = creaScanner();
	
	
	
	/**
	 * Crea un oggetto di tipo scanner.
	 * @return L'oggetto di tipo scanner.
	 */
	private static Scanner creaScanner() {
		
		Scanner creato = new Scanner(System.in);
		creato.useDelimiter(System.getProperty("line.separator"));
		
		return creato;
	 }
	
	
	
	/**
	 * Legge una stringa dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da esporre.
	 * @return La stringa letta.
	 */
	public static String leggiStringa(String messaggio) {
		
		System.out.print(messaggio);
		
		return lettore.next();
	}
	
	
	
	/**
	 * Legge una stringa che non deve essere vuota dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da esporre.
	 * @return La stringa letta.
	 */
	public static String leggiStringaNonVuota(String messaggio) {
		
		boolean finito=false;
		String lettura = null;
		
		do {
			
			lettura = leggiStringa(messaggio);
			lettura = lettura.trim();
			if (lettura.length() > 0)
				finito=true;
			else
				System.out.println(ERRORE_STRINGA_VUOTA);
			
	   } while (!finito);
		
	   return lettura;
	}
	
	
	
	/**
	 * Legge un carattere dopo aver stampato un messaggio.
	 * @param messaggio Il messagio da stampare.
	 * @return Il carattere letto.
	 */
	public static char leggiChar (String messaggio) {
		
		boolean finito = false;
		char valoreLetto = '\0';
		
		do {
			
			System.out.print(messaggio);
			String lettura = lettore.next();
			
			if (lettura.length() > 0) {
				
				valoreLetto = lettura.charAt(0);
				finito = true;
			}
			else {
				System.out.println(ERRORE_STRINGA_VUOTA);
			}
			
	    } while (!finito);
		
	   return valoreLetto;
	}
	  
	
	
	private static char leggiUpperChar(String messaggio, String ammissibili) {
		
		boolean finito = false;
		char valoreLetto = '\0';
	   
		do {
			
			valoreLetto = leggiChar(messaggio);
			valoreLetto = Character.toUpperCase(valoreLetto);
			
			if (ammissibili.indexOf(valoreLetto) != -1)
				finito  = true;
			else
				System.out.println(MESSAGGIO_AMMISSIBILI + ammissibili);
			
		} while (!finito);
		
		return valoreLetto;
	}
	
	
	/**
	 * Legge un intero dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da stampare.
	 * @return L'intero letto.
	 */
	public static int leggiIntero(String messaggio) {
		
		boolean finito = false;
		int valoreLetto = 0;
		
		do {
			
			System.out.print(messaggio);
			
			try {
				
				valoreLetto = lettore.nextInt();
				finito = true;
			}
			
			catch (InputMismatchException e) {
				
				System.out.println(ERRORE_FORMATO);
				@SuppressWarnings("unused")
				String daButtare = lettore.next();
			}
		} while (!finito);
		
		return valoreLetto;
	}
	
	
	
	/**
	 * Legge un intero positivo (>0) dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da stampare.
	 * @return L'intero letto.
	 */
	public static int leggiInteroPositivo(String messaggio) {
		
		return leggiInteroConMinimo(messaggio,1);
	}
	
	
	
	/**
	 * Legge un intero non negativo (>=0) dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da stampare.
	 * @return L'intero letto.
	 */
	public static int leggiInteroNonNegativo(String messaggio) {
		
		return leggiInteroConMinimo(messaggio,0);
	}
	
	
	/**
	 * Legge un intero purchè sia maggiore o uguale (>=x) di un certo valore, dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da stampare.
	 * @param minimo Il minimo
	 * @return L'intero letto.
	 */
	public static int leggiInteroConMinimo(String messaggio, int minimo) {
		
		boolean finito = false;
		int valoreLetto = 0;
		
		do {
			
			valoreLetto = leggiIntero(messaggio);
			
			if (valoreLetto >= minimo)
				finito = true;
			else
				System.out.println(ERRORE_MINIMO + minimo);
	    } while (!finito);
		
		return valoreLetto;
	}

	
	
	/**
	 * Legge un intero che è compreso uguale(min<=x<=max) tra due valori, dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da stampare.
	 * @param minimo Il valore minimo accettabile.
	 * @param massimo Il valore massimo accettabile.
	 * @return L'intero letto.
	 */
	public static int leggiIntero(String messaggio, int minimo, int massimo) {
		
		boolean finito = false;
		int valoreLetto = 0;
		
		do {
			
			valoreLetto = leggiIntero(messaggio);
			
			if (valoreLetto >= minimo && valoreLetto<= massimo)
				finito = true;
			else
				if (valoreLetto < minimo)
					System.out.println(ERRORE_MINIMO + minimo);
				else
					System.out.println(ERRORE_MASSIMO + massimo);
		} while (!finito);
		
		System.out.println();
		
		return valoreLetto;
	}
	
	
	
	/**
	 * Legge un double dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da stampare.
	 * @return Il double letto.
	 */
	public static double leggiDouble (String messaggio) {
		
		boolean finito = false;
		double valoreLetto = 0;
		
		do {
			
			System.out.print(messaggio);
			
			try {
				
				valoreLetto = lettore.nextDouble();
				finito = true;
			}
			
			catch (InputMismatchException e) {
				
				System.out.println(ERRORE_FORMATO);
				@SuppressWarnings("unused")
				String daButtare = lettore.next();
			}
		} while (!finito);
		
		return valoreLetto;
	}
	
	
	
	/**
	 * Legge un double purchè sia maggiore o uguale (>=x) di un certo valore, dopo aver stampato un messaggio.
	 * @param messaggio Il messaggio da stampare.
	 * @param minimo Il valore minimo accettablie.
	 * @return Il double letto.
	 */
	public static double leggiDoubleConMinimo (String messaggio, double minimo) {
		
		boolean finito = false;
		double valoreLetto = 0;
		
		do {
			
			valoreLetto = leggiDouble(messaggio);
			if (valoreLetto >= minimo)
				finito = true;
			else
				System.out.println(ERRORE_MINIMO + minimo);
	    } while (!finito);
		
		return valoreLetto;
	}
	
	
	
	/**
	 * Verifica se l'utente ha risposto in maniera affermativa o negativa a una domanda, dopo aver stamparo un messaggio.
	 * @param messaggio Il messaggio da stampare.
	 * @return True se la risposta è si, False se la risposta è no.
	 */
	public static boolean yesOrNo(String messaggio) {
		
		String mioMessaggio = messaggio + "("+RISPOSTA_SI+"/"+RISPOSTA_NO+")";
		char valoreLetto = leggiUpperChar(mioMessaggio,String.valueOf(RISPOSTA_SI)+String.valueOf(RISPOSTA_NO));
		
		if (valoreLetto == RISPOSTA_SI)
			return true;
		 else
			return false;
	}
}
