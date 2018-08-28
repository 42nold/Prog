package test;

import static org.junit.Assert.*;
import org.junit.Test;
import model.*;

public class ArchivioTest {
	Archivio archivio = new Archivio();
	
	@Test
	public void testElencoCategorie() {
		String[] actual = archivio.elencoCategorie();
		
		assertEquals(2, actual.length);
	}
}
