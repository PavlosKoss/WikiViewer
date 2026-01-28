 
package wikiviewer;


import org.jsoup.Jsoup;
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
    private String category;
    private int stars;

    public Article(String ns, String title, String pageid, String size, String wordcount, String snippet, String timestamp) 
    {
        this.title = Jsoup.parse(title).text();
        this.snippet = Jsoup.parse(snippet).text();
        this.timestamp = Jsoup.parse(timestamp).text();
    }

    public Article(String title, String snippet, String timestamp, String category, int stars) {
        this.title = title;
        this.snippet = snippet;
        this.timestamp = timestamp;
        this.category = category;
        this.stars = stars;
    }

    public Article(int id, String title, String snippet, String timestamp, String category, int stars, int timesOfSearch) {
        this.id = id;
        this.title = title;
        this.snippet = snippet;
        this.timestamp = timestamp;
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

    public String getCategory() 
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
        this.timestamp = Jsoup.parse(timestamp).text();
    }

    public void setCategory(String category) 
    {
        this.category = category;
    }

    public void setStars(int stars) 
    {
        this.stars = stars;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString()
    {
        String string = String.format("Title: %s\nStippet: %s\nTimeStamp: %s ",
                title, snippet, timestamp);
        return string;
    }
}
    

