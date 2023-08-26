package com.images.iMages.Service;

import com.images.iMages.Model.UserProfile;
import com.images.iMages.bucket.BucketName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import java.io.IOException;
import java.util.*;


import static org.springframework.util.MimeTypeUtils.*;


@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    public List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {
        isFileEmpty(file);
        isFileImage(file);
        UserProfile user = getUserProfileOrThrow(userProfileId);
        String bucketName = String.format("%s", BucketName.PROFILE_IMAGE.getBucketName());
        String filePathName = String.format("%s/%s", userProfileId, file.getOriginalFilename());

       try{
           fileStore.save(bucketName, filePathName, file.getBytes());
            user.setUserProfileImageLink(filePathName);
       } catch (IOException e) {
           throw new IllegalStateException(e);
       }
    }

    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);
        String bucketName = String.format("%s", BucketName.PROFILE_IMAGE.getBucketName());
        Optional<String> filePathName = user.getUserProfileImageLink();
        if (filePathName == null) {
            throw new IllegalStateException("No such file");
        }
        else {
            ResponseBytes<GetObjectResponse> object = fileStore.download(bucketName, filePathName.get());
            return object.asByteArray();
        }

    }



    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userProfileDataAccessService
                .getUserProfiles()
                .stream()
                .filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("no such user"));
    }

    private static void isFileImage(MultipartFile file) {
        if (Arrays.asList(
                IMAGE_JPEG.getType(),
                IMAGE_PNG.getType(),
                IMAGE_GIF.getType()).contains(file.getContentType())) {
           throw new IllegalStateException("File must be an image");
       }
    }

    private static void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("cannot be empty");
        }
    }
}
//    private static Map<String, String> extractMetadata(MultipartFile file) {
//        Map<String, String> metadata = new HashMap<>();
//        metadata.put("Content-Type", file.getContentType());
//        metadata.put("Content-Length", String.valueOf(file.getSize()));
//        return metadata;
//    }
//        Map<String, String> metadata = extractMetadata(file);
//String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
//String fileName = String.format("%s-%s", file.getName(), UUID.randomUUID());
