/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author User
 */
public class OnHandGoods {
    private int id;
    private String name;
    private double price ;
    private double count;
    private String vahid;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public OnHandGoods(int id,String name, double price, double count,String vahid) {
        this.name = name;
        this.price = price;
        this.count = count;
        this.id = id;
        this.vahid = vahid;
    }

    public OnHandGoods() {
    }

    public String getVahid() {
        return vahid;
    }

    public void setVahid(String vahid) {
        this.vahid = vahid;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return name;
    }

 
    
}
