package model;

import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;
import risorse.Film;
import storico.Storico;

public class VideotecaContenitore extends CategoriaPrimoLivello<Film>{

	private static final int NUM_ATTRIBUTI_STRINGA = 5;
	private static final int NUM_ATTRIBUTI_NUMERICI = 2;


	public VideotecaContenitore(String nome, int durataMassimaPrestito, int durataMassimaProroga, int termineProroga,
			int maxRisorse, int id) {
		super(nome, durataMassimaPrestito, durataMassimaProroga, termineProroga, maxRisorse, id);

	}

	
	@Override
	public void aggiungiSottoCategoria(String nome) {
		assert invariante();
 		int lunghPre = sottocategorie.size() ;
 		
		sottocategorie.add(new Videoteca(nome));
		
		assert invariante() && lunghPre+1==sottocategorie.size();	
		
	}

	@Override
	public void aggiungiRisorsa(String[] attributiStringa, int[] attributiNumerici) {

		assert invarianteC() && attributiStringa.length==NUM_ATTRIBUTI_STRINGA && attributiNumerici.length == NUM_ATTRIBUTI_NUMERICI+1;//+1 comprende anche il num di licenze
		int libriPre = risorse.size();
		int idPre = idRisorsa;
		
		String nome= attributiStringa[0];
		String gen= attributiStringa[1];
		String lin= attributiStringa[2];
		int anno =attributiNumerici[0];
		
		String regista= attributiStringa[3];
		String casa= attributiStringa[4];
		int durata = attributiNumerici[1];
		int numLicenze = attributiNumerici[2];

		int id = idRisorsa;
		idRisorsa++ ;
		
		risorse.add(new Film(nome,regista,casa,gen,lin,anno,durata,id,numLicenze));
	Storico.risorsaAggiunta(id);
		
		assert invarianteC() && (risorse.size()==libriPre+1 ) && idRisorsa==idPre+1 ;
		
	}

	@Override
	protected ArrayList<Integer> filtra(int attributoScelto, String chiaveDiRicerca, int numDiRicerca) {


		assert invarianteC() && chiaveDiRicerca!=null && attributoScelto>=1 && attributoScelto<=8&& (!chiaveDiRicerca.equals("") || numDiRicerca>0);
		VideotecaContenitore thisPre = this;
		
		ArrayList<Integer> risultato = new ArrayList<Integer>();
		
		if(risorse.size()>0)
			switch(attributoScelto) {
		
				default : break;
				
				case 1:
					for(Film f: risorse)
						if(f.matchNome(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 2:
					for(Film f: risorse)
						if(f.matchRegista(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 3:
					for(Film f: risorse)
						if(f.matchCasaProduzione(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 4:
					for(Film f: risorse)
						if(f.matchGenere(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 5:
					for(Film f: risorse)
						if(f.matchLingua(chiaveDiRicerca)) risultato.add(f.getId());
					break;
				case 6:
					for(Film f: risorse)
						if(f.matchAnno(numDiRicerca)) risultato.add(f.getId());
					break;
				case 8:
					for(Film f: risorse)
						if(f.matchDurata(numDiRicerca)) risultato.add(f.getId());
					break;		
			}
		assert invarianteC() && thisPre==this;	
		return risultato;
	}


	@Override
	public String[] getAttributiStringa() {
		// TODO Auto-generated method stub
		return Film.getAttributiStringa();
	}


	@Override
	public String[] getAttributiNumerici() {
		// TODO Auto-generated method stub
		return Film.getAttributiNumerici();
	}

}
