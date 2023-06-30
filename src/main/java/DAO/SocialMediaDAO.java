package DAO;
import java.sql.*;

import org.h2.command.Prepared;

import Model.Account;
import Util.ConnectionUtil;

public class SocialMediaDAO{
    public Account getUser(String username){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username=?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet result = preparedStatement.executeQuery();
            //create and return account object
            while(result.next()){
                Account acc = new Account(result.getInt("account_id"),result.getString("username"),result.getString("password"));
                return acc;
            }
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account createUser(Account acc){
        Connection con = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?,?)";
            PreparedStatement preparedStatement = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, acc.getUsername());
            preparedStatement.setString(2, acc.getPassword());
            preparedStatement.executeUpdate();
            //executeUpdate returns int of rows affected not result set so use getGeneratedKeys
            ResultSet result = preparedStatement.getGeneratedKeys();
            //get generated account id
            if(result.next()){
                int generatedID = (int)result.getLong(1);
                //return account with generated ID
                return new Account(generatedID,acc.getUsername(),acc.getPassword());
            }            
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

}