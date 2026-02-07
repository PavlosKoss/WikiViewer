/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikiviewer;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;


/**
 *
 * @author p.cosmides
 */
public class dbHandling {
    private static final String url = "jdbc:derby:wikidb;create=true";
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    
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
                "comments VARCHAR(550), " +
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
            LocalDateTime ldt = LocalDateTime.parse(article.getTimestamp(), formatter);
            pstmt.setTimestamp(3, Timestamp.valueOf(ldt));
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
    
    public static Article getArticleByTitle(String title) throws SQLException
    {       
        Article article = new Article();
        Category category = new Category();
        String sql = "SELECT p.id, p.title, p.snippet, p.timestamp, "
                + "p.stars, p.category_id, p.comment, c.cat_name "
                + "FROM article p LEFT JOIN category c "
                + "ON p.category_id = c.cat_id "
                + "WHERE p.title = ?";
        try(Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql) )
        {
            pstmt.setString(1, title);
            try (ResultSet rs = pstmt.executeQuery())
            {

                if (rs.next())
                {
                    
                    article.setTitle(rs.getString("title"));
                    article.setTimestamp(rs.getTimestamp("timestamp").toInstant().toString());
                    article.setSnippet(rs.getString("snippet"));
                    article.setComment(rs.getString("comment"));
                    article.setStars(rs.getInt("stars"));
                    category.setCatid(rs.getInt("category_id"));
                    category.setCategory(rs.getString("cat_name"));
                    article.setCategory(category);
                }
                 
            }               
        } catch(SQLException e)
        {
            System.out.println(e);
            return null;
        }
        return article;
        
        
    }
    
    
    public static List<Category> getAllCategory () throws SQLException
    {
        List<Category> categories = new ArrayList<>();
        String sql = "SELECT cat_id, cat_name FROM category ORDER BY "
                + "cat_name ASC";
        try(Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql))
        {
            while (rs.next())
            {
                Category cat = new Category();
                cat.setCatid(rs.getInt("cat_id"));
                cat.setCategory(rs.getString("cat_name"));
                
                categories.add(cat); 
            }
        } catch(SQLException e)
        {
            return null;
        }
        
        return categories;
    }
    
    
}
