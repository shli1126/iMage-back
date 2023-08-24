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
        USER_PROFILE_LIST.add(new UserProfile(UUID.randomUUID(), "kid a", null));
        USER_PROFILE_LIST.add(new UserProfile(UUID.randomUUID(), "kid b", null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILE_LIST;
    }

}
