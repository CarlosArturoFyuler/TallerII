package venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.acl.LastOwnerException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
public class FrameInfoCot extends JFrame{
	private JLabel infoCot, cantAsistEv, cantAsist,  nomClient, nom, lugarEv, lugar,
	totalCot, total, fechaEv, fecha, tipoEv, tipo, anticipo, notasCotizacion, notas;
	private JButton registrar;
	@SuppressWarnings("unused")
	private String[] valores;
	private int idCotizacion;
	private BDM bdm;
	JFormattedTextField efDecimal;
	private GUI principalCot;
	
	public FrameInfoCot(int idCotizacion, GUI principalCot){
		super("Confirmar Datos");
		this.idCotizacion = idCotizacion;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout(10,10));
		this.setSize(400,500);
		this.setResizable(false);
		bdm = new BDM();
		this.obtDatos();
		this.principalCot=principalCot;
		this.setVisible(true);		
	}
	
	public void obtDatos(){
		cantAsist=null;
		nom=null;
		lugar=null;
		total=null;
		fecha=null;
		tipo=null;
		notas =null;
		
		JPanel rellenoI = new JPanel(new FlowLayout(0,20,0));
	    this.add(rellenoI, BorderLayout.WEST);
	    
	    JPanel rellenoD = new JPanel(new FlowLayout(0,10,0));
	    this.add(rellenoD, BorderLayout.EAST);
		
		JPanel centro = new JPanel();
		centro.setLayout(new GridLayout(8,2,20,20));		
		try {			
			ResultSet aux = null;
			aux=Cotizacion.buscarCot(idCotizacion, bdm); 
			while(aux.next()){
				 	Cotizacion cot = new Cotizacion((Integer)aux.getObject(1),aux.getObject(2).toString(),aux.getObject(3).toString(),
				 	aux.getObject(4).toString(),(Double)aux.getObject(5),aux.getObject(6).toString(),aux.getObject(7).toString());
				 	
					cantAsist= new JLabel(Integer.toString(cot.getCantAsistentes()));
					notas = new JLabel(cot.getNotasCot());
					nom = new JLabel(cot.getNombreCliente());
					lugar = new JLabel(cot.getLugar());
					total = new JLabel(Double.toString(cot.getTotalCotizacion()));
					fecha = new JLabel(cot.getFechaEvento());
					tipo= new JLabel(cot.getTipoEvento());		
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Por el momento no ha sido posible registrar la venta, por favor intente más tarde o comuníquese con el Administrador del sistema");
		}
		
		infoCot = new JLabel("Información de la cotización");
		infoCot.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(infoCot, BorderLayout.PAGE_START);		
		nomClient = new JLabel("Nombre del cliente:");
		centro.add(nomClient);
		centro.add(nom);
		tipoEv = new JLabel("Tipo de Evento:");
		centro.add(tipoEv);
		centro.add(tipo);
		lugarEv = new JLabel("Ciudad del Evento:");
		centro.add(lugarEv);
		centro.add(lugar);
		fechaEv = new JLabel("Fecha de Evento:");
		centro.add(fechaEv);
		centro.add(fecha);
		cantAsistEv = new JLabel("Cantidad de Asistentes:");
		centro.add(cantAsistEv);
		centro.add(cantAsist);
		totalCot = new JLabel("Monto total Cotizacion:");
		centro.add(totalCot);
		centro.add(total);
		notasCotizacion = new JLabel("Notas Cotizacion:");
		centro.add(notasCotizacion);
		centro.add(notas);
		
		anticipo = new JLabel("Anticipo:");
		centro.add(anticipo);
		/*montoant = new JTextField(10);
		this.add(montoant);*/
		/* JSpinner m_numberSpinner;
		    SpinnerNumberModel m_numberSpinnerModel;
		    Double current = new Double(5.50);
		    Double min = new Double(0.00);
		    Double max = new Double(1000.00);
		    Double step = new Double(0.50);
		    m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);
		    m_numberSpinner = new JSpinner(m_numberSpinnerModel);
		    this.add(m_numberSpinner);
		*/
		//  JFormattedTextField efFecha = new JFormattedTextField(new Date());
		
		efDecimal = new JFormattedTextField();
				 // Formato de visualización
		 NumberFormat dispFormat = NumberFormat.getCurrencyInstance();
				 // Formato de edición: inglés (separador decimal: el punto)
		 NumberFormat editFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
				 // Para la edición, no queremos separadores de millares
		 editFormat.setGroupingUsed(false);
				// Creamos los formateadores de números
		 NumberFormatter dnFormat = new NumberFormatter(dispFormat);
		 NumberFormatter enFormat = new NumberFormatter(editFormat);
				 // Creamos la factoría de formateadores especificando los
				 // formateadores por defecto, de visualización y de edición
		 DefaultFormatterFactory currFactory = new DefaultFormatterFactory(dnFormat, dnFormat, enFormat);
				 // El formateador de edición admite caracteres incorrectos
				 //enFormat.setAllowsInvalid(true);
				 // Asignamos la factoría al campo
		 efDecimal.setFormatterFactory(currFactory);
		 efDecimal.setColumns(10);
		 centro.add(efDecimal);
		 
		 JPanel fin = new JPanel(new FlowLayout());
		 this.add(fin, BorderLayout.PAGE_END);	 
		 
		 registrar = new JButton("Registrar");
		 registrar.setPreferredSize(new Dimension(120,30));
		 registrar.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){
				 regVenta();
			 }
		 });
		 this.add(centro, BorderLayout.CENTER);
		 fin.add(registrar);		 
	}
	
	private void regVenta(){
		if (!efDecimal.getText().isEmpty()){
			int rs = 0;
			Calendar currentDate = Calendar.getInstance(); //Get the current date
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
			String dateNow = formatter.format(currentDate.getTime());
			//System.out.println("Now the date is :=>  " + dateNow);
			Venta v = new Venta(dateNow,idCotizacion);
			//Registrar Venta:
			try {
				v.registrarVenta(v.getFecha(), v.getCotizacion(), bdm);
				v.cambiarEstatus(v.getCotizacion(), bdm);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Por el momento no ha sido posible registrar la venta, por favor intente más tarde o comuníquese con el Administrador del sistema");
			}
			//obtenemos el ultimos ID de las ventas para ponerlo en la llave foranea de pago
			try {
				ResultSet aux = null;
				 aux= bdm.getSt().executeQuery("SELECT max(idVenta) from venta");
				 while(aux.next()){
						rs =(Integer)aux.getObject(1);
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			//insertamos el abono en la BD			
			Pago pago = new Pago(rs,dateNow,Double.parseDouble(efDecimal.getValue().toString()));
			try {
				pago.regPago(pago.getIdVenta(), pago.getFechaPago(), pago.getMontoPago(), bdm);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Por el momento no ha sido posible registrar la venta, por favor intente más tarde o comuníquese con el Administrador del sistema");
			}
			JOptionPane.showMessageDialog(this, "Su venta ha quedado registrada en el sistema");
		this.dispose();
		principalCot.dispose();
		
		}else{
			JOptionPane.showMessageDialog(this,"Por favor, ingrese un monto en el campo Anticipo, e intente de nuevo");
		}
	}

}
