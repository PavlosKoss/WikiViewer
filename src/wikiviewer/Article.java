 
package wikiviewer;

import org.jsoup.Jsoup;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.Instant;
/**
 *
 * @author p.cosmides
 * 
 */
public class Article 
{
    private int id;
    private String title;
    private String snippet;
    private String timestamp;
    private String comment;
    private Category category;
    private int stars;

    public Article() 
    {
    }

    public Article(String title, String snippet, String timestamp) 
    {
        this.title = Jsoup.parse(title).text();
        this.snippet = Jsoup.parse(snippet).text();
        this.timestamp = Jsoup.parse(timestamp).text();
    }

    public Article(String title, String snippet, String timestamp,
            String comments, Category category, int stars) 
    {
        this.title = title;
        this.snippet = snippet;
        this.timestamp = timestamp;
        this.comment = comments;
        this.category = category;
        this.stars = stars;
    }

    public Article(int id, String title, String snippet, String timestamp, 
            String comments, Category category, int stars) 
    {
        this.id = id;
        this.title = title;
        this.snippet = snippet;
        this.timestamp = timestamp;
        this.comment = comments;
        this.category = category;
        this.stars = stars;
    }
    
    

    public String getTitle() 
    {
        return title;
    }

    public String getSnippet() 
    {
        return snippet;
    }

    public String getTimestamp() 
    {
        return timestamp;
    }

    public String getComment() 
    {
        return comment;
    }

    public Category getCategory() 
    {
        return category;
    }

    public int getStars() 
    {
        return stars;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title) 
    {
        this.title = Jsoup.parse(title).text();
    }

    public void setSnippet(String snippet) 
    {
        this.snippet = Jsoup.parse(snippet).text();
    }

    public void setTimestamp(String timestamp) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                                               .withZone(ZoneId.systemDefault());
        Instant instant = Instant.parse(Jsoup.parse(timestamp).text());
        
        this.timestamp = formatter.format(instant);
        
    }

    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    public void setCategory(Category category) 
    {
        this.category = category;
    }

    public void setStars(int stars) 
    {
        this.stars = stars;
    }

    public void setId(int id) 
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
//        String string = String.format("""
//                                      Title: %s
//                                      Stippet: %s
//                                      Comment: %s
//                                      TimeStamp: %s
//                                      Stars: %s
//                                      Category: %s
//                                      """,
//                title, snippet, (comment!=null)?comment:"", timestamp, stars,
//                (category!=null)?category.toString():"");
        
        return title;
    }
}
    

