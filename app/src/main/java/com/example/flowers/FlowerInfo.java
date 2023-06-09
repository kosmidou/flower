package com.example.flowers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.text.DateFormat;
import java.util.Calendar;


/**
 * SecondActivity displays a screen where the user can add a new flower
 * or update/delete the existing ones
 */
public class FlowerInfo extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    public static final String EXTRA_REPLY = "com.example.android.flowers.REPLY";
    public static final int REQUEST_CAMERA_PERMISSION = 1;
    public static final int REQUEST_CAMERA_CAPTURE_ACCESSED = 2;
    private EditText flowerNameView;
    private TextView dateView;
    private FlowerViewModel flowerViewModel;
    private Bitmap capturedImage;
    private ImageView previewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        flowerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(FlowerViewModel.class);
        flowerNameView = findViewById(R.id.flower_name);
        dateView = findViewById(R.id.date_id);
        Button saveButton = findViewById(R.id.save_button);
        Button deleteButton = findViewById(R.id.delete_button);
        FloatingActionButton cameraButton = findViewById(R.id.add_picture_button);
        previewImage = findViewById(R.id.imagePreview);


        int id = -1;
        Flower flowerExtraData = (Flower) getIntent().getSerializableExtra(FlowerGallery.EXTRA_DATA);

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
            if (flowerDate != 0) {
                dateView.setText(flowerExtraData.getDateFromLong(flowerDate));
            }
            dateView.requestFocus();

            if (flowerExtraData.getFlowerImage() != null) {
                File file = new File(flowerExtraData.getFlowerImage());
                previewImage.setVisibility(View.VISIBLE);
                previewImage.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            }

        }
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askCameraPermissions();
            }
        });

        //When the user clicks the textView Date, we display a calendar for choosing date
        dateView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //When the user presses Delete Button, we get the id,name and data for deleting the appropriate flower.Delete image from internal storage
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent replyIntent = new Intent();

                if (flowerExtraData.getFlowerImage() != null) {
                    new File(flowerExtraData.getFlowerImage()).delete();
                }
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
                Flower flower;

                //  If we have non empty flower_extra, it means that we update an existing flower
                if (flowerExtraData == null) {
                    flower = new Flower(dataName).setDateFromString(dataDate);
                } else {
                    flower = flowerExtraData.setFlowerName(dataName)
                            .setDateFromString(dataDate);
                }

                if (capturedImage != null) {
                    flower.setFlowerImagePath(capturedImage, getApplicationContext(), capturedImage.getGenerationId());
                }
                replyIntent.putExtra(EXTRA_REPLY, flower);
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

    /**
     * This method checks if permission have been already given to the app.
     * If the answer is yes , it opens the camera , otherwise it asks the user to select one of the permissions
     */
    private void askCameraPermissions() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            openCamera();
        }
    }

    //Checks which permission has been selected
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(this, "Camera Permission is required to use Camera", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, REQUEST_CAMERA_CAPTURE_ACCESSED);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_CAPTURE_ACCESSED && resultCode == RESULT_OK) {
            capturedImage = (Bitmap) data.getExtras().get("data");
            previewImage.setVisibility(View.VISIBLE);
            previewImage.setImageBitmap(capturedImage);
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        long currentDate = calendar.getTimeInMillis();

        DateFormat dateFormat = (Flower.DATE_FORMAT);
        String stringDate = dateFormat.format(currentDate);
        TextView dateTextView = findViewById(R.id.date_id);
        dateTextView.setText(String.valueOf(stringDate));
    }

}