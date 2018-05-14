package com.example.kerolis.parcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    static int balance = 0;
    EditText bal;
    Button next;
    Button edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        bal = (EditText) findViewById(R.id.bal);
        next= (Button) findViewById(R.id.next);
        edit= (Button) findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                balance = Integer.parseInt(bal.getText().toString());

                if (balance != 0&&!bal.getText().toString().isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, MyList.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}