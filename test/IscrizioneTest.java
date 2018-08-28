package test;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import controller.Controller;
import it.unibs.ing.mylib.BelleStringhe;
import junit.framework.Assert;
import model.Model;
import utenti.Fruitore;
import view.BibliotecaView;
import view.MyView;

public class IscrizioneTest {

	Controller controller;
	Model model;
	
	@Before
	public void inizializza() {
		model =  new Model();
	 controller = new Controller(model,new BibliotecaView());
	
	}
	
	@Test
	public void testaIscrizione() {
		iscrivi("a","b","c","d",30);
		
		Fruitore utente = model.getFruitore(0);
		
		assert model.getFruitoreUsername(0).equals("d");
		assert utente.getNome().equals("a");
		assert utente.getCognome().equals("b");
		assert utente.getPassword().equals("c");
		assert utente.getEta()==30;

//iscrivo un'utente con stesso username
		
		iscrivi("z","z","z","d",18);
		
		assert model.fruitoriSize()==1;
		
//iscrivo un utente uguale tranne che per l'username
		
		iscrivi("a","b","c","e",30);

	    Fruitore utente2 = model.getFruitore(0);
		
		assert model.getFruitoreUsername(1).equals("e");
		assert utente.getNome().equals("a");
		assert utente.getCognome().equals("b");
		assert utente.getPassword().equals("c");
		assert utente.getEta()==30;
		
		
	}
	
	/**
	 * stesso metodo del controller per avviare iscrizione di nuovo fruitore, al posto delle acquisizioni da tastiera ci sono i parametri rispettivi
	 * @param nome
	 * @param cognome
	 * @param password
	 * @param username
	 * @param eta
	 */
	private void iscrivi(String nome, String cognome, String password ,String username,int eta){
		
		
		Calendar data_iscrizione, data_scadenza;
		boolean finito=false;
		
		if(eta<18) {
			
			return ;
		}	

		if (model.posizioneFruitore(username) == -1) {
				
		data_iscrizione=Calendar.getInstance();
		data_scadenza=Calendar.getInstance();
		data_scadenza.add(Calendar.YEAR, 5);
		
		model.addFruitore(nome, cognome, eta, username, password, data_iscrizione, data_scadenza);
			}
			
	}
	
	
	
}
