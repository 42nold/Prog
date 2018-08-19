package model;

import java.io.*;
import java.util.*;
import it.unibs.ing.mylib.*;
import utenti.Fruitore;
import utenti.Operatore;

public class Model {
	private static final String NOMEFILEFRUITORI = "Fruitori.dat";
	private static final String NOMEFILEOPERATORI = "Operatori.dat";
	
	private Archivio archivio;
	private static ArrayList<Operatore> operatori;
	private static ArrayList<Fruitore> fruitori; 
	
	public Model() {
		archivio = new Archivio();
		operatori = new ArrayList<Operatore>();
		fruitori = new ArrayList<Fruitore>();
	}

	private  boolean invariante() {
		
		boolean invariante = false ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;

		if( operatori.size()>0 && fruitori!= null && operatori!= null && archivio!=null) invariante = true ;
	
		assert operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;
		return invariante ;
	}
	/**
	 * verifica se il fruitore relativo a username e password � iscritto
	 * @param username del fruitore
	 * @param password del fruitore
	 * @return true se il fruitore � iscritto
	 * @pre username!=null && password!=null 
	 * @post @nochange
	 */
	public  boolean cercaFruitore(String username, String password) {
		assert invariante() && username!=null && password!=null  ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		int numFruitore;
		
		if((numFruitore = posizioneFruitore(username)) != -1 ) {									
			
			if(fruitori.get(numFruitore).getPassword().equals(password)) {
				
				assert invariante() && operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;
				return true;
			}
		}
		assert invariante() && operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;
		return false;
	}
	
	/**
	 * cerca fruitore tramite username e restituisce posizione nell'array dei fruitori        						
	 * @param username del fruitore
	 * @return posizione del fruitore
	 * @pre username != null 
	 * @post @nochange
	 */
	public  int posizioneFruitore(String username) {
		assert invariante() && username!= null ;
		ArrayList<Operatore> operatoriPre = operatori ;
		ArrayList<Fruitore> fruitoriPre = fruitori ;
		Archivio archivioPre = archivio ;
		
		for (int i=0; i<fruitori.size(); i++) 
			if(fruitori.get(i).getUsername().equals(username)) {
				
				assert invariante() && operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;
				return i;
				}
		assert invariante() && operatoriPre==operatori && fruitoriPre == fruitori && archivioPre == archivio;		
		return -1;
	}
	

	
	/**
	 * elimino fruitori decaduti e aggiorno il numero di licenze delle risorse liberate
	 * @pre true
	 * @post operatoriNoChange()
	 */
	public  void eliminaDecaduti() {
		assert invariante();
		ArrayList<Operatore> operatoriPre = operatori ;
		
		if(fruitori!=null) {
			for (int i=0; i<fruitori.size(); i++) 
				if(fruitori.get(i).getDataScadenza().before(Calendar.getInstance())) {
					
					//array contenente gli id delle risorse relative a prestiti da restituire
					ArrayList<Integer> prestitiDaRestituire = new ArrayList<Integer>();
					
					int catRisorsa;
					prestitiDaRestituire=fruitori.get(i).restituisciRisorseInPrestito();
					if(prestitiDaRestituire!=null)
						for (int j=0; j<prestitiDaRestituire.size(); j++) {
							catRisorsa=archivio.trovaIdCategoria(prestitiDaRestituire.get(i));
							if(catRisorsa==0 || catRisorsa==1)
								archivio.aggiornaLicenze(prestitiDaRestituire.get(j), 1); //1 � il flag per incrementare numeroLicenze
						}

					archivio.storiaFruitoreDecaduto(fruitori.get(i).getUsername());

					fruitori.remove(i);
				}
			assert invariante() && operatoriPre == operatori ;
		}
	}

	/**
	 * elimina i prestiti scaduti per oni fruitore iscritto e aggiorna le licenze alle rispettive risorse liberate
	 * @pre true
	 * @post operatoriNoChange()	
	 */
		public  void eliminaPrestitiScaduti() {
			assert invariante();
			ArrayList<Operatore> operatoriPre = operatori ;
			
			if(fruitori!=null) {
				for (int i=0; i<fruitori.size(); i++) {
					//array contenente gli id delle risorse relative a prestiti scaduti
					ArrayList<Integer> prestitiScaduti = new ArrayList<Integer>();
					
					prestitiScaduti=fruitori.get(i).eliminaPrestitiScaduti();
					int catRisorsa;
					
					if(prestitiScaduti!=null)
						for (int j=0; j<prestitiScaduti.size(); j++) {
							catRisorsa=archivio.trovaIdCategoria(prestitiScaduti.get(i));
							if(catRisorsa==0 || catRisorsa==1)
								archivio.aggiornaLicenze(prestitiScaduti.get(j), 1); //1 � il mio flag per incrementare numeroLicenze
							if(archivio.numeroLicenzeRisorsa(prestitiScaduti.get(j))==1) archivio.risorsaDisponibile(prestitiScaduti.get(j));
						}
				}
				assert invariante() && operatoriPre == operatori ; 
			}
		}
		
	

		/**
		 * ritorna la descrizione di tutti i fruitori iscritti	
		 * @return stringa descrittiva 
		 * @pre true
		 * @post @nochange && @return!= null 
		 */
			public  String elencoFruitori() {
				assert invariante() ;
				ArrayList<Operatore> operatoriPre = operatori ;
				ArrayList<Fruitore> fruitoriPre = fruitori ;
				Archivio archivioPre = archivio ;

				
				StringBuffer descrizione = new StringBuffer();
				for(Fruitore f: fruitori) 
					descrizione.append(f.visualizzaDatiFruitore()+"\n\n");
				String risultato = descrizione.toString();	
			
				assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio && risultato != null;
				return risultato ;
			}
			/**
			 * ritorna la descrizione di tutti i fruitori completa di prestiti per ognuno di essi 
			 * @pre true
			 * @post @nochange && @return!= null
			 * @return
			 */
				public  String elencoFruitoriFull() {
					assert invariante() ;
					ArrayList<Operatore> operatoriPre = operatori ;
					ArrayList<Fruitore> fruitoriPre = fruitori ;
					Archivio archivioPre = archivio ;

					
					StringBuffer descrizione = new StringBuffer();
					for(Fruitore f: fruitori) 
						descrizione.append(f.toString()+"\n\n");
					String risultato = descrizione.toString();	
					
					assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio && risultato != null;
					return risultato;
				}
			

				/**
				 * ritorna la descrizione di tutti gli operatori esistenti
				 * @pre true
				 * @post @return != null && @nochange			
				 * @return la stringa descrittiva
				 */
					public String elencoOperatori() {
						assert invariante() ;
						ArrayList<Operatore> operatoriPre = operatori ;
						ArrayList<Fruitore> fruitoriPre = fruitori ;
						Archivio archivioPre = archivio ;
						
								StringBuffer descrizione = new StringBuffer();
								for(Operatore o: operatori) 
									descrizione.append(o.toString()+"\n\n");
								
								String risultato = descrizione.toString();
								assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio && risultato!= null;
								return 	risultato ;	
							}
					
					/**
					 * salvare fruitori e operatori a fine sessione su file 
					 * @pre true
					 * @post @nochange
					 */
						public  void salvaFruitoriOperatori() {
								assert	invariante() ;	
								ArrayList<Operatore> operatoriPre = operatori ;
								ArrayList<Fruitore> fruitoriPre = fruitori ;
								Archivio archivioPre = archivio ;
								
								
									File g = new File(NOMEFILEOPERATORI);
									ServizioFile.salvaSingoloOggetto(g, operatori);
										
									File f = new File(NOMEFILEFRUITORI);
									ServizioFile.salvaSingoloOggetto(f, fruitori);
								
									
									assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
								}
						
						/**
						 *  carica da file i fruitori e operatori
						 *  @pre true
						 *  @post true
						 */
							public  void importaFruitoriOperatori() {
										
										File f = new File(NOMEFILEFRUITORI);
										@SuppressWarnings("unchecked")
										 ArrayList<Fruitore> a = ( ArrayList<Fruitore>)ServizioFile.caricaSingoloOggetto(f);
										if(a==null) {fruitori = new ArrayList<Fruitore>();}
										else {fruitori=a; }
										
										File g = new File(NOMEFILEOPERATORI);
										@SuppressWarnings("unchecked")
										 ArrayList<Operatore> b = ( ArrayList<Operatore>)ServizioFile.caricaSingoloOggetto(g);
									
										if(b==null) {operatori  = new ArrayList<Operatore>();}
										
										else {operatori=b; }
										
										if(operatori.size()==0) {operatori.add(new Operatore("admin", "admin", 18, "admin", "admin")) ; }     //inizializzazione di default 
										
										
										
										assert invariante() ;				  
									 }
							
							/** 
							 * salva dati dell'archivio e dello storico su file
							 * @pre true 
							 * @post @nochange
							 */
								public  void salvaArchivio() {//e storico
									assert invariante();
									ArrayList<Operatore> operatoriPre = operatori ;
									ArrayList<Fruitore> fruitoriPre = fruitori ;
									Archivio archivioPre = archivio ;
									
									archivio.salvaDati();	
									
									assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
								}
				
								
								/**
								 * 	verifica se � possibile dare la risorsa scelta in prestito al fruitore scelto
								 * @param fruitore scelto
								 * @param risorsaScelta id della risorsa
								 * @return true se il prestito desiderato soddisfa tutti i requisiti
								 * @pre id>=0 fruitore!=null 
								 * @post @nochange
								 */
									private  boolean verificaPrerequisitiPrestito(Fruitore fruitore, int risorsaScelta) {
										assert invariante() && risorsaScelta>=0 && fruitore!= null;
										

										int maxRisorsePerCategoria = archivio.maxRisorsePerCategoriaDataUnaRisorsa(risorsaScelta);
										
										ArrayList<Integer> idRisorseGiaPossedute = fruitore.getPrestiti();
										
										int match=0;
										
										int catRisorsa=archivio.trovaIdCategoria(risorsaScelta);
										int catPosseduta;
										
										for (Integer idInEsame : idRisorseGiaPossedute) {
											catPosseduta=archivio.trovaIdCategoria(idInEsame);
										
											if(catRisorsa==catPosseduta)
												match++;
										}
										
										
										int numLicenze=archivio.numeroLicenzeRisorsa(risorsaScelta);
										
										if( !fruitore.giaPresente(risorsaScelta) && match < maxRisorsePerCategoria && numLicenze>0 ) {
											
											assert invariante() ;
											return true;	
											}
										assert invariante() ;
										return false;
									}
				public void addFruitore(String nome,String cognome,int eta,String username,String password,Calendar data_iscrizione,Calendar data_scadenza) {


					fruitori.add(new Fruitore(nome, cognome, eta, username, password, data_iscrizione, data_scadenza));
				}

				public void storiaIscrizioneFruitore(String username) {
					archivio.storiaIscrizioneFruitore(username);
					
				}

				public void aggiornaDataScadenzaFruitore(int numFruitore) {
					 fruitori.get(numFruitore).aggiornaDataScadenza();
				}

				public void storiaRinnovoIscrizioneFruitore(int numFruitore) {

					archivio.storiaRinnovoIscrizioneFruitore(fruitori.get(numFruitore).getUsername());
				}

				public Calendar getDataScadenzaFruitore(int numFruitore) {
				
					return fruitori.get(numFruitore).getDataScadenza();
				}

				public boolean hasFruitori() {

					if(fruitori==null || fruitori.size()==0) return false;
					return true;
				}

				public void setIdPrestitoFruitore(int numFruitore) {


					fruitori.get(numFruitore).setIdPrestito();
				}

				public String visualizzaPrestitiFruitore(int numFruitore) {

					return fruitori.get(numFruitore).visualizzaPrestitiFruitore();
				}

				public void aggiornaLicenze(int risorsaScelta, int i) {


					archivio.aggiornaLicenze(risorsaScelta, 0); 
				}

				public String getDescrizioneRisorsa(int risorsaScelta) {

					return archivio.getDescrizioneRisorsa(risorsaScelta);
				}

				private Fruitore getFruitore(int numFruitore) {

					return fruitori.get(numFruitore);
				}

				public int durataPrestitoDataUnaRisorsa(int risorsaScelta) {

					return archivio.durataPrestitoDataUnaRisorsa(risorsaScelta);
				}

				public int durataProrogaDataUnaRisorsa(int risorsaScelta) {
				
					return archivio.durataProrogaDataUnaRisorsa(risorsaScelta);
				}

				public int termineProrogaDataUnaRisorsa(int risorsaScelta) {
				
					return archivio.termineProrogaDataUnaRisorsa(risorsaScelta);
				}

				public void richiediPrestitoFruitore(int numFruitore, int risorsaScelta, String descrizioneRisorsa,
					
						Calendar inizio, Calendar fine, int durataProroga, int termineProroga) {


					fruitori.get(numFruitore).richiediPrestito(risorsaScelta, descrizioneRisorsa, inizio, fine, durataProroga, termineProroga);
				}
				
				
				public void storiaNuovoPrestito(int risorsaScelta, int numFruitore) {
					
					archivio.storiaNuovoPrestito(risorsaScelta,archivio.numeroLicenzeRisorsa(risorsaScelta),fruitori.get(numFruitore).getUsername());
				}
				
				
			
				public String getFruitoreUsername(int numFruitore) {

					return fruitori.get(numFruitore).getUsername();
				}

				public ArrayList<Integer> getInScadenzafruitore(int numFruitore) {
					return fruitori.get(numFruitore).inScadenza();

				}

				public String getDescrizionePrestitoFruitore(int numFruitore, ArrayList<Integer> inScadenza, int i) {

					return fruitori.get(numFruitore).getDescrizionePrestito(inScadenza.get(i));
				}

				public void rinnovaPrestitoFruitore(int numFruitore, ArrayList<Integer> inScadenza,
						int scegliDaRinnovare) {


					fruitori.get(numFruitore).rinnova(inScadenza.get(scegliDaRinnovare-1));
				}

				public void prorogaPrestitoFruitore(int numFruitore, ArrayList<Integer> inScadenza,
						int scegliDaRinnovare) {


					archivio.prorogaPrestito(fruitori.get(numFruitore).getUsername(),inScadenza.get(scegliDaRinnovare-1));
				}

			

			

				public boolean ArchivioVuoto() {
					// TODO Auto-generated method stub
					return archivio.vuoto();
				}

				public String[] elencoCategorie() {

					return archivio.elencoCategorie();
				}

				public int sizeArchivio() {

					return archivio.size();
				}

				public String getDescrizioneStorico() {
					return archivio.getDescrizioneStorico();
				}

				public String numEventoAnnoSolare(String evento, String descrizione) {

					return archivio.numEventoAnnoSolare(evento,descrizione);
				}

				public String getEventoNuovoPrestito() {
					return Archivio.NUOVO_PRESTITO;
				}

				public String getEventoProrogaPrestito() {

					return archivio.PROROGA_PRESTITO;
				}

				public String risorsaPiuPrestata() {

					return archivio.risorsaPiuPrestata();
				}

				public String prestitiFruitoriAnnoSolare() {
					return archivio.prestitiFruitoriAnnoSolare();
				}

				public boolean haRisorseEsottocategorie(int categoria) {

					return archivio.haRisorseEsottocategorie(categoria);
				}

				public boolean categoriaHaRisorse(int categoria) {
					return archivio.categoriaHaRisorse(categoria);
				}

				public boolean categoriaHaSottoCategoria(int categoria) {
					// TODO Auto-generated method stub
					return archivio.categoriaHaSottoCategoria(categoria);				}

				public String[] elencoSottoCategorie(int categoria) {
					// TODO Auto-generated method stub
					return archivio.elencoSottoCategorie(categoria);
				}

				public String[] elencoRisorse(int categoria, int sottocategoria) {

					if(sottocategoria==-1) return archivio.elencoRisorse(categoria);
				
					else  return archivio.elencoRisorse(categoria,sottocategoria);
				}
				public int getId(int risorsaDaEliminare, int categoria, int sottocategoria) {
					if(sottocategoria == -1) return archivio.getId(risorsaDaEliminare-1,categoria);
					return archivio.getId(risorsaDaEliminare-1,categoria,sottocategoria);
				}

				public void rimuoviRisorsa(int id, int categoria, int sottocategoria) {


					if(sottocategoria == -1)	archivio.rimuoviRisorsa(id,categoria);		

					else archivio.rimuoviRisorsa(id,categoria,sottocategoria);		

				}

				public void aggiungiRisorsa(String[] attributiStringa, int[] attributinumerici, int categoria,int sottocategoria) {


					if(sottocategoria==-1)
						archivio.aggiungiRisorsa(attributiStringa, attributinumerici, categoria);
					
					else archivio.aggiungiRisorsa(attributiStringa, attributinumerici, categoria , sottocategoria);

				}

				public String[] getAttributiNumericiRisorse(int categoria) {

					return archivio.getAttributiNumericiRisorse( categoria);
				}

				public String[] getAttributiStringaRisorse(int categoria) {

					return archivio.getAttributiStringaRisorse( categoria);
				}

				public ArrayList<Integer> filtraRisorse(int attributoScelto, String chiaveDiRicerca, int numDiRicerca) {
					// TODO Auto-generated method stub
					return    archivio.filtraRisorse(attributoScelto,chiaveDiRicerca,numDiRicerca);

				}

				public String getNomeRisorsa(int r) {
					// TODO Auto-generated method stub
					return archivio.getNomeRisorsa(r);
				}

				public void modificaRisorsa(int id, int categoria, String[] nuoviAttributiStringa,
						int[] nuoviAttributiNumerici) {


					archivio.modificaRisorsa(id,categoria,nuoviAttributiStringa,nuoviAttributiNumerici);

				}

				public String showRisorsa(int id, int c, int s) {
					if(s == -1) return archivio.showRisorsa(id,c);
					else return archivio.showRisorsa(id,c,s);
				}

				public boolean verificaPasswordOperatore(int numOperatore, String password) {

					return operatori.get(numOperatore).getPassword().equals(password);
				}

				public int sizeOperatori() {

					return operatori.size();
				}

				public boolean verificaUsernameOperatore(int i, String username) {
					// TODO Auto-generated method stub
					return operatori.get(i).getUsername().equals(username);
				}

				public void importaDati() {


					archivio.importaDati();
					
				}

				public int scegliRisorsa(int categoriaScelta, int sottoCategoriaScelta, int risorsaSelezionata) {

					if(sottoCategoriaScelta == -1) return archivio.scegliRisorsa(categoriaScelta,risorsaSelezionata);
					return 	archivio.scegliRisorsa(categoriaScelta,sottoCategoriaScelta,risorsaSelezionata);

				}
				/**
				 * triggera il metodo che aggiorna il contatore di id prestiti dell'archivio
				 * @pre true
				 * @post true	
				 */
				public void idCorrente() {


					archivio.idCorrente();
				}

				public boolean verificaPrerequisitiPrestito(int numFruitore, int risorsaScelta) {

					return verificaPrerequisitiPrestito(fruitori.get(numFruitore), risorsaScelta);
				}
}
