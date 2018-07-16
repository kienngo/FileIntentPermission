package com.example.admin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class IntentActivity extends AppCompatActivity {
    private final String CONTENT_SEND = "This is my text to send.";
    private final String SETUP_TYPE_INTENT = "text/plain";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        Button buttonSend = (Button) findViewById(R.id.button_sendintent);
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, CONTENT_SEND);
                sendIntent.setType(SETUP_TYPE_INTENT);
                startActivity(sendIntent);
            }
        });
    }
}
