package com.example.firebase_chat_basic.viewModel;
import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.firebase_chat_basic.constants.Constants;
import com.example.firebase_chat_basic.model.UserModel;
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
 * <Topic>
 *
 *     1.
 *     The class is "ViewModel Class" for RegisterActivity.
 *     This includes "RealTimeDatabase", "MutableLiveData", "MutableLivedata<List<String>>".
 *
 *     2.
 *     If client is push "Register Button" and then get data from editText.getText() in "activity_register.xml".
 *     and then data is insert to "RealTimeDatabase" after going thorough a lot of conditions.
 *
 *     3.
 *     If the process all succeeded, In the RegisterActivity, through observer can data observe so then insert the data in the "registerIntent".
 *
 * </Topic>
 *
 * */

public class RegisterViewModel extends AndroidViewModel {
    // firebaseDatabase instance
    private final DatabaseReference databaseReference;
    private final FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;


    // two-way dataBinding
    private final ArrayList<UserModel> stringArrayList;
    public MutableLiveData<ArrayList<UserModel>> getDataList = new MutableLiveData<>();
    public MutableLiveData<String> getRegister_first_name,
            getRegister_second_name,
            getRegister_email,
            getRegister_password,
            getRegister_phone_number;


    // shared preference
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;


    // data into firebase authentication
    private String currentUserUID,
            check_profile_image,
            check_profile_background_image,
            check_sum_name,
            check_phone_number;

    private final String check_state_message = "Default";
    private boolean onlineState = true;


    // registerViewModel constructor
    public RegisterViewModel(Application application) {
        super(application);
        // essential init
        Application context = getApplication();
        preferences = context.getSharedPreferences("authentication", Activity.MODE_PRIVATE);
        editor = preferences.edit();
        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.real_time_database_root_url);
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

        // image need to use glide
        if(check_profile_image == null) check_profile_image = "Default";
        if(check_profile_background_image == null) check_profile_background_image = "Default";

        // firebase authentication
        firebaseRegister(check_email, check_password);
    }



    // firebase authentication
    public void firebaseRegister(String check_email, String check_password) {
        firebaseAuth.createUserWithEmailAndPassword(check_email, check_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    onlineState = true;
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
                    databaseReference.child("users").child(currentUserUID).child("online").setValue(onlineState);
                    databaseReference.child("users").child(currentUserUID).child("backgroundImage").setValue(check_profile_background_image);
                    databaseReference.child("users").child(currentUserUID).child("stateMessage").setValue(check_state_message);

                    // user model
                    stringArrayList.add(new UserModel(
                            currentUserUID,
                            check_sum_name,
                            check_email,
                            check_phone_number,
                            check_profile_image,
                            check_profile_background_image,
                            String.valueOf(onlineState),
                            check_state_message));

                    getDataList.setValue(stringArrayList);

                    editor.putString("authentication_uid", currentUserUID);
                    editor.putString("authentication_name", check_sum_name);
                    editor.putString("authentication_email", check_email);
                    editor.putString("authentication_check_profile_image", check_profile_image);
                    editor.putString("authentication_phone_number", check_phone_number);
                    editor.putString("authentication_online_state", String.valueOf(onlineState));
                    editor.putString("authentication_profile_background_image", check_profile_background_image);
                    editor.putString("authentication_state_message", check_state_message);
                    editor.commit();

                    Log.d("authenticationUID", preferences.getString("authenticationUID", ""));

                } else {
                    System.out.println("값이 성공적으로 저장이 되지 않았어요");
                }
            }
        });
    }
}
