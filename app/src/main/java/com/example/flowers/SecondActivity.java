package com.example.flowers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * SecondActivity displays a screen where the user can add a new flower
 * or update/delete the existing ones
 */
public class SecondActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_REPLY = "com.example.android.flowers.REPLY";
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

        Flower flower_extra = (Flower) getIntent().getSerializableExtra(MainActivity.EXTRA_DATA);

        //If we pass content,fill it in for the user to edit
        if (flower_extra != null) {
            String fl_name = flower_extra.getFlowerName();
            long fl_date = flower_extra.getDate();
            id = flower_extra.getId();
            delete.setVisibility(View.VISIBLE);

            if (!fl_name.isEmpty()) {
                flower_name_view.setText(fl_name);
                flower_name_view.setSelection(fl_name.length());
                flower_name_view.requestFocus();

                //If we don't have date let the date's textView empty
                if (fl_date != 0)
                    date_view.setText(flower_extra.longToString(fl_date));
                date_view.requestFocus();
            }
        }

        //When the user clicks the textView Date, we display a calendar for choosing date
        date_view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //When the user presses Delete Button, we get keep the id,name and data for deleting the appropriate flower
        delete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent replyIntent = new Intent();
                String data_name = flower_extra.getFlowerName();
                long data_date = flower_extra.getDate();
                int data_id = flower_extra.getId();

                flowerViewModel.delete(new Flower(data_id, data_name, data_date));
                setResult(RESULT_CANCELED, replyIntent);
                finish();
            }
        });

        //When the user presses Save Button check the condition of name ,date and send the  Flower in MainActivity
        save_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent replyIntent = new Intent();
                Flower flower = null;

                //if the name field is empty
                if (TextUtils.isEmpty(flower_name_view.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                } else {
                    String data_name = flower_name_view.getText().toString();
                    long data_date = 0;
                    if (TextUtils.isEmpty(date_view.getText())) {
                        data_date = 0;
                    } else {
                        data_date = stringToLong(date_view.getText().toString());
                    }

                    //  If we have non empty flower_extra, it means that we update an existing flower
                    if (flower_extra != null) {
                        int id = flower_extra.getId();
                        if (id != -1) {
                            flower = new Flower(id, data_name, data_date);
                        }
                    } else {
                        flower = new Flower(data_name, data_date);
                    }
                    replyIntent.putExtra(EXTRA_REPLY, (Serializable) flower);
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