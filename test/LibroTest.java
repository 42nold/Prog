package test;

import org.junit.Test;

import model.Libro;

public class LibroTest {
	
	Libro libro = new Libro("nom","aut","casa","gen","lin",5,5,5,5);

	

	@Test
	public void matchs() {

		assert libro.matchAutore("aut") ;
		assert !libro.matchAutore("auto") ;
		assert !libro.matchAutore("") ;
		
		assert libro.matchCasaEditrice("casa") ;
		assert !libro.matchCasaEditrice("casas") ;
		assert !libro.matchCasaEditrice("") ;
		
		assert libro.matchPagine(5) ;
		assert !libro.matchPagine(4) ;
		assert !libro.matchPagine(6) ;


	}


}
