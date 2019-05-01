import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Log_in {

	static Scanner teclado;
	static Statement stmt;
	static Connection connection;
	// DATOS PARA O ACCESO A BASE DE DATOS
	private static final String usuario = "root";
	private static final String contrasinal = "1234";
	private static final String url = "jdbc:mysql://" + "localhost" + "/" + "taquilla" + "?serverTimezone=UTC";

	public static void main(String args[]) {
		// CONECTAMOS COA BASE DE DATOS
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, usuario, contrasinal);
			stmt = connection.createStatement();
		} catch (ClassNotFoundException ex) {
			System.out.println("Error ao rexistrar o driver de MySQL: " + ex);
		} catch (Exception e) {
			System.out.println("\n\t\tNon foi posible a conexión,probablemente este mal a pass do root\n\n");
			e.printStackTrace();
			System.exit(0);
		}

		// SOLICITAMOS O DNI E CON EL LOGEAMONOS NA BASE DE DATOS
		System.out.println("Escribe o teu DNI(Sen letra) para acceder a tua conta");
		teclado = new Scanner(System.in);
		String dni = teclado.next();
		String tipo_usuario = login(dni);

		if (tipo_usuario.equals("Administrador"))
			Admin.menuAdmin();
		else
			Cliente.menuCliente(dni);
	}

	public static String login(String dni) {
		String tipo_usuario = "";

		String query = "select nombre,apellidos,tipo_usuario from cliente where cliente.DNI=" + dni;
		try {
			ResultSet consulta = stmt.executeQuery(query);
			if (consulta.next()) {
				System.out.println("Benvido " + consulta.getString("nombre") + " " + consulta.getString("apellidos")
						+ ". Eres un usuario " + consulta.getString("tipo_usuario"));
				tipo_usuario = consulta.getString("tipo_usuario").trim();
			}
		} catch (SQLException e) {
			System.out.println("DNI incorrecto.");
			System.exit(0);
		}
		if(tipo_usuario.equals("")) {
			System.out.println("DNI incorrecto");
			System.exit(0);
		};
		return tipo_usuario;
	}

}