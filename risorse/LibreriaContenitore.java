package risorse;

import java.util.ArrayList;


public class LibreriaContenitore extends CategoriaPrimoLivello<Libro> {

	private static final String AUTORE="Autore";
	private static final String GENERE="Genere";
	private static final String LINGUA="Lingua";
	private static final String CASAEDITRICE="Casa Editrice";
	private static final String NUMEROPAGINE="Numero pagine";
	
	private static final int DURATA_PRESTITO_LIBRI = 30;
	private static final int DURATA_PROROGA_LIBRI = 30;
	private static final int TERMINE_PROROGA_LIBRI = 3;
	private static final int MAX_RISORSE_PER_LIBRI= 3;	
	
	
	public LibreriaContenitore(String nome, int id) {
		
		super(nome, DURATA_PRESTITO_LIBRI, DURATA_PROROGA_LIBRI , TERMINE_PROROGA_LIBRI, MAX_RISORSE_PER_LIBRI, id);
		
		setDescrizioneCampi();
		
		aggiungiSottoCategoria("Horror");
		aggiungiSottoCategoria("Romanzo");
	}

	@Override
	public void aggiungiSottoCategoria(String nome) {
 		//assert invariante();
 		//int lunghPre = sottocategorie.size() ;
 		
		sottocategorie.add(new Libreria(nome));
		
		//assert invariante() && lunghPre+1==sottocategorie.size();		
	}
	
	@Override
	public void aggiungiRisorsa(ArrayList<Object> attributiNuovaRisorsa) {
			
	}

	@Override
	protected void setDescrizioneCampi() {
		descrizioneCampi.add(ID);
		descrizioneCampi.add(TITOLO);
		descrizioneCampi.add(ANNO);
		descrizioneCampi.add(NUMEROLICENZE);
		descrizioneCampi.add(AUTORE);
		descrizioneCampi.add(GENERE);
		descrizioneCampi.add(LINGUA);
		descrizioneCampi.add(CASAEDITRICE);
		descrizioneCampi.add(NUMEROPAGINE);
	}
	
	@Override
	public int getIdCategoria(int idRisorsa) {
		
		for(Categoria<Libro> categoria : sottocategorie) {
			
			int risultatoParziale = categoria.getIdCategoria(idRisorsa);
			
			if(risultatoParziale!=-1) return risultatoParziale;
		}
		return -1;
	}
	
	
	
}


