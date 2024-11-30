package seifemadhamdy.habita.domain.usecases;

import android.text.TextUtils;
import android.util.Patterns;

public class ValidateEmailUseCase {
  public boolean execute(String email) {
    return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
  }
}
