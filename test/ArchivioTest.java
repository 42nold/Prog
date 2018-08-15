package test;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Archivio;

public class ArchivioTest {

	Archivio archivio = new Archivio();
	@Test
	public void testElencoCategorie() {
		String[] expected = {"Libri","Film"};
		
		System.out.println(archivio.elencoCategorie());
     assert     archivio.elencoCategorie()==expected ;
		
		
	}

	@Test
	public void testCercaPerAttributoOmode() {
		fail("Not yet implemented");
	}

	@Test
	public void testAggiungiCategoria() {
		fail("Not yet implemented");
	}

	@Test
	public void testEliminaCategoria() {
		fail("Not yet implemented");
	}

	@Test
	public void testCercaPerAttributoFmode() {
		fail("Not yet implemented");
	}

	@Test
	public void testDurataPrestitoDataUnaRisorsa() {
		fail("Not yet implemented");
	}

	@Test
	public void testDurataProrogaDataUnaRisorsa() {
		fail("Not yet implemented");
	}

	@Test
	public void testTermineProrogaDataUnaRisorsa() {

}

}
