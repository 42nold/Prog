package test;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import model.Evento;
import model.Controller;
import model.Storico;

public class StoricoTest {

	Storico storico = new Storico();

	//String[] eventi = storico.getEventi();
	
	Calendar oggi,annoScorso,annoProssimo;
	
	@Before
	public void inizia() {
		 oggi = Calendar.getInstance();
			
		 annoScorso = Calendar.getInstance();
		annoScorso.add(Calendar.YEAR,-1);
		 
		annoProssimo=  Calendar.getInstance();
		annoProssimo.add(Calendar.YEAR,1);
	}
	
	
	/*@Test
	public void testNumEventoAnnoSolare() {
		
		assert 	storico.numEventoAnnoSolare(eventi[0]," ").equals("");


		for(String evento : eventi) {
			Evento e = new Evento(evento,1);
			e.setData(annoScorso);
			storico.addEvento(e);
			
			assert 	storico.numEventoAnnoSolare(evento," ").equals("2017 : 1  \n");

		}
		
		for(String evento : eventi) {
			storico.addEvento(new Evento(evento,1));

			System.out.println(storico.numEventoAnnoSolare(evento," "));
		assert 	storico.numEventoAnnoSolare(evento," ").equals("2017 : 1  \n2018 : 1  \n");

		}
		
		for(String evento : eventi) {
			Evento e = new Evento(evento,1);
			e.setData(annoProssimo);
			storico.addEvento(e);
			
			
		assert storico.numEventoAnnoSolare(evento," ").equals("2017 : 1  \n2018 : 1  \n2019 : 1  \n");

		}
	
		
	}*/


	@Test
	public void testRisorsaPiuPrestata() {

	
    storico.nuovoPrestito(1,5,"a");
	
	assert storico.risorsaPiuPrestata().equals("2018 : risorsa più prestata : 1");
	
    storico.nuovoPrestito(2,5,"a");

	
    assert storico.risorsaPiuPrestata().equals("2018 : risorse più prestate a pari merito : 1 , 2");

    storico.nuovoPrestito(1,5,"a");
    
	assert storico.risorsaPiuPrestata().equals("2018 : risorsa più prestata : 1");


	}

	@Test
	public void testPrestitiFruitoriAnnoSolare() {

		storico.iscrizioneFruitore("a");
		storico.nuovoPrestito(1, 5, "a");

		assert storico.prestitiFruitoriAnnoSolare().equals("2018 : utente : a -> 1 prestiti \n");
	
		storico.iscrizioneFruitore("b");
		
		assert storico.prestitiFruitoriAnnoSolare().equals("2018 : utente : a -> 1 prestiti \n");


		storico.nuovoPrestito(3, 5, "b");
		storico.nuovoPrestito(3, 5, "b");

		
		assert storico.prestitiFruitoriAnnoSolare().equals("2018 : utente : a -> 1 prestiti \n2018 : utente : b -> 2 prestiti \n");

		
		
	}

}
