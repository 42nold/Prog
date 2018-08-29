package test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import model.Archivio;
import risorse.*;

public class ArchivioTest {
	
	Archivio archivio = new Archivio();
	
	@Test
	public void testElencoCategorie() {
		assertEquals("libri", archivio.elencoCategorie()[0].toString());
		assertEquals("film", archivio.elencoCategorie()[1].toString());
	}
	
	@Test
	public void testCategoriaHaRisorse() {
		assertFalse(archivio.categoriaHaRisorse(0));
	}
	
	@Test
	public void testCategoriaHaRisorse1() {
		
		Libro libro = new Libro(0, "0", "tit", "tit", 2000, "2000", 5, "5", "aut", "aut", "gen", "gen", "lin", "lin", "casa", "casa", 100, "100");
		
		int categoria = 0;
		int sottocategoria = 0;
		ArrayList<Object> nuoviAttributi = new ArrayList<>();
		nuoviAttributi.add(libro);
		archivio.aggiungiRisorsa(nuoviAttributi, categoria, sottocategoria);
		
		assertTrue(archivio.categoriaHaRisorse(0));
	}

}