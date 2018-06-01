package operacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clase.Pedido;

public class ChuckNorrisFacts implements Operacion{

	private Operacion siguiente;
	
	private int contadorChuck;
	
	private String[] v_ChuckNorrisFacts = {
			"Chuck Norris arroj� una granada y mat� a 50 personas, luego explot�.",
			"Chuck Norris cont� hasta el infinito. Dos veces.",
			"Chuck Norris puede recoger naranjas de un manzano y hacer la mejor limonada que haya probado en su vida.",
			"Una vez, una cobra mordi� la pierna de Chuck Norris. Despu�s de cinco d�as de dolor insoportable, la cobra muri�.",
			"No existe una teor�a de la evoluci�n, solo una lista de las criaturas que Chuck Norris permite vivir.",
			"Chuck Norris puede matar a tus amigos imaginarios.",
			"Chuck Norris es la �nica persona que puede golpear un c�clope entre los ojos.",
			"Chuck Norris puede estrangularte con un tel�fono inal�mbrico.",
			"Chuck puede prender fuego a las hormigas con una lupa. Por la noche.",
			"Chuck Norris hace llorar a las cebollas.",
			"Chuck Norris es la raz�n por la que Waldo se est� escondiendo.",
			"El tipo de sangre de Chuck Norris es AK-47."
	};
	
	@Override
	public void siguiente(Operacion siguiente) {
		this.siguiente = siguiente;		
	}


	@Override
	public String calcular(Pedido pedido) {
		
		String regex = ".*(?:dime|deci|cuent|conta).*(?:chuck|norris).*";
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher = pattern.matcher(pedido.getMensaje());
		while(matcher.find()) {
			if(matcher.matches()) {
				String fact = v_ChuckNorrisFacts[contadorChuck++ % v_ChuckNorrisFacts.length];
				return "" + pedido.getNameUsuario() + ", " + fact;
			}
		}
		return siguiente.calcular(pedido);
	}

}
