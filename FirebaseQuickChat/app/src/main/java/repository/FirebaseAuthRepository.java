package repository;


import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * [Firebase Authentication Repository]
 */
public class FirebaseAuthRepository {
    private Application application;
    private FirebaseAuth firebaseAuth;
    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private MutableLiveData<Boolean> booleanMutableLiveData;


    public FirebaseAuthRepository(Application application) {
        this.application = application;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.userMutableLiveData = new MutableLiveData<>();
        this.booleanMutableLiveData = new MutableLiveData<>();


        /*
         * [FirebaseAuthRepository]
         *
         * 현재 정보가 있다면 userMutableLiveData 에 있는 값을 그대로 넘겨주고
         * 로그인 상태를 넘겨준다.
         */

        if(firebaseAuth.getCurrentUser() != null) {
            userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
            booleanMutableLiveData.postValue(false);
        }
    }

    /**
     * [Firebase Login Method]
     * @param email 이메일을 EditText 에서 입력된 값을 가져와서 넣어준다.
     * @param password 패스워드를 EditText 에서 입력된 값을 가져와서 넣어준다.
     */
    public void firebaseLoginProcess(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else {
                    Toast.makeText(application, "LoginFailure", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    /**
     * [Firebase Register Method]
     * @param email 회원가입 화면에서 EditText 에서 작성한 값을 받아서 가져온다.
     * @param password 회원가입 화면에서 EditText 에서 작성한 값을 받아서 가져온다.
     */

    public void firebaseRegisterProcess(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(application.getMainExecutor(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    userMutableLiveData.postValue(firebaseAuth.getCurrentUser());
                } else {
                    Toast.makeText(application, "RegisterFailure", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * [Firebase LogOut]
     */
    public void logOutProcess(){
        firebaseAuth.signOut();
        // 로그아웃 상태가 활성화 되었다고 알려준다.
        booleanMutableLiveData.postValue(true);
    }


    // @TODO 아래 메서드 설명 달기
    /**
     * Blow Method is currentUser data that's mean
     * @return 으로 userMutableLivedata 현재 값을 가져온다.
     */


    // 유저 정보를 가져오는 메서드
    public MutableLiveData<FirebaseUser> getUserMutableLiveData(){
        return userMutableLiveData;
    }

    // 유저의 정보가 로그인 되었는지 안되었는지
    public MutableLiveData<Boolean> getBooleanMutableLiveData(){
        return booleanMutableLiveData;
    }
}
