package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Intent intent = getIntent();
        User user = Model.getInstance().users.get(intent.getIntExtra("logUser", -1));

        ImageView profilePicture = (ImageView) findViewById(R.id.iv_profilePicture);
        profilePicture.setImageDrawable(user.getProfile_image(profilePicture, this));

        ((TextView) findViewById(R.id.tv_twitterName)).setText(user.getName());
        ((TextView) findViewById(R.id.tv_twitterUserName)).setText("@" + user.getScreen_name());
        ((TextView) findViewById(R.id.tv_status)).setText(user.getDescription());
        ((TextView) findViewById(R.id.tv_amountFollowing)).setText(user.getFriends_count());
        ((TextView) findViewById(R.id.tv_amountFollowers)).setText(user.getFollowers_count());
        //((TextView) findViewById(R.id.tv_amountTweets)).setText();
    }
}
