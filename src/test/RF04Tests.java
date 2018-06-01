package test;

import clase.Asistente;

//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RF04Tests {

	public final static String USUARIO = "delucas";
	public final static Date FECHA_HORA = new GregorianCalendar(2018, 3, 1, 15, 15, 0).getTime();
	
	Asistente jenkins;
	
	@Before
	public void setup() {
		jenkins = new Asistente("jenkins");
	}
	
	@Test
	public void diaDentroDe() {
		
		Assert.assertEquals(
				"@delucas ser� el lunes 2 de abril de 2018",
				jenkins.escuchar("@jenkins qu� d�a ser� ma�ana?")
			);
		
		Assert.assertEquals(
				"@delucas ser� el martes 3 de abril de 2018",
				jenkins.escuchar("@jenkins qu� d�a ser� dentro de 2 d�as?")
			);

		Assert.assertEquals(
				"@delucas ser� el lunes 23 de abril de 2018",
				jenkins.escuchar("@jenkins qu� d�a ser� dentro de 22 d�as?")
			);
		
		Assert.assertEquals(
				"@delucas ser� el viernes 1 de junio de 2018",
				jenkins.escuchar("@jenkins qu� d�a ser� dentro de 2 meses?")
			);
		
		Assert.assertEquals(
				"@delucas ser� el mi�rcoles 1 de abril de 2020",
				jenkins.escuchar("@jenkins qu� d�a ser� dentro de 2 a�os?")
			);
	}
	
	@Test
	public void diaHace() {
		Assert.assertEquals(
				"@delucas fue el s�bado 31 de marzo de 2018",
				jenkins.escuchar("@jenkins qu� d�a fue ayer?")
			);
		
		Assert.assertEquals(
				"@delucas fue el jueves 29 de marzo de 2018",
				jenkins.escuchar("@jenkins qu� d�a fue hace 3 d�as?")
			);
		
		Assert.assertEquals(
				"@delucas fue el jueves 1 de febrero de 2018",
				jenkins.escuchar("@jenkins qu� d�a fue hace 2 meses?")
			);
		
		Assert.assertEquals(
				"@delucas fue el viernes 1 de abril de 2016",
				jenkins.escuchar("@jenkins qu� d�a fue hace 2 a�os?")
			);
	}
	
	@Test
	public void tiempoDesde() {
		
		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2017 y el 1 de abril de 2018 pasaron 365 d�as",
				jenkins.escuchar("@jenkins cu�ntos d�as pasaron desde el 1 de abril de 2017?")
			);
		
		Assert.assertEquals(
				"@delucas entre el 2 de abril de 2017 y el 1 de abril de 2018 pasaron 364 d�as",
				jenkins.escuchar("@jenkins cu�ntos d�as pasaron desde el 2 de abril de 2017?")
			);
		
		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2017 y el 1 de abril de 2018 pasaron 52 semanas",
				jenkins.escuchar("@jenkins cu�ntas semanas pasaron desde el 1 de abril de 2017?")
			);
		

		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2017 y el 1 de abril de 2018 pasaron 12 meses",
				jenkins.escuchar("@jenkins cu�ntos meses pasaron desde el 1 de abril de 2017?")
			);

		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2017 y el 1 de abril de 2018 paso 1 a�o",
				jenkins.escuchar("@jenkins cu�ntos a�os pasaron desde el 1 de abril de 2017?")
			);

		Assert.assertEquals(
				"@delucas entre el 2 de abril de 2017 y el 1 de abril de 2018 pasaron 0 a�os",
				jenkins.escuchar("@jenkins cu�ntos a�os pasaron desde el 2 de abril de 2017?")
			);
		
		Assert.assertEquals(
				"@delucas pasaron 31 d�as",
				jenkins.escuchar("@jenkins cu�ntos d�as pasaron desde el 1 de marzo?")
			);
		
		Assert.assertEquals(
				"@delucas pasaron 4 semanas",
				jenkins.escuchar("@jenkins cu�ntas semanas pasaron desde el 1 de marzo?")
			);
		
		Assert.assertEquals(
				"@delucas paso 1 mes",
				jenkins.escuchar("@jenkins cu�ntos meses pasaron desde el 1 de marzo?")
			);
		
		Assert.assertEquals(
				"@delucas pasaron 3 meses",
				jenkins.escuchar("@jenkins cu�ntos meses pasaron desde el 1 de enero?")
			);
		
		Assert.assertEquals(
				"@delucas pasaron 0 a�os",
				jenkins.escuchar("@jenkins cu�ntos a�os pasaron desde el 1 de marzo?")
			);
		
		// agregar casos de prueba
	}
	
	@Test
	public void tiempoHasta() {
		Assert.assertEquals(
				"@delucas faltan 9 d�as",
				jenkins.escuchar("@jenkins cu�ntos d�as faltan para el 10 de abril?")
			);
		
		
		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2018 y el 1 de abril de 2019 faltan 365 d�as",
				jenkins.escuchar("@jenkins cu�ntos d�as faltan para el 1 de abril de 2019?")
			);
		
		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2018 y el 2 de abril de 2019 faltan 366 d�as",
				jenkins.escuchar("@jenkins cu�ntos d�as faltan para el 2 de abril de 2019?")
			);

		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2018 y el 1 de abril de 2019 faltan 52 semanas",
				jenkins.escuchar("@jenkins cu�ntas semanas faltan para el 1 de abril de 2019?")
			);
		

		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2018 y el 1 de abril de 2019 faltan 12 meses",
				jenkins.escuchar("@jenkins cu�ntos meses faltan para el 1 de abril de 2019?")
			);

		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2018 y el 1 de abril de 2019 falta 1 a�o",
				jenkins.escuchar("@jenkins cu�ntos a�os faltan para el 1 de abril de 2019?")
			);

		Assert.assertEquals(
				"@delucas entre el 1 de abril de 2018 y el 31 de marzo de 2019 faltan 0 a�os",
				jenkins.escuchar("@jenkins cu�ntos a�os faltan para el 31 de marzo de 2019?")
			);
		
		Assert.assertEquals(
				"@delucas faltan 61 d�as",
				jenkins.escuchar("@jenkins cu�ntos d�as faltan para el 1 de junio?")
			);
		
		Assert.assertEquals(
				"@delucas faltan 8 semanas",
				jenkins.escuchar("@jenkins cu�ntas semanas faltan para el 1 de junio?")
			);
		
		Assert.assertEquals(
				"@delucas faltan 2 meses",
				jenkins.escuchar("@jenkins cu�ntos meses faltan para el 1 de junio?")
			);
		
		Assert.assertEquals(
				"@delucas faltan 0 a�os",
				jenkins.escuchar("@jenkins cu�ntos a�os faltan para el 1 de junio?")
			);
		
		// agregar casos de prueba
	}
	
}
