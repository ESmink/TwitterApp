package ehi1vsc.saxion.twitterapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {

    static ImageView profilePicture;
    static TextView name, userName, status, amountFollowing, amountFollowers, amountTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        profilePicture = (ImageView) findViewById(R.id.iv_profilePicture);
        name = (TextView) findViewById(R.id.tv_twitterName);
        userName = (TextView) findViewById(R.id.tv_twitterUserName);
        status = (TextView) findViewById(R.id.tv_status);
        amountFollowing = (TextView) findViewById(R.id.tv_amountFollowing);
        amountFollowers = (TextView) findViewById(R.id.tv_amountFollowers);
        amountTweets = (TextView) findViewById(R.id.tv_amountTweets);

        new GetUser().execute();
    }

    public static void setUser(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            profilePicture.setImageResource(R.drawable.crashphoto);
            name.setText(jsonObject.getString("name"));
            userName.setText("@" + jsonObject.getString("screen_name"));
            status.setText(jsonObject.getString("description"));
            amountFollowers.setText(jsonObject.getString("followers_count"));
            amountFollowing.setText(jsonObject.getString("friends_count"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
