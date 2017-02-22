package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
	private static String mysqlAddr = "jdbc:mysql://mysql.stud.ntnu.no:3306/prodoteam_db?allowMultiQueries=true";
    private static String mysqlUser = "chrisnyv_demo";
    private static String mysqlPass = "rM48DmzH";
    
    
    public static boolean runQuery(String query, String... args){
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement(query);
            int i = 0;
            for(String arg : args)
                stmt.setString(++i, arg);
            return stmt.executeUpdate() != 0;
        }
        catch(SQLException e){
            return false;
        }
    }
    
    public static String loadSingleValue(String query, String... args){
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement(query);

            int i = 0;
            for(String arg : args)
                stmt.setString(++i, arg);

            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString(1);
            return null;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    public static String test(){
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM bruker");

            ResultSet rs = stmt.executeQuery();
            if(rs.next())
                return rs.getString(2);
            return null;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    public static String subjectsDB(String string){
        try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM course");

            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
            		System.out.println(rs.getString(2) + rs.getString(3));
            	}
            return null;
        }
        catch(SQLException e){
            return null;
        }
    }
    
    public static ObservableList<Course> courses(){
    	// Makes a new observable list
    	ObservableList<Course> courseList = FXCollections.observableArrayList();
    	try{
            Connection conn = DriverManager.getConnection(mysqlAddr, mysqlUser, mysqlPass);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM course");
            
            //
            ResultSet rs = stmt.executeQuery();
            
            // While the
            while(rs.next()){
            	ArrayList<String> course = new ArrayList<String>();
            	for(int i = 1; i < 4; i++){
            		course.add(rs.getString(i));
            	}courseList.add(new Course(course.get(0), course.get(1), course.get(2)));
            }
            return courseList;
        }
        catch(SQLException e){
        	System.out.println(e);
            return null;
        }	
    }
}
