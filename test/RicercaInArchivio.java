package test;
import model.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import controller.*;
import view.*;
import risorse.*;

public class RicercaInArchivio {

	Model model;
	Controller controller;
	
	@Before
	public void inizializza() {
		 model = new Model();
		 controller = new Controller(model,new BibliotecaView());
	}
	@Test
	public void testSelezione(){
		inizializza();
		
		selezionaRisorsa(0,0,0);
		selezionaRisorsa(0,0,1);
		selezionaRisorsa(0,1,0);
		selezionaRisorsa(1,0,0);
		selezionaRisorsa(1,1,0);
		selezionaRisorsa(1,1,1);
	}
/**
 *  aggiungi una risorsa alla posizione indicata dai parametri 
 * @param cat posizione della categoria dove inserire e cercare la risorsa
 * @param sotto posizione della sottocategoria dove aggiungere la sottocategoria(-1 per metterla in categoria principale)
 * @param pos indica la posizione della risorsa che si vuole scegliere nell'array della categoria
 */
	public void selezionaRisorsa(int cat,int sotto,int pos) {
				
		Archivio archivio = model.getArchivio();
	//crea risorsa con attributi casuali
		ArrayList<Object> attributi = new ArrayList<Object>();
		attributi.add("5");
		attributi.add(5);
		attributi.add(5);
		attributi.add("5");
		attributi.add("5");
		attributi.add("5");
		attributi.add("5");
		attributi.add(5);


		model.aggiungiRisorsa(attributi ,cat, sotto);
		
	
		int idRisorsa = model.scegliRisorsa(cat,sotto,pos);
		
		ArrayList<CategoriaPrimoLivello> categorie = archivio.getCategorie();
		ArrayList<Categoria> sottocategoria = categorie.get(cat).getSottocategorie();
		 
		int idRisorsaVero = sottocategoria.get(sotto).getIdRisorsa(pos);
		
		assert idRisorsa==idRisorsaVero;
											
	}
}
