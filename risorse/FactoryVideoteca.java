package risorse;

public class FactoryVideoteca extends FactoryCategoria {

	@Override
	public CategoriaPrimoLivello creaCategoriaPrimoLivello(String nome, int idCategoria) {
		
		return new VideotecaContenitore(nome,idCategoria);
	}
}