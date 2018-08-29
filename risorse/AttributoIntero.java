package risorse;

import java.io.Serializable;

public class AttributoIntero implements Attributo,Serializable{
	
	String descrizione;
	int value;
	
	
	public AttributoIntero (int num, String descr) {
		value = num;
		descrizione = descr;
	}
	
	@Override
	public void setValue(Object o) throws ClassCastException{
		value = (( Integer)o).intValue();		
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	public String toString() {
		return descrizione+": "+value;
	}
	
	@Override
	public String getDescrizione() {
		return descrizione;
	}
	
	@Override
	public boolean match(Object o) throws ClassCastException{
		return value == ((Integer) o);
	}

}
