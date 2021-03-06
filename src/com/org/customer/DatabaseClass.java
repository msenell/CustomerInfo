package com.org.customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

public class DatabaseClass 
{
	
	public Connection ConnectDb() //MusteriDb baglantı olusturma
    {
            try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ODBC Microsoft Access Sürücüsü yüklenemedi!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:odbc:MusteriDb");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Veritabanına erişilemiyor!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
                return conn;
    }

	public ResultSet createResult(Connection conn, String sql) //ResultSet olusturma
    {
        ResultSet rs = null;
        
        Statement st = null;
        try {
            st = conn.createStatement();
        } catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Veritabanına erişilemiyor!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
        }
        try {
            st.executeQuery(sql);
        } catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "İstenen tabloya erişilemiyor!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
        }
        try {
            rs = st.getResultSet();
        } catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Tablodan veri okunamadı!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
        }
        return rs;
    }
	
	public boolean exeQuery(PreparedStatement pSTM) //Sorgu işletme
	{
		try {
			pSTM.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Sorgu işletilemedi!", "Database Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
	}

	
}
