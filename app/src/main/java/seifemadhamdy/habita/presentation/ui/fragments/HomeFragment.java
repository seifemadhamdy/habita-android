package seifemadhamdy.habita.presentation.ui.fragments;

import android.os.Bundle;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import java.util.ArrayList;
import java.util.Objects;
import seifemadhamdy.habita.R;
import seifemadhamdy.habita.core.utils.DateUtil;
import seifemadhamdy.habita.databinding.DialogAddHabitBinding;
import seifemadhamdy.habita.databinding.FragmentHomeBinding;
import seifemadhamdy.habita.domain.models.Habit;
import seifemadhamdy.habita.presentation.adapters.HabitAdapter;
import seifemadhamdy.habita.presentation.ui.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {
  private FragmentHomeBinding binding;
  private NavController navController;
  private HomeViewModel homeViewModel;
  private HabitAdapter habitAdapter;

  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    navController = NavHostFragment.findNavController(this);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

    binding.toolbar.setOnMenuItemClickListener(
        item -> {
          if (item.getItemId() == R.id.sign_out) {
            homeViewModel.signOutUser();
            navigateToAuthentication();
            return true;
          }
          return false;
        });

    habitAdapter = new HabitAdapter(new ArrayList<>());
    binding.habitsRecyclerView.setAdapter(habitAdapter);

    homeViewModel
        .getDataSnapshotValue()
        .observe(
            getViewLifecycleOwner(),
            dataSnapshotValue -> {
              if (dataSnapshotValue != null) {
                habitAdapter.updateHabits(dataSnapshotValue);
              }
            });

    binding.newHabitExtendedFloatingActionButton.setOnClickListener(_ -> showAddHabitDialog());

    homeViewModel
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

  private void showAddHabitDialog() {
    DialogAddHabitBinding dialogAddHabitBinding =
        DialogAddHabitBinding.inflate(getLayoutInflater());

    new MaterialAlertDialogBuilder(requireContext())
        .setTitle(getString(R.string.new_habit))
        .setPositiveButton(
            getString(R.string.add),
            (_, _) -> {
              String name =
                  Objects.requireNonNull(dialogAddHabitBinding.habitTextInputEditText.getText())
                      .toString();

              if (!name.isBlank()) {
                String completionDate = null;

                if (dialogAddHabitBinding.markAsCompletedSwitch.isChecked()) {
                  completionDate = DateUtil.getCurrentDate();
                }

                homeViewModel.addHabitToUserHabits(new Habit(name, completionDate));
              } else {
                Toast.makeText(
                        getContext(), getString(R.string.empty_habit_name_error), Toast.LENGTH_LONG)
                    .show();
              }
            })
        .setNegativeButton(getString(R.string.cancel), (_, _) -> {})
        .setView(dialogAddHabitBinding.getRoot())
        .show();
  }

  private void navigateToAuthentication() {
    NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.home, true).build();
    navController.navigate(R.id.action_home_to_authentication, null, navOptions);
  }
}
