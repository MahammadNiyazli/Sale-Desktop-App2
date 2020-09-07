/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

/**
 *
 * @author User
 */
public class SoldGoods {
    private int id ;
    private double price ;
    private double count;
    private String salesDate;
    private OnHandGoods good;
    private String vahid;

    public SoldGoods() {
    }

    public SoldGoods(int id, OnHandGoods good, double price, double count, String salesDate,String vahid) {
        this.id = id;
        this.good = good;
        this.price = price;
        this.count = count;
        this.salesDate = salesDate;
        this.vahid = vahid;
    }

    public String getVahid() {
        return vahid;
    }

    public void setVahid(String vahid) {
        this.vahid = vahid;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OnHandGoods getGood() {
        return good;
    }

    public void setGood(OnHandGoods good) {
        this.good = good;
    }

    

    public String getSalesDate() {
        return salesDate;
    }

    public void setSalesDate(String salesDate) {
        this.salesDate = salesDate;
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
        return "SoldGoods{" + "id=" + id + ", price=" + price + ", count=" + count + ", salesDate=" + salesDate + ", good=" + good + '}';
    }

  


    
    
    
}
