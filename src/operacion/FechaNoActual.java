package operacion;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import clase.Pedido;
import util.Fecha;

public class FechaNoActual implements Operacion{
	
	private Operacion siguiente;

	@Override
	public void siguiente(Operacion siguiente) {
		this.siguiente = siguiente;		
	}

	@Override
	public String calcular(Pedido pedido) {
		
		String regex_Ma�ana = ".*(?:ma�ana que dia|es ma�ana|sera ma�ana).*";
		Pattern pattern_Ma�ana = Pattern.compile(regex_Ma�ana, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_Ma�ana = pattern_Ma�ana.matcher(pedido.getMensaje());
		while(matcher_Ma�ana.find()) {
			if(matcher_Ma�ana.matches()) {
				Fecha date = new Fecha(0, 0, 1, 0, 0, 0);
				return pedido.getNameUsuario() + " ser� el " + date.fechaToString(date.formato_fecha[4]);
			}
		}

		String regex_DiaDentro_X = ".*(?:que dia sera dentro de) (\\d+).*";
		Pattern pattern_DiaDentro_X = Pattern.compile(regex_DiaDentro_X, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_DiaDentro_X = pattern_DiaDentro_X.matcher(pedido.getMensaje());
		while(matcher_DiaDentro_X.find()) {
			if(matcher_DiaDentro_X.matches()) {
				int dia = 0, mes = 0, a�o = 0;
				if(pedido.getMensaje().contains(pedido.normalizado("d�as?")) || pedido.getMensaje().contains(pedido.normalizado("d�a?")))
					dia = Integer.parseInt(matcher_DiaDentro_X.group(1));
				if(pedido.getMensaje().contains(pedido.normalizado("meses?")) || pedido.getMensaje().contains(pedido.normalizado("mes?")))
					mes = Integer.parseInt(matcher_DiaDentro_X.group(1));
				if(pedido.getMensaje().contains(pedido.normalizado("a�os?")) || pedido.getMensaje().contains(pedido.normalizado("a�o?")))
					a�o = Integer.parseInt(matcher_DiaDentro_X.group(1));
				Fecha date = new Fecha(a�o, mes, dia, 0, 0, 0);
				return pedido.getNameUsuario() + " ser� el " + date.fechaToString(date.formato_fecha[4]);
			}
		}

		String regex_Ayer = ".*(?:ayer fue|es ma�ana|dia fue ayer).*";
		Pattern pattern_Ayer = Pattern.compile(regex_Ayer, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_Ayer = pattern_Ayer.matcher(pedido.getMensaje());
		while(matcher_Ayer.find()) {
			if(matcher_Ayer.matches()) {
				Fecha date = new Fecha(0, 0, -1, 0, 0, 0, "PASADO");
				return pedido.getNameUsuario() + " fue el " + date.fechaToString(date.formato_fecha[4]);
			}
		}
		
		String regex_DiaHace_X = ".*(?:que dia fue hace) (\\d+).*";
		Pattern pattern_DiaHace_X = Pattern.compile(regex_DiaHace_X, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_DiaHace_X = pattern_DiaHace_X.matcher(pedido.getMensaje());
		while(matcher_DiaHace_X.find()) {
			if(matcher_DiaHace_X.matches()) {
				int dia = 0, mes = 0, a�o = 0;
				if(pedido.getMensaje().contains(pedido.normalizado("d�as?")) || pedido.getMensaje().contains(pedido.normalizado("d�a?")))
					dia = Integer.parseInt(matcher_DiaHace_X.group(1));
				if(pedido.getMensaje().contains(pedido.normalizado("meses?")) || pedido.getMensaje().contains(pedido.normalizado("mes?")))
					mes = Integer.parseInt(matcher_DiaHace_X.group(1));
				if(pedido.getMensaje().contains(pedido.normalizado("a�os?")) || pedido.getMensaje().contains(pedido.normalizado("a�o?")))
					a�o = Integer.parseInt(matcher_DiaHace_X.group(1));
				Fecha date = new Fecha(-a�o, -mes, -dia, 0, 0, 0, "PASADO");
				return pedido.getNameUsuario() + " fue el " + date.fechaToString(date.formato_fecha[4]);
			}
		}
		
		String regex_TiempoDesde = ".*(?:cuantos|cuantas) (\\w*|a�os) (?:pasaron desde).*";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				switch (matcher_TiempoDesde.group(1)) {
				case "dias": return tiempoDesde_Dias(pedido);
				case "semanas": return tiempoDesde_Semanas(pedido);
				case "meses": return tiempoDesde_Meses(pedido);
				case "a�os": return tiempoDesde_A�os(pedido);
				default:
					break;
				}
			}
		}
		
		
		String regex_TiempoHasta = ".*(?:cuantos|cuantas) (\\w*|a�os) (?:faltan para).*";
		Pattern pattern_TiempoHasta = Pattern.compile(regex_TiempoHasta, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoHasta = pattern_TiempoHasta.matcher(pedido.getMensaje());
		while(matcher_TiempoHasta.find()) {
			if(matcher_TiempoHasta.matches()) {
				switch (matcher_TiempoHasta.group(1)) {
				case "dias": return tiempoHasta_Dias(pedido);
				case "semanas": return tiempoHasta_Semanas(pedido);
				case "meses": return tiempoHasta_Meses(pedido);
				case "a�os": return tiempoHasta_A�os(pedido);
				default:
					break;
				}
			}
		}
		
		
		return siguiente.calcular(pedido);
	}
	
	private String tiempoDesde_Dias(Pedido pedido) {
		String respuesta = null;
		Fecha fechaActual = new Fecha();
		Fecha fechaPasado = null;
		String regex_TiempoDesde = ".*(?:pasaron desde el) (\\d+) (?:de) ([a-zA-Z]*) (?:de) (\\d+) ?.";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				try {
					fechaPasado = new Fecha(matcher_TiempoDesde.group(1)+"/"+matcher_TiempoDesde.group(2)+"/"+matcher_TiempoDesde.group(3),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e1) {
					return siguiente.calcular(pedido);
				}
				int dias = fechaPasado.restaFechas_Dias(fechaActual);
				if (dias < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + " entre el " + fechaPasado.fechaToString("d 'de' MMMMM 'de' yyyy");
				respuesta += " y el " + fechaActual.fechaToString("d 'de' MMMMM 'de' yyyy") + (dias != 1 ? " pasaron "  : " paso " ) + dias ;
				respuesta += (dias != 1 ? " d�as" : " d�a");
				return respuesta;
			}
		}
		String regex_TiempoDesde_sinA�o = ".*(?:pasaron desde el) (\\d+) (?:de) ([a-zA-Z]*) ?.";
		Pattern pattern_TiempoDesde_sinA�o = Pattern.compile(regex_TiempoDesde_sinA�o, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde_sinA�o = pattern_TiempoDesde_sinA�o.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde_sinA�o.find()) {
			if(matcher_TiempoDesde_sinA�o.matches()) {
				try {
					fechaPasado = new Fecha(matcher_TiempoDesde_sinA�o.group(1)+"/"+matcher_TiempoDesde_sinA�o.group(2)+"/"+fechaActual.getA�o(),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e2) {
					return siguiente.calcular(pedido);
				}
				int dias = fechaPasado.restaFechas_Dias(fechaActual);
				if (dias < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + (dias != 1 ? " pasaron "  : " paso " ) + dias ;
				respuesta += (dias != 1 ? " d�as" : " d�a");
				return respuesta;
			}
		}
		return siguiente.calcular(pedido);
	}

	private String tiempoDesde_Semanas(Pedido pedido) {
		String respuesta = null;
		Fecha fechaActual = new Fecha();
		Fecha fechaPasado = null;
		String regex_TiempoDesde = ".*(?:pasaron desde el) (\\d+) (?:de) ([a-zA-Z]*) (?:de) (\\d+) ?.";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				try {
					fechaPasado = new Fecha(matcher_TiempoDesde.group(1)+"/"+matcher_TiempoDesde.group(2)+"/"+matcher_TiempoDesde.group(3),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e1) {
					return siguiente.calcular(pedido);
				}
				int semanas = fechaPasado.restaFechas_Semanas(fechaActual);
				if (semanas < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + " entre el " + fechaPasado.fechaToString("d 'de' MMMMM 'de' yyyy");
				respuesta += " y el " + fechaActual.fechaToString("d 'de' MMMMM 'de' yyyy") + (semanas != 1 ? " pasaron "  : " paso " ) + semanas ;
				respuesta += (semanas != 1 ? " semanas" : " semana");
				return respuesta;
			}
		}
		String regex_TiempoDesde_sinA�o = ".*(?:pasaron desde el) (\\d+) (?:de) ([a-zA-Z]*) ?.";
		Pattern pattern_TiempoDesde_sinA�o = Pattern.compile(regex_TiempoDesde_sinA�o, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde_sinA�o = pattern_TiempoDesde_sinA�o.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde_sinA�o.find()) {
			if(matcher_TiempoDesde_sinA�o.matches()) {
				try {
					fechaPasado = new Fecha(matcher_TiempoDesde_sinA�o.group(1)+"/"+matcher_TiempoDesde_sinA�o.group(2)+"/"+fechaActual.getA�o(),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e2) {
					return siguiente.calcular(pedido);
				}
				int semanas = fechaPasado.restaFechas_Semanas(fechaActual);
				if (semanas < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + (semanas != 1 ? " pasaron "  : " paso " ) + semanas ;
				respuesta += (semanas != 1 ? " semanas" : " semana");
				return respuesta;
			}
		}
		return siguiente.calcular(pedido);
	}

	private String tiempoDesde_Meses(Pedido pedido) {
		String respuesta = null;
		Fecha fechaActual = new Fecha();
		Fecha fechaPasado = null;
		String regex_TiempoDesde = ".*(?:pasaron desde el) (\\d+) (?:de) ([a-zA-Z]*) (?:de) (\\d+) ?.";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				try {
					fechaPasado = new Fecha(matcher_TiempoDesde.group(1)+"/"+matcher_TiempoDesde.group(2)+"/"+matcher_TiempoDesde.group(3),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e1) {
					return siguiente.calcular(pedido);
				}
				int meses = fechaPasado.restaFechas_Meses(fechaActual);
				if (meses < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + " entre el " + fechaPasado.fechaToString("d 'de' MMMMM 'de' yyyy");
				respuesta += " y el " + fechaActual.fechaToString("d 'de' MMMMM 'de' yyyy") + (meses != 1 ? " pasaron "  : " paso " ) + meses ;
				respuesta += (meses != 1 ? " meses" : " mes");
				return respuesta;
			}
		}
		String regex_TiempoDesde_sinA�o = ".*(?:pasaron desde el) (\\d+) (?:de) ([a-zA-Z]*) ?.";
		Pattern pattern_TiempoDesde_sinA�o = Pattern.compile(regex_TiempoDesde_sinA�o, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde_sinA�o = pattern_TiempoDesde_sinA�o.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde_sinA�o.find()) {
			if(matcher_TiempoDesde_sinA�o.matches()) {
				try {
					fechaPasado = new Fecha(matcher_TiempoDesde_sinA�o.group(1)+"/"+matcher_TiempoDesde_sinA�o.group(2)+"/"+fechaActual.getA�o(),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e2) {
					return siguiente.calcular(pedido);
				}
				int meses = fechaPasado.restaFechas_Meses(fechaActual);
				if (meses < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + (meses != 1 ? " pasaron "  : " paso " ) + meses ;
				respuesta += (meses != 1 ? " meses" : " mes");
				return respuesta;
			}
		}
		return siguiente.calcular(pedido);
	}
	

	private String tiempoDesde_A�os(Pedido pedido) {
		String respuesta = null;
		Fecha fechaActual = new Fecha();
		Fecha fechaPasado = null;
		String regex_TiempoDesde = ".*(?:pasaron desde el) (\\d+) (?:de) ([a-zA-Z]*) (?:de) (\\d+) ?.";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				try {
					fechaPasado = new Fecha(matcher_TiempoDesde.group(1)+"/"+matcher_TiempoDesde.group(2)+"/"+matcher_TiempoDesde.group(3),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e1) {
					return siguiente.calcular(pedido);
				}
				int a�os = fechaPasado.restaFechas_A�os(fechaActual);
				if (a�os < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + " entre el " + fechaPasado.fechaToString("d 'de' MMMMM 'de' yyyy");
				respuesta += " y el " + fechaActual.fechaToString("d 'de' MMMMM 'de' yyyy") + (a�os != 1 ? " pasaron "  : " paso " ) + a�os ;
				respuesta += (a�os != 1 ? " a�os" : " a�o");
				return respuesta;
			}
		}
		String regex_TiempoDesde_sinA�o = ".*(?:pasaron desde el) (\\d+) (?:de) ([a-zA-Z]*) ?.";
		Pattern pattern_TiempoDesde_sinA�o = Pattern.compile(regex_TiempoDesde_sinA�o, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde_sinA�o = pattern_TiempoDesde_sinA�o.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde_sinA�o.find()) {
			if(matcher_TiempoDesde_sinA�o.matches()) {
				try {
					fechaPasado = new Fecha(matcher_TiempoDesde_sinA�o.group(1)+"/"+matcher_TiempoDesde_sinA�o.group(2)+"/"+fechaActual.getA�o(),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e2) {
					return siguiente.calcular(pedido);
				}
				int a�os = fechaPasado.restaFechas_A�os(fechaActual);
				if (a�os < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + (a�os != 1 ? " pasaron "  : " paso " ) + a�os ;
				respuesta += (a�os != 1 ? " a�os" : " a�o");
				return respuesta;
			}
		}
		return siguiente.calcular(pedido);
	}
	
	
	private String tiempoHasta_Dias(Pedido pedido) {
		String respuesta = null;
		Fecha fechaActual = new Fecha();
		Fecha fechaFuturo = null;
		String regex_TiempoDesde = ".*(?:faltan para el) (\\d+) (?:de) ([a-zA-Z]*) (?:de) (\\d+) ?.";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				try {
					fechaFuturo = new Fecha(matcher_TiempoDesde.group(1)+"/"+matcher_TiempoDesde.group(2)+"/"+matcher_TiempoDesde.group(3),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e1) {
					return siguiente.calcular(pedido);
				}
				int dias = fechaActual.restaFechas_Dias(fechaFuturo);
				if (dias < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + " entre el " + fechaActual.fechaToString("d 'de' MMMMM 'de' yyyy");
				respuesta += " y el " + fechaFuturo.fechaToString("d 'de' MMMMM 'de' yyyy") + (dias != 1 ? " faltan "  : " falta " ) + dias ;
				respuesta += (dias != 1 ? " d�as" : " d�a");
				return respuesta;
			}
		}
		String regex_TiempoDesde_sinA�o = ".*(?:faltan para el) (\\d+) (?:de) ([a-zA-Z]*) ?.";
		Pattern pattern_TiempoDesde_sinA�o = Pattern.compile(regex_TiempoDesde_sinA�o, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde_sinA�o = pattern_TiempoDesde_sinA�o.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde_sinA�o.find()) {
			if(matcher_TiempoDesde_sinA�o.matches()) {
				try {
					fechaFuturo = new Fecha(matcher_TiempoDesde_sinA�o.group(1)+"/"+matcher_TiempoDesde_sinA�o.group(2)+"/"+fechaActual.getA�o(),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e2) {
					return siguiente.calcular(pedido);
				}
				int dias = fechaActual.restaFechas_Dias(fechaFuturo);
				if (dias < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + (dias != 1 ? " faltan "  : " falta " ) + dias ;
				respuesta += (dias != 1 ? " d�as" : " d�a");
				return respuesta;
			}
		}
		return siguiente.calcular(pedido);
	}

	private String tiempoHasta_Semanas(Pedido pedido) {
		String respuesta = null;
		Fecha fechaActual = new Fecha();
		Fecha fechaFuturo = null;
		String regex_TiempoDesde = ".*(?:faltan para el) (\\d+) (?:de) ([a-zA-Z]*) (?:de) (\\d+) ?.";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				try {
					fechaFuturo = new Fecha(matcher_TiempoDesde.group(1)+"/"+matcher_TiempoDesde.group(2)+"/"+matcher_TiempoDesde.group(3),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e1) {
					return siguiente.calcular(pedido);
				}
				int semanas = fechaActual.restaFechas_Semanas(fechaFuturo);
				if (semanas < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + " entre el " + fechaActual.fechaToString("d 'de' MMMMM 'de' yyyy");
				respuesta += " y el " + fechaFuturo.fechaToString("d 'de' MMMMM 'de' yyyy") + (semanas != 1 ? " faltan "  : " falta " ) + semanas ;
				respuesta += (semanas != 1 ? " semanas" : " semana");
				return respuesta;
			}
		}
		String regex_TiempoDesde_sinA�o = ".*(?:faltan para el) (\\d+) (?:de) ([a-zA-Z]*) ?.";
		Pattern pattern_TiempoDesde_sinA�o = Pattern.compile(regex_TiempoDesde_sinA�o, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde_sinA�o = pattern_TiempoDesde_sinA�o.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde_sinA�o.find()) {
			if(matcher_TiempoDesde_sinA�o.matches()) {
				try {
					fechaFuturo = new Fecha(matcher_TiempoDesde_sinA�o.group(1)+"/"+matcher_TiempoDesde_sinA�o.group(2)+"/"+fechaActual.getA�o(),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e2) {
					return siguiente.calcular(pedido);
				}
				int semanas = fechaActual.restaFechas_Semanas(fechaFuturo);
				if (semanas < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + (semanas != 1 ? " faltan "  : " falta " ) + semanas ;
				respuesta += (semanas != 1 ? " semanas" : " semana");
				return respuesta;
			}
		}
		return siguiente.calcular(pedido);
	}

	private String tiempoHasta_Meses(Pedido pedido) {
		String respuesta = null;
		Fecha fechaActual = new Fecha();
		Fecha fechaFuturo = null;
		String regex_TiempoDesde = ".*(?:faltan para el) (\\d+) (?:de) ([a-zA-Z]*) (?:de) (\\d+) ?.";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				try {
					fechaFuturo = new Fecha(matcher_TiempoDesde.group(1)+"/"+matcher_TiempoDesde.group(2)+"/"+matcher_TiempoDesde.group(3),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e1) {
					return siguiente.calcular(pedido);
				}
				int meses = fechaActual.restaFechas_Meses(fechaFuturo);
				if (meses < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + " entre el " + fechaActual.fechaToString("d 'de' MMMMM 'de' yyyy");
				respuesta += " y el " + fechaFuturo.fechaToString("d 'de' MMMMM 'de' yyyy") + (meses != 1 ? " faltan "  : " falta " ) + meses ;
				respuesta += (meses != 1 ? " meses" : " mes");
				return respuesta;
			}
		}
		String regex_TiempoDesde_sinA�o = ".*(?:faltan para el) (\\d+) (?:de) ([a-zA-Z]*) ?.";
		Pattern pattern_TiempoDesde_sinA�o = Pattern.compile(regex_TiempoDesde_sinA�o, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde_sinA�o = pattern_TiempoDesde_sinA�o.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde_sinA�o.find()) {
			if(matcher_TiempoDesde_sinA�o.matches()) {
				try {
					fechaFuturo = new Fecha(matcher_TiempoDesde_sinA�o.group(1)+"/"+matcher_TiempoDesde_sinA�o.group(2)+"/"+fechaActual.getA�o(),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e2) {
					return siguiente.calcular(pedido);
				}
				int meses = fechaActual.restaFechas_Meses(fechaFuturo);
				if (meses < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + (meses != 1 ? " faltan "  : " falta " ) + meses ;
				respuesta += (meses != 1 ? " meses" : " mes");
				return respuesta;
			}
		}
		return siguiente.calcular(pedido);
	}
	

	private String tiempoHasta_A�os(Pedido pedido) {
		String respuesta = null;
		Fecha fechaActual = new Fecha();
		Fecha fechaFuturo = null;
		String regex_TiempoDesde = ".*(?:faltan para el) (\\d+) (?:de) ([a-zA-Z]*) (?:de) (\\d+) ?.";
		Pattern pattern_TiempoDesde = Pattern.compile(regex_TiempoDesde, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde = pattern_TiempoDesde.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde.find()) {
			if(matcher_TiempoDesde.matches()) {
				try {
					fechaFuturo = new Fecha(matcher_TiempoDesde.group(1)+"/"+matcher_TiempoDesde.group(2)+"/"+matcher_TiempoDesde.group(3),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e1) {
					return siguiente.calcular(pedido);
				}
				int a�os = fechaActual.restaFechas_A�os(fechaFuturo);
				if (a�os < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + " entre el " + fechaActual.fechaToString("d 'de' MMMMM 'de' yyyy");
				respuesta += " y el " + fechaFuturo.fechaToString("d 'de' MMMMM 'de' yyyy") + (a�os != 1 ? " faltan "  : " falta " ) + a�os ;
				respuesta += (a�os != 1 ? " a�os" : " a�o");
				return respuesta;
			}
		}
		String regex_TiempoDesde_sinA�o = ".*(?:faltan para el) (\\d+) (?:de) ([a-zA-Z]*) ?.";
		Pattern pattern_TiempoDesde_sinA�o = Pattern.compile(regex_TiempoDesde_sinA�o, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		Matcher matcher_TiempoDesde_sinA�o = pattern_TiempoDesde_sinA�o.matcher(pedido.getMensaje());
		while(matcher_TiempoDesde_sinA�o.find()) {
			if(matcher_TiempoDesde_sinA�o.matches()) {
				try {
					fechaFuturo = new Fecha(matcher_TiempoDesde_sinA�o.group(1)+"/"+matcher_TiempoDesde_sinA�o.group(2)+"/"+fechaActual.getA�o(),"d'/'MMMMM'/'yyyy");
				} catch (ParseException e2) {
					return siguiente.calcular(pedido);
				}
				int a�os = fechaActual.restaFechas_A�os(fechaFuturo);
				if (a�os < 0)
					return siguiente.calcular(pedido);
				respuesta  = pedido.getNameUsuario() + (a�os != 1 ? " faltan "  : " falta " ) + a�os ;
				respuesta += (a�os != 1 ? " a�os" : " a�o");
				return respuesta;
			}
		}
		return siguiente.calcular(pedido);
	}
	
}
