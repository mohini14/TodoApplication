package com.example.mohini.todoapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> taskList;
    private ArrayAdapter<String> adapter;
    private ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView) findViewById(R.id.lvItems);
        taskList = new ArrayList<String>();

        readItemsFromFile();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, taskList);
        lvItems.setAdapter(adapter);
//        taskList.add("item1");
//        taskList.add("item2");
        setUpListViewListner();
    }

    // create menu on activity
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Actions on activity
    public void addItemButtonClicked(View v) {

        EditText etNewTask = (EditText) findViewById(R.id.etEnterNewTask);
        String newTaskText = etNewTask.getText().toString();
        adapter.add(newTaskText);
        writeItemsToFile();
        etNewTask.setText("");
    }

    // private methods
    private void setUpListViewListner() {

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                taskList.remove(i);
                adapter.notifyDataSetChanged();
                writeItemsToFile();
                return true;
            }
        });
    }

    //file related methods
    private void readItemsFromFile() {

        File fileDirectory = getFilesDir();
        File file = new File(fileDirectory, "todoFile.txt");

        // read from file
        try {
            taskList = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            taskList = new ArrayList<String>();
        }

    }

    private void writeItemsToFile() {

        File fileDirectory = getFilesDir();
        File file = new File(fileDirectory, "todoFile.txt");

        try {
            FileUtils.writeLines(file, taskList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
