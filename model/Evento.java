package model;

import java.io.Serializable;
import java.util.Calendar;


@SuppressWarnings("serial")
public class Evento implements Serializable{

	
	/**
	 * data dell'evento , descrizione e valore dell'id della risorsa coinvolta se necessario(altrimenti -1)
	 * @invariant invariante()
	 */
	private Calendar data ;
	private String   nome ;
	private int      valore;
	
	/**
	 * costruisce istanza evento
	 * @param nome descrizione dell'evento
	 * @param valore valore dell'id della risorsa coinvolta	 * 
	 */
	public Evento (String nome, int valore) {
		data = Calendar.getInstance();
		this.nome = nome ;
		this.valore = valore ;
		
		assert invariante();	}
	/**
	 * formatta i dati contenuti nell'oggetto evento in una stringa ordinata
	 * @pre true
	 * @post @nochange 
	 * @return stringa riassuntiva
	 */
	public String toString() {
		assert invariante();
		Evento eventoPre = this ;
		
		StringBuffer a =  new StringBuffer();
		a.append(data.get(Calendar.DAY_OF_MONTH)+"/"+data.get(Calendar.MONTH)+"/"+data.get(Calendar.YEAR) + "\n");
		a.append(nome + "\n");
		if(valore!=-1) a.append(valore);
		String risultato = a.toString() ;
	
		assert eventoPre==this ;
		assert invariante() ;
		
		return risultato;
	}
/**
 * restituisce il valore dell'attributo data
 * @return la data di creazione dell'evento
 * @pre true
 * @post @nochange
 * @return data di creazione evento
 */
	public Calendar getData() {
		
		return data;
	}
/**
 * restituisce il valore dell'attributo descrizione
 * @return la stringa che descrive l'evento
 * @pre true
 * @post @nochange
 * @return stringa contenente la descrizione dell'evento
 */
	public String getDescrizione() {
		return nome;
	}
/**
 * restituisce il valore dell'id della risorsa interessata all'evento
 * @return intero rappresentante l'id
 */
	public Integer getValore() {

		return valore;
	}
	/**
	 * invariante di classe
	 * @pre true
	 * @post @nochange
	 * @return true se l'invariante di classe è rispettata
	 */
	private boolean invariante() {
		Evento eventoPre = this;
		
		boolean invariante = false;
		if(data != null && nome!=null && data == getData() && nome == getDescrizione() && valore == getValore()) invariante = true ;
		
		assert eventoPre==this ;
		return invariante;
	}
}
