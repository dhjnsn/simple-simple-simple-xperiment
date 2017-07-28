package io.github.dhjnsn.s3experiment;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class FilesActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    ListView listView;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.list_view);
        FilesViewModel viewModel = ViewModelProviders.of(this).get(FilesViewModel.class);
        viewModel.getFiles().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> files) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(FilesActivity.this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, files);
                FilesActivity.this.listView.setAdapter(adapter);
            }
        });
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }
/*
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
*/
}
