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
		
		assert (fru.giaPresente(1)); // qui fallisce sia se fai assert true che assert false;
	}

	
}
