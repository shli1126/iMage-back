package com.images.iMages.bucket;

public enum BucketName {

    PROFILE_IMAGE("images-bucket-xyz");
    private final String bucketName;
    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }
    public String getBucketName() {
        return bucketName;
    }
}
