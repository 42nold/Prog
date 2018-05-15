package it.unibs.ing.mylib;

import java.util.Observable;
import java.util.Observer;

public class Stampa implements Observer{

	public static void aVideo(String stringaDaStampare) {
		System.out.println(stringaDaStampare);
	}

	@Override
	public void update(Observable arg0, Object arg1) {

		
	}
}
