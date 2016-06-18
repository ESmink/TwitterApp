package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;

import org.json.JSONException;
import org.json.JSONObject;

import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        User user = Model.getInstance().users.get(intent.getIntExtra("logUser", -1));

        ListView listView = (ListView) findViewById(R.id.listView);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_user, listView, false);
        listView.addHeaderView(view);

        ImageView profilePicture = (ImageView) view.findViewById(R.id.iv_profilePicture);
        profilePicture.setImageDrawable(user.getProfile_image(profilePicture, this));

        ((TextView) view.findViewById(R.id.tv_twitterName)).setText(user.getName());
        ((TextView) view.findViewById(R.id.tv_twitterUserName)).setText("@" + user.getScreen_name());
        ((TextView) view.findViewById(R.id.tv_status)).setText(user.getDescription());
        ((TextView) view.findViewById(R.id.tv_amountFollowing)).setText(user.getFriends_count());
        ((TextView) view.findViewById(R.id.tv_amountFollowers)).setText(user.getFollowers_count());
        //((TextView) view.findViewById(R.id.tv_amountTweets)).setText();

        Model.getInstance().getTweets().clear();
        final TweetAdapter adapter = new TweetAdapter(getBaseContext(), Model.getInstance().getTweets());
        listView.setAdapter(adapter);

        OAuthRequest request;
        if (user.equals(Ref.currentUser)) {
            request = new OAuthRequest(Verb.GET,
                    "https://api.twitter.com/1.1/statuses/home_timeline.json",
                    Model.getInstance().getTwitterService());
        } else {
            request = new OAuthRequest(Verb.GET,
                    "https://api.twitter.com/1.1/statuses/user_timeline.json",
                    Model.getInstance().getTwitterService());

            request.addBodyParameter("user_id", user.getId_str());
        }
        new CommonRequest() {

            @Override
            protected void onPostExecute(String s) {
                try {
                    finished(new JSONObject("{ statuses : " + s + " }"));
                } catch (JSONException | NullPointerException e) {
                }
            }

            @Override
            public void finished(JSONObject e) {
                try {
                    for (int x = 0; e.getJSONArray("statuses").length() > x; x++) {
                        Model.getInstance().getTweets().add(
                                new Tweet(e.getJSONArray("statuses").getJSONObject(x)));
                    }
                } catch (JSONException e1) {
                    //access denied :(
                }
                adapter.notifyDataSetChanged();
            }
        }.execute(request, this);
    }
}
