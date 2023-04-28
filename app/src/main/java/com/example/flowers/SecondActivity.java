package com.example.flowers;

import static com.example.flowers.MainActivity.EXTRA_DATA_ID;
import static com.example.flowers.MainActivity.EXTRA_DATA_UPDATE_DATE;
import static com.example.flowers.MainActivity.EXTRA_DATA_UPDATE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {

    public static final String EXTRA_REPLY_NAME="com.example.android.flowers.REPLY_NAME";
    public static final String EXTRA_REPLY_DATE="com.example.android.flowers.REPLY_DATE";
    public static final String EXTRA_REPLY_ID="com.example.android.flowers.REPLY_ID";

    private EditText flower_name;
    private TextView date;
    private Button save_button;

    private  Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);


        flower_name=findViewById(R.id.flower_name);
        date=findViewById(R.id.date_id);
        save_button=findViewById(R.id.save_button);
        delete=findViewById(R.id.delete_button);

        int id=-1;
        final Bundle extras=getIntent().getExtras();
        if(extras!=null){
            String fl_name=extras.getString(EXTRA_DATA_UPDATE_NAME,"");
            String fl_date=extras.getString(EXTRA_DATA_UPDATE_DATE,"");
            delete.setVisibility(View.VISIBLE);


            if(!fl_name.isEmpty()){
                flower_name.setText(fl_name);
                flower_name.setSelection(fl_name.length());
                flower_name.requestFocus();
                date.setText(fl_date);
                //date.setSelection(fl_date.length());
                date.requestFocus();
            }
        }

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SecondActivity.this,"hdjddkj",Toast.LENGTH_LONG).show();
                DialogFragment datePicker= new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent=new Intent();

                String fname=null;
                String fdate=null;
                int id=extras.getInt(EXTRA_DATA_ID,-1);
                replyIntent.putExtra(EXTRA_REPLY_NAME,fname);
                replyIntent.putExtra(EXTRA_REPLY_DATE,fdate);
                replyIntent.putExtra(EXTRA_REPLY_ID,id);

                setResult(RESULT_OK,replyIntent);
                finish();
            }
        });

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
                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                       int id =extras.getInt(EXTRA_DATA_ID,-1);
                        if(id!=-1){
                            replyIntent.putExtra(EXTRA_REPLY_ID,id);
                        }
                    }
                    setResult(RESULT_OK, replyIntent);
                }
                finish();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        long currentDate =c.getTimeInMillis();

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate=dateFormat.format(currentDate);
        TextView dateTextView=findViewById(R.id.date_id);
        dateTextView.setText(String.valueOf(strDate));
    }
}