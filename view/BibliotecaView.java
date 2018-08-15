package view;

import java.util.Observable;

import it.unibs.ing.mylib.BelleStringhe;
import it.unibs.ing.mylib.InputDati;
import it.unibs.ing.mylib.MyMenu;
import it.unibs.ing.mylib.Stampa;
import model.MyView;

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

}
