# Droid Commander

> **Note:** This project was developed earlier and pushed to GitHub later, as I was not familiar with GitHub at the time of development.

## 📱 Overview

**Droid Commander** is an Android application demonstrating the security risks associated with enabling developer options. It provides a comprehensive set of device management features, allowing **complete control over a connected Android device** for educational and demonstration purposes.

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
  - **UI replicated from Google Messages** for realistic demonstration.

- **App Manager**
  - View, install, and uninstall apps on the device.

- **Power Menu**
  - Reboot, shutdown, and perform other power operations.

- **Terminal**
  - Execute additional ADB commands not implemented directly in the UI, providing extended functionality for advanced users.

---

## 🔒 Purpose

This app was developed to **demonstrate why developer options should remain disabled for normal users**. It highlights how enabling USB debugging and developer settings can allow external apps or users to:

- Access and modify files without user consent
- View and send SMS messages programmatically
- Make calls without device owner’s knowledge
- Install or uninstall apps silently
- Control system power operations
- Execute arbitrary shell commands through the integrated terminal

---

## ⚠️ Root Access

Certain operations within Droid Commander **require root access** to execute privileged commands, especially for:

- Complete file system access
- Unrestricted app management operations
- Advanced power controls

---

## 💻 Technical Details

- **Platform:** Android (Kotlin)
- **Architecture:** MVVM with Activities and intuitive UI components
- **Key Implementations:**
  - Intent-based activity navigation for each feature
  - Dynamic push/pull operations for file management
  - Dialog-based controls for calling and power options
  - Integrated terminal for extended ADB command execution
  - Google Messages-inspired UI for the messaging module

---
