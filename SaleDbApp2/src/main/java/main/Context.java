/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.impl.OnHandGoodsDaoImpl;
import dao.impl.SoldGoodsDaoImpl;
import dao.inter.OnHandGoodsDaoInter;
import dao.inter.SoldGoodsDaoInter;

/**
 *
 * @author User
 */
public class Context {
    public static OnHandGoodsDaoInter instanceOnHandGoods(){
        return new OnHandGoodsDaoImpl();
    }
    
    public static SoldGoodsDaoInter instanceSoldGoods(){
        return new SoldGoodsDaoImpl();
    }
}
