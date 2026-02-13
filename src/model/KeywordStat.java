package model;

/**
 * Η κλάση KeywordStat υλοποιεί τη διεπαφή {@link Statistic} και αναπαριστά 
 * τα στατιστικά στοιχεία των αναζητήσεων.
 * * Καταγράφει συγκεκριμένες λέξεις-κλειδιά που χρησιμοποιήθηκαν από τον χρήστη
 * και τη συχνότητα (πλήθος) εμφάνισής τους στο ιστορικό αναζητήσεων της βάσης δεδομένων.
 * @author PLH24Team Vasiliadou - Aggelopoulos - Kosmidis
 */
public class KeywordStat implements Statistic {
    
    /** Η λέξη-κλειδί ή ο όρος αναζήτησης. */
    private String name;
    
    /** Ο συνολικός αριθμός των φορών που πραγματοποιήθηκε αναζήτηση με αυτόν τον όρο. */
    private int count;
    
    /**
     * Κατασκευαστής για τη δημιουργία ενός αντικειμένου στατιστικών λέξης-κλειδιού.
     * @param name Η λέξη-κλειδί (π.χ. "Ελλάδα").
     * @param count Ο αριθμός των επαναλήψεων της συγκεκριμένης αναζήτησης.
     */
    public KeywordStat(String name, int count) {
        this.name = name;
        this.count = count;
    }

    /**
     * @return Τη λέξη-κλειδί ως String.
     */
    @Override
    public String getName() { return name; }
    
    /**
     * @return Τον αριθμό των φορών που αναζητήθηκε ο όρος.
     */
    @Override
    public int getCount() { return count; }   
}
