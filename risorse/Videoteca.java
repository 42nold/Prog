package risorse;

import java.util.ArrayList;



@SuppressWarnings("serial")
public class Videoteca extends Categoria<Film>{

	private static final String REGISTA="Regista";
	private static final String GENERE="Genere";
	private static final String LINGUA="Lingua";
	private static final String CASAPRODUZIONE="Casa Produzione";
	private static final String DURATA="Durata";
	
	private static final int NUM_ATTRIBUTI = 9 ;
	
	private static final int DURATA_PRESTITO_FILM = 15;
	private static final int DURATA_PROROGA_FILM = 15;
	private static final int TERMINE_PROROGA_FILM = 2;
	private static final int MAX_RISORSE_PER_FILM= 2;
	
	
	public Videoteca (String nome, int id) {
		super(nome, DURATA_PRESTITO_FILM,  DURATA_PROROGA_FILM, TERMINE_PROROGA_FILM, MAX_RISORSE_PER_FILM, id);
		
		setDescrizioneCampi();
	}
	
	public Videoteca(String nome) {
		super(nome);
		setDescrizioneCampi();
	}
	
	@Override
	protected void setDescrizioneCampi() {
		descrizioneCampi.add(ID);
		descrizioneCampi.add(TITOLO);
		descrizioneCampi.add(ANNO);
		descrizioneCampi.add(NUMEROLICENZE);
		descrizioneCampi.add(REGISTA);
		descrizioneCampi.add(GENERE);
		descrizioneCampi.add(LINGUA);
		descrizioneCampi.add(CASAPRODUZIONE);
		descrizioneCampi.add(DURATA);
	}
	
	public void aggiungiRisorsa(ArrayList<Object> attributiNuovaRisorsa) throws ClassCastException{
		/*assert invarianteC() && attributiStringa.length==NUM_ATTRIBUTI_STRINGA && attributiNumerici.length == NUM_ATTRIBUTI_NUMERICI+1;//+1 comprende anche il num di licenze
		int libriPre = risorse.size();
		int idPre = idRisorsa;*/
		
		int attributiRestanti=NUM_ATTRIBUTI-2;
		
		int durata = (Integer) attributiNuovaRisorsa.get(attributiRestanti--);
		String casa = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		String lin = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		String gen = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		String reg = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		int numLicenze = (Integer) attributiNuovaRisorsa.get(attributiRestanti--);
		int anno = (Integer) attributiNuovaRisorsa.get(attributiRestanti--);
		String titolo = (String) attributiNuovaRisorsa.get(attributiRestanti--);
		
		int id = idRisorsa;
		idRisorsa++;
		
		Film film = new Film(id, ID, titolo, TITOLO, anno, ANNO, numLicenze, NUMEROLICENZE, reg, REGISTA, 
				 gen, GENERE, lin, LINGUA, casa, CASAPRODUZIONE, durata, DURATA) ;
		risorse.add(film);
		
		//assert invarianteC() && (risorse.size()==libriPre+1 ) && idRisorsa==idPre+1 ;
	}
	
}
