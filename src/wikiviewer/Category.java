/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package wikiviewer;

/**
 *
 * @author p.cosmides
 */
public class Category {
    private int catid;
    private String category;

    public Category() {
    }
    
    public Category(int catid, String category) {
        this.catid = catid;
        this.category = category;
    }

    public Category(String category) {
        this.category = category;
    }

    public int getCatid() {
        return catid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCatid(int catid) {
        this.catid = catid;
    }
    
    public String toString()
    {
        return String.format("%s", category);
    }
    
    
    
}
