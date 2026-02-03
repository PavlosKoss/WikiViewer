/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikiviewer;


import java.sql.*;


/**
 *
 * @author p.cosmides
 */
public class dbHandling {
    private static final String url = "jdbc:DataBufferByte:wikidb;create=true";
    
    
    
    public static void buildDB()
    {
        try (Connection conn = DriverManager.getConnection(url))
        {
            if(conn != null)
            {
                createTables(conn);
            }
        }
        catch(SQLException e)
        {
            
        }
    }
    
    private static void createTables(Connection conn) throws SQLException
    {
        String createTableCategory = "CREATE TABLE category (" +
            "cat_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
            "cat_name VARCHAR(100) NOT NULL)";
        
        
        String createTableArticle = "CREATE TABLE article (" +
                "id INT PRIMARY KEY GENERATE ALWAYS AS IDENTITY, " +
                "title VARCHAR(100), " +
                "snippet CLOB, " +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                "stars INT, " +
                "category_id INT, " +
                "CONSTRAINT fk_category " +
                    "FOREIGN KEY (category_id) " +
                    "REFERENCES categories(cat_id))";
        
        try (var stmt = conn.createStatement())
        {
            stmt.execute(createTableArticle);
        }
        catch (SQLException e)
        {
            if (e.getSQLState().equals("X0Y32"))
            {
                
            }
            else
            {
                //παράθυρο με σφάλμα
            }
        }
        
        try (var stmt = conn.createStatement())
        {
            stmt.execute(createTableCategory);
        }
        catch (SQLException e)
        {
            if (e.getSQLState().equals("X0Y32"))
            {
                
            }
            else
            {
                //παράθυρο με σφάλμα
            }
        }
        
        
    }
    
    
    
    
    
}
