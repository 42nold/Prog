package risorse;

public class FactoryLibreria extends FactoryCategoria {

	@Override
	public CategoriaPrimoLivello creaCategoriaPrimoLivello(String nome,int idCategoria) {

		return new LibreriaContenitore(nome, idCategoria);
	}

}
