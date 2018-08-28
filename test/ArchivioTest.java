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
		
		Libro libro = new Libro(0, null, null, null, 0, null, 0, null, null, null, null, null, null, null, null, null, 0, null);
		
		int categoria = 0;
		int sottocategoria = 0;
		ArrayList<Object> nuoviAttributi = new ArrayList<>();
		nuoviAttributi.add(libro);
		archivio.aggiungiRisorsa(nuoviAttributi, categoria, sottocategoria);
		
		assertTrue(archivio.categoriaHaRisorse(0));
	}

}
