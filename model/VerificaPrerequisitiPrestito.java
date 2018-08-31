package model;

import java.util.*;
import utenti.Fruitore;

class VerificaPrerequisitiPrestito {
	private final Model model;
	private Fruitore fruitore;
	private int risorsaScelta;
	private int match;
	private int catPosseduta;
	private int catRisorsa;
	private int maxRisorsePerCategoria;
	private int numLicenze;
	private ArrayList<Integer> idRisorseGiaPossedute;
	
	VerificaPrerequisitiPrestito(Model model, Fruitore fruitore, int risorsaScelta) {
		this.model = model;
		this.fruitore = fruitore;
		this.risorsaScelta = risorsaScelta;
	}
	
	boolean compute() {
		catRisorsa=model.getArchivio().trovaIdCategoria(risorsaScelta);
		maxRisorsePerCategoria = model.getArchivio().maxRisorsePerCategoriaDataUnaRisorsa(risorsaScelta);
		idRisorseGiaPossedute = fruitore.getPrestiti();
		
		match=matchCount();
										
		numLicenze = model.getArchivio().numeroLicenzeRisorsa(risorsaScelta);
										
		if( !fruitore.giaPresente(risorsaScelta) && match < maxRisorsePerCategoria && numLicenze>0 ) {
			return true;	
		}
		return false;
	}
	
	private int matchCount(){
		int count=0;
		for (Integer idInEsame : idRisorseGiaPossedute) {
			catPosseduta=model.getArchivio().trovaIdCategoria(idInEsame);
			
			if(catRisorsa==catPosseduta) count++;
		}
		return count;
	}
}
