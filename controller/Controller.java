package controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.ServizioFile;
import it.unibs.ing.mylib.Stampa;
import model.Model;
import storico.Storico;
import utility.Load;
import utility.Save;
import view.BibliotecaView;
import view.MyView;

@SuppressWarnings("serial")

public class Controller  implements Serializable{
	/**
	 * @invariant invariante() 
	 */
	
	private static final String MESSAGGIO_ERRORE = "Scelta non valida";
	private static final String INPUT_USERNAME = "Inserire username -> ";
	private static final String INPUT_PASSWORD = "Inserire password -> ";
	private static final String ERRORE = "Username o password sabliata!!!";
	private static final String RIPROVA = "Voi riprovare il login?";
	private static final String TITOLO = "Scegli tra le seguenti opzioni";
	private static final String[] VOCI = {"Login","Registrati"};
	private static final String ARRIVEDERCI = "Arrivederci";
	
	private Save save;
	private Load load;
	private Model model;
	private MyView view ;
	private ControllerFruitore controllerFruitore;
	private ControllerOperatore controllerOperatore;
	private ControllerArchivio controllerArchivio;
	
	
	public Controller(Model model , MyView view, Save save, Load load) {
		this.model = model ;
		this.view = view ;
		this.save=save;
		this.load=load;
		controllerArchivio = new ControllerArchivio(model, view);
		controllerFruitore = new ControllerFruitore(model, view,controllerArchivio);
		controllerOperatore = new ControllerOperatore(model, view,controllerArchivio);
		
	}
	
	
	public void start() {

		controllerFruitore.importaFruitori(load);
		controllerOperatore.importaOperatori(load);//caricare fruitori e operatori da file a inizio sessione
		controllerArchivio.importaArchivio(load);//importa archivio da file 
		controllerFruitore.eliminaDecaduti();//cerco decaduti a inizio di ogni sessione
		controllerFruitore.eliminaPrestitiScaduti();
		controllerArchivio.idCorrente();		
		
		view.notify("Benvenuto  ");
		
		int scelta;
		String username;
		
		do {
			scelta = view.scelta(TITOLO, VOCI);
			
			
			switch (scelta) {
				case 0:
					break;
					
				case 1:
					login();
					break;
				
				case 2:
					username = controllerFruitore.iscrizioneFruitore();
					 if (username != "") {
						 controllerFruitore.usaFruitore(username);
					}
					break;
					
				default:
					view.notify(MESSAGGIO_ERRORE);
					break;
			}
			
		} while (scelta != 0);
		
		
		
		controllerFruitore.salvaFruitori(save);
		controllerOperatore.salvaOperatori(save);//salvare fruitori e Operatori a fine sessione su file
		controllerArchivio.salvaArchivio(save);        //salvaArchivio su file
		
		view.notify(BelleStringhe.incornicia(ARRIVEDERCI));
	}
	
	private  void login() {
		boolean finito = false;
		boolean continua = true;
		String username, password;
		
		do {
			username = view.StringaNonVuota(INPUT_USERNAME);
			password = view.StringaNonVuota(INPUT_PASSWORD);
			
			if (controllerFruitore.cercaFruitore(username, password)) {
				controllerFruitore.usaFruitore(username);
				finito = true;
			}else {
				if(controllerOperatore.cercaOperatore(username, password)) {
					controllerOperatore.usaOperatore(username);
					finito = true;
				}else {
					view.notify(BelleStringhe.rigaIsolata(ERRORE));
					continua = view.yesOrNo(RIPROVA);
					if (!continua)
						return;
				}
			}
		}while(!finito);		
	}
	

}
