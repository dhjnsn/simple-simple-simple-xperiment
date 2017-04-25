package io.github.dhjnsn.s3experiment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class FilesActivity extends AppCompatActivity implements FilesView {

    FilesPresenter presenter;
    ListViewAdapter listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listViewAdapter = new ListViewAdapter(this);
        listView.setAdapter(listViewAdapter);
        presenter = new FilesPresenter(this, new FilesLoader(getApplicationContext()));

        getLoaderManager().initLoader(0, null, presenter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.loadFiles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showFiles(List<String> files) {
        listViewAdapter.clear();
        listViewAdapter.addAll(files);
        listViewAdapter.notifyDataSetChanged();
    }

    private class ListViewAdapter extends ArrayAdapter<String> {

        ListViewAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());

            View view = (convertView == null) ?
                    layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false) :
                    convertView;
            TextView text = (TextView) view.findViewById(android.R.id.text1);
            text.setText(getItem(position));

            return view;
        }
    }

}
