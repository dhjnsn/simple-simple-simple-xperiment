package io.github.dhjnsn.s3experiment;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.util.ArrayList;
import java.util.List;

class FilesViewModel extends AndroidViewModel {

    MutableLiveData<List<String>> files;

    public FilesViewModel(Application application) {
        super(application);
        files = new MutableLiveData<List<String>>();
        loadFiles();
    }

    public LiveData<List<String>> getFiles() {
        return files;
    }

    private void loadFiles() {
        new AsyncTask<Void, Void, List<String>>() {

            @Override
            protected List<String> doInBackground(Void... params) {
                SharedPreferences prefs =
                        PreferenceManager.getDefaultSharedPreferences(getApplication());
                String accessKey = prefs.getString("pref_aws_access_key", "");
                String secretKey = prefs.getString("pref_aws_secret_key", "");
                String bucketName = prefs.getString("pref_s3_bucket_name", "");

                BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
                AmazonS3Client s3Client = new AmazonS3Client(credentials);
                s3Client.setEndpoint("https://s3.eu-central-1.amazonaws.com");

                List list = new ArrayList(50);
                for (S3ObjectSummary summary : s3Client.listObjects(bucketName).getObjectSummaries())
                    list.add(summary.getKey());

                return list;
            }

            @Override
            protected void onPostExecute(List<String> files) {
                FilesViewModel.this.files.setValue(files);
            }
        }.execute();
    }
}
