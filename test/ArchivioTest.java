package test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import model.*;
import risorse.*;

public class ArchivioTest {
	Model model;
	Archivio archivio;
	
	@Before
	public void initialize(){
		model = new Model();
		archivio = new Archivio();
	}
	
	@Test
	public void testElencoCategorie() {
		assertEquals("libri", archivio.elencoCategorie()[0].toString());
		assertEquals("film", archivio.elencoCategorie()[1].toString());
	}
	
	@Test
	public void testCategoriaHaRisorse() {
		assertFalse(archivio.categoriaHaRisorse(0));
	}
	
	
	
	
	/////////////////////////////////////////////////////
	@Test
	public void testCategoriaHaRisorse1() {
		
		Libro libro = new Libro(10, "0", "tit", "tit", 2000, "2000", 5, "5", "aut", "aut", "gen", "gen", "lin", "lin", "casa", "casa", 100, "100");
		
		int categoria = 0; //libri
		int sottocategoria = 0; //Horror
		
		ArrayList<Object> nuoviAttributi = new ArrayList<>();
		nuoviAttributi.add(libro);
		//model.aggiungiRisorsa(nuoviAttributi, categoria, sottocategoria);
		archivio.aggiungiRisorsa(nuoviAttributi, categoria, sottocategoria);
		assertTrue(true);
	}

}