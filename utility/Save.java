package utility;

import java.io.File;
import java.util.ArrayList;

import it.unibs.ing.mylib.ServizioFile;
import model.Archivio;
import risorse.CategoriaPrimoLivello;

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
