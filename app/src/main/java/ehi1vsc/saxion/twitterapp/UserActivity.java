package ehi1vsc.saxion.twitterapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Verb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ehi1vsc.saxion.twitterapp.Tweet.Tweet;

public class UserActivity extends AppCompatActivity {
    User user;
    ImageView profilePicture;
    TweetAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        user = Model.getInstance().users.get(intent.getIntExtra("logUser", -1));

        ListView listView = (ListView) findViewById(R.id.listView);
        View view = LayoutInflater.from(this).inflate(R.layout.activity_user, listView, false);
        listView.addHeaderView(view);

        profilePicture = (ImageView) view.findViewById(R.id.iv_profilePicture);
        profilePicture.setImageDrawable(user.getProfile_image(profilePicture, this));

        ((TextView) view.findViewById(R.id.tv_twitterName)).setText(user.getName());
        ((TextView) view.findViewById(R.id.tv_twitterUserName)).setText("@" + user.getScreen_name());
        ((TextView) view.findViewById(R.id.tv_status)).setText(user.getDescription());
        ((TextView) view.findViewById(R.id.tv_amountFollowing)).setText(user.getFriends_count());
        ((TextView) view.findViewById(R.id.tv_amountFollowers)).setText(user.getFollowers_count());
        ((TextView) view.findViewById(R.id.tv_amountTweets)).setText(user.getStatuses_count());

        //ready's listview for timeline
        Model.getInstance().getTweets().clear();
        adapter = new TweetAdapter(getBaseContext(), Model.getInstance().getTweets());
        listView.setAdapter(adapter);

        //Code for showing the Timeline of the user
        OAuthRequest request = new OAuthRequest(Verb.GET,
                "https://api.twitter.com/1.1/statuses/user_timeline.json",
                Model.getInstance().getTwitterService());

        request.addParameter("user_id", user.getId_str());
        new CommonRequest() {

            @Override
            protected void onPostExecute(String s) {
                try {
                    finished(new JSONObject("{ statuses : " + s + " }"));
                } catch (JSONException | NullPointerException e) {
                    e.printStackTrace();
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

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        if (user.equals(Ref.currentUser)) {
            menu.add("Change Profile Picture");
        } else {

            OAuthRequest request = new OAuthRequest(Verb.GET,
                    "https://api.twitter.com/1.1/friendships/lookup.json",
                    Model.getInstance().getTwitterService());
            request.addParameter("user_id", user.getId_str());

            new CommonRequest() {
                @Override
                protected void onPostExecute(String s) {
                    try {
                        finished(new JSONArray(s).getJSONObject(0));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void finished(JSONObject e) {
                    try {
                        JSONArray array = e.getJSONArray("connections");
                        MenuItem follow = menu.add("follow");
                        MenuItem mute =  menu.add("mute");
                        MenuItem block = menu.add("block");
                        for (int i = 0; i < array.length(); i++) {
                            switch (array.getString(i)) {
                                case "following":
                                    follow.setTitle("unfollow");
                                    break;
                                case "muting":
                                    mute.setTitle("unmute");
                                    break;
                                case "blocking":
                                    block.setTitle("unblock");
                            }
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }.execute(request, this);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        OAuthRequest request = null;
        switch (item.getTitle().toString()) {
            case "Change Profile Picture":
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 5);
                return false;
            case "follow":
                request = new OAuthRequest(Verb.POST,
                        "https://api.twitter.com/1.1/friendships/create.json",
                        Model.getInstance().getTwitterService());
                break;
            case "unfollow":
                request = new OAuthRequest(Verb.POST,
                        "https://api.twitter.com/1.1/friendships/destroy.json",
                        Model.getInstance().getTwitterService());
                break;
            case "mute":
                request = new OAuthRequest(Verb.POST,
                        "https://api.twitter.com/1.1/mutes/users/create.json",
                        Model.getInstance().getTwitterService());
                break;
            case "unmute":
                request = new OAuthRequest(Verb.POST,
                        "https://api.twitter.com/1.1/mutes/users/destroy.json",
                        Model.getInstance().getTwitterService());
                break;
            case "block":
                request = new OAuthRequest(Verb.POST,
                        "https://api.twitter.com/1.1/blocks/create.json",
                        Model.getInstance().getTwitterService());
                break;
            case "unblock":
                request = new OAuthRequest(Verb.POST,
                        "https://api.twitter.com/1.1/blocks/destroy.json",
                        Model.getInstance().getTwitterService());
        }
        if (request != null) {
            request.addParameter("user_id", user.getId_str());
            new CommonRequest() {
                @Override
                public void finished(JSONObject e) {
                    invalidateOptionsMenu();
                }
            }.execute(request, this);
            return true;
        }
        return false;
    }

    //handles the receiving of a new image for your profile
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5 && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOS);
                String encoded = Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);

                //places request for profile image change
                OAuthRequest request = new OAuthRequest(Verb.POST,
                        "https://api.twitter.com/1.1/account/update_profile_image.json",
                        Model.getInstance().getTwitterService());

                request.addParameter("image", encoded);
                new CommonRequest() {
                    @Override
                    public void finished(JSONObject e) {
                        //reloads user definition & reloads profilePicture
                        Ref.currentUser = new User(e);
                        Model.getInstance().users.set(
                                Model.getInstance().users.indexOf(user), Ref.currentUser);
                        profilePicture.setImageDrawable(
                                Ref.currentUser.getProfile_image(profilePicture,
                                        UserActivity.this));

                    }
                }.execute(request, this);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
