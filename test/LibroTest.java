package test;

import org.junit.Test;

import risorse.Libro;

public class LibroTest {
	private static final String ID="Id";
	private static final String TITOLO="Titolo";
	private static final String ANNO="Anno";
	private static final String NUMEROLICENZE="Numero licenze";	
	private static final String AUTORE="Autore";
	private static final String GENERE="Genere";
	private static final String LINGUA="Lingua";
	private static final String CASAEDITRICE="Casa Editrice";
	private static final String NUMEROPAGINE="Numero pagine";
	Libro libro ;
	
	public void initializated() {
	 libro = new Libro(5, ID, "tit", TITOLO, 1996, ANNO, 5, NUMEROLICENZE, "aut", AUTORE, "gen", GENERE,
			"lin",LINGUA,"casa",CASAEDITRICE,5,NUMEROPAGINE);
	}

	@Test
	public void matchs() {

		assert libro.match("aut", AUTORE) ;
		assert !libro.match("auto", AUTORE) ;
		assert !libro.match("", AUTORE) ;
		
		assert libro.match("casa", CASAEDITRICE) ;
		assert !libro.match("casas", CASAEDITRICE) ;
		assert !libro.match("", CASAEDITRICE) ;
		
		assert libro.match(5, NUMEROPAGINE) ;
		assert !libro.match(4, NUMEROPAGINE) ;
		assert !libro.match(6, NUMEROPAGINE) ;


	}



}
