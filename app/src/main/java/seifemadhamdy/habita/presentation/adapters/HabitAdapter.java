package seifemadhamdy.habita.presentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.Objects;
import seifemadhamdy.habita.R;
import seifemadhamdy.habita.core.utils.DateUtil;
import seifemadhamdy.habita.domain.models.Habit;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.HabitViewHolder> {
  private List<Habit> habits;

  public HabitAdapter(List<Habit> habits) {
    this.habits = habits;
  }

  public void updateHabits(List<Habit> newHabits) {
    HabitDiffCallback diffCallback = new HabitDiffCallback(habits, newHabits);
    DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

    habits = newHabits;
    diffResult.dispatchUpdatesTo(this);
  }

  @NonNull
  @Override
  public HabitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_habit, parent, false);
    return new HabitViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull HabitViewHolder holder, int position) {
    Habit habit = habits.get(position);
    holder.bind(habit);
  }

  @Override
  public int getItemCount() {
    return habits.size();
  }

  public static class HabitViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView completionStatusTextView;

    public HabitViewHolder(@NonNull View itemView) {
      super(itemView);
      nameTextView = itemView.findViewById(R.id.name_text_view);
      completionStatusTextView = itemView.findViewById(R.id.completion_status_text_view);
    }

    public void bind(Habit habit) {
      nameTextView.setText(habit.getName());

      if (Objects.equals(habit.getCompletionDate(), DateUtil.getCurrentDate())) {
        completionStatusTextView.setVisibility(View.VISIBLE);

        completionStatusTextView.setText(
            completionStatusTextView
                .getContext()
                .getString(R.string.habit_completion_status, habit.getName()));
      }
    }
  }

  static class HabitDiffCallback extends DiffUtil.Callback {
    private final List<Habit> oldHabits;
    private final List<Habit> newHabits;

    public HabitDiffCallback(List<Habit> oldHabits, List<Habit> newHabits) {
      this.oldHabits = oldHabits;
      this.newHabits = newHabits;
    }

    @Override
    public int getOldListSize() {
      return oldHabits.size();
    }

    @Override
    public int getNewListSize() {
      return newHabits.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
      return oldHabits.get(oldItemPosition).equals(newHabits.get(newItemPosition));
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
      return oldHabits.get(oldItemPosition).equals(newHabits.get(newItemPosition));
    }
  }
}
