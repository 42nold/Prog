package test;

import org.junit.Before;
import org.junit.Test;

import risorse.Film;


public class FilmTest {
	protected static final String ID="Id";
	protected static final String TITOLO="Titolo";
	protected static final String ANNO="Anno";
	protected static final String NUMEROLICENZE="Numero licenze";	
	private static final String REGISTA="Regista";
	private static final String GENERE="Genere";
	private static final String LINGUA="Lingua";
	private static final String CASAPRODUZIONE="Casa Produzione";
	private static final String DURATA="Durata";
	
	
	Film film ;
	@Before
	public void initializated() {
		film = new Film(5, ID, "tit", TITOLO, 1996, ANNO, 5, NUMEROLICENZE, "reg", REGISTA, "gen", GENERE,
			"lin",LINGUA,"casa",CASAPRODUZIONE,5,DURATA);
	}
	

	@Test
	public void matchs() {

		assert film.match("reg", REGISTA) ;
		assert !film.match("r", REGISTA) ;
		assert !film.match("", REGISTA) ;
		
		assert film.match("casa", CASAPRODUZIONE) ;
		assert !film.match("casas", CASAPRODUZIONE) ;
		assert !film.match("", CASAPRODUZIONE) ;
		
		assert film.match(5, DURATA) ;
		assert !film.match(4, DURATA) ;
		assert !film.match(6, DURATA) ;


	}
	
	

}
