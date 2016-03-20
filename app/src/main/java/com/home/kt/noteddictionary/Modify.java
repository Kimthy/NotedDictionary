package com.home.kt.noteddictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Modify extends AppCompatActivity implements View.OnClickListener{

    private Button edit,delete,back;
    private EditText txtword,txtdefinition;
    private int id;
    private MyModify mycon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        mycon=new MyModify(this);
        mycon.openCon();

        Typeface myfont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Bold.ttf");
        Typeface myfont1=Typeface.createFromAsset(getAssets(),"fonts/Roboto-BlackItalic.ttf");


        txtword=(EditText)findViewById(R.id.txtword);
        txtdefinition=(EditText)findViewById(R.id.txtdefinition);
        edit=(Button)findViewById(R.id.btnedit);
        delete=(Button)findViewById(R.id.btndelete);
        back=(Button)findViewById(R.id.btnback);

        txtword.setTypeface(myfont);
        txtdefinition.setTypeface(myfont1);
        edit.setTypeface(myfont1);
        delete.setTypeface(myfont1);
        back.setTypeface(myfont1);

        edit.setOnClickListener(this);
        delete.setOnClickListener(this);
        back.setOnClickListener(this);

        Intent i=getIntent();
        String val_id=i.getStringExtra("id");
        String val_word=i.getStringExtra("word");
        String val_definition=i.getStringExtra("definition");
        id=Integer.parseInt(val_id);
        txtword.setText(val_word);
        txtdefinition.setText(val_definition);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnedit:
                String myword=txtword.getText().toString();
                String mydefinition=txtdefinition.getText().toString();
                mycon.updateDB(id,myword,mydefinition);
                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_LONG).show();
                gotoHome(); break;
            case R.id.btndelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteWord)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int idd) {
                                mycon.deleteDB(id);
                                gotoHome();
                                Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int idd) {
                                // User cancelled the dialog
                            }
                        });
                AlertDialog d = builder.create();
                d.setTitle("Comf");
                d.show();
                //mycon.deleteDB(id);
                //gotoHome(); break;
                break;
            case R.id.btnback:
                this.gotoHome(); break;
            default: break;
        }
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
                builder.setMessage(R.string.dev).setNegativeButton("Close", new DialogInterface.OnClickListener() {
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
                Intent localIntent=new Intent(Modify.this,HelpActivity.class);
                startActivity(localIntent);
                //Toast.makeText(this,"Help",Toast.LENGTH_LONG).show();
                break;
            default: break;
        }
        return true;
    }

    public void gotoHome(){
        Intent main_layout=new Intent(getApplicationContext(),MainActivity.class).setFlags
                (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(main_layout);
    }
}