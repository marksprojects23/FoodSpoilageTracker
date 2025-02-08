# Overview

As a software engineer, I'm always looking to build my skills and expand my knowledge in various areas of development. With this project, I set out to create a Food Spoilage Tracker—an Android application that helps users monitor the expiration dates of their food items. The app allows users to add food items along with their expiration dates, displays them in a clean list, and visually indicates expired items by highlighting them in red. Users can easily add new entries through a dialog interface and remove items with a simple swipe gesture.

The purpose of creating this app was to further my learning in mobile development using Kotlin and Android Studio while addressing a real-world problem. By building this application, I was able to explore key concepts such as user interface design, local data persistence, and effective handling of user interactions on Android devices.

[Software Demo Video](https://youtu.be/CP4NiptnIfg)

# Development Environment

I developed this project using Android Studio on a Windows 10 PC. The app is written in Kotlin and leverages Android’s modern development practices. Key libraries and components used include:

- **RecyclerView:** For efficiently displaying the list of food items.
- **Material Design Components:** To create a clean, user-friendly interface.
- **Gson:** For JSON serialization and deserialization, which enables simple local data persistence via SharedPreferences.
- **ItemTouchHelper:** To implement swipe-to-delete functionality.

This setup allowed me to rapidly prototype and iterate on the application while deepening my understanding of Android development.

# Useful Websites

- [Android Developers](https://developer.android.com/) – Official documentation and resources for Android development.
- [Stack Overflow](https://stackoverflow.com/) – A great resource for troubleshooting and finding code examples.
- [Material Design Guidelines](https://material.io/) – For designing intuitive and aesthetically pleasing user interfaces.
- [Gson GitHub Repository](https://github.com/google/gson) – Documentation and examples for using Gson.
- [ChatGPT](https://chat.openai.com/) – An invaluable tool for code assistance and debugging.

# Future Work

- Implement local notifications to alert users when food items are nearing or have passed their expiration dates.
- Migrate from SharedPreferences to Room for more robust and scalable data persistence.
- Enhance the UI with additional Material Design components and animations for a more polished look.
- Add filtering and sorting options to improve the user experience.
- Refactor and modularize the codebase for better maintainability and readability.
