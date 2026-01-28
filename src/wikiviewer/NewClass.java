/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikiviewer;

/**
 *
 * @author p.cosmides
 */
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;

public class NewClass {
    public static void main(String[] args) {
        String jsonResponse = "https://el.wikipedia.org/w/api.php?format=json&action=query&exchars=950&formatversion=2&prop=extracts&exintro&explaintext&titles=Ελλάδα"; // Το JSON που πήρες από το API

        // 1. Μετατρέπουμε το String σε JsonObject
        JsonObject root = JsonParser.parseString(jsonResponse).getAsJsonObject();

        // 2. Μπαίνουμε στο query -> pages (που είναι Array λόγω formatversion=2)
        JsonArray pages = root.getAsJsonObject("query").getAsJsonArray("pages");

        // 3. Παίρνουμε το πρώτο αντικείμενο της λίστας
        if (pages.size() > 0) {
            JsonObject firstPage = pages.get(0).getAsJsonObject();

            // 4. Παίρνουμε τις τιμές ως String
            String title = firstPage.get("title").getAsString();
            String extract = firstPage.get("extract").getAsString();

            System.out.println("Τίτλος: " + title);
            System.out.println("Περίληψη: " + extract);
        }
    }
}
