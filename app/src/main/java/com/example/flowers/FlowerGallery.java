package com.example.flowers;

import static com.example.flowers.R.string.delete_data;
import static com.example.flowers.R.string.no_update;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


/**
 * MainActivity displays a list of flowers in the RecyclerView
 * the layout displays a button,that allows users to move in SecondActivity and add a new Flower
 * Whenever a new flower is added,deleted or updated the RecyclerView manages the list of flowers
 */
public class FlowerGallery extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_DATA = "extra_data";
    private FlowerViewModel flowerViewModel;
    private  FlowerAdapter adapter;
    private TextView welcomeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        adapter = new FlowerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton addFlower = findViewById(R.id.addFlowerButton);
        welcomeText = findViewById(R.id.welcomeText);

        addFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlowerGallery.this, FlowerInfo.class);
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        //Get the flowers from the database
        //associate them with the adapter
        flowerViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(FlowerViewModel.class);

      flowerViewModel.getAllItems().observe(FlowerGallery.this, new Observer<List<Flower>>() {
           @Override
          public void onChanged(List<Flower> flowers) {
             adapter.setFlowers(flowers);
           }
     });

        adapter.setOnItemListener(new FlowerAdapter.Listener() {
            @Override
            public void itemClicked(View v, int position) {
                Flower flower = adapter.getFlowerAtPosition(position);
                launchUpdate(flower);
            }
        });

        if(flowerViewModel.getAllItems().getValue() == null){
            welcomeText.setVisibility(View.VISIBLE);
        }else {
            welcomeText.setVisibility(View.INVISIBLE);
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Flower flower = (Flower) data.getSerializableExtra(FlowerInfo.EXTRA_REPLY);
            flowerViewModel.insert(flower);
        } else if (requestCode == UPDATE_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Flower flower = (Flower) data.getSerializableExtra(FlowerInfo.EXTRA_REPLY);
            flowerViewModel.update(flower);
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, no_update, Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, delete_data, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * @param flower The flower that user clicked for updating
     *               We pass the flower in SecondActivity as ExtraData , so we can modify it
     */

    public void launchUpdate(Flower flower) {
        Intent intent = new Intent(this, FlowerInfo.class);
        intent.putExtra(EXTRA_DATA, flower);
        startActivityForResult(intent, UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }

}