package risorse;

public interface Attributo {

	
	public abstract void setValue(Object o);
	
	public abstract Object getValue();
	
	public abstract String getDescrizione();
	
	public abstract boolean match(Object o);
	
}
