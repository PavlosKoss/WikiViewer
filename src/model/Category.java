package model;

/**
 * Η κλάση Category αναπαριστά μια θεματική κατηγορία (π.χ. "Επιστήμη", "Τέχνη") 
 * στην οποία μπορεί να ταξινομηθεί ένα άρθρο.
 * * <p>Περιλαμβάνει μεθόδους για τη σύγκριση αντικειμένων βάσει του αναγνωριστικού τους,
 * διευκολύνοντας τη σωστή λειτουργία στοιχείων GUI όπως τα ComboBoxes.</p>
 * * @author PLH24Team Vasiliadou - Aggelopoulos - Kosmidis
 */
public class Category {
    
    /** Ο μοναδικός αναγνωριστικός αριθμός της κατηγορίας στη βάση δεδομένων. */
    private int catid;
    
    /** Το όνομα της κατηγορίας. */
    private String category;

    /**
     * Προκαθορισμένος κατασκευαστής. Δημιουργεί ένα κενό αντικείμενο κατηγορίας.
     */
    public Category() {
    }
    
    /**
     * Κατασκευαστής για πλήρη αρχικοποίηση κατηγορίας.
     * @param catid Το μοναδικό ID της κατηγορίας.
     * @param category Το όνομα της κατηγορίας.
     */
    public Category(int catid, String category) {
        this.catid = catid;
        this.category = category;
    }

    /**
     * Κατασκευαστής για δημιουργία κατηγορίας μόνο με το όνομα.
     * Χρησιμοποιείται συνήθως κατά τη δημιουργία νέων εγγραφών πριν την απόδοση ID από τη βάση.
     * @param category Το όνομα της κατηγορίας.
     */
    public Category(String category) {
        this.category = category;
    }

    /** @return Το ID της κατηγορίας. */
    public int getCatid() {
        return catid;
    }

    /** @return Το όνομα της κατηγορίας. */
    public String getCategory() {
        return category;
    }

    /** @param category Το νέο όνομα της κατηγορίας. */
    public void setCategory(String category) {
        this.category = category;
    }

    /** @param catid Το νέο ID της κατηγορίας. */
    public void setCatid(int catid) {
        this.catid = catid;
    }
    
    /**
     * Συγκρίνει το τρέχον αντικείμενο με ένα άλλο. Η σύγκριση γίνεται με βάση το 
     * πεδίο {@code catid}. Αυτό είναι απαραίτητο για τη σωστή επιλογή αντικειμένων
     * μέσα σε λίστες και ComboBoxes.
     * @param obj Το αντικείμενο προς σύγκριση.
     * @return true αν τα αντικείμενα έχουν το ίδιο ID, false σε κάθε άλλη περίπτωση.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Category other = (Category) obj;
        // Συγκρίνουμε με βάση το ID (αν έχουν ίδιο ID, είναι η ίδια κατηγορία)
        return this.catid == other.catid; 
    }
    
    /**
     * Επιστρέφει την αλφαριθμητική αναπαράσταση της κατηγορίας.
     * @return Το όνομα της κατηγορίας.
     */
    public String toString()
    {
        return String.format("%s", category);
    }
    
    
    
}
