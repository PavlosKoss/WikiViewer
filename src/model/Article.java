package model;

import org.jsoup.Jsoup;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.Instant;

/**
 * Η κλάση Article αναπαριστά ένα λήμμα της Wikipedia στο σύστημα.
 * Περιλαμβάνει πληροφορίες όπως ο τίτλος, το απόσπασμα κειμένου, 
 * η ημερομηνία τροποποίησης και στοιχεία αξιολόγησης από τον χρήστη.
 * * @author PLH24Team Vasiliadou - Aggelopoulos - Kosmidis
 */
public class Article 
{
    /** Ο μοναδικός αναγνωριστικός αριθμός του άρθρου στη βάση δεδομένων. */
    private int id;
    
    /** Ο τίτλος του άρθρου. */
    private String title;
    
    /** Ένα σύντομο απόσπασμα (snippet) από το περιεχόμενο του άρθρου. */
    private String snippet;
    
    /** Η χρονική σήμανση της τελευταίας επεξεργασίας του άρθρου. */
    private String timestamp;
    
    /** Προσωπικά σχόλια του χρήστη για το άρθρο. */
    private String comment = "";
    
    /** Η κατηγορία στην οποία ανήκει το άρθρο. */
    private Category category;
    
    /** Η βαθμολογία (αστέρια) που έχει δώσει ο χρήστης στο άρθρο. */
    private int stars;

    /**
     * Προκαθορισμένος κατασκευαστής (Default Constructor).
     * Δημιουργεί ένα κενό αντικείμενο Article.
     */
    public Article() 
    {
    }

    /**
     * Κατασκευαστής για τη δημιουργία άρθρου με βασικά στοιχεία.
     * Χρησιμοποιεί τη βιβλιοθήκη Jsoup για τον καθαρισμό τυχόν HTML tags.
     * * @param title Ο τίτλος του άρθρου (ενδέχεται να περιέχει HTML).
     * @param snippet Το απόσπασμα του άρθρου (ενδέχεται να περιέχει HTML).
     * @param timestamp Η χρονική σήμανση (ενδέχεται να περιέχει HTML).
     */
    public Article(String title, String snippet, String timestamp) 
    {
        this.title = Jsoup.parse(title).text();
        this.snippet = Jsoup.parse(snippet).text();
        this.timestamp = Jsoup.parse(timestamp).text();
    }

    /**
     * Κατασκευαστής για πλήρη δημιουργία άρθρου χωρίς το ID.
     * * @param title Ο τίτλος του άρθρου.
     * @param snippet Το απόσπασμα του άρθρου.
     * @param timestamp Η χρονική σήμανση.
     * @param comments Τα σχόλια του χρήστη.
     * @param category Η κατηγορία του άρθρου.
     * @param stars Η βαθμολογία του άρθρου.
     */
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
    
    
    /**
     * Πλήρης κατασκευαστής για τη δημιουργία άρθρου με όλα τα πεδία, 
     * συμπεριλαμβανομένου του ID.
     * @param id Το αναγνωριστικό του άρθρου.
     * @param title Ο τίτλος του άρθρου.
     * @param snippet Το απόσπασμα του άρθρου.
     * @param timestamp Η χρονική σήμανση.
     * @param comments Τα σχόλια του χρήστη.
     * @param category Η κατηγορία του άρθρου.
     * @param stars Η βαθμολογία του άρθρου.
     */
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

    /** @return Τον τίτλο του άρθρου. */
    public String getTitle() 
    {
        return title;
    }

    /** @return Το απόσπασμα του άρθρου. */
    public String getSnippet() 
    {
        return snippet;
    }

    /** @return Τη χρονική σήμανση του άρθρου. */
    public String getTimestamp() 
    {
        return timestamp;
    }

    /** @return Τα σχόλια του χρήστη. */
    public String getComment() 
    {
        return comment;
    }

    /** @return Την κατηγορία του άρθρου. */
    public Category getCategory() 
    {
        return category;
    }

    /** @return Τον αριθμό των αστεριών (βαθμολογία). */
    public int getStars() 
    {
        return stars;
    }

    /** @return Το ID του άρθρου. */
    public int getId() {
        return id;
    }

    /**
     * Θέτει τον τίτλο του άρθρου, αφαιρώντας τυχόν HTML tags.
     * @param title Ο νέος τίτλος.
     */
    public void setTitle(String title) 
    {
        this.title = Jsoup.parse(title).text();
    }

    /**
     * Θέτει το απόσπασμα του άρθρου, αφαιρώντας τυχόν HTML tags.
     * @param snippet Το νέο απόσπασμα.
     */
    public void setSnippet(String snippet) 
    {
        this.snippet = Jsoup.parse(snippet).text();
    }

    /**
     * Μετατρέπει μια χρονική σήμανση ISO (από τη Wikipedia) σε μορφή dd/MM/yyyy HH:mm:ss
     * χρησιμοποιώντας την τοπική ζώνη ώρας του συστήματος.
     * * @param timestamp Η χρονική σήμανση σε μορφή κειμένου (ISO Instant).
     */
    public void setTimestamp(String timestamp) 
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                                                       .withZone(ZoneId.systemDefault());
        Instant instant = Instant.parse(Jsoup.parse(timestamp).text());
        
        this.timestamp = formatter.format(instant);
    }

    /**
     * Θέτει το σχόλιο του χρήστη για το άρθρο.
     * @param comment Το περιεχόμενο του σχολίου.
     */
    public void setComment(String comment) 
    {
        this.comment = comment;
    }

    /**
     * Ορίζει την κατηγορία στην οποία ανήκει το άρθρο.
     * @param category Αντικείμενο τύπου Category.
     */
    public void setCategory(Category category) 
    {
        this.category = category;
    }

    /**
     * Θέτει τη βαθμολογία του άρθρου.
     * @param stars Αριθμός αστεριών.
     */
    public void setStars(int stars) 
    {
        this.stars = stars;
    }

    /**
     * Θέτει το μοναδικό αναγνωριστικό του άρθρου.
     * @param id Το ID από τη βάση δεδομένων.
     */
    public void setId(int id) 
    {
        this.id = id;
    }
    
    /**
     * Συγκρίνει το τρέχον αντικείμενο με ένα άλλο. Η σύγκριση γίνεται με βάση  
     * όλα τα πεδία. Χρησημοποιήτε για να καταλάβουμε αν έγινε κάποια αλλαγή στο
     * άρθρο στη φόρμα προεπισκόπησης.
     * @param obj Το αντικείμενο προς σύγκριση.
     * @return true αν τα αντικείμενα έχουν τα ίδια πεδία, false σε κάθε άλλη περίπτωση.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Article other = (Article) obj;
        // καλύπτουμε τις περιπτώσεις που η κατηγορία είναι null για να αποφή-
        // γουμε σφάλματα 
        if (other.category == null && this.category != null){
            return false;
        }
        if (this.category == null){
            return this.title.equals(other.title) && this.comment.equals(other.comment) 
                    && this.snippet.equals(other.snippet) && this.stars == other.stars;
        }else{
            return this.title.equals(other.title) && this.category.equals(other.category)
                    && this.comment.equals(other.comment) && this.snippet.equals(other.snippet)
                    && this.stars == other.stars;
        }
    }

    /**
     * Επιστρέφει μια συμβολοσειρά που αντιπροσωπεύει το αντικείμενο.
     * Χρήσιμο για την απεικόνιση του άρθρου σε λίστες (UI).
     * * @return Ο τίτλος του άρθρου.
     */
    @Override
    public String toString()
    {
        return title;
    }
}