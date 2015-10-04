package com.codepath.apps.mysimpletweets.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

@Table(name = "Users")
public class User extends Model implements Serializable {

    // Define table fields
    @Column(name = "uid_str", unique = true, onUniqueConflict = Column.ConflictAction.REPLACE)
    private String uid_str;

    @Column(name = "name")
    private String name;

    @Column(name = "screenName")
    private String screenName;

    @Column(name = "profileImageUrl")
    private String profileImageUrl;

    public User() {
        super();
    }

    // Parse model from JSON
    public User(JSONObject jsonObject) {
        super();

        try {
            this.uid_str = jsonObject.getString("id_str");
            this.name = jsonObject.getString("name");
            this.screenName = jsonObject.getString("screen_name");
            this.profileImageUrl = jsonObject.getString("profile_image_url");
//            this.save();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Finds existing user based on uid_str or creates new user and returns
    public static User findOrCreateFromJson(JSONObject json) {

        User existingUser = null;
        try {
            existingUser = new Select().from(User.class).where("uid_str = ?", json.getString("id_str")).executeSingle();
//        } catch (JSONException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (existingUser != null) {
            // found and return existing
            return existingUser;
        } else {
            // create and return new user
            User user = new User(json);
            user.save();
            return user;
        }
    }

    // Getters
    public String getUid_str() {
        return uid_str;
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
