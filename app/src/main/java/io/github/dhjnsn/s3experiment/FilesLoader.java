package io.github.dhjnsn.s3experiment;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by dennis on 4/5/17.
 */

class FilesLoader extends AsyncTaskLoader<List<String>> {

    AmazonS3Client s3;
    String bucketName;

    FilesLoader(Context context) {
        super(context);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String accessKey = prefs.getString("pref_aws_access_key", "");
        String secretKey = prefs.getString("pref_aws_secret_key", "");
        bucketName = prefs.getString("pref_s3_bucket_name", "");

        s3 = new AmazonS3Client(new BasicAWSCredentials(accessKey, secretKey));

        s3.setEndpoint("https://s3.eu-central-1.amazonaws.com");
    }

    @Override
    public List<String> loadInBackground() {
        List list = new ArrayList(50);
        for (S3ObjectSummary summary : s3.listObjects(bucketName).getObjectSummaries())
            list.add(summary.getKey());
        return list;
    }
}
