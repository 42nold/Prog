/**
 * 
 */
package test;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.*;
import utenti.Fruitore;
import utenti.Prestito;
/**
 * @author Arnold
 *
 */
public class FruitoreTest{
	
	Calendar iscrizioneIscrizione = Calendar.getInstance();
	Calendar scandezaIscrizione = Calendar.getInstance();
	
	Calendar inizioPrestito1 = Calendar.getInstance();
	Calendar finePrestito1 = Calendar.getInstance();
	
	Calendar inizioPrestito2 = Calendar.getInstance();
	Calendar finePrestito2 = Calendar.getInstance();
	
	public void setUpDate(){
		iscrizioneIscrizione.set(2018, 0, 1);
		scandezaIscrizione.set(2023, 0, 1);
		
		inizioPrestito1.set(2018, 5, 7);
		finePrestito1.set(2018, 6, 7);
		
		inizioPrestito2.set(2018, 4, 7);
		finePrestito2.set(2018, 5, 7);
	}
	
	Fruitore fru = new Fruitore("nome", "cognome", 20, "a", "a", iscrizioneIscrizione, scandezaIscrizione);
	Prestito pre1 = new Prestito(1, 1, "Prestito1", inizioPrestito1, finePrestito1, 30, 5);
	Prestito pre2 = new Prestito(2, 2, "Prestito2", inizioPrestito2, finePrestito2, 30, 5);
	
	public void setUpFruitore(){
		ArrayList<Prestito> prestiti = new ArrayList<Prestito>();
		prestiti.add(pre1);
		prestiti.add(pre2);
		
		fru.setPrestiti(prestiti);
	}
	
	/**
	 * Test method for {@link utenti.Fruitore#giaPresente(int)}.
	 */
	@Test
	public void testGiaPresente() {
		setUpDate();
		setUpFruitore();
		
		assertFalse(fru.giaPresente(1)); // qui fallisce sia se fai assert true che assert false;
	}

	/**
	 * Test method for {@link utenti.Fruitore#getDataScadenza()}.
	 */
	@Test
	public void testGetDataScadenza() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#aggiornaDataScadenza()}.
	 */
	@Test
	public void testAggiornaDataScadenza() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#richiediPrestito(int, java.lang.String, java.util.Calendar, java.util.Calendar, int, int)}.
	 */
	@Test
	public void testRichiediPrestito() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#visualizzaDatiFruitore()}.
	 */
	@Test
	public void testVisualizzaDatiFruitore() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#visualizzaPrestitiFruitore()}.
	 */
	@Test
	public void testVisualizzaPrestitiFruitore() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#setPrestiti(java.util.ArrayList)}.
	 */
	@Test
	public void testSetPrestiti() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#getNumeroPrestiti()}.
	 */
	@Test
	public void testGetNumeroPrestiti() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#inScadenza()}.
	 */
	@Test
	public void testInScadenza() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#rinnova(java.lang.Integer)}.
	 */
	@Test
	public void testRinnova() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#eliminaPrestitiScaduti()}.
	 */
	@Test
	public void testEliminaPrestitiScaduti() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#restituisciRisorseInPrestito()}.
	 */
	@Test
	public void testRestituisciRisorseInPrestito() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#getDescrizionePrestito(java.lang.Integer)}.
	 */
	@Test
	public void testGetDescrizionePrestito() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#setData_iscrizione(java.util.Calendar)}.
	 */
	@Test
	public void testSetData_iscrizione() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#setData_scadenza(java.util.Calendar)}.
	 */
	@Test
	public void testSetData_scadenza() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Fruitore#getPrestiti()}.
	 */
	@Test
	public void testGetPrestiti() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Utente#Utente(java.lang.String, java.lang.String, int, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testUtente() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Utente#invarianteU()}.
	 */
	@Test
	public void testInvarianteU() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Utente#getUsername()}.
	 */
	@Test
	public void testGetUsername() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link utenti.Utente#getPassword()}.
	 */
	@Test
	public void testGetPassword() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#Object()}.
	 */
	@Test
	public void testObject() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#getClass()}.
	 */
	@Test
	public void testGetClass() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#equals(java.lang.Object)}.
	 */
	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#clone()}.
	 */
	@Test
	public void testClone() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#toString()}.
	 */
	@Test
	public void testToString1() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notify()}.
	 */
	@Test
	public void testNotify() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#notifyAll()}.
	 */
	@Test
	public void testNotifyAll() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait()}.
	 */
	@Test
	public void testWait() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long)}.
	 */
	@Test
	public void testWaitLong() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#wait(long, int)}.
	 */
	@Test
	public void testWaitLongInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link java.lang.Object#finalize()}.
	 */
	@Test
	public void testFinalize() {
		fail("Not yet implemented");
	}

}
