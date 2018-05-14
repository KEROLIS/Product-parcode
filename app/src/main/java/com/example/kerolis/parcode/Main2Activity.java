package com.example.kerolis.parcode;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {


    final DataBaseHelper myDB = new DataBaseHelper(this);
    TextView tv;
    Button delAll;
    Button search;
    Button insert;
    EditText price;
    //Button deleteSelc;


    String scanContent;
    String scanFormat;
    boolean isInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insert = (Button) findViewById(R.id.insert);
        price = (EditText) findViewById(R.id.price);
        delAll = (Button) findViewById(R.id.delete);
        search = (Button) findViewById(R.id.in);

        // deleteSelc = (Button) findViewById(R.id.delet);


        tv = (TextView) findViewById(R.id.tv);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                StringBuffer buffer = new StringBuffer();
                if (res != null && res.getCount() > 0) {
                    while (res.moveToNext()) {
                        //     buffer.append("ID  " +res.getInt(0)+"\n");
                        buffer.append("Serial  " + res.getString(1) + "\n");
                        buffer.append("Price  " + res.getInt(2) + "\n");
                        tv.setText(buffer.toString());
                        Toast.makeText(Main2Activity.this, buffer.toString(), Toast.LENGTH_SHORT).show();


                    }


                } else
                    Toast.makeText(Main2Activity.this, "ops", Toast.LENGTH_SHORT).show();


            }
        });


        delAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDB.deleteAllData();
                Toast.makeText(Main2Activity.this, "All data deleted", Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scan();
                isInsert = false;
                if (scanContent != null) {


                }

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!price.getText().toString().isEmpty()) {
                        scan();
                        isInsert = true;

                    } else
                        Toast.makeText(Main2Activity.this, "fill the price frist!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(Main2Activity.this, "faild", Toast.LENGTH_SHORT).show();
                }
            }
        });
//        deleteSelc.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                myDB.deleteData(myDB.qsearch(price.getText().toString()) + "");
//                Toast.makeText(MainActivity.this, myDB.qsearch("id "+serial.getText().toString())+" deleted", Toast.LENGTH_SHORT).show();
//            }
//        });
    }


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            scanContent = scanningResult.getContents();
            scanFormat = scanningResult.getFormatName();
            if (scanContent != null) {
                if (isInsert) {


                    boolean result = myDB.inseartData(scanContent, Integer.parseInt(price.getText().toString()));
                    if (result) {
                        Toast.makeText(Main2Activity.this, "suc", Toast.LENGTH_SHORT).show();

                    } else
                        Toast.makeText(Main2Activity.this, "failed", Toast.LENGTH_SHORT).show();


                } else {
                    int pri = myDB.qsearch(scanContent);
                    scanContent = null;

                    if (pri != -1) {
                        Toast.makeText(Main2Activity.this, "found", Toast.LENGTH_SHORT).show();
                        tv.setText("price = " + pri);
                    } else
                        Toast.makeText(Main2Activity.this, "not found", Toast.LENGTH_SHORT).show();
                }


            }

            Toast.makeText(this, "FORMAT: " + scanFormat + "CONTENT: " + scanContent, Toast.LENGTH_LONG).show();

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void scan() {
        IntentIntegrator scanIntegrator = new IntentIntegrator(Main2Activity.this);
        scanIntegrator.initiateScan();
    }


}
