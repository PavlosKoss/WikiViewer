package wikiviewer;

/**
 * Η κλάση CategoryStat υλοποιεί τη διεπαφή {@link Statistic} και χρησιμοποιείται
 * για την αναπαράσταση στατιστικών στοιχείων ανά κατηγορία άρθρων.
 * Αποθηκεύει το όνομα της κατηγορίας και τον συνολικό αριθμό των άρθρων που 
 * έχουν ταξινομηθεί σε αυτήν στη βάση δεδομένων.
 * @author PLH24Team Vasiliadou - Aggelopoulos - Kosmidis
 */
public class CategoryStat implements Statistic {
    
    /** Το όνομα της κατηγορίας. */
    private String name;
    
    /** Ο αριθμός των άρθρων που ανήκουν στην κατηγορία. */
    private int count;
    
    /**
     * Κατασκευαστής της κλάσης για τη δημιουργία ενός αντικειμένου στατιστικών κατηγορίας.
     * @param name Το όνομα της κατηγορίας (π.χ. "Επιστήμη").
     * @param count Το πλήθος των άρθρων που βρέθηκαν για αυτή την κατηγορία.
     */
    public CategoryStat(String name, int count) {
        this.name = name;
        this.count = count;
    }
    
    /**
     * @return Το όνομα της κατηγορίας.
     */
    @Override
    public String getName() { return name; }
    
    /**
     * @return Το πλήθος των άρθρων.
     */
    @Override
    public int getCount() { return count; }
}
