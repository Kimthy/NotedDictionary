package com.home.kt.noteddictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class ViewWord extends AppCompatActivity {

    private TextToSpeech textToSpeech;
    private TextView txtword,txtdefinition;
    private MyModify mycon;
    private ImageView listent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_word);

        mycon=new MyModify(this);
        mycon.openCon();

        Typeface myfont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Bold.ttf");
        Typeface myfont1=Typeface.createFromAsset(getAssets(),"fonts/Roboto-BlackItalic.ttf");

        textToSpeech=new TextToSpeech(getApplicationContext(),new TextToSpeech.OnInitListener(){

            @Override
            public void onInit(int status) {
                if(status !=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.UK);
                }

            }
        });

        txtword=(TextView)findViewById(R.id.txtword);
        txtdefinition=(TextView)findViewById(R.id.txtdefinition);
        txtword.setTypeface(myfont);
        txtdefinition.setTypeface(myfont1);

        listent=(ImageView)findViewById(R.id.imageButton);
        listent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toSpeak=txtword.getText().toString();
                textToSpeech.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
            }
        });

        Intent i=getIntent();
        String val_word=i.getStringExtra("word");
        String val_definition=i.getStringExtra("definition");
        txtword.setText(val_word);
        txtdefinition.setText(val_definition);


        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.show();
        actionBar.setTitle("Noted Dictionary");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_about :
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.about).setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int idd) {
                        // User cancelled the dialog
                        dialog.cancel();
                        //dialog.dismiss();
                    }
                });
                AlertDialog d = builder.create();
                d.setTitle(R.string.setTitle);
                d.show();
                /*==========Change font dialog==============*/
                TextView textView = (TextView) d.findViewById(android.R.id.message);
                textView.setTextSize(16);
                textView.setTextColor(Color.BLUE);
                //textView.setGravity(Gravity.CENTER);
                //Toast.makeText(this,"KT",Toast.LENGTH_LONG).show();
                break;
            case R.id.action_help :
                Intent localIntent=new Intent(ViewWord.this,HelpActivity.class);
                startActivity(localIntent);
                //Toast.makeText(this,"Help",Toast.LENGTH_LONG).show();
                break;
            default: break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }
}