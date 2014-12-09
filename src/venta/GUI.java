package venta;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.MenuBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;


public class GUI extends JFrame{
	
	private final String[] titulos = {" Folio Cotizacion","Nombre Cliente", "Salón","Ciudad Evento", "Fecha"};
	private DefaultTableModel dtm = new DefaultTableModel();
	private JTable cotTable = new JTable (dtm){
		public boolean isCellEditable(int Row, int vColIndex){
			return false;
		}
	};
	private BDM bdm = new BDM();
	private JScrollPane scroll;
	private JLabel busqueda;
	private JTextField cliente;
	private JButton buscar;
	private JButton registVenta;
	private FrameInfoCot ficot;
	
	private List<Integer> ids = new ArrayList<Integer>();
	
	
	public GUI(){
		super ("Generar Venta");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(800, 550);
		this.setResizable(false);
		this.addComponents();
		this.setVisible(true);
	}
	
	public void busqCotizaciones(){
		dtm.setRowCount(0);
		dtm.setColumnCount(0);
		dtm.setColumnIdentifiers(titulos);
		ids.clear();
		try {
			ResultSet aux = null;
			if (cliente.getText()!=null && !cliente.getText().isEmpty()){
				//buscar cotizaciones por nombre de cliente
				aux = Cotizacion.buscarCotCli(cliente.getText(), bdm);
				while(aux.next()){
					//CHECAR ESTO (Te estoy hablando a ti! Gusano Elizondo!)
					//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
					//System.out.println(aux.getObject(4).toString());
					//System.out.println("PARSEADO: "+sdf.parse(aux.getObject(4).toString()));
					Object[] fila ={aux.getObject(1), aux.getObject(2).toString()+" "+ aux.getObject(3).toString()+" "+aux.getObject(4).toString(),aux.getObject(5),aux.getObject(6),aux.getObject(7).toString()};
					ids.add((Integer)aux.getObject(1));
					dtm.addRow(fila);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void addComponents(){
		
		cotTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//cotTable.setBounds(0, 0, 200, 100);
		//dtm.setColumnIdentifiers(titulos);
		//cotTable.setEnabled(false);
		JPanel superior = new JPanel(new FlowLayout());		
		busqueda = new JLabel("Nombre cliente/salon: ");
		cliente = new JTextField(23);
		cliente.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if (cliente.getText().length()==23)
					e.consume();
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
			}
		});
		buscar = new JButton("Buscar");
		buscar.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			busqCotizaciones();
			}
		});		
		
		registVenta = new JButton("Registrar Venta");
		//Llamo a el resumen de la cotizacion
		final GUI principal = this;
		registVenta.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int[] selecciones = cotTable.getSelectedRows();
				if (selecciones.length==1 ){
					int selectedObject = (Integer) cotTable.getModel().getValueAt(selecciones[0],0);
					ficot = new FrameInfoCot(selectedObject,principal);
				}
				else{
					JOptionPane.showMessageDialog(ficot,"Debe seleccionar una cotización");
				}
			}
		});
		JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER));				
		scroll = new JScrollPane(cotTable);
		scroll.setPreferredSize(new Dimension(700,400));
		
		//scroll.setBounds(100,50,0,0);
		superior.add(busqueda);
		superior.add(cliente);
		superior.add(buscar);
		this.add(superior,BorderLayout.PAGE_START);
		centro.add(scroll);
		this.add(centro, BorderLayout.CENTER);
		JPanel inferior = new JPanel(new FlowLayout());
		inferior.add(registVenta);
		this.add(inferior,BorderLayout.PAGE_END);		
	}
	public GUI getGUI(){
		return this;
	}

}