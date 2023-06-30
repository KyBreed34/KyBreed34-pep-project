package Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
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
        app.get("example-endpoint", this::exampleHandler);

        
        //create account
        app.post("/register", this::createAccountHandler);
        /*
        //login
        app.post("/login",);

        //new message
        app.post("/messages",);

        //get all messages
        app.get("/messages",);

        //get message by id
        app.get("/messages/{message_id}",);

        //delete message by id
        app.delete("/messages/{message_id},");

        //update message by id
        app.patch("/messages/{message_id},");

        //get messages by user
        app.get("/accounts/{account_id}/messages",);
        */
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
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


}