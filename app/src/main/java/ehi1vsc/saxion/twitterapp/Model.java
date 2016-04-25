package ehi1vsc.saxion.twitterapp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edwin_000 on 25/04/2016.
 */
public class Model {
    private ArrayList<Tweet> tweets = new ArrayList<>();

    private static Model ourInstance = new Model();

    public static Model getInstance() {
        return ourInstance;
    }

    private Model() {
    }

    public List<Tweet> getTweets(){
        return tweets;
    }
}
