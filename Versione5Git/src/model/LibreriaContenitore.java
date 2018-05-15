package model;

import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;

public class LibreriaContenitore extends CategoriaPrimoLivello<Libro> {

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
	public void aggiungiRisorsa() {

		assert invarianteC();
		int libriPre = risorse.size();
		int idPre = idRisorsa;
		
		String nome= InputDati.leggiStringaNonVuota("inserisci il nome della risorsa");
		String gen= InputDati.leggiStringaNonVuota("inserisci il genere della risorsa");
		String lin= InputDati.leggiStringaNonVuota("inserisci la lingua della risorsa");
		int anno = InputDati.leggiInteroPositivo("inserisci l'anno di uscita");
		int numLicenze = InputDati.leggiInteroPositivo("inserisci il numero di licenze");
		
		String aut= InputDati.leggiStringaNonVuota("inserisci l'autore del libro");
		String casa= InputDati.leggiStringaNonVuota("inserisci la casa editrice del libro");
		int pagine = InputDati.leggiInteroPositivo("inserisci il numero di pagine");
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

}
