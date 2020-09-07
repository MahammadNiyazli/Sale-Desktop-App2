/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import entity.OnHandGoods;
import entity.SoldGoods;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import main.AbstractDAO;
import dao.inter.SoldGoodsDaoInter;

/**
 *
 * @author User
 */
public class SoldGoodsDaoImpl  extends AbstractDAO implements SoldGoodsDaoInter{

    public SoldGoods getSoldGoods(ResultSet rs){
         SoldGoods good = null;
        try {
            int id = rs.getInt("id");
            double price  = rs.getDouble("price");
            double count = rs.getDouble("count");
            String sales_date = rs.getString("sales_date");
             String vahid = rs.getString("vahid");
             String hg_vahid = rs.getString("hg_vahid");
            int hg_id = rs.getInt("hg_id");
            String hg_name = rs.getString("hg_name");
            double hg_price = rs.getDouble("hg_price");
            int hg_count = rs.getInt("hg_count");
           
            
            OnHandGoods onHandGood = new OnHandGoods(hg_id, hg_name, hg_price, hg_count,hg_vahid);
            
            good = new SoldGoods(id, onHandGood, price, count, sales_date,vahid);
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return good;
        
    }
    
    public List<SoldGoods> getSoldGoodsBySalesDate(String salesDate){
        List<SoldGoods> listGoods = new ArrayList<>();
      try(Connection c = connect()){
          PreparedStatement stmt = c.prepareStatement("select sg.*,hg.vahid as hg_vahid,hg.count as hg_count,hg.id as hg_id, "
                  + "hg.`name` as hg_name,hg.price as hg_price"
                  + " from sold_goods sg "
                  + " left join on_hand_goods hg"
                  + " on sg.good_id = hg.id where sales_date =?");
          stmt.setString(1, salesDate);
          stmt.execute();
          ResultSet rs = stmt.getResultSet();
          
          while(rs.next()){
             SoldGoods good = getSoldGoods(rs);
             listGoods.add(good);
          }
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return listGoods;
    }
    
    @Override
    public List<SoldGoods> getAllSoldGoods() {
      List<SoldGoods> listGoods = new ArrayList<>();
      try(Connection c = connect()){
          Statement stmt = c.createStatement();
          stmt.execute("select sg.*,hg.count as hg_count,hg.vahid as hg_vahid,hg.id as hg_id, hg.`name` as hg_name,hg.price as hg_price"
                  + " from sold_goods sg "
                  + "left join on_hand_goods hg"
                  + " on sg.good_id = hg.id ");
          ResultSet rs = stmt.getResultSet();
          
          while(rs.next()){
             SoldGoods good = getSoldGoods(rs);
             listGoods.add(good);
          }
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return listGoods;
    }

    @Override
    public boolean removeSoldGoods(int id) {
     try(Connection c = connect()){
          PreparedStatement stmt = c.prepareStatement("delete from sold_goods where id = ?");
          stmt.setInt(1, id);
          stmt.execute();
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return true;
    }

    @Override
    public boolean updateSoldGoods(SoldGoods good) {
    try(Connection c = connect()){
          PreparedStatement stmt = c.prepareStatement("update sold_goods set good_id = ? ,price=?,count=?,sales_date=?,vahid=? where id = ?");
          stmt.setInt(1,good.getGood().getId());
          stmt.setDouble(2,good.getPrice());
          stmt.setDouble(3,good.getCount());
          stmt.setString(4,good.getSalesDate());
          stmt.setString(5,good.getVahid());
          stmt.setInt(6,good.getId());
          stmt.execute();
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return true;
    }

    @Override
    public boolean addSoldGoods(SoldGoods good) {
      try(Connection c = connect()){
          PreparedStatement stmt = c.prepareStatement("insert into sold_goods(good_id,price,count,sales_date,vahid) values(?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
          stmt.setInt(1,good.getGood().getId());
          stmt.setDouble(2,good.getPrice());
          stmt.setDouble(3,good.getCount());
          stmt.setString(4,good.getSalesDate());
          stmt.setString(5,good.getVahid());
          stmt.execute();
          
           ResultSet generatedKeys = stmt.getGeneratedKeys();
          if(generatedKeys.next()){
              good.setId(generatedKeys.getInt(1));
          }
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return true;
    }
    
}
