package com.example.shuvo.simplenotetakingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by Shuvo on 4/28/2017.
 */
public class AddActivity extends Activity implements View.OnClickListener {
    DBHelper dbHelper;
    EditText editText;
    Button button;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        editText=(EditText)findViewById(R.id.edittext);
        button=(Button)findViewById(R.id.button2);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (button.equals(v))
        {
            String str= editText.getText().toString();
            int length = str.length();
            if (str.charAt(length-1)!='.')
            {
                str += '.';
            }

            DBAccess dbAccess = DBAccess.getInstance(this);
            dbAccess.open();
            dbAccess.save(str,getApplicationContext());
            dbAccess.close();
            this.finish();
        }
    }
}
