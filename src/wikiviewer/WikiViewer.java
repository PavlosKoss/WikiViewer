/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package wikiviewer;

/**
 *
 * @author Pavlos Cosmides
 */




public class WikiViewer 
{
    public static void main(String[] args) 
    {
        String search = "ΕΑΠ";
        String urlToCall = "https://el.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + search + "&format=json";
        GetResults.getList(urlToCall);

    }
    
    
        
        
}