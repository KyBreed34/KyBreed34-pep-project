package Service;

import DAO.SocialMediaDAO;
import Model.Account;

public class SocialMediaService {
    SocialMediaDAO smDAO;

    public SocialMediaService(){
        smDAO = new SocialMediaDAO();
    }

    public Account createAccount(Account account){
        //do not create if the username is empty, password is not at least 4, or username is in use
        if(account.username=="" || account.password.length()<4 || smDAO.getUser(account.username)!=null)return null;
        Account result = smDAO.createUser(account);
        return result;    
    }
}
