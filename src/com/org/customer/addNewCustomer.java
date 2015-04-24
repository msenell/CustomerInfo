
package com.org.customer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class addNewCustomer extends JFrame
{
	static String[] content;
	static int siraNo;
	static boolean mode;
	
	private JPanel pnlShow;
	private JPanel pnlButton;
	private JTextField[] textFields;
	private JLabel[] labels;
	private Box btnBox;
	private JButton btnSave;
	private JButton btnCancel;
	
	addNewCustomer()
	{
		createAllObject(); //Nesneleri yarat.
		
		customizeAllObject(); //Ayarlamalarý yap.
        
        addOnParentAll(); //Yerlerine ekle.	
	}
	
	//Deðiþken ve componentler oluþturuluyor.
	private void createAllObject()
	{
		String[] strLabel = {"Adý Soyadý :", "Traktör Modeli :", "Þasi No :", "Motor No :",
				"KBE Tarihi :", "Adres :", "Telefon(1) :", "Telefon(2) :" };
		String strIco = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		
		ImageIcon icoSave = new ImageIcon(strIco + "Images\\save.png");
		ImageIcon icoCnc = new ImageIcon(strIco + "Images\\cancel.png");
		
		btnSave = new JButton(icoSave);
		btnCancel = new JButton(icoCnc);
		pnlButton = new JPanel();
		btnBox = Box.createHorizontalBox();
		pnlShow = new JPanel();
		textFields = new JTextField[8];
		labels = new JLabel[8];	
		
		for(int i = 0; i<8; i++)
		{
			labels[i] = new JLabel(strLabel[i]);
			labels[i].setForeground(Color.blue);
			textFields[i] = new JTextField();
			textFields[i].setBackground(Color.yellow);
			textFields[i].setForeground(Color.blue);
			textFields[i].setFont(new Font(getName(), Font.BOLD, 12));
			if(!mode)
				textFields[i].setText(content[i]);
		}
	}
	
	//Deðiþken ve componentler özelleþtiriliyor.
	private void customizeAllObject()
	{
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSaveClick();
				
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnCancelClick();
			}
		});
		
		pnlShow.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
		btnSave.setBorder(BorderFactory.createEmptyBorder());
		btnSave.setContentAreaFilled(false);
		btnCancel.setBorder(BorderFactory.createEmptyBorder());
		btnCancel.setContentAreaFilled(false);
		pnlShow.setBackground(Color.yellow);
		pnlButton.setBackground(Color.yellow);		
		setGroupLayout();
		this.setBounds(100, 100, 500,200);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setVisible(true);

	}

	//Tüm componentler yerlerine ekleniyor.
	private void addOnParentAll()
	{
		btnBox.add(btnSave);
		btnBox.add(Box.createRigidArea(new Dimension(5,0)));
		btnBox.add(btnCancel);
		pnlButton.add(btnBox, BorderLayout.EAST);
		
		this.add(pnlShow, BorderLayout.NORTH);
		this.add(pnlButton, BorderLayout.SOUTH);
	}
	
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
	
	private void btnSaveClick()
	{
		if(mode) //insert mode
		{
			_addNewCustomer();
		}
		else //update mode
		{
			updateCustomer();
		}
	}
	
	private void updateCustomer()
	{
		DatabaseClass dc = new DatabaseClass();
		Connection con = dc.ConnectDb();
		PreparedStatement pStm = null;
		String sql = "UPDATE Customer SET MusteriAdi = ?," +
										 " Model = ?," +
										 " SasiNo = ?," +
										 " MotorNo = ?," +
										 " KBE_TES_tarihi = ?," +
										 " Mevki_Adres = ?," +
										 " BirinciTelefon = ?," +
										 " ikinciTelefon = ?" +
										 " WHERE SiraNo = ?";
		try {
			pStm = con.prepareStatement(sql);
			for(int i = 0; i<8; i++)
				pStm.setString(i+1, textFields[i].getText());
			pStm.setInt(9, siraNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(!dc.exeQuery(pStm))
		{
			JOptionPane.showMessageDialog(null, "Sorgu Ýþletilemedi!", "Database Error", JOptionPane.ERROR_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(null, siraNo + " Numaralý Kayýt Güncellendi!", "Mission Completed", JOptionPane.INFORMATION_MESSAGE);
		this.dispose();

		
		try {
			pStm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void _addNewCustomer()
	{
		DatabaseClass dc = new DatabaseClass();
		Connection con = dc.ConnectDb();
		PreparedStatement pStm = null;
		String sql = "INSERT INTO Customer(MusteriAdi, Model, SasiNo, MotorNo, KBE_TES_tarihi, Mevki_Adres, BirinciTelefon, ikinciTelefon) " +
				"VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
		
		
			try {
				pStm = con.prepareStatement(sql);
				for(int i = 0; i<8; i++)
					pStm.setString(i+1, textFields[i].getText());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if(!dc.exeQuery(pStm))
		{
			JOptionPane.showMessageDialog(null, "Sorgu Ýþletilemedi!", "Database Error", JOptionPane.ERROR_MESSAGE);
		}
		else
			JOptionPane.showMessageDialog(null, textFields[0].getText() + " Adlý Müþteri Eklendi!", "Mission Completed", JOptionPane.INFORMATION_MESSAGE);
		this.dispose();

		
		try {
			pStm.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void btnCancelClick()
	{
		this.dispose();
	}
	
}
