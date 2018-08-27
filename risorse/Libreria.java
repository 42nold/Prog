package risorse;

import java.util.ArrayList;

public class Libreria extends Categoria<Libro> {
	
	private static final String AUTORE="Autore";
	private static final String GENERE="Genere";
	private static final String LINGUA="Lingua";
	private static final String CASAEDITRICE="Casa Editrice";
	private static final String NUMEROPAGINE="Numero pagine";
	
	private static final int NUM_ATTRIBUTI = 9 ;
	
	private static final int DURATA_PRESTITO_LIBRI = 30;
	private static final int DURATA_PROROGA_LIBRI = 30;
	private static final int TERMINE_PROROGA_LIBRI = 3;
	private static final int MAX_RISORSE_PER_LIBRI= 3;	
	
	
	public Libreria(String nome, int id) {
		
		super(nome, DURATA_PRESTITO_LIBRI, DURATA_PROROGA_LIBRI , TERMINE_PROROGA_LIBRI, MAX_RISORSE_PER_LIBRI, id);
		
		setDescrizioneCampi();
	}
	
	public Libreria (String nome){
		super(nome);
		setDescrizioneCampi();
	}
	
	public void aggiungiRisorsa(ArrayList<Object> attributiNuovaRisorsa) throws ClassCastException {
		
		int attributiRestanti=NUM_ATTRIBUTI-2;
	
		int pagine = (Integer) attributiNuovaRisorsa.get(attributiRestanti--);
		String casa = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		String lin = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		String gen = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		String aut = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		int numLicenze = (Integer) attributiNuovaRisorsa.get(attributiRestanti--);
		int anno = (Integer) attributiNuovaRisorsa.get(attributiRestanti--);
		String titolo = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		
	
		int id = idRisorsa;
		idRisorsa++;
		
		Libro libro = new Libro(id, ID, titolo, TITOLO, anno, ANNO, numLicenze, NUMEROLICENZE, aut, AUTORE, 
				 gen, GENERE, lin, LINGUA, casa, CASAEDITRICE, pagine, NUMEROPAGINE) ;
		risorse.add(libro);
	}
	
	@Override
	protected void setDescrizioneCampi() {
		descrizioneCampi.add(ID);
		descrizioneCampi.add(TITOLO);
		descrizioneCampi.add(ANNO);
		descrizioneCampi.add(NUMEROLICENZE);
		descrizioneCampi.add(AUTORE);
		descrizioneCampi.add(GENERE);
		descrizioneCampi.add(LINGUA);
		descrizioneCampi.add(CASAEDITRICE);
		descrizioneCampi.add(NUMEROPAGINE);
	}
	
	
}
