package viewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import repository.FirebaseAuthRepository;

public class FirebaseLoginViewModel extends AndroidViewModel {
    private FirebaseAuthRepository firebaseAuthRepository;
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;

    public FirebaseLoginViewModel(Application application) {
        super(application);
        firebaseAuthRepository = new FirebaseAuthRepository(application);
        firebaseUserMutableLiveData = firebaseAuthRepository.getUserMutableLiveData();
    }

    public void firebaseLogin(String email, String password){
        firebaseAuthRepository.firebaseLoginProcess(email, password);
    }

    public void firebaseRegister(String email, String password) {
        firebaseAuthRepository.firebaseRegisterProcess(email, password);
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }
}
