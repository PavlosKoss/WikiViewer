package api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import model.Article;

/**
 * Η κλάση GetResults παρέχει στατικές μεθόδους για την επικοινωνία με το MediaWiki API.
 * Αναλαμβάνει την εκτέλεση HTTP αιτημάτων, τη λήψη απαντήσεων σε μορφή JSON 
 * και τη μετατροπή τους σε αντικείμενα της κλάσης {@link Article}.
 * @author PLH24Team Vasiliadou - Aggelopoulos - Kosmidis
 */
public class GetResults 
{
    /**
     * Ανακτά ένα εκτενέστερο και καθαρό κείμενο (extract) για ένα συγκεκριμένο άρθρο,
     * καθώς η βασική αναζήτηση επιστρέφει περιορισμένα snippets.
     * @param title Ο τίτλος του άρθρου για το οποίο αναζητούμε το κείμενο.
     * @return Μια συμβολοσειρά (String) που περιέχει το πλήρες κείμενο της εισαγωγής του άρθρου.
     */
    public static String setProperSnippet(String title){
        String snippet;
        String jsonResponse = CallURL(
                "https://el.wikipedia.org/w/api.php?format=json&action=query&"
                        + "exchars=2000&formatversion=2&prop=extracts&exintro&"
                        + "explaintext&titles=" + title);
        
        JsonObject obj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        JsonArray pages = obj.getAsJsonObject("query").getAsJsonArray("pages");
        JsonObject page = pages.get(0).getAsJsonObject();
        snippet = page.get("extract").getAsString();
        
        return snippet;
    }
    
    /**
     * Πραγματοποιεί μια κλήση HTTP GET στην καθορισμένη διεύθυνση URL.
     * Χρησιμοποιεί τη βιβλιοθήκη OkHttp για τη διαχείριση της σύνδεσης.
     * @param urlToCall Η πλήρης διεύθυνση URL προς την οποία θα γίνει το αίτημα.
     * @return Το σώμα της απάντησης ως String αν η κλήση είναι επιτυχής, διαφορετικά null.
     */
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
                return response.body().string();
            }
        } catch (IOException e) 
        {
            // Σε περίπτωση σφάλματος δικτύου ή ανάγνωσης, επιστρέφει null
            return null;
        }
        return null;
    }
    
    /**
     * Αναζητά άρθρα στη Wikipedia με βάση έναν όρο αναζήτησης και επιστρέφει έναν πίνακα αντικειμένων Article.
     * Η μέθοδος επεξεργάζεται τα αποτελέσματα, καθαρίζει τους τίτλους και ανακτά βελτιωμένα αποσπάσματα κειμένου.
     * @param search Ο όρος αναζήτησης που εισάγει ο χρήστης.
     * @return Ένας πίνακας {@link Article} με τα αποτελέσματα της αναζήτησης.
     */
    public static Article[] getList(String search)
    {
        String urlToCall = "https://el.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + search + "&format=json";
        String jsonResponse = CallURL(urlToCall);
        Gson gson = new Gson();

        if (jsonResponse == null) return new Article[0];

        JsonObject odj = JsonParser.parseString(jsonResponse).getAsJsonObject();
        
        // Μετατροπή του JSON array "search" σε πίνακα αντικειμένων Article μέσω Gson
        Article[] articles = gson.fromJson(odj.getAsJsonObject("query").get("search"), Article[].class);
        
        // Εμπλουτισμός κάθε άρθρου με καθαρό περιεχόμενο και σωστή μορφή ημερομηνίας
        for (Article a : articles)
        {
            a.setTitle(a.getTitle()); 
            a.setSnippet(setProperSnippet(a.getTitle())); 
            a.setTimestamp(a.getTimestamp()); 
        }
        
        return articles;
    }
}
