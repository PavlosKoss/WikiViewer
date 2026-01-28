/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikiviewer;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;


/**
 *
 * @author p.cosmides
 */
public class GetResolts 
{
   
    
    
    public static String setProperSnippet(String title){
        String snippet;
        String jsonResponse = CallURL(
                "https://el.wikipedia.org/w/api.php?format=json&action=query&"
                        + "exchars=950&formatversion=2&prop=extracts&exintro&"
                        + "explaintext&titles=" + title);
        JsonObject obj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray pages = obj.getAsJsonObject("query").getAsJsonArray("pages");
        JsonObject page = pages.get(0).getAsJsonObject();
        snippet = page.get("extract").getAsString();
        return snippet;
    }
    
    public static String CallURL(String urlToCall)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlToCall)
                .header("User-Agent", "WikiViewer/1.0")
                .build();
        try (Response response = client.newCall(request).execute()) 
        {
            if(response.isSuccessful() && response.body() != null)
            {
                String responseString = response.body().string();
                return responseString;
            }
        } catch (IOException e) 
        {
            return null;
        }
        return null;
    }
    
    public static Article[] getList(String urlToCall)
    {
        
        String jsonResponse = CallURL(urlToCall);
        Gson gson = new Gson();

        JsonObject odj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        Article[] articles = gson.fromJson(odj.getAsJsonObject("query").get("search"), Article[].class);
        for (Article a : articles)
        {
            a.setTitle(a.getTitle());
            a.setSnippet(setProperSnippet(a.getTitle()));
            a.setTimestamp(a.getTimestamp());

        }
        for (Article a : articles)
        {
            System.out.println("\n" + a);
        }
        return articles;
    }
}
    
