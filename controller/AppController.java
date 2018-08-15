package controller;

//Commit di prova. questo è il commit.

import java.io.Serializable;

import model.Controller;
import model.Model;
import view.BibliotecaView;



public class AppController implements Serializable{
	


	public static void main(String[] args){
		
		

		BibliotecaView view = new BibliotecaView();
		
		Model model = new Model() ;
		
		Controller controller = new Controller(model,view);
		
		controller.start();
		
	}
}