/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikiviewer;


import org.jsoup.Jsoup;
/**
 *
 * @author p.cosmides
 */
public class Article 
{
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

    @Override
    public String toString()
    {
        String string = String.format("Title: %s\nStippet: %s\nTimeStamp: %s ",
                title, snippet, timestamp);
        return string;
    }
}
    

