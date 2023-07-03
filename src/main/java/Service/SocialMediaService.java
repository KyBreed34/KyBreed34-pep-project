package Service;

import DAO.SocialMediaDAO;
import Model.Account;
import Model.Message;
import java.util.ArrayList;

public class SocialMediaService {
    SocialMediaDAO smDAO;

    public SocialMediaService(){
        smDAO = new SocialMediaDAO();
    }

    public Account createAccount(Account account){
        //do not create if the username is empty, password is not at least 4, or username is in use
        if(account.username=="" || account.password.length()<4 || smDAO.getUserByName(account.username)!=null)return null;
        Account result = smDAO.createUser(account);
        return result;    
    }

    public Account login(Account account){
        Account found = smDAO.getUserByName(account.getUsername());
        //if can't find user with username usernames can't match and passwords must be equal 
        if(found == null || !found.getPassword().equals(account.getPassword())) return null;
        return found;
    }

    public Message createMessage(Message message){
        Account user = smDAO.getUserByID(message.getPosted_by());
        //posted by valid user, message is not blank, message under 255 chars
        if(user==null || 0==message.message_text.length() || message.message_text.length()>=255)return null;
        Message result = smDAO.createMessage(message);
        return result;
    }

    public ArrayList<Message> getAllMessages(){
        return smDAO.getAllMessages();
    }

    public Message getMessageByID(int id){
        return smDAO.getMessageByID(id);
    }

    public Message deleteMessage(int id){
        return smDAO.deleteMessage(id);
    }

    public Message updateMessage(Message newMessage, int id){
        //valid messageid, message is not blank, message under 255 chars
        if( 0==newMessage.getMessage_text().length() || newMessage.getMessage_text().length()>=255)return null;
        return smDAO.updateMessage(newMessage, id);
    }

    public ArrayList<Message> getMessageByUser(int id){
        return smDAO.getMessageByUser(id);
    }
}
