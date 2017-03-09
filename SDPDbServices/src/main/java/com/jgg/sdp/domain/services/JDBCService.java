package com.jgg.sdp.domain.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.jgg.sdp.domain.base.CFGCodigo;

public class JDBCService extends AbstractService<CFGCodigo> {

	protected Connection cnn = null;
	
	private ResultSet                rs   = null;
    private ResultSetMetaData        md   = null;
    private HashMap<String, Integer> cols = new HashMap<String, Integer>();
    
	public JDBCService() {
	}
	
	public int executeQuery(String sqlStmt) {
	   execute(sqlStmt);
	   try {
		 md = rs.getMetaData();
         cols.clear();
	     for (int i = 1; i <= md.getColumnCount(); i++ ) {
		      cols.put(md.getColumnName(i), i);
	     }
	   } catch (SQLException e) {
		// TODO Auto-generated catch block
		 e.printStackTrace();
	   }
       
	   return getCount();
	}
	
	public boolean next() {
		try {
			return rs.next();
		} catch (SQLException e) {
			return false;
		}
	}
	
	public void closeQuery() {
		try {
			rs.close();
		} catch (Exception e) {
			// Ignorar el error
		}
	}
	
	private void execute(final String sqlStmt) {
        Session session = em.unwrap(Session.class);
        session.doWork(new Work() {
             public void execute(Connection connection) throws SQLException {
                 Statement s = null;
                 try {
                    s = connection.createStatement();
                    rs = s.executeQuery(sqlStmt);
                 } 
                 finally {
//                   if (s != null) {
//                       s.close();
//                   }
                }
            }

      });
	}

	public int getCount() {
		int total = 0;
		try {
			int currRow = rs.getRow();
			rs.last();
            total = rs.getRow();
            rs.absolute(currRow);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return total;
	}

	public int getColumn(String name) {
	   Integer col = cols.get(name);
	   return (col == null) ? 0 : col;	
	}
	
	public Object getValue(String colName) {
		try {
			return rs.getObject(colName);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public Object getValue(int column) {
		
		DBField field = new DBField();
		try {
			field.setName(md.getColumnName(column));
			field.setSQLType(md.getColumnType(column));
			field.setSize(md.getColumnDisplaySize(column));
			field.setValue(rs.getObject(column));
			return field;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<String> getFirst() {
		try {
			System.out.println(rs.getMetaData());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<String>();
	}
}

