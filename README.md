# ParentGuard (FlashGet Kids Style Parental Control)

<p align="center">
  <img src="app/src/main/res/drawable/user_profile_logo_1787259898289.jpg" width="120" height="120" style="border-radius: 24px;" alt="ParentGuard Logo" />
</p>

<p align="center">
  <b>Comprehensive Real-Time Android Parental Control & Child Safety Application</b><br>
  <i>Built with Jetpack Compose, Kotlin, Room Database & Material Design 3</i>
</p>

---

## 🌟 Live Demo & Web Emulator

- 🔗 **Live Streaming Preview / Demo**: [Open in Google AI Studio](https://ais-pre-oxcyawfd6tthzaj4xgget6-257389990740.europe-west2.run.app)
- 📲 **Download APK**: You can build and download the debug APK via Android Studio or GitHub Releases.

---

## 🛡️ Core Features (FlashGet Kids Capabilities)

### 1. 📷 Live Remote Monitoring
- **Remote Camera**: Stream front and rear cameras in real-time, toggle flashlight, take snapshots, and switch camera angles.
- **One-Way Audio**: Listen to surrounding audio safely in real-time with visual waveform decibel meters.
- **Screen Mirroring**: Real-time child screen mirroring with rotation, pause/resume, and snapshot tools.
- **Two-Way Voice Intercom**: Direct push-to-talk voice communication between parent and child.

### 2. 📍 GPS Tracking & Geofencing
- **Real-Time Live Location**: Accurate GPS tracking, speed, address geocoding, and battery status.
- **Travel Route History**: Full timeline playback of routes and places visited.
- **Geofencing (Safe Zones)**: Custom radius safe zones (Home, School, Playground) with instant entry/exit alert triggers.

### 3. ⏱️ App Blocker & Screen Time Limits
- **Instant Device Lock**: One-tap emergency screen lockout for the child device.
- **App Usage Controls & Limits**: Category-wise and per-app time allowances (Games, Social Media, YouTube).
- **Bedtime & Downtime Schedule**: Automated lock schedules during study hours and sleep time.

### 4. 🔔 Smart Alerts & Security Protection
- **Live Notification Sync**: Real-time mirror for WhatsApp, Instagram, YouTube, and SMS notifications.
- **Call & SMS Log Tracker**: Monitor incoming, outgoing, and missed communications.
- **Sensitive Keyword Detection**: Immediate alerts on detected unsafe words or cyberbullying keywords.
- **SOS Panic Button**: Instant high-priority alarm with location coordinates when triggered by child.
- **Tamper & Anti-Uninstall Protection**: Requires Parent PIN to modify or uninstall child agent.

### 5. 🏆 Activity Reports & Reward Chore Stars
- **Visual App Usage Charts**: Daily & weekly screentime bar charts and phone unlock frequency.
- **Chores & Star Rewards**: Assign daily tasks (e.g., Homework, Cleaning) to reward screen time.

---

## 🔒 Security & API Key Best Practices

- **Zero Hardcoded Secrets**: This project does not store API keys or secrets in the source code.
- **Environment Configuration**: Sensitive environment variables are kept in `.env` (which is included in `.gitignore` and NEVER committed).
- **Template Provided**: See `.env.example` to configure any optional external integrations.

```bash
# Example .env configuration
GEMINI_API_KEY=your_gemini_api_key_here
```

---

## 🛠️ Tech Stack & Architecture

- **Language**: Kotlin 100%
- **UI Framework**: Jetpack Compose (Material 3 Dynamic Theming)
- **Architecture**: MVVM + Clean Architecture + StateFlow & Coroutines
- **Local Persistence**: Room SQLite Database (Offline-first data sync)
- **Asynchronous Flow**: Kotlin Coroutines & Flow
- **Icons & Assets**: Material Symbols & Custom Adaptive Vector Assets

---

## 🚀 How to Run Locally (Installation Steps)

### Prerequisites:
- [Android Studio Ladybug / Meerkat (or newer)](https://developer.android.com/studio)
- JDK 17 or JDK 21
- Android SDK (API 26 to API 35)

### Steps:
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/<your-username>/parent-guard-kids.git
   cd parent-guard-kids
   ```
2. **Open in Android Studio**:
   - Open Android Studio -> Select **Open** -> Choose the project root folder.
3. **Gradle Sync & Build**:
   - Allow Gradle to sync all dependencies automatically.
4. **Run on Emulator / Physical Device**:
   - Select an emulator or connected Android device (API 26+) and click **Run (Shift + F10)**.

---

## 📄 License
This project is open-source under the [MIT License](LICENSE).
