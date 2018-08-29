package test;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Model;
import view.BibliotecaView;

public class LoadSaveTest {

	Model model;
	Controller controller;

	@Before
	public void inizializza() {
		 model = new Model();
		 controller = new Controller(model,new BibliotecaView());
	}
	
	
	@Test
	public void test() {
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_YEAR,100);
		model.addFruitore("a", "b", 18, "c", "d", Calendar.getInstance(), data);
		model.addFruitore("a", "b", 18, "e", "d", Calendar.getInstance(), data);
		
		//aggiunge una risorsa in ogni sottocategoria .tenere presente gli id che vengono assegnati
		for(int i=0 ;i<2;i++) {
			for(int j=0 ; j<2 ; j++) {
				
				aggiungiRisorsa(i,j);
			}
		}
		
		model.salvaArchivio();
		model.salvaFruitoriOperatori();
		
		Model nuovoModel = new Model();
		
		nuovoModel.importaDati();
		nuovoModel.importaFruitoriOperatori();
		
		//verifica che ci sia la stessa risorsa nella stessa posizione
		for(int i=0 ;i<2;i++) {
			for(int j=0 ; j<2 ; j++) {
				
				assert model.getArchivio().getId(0,i,j)==nuovoModel.getArchivio().getId(0, i, j);
			}
		}
		//verifica anche i fruitori
		assert model.getFruitoreUsername(0).equals(nuovoModel.getFruitoreUsername(0));
		assert model.getFruitoreUsername(1).equals(nuovoModel.getFruitoreUsername(1));
		
		//verifica lo storico
		assert nuovoModel.getDescrizioneStorico().equals(model.getDescrizioneStorico());
	}
	
    public void aggiungiRisorsa(int cat,int sotto) {
    	
    	ArrayList<Object> attributi = new ArrayList<Object>();
		attributi.add("5");
		attributi.add(5);
		attributi.add(10);
		attributi.add("5");
		attributi.add("5");
		attributi.add("5");
		attributi.add("5");
		attributi.add(5);


		model.aggiungiRisorsa(attributi ,cat, sotto);
    }
}
