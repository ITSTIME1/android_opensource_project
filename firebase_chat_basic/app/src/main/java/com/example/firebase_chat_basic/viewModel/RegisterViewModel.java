package com.example.firebase_chat_basic.viewModel;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;

/**
 * [RegisterViewModel]
 *
 * 1.
 * The class is "ViewModel Class" for RegisterActivity.
 * This includes "RealTimeDatabase", "MutableLiveData", "MutableLivedata<List<String>>".
 *
 * 2.
 * If client is push "Register Button" and then get data from editText.getText() in "activity_register.xml".
 * and then data is insert to "RealTimeDatabase" after going thorough a lot of conditions.
 *
 * 3.
 * If the process all succeeded, In the RegisterActivity, through observer can data observe so then insert the data in the "registerIntent".
 * */

public class RegisterViewModel extends AndroidViewModel {

    // firebase "realTimeDataBase" url
    private static final String realTimeDataBaseUserUrl = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";


    // firebaseDatabase instance
    private final DatabaseReference databaseReference;
    private final FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    // two-way dataBinding
    private final ArrayList<String> stringArrayList;
    public MutableLiveData<ArrayList<String>> getDataList = new MutableLiveData<>();
    public MutableLiveData<String> getRegister_first_name;
    public MutableLiveData<String> getRegister_second_name;
    public MutableLiveData<String> getRegister_email;
    public MutableLiveData<String> getRegister_password;
    public MutableLiveData<String> getRegister_phone_number;


    // shared preference
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;


    // data into firebase authentication
    private String currentUserUID;
    private String check_profile_image;
    private String check_sum_name;
    private String check_phone_number;


    // registerViewModel constructor
    public RegisterViewModel(Application application) {
        super(application);
        // essential init
        Application context = getApplication();
        preferences = context.getSharedPreferences("authentication", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(realTimeDataBaseUserUrl);
        firebaseAuth = FirebaseAuth.getInstance();

        // data init
        stringArrayList = new ArrayList<>();
        getRegister_first_name = new MutableLiveData<>();
        getRegister_second_name= new MutableLiveData<>();
        getRegister_email = new MutableLiveData<>();
        getRegister_password = new MutableLiveData<>();
        getRegister_phone_number = new MutableLiveData<>();
    }

    // register button
    public void registerButton(){

        // get data from EditText
        String check_first_name = getRegister_first_name.getValue();
        String check_second_name = getRegister_second_name.getValue();
        String check_email = getRegister_email.getValue();
        String check_password = getRegister_password.getValue();

        check_phone_number = getRegister_phone_number.getValue();
        check_sum_name = check_first_name + check_second_name;
        if(check_profile_image == null) check_profile_image = "Default";

        // firebase authentication
        firebaseRegister(check_email, check_password);
    }



    // firebase authentication
    public void firebaseRegister(String check_email, String check_password) {
        firebaseAuth.createUserWithEmailAndPassword(check_email, check_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {

                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if(firebaseUser != null ) {
                        currentUserUID = firebaseUser.getUid();
                        Log.d("currentUserUID", currentUserUID);
                    }
                    System.out.println("성공");

                    String finalCheckProfileImage = check_profile_image;



                    // realtime database save.
                    // uid, name, email, profileImage, phoneNumber
                    databaseReference.child("users").child(currentUserUID).child("uid").setValue(currentUserUID);
                    databaseReference.child("users").child(currentUserUID).child("name").setValue(check_sum_name);
                    databaseReference.child("users").child(currentUserUID).child("email").setValue(check_email);
                    databaseReference.child("users").child(currentUserUID).child("profileImage").setValue(finalCheckProfileImage);
                    databaseReference.child("users").child(currentUserUID).child("phoneNumber").setValue(check_phone_number);




                    // mutable livedata
                    stringArrayList.add(currentUserUID);
                    stringArrayList.add(check_sum_name);
                    stringArrayList.add(check_email);
                    stringArrayList.add(check_profile_image);
                    stringArrayList.add(check_phone_number);

                    getDataList.setValue(stringArrayList);

                    editor.putString("authentication_uid", currentUserUID);
                    editor.putString("authentication_name", check_sum_name);
                    editor.putString("authentication_email", check_email);
                    editor.putString("authentication_check_profile_image", check_profile_image);
                    editor.putString("authentication_phone_number", check_phone_number);
                    editor.commit();

                    Log.d("authenticationUID", preferences.getString("authenticationUID", ""));


                } else {
                    System.out.println("값이 성공적으로 저장이 되지 않았어요");
                }
            }
        });
    }
}
