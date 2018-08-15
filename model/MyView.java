package model;

import java.util.Observable;
import java.util.Observer;

public interface MyView {


	
	
	
	public void update(Observable o, Object arg) ;

	public String StringaNonVuota(String string);


	public int InteroNonNegativo(String string);

	public int scelta(String string, String[] opzioniEsiti);

	public void notify(String string);

	public String incornicia(String messaggioErrore);

	public char Char(String string);

	public boolean yesOrNo(String riprova);
	
	
	

}
