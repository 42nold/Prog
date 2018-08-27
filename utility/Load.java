package utility;

import java.io.File;
import java.util.ArrayList;

import it.unibs.ing.mylib.ServizioFile;
import risorse.CategoriaPrimoLivello;

public class Load {
	/**
	 * importa le categorie da file e lo storico
	 * @pre true
	 * @post true
	 */
	public Object importaDatiDaFile(String nomeFile) {
		
		File f = new File(nomeFile);
		
		Object o = ServizioFile.caricaSingoloOggetto(f);
		
		return o;
		
		
	}
}
