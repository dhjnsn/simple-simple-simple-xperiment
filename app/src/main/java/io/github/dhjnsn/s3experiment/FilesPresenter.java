package io.github.dhjnsn.s3experiment;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import java.util.List;

/**
 * Created by dennis on 4/2/17.
 */


class FilesPresenter implements LoaderManager.LoaderCallbacks<List<String>> {

    FilesView view;
    FilesLoader loader;

    FilesPresenter(FilesView filesView, FilesLoader filesLoader) {
        view = filesView;
        loader = filesLoader;
    }

    void loadFiles() {
        loader.forceLoad();
    }

    @Override
    public Loader<List<String>> onCreateLoader(int id, Bundle args) {
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<String>> loader, List<String> data) {
        view.showFiles(data);
    }

    @Override
    public void onLoaderReset(Loader<List<String>> loader) {

    }
}
