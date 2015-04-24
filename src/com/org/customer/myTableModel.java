package com.org.customer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class myTableModel 
{
	public void getTableModel(DefaultTableModel d, ResultSet rs) throws SQLException {
		d.setRowCount(0); //Sat�r say�s�n� s�f�rlar :)
		ResultSetMetaData metadata = (ResultSetMetaData) rs.getMetaData(); //RS metadatas� okunur.
        int columnsCount = metadata.getColumnCount(); //kolon say�s� bulunur.
        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i < columnsCount; i++) {
            columnNames.add(metadata.getColumnName(i)); //Kolon adlar� al�n�r.
        }
        d.setColumnIdentifiers(columnNames); //Tablo modeline kolon adlar� ayarlan�r.
        while (rs.next()) {
            Vector<Object> rowData = new Vector<>();
            for (int i = 1; i < columnsCount; i++) {
            	rowData.add(rs.getObject(i)); //Sat�rlar okunur
            }
            d.addRow(rowData); //Tablo modelinin i�i doldurulur.
        }
        
    }
}
