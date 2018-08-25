package risorse;

public class AttributoIntero implements Attributo{
	
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
		// TODO Auto-generated method stub
		return descrizione;
	}
	
	@Override
	public boolean match(Object o) throws ClassCastException{
		return value == ((Integer) o);
	}

}
