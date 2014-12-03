package venta;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

public class FrameInfoPag extends JFrame{
	private JLabel lblSumaTotTxt, lblSumaTotRs, lblTotalRestTxt, lblTotRestRs, lblabonoNuevo;
	private JButton regAbono;
	private int idVenta;
	private BDM bdm;
	JFormattedTextField abono;
	private final String[] titulos = {"No. Pago","Monto","Fecha"};
	private DefaultTableModel dtm = new DefaultTableModel();
	private JTable pagosTable = new JTable (dtm){
		public boolean isCellEditable(int Row, int vColIndex){
			return false;
		}
	};
	private JScrollPane scroll;
	private List<Integer> ids = new ArrayList<Integer>();
	private double totalCot;
	private double sumaPagos;
	
	public FrameInfoPag(int idVenta){
		super("Historial Ventas");
		this.idVenta = idVenta;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(490,550);
		bdm = new BDM();
		this.obtDatos();
		this.setVisible(true);
	}
	
	public void obtDatos(){
		totalCot=0;
		sumaPagos=0;
		ResultSet aux2 = null;
		lblabonoNuevo=null;
		lblSumaTotRs=null;
		lblSumaTotTxt=null;
		lblTotalRestTxt=null;
		lblTotRestRs=null;
		regAbono=null;
		abono=null;
		scroll=null;
		try {
			aux2=((Cotizacion.obtTotal(idVenta, bdm)));
			while(aux2.next()){
				totalCot=(Double)aux2.getObject(1);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		//lleno la tabla de pagos
		pagosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dtm.setRowCount(0);
		dtm.setColumnCount(0);
		dtm.setColumnIdentifiers(titulos);
		ids.clear();
		try {
			ResultSet aux = null;
			//obtener pagos de una venta
				aux = Pago.obtPagos(idVenta, bdm);
				int numPag=1;
				while(aux.next()){
					Object[] fila ={numPag++, aux.getObject(3), aux.getObject(2)};
					sumaPagos=sumaPagos+(Double)aux.getObject(3);
					ids.add((Integer)aux.getObject(1));
					dtm.addRow(fila);
				}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(sumaPagos);
		regAbono = new JButton("Registrar Abono");
		//Registro nuevo abono a la BD
		regAbono.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Registrar el abono 
				if (!abono.getText().isEmpty()){
					regAbono();
				}
			}
		});
		scroll = new JScrollPane(pagosTable);
		scroll.setBounds(0,0,800,400);
		lblSumaTotTxt = new JLabel("Suma Total: ");
		lblSumaTotRs = new JLabel(String.valueOf(sumaPagos));
		lblTotalRestTxt = new JLabel("Total Restante: ");
		double totRest = totalCot-sumaPagos;
		lblTotRestRs = new JLabel(String.valueOf(totRest));
		
		lblabonoNuevo = new JLabel("Abono Nuevo:");
		abono = new JFormattedTextField(new Double(0));
		NumberFormat dispFormat = NumberFormat.getCurrencyInstance();
		NumberFormat editFormat = NumberFormat.getNumberInstance(Locale.ENGLISH);
		editFormat.setGroupingUsed(false);
		//nuevo
		dispFormat.setMaximumIntegerDigits(10);
		editFormat.setMaximumIntegerDigits(10);
		NumberFormatter dnFormat = new NumberFormatter(dispFormat);
		NumberFormatter enFormat = new NumberFormatter(editFormat);
		DefaultFormatterFactory currFactory = new DefaultFormatterFactory(dnFormat, dnFormat, enFormat);
		abono.setFormatterFactory(currFactory);
		abono.setColumns(10);
		//solo presionara 10 caracteres a lo mas
		abono.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (abono.getText().length()==10)
					e.consume();
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		this.add(scroll,BorderLayout.PAGE_START);
		this.add(regAbono,BorderLayout.PAGE_END);
		JPanel pan = new JPanel(new FlowLayout());
		pan.add(lblSumaTotTxt);
		pan.add(lblSumaTotRs);
		pan.add(lblTotalRestTxt);
		pan.add(lblTotRestRs);
		pan.add(lblabonoNuevo);
		pan.add(abono);
		this.add(pan,BorderLayout.CENTER);	
	}
	
	public void regAbono(){
		Calendar currentDate = Calendar.getInstance(); //Get the current date
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); //format it as per your requirement
		String dateNow = formatter.format(currentDate.getTime());
		Pago pago = new Pago(idVenta,dateNow,Double.parseDouble(abono.getValue().toString()));
		try {
			pago.regPago(pago.getIdVenta(), pago.getFechaPago(), pago.getMontoPago(), bdm);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Por el momento no ha sido posible registrar la venta, por favor intente m�s tarde o comun�quese con el Administrador del sistema");
		}
		this.obtDatos();
	}

}
