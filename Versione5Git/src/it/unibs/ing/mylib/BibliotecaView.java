package it.unibs.ing.mylib;

import java.util.Observable;

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
	public void incornicia(String messaggio) {

		Stampa.aVideo(BelleStringhe.incornicia(messaggio));
	}

}
