package com.iit.lab2.bucket;

public enum BucketName {
    PROFILE_IMAGE("bucket-ad-games-images");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
