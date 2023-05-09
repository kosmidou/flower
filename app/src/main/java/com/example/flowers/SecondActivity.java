package com.example.flowers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * SecondActivity displays a screen where the user can add a new flower
 * or update/delete the existing ones
 */
public class SecondActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_REPLY = "com.example.android.flowers.REPLY";
    private EditText flowerNameView;
    private TextView dateView;

    private FlowerViewModel flowerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        flowerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(FlowerViewModel.class);
        flowerNameView = findViewById(R.id.flower_name);
        dateView = findViewById(R.id.date_id);
        Button saveButton = findViewById(R.id.save_button);
        Button deleteButton = findViewById(R.id.delete_button);

        int id = -1;
        Flower flowerExtraData = (Flower) getIntent().getSerializableExtra(MainActivity.EXTRA_DATA);

        //If we pass content,fill it in for the user to edit
        if (flowerExtraData != null) {
            String flowerName = flowerExtraData.getFlowerName();
            long flowerDate = flowerExtraData.getFlowerDate();
            id = flowerExtraData.getFlowerId();
            deleteButton.setVisibility(View.VISIBLE);

            flowerNameView.setText(flowerName);
            flowerNameView.setSelection(flowerName.length());
            flowerNameView.requestFocus();

                //If we don't have date let the date's textView empty
            if (flowerDate != 0){
                dateView.setText(flowerExtraData.getDateFromLong(flowerDate));}
            dateView.requestFocus();

        }

        //When the user clicks the textView Date, we display a calendar for choosing date
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //When the user presses Delete Button, we get keep the id,name and data for deleting the appropriate flower
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();
                flowerViewModel.delete(flowerExtraData);
                setResult(RESULT_FIRST_USER, replyIntent);
                finish();
            }
        });

        //When the user presses Save Button check the condition of name ,date and send the  Flower in MainActivity
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                //if the name field is empty
                if (TextUtils.isEmpty(flowerNameView.getText())) {
                    setResult(RESULT_CANCELED, replyIntent);
                    finish();
                }

                String dataName = flowerNameView.getText().toString();
                String dataDate = dateView.getText().toString();
                Flower flower ;

                //  If we have non empty flower_extra, it means that we update an existing flower
                if (flowerExtraData == null) {
                    flower = new Flower(dataName).setDateFromString(dataDate);
                } else {
                    flower = flowerExtraData.setFlowerName(dataName)
                                         .setDateFromString(dataDate);
                }

                replyIntent.putExtra(EXTRA_REPLY, flower);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        long currentDate = calendar.getTimeInMillis();

        DateFormat dateFormat =(Flower.DATE_FORMAT);
        String stringDate = dateFormat.format(currentDate);
        TextView dateTextView = findViewById(R.id.date_id);
        dateTextView.setText(String.valueOf(stringDate));
    }
}