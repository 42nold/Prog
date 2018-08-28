package risorse;

import java.util.ArrayList;


public class VideotecaContenitore extends CategoriaPrimoLivello<Film>{

	private static final String REGISTA="Regista";
	private static final String GENERE="Genere";
	private static final String LINGUA="Lingua";
	private static final String CASAPRODUZIONE="Casa Produzione";
	private static final String DURATA="Durata";
	
	private static final int DURATA_PRESTITO_FILM = 15;
	private static final int DURATA_PROROGA_FILM = 15;
	private static final int TERMINE_PROROGA_FILM = 2;
	private static final int MAX_RISORSE_PER_FILM= 2;

	public VideotecaContenitore(String nome, int id) {
		super(nome, DURATA_PRESTITO_FILM,  DURATA_PROROGA_FILM, TERMINE_PROROGA_FILM, MAX_RISORSE_PER_FILM, id);
		
		setDescrizioneCampi();
		
		aggiungiSottoCategoria("fantascienza");
		aggiungiSottoCategoria("drammatico");
	}

	
	@Override
	public void aggiungiSottoCategoria(String nome) {
		//assert invariante();
 		//int lunghPre = sottocategorie.size() ;
 		
		sottocategorie.add(new Videoteca(nome));
		
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
		descrizioneCampi.add(REGISTA);
		descrizioneCampi.add(GENERE);
		descrizioneCampi.add(LINGUA);
		descrizioneCampi.add(CASAPRODUZIONE);
		descrizioneCampi.add(DURATA);
	}

	@Override
	public int getIdCategoria(int idRisorsa) {
		//assert invarianteC();
		
	for(Categoria<Film> categoria : sottocategorie) {
		
		int risultatoParziale = categoria.getIdCategoria(idRisorsa);
		
		if(risultatoParziale!=-1) return risultatoParziale;
	}
	return -1;
	}

}
