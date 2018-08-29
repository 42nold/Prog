package test;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import utenti.Prestito;

public class PrestitoTest {

	Prestito prestito ;
	@Before
	public void inizia() {
		Calendar scadenza = Calendar.getInstance();
		scadenza.add(Calendar.DAY_OF_YEAR, 1);
		prestito =new Prestito(5,5,"des",Calendar.getInstance(),scadenza,5,5);
	}
	
	@Test
	public void testInScadenza() {
		
		assert !prestito.inScadenza();
		
		aggiungiFine(prestito.getTermineProroga()-1);
		
		assert prestito.inScadenza();
		
		
		
	}
	
	@Test
	public void testScaduto() {

		appenaScaduto();
		aggiungiFine(-1);
		
		assert prestito.scaduto();

		aggiungiFine(2);
		
		assert !prestito.scaduto();
		
		
	}
	

private void aggiungiFine(int giorni) {
	Calendar fine=prestito.getFine();
	fine.add(Calendar.DAY_OF_MONTH,giorni);
	prestito=new Prestito(5,5,"des",prestito.getInizio(),fine,5,5);
}

private void appenaScaduto() {
	prestito=new Prestito(5,5,"des",Calendar.getInstance(),Calendar.getInstance(),5,5);


}

}
