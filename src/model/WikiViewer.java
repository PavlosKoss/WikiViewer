package model;

/**
 *
 * @author Pavlos Cosmides
 */


import GUI.MainGui;
import javax.swing.JFrame;


/**
 * Η κλάση WikiViewer αποτελεί την κεντρική κλάση εκκίνησης της εφαρμογής.
 * Περιέχει τη μέθοδο {@code main}, η οποία είναι υπεύθυνη για τη δημιουργία 
 * και την εμφάνιση του κύριου γραφικού περιβάλλοντος (MainGui).
 * @author PLH24Team Vasiliadou - Aggelopoulos - Kosmidis
 */
public class WikiViewer 
{
    public static void main(String[] args) 
    {
        // Δημιουργία του κεντρικού παραθύρου της εφαρμογής  
        JFrame a = new MainGui();
        
        // Εμφάνιση του παραθύρου στην οθόνη
        a.setVisible(true);
    }

    
    
        
        
}