/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.impl;

import entity.OnHandGoods;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.AbstractDAO;
import dao.inter.OnHandGoodsDaoInter;
/**
 *
 * @author User
 */
public class OnHandGoodsDaoImpl extends AbstractDAO implements OnHandGoodsDaoInter  {
    
    public OnHandGoods getOnHandGoods(ResultSet rs){
        OnHandGoods good = null;
        try {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double price  = rs.getDouble("price");
            double count = rs.getDouble("count");
            String vahid = rs.getString("vahid");
            
            good  = new OnHandGoods(id, name, price, count,vahid);
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
        return good;
    }

    @Override
    public List<OnHandGoods> getAllOnHandGoods() {
        List<OnHandGoods> listGoods = new ArrayList<>();
      try(Connection c = connect()){
          Statement stmt = c.createStatement();
          stmt.execute("select * from on_hand_goods ");
          ResultSet rs = stmt.getResultSet();
          
          while(rs.next()){
             OnHandGoods good = getOnHandGoods(rs);
             listGoods.add(good);
          }
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return listGoods;
    }

    @Override
     public boolean removeOnHandGoods(int id) {
      try(Connection c = connect()){
          PreparedStatement stmt = c.prepareStatement("delete from on_hand_goods where id = ?");
          stmt.setInt(1, id);
          stmt.execute();
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return true;
    }

    @Override
    public boolean updateOnHandGoods(OnHandGoods good) {
      try(Connection c = connect()){
          PreparedStatement stmt = c.prepareStatement("update on_hand_goods set name = ? ,price=?,count=?,vahid=? where id = ?");
          stmt.setString(1,good.getName());
          stmt.setDouble(2,good.getPrice());
          stmt.setDouble(3,good.getCount());
          stmt.setString(4,good.getVahid());
          stmt.setInt(5,good.getId());
          stmt.execute();
      }catch(Exception ex){
          ex.printStackTrace();
      }
      return true;
    }

    @Override
    public boolean addOnHandGoods(OnHandGoods good) {
        try(Connection c = connect()){
          PreparedStatement stmt = c.prepareStatement("insert into on_hand_goods(name,price,count,vahid) values(?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
          stmt.setString(1,good.getName());
          stmt.setDouble(2,good.getPrice());
          stmt.setDouble(3,good.getCount());
          stmt.setString(4,good.getVahid());
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
