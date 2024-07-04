package com.library.helpers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.library.Log;
import com.library.Store;
import com.library.listeners.QASuiteListener;

import java.io.File;

public class S3Helper
{
    public static String upload(File file)
    {
        String keyPath = QASuiteListener.runId + "/" + Store.getTestId();
        String folder = "/" + keyPath;
        try {
            if (upload(file, folder) != null) {
                return keyPath + "/" + file.getName();
            }
        } catch (Exception e) {
            Log.exception(e);
        }
        return null;
    }

    public static PutObjectResult upload(File file, String folder)
    {
        try {
            return getAmazonS3Client().putObject(Store.getSettings().getAwsS3BucketName() + folder, file.getName(), file);
        } catch (Exception e) {
            Log.exception(e);
        }
        return null;
    }

    private static AmazonS3 getAmazonS3Client()
    {
        String awsAccess = PasswordHelper.decryptPassword(Store.getSettings().getPasswordKey(), Store.getSettings().getAwsAccessKeyEncrypted());
        String awsSecret = PasswordHelper.decryptPassword(Store.getSettings().getPasswordKey(), Store.getSettings().getAwsSecretKeyEncrypted());

        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(awsAccess, awsSecret)))
                .withRegion(Regions.EU_WEST_1)
                .build();
    }
}
