package com.example.kerolis.parcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.kerolis.parcode.MyList.serilas;

public class MyList extends AppCompatActivity {
    DataBaseHelper myDB = new DataBaseHelper(this);
    int qbalance;
    int total = 0;
    ListView lv;
    static ArrayList<String> serilas = new ArrayList<>();
    int pri;
    TextView totl;
    TextView myBalance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);
        qbalance = MainActivity.balance;
        myBalance= (TextView) findViewById(R.id.qbalance);
        myBalance.setText("Balance = "+qbalance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total < qbalance)
                    scan();
                else
                    new AlertDialog.Builder(MyList.this)
                            .setMessage("You dont have enough money to contenu")
                            .setPositiveButton("OK", null).show();


            }
        });
    }

    public void scan() {
        IntentIntegrator scanIntegrator = new IntentIntegrator(this);
        scanIntegrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {

            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            if (scanContent != null) {
                pri = myDB.qsearch(scanContent);
                total+=pri;
                if (pri != -1&&total < qbalance) {
                    Toast.makeText(MyList.this, "added", Toast.LENGTH_SHORT).show();
                    serilas.add(scanContent);
                    totl= (TextView) findViewById(R.id.total);
                    totl.setText("Total = "+total);
                    lv = (ListView) findViewById(R.id.myList);
                    MyAdapter adapter = new MyAdapter(this, serilas, myDB);
                    lv.setAdapter(adapter);


                } else if (total>=qbalance){
                    new AlertDialog.Builder(MyList.this)
                            .setMessage("You dont have enough money to add more")
                            .setPositiveButton("OK", null).show();
                    total-=pri;

                }
                else Toast.makeText(this, "not found !", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

class MyAdapter extends ArrayAdapter {
    DataBaseHelper myDB;


    public MyAdapter(Context context, ArrayList<String> seriaList, DataBaseHelper myDB) {
        super(context, R.layout.example_cuslistview_row, R.id.serial, seriaList);
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.example_cuslistview_row, parent, false);
        TextView serial = (TextView) row.findViewById(R.id.serial);
        TextView price = (TextView) row.findViewById(R.id.price);
        serial.setText(serilas.get(position));
        price.setText("price = "+myDB.qsearch(serilas.get(position)) );


        return row;
    }

}
