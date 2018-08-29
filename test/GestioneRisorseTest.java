package test;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Model;
import view.BibliotecaView;

public class GestioneRisorseTest {

	Controller controller;
	Model model;
	
	@Before
	public void inizializza() {
		model =  new Model();
	 controller = new Controller(model,new BibliotecaView());
	
	}
	
	
	@Test
	public void test() {

	    	
	    	ArrayList<Object> attributi1 = new ArrayList<Object>();
			attributi1.add("5");
			attributi1.add(5);
			attributi1.add(10);
			attributi1.add("5");
			attributi1.add("5");
			attributi1.add("5");
			attributi1.add("5");
			attributi1.add(5);


			model.aggiungiRisorsa(attributi1 ,0,0);
			
	    	ArrayList<Object> attributi2 = new ArrayList<Object>();
			attributi2.add("6");
			attributi2.add(6);
			attributi2.add(6);
			attributi2.add("6");
			attributi2.add("6");
			attributi2.add("6");
			attributi2.add("6");
			attributi2.add(6);


			model.aggiungiRisorsa(attributi2 ,0,0);
			
			//modifica la risorsa 1
			attributi2.set(0, "cambio");
			
			Object[] nuoviAttributi = new Object[attributi2.size()+1];
			
			nuoviAttributi[0] = 1;	//il metodo modifica vuole un array con indicato il nuovo id mentre il metodo aggiungi risorsa no
			
			for(int i= 0; i< attributi2.size() ; i++) {
				nuoviAttributi[i+1]= attributi2.get(i);
			}
			
			model.modificaRisorsa(1,0,nuoviAttributi);
			
			model.getArchivio().rimuoviRisorsa(0, 0);
			
		//	verifico i cambiamenti
			assert model.getNomeRisorsa(0) == null;
			
			assert model.getNomeRisorsa(1).equals("cambio");
			
			assert model.getId(0, 0, 0) == 1 ;
			
			
			
		
	}


}
