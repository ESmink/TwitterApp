package ehi1vsc.saxion.twitterapp;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;

import java.net.URLEncoder;
import java.util.ArrayList;

import ehi1vsc.saxion.twitterapp.Oauth.BearerToken;
import ehi1vsc.saxion.twitterapp.Oauth.SearchTweets;
import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

public class MainActivity extends AppCompatActivity {

    public BearerToken bearerToken;
    public String text;
    //SearchTweets searchTweets = new SearchTweets();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONreader.readJSON(getBaseContext());

        bearerToken = new BearerToken();
        bearerToken.execute();

        listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(new TweetAdapter(getBaseContext(), Model.getInstance().getTweets()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                text = URLEncoder.encode(query);
                new SearchTweets().execute(MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    public void setSearchTweetsList(ArrayList<Tweet> tweets){
        listview.setAdapter(new TweetAdapter(getBaseContext(), tweets));
    }
}
