package com.images.iMages.Service;

//import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.S3Client;

@Service
public class FileStore {
    private final S3Client s3;

    @Autowired
    public FileStore(S3Client s3) {
        this.s3 = s3;
    }

    public void putObject(String bucketName, String key, byte[] file) {

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
        s3.putObject(objectRequest, RequestBody.fromBytes(file));

    }

}

//    public void save(String path,
//                     String fileName,
//                     Optional<Map<String, String>> optionalMetadata,
//                     InputStream inputStream) {
//        ObjectMetadata metadata = new ObjectMetadata();
//        optionalMetadata.ifPresent(map -> {
//            if (!map.isEmpty()) {
//                map.forEach(metadata::addUserMetadata);
//            }
//        });
//        try {
//            //how to put????????
//
//            s3.putObject(new PutObjectRequest(path, fileName, inputStream.toString));
//        } catch (AmazonServiceException e) {
//            throw new IllegalStateException("Failed to store file to s3", e);
//        }
//    }
