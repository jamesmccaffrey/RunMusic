package com.example.admin.runmusic;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import ir.sohreco.androidfilechooser.ExternalStorageNotAvailableException;
import ir.sohreco.androidfilechooser.FileChooserDialog;

public class MusicActivity extends AppCompatActivity implements FileChooserDialog.ChooserListener {
TextView pathView;
        EditText artist, song;
    String filePath;
    Button btnAdd;
    private MusicDBHelper musicDB;
    String speed, username;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        spinner=(Spinner)findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.music_speed,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        Button btnFile=(Button) findViewById(R.id.btnFile);
        pathView=(TextView) findViewById(R.id.textviewPath);
        final FileChooserDialog.Builder builder = new FileChooserDialog.Builder(FileChooserDialog.ChooserType.FILE_CHOOSER, this);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        musicDB=new MusicDBHelper(this);
        Intent get=getIntent();
        Bundle user=getIntent().getExtras();
        username=user.getString("user");
        artist=(EditText)findViewById(R.id.textArist);
        song=(EditText)findViewById(R.id.textSong);

        btnFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    builder.build().show(getSupportFragmentManager(), null);
                } catch (ExternalStorageNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add();
            }
        });


    }

    public void add() {
        speed=spinner.getSelectedItem().toString();
        String Artist=artist.getText().toString();
        String Song=song.getText().toString();

        if(musicDB.doesFileExist(filePath)==true){
            Toast.makeText(MusicActivity.this, "Track is already in Database", Toast.LENGTH_LONG).show();
            return;
        }
        try {
            musicDB.add(filePath, speed, Artist, Song);
            musicDB.close();
            Toast.makeText(MusicActivity.this, "Track added successfully", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(MusicActivity.this,MainActivity.class);
            intent.putExtra("user",username);
            startActivity(intent);
        }
        catch(Exception e){
            Toast.makeText(MusicActivity.this, "Error", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onSelect(String path) {
        filePath=path;
    pathView.setText(path);
    }
}
