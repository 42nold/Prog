package test;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import controller.*;
import model.*;
import view.*;


public class RicercaPerAttributo {
	Model model = new Model();
	MyView view = new BibliotecaView();
	
	Controller controller = new Controller(model,view);
	
	@Before
	public void inizializzazione() {
		Calendar data_iscrizione = Calendar.getInstance();
		Calendar data_scadenza = Calendar.getInstance();
		data_scadenza.add(Calendar.YEAR, 5);
		
		model.addFruitore("a", "a", 33, "a", "a", data_iscrizione, data_scadenza);
	}
	

	@Test
	public void test() {
		//controller.usaFruitore("a"); questo metodo stampa a video cosa che non ha senso nel test
		
	}

}
