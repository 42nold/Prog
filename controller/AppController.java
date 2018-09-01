package controller;

import java.io.Serializable; 
import model.*;
import view.*;

public class AppController implements Serializable{
	
	public static void main(String[] args){
		
		MyView view = new BibliotecaView();
		
		Model model = new Model() ;

		Controller controller = new Controller(model,view);
		
		controller.start();
	}
}