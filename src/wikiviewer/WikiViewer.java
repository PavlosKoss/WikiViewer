/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wikiviewer;

/**
 *
 * @author Pavlos
 */

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

import org.jsoup.Jsoup;


public class WikiViewer {
    public static void main(String[] args) {
        String search = "Ελλάδα";
        String urlToCall = "https://el.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + search + "&format=json";
        System.out.println(urlToCall);
        String jsonResponse = CallURL(urlToCall);
        Gson gson = new Gson();

        JsonObject odj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        Article[] articles = gson.fromJson(odj.getAsJsonObject("query").get("search"), Article[].class);
        for (Article a : articles){
            a.setTitle(a.getTitle());
            a.setSnippet(setProperSnippet(a.getTitle()));
            a.setTimestamp(a.getTimestamp());
            
        }
        for (Article a : articles){
            System.out.println("\n" + a);
        }

    }
    
    public static String setProperSnippet(String title){
        String snippet;
        String jsonResponse = CallURL(
                "https://el.wikipedia.org/w/api.php?format=json&action=query&"
                        + "exchars=950&formatversion=2&prop=extracts&exintro&"
                        + "explaintext&titles=" + title);
        System.out.println(jsonResponse);
        Gson gson = new Gson();
        JsonObject obj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray pages = obj.getAsJsonObject("query").getAsJsonArray("pages");
        JsonObject page = pages.get(0).getAsJsonObject();
        snippet = page.get("extract").getAsString();
        return snippet;
    }

    public static String CallURL(String urlToCall){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlToCall)
                .header("User-Agent", "WikiViewer/1.0")
                .build();
        try (Response response = client.newCall(request).execute()) {
            System.out.println(response);
            if(response.isSuccessful() && response.body() != null){
                String responseString = response.body().string();
                System.out.println(responseString);
                return responseString;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static class Article{
        
        private String title;
        private String snippet;
        private String timestamp;
        private String category;
        private int stars;

        public Article(String ns, String title, String pageid, String size, String wordcount, String snippet, String timestamp) {

            this.title = Jsoup.parse(title).text();
            this.snippet = Jsoup.parse(snippet).text();
            this.timestamp = Jsoup.parse(timestamp).text();
        }

        public String getTitle() {
            return title;
        }

        public String getSnippet() {
            return snippet;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public String getCategory() {
            return category;
        }

        public int getStars() {
            return stars;
        }

        public void setTitle(String title) {
            this.title = Jsoup.parse(title).text();
        }

        public void setSnippet(String snippet) {
            this.snippet = Jsoup.parse(snippet).text();
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = Jsoup.parse(timestamp).text();
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }
        
        @Override
        public String toString(){
            String string = String.format("Title: %s\nStippet: %s\nTimeStamp: %s ",
                    title, snippet, timestamp);
            return string;
        }
    }
}