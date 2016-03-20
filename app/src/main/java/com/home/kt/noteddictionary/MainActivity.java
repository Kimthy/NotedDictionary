package com.home.kt.noteddictionary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button add;
    private EditText et_search;
    private ListView lst;
    private MyModify mycon;
    private ImageView imgv;
    private TextView tv_id,tv_word,tv_definition;
    private String searchText;
    //private int backButtonCount=0;
    boolean doubleBackToExitPressOne=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface myfont=Typeface.createFromAsset(getAssets(),"fonts/Roboto-Bold.ttf");
        Typeface myfont1=Typeface.createFromAsset(getAssets(),"fonts/Roboto-BlackItalic.ttf");

        mycon=new MyModify(this);
        mycon.openCon();

        add=(Button)findViewById(R.id.btnadd);
        add.setTypeface(myfont1);

        add.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent add_layout=new Intent(MainActivity.this,Add.class);
                startActivity(add_layout);
            }
        });

        lst=(ListView)findViewById(R.id.listview);

        //Read data from Database
        Cursor cursor=mycon.readDB();
        String []from=new String[]{     MySQLiteOpenHelper.col_id,
                                        MySQLiteOpenHelper.col_word,
                                        MySQLiteOpenHelper.col_definition
        };
        int []to=new int[]{     R.id.txt_id,
                                R.id.txt_word,
                                R.id.txt_definition
        };

        SimpleCursorAdapter adapter=new SimpleCursorAdapter(
                MainActivity.this,R.layout.view_layout,cursor,from,to);
        //Refresh data in listview
        adapter.notifyDataSetChanged();
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tv_id = (TextView) view.findViewById(R.id.txt_id);
                tv_word = (TextView) view.findViewById(R.id.txt_word);
                tv_definition = (TextView) view.findViewById(R.id.txt_definition);

                String id_value = tv_id.getText().toString();
                String word_value = tv_word.getText().toString();
                String definition_value = tv_definition.getText().toString();

                Intent modify_layout = new Intent(getApplicationContext(), ViewWord.class);
                modify_layout.putExtra("id", id_value);
                modify_layout.putExtra("word", word_value);
                modify_layout.putExtra("definition", definition_value);
                startActivity(modify_layout);
            }
        });

        lst.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                tv_id = (TextView) view.findViewById(R.id.txt_id);
                tv_word = (TextView) view.findViewById(R.id.txt_word);
                tv_definition = (TextView) view.findViewById(R.id.txt_definition);

                String id_value = tv_id.getText().toString();
                String word_value = tv_word.getText().toString();
                String definition_value = tv_definition.getText().toString();

                Intent modify_layout = new Intent(getApplicationContext(), Modify.class);
                modify_layout.putExtra("id", id_value);
                modify_layout.putExtra("word", word_value);
                modify_layout.putExtra("definition", definition_value);
                startActivity(modify_layout);
                return true;
            }
        });

        et_search=(EditText)findViewById(R.id.txtsearch);
        et_search.setTypeface(myfont);
        imgv=(ImageView)findViewById(R.id.imgClear);
        imgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_search.setText("");
            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchText = et_search.getText().toString();

                Cursor cursor1 = mycon.readDBByWord(searchText);

                String[] from1 = new String[]{
                        MySQLiteOpenHelper.col_id,
                        MySQLiteOpenHelper.col_word,
                        MySQLiteOpenHelper.col_definition
                };
                int[] to1 = new int[]{
                        R.id.txt_id,
                        R.id.txt_word,
                        R.id.txt_definition
                };

                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        MainActivity.this, R.layout.view_layout, cursor1, from1, to1);
                //Refresh data in listview
                adapter.notifyDataSetChanged();
                lst.setAdapter(adapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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
                Intent localIntent=new Intent(MainActivity.this,HelpActivity.class);
                startActivity(localIntent);

                //Toast.makeText(this,"Help",Toast.LENGTH_LONG).show();
                break;
            default: break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if(doubleBackToExitPressOne){
            super.onBackPressed();
            //finish();
            return;
        }

        this.doubleBackToExitPressOne=true;
        Toast.makeText(this,"Please, press back again to exit.",Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressOne=false;
            }
        },2000);
        /*if (backButtonCount >= 1){
            Intent intent=new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            super.onBackPressed();
        }else{
            Toast.makeText(this,"Press the back button once again to close the application",Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }*/
    }
}