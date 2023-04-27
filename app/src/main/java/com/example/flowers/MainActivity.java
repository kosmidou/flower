package com.example.flowers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    public static final String EXTRA_DATA_UPDATE_NAME="extra_name_to_be_updated";
    public static final String EXTRA_DATA_UPDATE_DATE="extra_data_date";
    public static final String EXTRA_DATA_ID="extra_data_id";

    private FlowerViewModel flowerViewModel;
    private Button addFlower;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final FlowerAdapter adapter = new FlowerAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        addFlower=findViewById(R.id.addFlowerButton);
        addFlower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MainActivity.this,SecondActivity.class);
                startActivityForResult(intent,NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        flowerViewModel =
                new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(FlowerViewModel.class);
        flowerViewModel.getAllItems().observe(this, new Observer<List<Flower>>() {
            @Override
            public void onChanged(List<Flower> flowers) {
                adapter.setFlowers(flowers);
            }
        });


        adapter.setOnItemListener(new FlowerAdapter.Listener() {


            @Override
            public void itemClicked(View v, int position) {
                Flower flower=adapter.getFlowerAtPosition(position);
                launchUpdate(flower);
            }
        });
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
            String fName=data.getStringExtra(SecondActivity.EXTRA_REPLY_NAME);
            String fDate=data.getStringExtra(SecondActivity.EXTRA_REPLY_DATE);
            Flower new_flower=new Flower(fName,fDate);
            flowerViewModel.insert(new_flower);
        }else if (requestCode==UPDATE_WORD_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
            String data_name=data.getStringExtra(SecondActivity.EXTRA_REPLY_NAME);
            String data_date=data.getStringExtra(SecondActivity.EXTRA_REPLY_DATE);
            int id = data.getIntExtra(SecondActivity.EXTRA_REPLY_ID,-1);
            if(id!=-1 && data_name!=null){
               flowerViewModel.update(new Flower(id,data_name,data_date));}
            else if(id!=-1 && data_date!=null){
                Toast.makeText(this,"Unable to update,flower name is empty",Toast.LENGTH_LONG).show();
            }else{
                flowerViewModel.delete(new Flower(id,data_name,data_date));
            }
        }else{
            Toast.makeText(this,"Empty data , not saved",Toast.LENGTH_LONG).show();
        }
    }

    public void launchUpdate(Flower flower){
        Intent intent=new Intent(this,SecondActivity.class);
        intent.putExtra(EXTRA_DATA_UPDATE_NAME,flower.getFlowerName());
        intent.putExtra(EXTRA_DATA_UPDATE_DATE,flower.getDate());
        intent.putExtra(EXTRA_DATA_ID,flower.getId());
        startActivityForResult(intent,UPDATE_WORD_ACTIVITY_REQUEST_CODE);
    }
}