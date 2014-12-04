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
	
	public void registrarVenta(String fecha, int idCotizacion, BDM bdm) throws SQLException{
		if(!this.fecha.isEmpty()&&this.cotizacion!=0){
			String query = "INSERT INTO venta (fechaVenta,Cotizacion_idCotizacion) VALUES ('"+fecha+"','"+idCotizacion+"')";
			bdm.getSt().executeUpdate(query);
		}
	}
	
	public static ResultSet buscarVentCli(String nomClie,BDM bdm) throws SQLException{
		return bdm.getSt().executeQuery("SELECT venta.idVenta, venta.fechaVenta, cotizacion.nombreCliente, cotizacion.totalCotizacion, cotizacion.tipoEvento, cotizacion.fechaEvento from venta inner join cotizacion on venta.Cotizacion_idCotizacion = cotizacion.idCotizacion where cotizacion.nombreCliente like "+"'%"+nomClie+"%'");
	}
	
	public void cambiarEstatus(int idCot, BDM bdm) throws SQLException{
		String query = "UPDATE cotizacion set estatusCotizacion=1 where idCotizacion ="+idCot;
		bdm.getSt().executeUpdate(query);
	}
	
}
