package venta;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class GUIControlPago extends JFrame {
	private final String[] titulos = {"idVenta","Fecha de la Venta","Evento", "NombreCliente", "Fecha Evento","Precio"};
	private DefaultTableModel dtm = new DefaultTableModel();
	private JTable ventasTable = new JTable (dtm){
		public boolean isCellEditable(int Row, int vColIndex){
			return false;
		}
	};
	private BDM bdm = new BDM();
	private JScrollPane scroll;
	private JLabel busqueda;
	private JTextField cliente;
	private JButton buscar;
	private JButton verPagos;
	private FrameInfoPag fipag;
	private List<Integer> ids = new ArrayList<Integer>();
	
	public GUIControlPago(){
		super ("Control de Pagos");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.setSize(510, 510);
		this.setResizable(false);
		this.addComponents();
		this.setVisible(true);
	}
	
	public void addComponents(){
		
		ventasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		busqueda = new JLabel("Nombre del cliente: ");
		cliente = new JTextField(23);
		buscar = new JButton("Buscar");
		buscar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			busqVentas();
			}
		});
		verPagos = new JButton("Ver Pagos");
		//Llamo a el resumen de la cotizacion
		verPagos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int[] selecciones = ventasTable.getSelectedRows();
				if (selecciones.length==1 ){
					int selectedObject = (Integer) ventasTable.getModel().getValueAt(selecciones[0],0);
					fipag = new FrameInfoPag(selectedObject);
				}
				else{
					JOptionPane.showMessageDialog(fipag,"Debe seleccionar una cotización");
				}
			}
		});
		scroll = new JScrollPane(ventasTable);
		scroll.setBounds(0,0,800,400);
		this.add(busqueda);
		this.add(cliente);
		this.add(buscar);
		this.add(scroll);
		this.add(verPagos);
		
	}
	
	public void busqVentas(){
		dtm.setRowCount(0);
		dtm.setColumnCount(0);
		dtm.setColumnIdentifiers(titulos);
		ids.clear();
		//ventasTable.getColumnModel().getColumn(2).setPreferredWidth(100);
		try {
			ResultSet aux = null;
			if (cliente.getText()!=null && !cliente.getText().isEmpty()){
				//buscar ventas por nombre de cliente
				aux = Venta.buscarVentCli(cliente.getText(), bdm);
				while(aux.next()){
					Object[] fila ={aux.getObject(1), aux.getObject(2), aux.getObject(5), aux.getObject(3), aux.getObject(6), aux.getObject(4)};
					ids.add((Integer)aux.getObject(1));		
					dtm.addRow(fila);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
}
