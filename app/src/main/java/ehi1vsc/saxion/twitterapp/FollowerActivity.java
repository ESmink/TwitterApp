package ehi1vsc.saxion.twitterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;

import org.json.JSONObject;

import java.util.ArrayList;

public class FollowerActivity extends AppCompatActivity {
    private ArrayList<User> users = new ArrayList<>();
    private UserAdapter adapter = new UserAdapter(this, users);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((ListView)findViewById(R.id.listView)).setAdapter(adapter);

//        OAuthRequest request = new OAuthRequest(Verb.GET,
//                "https://api.twitter.com/1.1/friendships/lookup.json",
//                Model.getInstance().getTwitterService());
//        request.addParameter("user_id", user.getId_str());

        new CommonRequest() {
            @Override
            public void finished(JSONObject e) {

            }
        }.execute(request, this);
    }
}
