package ehi1vsc.saxion.twitterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONObject;

import ehi1vsc.saxion.twitterapp.Oauth.Request_Token;
import ehi1vsc.saxion.twitterapp.Oauth.TwitterOauth;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONreader.readJSON(getBaseContext());

        Request_Token token = new Request_Token();


        ListView listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(new TweetAdapter(getBaseContext(), Model.getInstance().getTweets()));
    }
}
