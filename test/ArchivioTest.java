package test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import model.*;
import risorse.*;

public class ArchivioTest {
	Archivio archivio;
	
	@Before
	public void initialize(){
		archivio = new Archivio();
		
		int categoria = 0; //libri
		int sottocategoria = 0; //Horror
		
		ArrayList<Object> nuoviAttributi = new ArrayList<>();
		
		nuoviAttributi.add("5");
		nuoviAttributi.add(5);
		nuoviAttributi.add(5);
		nuoviAttributi.add("5");
		nuoviAttributi.add("5");
		nuoviAttributi.add("5");
		nuoviAttributi.add("5");
		nuoviAttributi.add(5);
		
		nuoviAttributi.add("5");
		nuoviAttributi.add(5);
		nuoviAttributi.add(5);
		nuoviAttributi.add("5");
		
		archivio.aggiungiRisorsa(nuoviAttributi, categoria, sottocategoria);
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
	
	@Test
	public void categoriaHaSottoCategoria() {
		assertTrue(archivio.categoriaHaSottoCategoria(0));
	}
	
	@Test
	public void elencoSottoCategorie() {
		assertTrue(archivio.categoriaHaSottoCategoria(0));
	}
	
	@Test
	public void testCategoriaHaRisorse1() {
		//System.out.println(archivio.getDescrizioneRisorsa(0));
		assertFalse(archivio.categoriaHaRisorse(0));
		assert archivio.getDescrizioneRisorsa(0) != null;
		
	}
	
	
	
}