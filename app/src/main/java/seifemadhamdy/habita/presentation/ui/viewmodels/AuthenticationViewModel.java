package seifemadhamdy.habita.presentation.ui.viewmodels;

import android.app.Activity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import java.util.Objects;
import seifemadhamdy.habita.domain.usecases.ValidateEmailUseCase;
import seifemadhamdy.habita.domain.usecases.ValidatePasswordUseCase;

public class AuthenticationViewModel extends ViewModel {
  private final ValidateEmailUseCase validateEmailUseCase;
  private final ValidatePasswordUseCase validatePasswordUseCase;
  private final MutableLiveData<String> email = new MutableLiveData<>();
  private final MutableLiveData<String> password = new MutableLiveData<>();
  private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
  private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
  private final MutableLiveData<FirebaseUser> firebaseUser = new MutableLiveData<>();

  public AuthenticationViewModel(
      ValidateEmailUseCase validateEmailUseCase, ValidatePasswordUseCase validatePasswordUseCase) {
    this.validateEmailUseCase = validateEmailUseCase;
    this.validatePasswordUseCase = validatePasswordUseCase;
  }

  public LiveData<String> getEmail() {
    return email;
  }

  public void setEmail(String value) {
    email.setValue(value);
  }

  public LiveData<String> getPassword() {
    return password;
  }

  public void setPassword(String value) {
    password.setValue(value);
  }

  public LiveData<FirebaseUser> getFirebaseUser() {
    return firebaseUser;
  }

  public LiveData<String> getErrorMessage() {
    return errorMessage;
  }

  public boolean validateEmail() {
    String emailValue = email.getValue();
    return emailValue != null && !emailValue.isEmpty() && validateEmailUseCase.execute(emailValue);
  }

  public boolean validatePassword() {
    String passwordValue = password.getValue();

    return passwordValue != null
        && !passwordValue.isEmpty()
        && validatePasswordUseCase.execute(passwordValue);
  }

  public void continueWithEmailAndPassword(Activity activity) {
    if (validateEmail() && validatePassword()) {
      firebaseAuth
          .signInWithEmailAndPassword(
              Objects.requireNonNull(email.getValue()), Objects.requireNonNull(password.getValue()))
          .addOnCompleteListener(
              activity,
              task -> {
                if (task.isSuccessful()) {
                  firebaseUser.setValue(firebaseAuth.getCurrentUser());
                } else {
                  handleAuthenticationError(task.getException(), activity);
                }
              });
    } else {
      errorMessage.setValue("Invalid email or password");
    }
  }

  private String getErrorMessageFromException(Exception exception) {
    return exception != null ? exception.getMessage() : "Unknown error";
  }

  private void createUser(Activity activity) {
    firebaseAuth
        .createUserWithEmailAndPassword(
            Objects.requireNonNull(email.getValue()), Objects.requireNonNull(password.getValue()))
        .addOnCompleteListener(
            activity,
            task -> {
              if (task.isSuccessful()) {
                firebaseUser.setValue(firebaseAuth.getCurrentUser());
              } else {
                errorMessage.setValue(getErrorMessageFromException(task.getException()));
              }
            });
  }

  private void handleAuthenticationError(Exception exception, Activity activity) {
    if (exception instanceof FirebaseAuthInvalidCredentialsException) {
      createUser(activity);
    } else {
      errorMessage.setValue(getErrorMessageFromException(exception));
    }
  }
}
