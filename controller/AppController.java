package controller;

//Commit di prova. questo è il commit.

import java.io.Serializable;

import model.Model;
import utility.Load;
import utility.Save;
import view.BibliotecaView;



public class AppController implements Serializable{
	


	public static void main(String[] args){
		
		

		BibliotecaView view = new BibliotecaView();
		
		Model model = new Model() ;
		
		Save save =new Save();
		Load load = new Load();
		
		Controller controller = new Controller(model,view,save,load);
		
		controller.start();
		
	}
}