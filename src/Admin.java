import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Admin {

	public static void menuAdmin() {
		int seleccion = 0;

		do {
			System.out.println("Escolla unha das opcions:");
			System.out.println("1: Crear cliente");
			System.out.println("2: Crear recinto");
			System.out.println("3: Crear espectaculo");
			System.out.println("4: Crear evento");
			System.out.println("5: Crear grada");
			System.out.println("6: Asociar participante");
			System.out.println("7: Eliminar cliente");
			System.out.println("8: Eliminar recinto");
			System.out.println("9: Eliminar espectaculo");
			System.out.println("10: Eliminar evento");
			System.out.println("11: Eliminar grada");
			System.out.println("12: Listar");
			System.out.println("0: Sair");
			seleccion = Log_in.teclado.nextInt();

			switch (seleccion) {
			case 1:
				crearCliente();
				break;
			case 2:
				CrearRecinto();
				break;
			case 3:
				CrearEspectaculo();
				break;
			case 4:
				CrearEvento();
				break;
			case 5:
				CrearGrada();
				break;
			case 6:
				System.out.println("Que participante quere asociar?");
				String participante=Log_in.teclado.next();
				System.out.println("A que espectaculo?");
				String espectaculo=Log_in.teclado.next();
				asociarParticipante(participante,espectaculo);
			case 7:
				eliminarCliente();
				break;
			case 8:
				EliminarRecinto();
				break;
			case 9:
				EliminarEspectaculo();
				break;
			case 10:
				EliminarEvento();
				break;
			case 11:
				EliminarGrada();
				break;
			case 12:
				ListarAdmin();
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

	public static void crearCliente() {
		System.out.println("Introduzca o DNI:");
		String DNI = Log_in.teclado.next();
		System.out.println("Introduzca o nome:");
		String Nombre = Log_in.teclado.next();
		System.out.println("Introduzca os apellidos:");
		String Apellidos = Log_in.teclado.next();
		System.out.println("Introduzca o correo electronico:");
		String email = Log_in.teclado.next();
		System.out.println("Introduzca a tarxeta de credito:");
		String tarjeta = Log_in.teclado.next();
		System.out.println("Introduzca o tipo de Log_in:");
		String tipo = Log_in.teclado.next();

		String update = "insert into cliente values('" + DNI + "','" + Nombre + "','" + Apellidos + "','" + email
				+ "','" + tarjeta + "','" + tipo + "')";

		try {
			Log_in.stmt.executeUpdate(update);
			System.out.println("Cliente insertado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void CrearRecinto() {
		System.out.println("Introduzca o ID:");
		String id_recinto = Log_in.teclado.next();
		System.out.println("Introduzca o tipo:");
		String tipo = Log_in.teclado.next();
		System.out.println("Introduzca o aforo:");
		String aforo = Log_in.teclado.next();

		String update = "insert into recinto values('" + id_recinto + "','" + tipo + "','" + aforo + "')";

		try {
			Log_in.stmt.executeUpdate(update);
			System.out.println("Recinto creado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void CrearEspectaculo() {
		System.out.println("Introduzca o ID:");
		String id_espectaculo = Log_in.teclado.next();
		System.out.println("Introduzca a descripcion:");
		String descripcion = Log_in.teclado.next();
		System.out.println("Introduzca o tipo:");
		String tipo = Log_in.teclado.next();

		String update = "insert into espectaculo values('" + id_espectaculo + "','" + descripcion + "','" + tipo + "')";

		try {
			Log_in.stmt.executeUpdate(update);
			System.out.println("Espectaculo insertado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Digame un participante del evento");
		asociarParticipante(Log_in.teclado.next(),id_espectaculo);
	}

	public static void CrearEvento() {
		System.out.println("Introduzca o ID do espectaculo:");
		String id_espectaculo = Log_in.teclado.next();
		System.out.println("Introduzca o ID do recinto:");
		String id_recinto = Log_in.teclado.next();
		String fecha = dameFecha();
		System.out.println("Introduzca o estado:");
		String estado = Log_in.teclado.next();
		System.out.println("Introduzca t1:");
		String t1 = Log_in.teclado.next();
		System.out.println("Introduzca t2:");
		String t2 = Log_in.teclado.next();
		System.out.println("Introduzca t3:");
		String t3 = Log_in.teclado.next();
		System.out.println("Introduzca o numero de entradas de xubilado:");
		String entradas_jubilado = Log_in.teclado.next();
		System.out.println("Introduzca o numero de entradas de bebe:");
		String entradas_bebe = Log_in.teclado.next();
		System.out.println("Introduzca o numero de entradas de adulto:");
		String entradas_adulto = Log_in.teclado.next();
		System.out.println("Introduzca o numero de entradas de parado:");
		String entradas_parado = Log_in.teclado.next();
		System.out.println("Introduzca o numero de entradas de infantil:");
		String entradas_infantil = Log_in.teclado.next();

		String update = "insert into evento values('" + id_espectaculo + "','" + id_recinto + "','" + fecha + "','"
				+ estado + "','" + t1 + "','" + t2 + "','" + t3 + "','" + entradas_jubilado + "','" + entradas_bebe
				+ "','" + entradas_adulto + "','" + entradas_parado + "','" + entradas_infantil + "');";

		try {
			String query = "select * from espectaculo where ID_espectaculo='" + id_espectaculo + "'";
			ResultSet rs = Log_in.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("Non existe o espectaculo");
				return;
			}
			query = "select * from recinto where ID_recinto='" + id_recinto + "'";
			rs = Log_in.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("Non existe o recinto");
				return;
			}
			Log_in.stmt.executeUpdate(update);
			System.out.println("Espectaculo insertado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void CrearGrada() {

		System.out.println("Introduzca o ID do espectaculo:");
		String id_espectaculo = Log_in.teclado.next();
		System.out.println("Introduzca o ID do recinto:");
		String id_recinto = Log_in.teclado.next();
		String fecha = dameFecha();
		System.out.println("Introduzca o ID da grada:");
		String id_grada = Log_in.teclado.next();
		System.out.println("Introduzca o numero de localidades:");
		String n_localidades = Log_in.teclado.next();
		System.out.println("Introduzca o prezo das entradas de bebe:");
		String precio_bebe = Log_in.teclado.next();
		System.out.println("Introduzca o prezo das entradas de infantil:");
		String precio_infantil = Log_in.teclado.next();
		System.out.println("Introduzca o prezo das entradas de parado:");
		String precio_parado = Log_in.teclado.next();
		System.out.println("Introduzca o prezo das entradas de adulto:");
		String precio_adulto = Log_in.teclado.next();
		System.out.println("Introduzca o prezo das entradas de xubilado:");
		String precio_jubilado = Log_in.teclado.next();
		System.out.println("Introduzca a localidade de inicio:");
		String local_ini = Log_in.teclado.next();
		System.out.println("Introduzca a localidade de fin:");
		String local_fin = Log_in.teclado.next();

		try {
			CallableStatement cstmt = Log_in.connection.prepareCall("call crear_gradas(?,?,?,?,?,?,?,?,?,?,?,?)");
			cstmt.setString(1, id_espectaculo);
			cstmt.setString(2, id_recinto);
			cstmt.setString(3, fecha);
			cstmt.setString(4, id_grada);
			cstmt.setString(5, n_localidades);
			cstmt.setString(6, precio_bebe);
			cstmt.setString(7, precio_infantil);
			cstmt.setString(8, precio_parado);
			cstmt.setString(9, precio_adulto);
			cstmt.setString(10, precio_jubilado);
			cstmt.setString(11, local_ini);
			cstmt.setString(12, local_fin);
			cstmt.executeQuery();
			System.out.println("Grada insertada");
		} catch (Exception e) {
			System.out.println("Introduciu incorrectamente un dos campos");
		}

	}

	public static void eliminarCliente() {
		System.out.println("Introduzca o DNI:");
		String DNI = Log_in.teclado.next();

		String update = "DELETE FROM cliente WHERE DNI='" + DNI + "'";

		try {
			String query = "select 1 from localidade where Cliente='" + DNI + "'";
			ResultSet rs = Log_in.stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("Non se pode eliminar o cliente porque ten localidades pendentes");
				return;
			}
			query = "select 1 from Cliente where DNI='" + DNI + "'";
			rs = Log_in.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("Non existe cliente co DNI: " + DNI);
				return;
			}
			Log_in.stmt.executeUpdate(update);
			System.out.println("Cliente eliminado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void asociarParticipante(String participante,String espectaculo) {
		String query ="insert into espectaculo_participante values('"+espectaculo+"','"+participante+"')";
		try {
			Log_in.stmt.executeUpdate(query);
			//Log_in.connection.prepareCall()
		} catch (SQLException e) {
			e.printStackTrace();
		System.out.println("Espectaculo introducido incorrectamente");
		}
	}
	
	
	public static void EliminarRecinto() {
		System.out.println("Introduzca o ID:");
		String id_recinto = Log_in.teclado.next();

		String update = "DELETE FROM recinto WHERE id_recinto='" + id_recinto + "'";

		try {
			String query = "select 1 from evento where ID_recinto='" + id_recinto + "'";
			ResultSet rs = Log_in.stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("Non se pode eliminar o recinto, elimine primeiro os eventos nel");
				return;
			}
			query = "select 1 from recinto where id_recinto='" + id_recinto + "'";
			rs = Log_in.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("Non existe id_recinto co ID: " + id_recinto);
				return;
			}
			Log_in.stmt.executeUpdate(update);
			System.out.println("Recinto eliminado");
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void EliminarEspectaculo() {
		System.out.println("Introduzca o ID:");
		String id_espectaculo = Log_in.teclado.next();

		String update = "DELETE FROM espectaculo WHERE id_espectaculo='" + id_espectaculo + "'";

		try {
			String query = "select ID_espectaculo from evento where ID_espectaculo='" + id_espectaculo + "'";
			ResultSet rs = Log_in.stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("Non se pode eliminar o espectaculo, elimine primeiro os seus eventos");
				return;
			}
			query = "select 1 from espectaculo where id_espectaculo='" + id_espectaculo + "'";
			rs = Log_in.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("Non existe espectaculo co ID: " + id_espectaculo);
				return;
			}
			Log_in.stmt.executeUpdate(update);
			System.out.println("Recinto eliminado");
		} catch (SQLException e) {
			System.out.println("Procedese a eliminar o espectaculo: " + id_espectaculo);
		}
	}

	public static void EliminarEvento() {
		System.out.println("Introduzca o ID do espectaculo:");
		String id_espectaculo = Log_in.teclado.next();
		System.out.println("Introduzca o ID do recinto:");
		String id_recinto = Log_in.teclado.next();
		String fecha = dameFecha();

		String update = "DELETE FROM evento where ID_espectaculo='" + id_espectaculo + "' and ID_recinto='" + id_recinto
				+ "' and Fecha='" + fecha + "'";
		try {
			String query = "select * from grada where ID_espectaculo='" + id_espectaculo + "' and ID_recinto='"
					+ id_recinto + "' and Fecha='" + fecha + "'";
			ResultSet rs = Log_in.stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("Non se pode eliminar o evento, elimine primeiro as suas gradas");
				return;
			}
			query = "select 1 from evento where ID_espectaculo='" + id_espectaculo + "' and ID_recinto='" + id_recinto
					+ "' and Fecha='" + fecha + "'";
			rs = Log_in.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("Non existe o evento");
				return;
			}
			Log_in.stmt.executeUpdate(update);
			System.out.println("Grada eliminada");
		} catch (SQLException e) {
			System.out.println("Procedese a eliminar o evento");
		}

		try {
			Log_in.stmt.executeUpdate(update);
			System.out.println("Evento eliminado");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void EliminarGrada() {
		System.out.println("Introduzca o ID da grada:");
		String id_grada = Log_in.teclado.next();
		System.out.println("Introduzca o ID do espectaculo:");
		String id_espectaculo = Log_in.teclado.next();
		System.out.println("Introduzca o ID do recinto:");
		String id_recinto = Log_in.teclado.next();
		String fecha = dameFecha();

		String update = "DELETE FROM grada WHERE ID_grada='" + id_grada + "' and ID_recinto='" + id_recinto
				+ "' and Fecha='" + fecha + "' and id_espectaculo='" + id_espectaculo + "'";

		try {
			String query = "select 1 from localidade where ID_grada='" + id_grada + "' and ID_recinto='" + id_recinto
					+ "' and Fecha='" + fecha + "' and id_espectaculo='" + id_espectaculo + "'";
			ResultSet rs = Log_in.stmt.executeQuery(query);
			if (rs.next()) {
				System.out.println("Non se pode eliminar a grada, ten localidades pendentes");
				return;
			}
			query = "select 1 from grada where id_grada='" + id_grada + "' and ID_recinto='" + id_recinto
					+ "' and Fecha='" + fecha + "' and id_espectaculo='" + id_espectaculo + "'";
			rs = Log_in.stmt.executeQuery(query);
			if (!rs.next()) {
				System.out.println("Non existe a grada indicada");
				return;
			}
			Log_in.stmt.executeUpdate(update);
			System.out.println("Grada eliminada");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void ListarAdmin() {
		System.out.println("Introduzca a tabla a consultar");
		String tabla = Log_in.teclado.next();
		System.out.println("Introduzca as columnas a consultar");
		String columnas = Log_in.teclado.next();
		System.out.println("Predicado (formato[PREDICADO/NO]):");
		String predicado = Log_in.teclado.next();

		if (predicado.length() > 5)
			predicado = " where " + predicado;
		else
			predicado = "";

		String query = "select " + columnas + " from " + tabla + predicado;

		try {
			ResultSet consulta = Log_in.stmt.executeQuery(query);
			ResultSetMetaData rsmd = consulta.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (consulta.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1)
						System.out.print(",  ");
					String columnValue = consulta.getString(i);
					System.out.print(columnValue);
				}
				System.out.println("");
			}
		} catch (SQLException e) {
			System.out.println("Error na consulta");
		}
	}

	public static String dameFecha() {
		System.out.println("Introduzca anho:");
		String anho = Log_in.teclado.next();
		System.out.println("Introduzca mes:");
		String mes = Log_in.teclado.next();
		System.out.println("Introduzca dia:");
		String dia = Log_in.teclado.next();
		System.out.println("Introduzca hora:");
		String hora = Log_in.teclado.next();
		System.out.println("Introduzca minuto:");
		String minuto = Log_in.teclado.next();
		System.out.println("Introduzca segundo:");
		String segundo = Log_in.teclado.next();

		String fecha = anho + "-" + mes + "-" + dia + " " + hora + ":" + minuto + ":" + segundo;
		return fecha;
	}
}