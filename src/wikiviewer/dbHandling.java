/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikiviewer;


import java.sql.*;
import java.time.Instant;


/**
 *
 * @author p.cosmides
 */
public class dbHandling {
    private static final String url = "jdbc:derby:wikidb;create=true";
    
    
    
    public static void buildDB()
    {
        try (Connection conn = DriverManager.getConnection(url))
        {
            if(conn != null)
            {
                System.out.println("the db is allready exist");
                createTables(conn);
                
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    private static void createTables(Connection conn) throws SQLException
    {
        String createTableCategory = "CREATE TABLE category (" +
            "cat_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
            "cat_name VARCHAR(100) NOT NULL)";
        
        
        String createTableArticle = "CREATE TABLE article (" +
                "id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, " +
                "title VARCHAR(255), " +
                "snippet CLOB, " +
                "timestamp TIMESTAMP, " +
                "stars INT CHECK (stars >=0 and stars <=5), " +
                "category_id INT, " +
                "CONSTRAINT fk_category " +
                    "FOREIGN KEY (category_id) " +
                    "REFERENCES category(cat_id))";
        
        try (var stmt = conn.createStatement())
        {
            stmt.execute(createTableArticle);
        }
        catch (SQLException e)
        {
            if (e.getSQLState().equals("X0Y32"))
            {
                System.out.println("the table allready exists");
            }
            else
            {
                System.out.println(e);
            }
        }
        
        try (Statement stmt = conn.createStatement())
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
                System.out.println(e);
            }
        }
        
        
    }
    
    public static boolean insertArticle(Article article) throws SQLException
    {
        String sql = "INSERT INTO article (title, snippet, timestamp, stars,"
                + "category_id) VALUES (?, ?, ?, ?, ?)";
        
        int affectedRows;
        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getSnippet());
            Instant instant = Instant.parse(article.getTimestamp());
            pstmt.setTimestamp(3, Timestamp.from(instant));
            pstmt.setInt(4, article.getStars());
            pstmt.setInt(5, article.getCategory().getCatid());
            affectedRows = pstmt.executeUpdate();
            return (affectedRows > 0);           
        } catch (SQLException e) 
        {
            System.out.println("in insert" + e);
            return false;
        }
    }
    
    
    
    
    
}
