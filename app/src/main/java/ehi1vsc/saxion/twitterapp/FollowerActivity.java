package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FollowerActivity extends AppCompatActivity {
    private ArrayList<User> users = new ArrayList<>();
    private UserAdapter adapter;

    public static boolean FOLLOWER = true;
    public static boolean FRIEND = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new UserAdapter(this, users);

        ((ListView)findViewById(R.id.listView)).setAdapter(adapter);

        Intent intent = getIntent();
        OAuthRequest request;
        if(intent.getBooleanExtra("mode", FOLLOWER)){
            request = new OAuthRequest(Verb.GET,
                    "https://api.twitter.com/1.1/followers/list.json",
                    Model.getInstance().getTwitterService());
        }else{
            request = new OAuthRequest(Verb.GET,
                    "https://api.twitter.com/1.1/friends/list.json",
                    Model.getInstance().getTwitterService());
        }
        request.addParameter("user_id", Model.getInstance().getUsers().get(intent.getStringExtra("id")).toString());

        new CommonRequest() {
            @Override
            public void finished(JSONObject e) {
                try {
                    JSONArray array = e.getJSONArray("users");
                    for(int x = 0;array.length() > x;x++){
                        User user = Model.getInstance().addUser(new User(array.getJSONObject(x)));
                        users.add(user);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        }.execute(request, this);
    }
}
