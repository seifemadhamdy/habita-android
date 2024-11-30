package seifemadhamdy.habita.presentation;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.splashscreen.SplashScreen;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import seifemadhamdy.habita.R;
import seifemadhamdy.habita.databinding.ActivityHabitaBinding;

public class HabitaActivity extends AppCompatActivity {
  private NavController navController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    SplashScreen.installSplashScreen(this);
    EdgeToEdge.enable(this);
    ActivityHabitaBinding binding = ActivityHabitaBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    applySystemBarsPadding();
    setupBackPressHandling();
    navController = getNavController();
  }

  private void applySystemBarsPadding() {
    ViewCompat.setOnApplyWindowInsetsListener(
        findViewById(R.id.nav_host_fragment),
        (v, insets) -> {
          Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
          v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
          return insets;
        });
  }

  private void setupBackPressHandling() {
    getOnBackPressedDispatcher()
        .addCallback(
            this,
            new OnBackPressedCallback(true) {
              @Override
              public void handleOnBackPressed() {
                if (!navController.popBackStack()) finish();
              }
            });
  }

  private NavController getNavController() {
    NavHostFragment navHostFragment =
        (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
    return navHostFragment != null ? navHostFragment.getNavController() : null;
  }
}
