package model;

//Commit di prova. questo è il commit.

import java.io.Serializable;

import it.unibs.ing.mylib.BibliotecaView;



public class AppMain implements Serializable{
	


	public static void main(String[] args){
		
		

		BibliotecaView view = new BibliotecaView();
		
		Model model = new Model() ;
		
		Controller controller = new Controller(model,view);
		
		controller.start();
		
	}
}