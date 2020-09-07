/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.inter;

import entity.OnHandGoods;
import entity.SoldGoods;
import java.sql.Date;
import java.util.List;

/**
 *
 * @author User
 */
public interface SoldGoodsDaoInter {
     
    public List<SoldGoods> getAllSoldGoods();
     
     public boolean removeSoldGoods(int id);
     
     public boolean updateSoldGoods(SoldGoods good);
     
     public boolean addSoldGoods(SoldGoods good);
     
     public List<SoldGoods> getSoldGoodsBySalesDate(String salesDate);
}
