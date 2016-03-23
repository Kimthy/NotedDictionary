package com.home.kt.noteddictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Add extends AppCompatActivity implements View.OnClickListener{

    private Button save,back;
    private MyModify mycon;
    private EditText txtword,txtdefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Typeface myfont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Bold.ttf");
        Typeface myfont1=Typeface.createFromAsset(getAssets(),"fonts/Roboto-BlackItalic.ttf");

        txtword=(EditText)findViewById(R.id.txtword);
        txtdefinition=(EditText)findViewById(R.id.txtdefinition);
        back=(Button)findViewById(R.id.btnback);
        save=(Button)findViewById(R.id.btnsave);

        txtword.setTypeface(myfont);
        txtdefinition.setTypeface(myfont1);
        back.setTypeface(myfont1);
        save.setTypeface(myfont1);

        save.setOnClickListener(this);
        back.setOnClickListener(this);

        mycon=new MyModify(this);
        mycon.openCon();

        android.support.v7.app.ActionBar actionBar=getSupportActionBar();
        actionBar.show();
        actionBar.setTitle("Noted Dictionary");
        //actionBar.setDisplayHomeAsUpEnabled(true);
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
                Intent localIntent=new Intent(Add.this,HelpActivity.class);
                startActivity(localIntent);
                //Toast.makeText(this,"Help",Toast.LENGTH_LONG).show();
                break;
            default: break;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnsave:
                String word=txtword.getText().toString();
                String definition=txtdefinition.getText().toString();
                if(word.length()>0 && definition.length()>0) {
                    mycon.insertDB(word, definition);
                    Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_LONG).show();
                    this.gotoHome();
                }else {
                    Toast.makeText(this,"Your words or definitions are empty",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnback:
                this.gotoHome(); break;
            default: break;
        }
    }

    public void gotoHome(){
        Intent main_layout=new Intent(this,MainActivity.class).setFlags
                (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main_layout);
    }
}