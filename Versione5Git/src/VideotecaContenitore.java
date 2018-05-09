package dafault;

import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;

public class VideotecaContenitore extends CategoriaPrimoLivello<Film>{

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
	public void aggiungiRisorsa() {

		assert invarianteC();
		int libriPre = risorse.size();
		int idPre = idRisorsa;
		
		String nome= InputDati.leggiStringaNonVuota("inserisci il nome della risorsa");
		String gen= InputDati.leggiStringaNonVuota("inserisci il genere della risorsa");
		String lin= InputDati.leggiStringaNonVuota("inserisci la lingua della risorsa");
		int anno = InputDati.leggiInteroPositivo("inserisci l'anno di uscita");
		int numLicenze = InputDati.leggiInteroPositivo("inserisci il numero di licenze");
		
		String regista= InputDati.leggiStringaNonVuota("inserisci il regista del film");
		String casa= InputDati.leggiStringaNonVuota("inserisci la casa di produzione del film");
		int durata = InputDati.leggiInteroPositivo("inserisci la durata del film in minuti");
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

}
