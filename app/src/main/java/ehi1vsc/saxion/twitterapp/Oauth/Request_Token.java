package ehi1vsc.saxion.twitterapp.Oauth;

/**
 * Created by edwin_000 on 23/05/2016.
 */
public class Request_Token extends TwitterOauth {

    public Request_Token(){
        super("https://api.twitter.com/oauth/request_token", "POST");
        properties.add(new String[]{"oauth_callback", "twitterclient://callback"});

        super.execute();
    }
}
