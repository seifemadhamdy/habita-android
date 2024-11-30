package seifemadhamdy.habita.presentation.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import seifemadhamdy.habita.domain.usecases.ValidateEmailUseCase;
import seifemadhamdy.habita.domain.usecases.ValidatePasswordUseCase;

public class AuthenticationViewModelFactory implements ViewModelProvider.Factory {
  private final ValidateEmailUseCase validateEmailUseCase;
  private final ValidatePasswordUseCase validatePasswordUseCase;

  public AuthenticationViewModelFactory(
      ValidateEmailUseCase validateEmailUseCase, ValidatePasswordUseCase validatePasswordUseCase) {
    this.validateEmailUseCase = validateEmailUseCase;
    this.validatePasswordUseCase = validatePasswordUseCase;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    if (modelClass.isAssignableFrom(AuthenticationViewModel.class)) {
      //noinspection unchecked
      return (T) new AuthenticationViewModel(validateEmailUseCase, validatePasswordUseCase);
    }
    throw new IllegalArgumentException("Unknown ViewModel class");
  }
}
