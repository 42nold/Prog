package model;

import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;
import risorse.Libro;
import storico.Storico;

public class LibreriaContenitore extends CategoriaPrimoLivello<Libro> {

	private static final int NUM_ATTRIBUTI_STRINGA = 5;
	private static final int NUM_ATTRIBUTI_NUMERICI = 2;
	
	public LibreriaContenitore(String nome, int durataMassimaPrestito, int durataMassimaProroga, int termineProroga,
			int maxRisorse, int id) {
		super(nome, durataMassimaPrestito, durataMassimaProroga, termineProroga, maxRisorse, id);
	}

	@Override
	public void aggiungiSottoCategoria(String nome) {
 		assert invariante();
 		int lunghPre = sottocategorie.size() ;
 		
		sottocategorie.add(new Libreria(nome));
		
		assert invariante() && lunghPre+1==sottocategorie.size();		
	}

	@Override
	public void aggiungiRisorsa(String[] attributiStringa , int[] attributiNumerici) {

		assert invarianteC() && attributiStringa.length==NUM_ATTRIBUTI_STRINGA && attributiNumerici.length == NUM_ATTRIBUTI_NUMERICI+1;//+1 comprende anche il num di licenze
		int libriPre = risorse.size();
		int idPre = idRisorsa;
		
		String nome=attributiStringa[0];
		String gen= attributiStringa[1];
		String lin= attributiStringa[2];
		int anno = attributiNumerici[0];
		int numLicenze =  attributiNumerici[2];
		
		String aut= attributiStringa[3];
		String casa= attributiStringa[4];
		int pagine =  attributiNumerici[1];
		int id = idRisorsa;
		idRisorsa++;
	
		Libro libro = new Libro(nome,aut,casa,gen,lin,anno,pagine,id,numLicenze) ;
		risorse.add(libro);
		
		Storico.risorsaAggiunta(id);
		
		assert invarianteC() && (risorse.size()==libriPre+1 ) && idRisorsa==idPre+1 ;

		
	}

	@Override
	protected ArrayList<Integer> filtra(int attributoScelto, String chiaveDiRicerca, int numDiRicerca) {
		
		assert invarianteC() && chiaveDiRicerca!=null && attributoScelto>=1 && attributoScelto<=8;
		LibreriaContenitore thisPre = this;
		
		ArrayList<Integer> risultato = new ArrayList<Integer>();
		
		if (risorse.size()>0)
			switch(attributoScelto) {
		
				default : break;
				
				case 1:
					for(Libro l: risorse)
						if(l.matchNome(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 2:
					for(Libro l: risorse)
						if(l.matchAutore(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 3:
					for(Libro l: risorse)
						if(l.matchCasaEditrice(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 4:
					for(Libro l: risorse)
						if(l.matchGenere(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 5:
					for(Libro l: risorse)
						if(l.matchLingua(chiaveDiRicerca)) risultato.add(l.getId());
					break;
				case 6:
					for(Libro l: risorse)
						if(l.matchAnno(numDiRicerca)) risultato.add(l.getId());
					break;
				case 7:
					for(Libro l: risorse)
						if(l.matchPagine(numDiRicerca)) risultato.add(l.getId());
					break;		
				}	
			assert invarianteC() && thisPre==this;
			return risultato;
	}

	@Override
	public String[] getAttributiStringa() {

		return Libro.getAttributiStringa();
	}

	@Override
	public String[] getAttributiNumerici() {

		return Libro.getAttributiNumerici();
	}

}
