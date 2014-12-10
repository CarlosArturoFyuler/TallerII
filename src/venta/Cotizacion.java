package venta;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Cotizacion {
private String nombreCliente;
private String notasCot;
private Float totalCotizacion;
private String nombreSalon;
private String lugar;
private String fechaEvento;
private String tipoEvento;


	public Cotizacion(String notasCot, String nombreCliente,String lugar,
	Float totalCotizacion, String nombreSalon,  
	String fechaEvento, String tipoEvento){
		this.nombreCliente=nombreCliente;
		this.notasCot=notasCot;
		this.totalCotizacion=totalCotizacion;
		this.nombreSalon=nombreSalon;
		this.lugar=lugar;
		this.fechaEvento=fechaEvento;
		this.tipoEvento=tipoEvento;
		
	}
	
	public Float obtenerTotal(Float costoMusicaCotizacion,Float costoSalonCotizacion, Float costoOtrosCotizacion){
		return this.totalCotizacion + costoMusicaCotizacion + costoSalonCotizacion + costoOtrosCotizacion;
	}
	public String getNombreCliente() {
		return nombreCliente;
	}



	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public String getNotasCot() {
		return notasCot;
	}



	public void setNotasCot(String notasCot) {
		this.notasCot = notasCot;
	}



	public Float getTotalCotizacion() {
		return totalCotizacion;
	}



	public void setTotalCotizacion(Float totalCotizacion) {
		this.totalCotizacion = totalCotizacion;
	}

	public String getNombreSalon() {
		return nombreSalon;
	}



	public void setNombreSalon(String nombreSalon) {
		this.nombreSalon = nombreSalon;
	}



	public String getLugar() {
		return lugar;
	}



	public void setLugar(String lugar) {
		this.lugar = lugar;
	}



	public String getFechaEvento() {
		return fechaEvento;
	}



	public void setFechaEvento(String fechaEvento) {
		this.fechaEvento = fechaEvento;
	}



	public String getTipoEvento() {
		return tipoEvento;
	}



	public void setTipoEvento(String tipoEvento) {
		this.tipoEvento = tipoEvento;
	}



	public static ResultSet buscarCotCli(String nomClie,BDM bdm) throws SQLException{
		return bdm.getSt().executeQuery("SELECT idcotizacion,Nombre,apPaterno,apMaterno,nombreinsumo,ciudadevento,fechaevento FROM cotizacion where (Nombre like "+"'%"+nomClie+"%' OR nombreinsumo like "+"'%"+nomClie+ "%' OR ApPaterno like "+"'%"+nomClie+ "%' OR ApMaterno like "+"'%"+nomClie+ "%') AND estado =0 ORDER BY idcotizacion DESC");
	}
	public static ResultSet buscarCot(int idCot,BDM bdm) throws SQLException{
		return bdm.getSt().executeQuery("SELECT observaciones,Nombre,ciudadevento,costototal,nombreinsumo,fechaevento,tipoevento FROM cotizacion where idCotizacion ="+idCot);
	}
	public static ResultSet obtTotal(int idVenta,BDM bdm) throws SQLException{
		return bdm.getSt().executeQuery("SELECT costototal FROM venta where idventa ="+idVenta);
	}
}
