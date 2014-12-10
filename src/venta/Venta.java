package venta;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Venta {
private String fecha;
private int cotizacion;

	public Venta(String fecha, int cotizacion){
		this.fecha=fecha;
		this.cotizacion=cotizacion;
		
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public int getCotizacion() {
		return cotizacion;
	}

	public void setCotizacion(int cotizacion) {
		this.cotizacion = cotizacion;
	}
	
	public void registrarVenta(String fecha, int idCotizacion,Float costoSalon,Float costomusica,Float costootros, Float costototal,BDM bdm) throws SQLException{
		if(!this.fecha.isEmpty()&&this.cotizacion!=0){
			String query = "INSERT INTO venta (fechaVenta,Cotizacion_idCotizacion,costosalon,costomusica,costootros,costototal) VALUES ('"+fecha+"',"+idCotizacion+","+costoSalon+","+costomusica+","+costootros+","+costototal+")";
			bdm.getSt().executeUpdate(query);
		}
	}
	
	public static ResultSet buscarVentCli(String nomClie,BDM bdm) throws SQLException{
		return bdm.getSt().executeQuery("SELECT venta.idventa, venta.fechaVenta,cotizacion.tipoevento, cotizacion.Nombre, cotizacion.ApPaterno,cotizacion.ApMaterno,cotizacion.fechaevento,venta.costototal, cotizacion.nombreinsumo from venta inner join cotizacion on venta.Cotizacion_idCotizacion = cotizacion.idcotizacion where (cotizacion.Nombre like "+"'%"+nomClie+"%' OR cotizacion.ApPaterno like "+"'%"+nomClie+ "%' OR cotizacion.ApMaterno like "+"'%"+nomClie+ "%')  ORDER BY idventa DESC");
	}
	
	public void cambiarEstatus(int idCot, BDM bdm) throws SQLException{
		String query = "UPDATE cotizacion set estado=1 where idCotizacion ="+idCot;
		bdm.getSt().executeUpdate(query);
	}
	
}
