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
import view.BibliotecaView;
import view.MyView;

@SuppressWarnings("serial")

public class Controller  implements Serializable{
	/**
	 * @invariant invariante() 
	 */
	
	
	
	
	
	private static final String TITOLO_SELEZIONA_ATTRIBUTO = "Scegli attributo con cui filtrare la ricerca";
	private static final String[] vociMenuSelezionaAttributo = {"Nome","Autore/Regista","Casa editrice/Casa Di produzione","Genere","Lingua","Anno di pubblicazione","Numero di pagine", "Durata"};
	
	
	private static final String[] opzioniDiricerca = {"Ricerca per attributo", "Ricerca navigando l'archivio"};
	private static final String TITOLO_CATEGORIA = "Scegli la categoria";
	private static final String TITOLO_SOTTOCATEGORIA = "Scegli la sottocategoria";
	

	private static final String MESSAGGIO_ERRORE = "Scelta non valida";
	private static final String TITOLO_MENU_OPERATORE = "Menu Operatore.";
	private static final String[] VOCI_MENU_OPERATORE = {"Visualizza dati fruitori", "Visualizza dati e prestiti dei fruitori", "Apri archivio","visualizza storico"};
	private static final String[] OPZIONI_MENU_RICERCA = {"Esplora archivio", "Ricerca per attributo"};
	
	
	
	
	private static final String TITOLO_MENU_STORICO = "scegli l'opzione desiderata";
	private static final String[] vociMenuStorico = {"visualizza storico completo","visualizza numero prestiti per anno solare","visualizza numero proroghe per anno solare","visualizza risorsa prestata pi� volte per anno solare","visualizza i prestiti per fruitore per anno solare"};
	private static final String TITOLO_ELI_O_MOD = "seleziona l'azione desiderata";
	private static final String[] OPZIONI_ELI_O_MOD = {"visualizza", "modifica", "elimina"};
	private static final String INPUT_USERNAME = "Inserire username -> ";
	private static final String INPUT_PASSWORD = "Inserire password -> ";
	private static final String ERRORE = "Username o password sabliata!!!";
	private static final String RIPROVA = "Voi riprovare il login?";
	private static final String TITOLO = "Scegli tra le seguenti opzioni";
	private static final String[] VOCI = {"Login","Registrati"};
	private static final String ARRIVEDERCI = "Arrivederci";
	

	private Model model;
	private MyView view ;
	private ControllerFruitore controllerFruitore;
	private ControllerOperatore controllerOperatore;
	private ControllerArchivio controllerArchivio;
	
	
	public Controller(Model model , MyView view) {
		this.model = model ;
		this.view = view ;
		controllerArchivio = new ControllerArchivio(model, view);
		controllerFruitore = new ControllerFruitore(model, view,controllerArchivio);
		controllerOperatore = new ControllerOperatore(model, view,controllerArchivio);
		
	}
	
	
	public void start() {

		controllerFruitore.importaFruitori();
		controllerOperatore.importaOperatori();//caricare fruitori e operatori da file a inizio sessione
		controllerArchivio.importaArchivio();//importa archivio da file 
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
		
		
		
		controllerFruitore.salvaFruitori();
		controllerOperatore.salvaOperatori();//salvare fruitori e Operatori a fine sessione su file
		controllerArchivio.salvaArchivio();        //salvaArchivio su file
		
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
