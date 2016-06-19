package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import ehi1vsc.saxion.twitterapp.Oauth.SearchTweets;
import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

public class MainActivity extends AppCompatActivity {
    private ListView listview;
    private TweetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting existing login
        SharedPreferences pref = getSharedPreferences("login", MODE_PRIVATE);
        if(!pref.getString("token", "").equals("")) {
            Ref.accessToken = new OAuth1AccessToken(pref.getString("token", ""), pref.getString("secret", ""));
            OAuthRequest request = new OAuthRequest(Verb.GET,
                    "https://api.twitter.com/1.1/account/verify_credentials.json",
                    Model.getInstance().getTwitterService());
            new CommonRequest() {
                @Override
                public void finished(JSONObject e) {
                    Ref.currentUser = Model.getInstance().addUser(new User(e));
                }
            }.execute(request, getBaseContext());
        }else{
            startActivity(new Intent(this, LoginActivity.class));
        }

        listview = (ListView) findViewById(R.id.listView);

        View view = LayoutInflater.from(this).inflate(R.layout.tweetheader, listview, false);
        listview.addHeaderView(view);
        final EditText text = (EditText) findViewById(R.id.TweetEdit);
        view.findViewById(R.id.tweetButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!(text.getText() + "").isEmpty()) {

                    OAuthRequest request = new OAuthRequest(Verb.POST,
                            "https://api.twitter.com/1.1/statuses/update.json",
                            Model.getInstance().getTwitterService());
                    request.addBodyParameter("status", text.getText() + "");
                    new CommonRequest() {
                        @Override
                        public void finished(JSONObject e) {
                            Log.d("tweet:", e.toString());
                        }
                    }.execute(request, MainActivity.this);
                }
            }
        });
        listview.setAdapter(adapter = new TweetAdapter(getBaseContext(), Model.getInstance().getTweets()));
        //showTimeline();
    }

    @Override
    public void onStart() {
        //hier komt het laden van tweets in de toekomst
        JSONreader.readJSON(this);
        adapter.notifyDataSetChanged();
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("searching for: ", URLEncoder.encode(query));
                new SearchTweets().execute(URLEncoder.encode(query), adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        menu.addSubMenu("to profile");
        menu.addSubMenu("logout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getTitle().equals("to profile")) {
            if (Ref.currentUser != null) {
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                intent.putExtra("logUser", Model.getInstance().users.indexOf(Ref.currentUser));
                startActivity(intent);
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }else if(item.getTitle().equals("logout")){
            Ref.requestToken = null;
            Ref.accessToken = null;
            Ref.verifier = null;
            Ref.bearertoken = null;
            Ref.currentUser = null;
            SharedPreferences.Editor pref = getSharedPreferences("login", MODE_PRIVATE).edit();
            pref.clear();
            pref.apply();
            startActivity(new Intent(this, LoginActivity.class));
        }
        return false;
    }
}
