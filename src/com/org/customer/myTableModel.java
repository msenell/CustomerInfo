package com.org.customer;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class myTableModel 
{
	public void getTableModel(DefaultTableModel d, ResultSet rs) throws SQLException {
		d.setRowCount(0); //Satýr sayýsýný sýfýrlar :)
		ResultSetMetaData metadata = (ResultSetMetaData) rs.getMetaData(); //RS metadatasý okunur.
        int columnsCount = metadata.getColumnCount(); //kolon sayýsý bulunur.
        Vector<String> columnNames = new Vector<>();
        for (int i = 1; i < columnsCount; i++) {
            columnNames.add(metadata.getColumnName(i)); //Kolon adlarý alýnýr.
        }
        d.setColumnIdentifiers(columnNames); //Tablo modeline kolon adlarý ayarlanýr.
        while (rs.next()) {
            Vector<Object> rowData = new Vector<>();
            for (int i = 1; i < columnsCount; i++) {
            	rowData.add(rs.getObject(i)); //Satýrlar okunur
            }
            d.addRow(rowData); //Tablo modelinin içi doldurulur.
        }
        
    }
}
