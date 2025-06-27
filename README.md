# Droid Commander

> **Note:** This project was developed earlier and pushed to GitHub later, as I was not familiar with GitHub at the time of development.

## 📱 Overview

**Droid Commander** is an Android application demonstrating the security risks associated with enabling developer options. It provides a comprehensive set of device management features, allowing **complete control over a connected Android device** for educational and demonstration purposes.

This was also my **first Android application developed using Kotlin**, after previously developing apps in Java.

---

## ✨ Features

- **Dialer**
  - Make calls directly from the connected device.

- **File Explorer**
  - Browse and access files stored on the device.

- **File Uploader**
  - Upload files from the host machine to the device seamlessly.

- **Messaging**
  - View and send SMS/text messages using the connected device.
  - **UI replicates Google Messages** for a realistic experience.

- **App Manager**
  - View, install, and uninstall apps on the device.

- **Power Menu**
  - Reboot, shut down, and perform other power operations.
    
- **Silent Screenshot**
  - Capture and display screenshots from the connected device without user interaction, using ADB.

- **Terminal**
  - Execute additional ADB commands not implemented directly in the UI, enabling advanced control.

---

## 🔒 Purpose and Ethical Use

This app was developed to **demonstrate why developer options should remain disabled for normal users**, by highlighting how enabling USB debugging and developer settings can allow external applications to:

- Access and modify files without user consent  
- View and send SMS messages programmatically  
- Make calls without the device owner's knowledge  
- Install or uninstall apps silently  
- Control system power operations  
- Execute arbitrary shell commands through an integrated terminal  

> **Important:** Droid Commander is intended **solely for educational and demonstration purposes** to raise awareness about Android security. **It is not designed for any unethical or unauthorized use.**

---

## 💻 Technical Details

- **Platform:** Android (Kotlin using Jetpack Compose)
- **Key Implementations:**
  - Intent-based activity navigation for each feature
  - Dynamic push/pull operations for file management
  - Dialog-based controls for calling and power options
  - Integrated terminal for extended ADB command execution
  - Google Messages-inspired UI for the messaging module

---

## ⚠️ Disclaimer

This application is intended **only for educational and ethical demonstration purposes**. Use responsibly and never operate on devices without explicit authorization.

- **Root Access:** While all functionalities can technically be performed without root access if ADB is properly configured, **root access is intentionally required** to prevent misuse by unauthorized users.
- **Security Note:** The app does **not include ADB setup instructions** to discourage unethical use.  
- **Warning:** Enabling developer options can pose serious security risks to device integrity and user privacy.

---
