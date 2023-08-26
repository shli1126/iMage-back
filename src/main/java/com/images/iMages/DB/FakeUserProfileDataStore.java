package com.images.iMages.DB;

import com.images.iMages.Model.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILE_LIST = new ArrayList<>();

    static {
        USER_PROFILE_LIST.add(new UserProfile(UUID.fromString("2d2367c4-5dbb-49e3-8f86-a7b8f604829a"), "kid a", null));
        USER_PROFILE_LIST.add(new UserProfile(UUID.fromString("0fdf1e4b-250b-432b-8bb8-5be2b69f5e0a"), "kid b", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILE_LIST;
    }

}
