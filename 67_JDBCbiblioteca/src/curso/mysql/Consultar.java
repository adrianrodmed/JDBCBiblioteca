package curso.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Consultar {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biblioteca", "root", ""); /*Creamos la conexion*/
		/*CREATE STATEMENT*/
		Statement st = conn.createStatement(); /*Creamos la consulta*/
		ResultSet rs = st.executeQuery("SELECT * FROM libros"); /*Pedimos la consulta y se guarda*/
		
		while(rs.next()) {/*Para que nos pinte los datos*/
			System.out.println("El título del libro es: " + rs.getString("titulo"));
			System.out.println("El precio del libro es: " + rs.getFloat("precio") + "€");
			System.out.println("La fecha de publicación del libro es: " + rs.getDate("fechadepublicacion"));
			System.out.println("--------------------------------------------------------");
		}
		/*PARA INSERTAR*/
		int insertada = st.executeUpdate("INSERT INTO libros(titulo, autor, precio, fechadepublicacion) values('El Quijote', 'Miguel de Cervantes', 10.25, '1605/01/01')");/*Para insertar datos en la base de datos*/
		System.out.println("");
		System.out.println("*********** Fila insertada " + insertada + " ***********");
		System.out.println("");
		/*PREPARED STATEMENT*/
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM libros WHERE titulo = ?");/**/
		pstmt.setString(1, "El Quijote");
		ResultSet rs1 = pstmt.executeQuery();
		
		while(rs1.next()) {
			System.out.println("El título del libro es: " + rs1.getString("titulo"));
			System.out.println("El precio del libro es: " + rs1.getFloat("precio") + "€");
			System.out.println("La fecha de publicación del libro es: " + rs1.getDate("fechadepublicacion"));
			System.out.println("--------------------------------------------------------");
		}
		
		/*CALLABLE STATEMENT*/
		System.out.println("");
		System.out.println("************** Procedure **************");
		System.out.println("");
		CallableStatement cstmt = conn.prepareCall("CALL listalibrosporautor(?)");
		cstmt.setString(1, "Miguel de Cervantes");
		ResultSet rs2 = cstmt.executeQuery();
		
		while(rs2.next()) {
			System.out.println("El título del libro es: " + rs2.getString("titulo"));
			System.out.println("El precio del libro es: " + rs2.getFloat("precio") + "€");
			System.out.println("La fecha de publicación del libro es: " + rs2.getDate("fechadepublicacion"));
			System.out.println("--------------------------------------------------------");
		}
	}

}
