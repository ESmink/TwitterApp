package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import ehi1vsc.saxion.twitterapp.Oauth.BearerToken;
import ehi1vsc.saxion.twitterapp.Oauth.SearchTweets;
import ehi1vsc.saxion.twitterapp.Oauth.TwitterOauth;

public class MainActivity extends AppCompatActivity {

    BearerToken bearerToken;
    SearchTweets searchTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONreader.readJSON(getBaseContext());

        if(Ref.accessToken == null) {
            new TwitterOauth().execute(this);
        }
//        BearerToken bearerToken = new BearerToken();
//        bearerToken.execute();
//
//        String token = bearerToken.getBearerToken();

        ListView listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(new TweetAdapter(getBaseContext(), Model.getInstance().getTweets()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchTweets.execute(new String[]{bearerToken.getBearerToken(), newText});

                return true;
            }
        });
        menu.addSubMenu("to profile");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getTitle().equals("to profile")){
            Intent intent = new Intent(MainActivity.this, UserActivity.class);
            startActivity(intent);
        }
        return false;
    }
}
