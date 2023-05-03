package com.example.flowers;

import static com.example.flowers.MainActivity.EXTRA_DATA_ID;
import static com.example.flowers.MainActivity.EXTRA_DATA_UPDATE_DATE;
import static com.example.flowers.MainActivity.EXTRA_DATA_UPDATE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

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

public class SecondActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String EXTRA_REPLY_NAME = "com.example.android.flowers.REPLY_NAME";
    public static final String EXTRA_REPLY_DATE = "com.example.android.flowers.REPLY_DATE";
    public static final String EXTRA_REPLY_ID = "com.example.android.flowers.REPLY_ID";

    private EditText flower_name_view;
    private TextView date_view;
    private Button save_button;

    private Button delete;
    private FlowerViewModel flowerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        flowerViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(FlowerViewModel.class);
        flower_name_view = findViewById(R.id.flower_name);
        date_view = findViewById(R.id.date_id);
        save_button = findViewById(R.id.save_button);
        delete = findViewById(R.id.delete_button);
        int id = -1;

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fl_name = extras.getString(EXTRA_DATA_UPDATE_NAME, "");
            String fl_date = extras.getString(EXTRA_DATA_UPDATE_DATE, "");
            id = extras.getInt(EXTRA_DATA_ID, -1);
            delete.setVisibility(View.VISIBLE);


            if (!fl_name.isEmpty()) {
                flower_name_view.setText(fl_name);
                flower_name_view.setSelection(fl_name.length());
                flower_name_view.requestFocus();
                if (fl_date != null)
                    date_view.setText(fl_date);
                date_view.requestFocus();
            }
        }

        date_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent replyIntent = new Intent();
                String data_name = flower_name_view.getText().toString();
                String data_date = date_view.getText().toString();
                int data_id = extras.getInt(EXTRA_DATA_ID, -1);

                flowerViewModel.delete(new Flower(data_id, data_name, stringToLong(data_date)));
                setResult(RESULT_CANCELED, replyIntent);
                finish();

            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent replyIntent = new Intent();

                //if the name field is empty
                if (TextUtils.isEmpty(flower_name_view.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String data_name = flower_name_view.getText().toString();
                    String data_date = null;
                    if (TextUtils.isEmpty(date_view.getText())) {
                        data_date = null;
                    } else {
                        data_date = date_view.getText().toString();
                    }

                    replyIntent.putExtra(EXTRA_REPLY_NAME, data_name);
                    replyIntent.putExtra(EXTRA_REPLY_DATE, data_date);

                    if (extras != null && extras.containsKey(EXTRA_DATA_ID)) {
                        int id = extras.getInt(EXTRA_DATA_ID, -1);
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


    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        long currentDate = c.getTimeInMillis();

        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(currentDate);
        TextView dateTextView = findViewById(R.id.date_id);
        dateTextView.setText(String.valueOf(strDate));
    }

    public long stringToLong(String currentDate) {
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long milliseconds = 100;
        try {
            Date d = dateFormat.parse(currentDate);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return milliseconds;
    }


}