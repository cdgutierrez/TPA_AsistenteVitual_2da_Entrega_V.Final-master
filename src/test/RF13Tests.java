package test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import clase.Asistente;

public class RF13Tests {

	public final static String USUARIO = "delucas";

	Asistente jenkins;

	@Before
	public void setup() {
		jenkins = new Asistente("jenkins");
	}

	@Test
	public void chuckNorrisFacts() {
		
		
		Assert.assertEquals(
				"�Hola, @delucas!",
				jenkins.escuchar("Hola @jenkins.")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris arroj� una granada y mat� a 50 personas, luego explot�.",
				jenkins.escuchar("@jenkins puedes decir una Chuck Norris Facts?")
			);

		Assert.assertEquals(
				"No es nada, @delucas",
				jenkins.escuchar("gracias @jenkins")
			);		

		Assert.assertEquals(
				"@delucas, Chuck Norris cont� hasta el infinito. Dos veces.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris puede recoger naranjas de un manzano y hacer la mejor limonada que haya probado en su vida.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Una vez, una cobra mordi� la pierna de Chuck Norris. Despu�s de cinco d�as de dolor insoportable, la cobra muri�.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"No es nada, @delucas",
				jenkins.escuchar("gracias @jenkins")
			);	
		
		Assert.assertEquals(
				"@delucas, No existe una teor�a de la evoluci�n, solo una lista de las criaturas que Chuck Norris permite vivir.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris puede matar a tus amigos imaginarios.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris es la �nica persona que puede golpear un c�clope entre los ojos.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris puede estrangularte con un tel�fono inal�mbrico.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck puede prender fuego a las hormigas con una lupa. Por la noche.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris hace llorar a las cebollas.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris es la raz�n por la que Waldo se est� escondiendo.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, El tipo de sangre de Chuck Norris es AK-47.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris arroj� una granada y mat� a 50 personas, luego explot�.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris cont� hasta el infinito. Dos veces.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);

		Assert.assertEquals(
				"@delucas, Chuck Norris puede recoger naranjas de un manzano y hacer la mejor limonada que haya probado en su vida.",
				jenkins.escuchar("@jenkins dime otra historia de Chuck Norris")
			);



	}

}
