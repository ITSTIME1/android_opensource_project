package com.example.intentspractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    // 데이터 전달을 위한 유니크한 키값
    public static final String EXTRA_MESSAGE =
            "com.example.android.twoactivities.extra.MESSAGE";

    // EditText에서 값을 받아와야 되기 때문에 설정해둔 변수.
    private EditText mMessageEditText;
    public static final int TEXT_REQUEST = 1;
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 앱이 실행되고 onCreate() 메서드가 라이프사이클에 의해서 onCreate()함수가 최초 실행되는데
        // 이때 미리 변수를 설정해 두었던 곳에 findViewByid를 통해서 값을 전달한다.
        mMessageEditText = findViewById(R.id.editText_main);
        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);
    }


    // Move On SecondActivity
    public void launchSecondaryActivity(View view) {

        // when this method excuted move on SecondActivityClass
        Intent secondIntent = new Intent(this, SecondActivity.class);
        String message = mMessageEditText.getText().toString();
        secondIntent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(secondIntent, TEXT_REQUEST);
        Log.d(LOG_TAG, "Send Button Clicked");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Test for the right intent reply.
        if (requestCode == TEXT_REQUEST) {
            // Test to make sure the intent reply result was good.
            if (resultCode == RESULT_OK) {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);

                // Make the reply head visible.
                mReplyHeadTextView.setVisibility(View.VISIBLE);

                // Set the reply and make it visible.
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}