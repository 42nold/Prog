package risorse;

import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;

@SuppressWarnings("serial")
public class Film extends Risorsa {
	
	/**
	 * costruisce istanza di Film con i parametri immessi
	 * @param nome
	 * @param reg regista
	 * @param casaP casa produzione
	 * @param gen genere 
	 * @param lin lingua
	 * @param anno
	 * @param dur durata
	 * @param id
	 * @param numLicenze
	 */
	public Film(int id, String descrId, String tit,  String descrTit, int anno,  String descrAnno, int numLicenze,  String descrNumLic,
			String reg, String descrRegista, String casaP, String descrCasaP, String gen, String descrGenere, 
			String lin, String descrLingua, int dur, String descrDurata) {

		super(id, descrId, tit, descrTit, anno, descrAnno,  numLicenze, descrNumLic);
		
		campiRisorsa.add(new AttributoStringa(reg, descrRegista));
		campiRisorsa.add(new AttributoStringa(gen, descrGenere));
		campiRisorsa.add(new AttributoStringa(lin, descrLingua));
		campiRisorsa.add(new AttributoStringa(casaP, descrCasaP));
		campiRisorsa.add(new AttributoIntero(dur, descrDurata));
		
	}
		
	/**
	 * verifica che le proprietà invarianti della classe Film siano rispettate
	 * @pre true
	 * @post @nochange
	 * @return true se gli attributi assumono valori validi && super.invariante()
	 */
	/*protected boolean invariante() {
		Film filmPre = this ;
		
		boolean invariante=false;
		if(super.invarianteR() && regista!=null && casaProduzione!=null && durata>0 && dataUscita!=null) invariante=true ;

		assert filmPre==this;
		;
		return invariante;
	}*/
	
}
