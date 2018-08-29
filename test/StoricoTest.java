package test;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import model.Model;
import storico.Storico;
import view.BibliotecaView;

public class StoricoTest {

	Model model;
	Controller controller;
	Calendar oggi,annoScorso,annoProssimo;
	
	@Before
	public void inizializza() {
		 model = new Model();
		 controller = new Controller(model,new BibliotecaView());
	
		 oggi = Calendar.getInstance();
			
		 annoScorso = Calendar.getInstance();
		annoScorso.add(Calendar.YEAR,-1);
		 
		annoProssimo=  Calendar.getInstance();
		annoProssimo.add(Calendar.YEAR,1);
	}
	
	
	@Test
	public void Consistenza() {
    	
    	ArrayList<Object> attributi = new ArrayList<Object>();
		attributi.add("5");
		attributi.add(1);
		attributi.add(1);
		attributi.add("5");
		attributi.add("5");
		attributi.add("5");
		attributi.add("5");
		attributi.add(1);
		
		model.aggiungiRisorsa(attributi, 0, 0);
		
		assert  model.getStorico().size()==1;    //aggiunta risorsa
		
		Calendar data = Calendar.getInstance();
		data.add(Calendar.DAY_OF_YEAR,100);
		
		model.addFruitore("a", "a", 18, "a", "a", Calendar.getInstance(), data);
		
		assert model.getStorico().size()==2;   //iscrizione fruitore
		
		nuovoPrestito(0,0);
		
		assert model.getStorico().size()==4; //nuovo prestito e disponibilità esaurita per la risorsa
		
		model.aggiungiRisorsa(attributi, 0, 0);
		model.rimuoviRisorsa(1, 0);
		
		assert model.getStorico().size()==6; // aggiunta e rimossa risorsa
		
		data.add(Calendar.DAY_OF_YEAR,-200);
		model.addFruitore("a", "a", 18, "b", "a", Calendar.getInstance(), data);
		
		model.eliminaDecaduti();
		
		assert model.getStorico().size()==8;  //iscrizione fruitore e fruitore decaduto

		ArrayList<Integer> inScadenza = new ArrayList<Integer>();
		inScadenza.add(0);
		
		model.rinnovaPrestitoFruitore(0, inScadenza, 1);
		
		assert model.getStorico().size()==9 ;  // rinnovo prestito
		
		model.aggiornaDataScadenzaFruitore(0);
		
		assert model.getStorico().size()==10 ;    //rinnovoiscrizione fruitore
				
	}

	/**
	 * metodo che crea prestiti già scaduti 
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
			fine.add(Calendar.DAY_OF_YEAR, -100);
			
			model.richiediPrestitoFruitore(numFruitore,risorsaScelta, descrizioneRisorsa, inizio, fine, durataProroga, termineProroga);
		} 
	}
	@Test
	public void testRisorsaPiuPrestata() {

	


	}

	@Test
	public void testPrestitiFruitoriAnnoSolare() {

	
		
		
	}

}
