/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao.inter;

import entity.OnHandGoods;
import java.util.List;

/**
 *
 * @author User
 */
public interface OnHandGoodsDaoInter {
    
     public List<OnHandGoods> getAllOnHandGoods();
     
     public boolean removeOnHandGoods(int id);
     
     public boolean updateOnHandGoods(OnHandGoods good);
     
     public boolean addOnHandGoods(OnHandGoods good);
    
}
