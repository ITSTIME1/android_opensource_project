package com.example.firebase_chat_basic.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.example.firebase_chat_basic.BuildConfig;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityCameraBinding;

/**
 * [Camera2Activity]
 *
 * if user granted success camera activity open
 */
public class Camera2Activity extends AppCompatActivity {
    private ActivityCameraBinding activityCameraBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCameraBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
        // 권한 요청
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 카메라 실행
            Toast.makeText(this, "카메라가 실행 되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            // 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    // fragment 에서는 onRequestPermissionResult 가 deprecated 되어있음
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 다시 권한을 물어 봤을 때 허용을 눌렀다면 카메라를 실행.
            Toast.makeText(this, "펄미션이 허용되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            // 만약 deny(거부)를 눌렀을 때 == true를 리턴
            // 즉 거부를 했다는 것으로 다시 요청
            Toast.makeText(this, "요청을 거부하셨습니다.", Toast.LENGTH_SHORT).show();
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // 요청을 거부 했을 때는 다이얼로그로 메세지를 띄어주고 왜 권한이 필요한지 명시해주자
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("카메라 접근 권한").setMessage("카메라 권한을 거부하시면 카메라를 사용할 수 없습니다.");
                builder.setPositiveButton(R.string.setting_string_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 허용하면 카메라를 열어준다.
                        Toast.makeText(Camera2Activity.this, "허용 했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }). setNegativeButton(R.string.setting_string_deny, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 거부한다면 다시 한번 물어본다.
                        ActivityCompat.requestPermissions(Camera2Activity.this, new String[]{Manifest.permission.CAMERA}, 100);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                // 그리고 나서 다시 시간이 좀 지나고 난 뒤에 리퀘스트 펄미션을 보내준다.
                // 그리고 나서 또 한번 거부했을 경우.

            } else {
                // 또 그냥 거부했을 떄 팝업창을 보여주지 않는다.
                // 그렇기에 설정 창으로 이동해서 권한을 허용시키도록 한다.
                goToSetting();
                // 또 한번 요청이 거부되어 지면 설정 메뉴로 이동한다..
            }
        }
    }


    // setting 메뉴로 이동
    private void goToSetting(){
        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
        Toast.makeText(this, "설정 메뉴로 이동하겠습니다.", Toast.LENGTH_SHORT).show();
    }
}
