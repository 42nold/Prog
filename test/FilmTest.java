package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.Film;

public class FilmTest {
	Film flm = new Film("flm", "regista", "casa", "genere", "lin", 2000, 100, 5, 5);
	
	@Test
	public void testModifica() {
		String[] nuoveStringhe = {"nuovoNome", "nuovoGenere", "nuovaLingua", "nuovoRegista", "nuovaCasa", "10/10/1000"};
		int[] nuoviNumeri = {1234, 200};
		flm.modifica(nuoveStringhe, nuoviNumeri);
		
		assertTrue(flm.matchAnno(1234));
		assertTrue(flm.matchCasaProduzione("nuovaCasa"));
		assertTrue(flm.matchDurata(200));
		assertTrue(flm.matchGenere("nuovoGenere"));
		assertTrue(flm.matchLingua("nuovaLingua"));
		assertTrue(flm.matchNome("nuovoNome"));
		assertTrue(flm.matchRegista("nuovoRegista"));
	}
	
	@Test
	public void testDecrementaLicenze() {
		flm.decrementaLicenze();
		
		assertEquals(4, flm.getNumeroLicenze());
		assertEquals(4, flm.getNumLicenze());
	}

	@Test
	public void testIncrementaLicenze() {
		flm.incrementaLicenze();
		assertEquals(6, flm.getNumeroLicenze());
		assertEquals(6, flm.getNumLicenze());
	}
}