package com.example.flowers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY_NAME="com.example.android.flowers.REPLY_NAME";
    public static final String EXTRA_REPLY_DATE="com.example.android.flowers.REPLY_DATE";
    public static final String EXTRA_REPLY_ID="com.example.android.flowers.REPLY_ID";

    private EditText flower_name;
    private EditText date;
    private Button save_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        flower_name=findViewById(R.id.flower_name);
        date=findViewById(R.id.date_id);
        save_button=findViewById(R.id.save_button);
        final Bundle extras=getIntent().getExtras();


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent=new Intent();
                if(TextUtils.isEmpty(flower_name.getText())){
                    setResult(RESULT_CANCELED,replyIntent);}
                else {
                    String fName = flower_name.getText().toString();
                    String fdate;
                    if (TextUtils.isEmpty(date.getText())) {
                        fdate = null;
                    } else {
                        fdate = date.getText().toString();
                    }

                    replyIntent.putExtra(EXTRA_REPLY_NAME, fName);
                    replyIntent.putExtra(EXTRA_REPLY_DATE, fdate);
                    if (extras != null && extras.containsKey(EXTRA_REPLY_ID)) {
                        int id = extras.getInt(EXTRA_REPLY_ID, -1);
                        if (id != -1) {
                            replyIntent.putExtra(EXTRA_REPLY_ID, id);
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }
}