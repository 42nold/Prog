package dafault;

import java.io.Serializable;


import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.Stampa;

@SuppressWarnings("serial")
public class AppMain implements Serializable{
	
	private static final String INPUT_USERNAME = "Inserire username -> ";
	private static final String INPUT_PASSWORD = "Inserire password -> ";
	private static final String ERRORE = "Username o password sabliata!!!";
	private static final String RIPROVA = "Voi riprovare il login?";
	private static final String TITOLO = "Scegli tra le seguenti opzioni";
	private static final String[] voci = {"Login","Registrati"};
	private static final String MESSAGGIO_ERRORE = "Scelta non valida";
	private static final String ARRIVEDERCI = "Arrivederci";
	
	public static void main(String[] args){
		
		Sistema.importaFruitoriOperatori();//caricare fruitori e operatori da file a inizio sessione
		Sistema.importaArchivio();//importa archivio da file 
		Sistema.eliminaDecaduti();//cerco decaduti a inizio di ogni sessione
		Sistema.eliminaPrestitiScaduti();
		Sistema.idCorrente();		
		System.out.println("Benvenuto  questo è il mio commit");
		
		MyMenu menuPrincipale = new MyMenu(TITOLO, voci);
		int scelta;
		String username;
		
		do {
			scelta = menuPrincipale.scegli();
			
			
			switch (scelta) {
				case 0:
					break;
					
				case 1:
					login();
					break;
				
				case 2:
					username = Sistema.iscrizioneFruitore();
					 if (username != "") {
						Sistema.usaFruitore(username);
					}
					break;
					
				default:
					Stampa.aVideo(MESSAGGIO_ERRORE);
					break;
			}
			
		} while (scelta != 0);
		
		
		
		Sistema.salvaFruitoriOperatori();//salvare fruitori e Operatori a fine sessione su file
		Sistema.salvaArchivio();        //salvaArchivio su file
		
		Stampa.aVideo(BelleStringhe.incornicia(ARRIVEDERCI));
	}
	
	public static void login() {
		boolean finito = false;
		boolean continua = true;
		String username, password;
		
		do {
			username = InputDati.leggiStringaNonVuota(INPUT_USERNAME);
			password = InputDati.leggiStringaNonVuota(INPUT_PASSWORD);
			
			if (Sistema.cercaUtente(username, password) && Sistema.isFruitore(Sistema.posizioneUtente(username))) {
				Sistema.usaFruitore(username);
				finito = true;
			}else {
				if(Sistema.cercaUtente(username, password)&& !Sistema.isFruitore(Sistema.posizioneUtente(username))) {
					Sistema.usaOperatore(username);
					finito = true;
				}else {
					Stampa.aVideo(BelleStringhe.rigaIsolata(ERRORE));
					continua = InputDati.yesOrNo(RIPROVA);
					if (!continua)
						return;
				}
			}
		}while(!finito);
	}
}