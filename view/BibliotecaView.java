package view;

import java.util.Observable;
import java.util.Scanner;

import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.Stampa;

public class BibliotecaView implements MyView{

	@Override
	public void update(Observable o, Object arg) {
		
	}

	@Override
	public String StringaNonVuota(String string) {

		return InputDati.leggiStringaNonVuota(string);
	}

	@Override
	public int InteroNonNegativo(String string) {

		return InputDati.leggiInteroNonNegativo(string);
	}

	@Override
	public int scelta(String titolo, String[] opzioni) {

		MyMenu menu = new MyMenu(titolo,opzioni);
		
		return menu.scegli();
	}

	@Override
	public void notify(String string) {


		Stampa.aVideo(string);
	}

	
	@Override
	public char Char(String messaggio) {

		return InputDati.leggiChar(messaggio);
	}

	@Override
	public String incornicia(String messaggio) {

		return BelleStringhe.incornicia(messaggio);
	}

	@Override
	public boolean yesOrNo(String riprova) {
		
		return InputDati.yesOrNo(riprova);
	}

	@Override
	public Object leggiInput(String messaggio) {
		
		String s;		
		s = InputDati.leggiStringaNonVuota(messaggio);
		
		try {
		if ( (Integer) Integer.parseInt(s) instanceof Integer)
		
			return Integer.parseInt(s);
		}catch(NumberFormatException e1) {
			try {
				if ((Double) Double.parseDouble(s) instanceof Double)
					return Double.parseDouble(s);
			}catch(NumberFormatException e2) {
				return s;
			}
		}
		
		return s;
		
		
	}

}
