package com.example.shuvo.simplenotetakingapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Shuvo on 5/1/2017.
 */
public class Edit extends Activity implements View.OnClickListener {
EditText editText;
    Button save;
    DBAccess dbAccess;
    DBHelper dhelp;
    String date;
    String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_lay);

        dbAccess = DBAccess.getInstance(this);
        dhelp = new DBHelper(this);


        editText=(EditText)findViewById(R.id.edit);
        save=(Button)findViewById(R.id.save);
        save.setOnClickListener(this);

        Bundle p = getIntent().getExtras();
         content = p.getString("content");
         date = p.getString("date");
        editText.setText(content.substring(0,content.length()));

    }

    @Override
    public void onClick(View v) {
        if (save.equals(v))
        {
            String str = editText.getText().toString();
              dbAccess.open();
              dhelp.onEdit(date,str,content);
              onResume();
            startActivity(new Intent(Edit.this,MainActivity.class));
        }
    }
}
