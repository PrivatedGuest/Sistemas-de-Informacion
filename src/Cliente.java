import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Cliente {

	static CallableStatement cstmt;

	public static void menuCliente(String DNI) {
		int seleccion = 0;
		boolean principio = false;
		do {
			if (principio) {
				System.out.println("");
				System.out.println("Introdzca un caracter para imprimir o menu");
				Log_in.teclado.next();
			}
			principio = true;
			System.out.println("Escolla unha das opcions:");
			System.out.println("1: Listar espectaculos");
			System.out.println("2: Listar eventos");
			System.out.println("3: Listar gradas");
			System.out.println("4: Listar localidades");
			System.out.println("5: Listar participantes");
			System.out.println("6: Reservar/pre-reservar localidades");
			System.out.println("7: Anular pre-reserva/reserva");
			System.out.println("0: Sair");
			seleccion = Log_in.teclado.nextInt();

			switch (seleccion) {
			case 1:
				ListarEspectaculos();
				break;
			case 2:
				ListarEventos();
				break;
			case 3:
				ListarGradas();
				break;
			case 4:
				ListarLocalidades();
				break;
			case 5:
				ListarParticipantes();
				break;
			case 6:
				ReservarLocalidade(DNI);
				break;
			case 7:
				AnularReserva();
				break;
			case 0:
				System.out.println("Ata logo");
				break;
			default:
				System.out.println("Opcion invalida");
				break;
			}
		} while (seleccion != 0);
	}

	public static void ListarEspectaculos() {
		String[] auxiliar;
		do {
			// Forzamos a que nos de ese formato, non ten nada que ver cos valores do mesmo
			// que é BD(SQL)
			System.out.println(
					"Formato filtro: Tipo-participantes-Nombre_espectaculo.Nos campos onde non queira filtrar escriba '*'");
			auxiliar = Log_in.teclado.next().replace("*", "%").split("-");
			// A funcion de arriba complemente fai un split cambiando antes o '*' por '%'
			if (auxiliar.length != 3) {
				System.out.println("O formato non é o correcto");
			}
		} while (auxiliar.length != 3);

		try {
			ArrayList<Obtener_espectaculos> Resultado = new ArrayList<Obtener_espectaculos>();
			cstmt = Log_in.connection.prepareCall("call obtener_espectaculos(?,?,?)");
			// As 3 abaixo encárganse de rellenar as interrogacións de arriba
			cstmt.setString(1, auxiliar[0]);
			cstmt.setString(2, auxiliar[1]);
			cstmt.setString(3, auxiliar[2]);
			ResultSet respuesta = cstmt.executeQuery();// Neste metodo executase o de antes
			/*
			 * Respuesta vai ter todo o que devolve o select, o facer un iterator en cada
			 * bucle do while respuesta solo sera unha tupla, asi podemos coller as columnas
			 * con respuesta.getString(nºcolumna) Con eses datos creamos un obxeto que
			 * metemos nun array
			 */

			while (respuesta.next()) {
				Resultado.add(new Obtener_espectaculos(respuesta.getString(1), respuesta.getString(2),
						respuesta.getString(3), respuesta.getString(4)));
			}
			// Co bucle de abaixo gardamos o tamaño maximo de algunha das tupas
			int dm = "descripcion".length(), idm = "id_espectaculo".length(), tm = "tipo".length(),
					pm = "participante".length();
			for (Obtener_espectaculos x : Resultado) {
				if (x.getId_espectaculo().length() > idm)
					idm = x.getId_espectaculo().length();
				if (x.getTipo().length() > tm)
					tm = x.getTipo().length();
				if (x.getDescripcion().length() > dm)
					dm = x.getDescripcion().length();
				if (x.getParticipante().length() > pm)
					pm = x.getParticipante().length();
			}
			// Vamos sumarlle algo de tamaño para que non esten pegados
			tm = tm + 3;
			pm = pm + 3;
			idm = idm + 3;
			dm = dm + 3;
			// Con "Metodo" de abaixo estamos facendo a tabulacion, ataa primeira coma
			// indicamos canto se van separar os argumentos
			// Por eso gardamos antes o tamaño maximo, asi crearemos unha taboa
			// dinamica.Despois da coma simplemente escribimos
			// entre comas os argumentos.Esto escribe a cabeceira
			System.out.println(String.format("%-" + idm + "s%-" + tm + "s%-" + dm + "s%-" + pm + "s", "id_espectaculo",
					"Tipo", "Descripcion", "Participante"));
			// Aqui facemos un bucle onde escribimos entre as mesmas distancias o que
			// queremos, e un iterator do array de antes
			// polo que cada "X" sera un dos obxetos que gardamos
			for (Obtener_espectaculos x : Resultado)
				System.out.println(String.format("%-" + idm + "s%-" + tm + "s%-" + dm + "s%-" + pm + "s",
						x.getId_espectaculo(), x.getTipo(), x.getDescripcion(), x.getParticipante()));
			// O metodo que da fallo e o de "executequery" se o resultado e
			// empty,enton....eso
		} catch (SQLException e1) {
			System.out.println("Non se atopou ningún resultado coa súa búsqueda.");
		}

	}

	public static void ListarEventos() {

		// O formate real vai ser
		// Estado-Espectaculo-Recinto-Fecha-Precio-Tipo_Usuario-Numero_entradas_dispoñibles
		// Log_in_consulta refirese para que tipo de Log_in vas a querer consultar o
		// precio
		// Numero_entradas refirese a sacar X eventos que teñan ese número de entradas
		// de tipo Tipo_Log_in
		
		String[] auxiliar;
		String aux = null;
		ArrayList<String> enviar = new ArrayList<String>();

		do {
			System.out.println(
					"Para facer unha filtracion siga o formato Estado-Espectaculo-Recinto-Fecha-Precio-Tipo_Usuario.Nos campos onde non queira filtrar escriba '*'");
			auxiliar = Log_in.teclado.next().replace("*", "%").split("-");

			if (auxiliar.length != 6) {
				System.out.println("Non sigue o formato correcto");
			}

		} while (auxiliar.length != 6);
		// Cargamos todo no ArrayList
		for (int i = 0; i < 6; i++) {
			enviar.add(auxiliar[i]);
		}

		if (!auxiliar[4].equals("%") && auxiliar[5].equals("%")) {
			System.out.println(
					"Para que tipo de Usuario quere facer a consulta do prezo?\n\tBebe\n\tInfantil\n\tAdulto\n\tParado\n\tJubilado");
			enviar.remove(5);
			enviar.add(Log_in.teclado.next());
		}
		if (!auxiliar[5].equals("%")) {
			System.out.println("Cuantas entradas de tipo " + auxiliar[5]
					+ " deben ter dispoñibles como mínimo os eventos que quere ver?");
			aux = Log_in.teclado.next();
			enviar.add(aux);
		} else {
			enviar.add("%");
		}
		ArrayList<Obtener_eventos> Resultado = new ArrayList<Obtener_eventos>();
		try {
			// EEEEEEEEEEEEEEEEEEEEEEE AQUI CHAMAR O METODO DE VERDADE
			cstmt = Log_in.connection.prepareCall("call obtener_eventos(?,?,?,?,?,?,?)");
			cstmt.setString(1, enviar.get(0));
			cstmt.setString(2, enviar.get(1));
			cstmt.setString(3, enviar.get(2));
			cstmt.setString(4, enviar.get(3));
			cstmt.setString(5, enviar.get(4));
			cstmt.setString(6, enviar.get(5));
			cstmt.setString(7, enviar.get(6));
			ResultSet respuesta = cstmt.executeQuery();
			while (respuesta.next()) {
				Resultado.add(new Obtener_eventos(respuesta.getString("id_espectaculo"),
						respuesta.getString("id_recinto"), respuesta.getString("fecha"), respuesta.getString("estado"),
						respuesta.getString("Entradas_bebe"), respuesta.getString("Entradas_infantil"),
						respuesta.getString("Entradas_parado"), respuesta.getString("Entradas_adulto"),
						respuesta.getString("Entradas_jubilado")));
			}
		} catch (Exception e) {
			System.out.println("Non se encontrou ningún resultado coa súa búsqueda");
		}

		int fm = "fecha".length(), idm = "id_espectaculo".length(), recm = "id_recinto".length(),
				estm = "estado".length(), ebm = "E_bebe".length(), epm = "E_parad".length(), efm = "E_inf".length(),
				eam = "E_adul".length(), ejm = "E_jub".length();
		for (Obtener_eventos x : Resultado) {
			if (x.getId_espectaculo().length() > idm)
				idm = x.getId_espectaculo().length();
			if (x.getFecha().length() > fm)
				fm = x.getFecha().length();
			if (x.getId_recinto().length() > recm)
				recm = x.getId_recinto().length();
			if (x.getEstado().length() > estm)
				estm = x.getEstado().length();
			if (x.getE_bebe().length() > ebm)
				ebm = x.getE_bebe().length();
			if (x.getE_Inf().length() > efm)
				efm = x.getE_Inf().length();
			if (x.getE_Adul().length() > eam)
				eam = x.getE_Adul().length();
			if (x.getE_Jub().length() > ejm)
				ejm = x.getE_Jub().length();
		}
		fm = fm + 3;
		idm = idm + 3;
		recm = recm + 3;
		estm = estm + 3;
		ebm = ebm + 1;
		efm = efm + 1;
		eam = eam + 1;
		epm = epm + 1;
		ejm = ejm + 1;

		System.out.println(String.format(
				"%-" + idm + "s%-" + estm + "s%-" + fm + "s%-" + recm + "s%-" + ebm + "s%-" + efm + "s%-" + eam + "s%-"
						+ epm + "s%-" + ejm + "s",
				"id_espectaculo", "estado", "fecha", "id_recinto", "E_bebe", "E_inf", "E_adul", "E_parad", "E_jub"));
		for (Obtener_eventos x : Resultado)
			System.out.println(String.format(
					"%-" + idm + "s%-" + estm + "s%-" + fm + "s%-" + recm + "s%-" + ebm + "s%-" + efm + "s%-" + eam
							+ "s%-" + epm + "s%-" + ejm + "s",
					x.getId_espectaculo(), x.getEstado(), x.getFecha(), x.getId_recinto(), x.getE_bebe(), x.getE_Inf(),
					x.getE_Adul(), x.getE_Para(), x.getE_Jub()));

	}

	public static void ListarGradas() {
		String[] auxiliar;
		ArrayList<String> enviar = new ArrayList<String>();
		ArrayList<Obtener_gradas> Resultado = new ArrayList<Obtener_gradas>();
		do {
			System.out.println("Para introducir unha grada,primeiro debe introducir Espectaculo-Recinto-Fecha");
			auxiliar = Log_in.teclado.next().replace("*", "%").split("-");
		} while (auxiliar.length != 3);
		for (int i = 0; i < 3; i++) {
			enviar.add(auxiliar[i]);
		}
		System.out.println("Que precio desexa engadir como máximo(pulsar '*' se non quere filtro)");
		enviar.add(Log_in.teclado.next().replace("*", "%"));
		if (!enviar.get(3).equals("%")) {
			System.out.println(
					"Para que tipo de Log_in quere facer a consulta do prezo?\n\tBebe\n\tInfantil\n\tAdulto\n\tParado\n\tJubilado");
			enviar.add(Log_in.teclado.next());
		} else {
			enviar.add("%");// Este polo tipo de Log_in
		}

		try {
			// EEEEEEEEEEEEEEEEEEEEEEE AQUI CHAMAR O METODO DE VERDADE
			cstmt = Log_in.connection.prepareCall("call obtener_gradas(?,?,?,?,?)");
			cstmt.setString(1, enviar.get(0));
			cstmt.setString(2, enviar.get(1));
			cstmt.setString(3, enviar.get(2));
			cstmt.setString(4, enviar.get(3));
			cstmt.setString(5, enviar.get(4));
			ResultSet respuesta = cstmt.executeQuery();

			while (respuesta.next()) {
				Resultado.add(
						new Obtener_gradas(respuesta.getString("id_espectaculo"), respuesta.getString("id_recinto"),
								respuesta.getString("id_grada"), respuesta.getString("fecha"),
								respuesta.getString("N_localidades"), respuesta.getString("Precio_bebe"),
								respuesta.getString("Precio_infantil"), respuesta.getString("Precio_adulto"),
								respuesta.getString("Precio_parado"), respuesta.getString("Precio_jubilado")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Non se encontrou ningún resultado coa súa búsqueda");
		}

		int fm = "fecha".length(), idm = "id_espectaculo".length(), recm = "id_recinto".length(),
				pbm = "P_bebe".length(), ppm = "P_parad".length(), grm = "grada".length(), mpm = "Max_Preres".length(),
				pfm = "P_inf".length(), pam = "P_adul".length(), pjm = "P_jub".length();
		for (Obtener_gradas x : Resultado) {
			if (x.getId_espectaculo().length() > idm)
				idm = x.getId_espectaculo().length();
			if (x.getFecha().length() > fm)
				fm = x.getFecha().length();
			if (x.getId_recinto().length() > recm)
				recm = x.getId_recinto().length();
			if (x.getP_parad().length() > ppm)
				ppm = x.getP_bb().length();
			if (x.getP_bb().length() > pbm)
				pbm = x.getP_bb().length();
			if (x.getP_inf().length() > pfm)
				pfm = x.getP_inf().length();
			if (x.getP_adul().length() > pam)
				pam = x.getP_adul().length();
			if (x.getP_jub().length() > pjm)
				pjm = x.getP_jub().length();
		}
		fm = fm + 3;
		idm = idm + 3;
		recm = recm + 3;
		mpm = mpm + 1;
		grm = grm + 1;
		pbm = pbm + 1;
		pfm = pfm + 1;
		pam = pam + 1;
		ppm = ppm + 1;
		pjm = pjm + 1;

		System.out.println(String.format(
				"%-" + grm + "s%-" + pbm + "s%-" + pfm + "s%-" + pam + "s%-" + ppm + "s%-" + pjm + "s%-" + mpm + "s%-"
						+ idm + "s%-" + recm + "s%-" + +fm + "s",
				"Grada", "P_bebe", "P_inf", "P_adul", "P_parad", "P_jub", "Max_Preres", "id_espectaculo", "id_recinto",
				"fecha"));

		for (Obtener_gradas x : Resultado)
			System.out.println(String.format(
					"%-" + grm + "s%-" + pbm + "s%-" + pfm + "s%-" + pam + "s%-" + ppm + "s%-" + pjm + "s%-" + mpm
							+ "s%-" + idm + "s%-" + recm + "s%-" + +fm + "s",
					x.getId_grada(), x.getP_bb(), x.getP_inf(), x.getP_adul(), x.getP_parad(), x.getP_jub(),
					x.getMax_preser(), x.getId_espectaculo(), x.getId_recinto(), x.getFecha()));

	}

	public static void ListarLocalidades() {
		String[] auxiliar;
		String aux = null;
		ArrayList<String> enviar = new ArrayList<String>();
		ArrayList<Obtener_localidades> Resultado = new ArrayList<Obtener_localidades>();
		do {
			System.out.println(
					"Para listar as localidades debe ser dun evento en concreto, escriba espectaculo-recinto-fecha");
			auxiliar = Log_in.teclado.next().replace("*", "%").split("-");
		} while (auxiliar.length != 3);

		for (int i = 0; i < 3; i++) {
			enviar.add(auxiliar[i]);
		}
		System.out.println("Desexa ver solo as localidades libres?(SI/NO)");
		if (Log_in.teclado.next().toLowerCase().equals("si")) {
			enviar.add("libre");
		} else {
			enviar.add("%");
		}
		System.out.println("Que precio desexa engadir como máximo(pulsar '*' se non quere filtro)");
		aux = Log_in.teclado.next().replace("*", "%");
		if (!aux.equals("%")) {
			enviar.add(aux);
			System.out.println(
					"Para que tipo de Log_in quere facer a consulta do prezo?\n\tBebe\n\tInfantil\n\tAdulto\n\tParado\n\tJubilado");
			aux = Log_in.teclado.next();
			enviar.add(aux);
		} else {
			enviar.add("%");
			enviar.add("%");// Este polo tipo de Log_in
		}

		try {
			cstmt = Log_in.connection.prepareCall("call obtener_localidades(?,?,?,?,?,?)");
			cstmt.setString(1, enviar.get(0));
			cstmt.setString(2, enviar.get(1));
			cstmt.setString(3, enviar.get(2));
			cstmt.setString(4, enviar.get(3));
			cstmt.setString(5, enviar.get(4));
			cstmt.setString(6, enviar.get(5));
			ResultSet respuesta = cstmt.executeQuery();

			while (respuesta.next()) {
				Resultado.add(new Obtener_localidades(respuesta.getString("id_localidade"),
						respuesta.getString("estado"), respuesta.getString("id_recinto"),
						respuesta.getString("id_grada"), respuesta.getString("Precio_bebe"),
						respuesta.getString("Precio_Infantil"), respuesta.getString("Precio_adulto"),
						respuesta.getString("Precio_parado"), respuesta.getString("Precio_jubilado")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Non se atopou o que busca");
			System.exit(0);
		}

		int recm = "id_recinto".length(), est = "Estado".length(), pbm = "P_bebe".length(), ppm = "P_parad".length(),
				grm = "grada".length(), ilm = "ID_local".length(), pfm = "P_inf".length(), pam = "P_adul".length(),
				pjm = "P_jub".length();

		for (Obtener_localidades x : Resultado) {
			if (x.getId_recinto().length() > recm)
				recm = x.getId_recinto().length();
			if (x.get_estado().length() > est)
				est = x.get_estado().length();
			if (x.getP_parad().length() > ppm)
				ppm = x.getP_bb().length();
			if (x.getP_bb().length() > pbm)
				pbm = x.getP_bb().length();
			if (x.getP_inf().length() > pfm)
				pfm = x.getP_inf().length();
			if (x.getP_adul().length() > pam)
				pam = x.getP_adul().length();
			if (x.getP_jub().length() > pjm)
				pjm = x.getP_jub().length();
			if (x.getId_grada().length() > grm)
				grm = x.getId_grada().length();
			if (x.getId_localidade().length() > ilm)
				ilm = x.getId_localidade().length();
		}
		recm = recm + 3;
		grm = grm + 1;
		pbm = pbm + 1;
		est = est + 3;
		pfm = pfm + 1;
		pam = pam + 1;
		ppm = ppm + 1;
		pjm = pjm + 1;
		ilm = ilm + 1;

		System.out.println(String.format(
				"%-" + ilm + "s%-" + est + "s%-" + grm + "s%-" + pbm + "s%-" + pfm + "s%-" + pam + "s%-" + ppm + "s%-"
						+ pjm + "s%-" + recm + "s",
				"ID_local", "Estado", "Grada", "P_bebe", "P_inf", "P_adul", "P_parad", "P_jub", "id_recinto"));

		for (Obtener_localidades x : Resultado)
			System.out.println(String.format(
					"%-" + ilm + "s%-" + est + "s%-" + grm + "s%-" + pbm + "s%-" + pfm + "s%-" + pam + "s%-" + ppm
							+ "s%-" + pjm + "s%-" + recm + "s",
					x.getId_localidade(), x.get_estado(), x.getId_grada(), x.getP_bb(), x.getP_inf(), x.getP_adul(),
					x.getP_parad(), x.getP_jub(), x.getId_recinto()));

	}

	public static void ListarParticipantes() {
		ArrayList<Obtener_participantes> Resultado = new ArrayList<Obtener_participantes>();
		String[] aux;
		do {
			System.out.println(
					"Pode filtrar a lista de participantes por Espectaculo-Recinto.Escriba * no campo onde non quere filtrar");
			aux = Log_in.teclado.next().replace("*", "%").split("-");
		} while (aux.length != 2);
		try {
			cstmt = Log_in.connection.prepareCall("call obtener_participantes(?,?)");
			cstmt.setString(1, aux[0]);
			cstmt.setString(2, aux[1]);
			ResultSet respuesta = cstmt.executeQuery();

			while (respuesta.next()) {
				Resultado.add(new Obtener_participantes(respuesta.getString("participante"),
						respuesta.getString("id_espectaculo"), respuesta.getString("fecha"),
						respuesta.getString("id_recinto"), respuesta.getString("tipo")));
			}
		} catch (SQLException e) {
			System.out.println("Lista vacía, non se atopou ningun resultado");
		}

		int fm = "Fecha".length(), idm = "ID_espectaculo".length(), recm = "ID_recinto".length(), tpm = "Tipo".length(),
				prm = "Participante".length();
		for (Obtener_participantes x : Resultado) {
			if (x.getId_espectaculo().length() > idm)
				idm = x.getId_espectaculo().length();
			if (x.getFecha().length() > fm)
				fm = x.getFecha().length();
			if (x.getId_recinto().length() > recm)
				recm = x.getId_recinto().length();
			if (x.getParticipante().length() > prm)
				prm = x.getId_recinto().length();
			if (x.getTipo().length() > tpm)
				tpm = x.getTipo().length();
		}
		fm = fm + 3;
		idm = idm + 3;
		recm = recm + 3;
		tpm = tpm + 1;
		prm = prm + 3;

		System.out.println(String.format("%-" + prm + "s%-" + idm + "s%-" + fm + "s%-" + recm + "s%" + tpm + "s",
				"Participante", "ID_espectaculo", "Fecha", "Recinto", "Tipo"));

		for (Obtener_participantes x : Resultado)
			System.out.println(String.format("%-" + prm + "s%-" + idm + "s%-" + fm + "s%-" + recm + "s%" + tpm + "s",
					x.getParticipante(), x.getId_espectaculo(), x.getFecha(), x.getId_recinto(), x.getTipo()));

	}

	public static void ReservarLocalidade(String Cliente) {

		System.out.println("Introduzca o ID do espectaculo:");
		String id_espectaculo = Log_in.teclado.next();
		System.out.println("Introduzca o ID do recinto:");
		String id_recinto = Log_in.teclado.next();
		String fecha = Admin.dameFecha();
		System.out.println("Introduzca o ID da grada:");
		String id_grada = Log_in.teclado.next();
		System.out.println("Introduzca o ID da localidade:");
		String id_localidade = Log_in.teclado.next();

		System.out.println("Desea 1)Reservar ou 2)Pre-reservar a localidade");
		int eleccion = Log_in.teclado.nextInt();

		try {
			cstmt = Log_in.connection.prepareCall("call pre_reserva(?,?,?,?,?,?,?)");
			cstmt.setString(1, id_espectaculo);
			cstmt.setString(2, id_recinto);
			cstmt.setString(3, fecha);
			cstmt.setString(4, id_grada);
			cstmt.setString(5, id_localidade);
			cstmt.setString(6, Cliente);
			if (eleccion == 1)
				cstmt.setString(7, "reservado");
			else if (eleccion == 2)
				cstmt.setString(7, "pre-reservado");
			else {
				System.out.println("Opcion invalida");
				return;
			}
			cstmt.executeQuery();
			System.out.println("Localidad reservada/pre-reservada");
		} catch (Exception e) {
			System.out.println("Introduciu incorrectamente un dos campos");
		}

	}

	public static void AnularReserva() {

		System.out.println("Introduzca o ID do espectaculo:");
		String id_espectaculo = Log_in.teclado.next();
		System.out.println("Introduzca o ID do recinto:");
		String id_recinto = Log_in.teclado.next();
		String fecha = Admin.dameFecha();
		System.out.println("Introduzca o ID da grada:");
		String id_grada = Log_in.teclado.next();
		System.out.println("Introduzca o ID da localidade:");
		String id_localidade = Log_in.teclado.next();

		try {
			cstmt = Log_in.connection.prepareCall("call anulacion(?,?,?,?,?)");
			cstmt.setString(1, id_espectaculo);
			cstmt.setString(2, id_recinto);
			cstmt.setString(3, fecha);
			cstmt.setString(4, id_grada);
			cstmt.setString(5, id_localidade);
			cstmt.executeQuery();
			System.out.println("Reserva/pre-reserva anulada");
		} catch (Exception e) {
			System.out.println("Introduciu incorrectamente un dos campos");
		}

	}

	public static void BorrarArray(ArrayList<String> x) {

		do {
			x.remove(0);
		} while (!x.isEmpty());

	}

}