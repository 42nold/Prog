package controller;


import java.io.Serializable;

import model.Model;
import view.BibliotecaView;
import view.MyView;



public class AppController implements Serializable{
	

	public static void main(String[] args){
		

		MyView view = new BibliotecaView();
		
		Model model = new Model() ;

		Controller controller = new Controller(model,view);
		
		controller.start();
		
	}
}