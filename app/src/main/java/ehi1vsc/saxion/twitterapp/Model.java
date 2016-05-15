package ehi1vsc.saxion.twitterapp;

import java.util.ArrayList;
import java.util.List;

import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

/**
 * Created by edwin_000 on 25/04/2016.
 */
public class Model {
    private ArrayList<Tweet> tweets = new ArrayList();
    private ArrayList<User> users = new ArrayList();

    private static Model ourInstance;

    public static Model getInstance() {
        if (ourInstance == null) {
            ourInstance = new Model();
        }
        return ourInstance;
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
