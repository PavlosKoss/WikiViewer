package db;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Article;
import model.Category;
import model.CategoryStat;
import model.KeywordStat;
import model.Statistic;



/**
 * Η κλάση dbHandling διαχειρίζεται όλες τις λειτουργίες της βάσης δεδομένων (Apache Derby).
 * Περιλαμβάνει μεθόδους για τη δημιουργία πινάκων, την εισαγωγή, ενημέρωση και 
 * ανάκτηση άρθρων, κατηγοριών και στατιστικών στοιχείων.
 * @author PLH24Team Vasiliadou - Aggelopoulos - Kosmidis
 */
public class dbHandling {
    /** Η διεύθυνση σύνδεσης (JDBC URL) για τη βάση δεδομένων Derby. */
    private static final String url = "jdbc:derby:wikidb;create=true";
    private static final String[] categories = {
            "Άλλο", 
            "Επιστήμη", 
            "Ιστορία", 
            "Τέχνες", 
            "Αθλητισμός", 
            "Υγεία", 
            "Γεωγραφία", 
            "Πολιτική",
            "Οικονομία",
            "Τεχνολογία"
        };

    /** Formatter για τη μετατροπή των χρονικών σημάνσεων από String σε αντικείμενα LocalDateTime. */
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    
    /**
    * Αρχικοποιεί τη σύνδεση με τη βάση δεδομένων και καλεί τη δημιουργία των πινάκων.
     *
    */
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
            JOptionPane.showMessageDialog(null, "!!! Πρόβλημα με τo"
                    + " άνοιγμα της βάσης δεδομένων !!!" +e);
        }
    }
    
    /**
    * Δημιουργεί τους απαραίτητους πίνακες (article, category, keywords) στη βάση δεδομένων.
    * @param conn Η ενεργή σύνδεση με τη βάση δεδομένων.
    * @throws SQLException Σε περίπτωση αποτυχίας εκτέλεσης της SQL.
    */
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
                "comment VARCHAR(550), " +
                "stars INT CHECK (stars >=0 and stars <=5), " +
                "category_id INT, " +
                "CONSTRAINT fk_category " +
                    "FOREIGN KEY (category_id) " +
                    "REFERENCES category(cat_id))";


        String createTableKeywords = "CREATE TABLE keywords (" + 
                "key_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, "
                + "keywords VARCHAR(255))";
        
        // Εκτέλεση εντολών δημιουργίας με έλεγχο αν οι πίνακες υπάρχουν ήδη (SQL State X0Y32)
        try (Statement stmt = conn.createStatement())
        {
            stmt.execute(createTableCategory);
            insertCategories(categories);
            
        }
        catch (SQLException e)
        {
            if (e.getSQLState().equals("X0Y32"))
            {
                //Do Nothing
            }
            else
            {
                JOptionPane.showMessageDialog(null, "!!! Πρόβλημα με τη "
                    + "Δημιουργία του πίνακα Κατηγορίων !!!");
            }
        }
        
        try (Statement stmt = conn.createStatement())
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
                JOptionPane.showMessageDialog(null, "!!! Πρόβλημα με τη "
                    + "Δημιουργία του πίνακα Άρθρων !!!");
            }
        }
               
        try (Statement stmt = conn.createStatement())
        {
            stmt.execute(createTableKeywords);
        }
        catch (SQLException e)
        {
            if (e.getSQLState().equals("X0Y32"))
            {
                
            }
            else
            {
                JOptionPane.showMessageDialog(null, "!!! Πρόβλημα με τη "
                    + "Δημιουργία του πίνακα Keywords !!!");
            }
        }
        
        
        
    }
    /**
    * Εισάγει κατηγορίες στον πίνακα category αν δεν υπάρχουν
     * @param categories
    */
    public static void insertCategories(String[] categories) {
        // 1. Η λίστα με τις κατηγορίες που θέλουμε να βάλουμε
        

        String checkSql = "SELECT COUNT(*) FROM category WHERE cat_name = ?";
        String insertSql = "INSERT INTO category (cat_name) VALUES (?)";

        try (Connection conn = DriverManager.getConnection(url)) {

            // Για κάθε κατηγορία στη λίστα μας...
            for (String catName : categories) {

                // Ελεγχος υπάρχει ήδη (για να μην έχουμε διπλότυπα)
                try (PreparedStatement checkPs = conn.prepareStatement(checkSql)) 
                {
                    checkPs.setString(1, catName);
                    ResultSet rs = checkPs.executeQuery();
                    rs.next();
                    int count = rs.getInt(1);

                    if (count > 0) 
                    {
                        JOptionPane.showMessageDialog(null, "!!! Η κατηγορία "
                                + "\"" + catName + "\" υπάρχει ήδη.\" !!!");
                        continue; // Πάμε στην επόμενη
                    }
                }

                // Αν δεν υπάρχει, INSERT
                try (PreparedStatement insertPs = conn.prepareStatement(insertSql)) {
                    insertPs.setString(1, catName);
                    insertPs.executeUpdate();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "!!! \"Πρόβλημα με την εγγραφή της "
                                + "κατηγορίας στη βάση !!!");
        }
    }
    
    /**
    * Εισάγει ένα νέο άρθρο στη βάση δεδομένων.
    * @param article Το αντικείμενο {@link Article} προς αποθήκευση.
    * @return true αν η εισαγωγή ήταν επιτυχής, false σε αντίθετη περίπτωση.
    */
    public static boolean insertArticle(Article article)
    {
        String sql = "INSERT INTO article (title, snippet, timestamp, stars, "
                + "category_id, comment) VALUES (?, ?, ?, ?, ?, ?)";
        
        int affectedRows;
        try (Connection conn = DriverManager.getConnection(url); PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, article.getTitle());
            pstmt.setString(2, article.getSnippet());
            LocalDateTime ldt = LocalDateTime.parse(article.getTimestamp(), formatter);
            pstmt.setTimestamp(3, Timestamp.valueOf(ldt));
            pstmt.setInt(4, article.getStars());
            pstmt.setInt(5, article.getCategory().getCatid());
            pstmt.setString(6, article.getComment());
            affectedRows = pstmt.executeUpdate();
            return (affectedRows > 0);           
        } catch (SQLException e) 
        {
            System.out.println(e);
            return false;
        }
    }
    
    /**
    * Αναζητά ένα αποθηκευμένο άρθρο στη βάση δεδομένων με βάση τον τίτλο του.
    * @param title Ο τίτλος του άρθρου.
    * @return Ένα αντικείμενο {@link Article} αν βρεθεί, διαφορετικά ένα κενό αντικείμενο.
    */
    public static Article getArticleByTitle(String title)
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
        }
        return article;
        
        
    }
    
    /**
    * Επιστρέφει όλες τις διαθέσιμες κατηγορίες από τη βάση.
    * @return Μια λίστα από αντικείμενα {@link Category}.
    */
    public static List<Category> getAllCategory ()
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
        }
        
        return categories;
    }
    
    /**
     * Ανακτά όλα τα αποθηκευμένα άρθρα από τη βάση δεδομένων, πραγματοποιώντας 
     * σύνδεση (LEFT JOIN) με τον πίνακα των κατηγοριών.
     * @return Μια λίστα {@link List} από αντικείμενα {@link Article}. 
     * Επιστρέφει null σε περίπτωση σφάλματος SQL.
     * @throws SQLException Εάν προκύψει σφάλμα κατά την επικοινωνία με τη βάση.
     */
    public static List<Article> getArticles() throws SQLException
    {       
        List<Article> articles = new ArrayList<>();
        String sql = "SELECT p.id, p.title, p.snippet, p.timestamp, "
                + "p.stars, p.category_id, p.comment, c.cat_name "
                + "FROM article p LEFT JOIN category c "
                + "ON p.category_id = c.cat_id "
                + "ORDER BY p.title";
        try(Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery())
        {

            while (rs.next())
            {
                Article article = new Article();
                Category category = new Category();
                article.setTitle(rs.getString("title"));
                article.setTimestamp(rs.getTimestamp("timestamp").toInstant().toString());
                article.setSnippet(rs.getString("snippet"));
                article.setComment(rs.getString("comment"));
                article.setStars(rs.getInt("stars"));
                category.setCatid(rs.getInt("category_id"));
                category.setCategory(rs.getString("cat_name"));
                article.setCategory(category);
                
                articles.add(article);
          
            }
            
        } catch(SQLException e)
        {
        }
        return articles;      
    }
    
    /**
     * Ανακτά μια λίστα άρθρων που ανήκουν σε μια συγκεκριμένη κατηγορία.
     * Χρησιμοποιεί το ID της κατηγορίας για το φιλτράρισμα των αποτελεσμάτων.
     * @param category Το αντικείμενο {@link Category} βάσει του οποίου θα γίνει
     * το φιλτράρισμα.
     * @return Μια λίστα {@link List} με τα άρθρα της συγκεκριμένης κατηγορίας. 
     * Επιστρέφει null σε περίπτωση σφάλματος SQL.
     * @throws SQLException Εάν προκύψει σφάλμα κατά την εκτέλεση του ερωτήματος.
     */
    public static List<Article> getArticles(Category category) throws SQLException
    {       
        List<Article> articles = new ArrayList<>();
       
        String sql = "SELECT p.id, p.title, p.snippet, p.timestamp, "
                + "p.stars, p.category_id, p.comment, c.cat_name "
                + "FROM article p LEFT JOIN category c "
                + "ON p.category_id = c.cat_id "
                + "WHERE p.category_id =" + category.getCatid() 
                + "ORDER BY p.title";
        try(Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery())
        {

            while (rs.next())
            {
                Article article = new Article();
                article.setTitle(rs.getString("title"));
                article.setTimestamp(rs.getTimestamp("timestamp").toInstant().toString());
                article.setSnippet(rs.getString("snippet"));
                article.setComment(rs.getString("comment"));
                article.setStars(rs.getInt("stars"));
                category.setCatid(rs.getInt("category_id"));
                category.setCategory(rs.getString("cat_name"));
                article.setCategory(category);
                
                articles.add(article);
          
            }
        } catch(SQLException e)
        {
        }
        return articles;
    }   
    
    /**
     * Ενημερώνει τα στοιχεία ενός ήδη αποθηκευμένου άρθρου.
     * @param article Το άρθρο με τα ενημερωμένα πεδία.
     * @return true αν η ενημέρωση πέτυχε.
     * @throws SQLException Σε περίπτωση σφάλματος SQL.
     */
    public static boolean updateArticle(Article article) throws SQLException
    {
        String sql = "UPDATE article SET "
                + "snippet = ?, "  
                + "stars = ?, "
                + "comment = ?, "
                + "category_id = ? "
                + "WHERE title = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, article.getSnippet());
            pstmt.setInt(2, article.getStars());
            pstmt.setString(3, article.getComment());
            pstmt.setInt(4, article.getCategory().getCatid()); 
            pstmt.setString(5, article.getTitle());
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            return false;
        }
        return false;
    }
    
    /**
     * Παράγει στατιστικά στοιχεία για τον αριθμό των άρθρων ανά κατηγορία.
     * @return Μια λίστα με αντικείμενα Statistic (CategoryStat).
     */
    public static List<Statistic> getCategoryStats() {
    
        List<Statistic> statsList = new ArrayList<>();

        String sql = "SELECT c.cat_name, COUNT(a.id) as article_count " +
                     "FROM category c " +
                     "LEFT JOIN article a ON c.cat_id = a.category_id " +
                     "GROUP BY c.cat_id, c.cat_name " +
                     "ORDER BY article_count DESC";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String categoryName = rs.getString("cat_name");
                int count = rs.getInt("article_count");

                // Δημιουργία του αντικειμένου και προσθήκη στη λίστα
                statsList.add(new CategoryStat(categoryName, count));
            }

        } catch (SQLException e) {
        }

        return statsList;
    }
    
    /**
     * Παράγει στατιστικά στοιχεία για τη συχνότητα εμφάνισης λέξεων-κλειδιών στις αναζητήσεις.
     * @return Μια λίστα με αντικείμενα Statistic (KeywordStat).
     */
    public static List<Statistic> getKeywordStats() {
    
        List<Statistic> statsList = new ArrayList<>();

        String sql = "SELECT keywords, COUNT(*) as plithos "
                + "FROM keywords "
                + "GROUP BY keywords "
                + "ORDER BY plithos DESC";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String key_name = rs.getString("keywords");
                int count = rs.getInt("plithos");

                // Δημιουργία του αντικειμένου και προσθήκη στη λίστα
                statsList.add(new KeywordStat(key_name, count));
            }

        } catch (SQLException e) {
        }

        return statsList;
    } 
    
    
    /**
     * Αποθηκεύει μια νέα λέξη-κλειδί στον πίνακα ιστορικού αναζητήσεων.
     * @param keyword Η λέξη που αναζητήθηκε.
     * @return true αν η καταγραφή πέτυχε.
     */
    public static boolean insertKeyword(String keyword)
    {
        String sql = "INSERT INTO keywords (keywords) VALUES (?)";
        
        int affectedRows;
        try (Connection conn = DriverManager.getConnection(url); 
                PreparedStatement pstmt = conn.prepareStatement(sql))
        {
            pstmt.setString(1, keyword);
            affectedRows = pstmt.executeUpdate();
            return (affectedRows > 0);           
        } catch (SQLException e) 
        {
            //Option
            return false;
        }
    }
    
    public static List<Article> searchArticles(String keyword) {
        List<Article> results = new ArrayList<>();

        // SQL: Ψάξε όπου ο τίτλος ΜΟΙΑΖΕΙ με τη λέξη Ή το snippet ΜΟΙΑΖΕΙ με τη λέξη
        // Χρησιμοποιούμε LOWER() για να μην έχει σημασία αν είναι κεφαλαία ή μικρά
        String sql = "SELECT p.title, p.snippet, p.timestamp, "
                + "p.stars, p.category_id, p.comment, c.cat_name "
                + "FROM article p LEFT JOIN category c "
                + "ON p.category_id = c.cat_id "
                + "WHERE LOWER(p.title) LIKE ? OR LOWER(p.snippet) LIKE ? "
                + "OR LOWER(p.comment) LIKE ?"
                + "ORDER BY p.title DESC";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement ps = conn.prepareStatement(sql)) 
        {

            // Φτιάχνουμε το pattern αναζήτησης: %λέξη%
            String searchPattern = "%" + keyword.toLowerCase() + "%";

            // Βάζουμε την ίδια λέξη και στις τρεις θέσεις (1 = Title, 2 = Snippet, 
            // 3 = Comments)
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            ps.setString(3, searchPattern);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) 
            {
                // Εδώ πρέπει να φτιάξεις το αντικείμενο Article όπως το έχεις ορίσει
                // Προσοχή: Πρέπει να διαβάσεις όλα τα πεδία από τη βάση
                Article article = new Article();
                Category category = new Category();
                article.setTitle(rs.getString("title"));
                article.setTimestamp(rs.getTimestamp("timestamp").toInstant().toString());
                article.setSnippet(rs.getString("snippet"));
                article.setComment(rs.getString("comment"));
                article.setStars(rs.getInt("stars"));
                category.setCatid(rs.getInt("category_id"));
                category.setCategory(rs.getString("cat_name"));
                article.setCategory(category);


                results.add(article);
            }

        } catch (SQLException e) {
        }
    
        return results;
    }
}
 
