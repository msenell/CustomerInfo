package com.org.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.ImageObserver;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.AttributedCharacterIterator;

import javax.swing.GroupLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class MainClass extends JFrame
{
       
    private String[] sendContent;
    private int sRow=0;
    
    private JPanel pnlTable;
    private JPanel pnlShow;
    private JPanel pnlControl;
    
    
    private JTable table;
    private JTextField[] textFields;
    private JLabel[] labels;
    private Box boxCtrls;
    private JButton[] btnCtrls;
    private JScrollPane scPane;
   
    public MainClass() 
	{
				
		createAllObject(); //Nesneleri yarat.
		
		customizeAllObject(); //Ayarlamalarý yap.
        
        addOnParentAll(); //Yerlerine ekle.
             
	}
	
	public static void main(String[] args) 
	{
		new MainClass();
	}

	//Deðiþken ve componentler oluþturuluyor.
	private void createAllObject()
	{
		//Deðiþkenler oluþturuluyor
		String strIco = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		ImageIcon[] icoCtrls;
		sendContent = new String[8];
		String[] strLabel = {"Adý Soyadý :", "Traktör Modeli :", "Þasi No :", "Motor No :",
				"KBE Tarihi :", "Adres :", "Telefon(1) :", "Telefon(2) :" };
		
		//Paneller oluþturuluyor
		pnlShow = new JPanel();
		pnlControl = new JPanel();
		pnlTable = new JPanel();
			scPane = new JScrollPane();
		
		//Diðer componentler oluþturuluyor.
			//Dizi elemanlarý daha sonra döngü ile oluþturulacak.
		textFields = new JTextField[8]; 
		labels = new JLabel[8];
		boxCtrls = Box.createHorizontalBox();
			btnCtrls = new JButton[7];
				icoCtrls = new ImageIcon[7];
		table = new JTable(new DefaultTableModel(){  //Tablo cellEditable özelliði 'false' 
			public boolean isCellEditable(int row, int column) //olacak þekilde oluþturuluyor.
		    {
				return false;
		    }
			});
		
		for(int i = 0; i<8; i++)
		{
			labels[i] = new JLabel(strLabel[i]);
			labels[i].setForeground(Color.yellow);
			labels[i].setFont(new Font("Courier New", Font.BOLD, 15));
			textFields[i] = new JTextField();
			textFields[i].setBackground(Color.blue);
			textFields[i].setForeground(Color.yellow);
			textFields[i].setFont(new Font(getName(), Font.BOLD, 12));
			sendContent[i] = new String();
			//Navigasyon butonlarýnýn ayarlarý yapýlýyor.
			if(i<7)
			{
				final int index = i;
				icoCtrls[i] = new ImageIcon(strIco + "Images\\" + i + ".png");
				btnCtrls[i] = new JButton();
				btnCtrls[i].setBorder(BorderFactory.createEmptyBorder());
				btnCtrls[i].setContentAreaFilled(false);
				btnCtrls[i].setIcon(icoCtrls[i]);
				btnCtrls[i].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						
						switch(index)
						{
						case 0: //ilk Kaydý seçen butona týklanmýþsa
							sRow = 0;
							selectARow();
							break;
						case 1: //Bir önceki kaydý seçen buton týklanmýþsa
							if(table.getSelectedRow() != 0)
							{
								sRow = table.getSelectedRow()-1;
								selectARow();	
							}
							break;
						case 2: //Düzenleme butonu týklanmýþsa
							addNewCustomer.mode=false;
							addNewCustomer.content=sendContent;
							sRow = table.getSelectedRow();
							btnEditClick();
							break;
						case 3: //Ekle butonu týklanmýþsa
							addNewCustomer.mode = true;
							btnAddClick();
							break;
						case 4: //Sil butonu týklanmýþsa
							btnDeleteClick();
							break;
						case 5: //Bir sonraki kaydý seçen buton týklanmýþsa
							if(table.getSelectedRow() != table.getRowCount()-1)
							{
								sRow = table.getSelectedRow()+1;
								selectARow();
							}
							break;
						case 6: //Son kaydý seçen buton týklanmýþsa
							sRow = table.getRowCount()-1;
							selectARow();
							break;
						}
						
					}
				});
				boxCtrls.add(btnCtrls[i]);
				boxCtrls.add(Box.createRigidArea(new Dimension(5,0))); //Butonlar arasý boþluk
			}
		}
	}

	//Deðiþken ve componentler özelleþtiriliyor.
	private void customizeAllObject()
	{
		String strIco = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();

		//Tablo birim seçme alaný satýr olarak ayarlandý.
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {    
            
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				fillTextFields(table.getSelectedRow());
			}    
        });  
		fillTable();
		sRow = 0;
		selectARow();
		setGroupLayout();
		
		pnlTable.setBackground(Color.blue);
		pnlControl.setBackground(Color.yellow);
		pnlShow.setBackground(Color.BLUE);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        pnlTable.setBorder(new EmptyBorder(5, 5, 5, 5));
        pnlTable.setBounds(0, 0, 100, 100);
        scPane.setBounds(0, 0, 0, 173);
        scPane.setViewportView(table);
        
		this.setBounds(200, 10, 740, 633);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setTitle("Customer Info");
        
	}
	
	//TextField ve Label grubu yerleþim planý
	private void setGroupLayout()
	{
		GroupLayout layout = new GroupLayout(pnlShow);
		pnlShow.setLayout(layout);
		
		GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
		
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(labels[0])
				.addComponent(labels[1])
				.addComponent(labels[2])
				.addComponent(labels[3]));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(textFields[0])
				.addComponent(textFields[1])
				.addComponent(textFields[2])
				.addComponent(textFields[3]));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(labels[4])
				.addComponent(labels[5])
				.addComponent(labels[6])
				.addComponent(labels[7]));
		hGroup.addGroup(layout.createParallelGroup()
				.addComponent(textFields[4])
				.addComponent(textFields[5])
				.addComponent(textFields[6])
				.addComponent(textFields[7]));
		layout.setHorizontalGroup(hGroup);
		
		GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
		vGroup.addGroup(layout.createParallelGroup()
				.addComponent(labels[0])
				.addComponent(textFields[0])
				.addComponent(labels[4])
				.addComponent(textFields[4]));
		vGroup.addGroup(layout.createParallelGroup()
				.addComponent(labels[1])
				.addComponent(textFields[1])
				.addComponent(labels[5])
				.addComponent(textFields[5]));
		vGroup.addGroup(layout.createParallelGroup()
				.addComponent(labels[2])
				.addComponent(textFields[2])
				.addComponent(labels[6])
				.addComponent(textFields[6]));
		vGroup.addGroup(layout.createParallelGroup()
				.addComponent(labels[3])
				.addComponent(textFields[3])
				.addComponent(labels[7])
				.addComponent(textFields[7]));
		layout.setVerticalGroup(vGroup);		
		
	}
	
	//Tüm componentler yerlerine ekleniyor.
	private void addOnParentAll()
	{
		pnlControl.add(boxCtrls);
        pnlTable.add(scPane);  
        
        this.add(pnlTable, BorderLayout.NORTH);
        this.add(pnlControl);
        this.add(pnlShow, BorderLayout.SOUTH);
	}

	//TextFields'larý seçili satýr ile doldur.
	private void fillTextFields(final int selectedRow)
	{
		if(table.getRowCount()>0)
		{
		addNewCustomer.siraNo = (int)table.getValueAt(selectedRow, 0);
		for(int i = 0; i<8; i++)
		{
			sendContent[i] = String.valueOf( table.getValueAt(selectedRow, i+1));
			textFields[i].setText(sendContent[i]);
		}
		}
	}
	
	//Düzenle butonu metodu
	private void btnEditClick()
	{
		addNewCustomer edit = new addNewCustomer();

		edit.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				fillTable();
				selectARow();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

	//Ekle butonu metodu
	private void btnAddClick()
	{
		addNewCustomer add = new addNewCustomer();

		add.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
				fillTable();
				sRow = table.getRowCount()-1;
				selectARow();
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
	//Sil butonu metodu
	private void btnDeleteClick()
	{
		int response = JOptionPane.showConfirmDialog(null, addNewCustomer.siraNo + " nolu müþteriyi silmek istediðinize emin misiniz?", "Are you Sure?", JOptionPane.YES_NO_OPTION);
		if(response == JOptionPane.YES_OPTION)
		{
			DatabaseClass dc = new DatabaseClass();
			Connection con = dc.ConnectDb();
			String sql = "DELETE FROM Customer WHERE SiraNo = ?";
			PreparedStatement pStm = null;
			try {
				pStm = con.prepareStatement(sql);
				pStm.setInt(1, addNewCustomer.siraNo);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(!dc.exeQuery(pStm))
			{
				JOptionPane.showMessageDialog(null, "Sorgu Ýþletilemedi!", "Database Error", JOptionPane.ERROR_MESSAGE);
			}
			else
				JOptionPane.showMessageDialog(null, addNewCustomer.siraNo + " Nolu Müþteri Silindi!", "Mission Completed", JOptionPane.INFORMATION_MESSAGE);
		}
		
	}

	//Tabloyu doldur
	private void fillTable()
	{
		Connection con = null;
	    ResultSet res = null;
		DatabaseClass dc = new DatabaseClass(); //Veritabaný iþlemlerini saðlayacak.
		myTableModel myTM = new myTableModel();
		con = dc.ConnectDb(); // Veritabanýna baðlantý yapma.
		res = dc.createResult(con, "SELECT * FROM Customer ORDER BY SiraNo");

		try {
			//Tablo dolduruluyor.
			DefaultTableModel d = (DefaultTableModel) table.getModel();
			myTM.getTableModel(d,res);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			res.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//Tablo üzerinde satýr seç.
	private void selectARow()
	{
		if(table.getRowCount() > sRow)
		{
	        table.setRowSelectionInterval(sRow, sRow);
	        fillTextFields(sRow);
	        table.scrollRectToVisible(new Rectangle(table.getCellRect(table.getSelectedRow(), table.getSelectedRow(), true)));
		}
	}
}
