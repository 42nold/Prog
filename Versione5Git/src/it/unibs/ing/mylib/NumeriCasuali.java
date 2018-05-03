package it.unibs.ing.mylib;



import java.util.*;



public class NumeriCasuali {
	
	private static Random estrattore = new Random();
	
	
	
	/**
	 * Estrae un intero in maniera casuale compresto uguale tra un valore minimo e uno massimo.
	 * @param min Il valore minimo accettabile.
	 * @param max Il valore massimo accettabile.
	 * @return L'intero estratto.
	 */
	public static int estraiIntero(int min, int max) {
		
		int range = max + 1 - min;
		int casual = estrattore.nextInt(range);
		 
		return casual + min;
	}
	
	
	
	/**
	 * Estrae un double in maniera casuale compresto uguale tra un valore minimo e uno massimo.
	 * @param min Il valore minimo accettabile.
	 * @param max Il valore massimo accettabile.
	 * @return Il double estratto.
	 */
	public static double estraiDouble(double min, double max) {
		
		double range = max - min;
		double casual = estrattore.nextDouble();
		double posEstratto = range*casual;
		
		return posEstratto + min;
	}
}
