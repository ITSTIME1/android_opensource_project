package com.example.firebase_chat_basic.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

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


    // 여기서 되는 되네?
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 펄미션이 허용되었습ㄴ디ㅏ.
            Toast.makeText(this, "펄미션이 허용되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            // 만약 deny(거부)를 눌렀을 때 == true를 리턴
            // 즉 거부를 했다는 것으로 다시 요청
            Toast.makeText(this, "요청을 거부하셨습니다.", Toast.LENGTH_SHORT).show();
            if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
            } else {
                // 또 그냥 거부했을 떄 팝업창을 보여주지 않는다.
                showdialogToGetPermission();
            }
        }
    }

    private void showdialogToGetPermission(){
        Toast.makeText(this, "설정 메뉴로 이동하겠습니다.", Toast.LENGTH_SHORT).show();
    }
}
