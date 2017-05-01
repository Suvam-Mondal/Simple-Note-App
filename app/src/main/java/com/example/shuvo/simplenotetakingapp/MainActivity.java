package com.example.shuvo.simplenotetakingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener {
    Button add;
    DBAccess dbAccess;
    SQLiteDatabase db;
    DBHelper dhelp;
    TextView textView;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        add = (Button) findViewById(R.id.button);
        add.setOnClickListener(this);
        dbAccess = DBAccess.getInstance(this);
        dhelp = new DBHelper(this);

        dbAccess.restoreID(getApplicationContext());

        // Find ListView to populate
         lvItems = (ListView) findViewById(R.id.listView);
        lvItems.setOnItemClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        dbAccess.open();
       List<String> cs = dhelp.getData();

       lvItems.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cs));
        dbAccess.close();
    }
    @Override
    public void onClick(View v) {
        if (add.equals(v)) {
            Intent ADD = new Intent(MainActivity.this, AddActivity.class);
            startActivity(ADD);
        }
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
        String text = (String) lvItems.getItemAtPosition(position);

        String idd="";
        char ch;
        char f='M';
        int i=0;
        while((ch=text.charAt(i))!=f)
        {
            idd += ch;
            i++;
        }
        String note_date = idd + 'M';

        char s1;
        int j=0;
        String g="";
        int len = text.length()-1;
        /*while((s1=text.charAt(j))!='.')
        {
            g += s1;
            j++;
        }*/
        for(j=0;j<len;j++)
        {
            s1= text.charAt(j);
            g += s1;
        }
        String content = g.replace(note_date,"");
        content=content.trim();
        note_date=note_date.trim();

        PopupMenu popupMenu = new PopupMenu(MainActivity.this,view);
        popupMenu.getMenuInflater().inflate(R.menu.popup,popupMenu.getMenu());
        final String finalNe = content;
        final String finalNote_date = note_date;
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               if (item.getTitle().equals("Delete")) {
                   dbAccess.open();
                   dhelp.onDelete(finalNote_date,finalNe);
                   onResume();
               }
                else if (item.getTitle().equals("Edit"))
               {

                   Intent edit = new Intent(MainActivity.this,Edit.class);
                   edit.putExtra("content",finalNe);
                   edit.putExtra("date",finalNote_date);
                   startActivity(edit);

               }
                return true;
            }
        });

        popupMenu.show();
    }
}