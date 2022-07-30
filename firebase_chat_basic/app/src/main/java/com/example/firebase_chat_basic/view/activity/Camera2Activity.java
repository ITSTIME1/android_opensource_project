package com.example.firebase_chat_basic.view.activity;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;

import com.example.firebase_chat_basic.BuildConfig;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.ActivityCameraBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * [Camera2Activity]
 *
 * if user granted success camera activity open
 * I used to basic camera (no cameraX, camera2)
 */


// @TODO 사진을 찍은 다음 그 사진을 확인을 눌렀을 때 전송이 되는 로직
// @TODO 사진 선택후 채팅으로 보낼 수 있는 로직 추가
// @TODO 동영상 선택후 채팅으로 보낼 수 있는 로직 추가


public class Camera2Activity extends AppCompatActivity {
    private ActivityCameraBinding activityCameraBinding;
    private final int REQUEST_WIDTH = 512;
    private final int REQUEST_HEIGHT = 512;
    private String photoPath;
    private Uri photoURI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCameraBinding = DataBindingUtil.setContentView(this, R.layout.activity_camera);
        // 권한 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // 카메라 실행
            Toast.makeText(this, "카메라가 실행 되었습니다.", Toast.LENGTH_SHORT).show();
            cameraLaunch();
            Log.d("첫 시작 카메라", "");


        } else {
            // 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
        imageSend();
    }

    // fragment 에서는 onRequestPermissionResult 가 deprecated 되어있음
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // 다시 권한을 물어 봤을 때 허용을 눌렀다면 카메라를 실행.
            cameraLaunch();
            Toast.makeText(this, "펄미션이 허용되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            // 만약 deny(거부)를 눌렀을 때 == true를 리턴
            // 즉 거부를 했다는 것으로 다시 요청
            Toast.makeText(this, "요청을 거부하셨습니다.", Toast.LENGTH_SHORT).show();
            if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // 요청을 거부 했을 때는 다이얼로그로 메세지를 띄어주고 왜 권한이 필요한지 명시해주자
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("카메라 접근 권한").setMessage("카메라 권한을 거부하시면 카메라를 사용할 수 없습니다.");
                builder.setPositiveButton(R.string.setting_string_accept, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // 허용하면 카메라를 열어준다.
                        cameraLaunch();
                        Toast.makeText(Camera2Activity.this, "허용 했습니다.", Toast.LENGTH_SHORT).show();
                    }
                }).setNegativeButton(R.string.setting_string_deny, new DialogInterface.OnClickListener() {
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


    // move to setting of application
    private void goToSetting() {
        startActivity(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:" + BuildConfig.APPLICATION_ID)));
        Toast.makeText(this, "설정 메뉴로 이동하겠습니다.", Toast.LENGTH_SHORT).show();
    }

    // cameraLaunch
    private void cameraLaunch() {
        Log.d("카메라 런처 시작", "");
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(cameraIntent.resolveActivity(getPackageManager()) != null) {
            // photoFile의 주소를 담을 변수.
            File photoFile = null;
            // scope variable
            Log.d("photoFile", String.valueOf(photoFile));
            try {
                // 빈 파일 생성.
                Log.d("try-catch 시작", "");
                File tempDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                File tempImage = File.createTempFile(
                        "JPEG_" + timeStamp + "_",  /* 파일이름 */
                        ".jpg",         /* 파일형식 */
                        tempDir      /* 경로 */
                );
                photoFile = tempImage;
                photoPath = tempImage.getAbsolutePath();
                // 여기까지 빈파일 잘 가지고 있고.
                Log.d("빈파일 생성 확인", String.valueOf(photoFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 생성된 File로부터 uri 생성.
            //URI 형식 EX) content://com.example.firebase_chat_basic.fileprovider/cameraImg/Pictures/JPEG_20211124_202832_6573897384086993610.jpg
            if(photoFile != null) {
                photoURI = FileProvider.getUriForFile(Camera2Activity.this, BuildConfig.APPLICATION_ID + ".fileprovider", photoFile);
                Log.d("photoURI ", String.valueOf(photoURI));
                // 인텐트에 photoURI를 같이 넘겨준다.
            }
            cameraActivityLauncher.launch(cameraIntent);

        }

    }
    // camera get data from camera
    ActivityResultLauncher<Intent> cameraActivityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                // 여기서 이제 photoURI를 받아오면 되는데
                // File photouri
                Log.d("result ok", "");
                File file = new File(photoPath);
                Log.d("file path", String.valueOf(file));
                Bitmap rotatedBitmap = null;
                try {
                    // 가져온 photoFile uri 주소를 bit map에 넣어주고
                    // 비트맵을 잘 못 가지고 오는것 같다.
                    // @TODO 여기를 고쳐야 될 것 같다.
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(file));

                    // 여기서 null 값이 들어온다.
                    Log.d("bitmap ", String.valueOf(bitmap));
                    if (bitmap != null) {
                        // 이미지 정보 추출 클래스 객체 생성.
                        ExifInterface ei = null;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                            ei = new ExifInterface(file);
                        }
                        int orientation = Objects.requireNonNull(ei).getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        rotatedBitmap = null;
                        switch (orientation) {
                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(bitmap, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(bitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(bitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = bitmap;
                        }
                        // imageView에 저장.

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(rotatedBitmap != null) {
                    activityCameraBinding.cameraImagePreview.setImageBitmap(rotatedBitmap);
                }
                Log.d("성공", "");
            } else {
                Log.d("실패", "");
            }
        }});

    // send image
    // @TODO 사진을 고화질로 가지고오자.
    public void imageSend(){
        final View applyBtn = activityCameraBinding.functionHeaderId.findViewById(R.id.apply_picture);
        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("적용 버튼", "");
            }
        });
    }


    // get image from when user click "check" in camera
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
