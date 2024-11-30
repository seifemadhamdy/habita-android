package seifemadhamdy.habita.domain.models;

public class Habit {
  private String name;
  private String completionDate;

  // No-argument constructor (required for Firebase)
  @SuppressWarnings("unused")
  private Habit() {}

  public Habit(String name, String completionDate) {
    this.name = name;
    this.completionDate = completionDate;
  }

  // Getters and setters (required for Firebase)
  public String getName() {
    return name;
  }

  @SuppressWarnings("unused")
  private void setName(String name) {
    this.name = name;
  }

  public String getCompletionDate() {
    return completionDate;
  }

  @SuppressWarnings("unused")
  private void setCompletionDate(String completionDate) {
    this.completionDate = completionDate;
  }
}
