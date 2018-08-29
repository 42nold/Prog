package test;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.Stampa;
import model.Model;
import view.BibliotecaView;

public class PrestitoRisorsa {
	Model model;
	Controller controller;
	
	@Before
	public void inizializza() {
		 model = new Model();
		 controller = new Controller(model,new BibliotecaView());
	}
	@Test
	public void testaPrestito() {
		Calendar scadenza = Calendar.getInstance();
		scadenza.add(Calendar.DAY_OF_YEAR,100);
		
		model.addFruitore("a", "a", 30, "a", "a", Calendar.getInstance(), scadenza);
		model.addFruitore("a", "a", 30, "b", "a", Calendar.getInstance(), scadenza);
		
		
		aggiungiRisorsaCasuale(0,0);
		aggiungiRisorsaCasuale(0,1);
		aggiungiRisorsaCasuale(1,0);
		aggiungiRisorsaCasuale(1,1);
		
		nuovoPrestito(0,0);
		nuovoPrestito(0,3);
		nuovoPrestito(1,2);
		nuovoPrestito(1,1);
 
		//verifica che il fruitore 1 abbia in prestito le risorse 0 e 1
		ArrayList<Integer> prestitiPrevisti = new ArrayList<Integer>();
		prestitiPrevisti.add(0);	
		prestitiPrevisti.add(3);
		
        assert model.getFruitore(0).getPrestiti().containsAll(prestitiPrevisti) && prestitiPrevisti.containsAll(model.getFruitore(0).getPrestiti())  ;

        //verifica che il fruitore2 abbia in prestito le risorse 2 e 3
		ArrayList<Integer> prestitiPrevisti2 = new ArrayList<Integer>();
	    prestitiPrevisti2.add(1);  
	    prestitiPrevisti2.add(2);
		
        assert model.getFruitore(1).getPrestiti().containsAll(prestitiPrevisti2) && prestitiPrevisti2.containsAll(model.getFruitore(1).getPrestiti())  ;
        
     
	}
	/**
	 * metodo uguale all'omonimo della classe controller senza interazioni con la view 
	 * @param numFruitore posizione del fruitore 
	 * @param risorsaScelta iddella risorsa
	 */
	public void nuovoPrestito(int numFruitore,int risorsaScelta) {

		boolean rispettaPrerequisiti = model.verificaPrerequisitiPrestito(numFruitore, risorsaScelta);
		
		if (rispettaPrerequisiti) {
			model.aggiornaLicenze(risorsaScelta, 0);// flag per diminuire numeroLicenze
			String descrizioneRisorsa = model.getDescrizioneRisorsa(risorsaScelta);
			
			int durataPrestito = model.durataPrestitoDataUnaRisorsa(risorsaScelta);
			int durataProroga = model.durataProrogaDataUnaRisorsa(risorsaScelta);
			int termineProroga = model.termineProrogaDataUnaRisorsa(risorsaScelta);				
			
			Calendar inizio = Calendar.getInstance();
			Calendar fine = Calendar.getInstance();
			fine.add(Calendar.DAY_OF_YEAR, durataPrestito);
			
			model.richiediPrestitoFruitore(numFruitore,risorsaScelta, descrizioneRisorsa, inizio, fine, durataProroga, termineProroga);
		} 
	}
	
/**
 * aggiungi una risorsa casuale nella categoria e sottocategoria scelte
 * @param cat categoria scelta
 * @param sotto sottocategoria scelta(-1 per nessuna)
 */
    public void aggiungiRisorsaCasuale(int cat,int sotto) {
    	
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
