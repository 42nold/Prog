package storico;

import java.io.Serializable;
import java.util.ArrayList; 
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;
import utility.Load;
import utility.Save;

@SuppressWarnings("serial")

public class Storico implements Serializable, Observer{
	

	/**
	 * rappresentazione di una linea del tempo composta da eventi
	 * @invariant invariante()
	 */
	public static final String NOMEFILESTORICO = "storico.dat";
	
	private static ArrayList<Evento> storia;


	 /**
	  * inizializza l'array storia con un array vuoto
	  */
	public Storico () {
		storia  = new ArrayList<Evento>() ;
		assert invariante();
	}

	/**
	 * restituisce una stringa con la descrizione di ogni evento nell'array storia
	 * @pre true
	 * @post @nochange
	 * @return stringa con elenco di eventi 
	 */
	public static  String getDescrizione() {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		StringBuffer a = new StringBuffer();
		for(Evento evento : storia) {
			a.append(evento.toString()+"\n");
			
		}
		
		assert(storiaPre.equals(storia))	;
		assert invariante() ;

		
		return a.toString() ;
	}
	/**
	 * salva l'array storia su file esterno
	 * @pre true
	 * @post @nochange
	 */
	public void salvaDati() {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		Save.salvaDatiSuFile(NOMEFILESTORICO, storia);
	
		assert storiaPre==storia;
		assert invariante() ;

	}
	/**
	 * carica l'array storia da file esterno
	 * @pre true
	 * @post invariante()
	 */
	public void importaDati() {
		
		ArrayList<Evento> b =(ArrayList<Evento>) Load.importaDatiDaFile(NOMEFILESTORICO);
				
		if(b==null) 
			storia = new ArrayList<Evento>();
		else 
			storia=b;
		
	}
	/**
	 * verifica che l'array storia non sia nullo
	 * @pre true
	 * @post @nochange
	 * @return true se l'array storia non è nullo 
	 */
	private static boolean invariante() {
		ArrayList<Evento> storiaPre = storia;
	
		boolean invariante = false ;
		if (storia!=null) invariante=true ;
		
		assert storiaPre==storia;

		return invariante;
	}

	public static String numEventoAnnoSolare(String nomeEvento, String descrizione,String[] nomiEventi) {
		assert invariante() ;
		assert eventoValido(nomiEventi,nomeEvento);
		ArrayList<Evento> storiaPre = storia;
		
		
		StringBuffer statistica = new StringBuffer();
		int annoIndice ;
		int count = 0 ;
		int i = 0 ;
		if(storia.size()>0) {
			annoIndice=storia.get(0).getData().get(Calendar.YEAR);
				for(Evento evento :storia) {
					int anno = evento.getData().get(Calendar.YEAR);
					if(evento.getDescrizione().contains(nomeEvento)&&anno==annoIndice) count++;
					if(evento.getDescrizione().contains(nomeEvento)&&anno>annoIndice) {
						statistica.append(annoIndice+" : "+count+descrizione+"\n");
						annoIndice=anno;
						count=1;
					}
					if(i==storia.size()-1) {
						statistica.append(annoIndice+" : "+count+" "+descrizione+"\n");
					}
					i++;
					}
			}
		
		assert storiaPre==storia;
		assert invariante() ;
		
		return statistica.toString();
	}
	/**
	 * restituisce una stringa con l'id delle risorse più prestate per anno solare
	 * @pre true
	 * @post @nochange
	 * @return stringa col risultato della statistica
	 */
	public static String maxOccorrenzeEvento(String nomeEvento) {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		StringBuffer statistica=new StringBuffer();
		
	    ArrayList<Integer> prestitiAnno = new ArrayList<Integer>();
	    
	    if (storia.size()>0){
	    	
	    	int anno = storia.get(0).getData().get(Calendar.YEAR);
	    	
	    	for( int annoIndice=anno ; annoIndice<=Calendar.getInstance().get(Calendar.YEAR); annoIndice++) {
	    		for(Evento evento : storia) {
	    			if(evento.getDescrizione().contains(nomeEvento)&&annoIndice==evento.getData().get(Calendar.YEAR)) prestitiAnno.add(evento.getValore()) ;
	    		}
	    		if (prestitiAnno.size()>0) {
	    			ArrayList<String> pariMerito = new ArrayList<String>() ;
	    			int risorsaMax=-1;
	    			int risorsaAtt=prestitiAnno.get(0);
	    			int countMax=0;
	    			int countAtt=0;
	    
	    			do {
	    	
	    				for(int i =0 ; i<prestitiAnno.size();i++) {
	    		
	    					if(prestitiAnno.get(i)==risorsaAtt) {
	    						countAtt++;
	    						prestitiAnno.remove(i);
	    						i--;
	    					}	
	    				}
	    				if(countAtt==countMax) pariMerito.add(""+risorsaAtt);
	    				if(countAtt>countMax) {risorsaMax=risorsaAtt; countMax=countAtt ; pariMerito = new ArrayList<String>();}
	    				if(prestitiAnno.size()>0) risorsaAtt=prestitiAnno.get(0);
	    				countAtt=0;
	    			}while(prestitiAnno.size()>0) ;
	    			if(pariMerito.size()>0) {
	    				statistica.append( anno+" : risorse più prestate a pari merito : "+risorsaMax );
	    			for(String ris : pariMerito)
	    				statistica.append(" , "+ris);
	    			}
	    			else statistica.append(anno+" : risorsa più prestata : "+risorsaMax);
	    		}
	    	}
	    }	    
	   
		assert storiaPre==storia;
		assert invariante() ;
		
	    return statistica.toString();
	
	}
	/**
	 * restituisce una stringa con i prestiti ottenuti per fruitore per anno solare
	 * @pre true
	 * @post @nochange
	 * @return stringa col risultato della statistica
	 */
	public static String prestitiFruitoriAnnoSolare(String eventoPrestito) {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		StringBuffer statistica = new StringBuffer();
		if(storia.size()>0) {
			
			int annoIndice =storia.get(0).getData().get(Calendar.YEAR); 
		
			for( int i=annoIndice ; i <= Calendar.getInstance().get(Calendar.YEAR) ; i++){
		
				ArrayList<String> eventiAnnoIndice = new ArrayList<String>();

				for(Evento evento : storia) {
			
					if(evento.getDescrizione().contains(eventoPrestito)&&evento.getData().get(Calendar.YEAR)==i) eventiAnnoIndice.add(evento.getDescrizione().substring(eventoPrestito.length()));
				}
					while(eventiAnnoIndice.size()>0) {
						String utenteCorrente = eventiAnnoIndice.get(0);
						int count=0;
						for(int j=0 ; j<eventiAnnoIndice.size() ; j++){
							if(eventiAnnoIndice.get(j).equals(utenteCorrente)) {
								count++;
								eventiAnnoIndice.remove(j);
								j--;
							}
						}
						statistica.append(annoIndice+" : utente : "+utenteCorrente+" -> "+count+" prestiti \n");
						
					}					
			}
		
		}
	   
			assert storiaPre.equals(storia);
			assert invariante() ;
			
		return statistica.toString();
	}
	/**
	 * ritorna la lunghezza dell'array storia
	 * @pre true
	 * @post @nochange
	 * @return lunghezza dell'array
	 */
	public static int size() {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;

		int size = storia.size();
		
		assert storiaPre==storia;
		assert invariante() ;
			
		return size;	   
	}
	/**
	 * verifica se il parametro immesso è una costante esistente che identifica un evento
	 * @param nomeEvento stringa da verificare
	 * @pre true
	 * @post @change
	 * @return true se il parametro è valido
	 */
	static boolean eventoValido(String[] nomiEvento,String eventoDaValutare) {
		assert invariante() ;
		ArrayList<Evento> storiaPre = storia;
		
		boolean valido=false;
		
		for(int i=0; i<nomiEvento.length ; i++)
		 if(nomiEvento[i].equals(eventoDaValutare)) return true;
		
		assert storiaPre==storia;
		assert invariante() ;
		
		return false;
	}

	@Override
	public void update(Observable o, Object arg) {
		
		storia.add((Evento) arg);
	}

}