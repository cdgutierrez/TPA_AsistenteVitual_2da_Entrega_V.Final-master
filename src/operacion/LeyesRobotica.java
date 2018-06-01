package operacion;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clase.Pedido;

public class LeyesRobotica implements Operacion{

	private Operacion siguiente;

	@Override
	public void siguiente(Operacion siguiente) {
		this.siguiente = siguiente;		
	}


	@Override
	public String calcular(Pedido pedido) {
		
	    String regex = ".*(?:leyes de la robotica).*";
	    Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	    Matcher matcher = pattern.matcher(pedido.getMensaje());
	    while(matcher.find()) {
	    	if(matcher.matches()) {
	    		return pedido.getNameUsuario() + ", las tres leyes de la rob�tica son: \n" +
					"1- Un robot no har� da�o a un ser humano, ni permitir� con su inacci�n que sufra da�o.\n" + 
					"2- Un robot debe cumplir las �rdenes dadas por los seres humanos, a excepci�n de aquellas que entrasen en conflicto con la primera ley.\n" + 
					"3- Un robot debe proteger su propia existencia en la medida en que esta protecci�n no entre en conflicto con la primera o con la segunda ley";
	    	}
	    }
	    
	    String regexPrimera = ".*(?:primer|1).*(?:ley de la robotica).*";
	    Pattern patternPrimera = Pattern.compile(regexPrimera, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	    Matcher matcherPrimera = patternPrimera.matcher(pedido.getMensaje());
	    while(matcherPrimera.find()) {
	    	if(matcherPrimera.matches()) {
	    		return pedido.getNameUsuario() + ", la primera ley de la rob�tica es: \n" + 
	    				"Un robot no har� da�o a un ser humano, ni permitir� con su inacci�n que sufra da�o.";
	    	}
	    }
	    
	    String regexSegunda = ".*(?:segun|2).*(?:ley de la robotica).*";
	    Pattern patternSegunda = Pattern.compile(regexSegunda, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	    Matcher matcherSegunda = patternSegunda.matcher(pedido.getMensaje());
	    while(matcherSegunda.find()) {
	    	if(matcherSegunda.matches()) {
	    		return pedido.getNameUsuario() + ", la segundea ley de la rob�tica es: \n" + 
	    				"Un robot debe cumplir las �rdenes dadas por los seres humanos, a excepci�n de aquellas que entrasen en conflicto con la primera ley.";
	    	}
	    }
	    
	    String regexTercera = ".*(?:tercer|3).*(?:ley de la robotica).*";
	    Pattern patternTercera = Pattern.compile(regexTercera, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	    Matcher matcherTercera = patternTercera.matcher(pedido.getMensaje());
	    while(matcherTercera.find()) {
	    	if(matcherTercera.matches()) {
	    		return pedido.getNameUsuario() + ", la tercera ley de la rob�tica es: \n" + 
	    				"Un robot debe proteger su propia existencia en la medida en que esta protecci�n no entre en conflicto con la primera o con la segunda ley";
	    	}
	    }
	    
	    return siguiente.calcular(pedido);
	}

}
