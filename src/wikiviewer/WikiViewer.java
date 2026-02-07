/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wikiviewer;

/**
 *
 * @author Pavlos Cosmides
 */


import GUI.MainGui;
import java.sql.SQLException;
import javax.swing.JFrame;



public class WikiViewer 
{
    public static void main(String[] args) 
    {
//        String search = "ΕΑΠ";
//        String urlToCall = "https://el.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + search + "&format=json";
//        Article[] articles = GetResults.getList(urlToCall);
//        Category category = new Category(1, "Γενικά");
//        articles[3].setCategory(category);
//        dbHandling.buildDB();
//        try {
//            
////            dbHandling.insertArticle(articles[3]);
//            System.out.println(dbHandling.getArticleByTitle("ΕΑΠ"));
//            
//        } catch (SQLException e) {
//            System.out.println("problem with insert");
//        }
//   
          JFrame a = new MainGui();
          a.setVisible(true);
    }

    
    
        
        
}