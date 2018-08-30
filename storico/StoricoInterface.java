package storico;

import java.util.Observer;

public interface StoricoInterface extends Observer{

	public String getDescrizione();
	
	public String numEventoAnnoSolare(String nomeEvento, String descrizione,String[] nomiEventi);
	
	public String maxOccorrenzeEvento(String nomeEvento);
	
	public String prestitiFruitoriAnnoSolare(String eventoPrestito);
	
	public void salvaDati();
	
	public void importaDati();

	public int size();
}
