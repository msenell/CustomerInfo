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
	
	public Connection ConnectDb() //MusteriDb baglant� olusturma
    {
            try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, "ODBC Microsoft Access S�r�c�s� y�klenemedi!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:odbc:MusteriDb");
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Veritaban�na eri�ilemiyor!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "Veritaban�na eri�ilemiyor!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
        }
        try {
            st.executeQuery(sql);
        } catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "�stenen tabloya eri�ilemiyor!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
        	System.exit(0);
        }
        try {
            rs = st.getResultSet();
        } catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Tablodan veri okunamad�!", "DatabaseClass Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
        }
        return rs;
    }
	
	public boolean exeQuery(PreparedStatement pSTM) //Sorgu i�letme
	{
		try {
			pSTM.execute();
			return true;
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Sorgu i�letilemedi!", "Database Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return false;
		}
		
	}

	
}
