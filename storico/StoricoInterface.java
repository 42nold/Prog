package storico;

public interface StoricoInterface {

	public String getDescrizione();
	
	public String numEventoAnnoSolare(String nomeEvento, String descrizione,String[] nomiEventi);
	
	public String maxOccorrenzeEvento(String nomeEvento);
	
	public String prestitiFruitoriAnnoSolare(String eventoPrestito);
	
	public void salvaDati();
	
	public void importaDati();
}
