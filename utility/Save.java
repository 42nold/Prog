package utility;

import java.io.File;

import it.unibs.ing.mylib.ServizioFile;


public class Save {

	
	/**
	 * salva categorie e storico su file
	 * @pre true
	 * @post @nochange
	 */
	public void salvaDatiSuFile(String nomeFile, Object o) {
		
		File f = new File(nomeFile);
		ServizioFile.salvaSingoloOggetto(f, o);
			
	}
	
}
