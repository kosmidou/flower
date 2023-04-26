package com.example.flowers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private FlowerViewModel flowerViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        final FlowerAdapter adapter=new FlowerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        flowerViewModel=
                new ViewModelProvider((ViewModelStoreOwner) this,(ViewModelProvider.Factory)new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(FlowerViewModel.class);
        flowerViewModel.getAllItems().observe(this, new Observer<List<Flower>>() {
            @Override
            public void onChanged(List<Flower> flowers) {
                adapter.setFlowers(flowers);
            }
        });
    }
}