package venta;

import java.sql.ResultSet;
import java.sql.SQLException;


public class Cotizacion {
private String nombreCliente;
private int cantAsistentes;
private String notasCot;
private double totalCotizacion;
private String lugar;
private String fechaEvento;
private String tipoEvento;


	public Cotizacion(int cantAsistentes,String notasCot, String nombreCliente,String lugar, Double totalCotizacion,  
	String fechaEvento, String tipoEvento){
		this.nombreCliente=nombreCliente;
		this.cantAsistentes=cantAsistentes;
		this.notasCot=notasCot;
		this.totalCotizacion=totalCotizacion;
		this.lugar=lugar;
		this.fechaEvento=fechaEvento;
		this.tipoEvento=tipoEvento;
		
	}


	public String getNombreCliente() {
		return nombreCliente;
	}


	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}


	public int getCantAsistentes() {
		return cantAsistentes;
	}


	public void setCantAsistentes(int cantAsistentes) {
		this.cantAsistentes = cantAsistentes;
	}


	public String getNotasCot() {
		return notasCot;
	}


	public void setNotasCot(String notasCot) {
		this.notasCot = notasCot;
	}


	public double getTotalCotizacion() {
		return totalCotizacion;
	}


	public void setTotalCotizacion(double totalCotizacion) {
		this.totalCotizacion = totalCotizacion;
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
		return bdm.getSt().executeQuery("SELECT idCotizacion,nombreCliente,lugar,fechaEvento FROM cotizacion where nombreCliente like "+"'%"+nomClie+"%' AND estatusCotizacion =0");
	}
	public static ResultSet buscarCot(int idCot,BDM bdm) throws SQLException{
		return bdm.getSt().executeQuery("SELECT cantAsistentes,notasCotizacion,nombreCliente,lugar,totalCotizacion,fechaEvento,tipoEvento FROM cotizacion where idCotizacion ="+idCot);
	}
	public static ResultSet obtTotal(int idVenta,BDM bdm) throws SQLException{
		return bdm.getSt().executeQuery("SELECT totalCotizacion FROM cotizacion inner join venta on Venta.Cotizacion_idCotizacion=cotizacion.idCotizacion where idVenta ="+idVenta);
	}
}
