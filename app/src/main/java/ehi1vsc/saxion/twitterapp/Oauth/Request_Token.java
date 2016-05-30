package ehi1vsc.saxion.twitterapp.Oauth;

import java.util.ArrayList;

/**
 * Created by edwin_000 on 23/05/2016.
 */
public class Request_Token extends TwitterOauth {

    public Request_Token(){
        String password = "b5NRVMTKd9Y6JXjRlIAOGBnkG";

        ArrayList objects = new ArrayList();
        objects.add("https://api.twitter.com/oauth/request_token");
        objects.add("POST");
        ArrayList<String[]> properties = new ArrayList<>();
        properties.add(new String[]{"oauth_callback", "https://www.google.nl/"});
        objects.add(properties);

        super.execute(objects.toArray());
    }
}
