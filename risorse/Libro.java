package risorse;


@SuppressWarnings("serial")
public class Libro extends Risorsa {
	
	/**
	 * costruisce un istanza di Libro con i parametri immessi
	 * @param nome
	 * @param aut
	 * @param casaeditrice
	 * @param gen
	 * @param lin
	 * @param anno
	 * @param pagine
	 * @param id
	 * @param numLicenze
	 * 
	 */
	public Libro(int id, String descrId, String tit,  String descrTit, int anno,  String descrAnno, int numLicenze,  String descrNumLic,
			String aut,  String descrAutore, String gen,  String descrGenere, String lin,  String descrLingua, 
			String casa,  String descrCasa, int pagine,  String descrPagine) {
		
		super(id, descrId, tit, descrTit, anno, descrAnno,  numLicenze, descrNumLic);

		campiRisorsa.add(new AttributoStringa(aut, descrAutore));
		campiRisorsa.add(new AttributoStringa(gen, descrGenere));
		campiRisorsa.add(new AttributoStringa(lin, descrLingua));
		campiRisorsa.add(new AttributoStringa(casa, descrCasa));
		campiRisorsa.add(new AttributoIntero(pagine, descrPagine));
	}

	/**
	 * verifica che le proprietà invarianti della classe Libro siano rispettate
	 * @pre true
	 * @post @nochange
	 * @return true se gli attributi assumono valori validi && super.invariante()
	 */
/*	protected boolean invariante() {
		Libro libroPre = this ;
		
		boolean invariante=false;
		if(super.invarianteR() && autore!=null && casaEditrice!=null && numeroPagine>0) invariante=true ;

		assert libroPre==this;
		
		return invariante;
	}*/	

}
