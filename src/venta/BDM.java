package venta;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class BDM {

	private final static String drv = "com.mysql.jdbc.Driver";
	private final static String db = "jdbc:mysql://localhost:3306/taller2";
	private final static String user = "root";
	private final static String pass = "123456";
			
	private Connection ct;
	private Statement st;
	
	public BDM(){
		try {
			Class.forName(drv);
			ct = DriverManager.getConnection(db,user,pass);
			st = ct.createStatement();
			System.out.println("Conexion exitosa");
		} catch (Exception e) {
			System.out.println("No se ha podido conectar =/"+e.toString());
			JOptionPane.showMessageDialog(new JFrame(), "Por el momento no ha sido posible conectarse a la Base de Datos, contacte el administrador de sistema");
		}
	}
	
	public void showAll(){
		try {
			ResultSet rs = st.executeQuery("SELECT * FROM cotizacion");
			while (rs.next()){
				System.out.println(rs.getString(1)+"|"+rs.getString(2)+"|"+rs.getString(3)+"|"+rs.getString(4)+"|"+rs.getString(5)+"|"+rs.getString(6)+"|"+rs.getString(7)+"|"+rs.getString(8)+"|"+rs.getString(9)+"|"+rs.getString(10));
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error al realizar la consulta");
		}
	}
	
	public Statement getSt(){
		return st;
	}
}
