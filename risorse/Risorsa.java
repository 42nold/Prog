package risorse;

import java.io.Serializable;
import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;

@SuppressWarnings("serial")
public abstract class Risorsa implements Serializable{
	/**
	 * classe astratta che definisce le operazioni esseziali eseguibili su ogni tipo di risorsa contenuta in archivio
	 * @invariant invariante()
	 */
	private final static String NUM_LICENZE = "Numero licenze";
	
	protected ArrayList<Attributo> campiRisorsa;
/**
 * costruisce un istanza di risorsa con i parametri comuni a tutti i tpi di risorsa
 * @param nome nome della risorsa
 * @param an anno di pubblicazione
 * @param id identificativo della risorsa
 * @param numLicenze numero di licenze attrivuito alla risorsa
 */
	public Risorsa(int id,  String descrId, String tit,  String descrTit, int an, String descrAnno,  int numLicenze, String descrNumLic) {
		campiRisorsa = new ArrayList<Attributo>();
		
		campiRisorsa.add(new AttributoIntero (id, descrId));
		campiRisorsa.add(new AttributoStringa(tit, descrTit));
		campiRisorsa.add(new AttributoIntero(an, descrAnno));
		campiRisorsa.add(new AttributoIntero(numLicenze, descrNumLic));
		
	}
	
/**
 * restituisce una stringa di descrizione completa della risorsa
 * @pre true
 * @post @nochange
 **/	
	public String toString() {
		
		for (int i=0; i<campiRisorsa.size(); i++)
			if(campiRisorsa.get(i).getDescrizione().equals(NUM_LICENZE))
				return descrizionePrestito() + "   "+campiRisorsa.get(i).toString() + "\n" ;
		
		return null;
	}
/**
 * restituisce una stringa che descrive parzialmente la risorsa
 * @pre true
 * @post @nochange	
 * @return stringa descrittiva
 */	
	public String descrizionePrestito() {
		StringBuffer des = new StringBuffer();
		
		for (int i=0; i<campiRisorsa.size(); i++)
			if(!(campiRisorsa.get(i).getDescrizione().equals(NUM_LICENZE)))
			des.append("   "+campiRisorsa.get(i).toString()+"\n");
		
		return des.toString();		
	}
	
	/**
	 * diminuisce di 1 il numero di licenze della risorsa
	 * @pre getNumLicenze()>0
	 * @post getNumLicenze() == getNumLicenze()@pre - 1
	 */
	public void decrementaLicenze(){
		//assert invarianteR();
		//assert numeroLicenze>0;
		//int licenzePre = numeroLicenze;
		for (int i=0; i<campiRisorsa.size(); i++)
			if(campiRisorsa.get(i).getDescrizione().equals(NUM_LICENZE)) 
				campiRisorsa.get(i).setValue((Integer)campiRisorsa.get(i).getValue()-1);
		//assert numeroLicenze==licenzePre-1 ;
		//assert invarianteR();
	}
/**
 * incremente di 1 le licenze della risorsa
 * @pre true
 * @post true	
 */
	public void incrementaLicenze() {
		//assert invarianteR();
		for (int i=0; i<campiRisorsa.size(); i++)
			if(campiRisorsa.get(i).getDescrizione().equals(NUM_LICENZE))
				campiRisorsa.get(i).setValue((Integer)campiRisorsa.get(i).getValue()+1);
		//assert invarianteR();
	}
	
/**
 * modifica campi della risorsa	
 * @param nuoviValori da attribuire alla risorsa
 * @pre true
 * @post true
 */	
	public void modifica(Object[] nuoviValori) throws ClassCastException{
		for (int i=0; i<campiRisorsa.size(); i++)
			if(nuoviValori[i]!=null)
				campiRisorsa.get(i).setValue(nuoviValori[i]);
	}
	
/**
 * controlla se una stringa data come parametro � contenuta in uno degli attributi della risorsa (no case sensitive)
 * @param parametroDiRicerca stringa da controllare
 * @pre parametroDiRicerca!=null
 * @post @nochange
 * @return true se la stringa � contenuta
 */
	public boolean match(Object parametroDiRicerca, String campo) throws ClassCastException {
		/*assert invarianteR();
		assert chiaveDiRicerca!=null;
		Risorsa risorsaPre = this;*/
		for (int i=0; i<campiRisorsa.size(); i++)
			if(campiRisorsa.get(i).getDescrizione().equals(campo))
				return campiRisorsa.get(i).match(parametroDiRicerca);
		return false;
		/*assert risorsaPre==this ;
		assert invarianteR();*/
	}

/**
 * verifica che le propriet� invarianti della classe siano rispettate
 * @pre true
 * @post @nochange
 * @return true se i getters restituiscano i reali valori degli attributi, che le stringhe non siano nulle e che i valori numerici non siano negativi	
 */
	/*protected boolean invarianteR() {
		Risorsa risorsaPre = this;
		
		boolean invariante = false;
		if(getId()==id && getNome()==nome && getNumLicenze()==numeroLicenze && id>=0 && nome!=null && genere!=null && lingua!=null && numeroLicenze>=0 && anno>=0 ) invariante =true;
		
		assert risorsaPre==this;
		
		return invariante;
	}*/
	
	public void setValue(Object nuovoValore, String campo) throws ClassCastException{
		for (int i=0; i<campiRisorsa.size(); i++)
			if (campiRisorsa.get(i).getDescrizione().equals(campo))
				campiRisorsa.get(i).setValue(nuovoValore);
			
	}
	
	public Object getValue(String campo) {
		for (int i=0; i<campiRisorsa.size(); i++)
			if (campiRisorsa.get(i).getDescrizione().equals(campo))
				return campiRisorsa.get(i).getValue();
		
		return null;		
	}
	
}