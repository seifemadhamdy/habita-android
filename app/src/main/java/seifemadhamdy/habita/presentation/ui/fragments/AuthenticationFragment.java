package seifemadhamdy.habita.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import seifemadhamdy.habita.databinding.FragmentAuthenticationBinding;
import seifemadhamdy.habita.presentation.ui.viewmodels.AuthenticationViewModel;

public class AuthenticationFragment extends Fragment {
  private FragmentAuthenticationBinding binding;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentAuthenticationBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    AuthenticationViewModel authenticationViewModel =
        new ViewModelProvider(this).get(AuthenticationViewModel.class);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    binding = null;
  }
}
