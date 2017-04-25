package io.github.dhjnsn.s3experiment;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.ArrayList;
import java.util.List;

import static io.github.dhjnsn.s3experiment.S3Settings.*;

/**
 * Created by dennis on 4/5/17.
 */

class FilesLoader extends AsyncTaskLoader<List<String>> {

    AmazonS3Client s3;

    FilesLoader(Context context) {
        super(context);
        s3 = new AmazonS3Client(new CognitoCachingCredentialsProvider(
                getContext(),
                COGNITO_POOL_ID, // Identity Pool ID
                Regions.EU_CENTRAL_1 // Region
        ));
        s3.setEndpoint("https://s3.eu-central-1.amazonaws.com");
    }

    @Override
    public List<String> loadInBackground() {
        List list = new ArrayList(50);
        for (S3ObjectSummary summary : s3.listObjects(BUCKET_NAME).getObjectSummaries())
            list.add(summary.getKey());
        return list;
    }
}
