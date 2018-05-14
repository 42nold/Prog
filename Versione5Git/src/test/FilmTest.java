package test;

import org.junit.Before;
import org.junit.Test;

import dafault.Film;

public class FilmTest {

	Film film ;
	@Before
	public void inizializza() {
		film= new Film("nome", "reg", "casa", "gen","lin",5,5,5,5);
	}
	@Test
	public void matches() {

		assert film.matchRegista("reg") ;
		assert !film.matchRegista("res") ;
		assert !film.matchRegista("") ;
		
		assert film.matchCasaProduzione("casa") ;
		assert !film.matchCasaProduzione("casas") ;
		assert !film.matchCasaProduzione("") ;
		
		assert film.matchDurata(5) ;
		assert !film.matchDurata(4) ;
		assert !film.matchDurata(6) ;


	}
	
	

}
