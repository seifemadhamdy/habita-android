package seifemadhamdy.habita.domain.usecases;

import java.util.regex.Pattern;

public class ValidatePasswordUseCase {
  public boolean execute(String password) {
    final String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
    return Pattern.compile(passwordPattern).matcher(password).matches();
  }
}
