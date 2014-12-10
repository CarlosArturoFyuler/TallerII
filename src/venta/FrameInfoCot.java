package venta;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.acl.LastOwnerException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

@SuppressWarnings("serial")
public class FrameInfoCot extends JFrame{
	private JLabel infoCot, nomClient, nom, lugarEv, lugar,
	totalCot, total, fechaEv, fecha, tipoEv, tipo, anticipo, notasCotizacion,
	lblcostoSalonTxt, lblcostoMusicaTxt, lblcostoOtrosTxt, lblSumaCostoTxt, lblSumaCosto,
	lblNombreSalonTxt, lblNombreSalon;
	private JButton registrar;
	private int idCotizacion;
	private BDM bdm;
	JFormattedTextField efDecimal,TFCostoSalon, TFCostoMusica, TFCostoOtros;
	private GUI principalCot;
	private Cotizacion cot;
	JTextArea tArea;
	
	public FrameInfoCot(int idCotizacion, GUI principalCot){
		super("Confirmar Datos");
		this.idCotizacion = idCotizacion;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout(10,10));
		this.setSize(500,600);
		//this.setResizable(false);
		bdm = new BDM();
		this.obtDatos();
		this.principalCot=principalCot;
		this.setVisible(true);		
	}
	
	public void obtDatos(){
		nom=null;
		lugar=null;
		total=null;
		fecha=null;
		tipo=null;
		TFCostoMusica=null;
		TFCostoOtros=null;
		TFCostoSalon=null;
		lblSumaCosto=null;
		lblNombreSalon=null;
		int sumaCosto=0;
		
		//ESPACIO VACÍO
		JPanel rellenoI = new JPanel(new FlowLayout(0,20,0));
	    this.add(rellenoI, BorderLayout.WEST);	    
	    JPanel rellenoD = new JPanel(new FlowLayout(0,20,0));
	    this.add(rellenoD, BorderLayout.EAST);
	    // ESPACIO VACÍO
		
		JPanel centro = new JPanel(new BorderLayout());
		
		JPanel centroSuperior = new JPanel(new GridLayout(6,2,0,15));		
		JPanel centroNotas = new JPanel(new GridLayout(1,2,0,0));
		JPanel centroInferior = new JPanel(new GridLayout(6,2,0,5));
		
		try {			
			ResultSet aux = null;
			aux=Cotizacion.buscarCot(idCotizacion, bdm); 
			while(aux.next()){
				 	cot = new Cotizacion(aux.getObject(1).toString(),aux.getObject(2).toString(),aux.getObject(3).toString(),(Float)aux.getObject(4),aux.getObject(5).toString(),aux.getObject(6).toString(),aux.getObject(7).toString());
				 	
					tArea = new JTextArea(cot.getNotasCot());
					nom = new JLabel(cot.getNombreCliente());
					lugar = new JLabel(cot.getLugar());
					total = new JLabel('$'+Float.toString(cot.getTotalCotizacion()));
					TFCostoSalon = new JFormattedTextField(new Float(0.0));
					 NumberFormat dispFormat = NumberFormat.getCurrencyInstance();
					 NumberFormat editFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
					 editFormat.setGroupingUsed(false);
					 NumberFormatter dnFormat = new NumberFormatter(dispFormat);
					 NumberFormatter enFormat = new NumberFormatter(editFormat);
					 DefaultFormatterFactory currFactory = new DefaultFormatterFactory(dnFormat, dnFormat, enFormat);		 
					 TFCostoSalon.setFormatterFactory(currFactory);		 
					 TFCostoSalon.setColumns(10);
					 TFCostoSalon.setPreferredSize(new Dimension(50,20));
					 TFCostoSalon.addKeyListener(new KeyListener() {
							@Override
							public void keyTyped(KeyEvent e) {
								if (TFCostoSalon.getText().length()==10)
									e.consume();
							}
							@Override
							public void keyReleased(KeyEvent arg0) {
							}
							
							@Override
							public void keyPressed(KeyEvent arg0) {
							}
						});
					 TFCostoMusica = new JFormattedTextField(new Float(0.0));		 
					 TFCostoMusica.setFormatterFactory(currFactory);		 
					 TFCostoMusica.setColumns(10);
					 TFCostoMusica.setPreferredSize(new Dimension(50,20));
					 TFCostoMusica.addKeyListener(new KeyListener() {
							@Override
							public void keyTyped(KeyEvent e) {
								if (TFCostoMusica.getText().length()==10)
									e.consume();
							}
							@Override
							public void keyReleased(KeyEvent arg0) {
							}
							
							@Override
							public void keyPressed(KeyEvent arg0) {
							}
						});
					 TFCostoOtros = new JFormattedTextField(new Float(0.0));		 
					 TFCostoOtros.setFormatterFactory(currFactory);		 
					 TFCostoOtros.setColumns(10);
					 TFCostoOtros.setPreferredSize(new Dimension(50,20));
					 TFCostoOtros.addKeyListener(new KeyListener() {
							@Override
							public void keyTyped(KeyEvent e) {
								if (TFCostoOtros.getText().length()==10)
									e.consume();
							}
							@Override
							public void keyReleased(KeyEvent arg0) {
							}
							
							@Override
							public void keyPressed(KeyEvent arg0) {
							}
						});
	
//					implementar cuando den click en calc total 
					lblSumaCosto = new JLabel(Float.toString(cot.obtenerTotal(new Float(0.0),new Float(0.0),new Float(0.0))));					
					lblNombreSalon = new JLabel(cot.getNombreSalon());	
					fecha = new JLabel(cot.getFechaEvento());
					tipo= new JLabel(cot.getTipoEvento());		
				}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Por el momento no ha sido posible registrar la venta, por favor intente más tarde o comuníquese con el Administrador del sistema");
		}
		
		infoCot = new JLabel("Información de la cotización");
		Font titulo = new Font("Arial", Font.BOLD, 14);
		infoCot.setFont(titulo);
		infoCot.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(infoCot, BorderLayout.PAGE_START);		
		
		// ENTRA AL PANEL CENTRAL
		nomClient = new JLabel("Nombre del cliente:");
		tipoEv = new JLabel("Tipo de Evento:");
		lugarEv = new JLabel("Ciudad del Evento:");
		fechaEv = new JLabel("Fecha de Evento:");
		lblNombreSalonTxt = new JLabel("Nombre del Salon: ");
		
		centroSuperior.add(nomClient);
		centroSuperior.add(nom);
		
		centroSuperior.add(tipoEv);
		centroSuperior.add(tipo);
		
		centroSuperior.add(lugarEv);
		centroSuperior.add(lugar);
		
		centroSuperior.add(fechaEv);
		centroSuperior.add(fecha);
		
		centroSuperior.add(lblNombreSalonTxt);
		centroSuperior.add(lblNombreSalon);
		
		centro.add(centroSuperior,BorderLayout.PAGE_START);
		
		notasCotizacion = new JLabel("Notas Cotizacion:");
		centroNotas.add(notasCotizacion);
		tArea.setEditable(false);			
	    tArea.setLineWrap(true);       // wrap line
	    tArea.setWrapStyleWord(true);  // wrap line at word boundary	    
	    tArea.setPreferredSize(new Dimension(300,100));
	    //Wrap the JTextArea inside a JScrollPane
	    JScrollPane tAreaScrollPane = new JScrollPane(tArea);
	    tAreaScrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    tAreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);		
		centroNotas.add(tAreaScrollPane);
				
		centro.add(centroNotas,BorderLayout.CENTER);
		
		totalCot = new JLabel("Costo Evento:");
		centroInferior.add(totalCot);
		centroInferior.add(total);
		lblcostoMusicaTxt = new JLabel("Costo Musica:");
		centroInferior.add(lblcostoMusicaTxt);
		centroInferior.add(TFCostoMusica);
		lblcostoSalonTxt = new JLabel("Costo Salon: ");
		centroInferior.add(lblcostoSalonTxt);
		centroInferior.add(TFCostoSalon);
		lblcostoOtrosTxt = new JLabel("Costo Otros: ");
		centroInferior.add(lblcostoOtrosTxt);
		centroInferior.add(TFCostoOtros);
		lblSumaCostoTxt = new JLabel("Total: ");		
		centroInferior.add(lblSumaCostoTxt);		
	    
		JPanel total = new JPanel(new FlowLayout(FlowLayout.LEADING));
		total.add(lblSumaCosto);
		JLabel vacio = new JLabel("    ");
		total.add(vacio);
		JButton calcular = new JButton("Calcular");
		calcular.addActionListener(new ActionListener() {
			
			
			public void actionPerformed(ActionEvent arg0) {
				Float total = cot.obtenerTotal(Float.parseFloat(TFCostoMusica.getValue().toString()), Float.parseFloat(TFCostoOtros.getValue().toString()), Float.parseFloat(TFCostoSalon.getValue().toString()));
				lblSumaCosto.setText(String.valueOf(total)); 	
			}
		});		
		
		total.add(calcular);
		centroInferior.add(total);	
		
	    anticipo = new JLabel("Anticipo:");
		centroInferior.add(anticipo);
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
		 efDecimal.setPreferredSize(new Dimension(50,20));
		 efDecimal.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					if (efDecimal.getText().length()==10)
						e.consume();
				}
				@Override
				public void keyReleased(KeyEvent arg0) {
				}
				
				@Override
				public void keyPressed(KeyEvent arg0) {
				}
			});
		 JPanel auxiliar = new JPanel(new FlowLayout(FlowLayout.LEADING));
		 auxiliar.add(efDecimal);		 
		 centroInferior.add(auxiliar);		 
		 
		 centro.add(centroInferior,BorderLayout.PAGE_END);	
		 
		 this.add(centro, BorderLayout.CENTER);
		 
		 JPanel fin = new JPanel(new FlowLayout());		 
		 registrar = new JButton("Registrar");
		 registrar.setPreferredSize(new Dimension(120,30));
		 registrar.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){
				 regVenta();
			 }
		 });		 
		 fin.add(registrar);
		 this.add(fin, BorderLayout.PAGE_END);
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
				v.registrarVenta(v.getFecha(), v.getCotizacion(), Float.parseFloat(TFCostoSalon.getValue().toString()),Float.parseFloat(TFCostoMusica.getValue().toString()),Float.parseFloat(TFCostoOtros.getValue().toString()),Float.parseFloat(lblSumaCosto.getText()),bdm);
				v.cambiarEstatus(v.getCotizacion(), bdm);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Por el momento no ha sido posible registrar la venta, por favor intente más tarde o comuníquese con el Administrador del sistema");
			}
			//obtenemos el ultimos ID de las ventas para ponerlo en la llave foranea de pago
			try {
				ResultSet aux = null;
				 aux= bdm.getSt().executeQuery("SELECT max(idventa) from venta");
				 while(aux.next()){
						rs =(Integer)aux.getObject(1);
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			//insertamos el abono en la BD			
			Pago pago = new Pago(rs,dateNow,Float.parseFloat(efDecimal.getValue().toString()));
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
