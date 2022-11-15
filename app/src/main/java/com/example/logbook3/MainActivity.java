package com.example.logbook3;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    EditText addLink_txt;
    Handler mainHandler = new Handler();
    DataBaseHandler myDB;
    ArrayList<String> id, url_img;
    Button backward_button, forward_button, add_link_button, clear_link_button;
    ArrayList<String> arrayList;
    int index;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        backward_button = findViewById(R.id.backward_button);
        forward_button = findViewById(R.id.forward_button);
        myDB = new DataBaseHandler(MainActivity.this);
        id = new ArrayList<>();
        url_img = new ArrayList<>();
        displayData();
        add_link_button = findViewById(R.id.add_link_button);
        addLink_txt = findViewById(R.id.addLink_txt);

        add_link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                arrayList.add(addLink_txt.getText().toString().trim());
                Glide.with(getApplicationContext())
                        .load(loadLastImg())
                        .placeholder(R.drawable.ic_baseline_image_24)
                        .into(imageView);

                Toast.makeText(MainActivity.this, "Add Successfully!!!", Toast.LENGTH_SHORT).show();
                DataBaseHandler myDB  = new DataBaseHandler(MainActivity.this);
                myDB.addImage(addLink_txt.getText().toString().trim());

            }
        });

        clear_link_button = findViewById(R.id.clear_link_button);

        clear_link_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addLink_txt.setText("");

            }
        });

        arrayList = new ArrayList<>();

        arrayList.add(0,"https://wallpaperaccess.com/full/2500093.jpg");
        arrayList.add(1,"https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/312536512_821678679034124_7381462594049441236_n.jpg?stp=dst-jpg_p843x403&_nc_cat=109&ccb=1-7&_nc_sid=5cd70e&_nc_ohc=61lTG468xDIAX_rzE6i&_nc_ht=scontent.fsgn5-8.fna&oh=00_AfCISnhLgFu95QkRML2pNpXiNI860PFKcgXrGlq-4HWAeQ&oe=63654E85");
        arrayList.add(2,"https://scontent.fdad3-4.fna.fbcdn.net/v/t39.30808-6/312089766_1859308337794883_8638998171115307785_n.jpg?stp=dst-jpg_p843x403&_nc_cat=1&ccb=1-7&_nc_sid=730e14&_nc_ohc=VePkZyXwAtkAX9tcipD&_nc_ht=scontent.fdad3-4.fna&oh=00_AfBgqsfoMOVnl9pUBs_Jf4357Fc1t2kqJaMtMG-BElHUiA&oe=6365AB3D");

        Glide.with(getApplicationContext())
                .load(loadLastImg())
                .placeholder(R.drawable.ic_baseline_image_24).into(imageView);


        backward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext())
                        .load(backward_button())
                        .placeholder(R.drawable.ic_baseline_image_24).into(imageView);
            }
        });

        forward_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Glide.with(getApplicationContext())
                        .load(forward_button())
                        .placeholder(R.drawable.ic_baseline_image_24).into(imageView);
            }
        });
    }

    // db
    void displayData() {
        Cursor cursor = myDB.readAllData();
        if(cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                url_img.add(cursor.getString(1));
            }
        }
    }

    String loadLastImg(){
        url = arrayList.get(arrayList.size() - 1 );
        return url;
    }

    String forward_button(){
        index = arrayList.indexOf(url);
        if(index == (arrayList.size()-1)){
            url = arrayList.get(0);
        } else {
            index++;
            url = arrayList.get(index);
        }
        return url;
    }

    String backward_button(){
        index = arrayList.indexOf(url);
        if(index == 0){
            url = arrayList.get(arrayList.size()-1);
        } else {
            index--;
            url = arrayList.get(index);
        }
        return url;
    }
}