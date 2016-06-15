package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import ehi1vsc.saxion.twitterapp.Oauth.SearchTweets;
import ehi1vsc.saxion.twitterapp.Oauth.TwitterOauth;
import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

public class MainActivity extends AppCompatActivity {
    public String text;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONreader.readJSON(getBaseContext());

        listview = (ListView) findViewById(R.id.listView);
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
        menu.addSubMenu("to profile");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("to profile")) {
            if(Ref.currentUser != null){
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("logUser", Model.getInstance().users.indexOf(Ref.currentUser));
                startActivity(intent);
            }else{
                new TwitterOauth().execute(this);
            }
        }
        return false;
    }

    public void setSearchTweetsList(ArrayList<Tweet> tweets) {
        listview.setAdapter(new TweetAdapter(getBaseContext(), tweets));
    }
}
