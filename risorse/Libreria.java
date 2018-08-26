package risorse;

import java.util.ArrayList;

import it.unibs.ing.mylib.InputDati;
import storico.Storico;

public class Libreria extends Categoria<Libro> {

	private static final int NUM_ATTRIBUTI_STRINGA = 5;
	private static final int NUM_ATTRIBUTI_NUMERICI = 2;

	public Libreria(String param) {
		super(param);
	}

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
		
		
		assert invarianteC() && (risorse.size()==libriPre+1 ) && idRisorsa==idPre+1 ;

		
	}

	@Override
	protected ArrayList<Integer> filtra(int attributoScelto, String chiaveDiRicerca, int numDiRicerca) {
		
		assert invarianteC() && chiaveDiRicerca!=null && attributoScelto>=1 && attributoScelto<=8;
		Libreria thisPre = this;
		
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
		// TODO Auto-generated method stub
		return Libro.getAttributiStringa();
	}

	@Override
	public String[] getAttributiNumerici() {
		// TODO Auto-generated method stub
		return Libro.getAttributiNumerici();
	}

	@Override
	public int getIdCategoria(int idRisorsa) {
		assert invarianteC();
		
		
		if(risorse.size()>0) {
			for(Libro l : risorse) {
				if (l.getId() == idRisorsa) {
					assert invarianteC() ;
					if(l.getClass()==new Libro("","","","","",1,1,1,1).getClass())    return 0;  //if clause ridondante
					
					}
			}
		}
	
		assert invarianteC() ;
		return -1;
	}

	

}
