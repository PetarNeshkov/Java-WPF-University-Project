/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CarFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Connect {

    public Connection conn;
    public Statement stmt;
    public ResultSet rs;

    public Connect() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:CarFactory.db");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> select(String[] columns, String table) {
        ArrayList<String> data = new ArrayList<>();

        String columnsString = String.join(", ", columns);
        String sql = "SELECT " + columnsString + " FROM " + table;

        System.out.println(sql);

        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String row = "";
                for (int i = 0; i < columns.length; i++) {
                    row += rs.getString(columns[i]) + "---";
                }
                data.add(row);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    
    public ArrayList<String> selectWhere(String[] columnsArray,String table,int[]colIndex, String[] whereValue){
        ArrayList<String> data = new ArrayList<String>();
        String columnsString = String.join(", ", columnsArray);
        String sql = "SELECT " + columnsString + " FROM " + table + " WHERE ";
            for (int i = 0; i < colIndex.length; i++) {
                sql += columnsArray[colIndex[i]] + " LIKE '%" + whereValue[i] + "%' OR ";
            }
        sql = sql.substring(0, sql.length() - 4);
        System.out.println(sql);
        
        try{
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while(rs.next()){
                String row = "";
                for (int i = 0; i < columnsArray.length; i++) {
                    row += rs.getString(columnsArray[i]) + "---";
                }
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public void insert(String[] columnsArray, String table, String[] values) {
        String columnsString = String.join("', '", columnsArray);
        String valuesString = String.join("', '", values);

        String sql = "INSERT INTO " + table + "('" + columnsString + "') VALUES('" + valuesString + "')";
        System.out.println(sql);
        try {
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void update(String[] columnsArray,String table,String[] valuesArray, String whereColumn, String whereValue){
        String sql = "UPDATE " + table + " SET ";
        for (int i = 0; i < columnsArray.length; i++) {
            sql += columnsArray[i] + " = " + (valuesArray[i].equals("") ? null : "'"+valuesArray[i]+"'") + ", ";
        }
        sql = sql.substring(0, sql.length() - 2);
        sql += " WHERE " + whereColumn + " = " + whereValue;
        System.out.println(sql);
        try{
            stmt = conn.createStatement();
            stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
    }

    public void delete(String column, int Id, String table) {
        String sql="delete from " + table + " where " + column + " = " + Id;

        System.out.println(sql);
        try {
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
