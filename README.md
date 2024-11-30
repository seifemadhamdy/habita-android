# 🌱 Habita

A modern Android habit tracking application that helps users build and maintain daily habits using Firebase Authentication and Firebase Realtime Database.

<table style="width: 100%; text-align: center; table-layout: fixed;">
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/a153d865-8a96-41f3-a2aa-1ec93e66db93" alt="Authentication Screen Light"><br>
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/62cffcda-2395-4cfe-bf06-f4e2382a23b2" alt="Home Screen Light"><br>
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/32b48c07-a948-42b7-b820-a48387939a2f" alt="Add Habit Dialog Light"><br>
    </td>
  </tr>
  <tr>
    <td>
      <img src="https://github.com/user-attachments/assets/c5e8e6fc-3bb7-4b37-a002-4b17d285afd4" alt="Authentication Screen Dark"><br>
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/bde79b88-630c-4109-b250-33a9469cd283" alt="Home Screen Dark"><br>
    </td>
    <td>
      <img src="https://github.com/user-attachments/assets/04f243d7-f38f-4fd2-aab3-5204449fbf56" alt="Add Habit Dialog Dark"><br>
    </td>
  </tr>
</table>

## ✨ Features

- User authentication with email and password
- Real-time habit tracking
- Add daily habits
- Mark habits as completed for the day
- Secure user data with Firebase authentication
- Responsive and intuitive UI design

## 🏗️ Technical Architecture

### Architecture Overview

The application follows Clean Architecture principles with MVVM pattern, ensuring:

- Clear separation of concerns
- High testability
- Scalable and maintainable codebase
- Unidirectional data flow

### Key Technical Decisions

#### UI Layer

- **Navigation Component**: Type-safe navigation between fragments
- **Material Design**: Modern design guidelines
- **MVVM Pattern**: Separation of UI logic and data
- **View Binding**: Efficient view interactions

#### Domain Layer

- **Use Cases**: Encapsulated business logic
- **Single Responsibility Principle**: Modular code design

## 🔄 Trade-offs & Decisions

1. **Authentication Strategy**
   - Chose email/password authentication for simplicity
   - Implemented robust validation and error handling

2. **Data Persistence**
   - Leveraged Firebase Realtime Database for instant updates
   - Implemented strict security rules for user data privacy

3. **UI Implementation**
   - Used Android Fragments for modular UI components
   - Implemented responsive design for various screen sizes

## 🔜 Future Improvements

1. **Technical**
   - Implement more authentication methods (Google, Facebook)
   - Add comprehensive unit and integration tests
   - Implement habit streaks and progress tracking

2. **User Experience**
   - Implement habit reminders and notifications
   - Add detailed habit analytics
   - Create custom habit categories

## 🛠️ Setup & Installation

1. Clone the repository
2. Set up Firebase project
   - Create Firebase project
   - Add google-services.json to the app module
3. Configure Firebase Realtime Database security rules
4. Build and run the project

### Firebase Realtime Database Rules

```json
{
  "rules": {
    "habits": {
      "$uid": {
        ".read": "auth !== null && auth.uid === $uid",
        ".write": "auth !== null && auth.uid === $uid"
      }
    }
  }
}
```

## 👨‍💻 Author

Seif Abu El-Ela
