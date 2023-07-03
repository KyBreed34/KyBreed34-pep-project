package Controller;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.SocialMediaService;
import io.javalin.Javalin;
import io.javalin.http.Context;
import DAO.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    SocialMediaService smService;
    public SocialMediaController(){
        smService = new SocialMediaService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        
        //create account
        app.post("/register", this::createAccountHandler);
        
        //login
        app.post("/login",this::loginHandler);

        //new message
        app.post("/messages",this::newMessageHandler);

        //get all messages
        app.get("/messages",this::getAllMessagesHandler);

        //get message by id
        app.get("/messages/{message_id}",this::getMessageByIDHandler);

        //delete message by id
        app.delete("/messages/{message_id}",this::deleteMessageHandler);
        

        //update message by id
        app.patch("/messages/{message_id}",this::updateMessageHandler);

        //get messages by user
        app.get("/accounts/{account_id}/messages",this::getMessageByUserHandler);
        
        return app;
    }

    
    private void createAccountHandler(Context ctx) throws JsonProcessingException{
            ObjectMapper om = new ObjectMapper();
            Account acc = om.readValue(ctx.body(), Account.class);
            Account create = smService.createAccount(acc);
            //not created 400 client error
            if(create==null){
                ctx.status(400);
            }
            //if created, response contains json of object and 200 ok response
            else{
                ctx.json(om.writeValueAsString(create));
                ctx.status(200);
            }
    }
    private void loginHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Account acc = om.readValue(ctx.body(), Account.class);
        Account login = smService.login(acc);
        if(login == null){
            ctx.status(401);
        }
        else{
            ctx.json(om.writeValueAsString(login));
            ctx.status(200);
        }
    }
    private void newMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = om.readValue(ctx.body(), Message.class);
        Message result = smService.createMessage(message);
        if(result == null){
            ctx.status(400);
        }
        else{
            ctx.json(om.writeValueAsString(result));
            ctx.status(200);
        }
    }
    private void getAllMessagesHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        ArrayList<Message> messageList = new ArrayList<Message>();
        messageList = smService.getAllMessages();
        ctx.json(om.writeValueAsString(messageList));
        ctx.status(200);
    }

    private void getMessageByIDHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = smService.getMessageByID(Integer.parseInt(ctx.pathParam("message_id")));
        if(message != null) ctx.json(om.writeValueAsString(message));
        ctx.status(200);
    }

    private void deleteMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message message = smService.deleteMessage(Integer.parseInt(ctx.pathParam("message_id")));
        if(message != null) ctx.json(om.writeValueAsString(message));
        ctx.status(200);
    }

    private void updateMessageHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        Message newMessage = om.readValue(ctx.body(), Message.class);
        Message resultMessage = smService.updateMessage(newMessage, Integer.parseInt(ctx.pathParam("message_id")));
        if(resultMessage != null){
            ctx.json(om.writeValueAsString(resultMessage));
            ctx.status(200);
        }
        else{
            ctx.status(400);
        }
    }

    private void getMessageByUserHandler(Context ctx) throws JsonProcessingException{
        ObjectMapper om = new ObjectMapper();
        int userID = Integer.parseInt(ctx.pathParam("account_id"));
        ArrayList<Message> usersMessages = smService.getMessageByUser(userID);
        
        ctx.json(om.writeValueAsString(usersMessages));  
        
        ctx.status(200);

        
    }

    


}