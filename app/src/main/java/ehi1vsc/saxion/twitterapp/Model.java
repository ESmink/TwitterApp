package ehi1vsc.saxion.twitterapp;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.util.ArrayList;
import java.util.List;

import ehi1vsc.saxion.twitterapp.Oauth.TwitterAPI;
import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

/**
 * Created by edwin_000 on 25/04/2016.
 */
public class Model {

    private OAuth10aService twitterService = new ServiceBuilder().apiKey(Ref.API_KEY)
            .apiSecret(Ref.API_SECRET)
            .callback(Ref.OAUTH_CALLBACK_URL)
            .build(new TwitterAPI());

    private ArrayList<Tweet> tweets = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    private static Model ourInstance;

    public static Model getInstance() {
        if (ourInstance == null) {
            ourInstance = new Model();
        }
        return ourInstance;
    }

    public OAuth10aService getTwitterService() {
        return twitterService;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public User addUser(User user) {
        for (User current : users) {
            if (user.getId_str().equals(current.getId_str())) {
                return current;
            }
        }
        users.add(user);
        return user;
    }
}
