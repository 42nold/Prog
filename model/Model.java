package model;

import java.util.*;
import storico.Evento;
import storico.Storico;
import storico.StoricoInterface;
import utenti.Fruitore;
import utenti.Operatore;
import utility.Load;
import utility.Save;

public class Model extends Observable {
	
	private static final String NOMEFILEFRUITORI = "Fruitori.dat";
	
	private static final String NOMEFILEOPERATORI = "Operatori.dat";
	
	public static final String ISCRIZIONE_FRUITORE = "ISCRIZIONE DEL FRUITORE: ";
	
	public static final String FRUITORE_DECADUTO = "E' DECADUTO IL FRUITORE: ";
	
	public static final String RINNOVO_ISCRIZIONE = "ISCRIZIONE RINNOVATA PER IL FRUITORE: ";
	
	public static final String RISORSA_AGGIUNTA = "AGGIUNTA LA RISORSA : ";

	public static final String RISORSA_ELIMINATA = "E' STATA ELIMINATA LA RISORSA : ";

	public static final String NUOVO_PRESTITO = "NUOVO PRESTITO CONCESSO A : ";

	public static final String TERMINE_DISPONIBILITA = "LA RISORSA NON E' PIU' DISPONIBILE AL PRESTITO";

	public static final String PROROGA_PRESTITO = "PROROGA DEL PRESTITO A : ";

	public static final String RISORSA_DISPONIBILE = "LA RISORSA E' NUOVAMENTE DISPONIBILE AL PRESTITO : ";
	
	private static final int VALORE_NULLO_EVENTI = -1;
	
	private Archivio archivio;
	private StoricoInterface storico;
	private static ArrayList<Operatore> operatori;
	private static ArrayList<Fruitore> fruitori; 

	
	public Model() {
		archivio = new Archivio();
		operatori = new ArrayList<Operatore>();
		operatori.add(new Operatore("admin", "admin", 18, "admin", "admin"));
		fruitori = new ArrayList<Fruitore>();
		storico = new Storico();
		addObserver(storico);
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
	  public boolean cercaFruitore(String username, String password) {
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
		//assert invariante();
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

					String utenteEliminato=(fruitori.get(i).getUsername());
					
					fruitori.remove(i);
					
					setChanged();
					notifyObservers(new Evento(FRUITORE_DECADUTO+utenteEliminato, VALORE_NULLO_EVENTI));
				}
			//assert invariante() && operatoriPre == operatori ;
		}
	}

	/**
	 * elimina i prestiti scaduti per oni fruitore iscritto e aggiorna le licenze alle rispettive risorse liberate
	 * @pre true
	 * @post operatoriNoChange()	
	 */
		public  void eliminaPrestitiScaduti() {
			//assert invariante();
			ArrayList<Operatore> operatoriPre = operatori ;
			
			if(fruitori!=null) {
				for (int i=0; i<fruitori.size(); i++) {
					//array contenente gli id delle risorse relative a prestiti scaduti
					ArrayList<Integer> prestitiScaduti = new ArrayList<Integer>();
					
					prestitiScaduti=fruitori.get(i).eliminaPrestitiScaduti();
					int catRisorsa;
					
					if(prestitiScaduti!=null)
						for (int j=0; j<prestitiScaduti.size(); j++) {
							catRisorsa=archivio.trovaIdCategoria(prestitiScaduti.get(j));
							if(catRisorsa==0 || catRisorsa==1)
								archivio.aggiornaLicenze(prestitiScaduti.get(j), 1);      //1 � il mio flag per incrementare numeroLicenze
							
							if(archivio.numeroLicenzeRisorsa(prestitiScaduti.get(j))==1) {
								setChanged();
								notifyObservers(new Evento(RISORSA_DISPONIBILE,prestitiScaduti.get(j)));
							}
						}
				}
				//assert invariante() && operatoriPre == operatori ; 
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
								//assert	invariante() ;	
								ArrayList<Operatore> operatoriPre = operatori ;
								ArrayList<Fruitore> fruitoriPre = fruitori ;
								Archivio archivioPre = archivio ;
							
								Save.salvaDatiSuFile(NOMEFILEFRUITORI, fruitori);
								Save.salvaDatiSuFile(NOMEFILEOPERATORI, operatori);
									
								//assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
								}
						
						/**
						 *  carica da file i fruitori e operatori
						 *  @pre true
						 *  @post true
						 */
							public  void importaFruitoriOperatori() {
										
										 @SuppressWarnings("unchecked")
										ArrayList<Fruitore> a = ( ArrayList<Fruitore>) Load.importaDatiDaFile(NOMEFILEFRUITORI);
										if(a==null) {fruitori = new ArrayList<Fruitore>();}
										else {fruitori=a; }
										
										 @SuppressWarnings("unchecked")
										ArrayList<Operatore> b = ( ArrayList<Operatore>)Load.importaDatiDaFile(NOMEFILEOPERATORI);
									
										if(b==null) {operatori  = new ArrayList<Operatore>();}
										else {operatori=b; }
										
										if(operatori.size()==0) {operatori.add(new Operatore("admin", "admin", 18, "admin", "admin")) ; }     //inizializzazione di default 
																				
										//assert invariante() ;				  
									 }
							
							/** 
							 * salva dati dell'archivio e dello storico su file
							 * @pre true 
							 * @post @nochange
							 */
								public  void salvaArchivio() {
									//assert invariante();
									ArrayList<Operatore> operatoriPre = operatori ;
									ArrayList<Fruitore> fruitoriPre = fruitori ;
									Archivio archivioPre = archivio ;
									
									archivio.salvaDati();	
									storico.salvaDati();
									
									//assert invariante() && operatoriPre == operatori && fruitoriPre == fruitori && archivioPre == archivio ;
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
					
					setChanged();
					notifyObservers(new Evento(ISCRIZIONE_FRUITORE+username,VALORE_NULLO_EVENTI));
				}


				/*
				 * nome autoesplicativo, invocato al verificarsi di un rinnovo di iscrizione
				 * @param numFruitore posizione del fruitore nell'archivio
				 */
				public void aggiornaDataScadenzaFruitore(int numFruitore) {
					 fruitori.get(numFruitore).aggiornaDataScadenza();
					 
					 setChanged();
					 notifyObservers(new Evento(RINNOVO_ISCRIZIONE+fruitori.get(numFruitore).getUsername(),VALORE_NULLO_EVENTI));
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

				public Fruitore getFruitore(int numFruitore) {

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

						Fruitore fruitoreInQuestione = fruitori.get(numFruitore);

					fruitoreInQuestione.richiediPrestito(risorsaScelta, descrizioneRisorsa, inizio, fine, durataProroga, termineProroga);
				
					//controllo degli eventi per gli observers
					setChanged();
					notifyObservers(new Evento(NUOVO_PRESTITO+fruitoreInQuestione.getUsername(),risorsaScelta));
					
					int licenzeRimaste = archivio.numeroLicenzeRisorsa(risorsaScelta);
					if(licenzeRimaste==0) {
						setChanged();
						notifyObservers(new Evento(TERMINE_DISPONIBILITA,risorsaScelta));
					}
				
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

					Fruitore fruitoreInQuestione = fruitori.get(numFruitore);
					int risorsa = inScadenza.get(scegliDaRinnovare-1);
					fruitoreInQuestione.rinnova(risorsa);
					
					setChanged();
					notifyObservers(new Evento(PROROGA_PRESTITO+fruitoreInQuestione.getUsername(),risorsa));
				}



				public String[] elencoCategorie() {

					return archivio.elencoCategorie();
				}

				public int sizeArchivio() {

					return archivio.size();
				}


				public boolean haRisorseEsottocategorie(int categoria) {

					return archivio.haRisorseEsottocategorie(categoria);
				}

				public boolean categoriaHaRisorse(int categoria) {
					return archivio.categoriaHaRisorse(categoria);
				}

				public boolean categoriaHaSottoCategoria(int categoria) {
					return archivio.categoriaHaSottoCategoria(categoria);				}

				public String[] elencoSottoCategorie(int categoria) {
					return archivio.elencoSottoCategorie(categoria);
				}

				public String[] elencoRisorse(int categoria, int sottocategoria) {

					if(sottocategoria==-1) return archivio.elencoRisorse(categoria);
				
					else  return archivio.elencoRisorse(categoria,sottocategoria);
				}
				
				public int getId(int pos, int categoria, int sottocategoria) {
					if(sottocategoria == -1) return archivio.getId(pos,categoria);
					return archivio.getId(pos,categoria,sottocategoria);
				}

				public void rimuoviRisorsa(int id, int categoria) {

					if( archivio.rimuoviRisorsa(id,categoria)) {
						
						setChanged();
						notifyObservers(new Evento(RISORSA_ELIMINATA, id));
						
					}					
				}

				public void aggiungiRisorsa(ArrayList<Object> nuoviAttributi, int categoria, int sottocategoria) throws ClassCastException{


					if(sottocategoria==-1)
						archivio.aggiungiRisorsa(nuoviAttributi, categoria);
					
					else archivio.aggiungiRisorsa(nuoviAttributi, categoria , sottocategoria);

					setChanged();
					notifyObservers(new Evento(RISORSA_AGGIUNTA,archivio.getIdCorrente()));
				}

				
				public ArrayList<String> getDescrizioneCampi(int categoria){
					return archivio.getDescrizioneCampi(categoria);
				}

				public ArrayList<Integer> filtraRisorse(int attributoScelto, Object parametroDiRicerca) throws ClassCastException{
					// TODO Auto-generated method stub
					return    archivio.filtraRisorse(parametroDiRicerca,attributoScelto);

				}

				public String getNomeRisorsa(int r) {
					// TODO Auto-generated method stub
					return archivio.getNomeRisorsa(r);
				}

				public void modificaRisorsa(int id, int categoria, Object[] nuoviAttributi) throws ClassCastException {


					archivio.modificaRisorsa(id,categoria,nuoviAttributi);

				}

				public String showRisorsa(int id, int c) {
					 return archivio.showRisorsa(id,c);
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
		storico.importaDati();
			
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
			archivio.setIdCorrente();
		}

		public boolean verificaPrerequisitiPrestito(int numFruitore, int risorsaScelta) {

			return verificaPrerequisitiPrestito(fruitori.get(numFruitore), risorsaScelta);
		}
		
		public ArrayList<String> getDescrizioneCampiRisorsa(int categoriaScelta){
			return archivio.getDescrizioneCampiRisorsa(categoriaScelta);
		}
			

		public String numEventoAnnoSolare(String nomeEvento, String descrizione) {
			
			return storico.numEventoAnnoSolare(nomeEvento, descrizione,getElencoEventi());
		}

		private String[] getElencoEventi() {
			String[] lista = {ISCRIZIONE_FRUITORE,FRUITORE_DECADUTO,NUOVO_PRESTITO,PROROGA_PRESTITO,RINNOVO_ISCRIZIONE,RISORSA_AGGIUNTA,RISORSA_DISPONIBILE,RISORSA_ELIMINATA,TERMINE_DISPONIBILITA}; 
			return lista;
		}

		public String risorsaPiuPrestata() {

			return storico.maxOccorrenzeEvento(NUOVO_PRESTITO);
		}

		public String prestitiFruitoriAnnoSolare() {

			return storico.prestitiFruitoriAnnoSolare(NUOVO_PRESTITO);
		}

		public String getDescrizioneStorico() {
			// TODO Auto-generated method stub
			return storico.getDescrizione();
		}

		public boolean ArchivioVuoto() {
			return archivio.vuoto();
		}

		public int trovaPosizioneCategoria(int id) {
			return archivio.trovaPosizioneCategoria(id);
		}
		//metodi usati per i test
		
		public int fruitoriSize() {
			return fruitori.size();
		}
		public Archivio getArchivio() {
			return archivio;
		}
		public StoricoInterface getStorico() {
			return storico;
		}
}

