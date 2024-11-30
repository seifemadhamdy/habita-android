package seifemadhamdy.habita.presentation.ui.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;
import java.util.Objects;
import seifemadhamdy.habita.R;
import seifemadhamdy.habita.databinding.FragmentAuthenticationBinding;
import seifemadhamdy.habita.domain.usecases.ValidateEmailUseCase;
import seifemadhamdy.habita.domain.usecases.ValidatePasswordUseCase;
import seifemadhamdy.habita.presentation.ui.viewmodels.AuthenticationViewModel;
import seifemadhamdy.habita.presentation.ui.viewmodels.AuthenticationViewModelFactory;

public class AuthenticationFragment extends Fragment {
  private FragmentAuthenticationBinding binding;
  private NavController navController;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentAuthenticationBinding.inflate(inflater, container, false);
    navController = NavHostFragment.findNavController(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    AuthenticationViewModelFactory authenticationViewModelFactory =
        new AuthenticationViewModelFactory(
            new ValidateEmailUseCase(), new ValidatePasswordUseCase());

    AuthenticationViewModel authenticationViewModel =
        new ViewModelProvider(this, authenticationViewModelFactory)
            .get(AuthenticationViewModel.class);

    authenticationViewModel
        .getEmail()
        .observe(
            getViewLifecycleOwner(),
            email -> {
              if (!Objects.equals(
                  email,
                  Objects.requireNonNull(binding.emailTextInputEditText.getText()).toString())) {
                binding.emailTextInputEditText.setText(email);
              }
            });

    binding.emailTextInputEditText.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            authenticationViewModel.setEmail(charSequence.toString());
          }

          @Override
          public void afterTextChanged(Editable editable) {
            binding.emailTextInputEditText.setError(
                (authenticationViewModel.validateEmail()
                        || Objects.requireNonNull(authenticationViewModel.getEmail().getValue())
                            .isEmpty())
                    ? null
                    : getString(R.string.email_help_message));
          }
        });

    authenticationViewModel
        .getPassword()
        .observe(
            getViewLifecycleOwner(),
            password -> {
              if (!Objects.equals(
                  password,
                  Objects.requireNonNull(binding.passwordTextInputEditText.getText()).toString())) {
                binding.passwordTextInputEditText.setText(password);
              }
            });

    binding.passwordTextInputEditText.addTextChangedListener(
        new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

          @Override
          public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            authenticationViewModel.setPassword(charSequence.toString());
          }

          @Override
          public void afterTextChanged(Editable editable) {
            binding.passwordTextInputEditText.setError(
                (authenticationViewModel.validatePassword()
                        || Objects.requireNonNull(authenticationViewModel.getPassword().getValue())
                            .isEmpty())
                    ? null
                    : getString(R.string.password_help_message));
          }
        });

    binding.continueButton.setOnClickListener(
        _ -> authenticationViewModel.continueWithEmailAndPassword(requireActivity()));

    authenticationViewModel
        .getFirebaseUser()
        .observe(getViewLifecycleOwner(), _ -> navigateToHome());

    authenticationViewModel
        .getErrorMessage()
        .observe(
            getViewLifecycleOwner(),
            errorMessage -> Toast.makeText(getContext(), errorMessage, Toast.LENGTH_LONG).show());
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }

  private void navigateToHome() {
    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.authentication, true).build();
    navController.navigate(R.id.action_authentication_to_home, null, navOptions);
  }
}
