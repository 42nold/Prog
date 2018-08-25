package test;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import storico.Evento;
import storico.Storico;

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

	


	}

	@Test
	public void testPrestitiFruitoriAnnoSolare() {

	
		
		
	}

}
