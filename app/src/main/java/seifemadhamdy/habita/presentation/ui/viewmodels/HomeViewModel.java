package seifemadhamdy.habita.presentation.ui.viewmodels;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import seifemadhamdy.habita.domain.models.Habit;

public class HomeViewModel extends ViewModel {
  private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
  private final MutableLiveData<List<Habit>> dataSnapshotValue = new MutableLiveData<>();
  private final MutableLiveData<String> errorMessage = new MutableLiveData<>();
  private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

  public HomeViewModel() {
    DatabaseReference userHabitsRef = getUserHabitsReference();

    userHabitsRef.addValueEventListener(
        new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
              List<Habit> habits = new ArrayList<>();

              for (DataSnapshot habitSnapshot : dataSnapshot.getChildren()) {
                Habit habit = habitSnapshot.getValue(Habit.class);

                if (habit != null) {
                  habits.add(habit);
                }
              }
              dataSnapshotValue.setValue(habits);
            } else {
              dataSnapshotValue.setValue(null);
            }
          }

          @Override
          public void onCancelled(@NonNull DatabaseError error) {
            errorMessage.setValue(error.toException().getMessage());
          }
        });
  }

  private DatabaseReference getUserHabitsReference() {
    return firebaseDatabase
        .getReference("habits")
        .child(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
  }

  public LiveData<List<Habit>> getDataSnapshotValue() {
    return dataSnapshotValue;
  }

  public LiveData<String> getErrorMessage() {
    return errorMessage;
  }

  public void addHabitToUserHabits(Habit habit) {
    getUserHabitsReference().push().setValue(habit);
  }

  public void signOutUser() {
    firebaseAuth.signOut();
  }
}
