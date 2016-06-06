package ehi1vsc.saxion.twitterapp.Oauth;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthConfig;

/**
 * Created by edwin_000 on 05/06/2016.
 */
public class OAuth10aService extends com.github.scribejava.core.oauth.OAuth10aService {
    private static OAuth10aService ourInstance = new OAuth10aService(new DefaultApi10a() {

        @Override
        public String getRequestTokenEndpoint() {
            return "https://api.twitter.com/oauth/request_token";
        }

        @Override
        public String getAccessTokenEndpoint() {
            return "https://api.twitter.com/oauth/access_token";
        }

        @Override
        public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
            return "https://api.twitter.com/oauth/authorize";
        }

    }, new OAuthConfig(null, null));

    public static OAuth10aService getInstance() {
        return ourInstance;
    }

    public OAuth10aService(DefaultApi10a api, OAuthConfig config) {
        super(api, config);
    }

}
