package ehi1vsc.saxion.twitterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JSONreader.readJSON(getBaseContext());

        ListView listview = (ListView)findViewById(R.id.listView);
        listview.setAdapter(new TweetAdapter(getBaseContext(), Model.getInstance().getTweets()));
    }
}
