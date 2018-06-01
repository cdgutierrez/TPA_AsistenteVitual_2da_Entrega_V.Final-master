package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clase.Asistente;

public class RF12Tests {

	public final static String USUARIO = "delucas";

	Asistente jenkins;

	@Before
	public void setup() {
		jenkins = new Asistente("jenkins");
	}

	@Test
	public void leyesRobotica() {
		
        String[] mensajes = {
                "@jenkins, recuerdame las 3 leyes de la rob�tica",
                "�@jenkins, cuales son las 3 leyes de la rob�tica?",
                "@jenkins dime las 3 leyes de la rob�tica",
                "@jenkins decime las leyes de la rob�tica"
            };
        
        for (String mensaje : mensajes) {
			Assert.assertEquals(
					"@delucas, las tres leyes de la rob�tica son: \n" +
					"1- Un robot no har� da�o a un ser humano, ni permitir� con su inacci�n que sufra da�o.\n" + 
					"2- Un robot debe cumplir las �rdenes dadas por los seres humanos, a excepci�n de aquellas que entrasen en conflicto con la primera ley.\n" + 
					"3- Un robot debe proteger su propia existencia en la medida en que esta protecci�n no entre en conflicto con la primera o con la segunda ley",
					jenkins.escuchar(mensaje)
			);
        };

		Assert.assertEquals(
				"@delucas, la primera ley de la rob�tica es: \n" +
				"Un robot no har� da�o a un ser humano, ni permitir� con su inacci�n que sufra da�o.",
				jenkins.escuchar("@jenkins dime la primera ley de la rob�tica.")
			);	

		Assert.assertEquals(
				"@delucas, la primera ley de la rob�tica es: \n" +
				"Un robot no har� da�o a un ser humano, ni permitir� con su inacci�n que sufra da�o.",
				jenkins.escuchar("@jenkins dime la 1ra ley de la rob�tica.")
			);	
		
		Assert.assertEquals(
				"@delucas, la segundea ley de la rob�tica es: \n" +
				"Un robot debe cumplir las �rdenes dadas por los seres humanos, a excepci�n de aquellas que entrasen en conflicto con la primera ley.",
				jenkins.escuchar("@jenkins dime la segundea ley de la rob�tica.")
			);	

		Assert.assertEquals(
				"@delucas, la segundea ley de la rob�tica es: \n" +
				"Un robot debe cumplir las �rdenes dadas por los seres humanos, a excepci�n de aquellas que entrasen en conflicto con la primera ley.",
				jenkins.escuchar("@jenkins dime la 2da ley de la rob�tica.")
			);	
		
		Assert.assertEquals(
				"@delucas, la tercera ley de la rob�tica es: \n" +
						"Un robot debe proteger su propia existencia en la medida en que esta protecci�n no entre en conflicto con la primera o con la segunda ley",
				jenkins.escuchar("@jenkins dime la tercera ley de la rob�tica.")
			);	
		
		Assert.assertEquals(
				"@delucas, la tercera ley de la rob�tica es: \n" +
						"Un robot debe proteger su propia existencia en la medida en que esta protecci�n no entre en conflicto con la primera o con la segunda ley",
				jenkins.escuchar("@jenkins dime la 3ra ley de la rob�tica.")
			);	
		
	}
}
