package com.example.boshen.ghost_gear;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class ManageActivity extends ListActivity {


    SharedPreferences userPref;
    SharedPreferences.Editor userPrefEditor;

    ListAdapter listAdapter;
    ArrayAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        userPref = getSharedPreferences("user_info", Context.MODE_PRIVATE);
        userPrefEditor = userPref.edit();

        server myserver = new server();
        String id = userPref.getString("username", "");
        myserver.getMyTraps(id);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, myserver.displaylist);
        setListAdapter(adapter);



    }
}
