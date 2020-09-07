/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author User
 */
public abstract class AbstractDAO {
    public Connection connect() throws SQLException, ClassNotFoundException {

        String url = "jdbc:mysql://localhost:3306/sale";
        String userName = "root";
        String password = "0503771480m";
        Connection c = DriverManager.getConnection(url, userName, password);
        return c;
    }
}

