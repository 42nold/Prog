package risorse;

import java.io.Serializable;

public class AttributoStringa implements Attributo,Serializable{
	
	String descrizione;
	String value;
	
	public AttributoStringa (String val, String descr) {
		value = val;
		descrizione = descr;
	}
	
	@Override
	public void setValue(Object o) throws ClassCastException {
		value = (String) o;		
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
		return value.toLowerCase().contains(((String) o).toLowerCase());
	}

}
