package com.example.intentspractice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.twoactivities.extra.REPLY";
    private EditText mReply;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_second);
        // Intent를 받겠다.
        Intent intent = getIntent();
        // message 라는 변수에다가 받아온 Intent의 데이터 값을 getStringExtra로 받는데 어떤 값으로 가져오냐
        // 이전에 MainActivity에 설정해 두었던 putExtra()로 고유의 키 값인 EXTRA_MESSAGE로 설정해두었던 것을 전달 받겠다. String 형태로.
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        // 받아온 Intent Data 값을 화면에 표시해주기 위해서 미리 만들어 두었던 TextView의 아이디 값을 가져와서.
        TextView textView = findViewById(R.id.text_message);

        // 참조했던 textView에다가 setText()를 사용해서 위에서 getStringExtra 메소드로 넘겨받았던 EXTRA_MESSAGE의 키값에 해당하는 Value 값을 message에 받아서
        // 참조했던 textView 화면에 뿌려준다.
        textView.setText(message);


        // SecondActivity 화면에서 editText를 이용해서 다시 값을 전달하기 위해서
        // edit_text값의 고유 id를 연결해준다.
        mReply = findViewById(R.id.editText_second);

    }

    // Second 버튼을 클릭하면 아래 로직대로 진행이되는데
    // reply 변수에 미리 참조해두었던 EditText에 입력된 텍스트 값을 getText()값으로 가져오고 그 값을 .toString()해서 reply 저장해 주겠다.
    // 데이터를 전달해주기 위해서 Intent 객체를 새로 생성해준다.
    // putExtra를 사용해서 키 값으로 EXTRA_REPLY 값을 value 값으로는 받았던 텍스트 값을 넣어준다.
    // 결과값을 반환해주기 위해서 result code, 반환되는 intent를 넘겨준다.

    public void returnReply(View view) {
        String reply = mReply.getText().toString();
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        setResult(RESULT_OK, replyIntent);
        finish();
    }


}
