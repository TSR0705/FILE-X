# FileX - Insider File Leak Detector (Phase 1)

This is Phase 1 of the FileX application - a professional cyber enterprise file monitoring application.

## Project Structure

```
FileX/
├── build.gradle
├── settings.gradle
├── gradlew.bat
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── src/
│   ├── main/
│   │   ├── java/com/filex/
│   │   │   ├── Main.java
│   │   │   ├── theme/
│   │   │   │   ├── Colors.java
│   │   │   │   └── ThemeManager.java
│   │   │   ├── model/
│   │   │   │   └── FileEvent.java
│   │   │   ├── util/
│   │   │   │   ├── AnimationUtil.java
│   │   │   │   ├── FXUtil.java
│   │   │   │   └── UIUtil.java
│   │   │   ├── service/
│   │   │   │   ├── MonitorService.java
│   │   │   │   ├── DatabaseService.java
│   │   │   │   └── PreferencesService.java
│   │   │   └── controller/
│   │   │       ├── DashboardController.java
│   │   │       ├── SidebarController.java
│   │   │       ├── TopBarController.java
│   │   │       ├── CardController.java
│   │   │       ├── AlertsController.java
│   │   │       ├── SettingsController.java
│   │   │       └── LogsController.java
│   │   └── resources/
│   │       ├── fxml/
│   │       │   ├── Dashboard.fxml
│   │       │   ├── Sidebar.fxml
│   │       │   ├── TopBar.fxml
│   │       │   ├── Card.fxml
│   │       │   ├── Alerts.fxml
│   │       │   ├── Settings.fxml
│   │       │   └── Logs.fxml
│   │       ├── css/
│   │       │   ├── theme-light.css
│   │       │   ├── theme-dark.css
│   │       │   ├── style.css
│   │       │   └── animation.css
│   │       ├── fonts/
│   │       │   ├── RobotoMono-Regular.ttf       (placeholder)
│   │       │   ├── Poppins-Medium.ttf           (placeholder)
│   │       │   └── Inter-Regular.ttf            (placeholder)
│   │       └── images/
│   │           ├── logo.png                    (placeholder)
│   │           └── icons/                      (placeholder)
└── README.md
```

## How to Run

### Prerequisites
- Java 21 JDK installed
- JavaFX 21 SDK downloaded
- Gradle (optional, wrapper included)

### Running with Gradle Wrapper
```bash
./gradlew run
```

### Running with system Gradle
```bash
gradle run
```

### Running directly with Java
```bash
# Compile the Java files
javac --module-path "path/to/javafx/lib" --add-modules javafx.controls,javafx.fxml,javafx.graphics -d target/classes src/main/java/module-info.java src/main/java/com/filex/*.java src/main/java/com/filex/theme/*.java src/main/java/com/filex/model/*.java src/main/java/com/filex/util/*.java src/main/java/com/filex/service/*.java src/main/java/com/filex/controller/*.java

# Run the application
java --module-path "path/to/javafx/lib;target/classes" --add-modules javafx.controls,javafx.fxml,javafx.graphics -m com.filex/com.filex.Main
```

## Blank Screen Fix

### Problem
When clicking any sidebar button, the screen would go blank momentarily with only brief visibility of the content.

### Solution
The issue has been resolved with the following fixes:

1. **MainController.java**:
   - Removed invalid `layout()` method calls on Node objects
   - Simplified the content loading mechanism to directly replace content
   - Ensured immediate content visibility with proper opacity settings
   - Maintained proper CSS application with `applyCss()` method

2. **SidebarController.java**:
   - Improved controller communication to properly navigate between views
   - Simplified the controller lookup mechanism to avoid null pointer exceptions

3. **FXML Structure**:
   - Verified proper FXML structure for all views
   - Ensured CSS stylesheets are correctly referenced

### Additional Files
- `BLANK_SCREEN_FIX_SUMMARY.md` - Detailed summary of the fixes applied
- `RUN_INSTRUCTIONS.md` - Instructions for running the application with the fixes
- `run-fixed.bat` - Batch file to run the application with the fixes

## Features Implemented in Phase 1

1. **Modular JavaFX Application**:
   - Proper `module-info.java` configuration
   - JavaFX 21 dependencies

2. **Professional Theme**:
   - Color palette with primary (#0078D4) and secondary (#00B8A9) accents
   - Light background (#F4F6F8) with white cards
   - Custom fonts (Roboto Mono, Poppins, Inter)

3. **Dashboard Layout**:
   - Top bar with application title and folder path
   - Sidebar with navigation buttons
   - Summary cards for Total Files, Suspicious files, and Last Scan
   - TableView placeholder for file events
   - LineChart and PieChart for data visualization

4. **Build Configuration**:
   - Gradle build with JavaFX plugin
   - Proper dependency management

## Dashboard Testing Instructions

After running the application, the FileX dashboard will be displayed with the following features:

1. **Dashboard Layout**:
   - Top bar showing "FileX — Dashboard" and monitored folder path
   - Three summary cards showing Total Files, Suspicious Files, and Last Scan time
   - Left side: Line chart showing events over time and Pie chart showing event type distribution
   - Right side: Table view of file events with columns for File Name, Event Type, Time, Hash, and Status
   - Bottom status bar showing system messages

2. **Mock Data**:
   - On startup, the dashboard loads 10-15 mock file events
   - At least one event is marked as "Suspicious" with visual distinction

3. **Live Event Simulation**:
   - Every 8 seconds, a new mock event is added to the top of the table
   - New events are animated with a fade-in effect
   - Summary cards and charts automatically update to reflect new data

4. **Suspicious Event Handling**:
   - When a suspicious event is added, an alert pane slides in from the right
   - The alert pane shows event details and a "View" button
   - Clicking "View" selects and highlights the corresponding row in the table
   - Suspicious rows are visually distinct with a red left border

5. **Visual Styling**:
   - All UI elements follow the FileX color theme
   - Cards have hover effects with subtle scaling and shadow changes
   - Tables have alternating row colors and hover highlights
   - Charts are styled with the application's color scheme

## Application Features

### Dashboard
- Summary cards with mock data
- LineChart and PieChart visualizations
- TableView with file events (8-15 mock rows)
- Simulated live updates every 8 seconds
- Suspicious event highlighting
- Alert panel for suspicious events

### Logs View
- Detailed TableView of all file events
- Export to CSV functionality (placeholder)

### Settings View
- Directory chooser for monitored folder
- Toggle switches for notifications
- Hashing algorithm selection
- Save and Reset buttons

### Sidebar Navigation
- Navigation between Dashboard, Logs, Alerts, and Settings
- Expandable/collapsible sidebar with smooth animations
- Theme toggle button

### TopBar
- Application title and logo
- Monitored folder path
- Monitoring status indicator with pulse animation
- Theme toggle button
- Simulate Event button for testing

### Professional Dashboard Features
The application now includes a professional dashboard with advanced features:

1. **Real-time Monitoring Dashboard**:
   - Live event simulation with periodic updates
   - Summary cards with animated counters
   - Interactive charts for data visualization
   - Comprehensive file event table with context menu

2. **Alert System**:
   - Real-time alert notifications for suspicious activities
   - Interactive alert panels with action buttons
   - Alert severity indicators

3. **Data Visualization**:
   - Line chart showing events over time
   - Pie chart showing event type distribution
   - Bar chart showing alert severity distribution

4. **Search and Filter**:
   - File name search functionality
   - Event type filtering
   - Suspicious events filter

5. **Export Functionality**:
   - CSV export for events
   - Individual row export

## Services (Stubs for Future Implementation)

### MonitorService
- File system monitoring (stub implementation)
- Event detection and handling

### DatabaseService
- Data persistence (stub implementation)
- Event storage and retrieval

### PreferencesService
- User preferences management
- Settings storage and retrieval

## Utilities

### AnimationUtil
- Fade-in animations
- Slide-in animations
- Pulse animations

### UIUtil
- Additional UI animations and effects
- Helper methods for common UI operations

### FXUtil
- Safe UI updates from background threads
- Alert dialogs
- Confirmation dialogs

## UI/UX Revamp — Sidebar & Themes

### New Features Implemented

1. **Improved Sidebar**
   - Fixed left column with expandable/collapsible functionality
   - Expanded width: 260px; Collapsed width: 72px
   - Smooth width animation with label fade transitions
   - Consistent spacing and alignment with 12px baseline grid
   - SVG icons for all navigation items
   - Keyboard navigation support (Arrow keys, Enter)

2. **Dual Theme Support**
   - Light Theme (default): Professional light color scheme
   - Dark Theme: Modern dark color scheme
   - Smooth crossfade transition when switching themes
   - Theme toggle button in both sidebar and topbar

3. **Enhanced Visual Design**
   - Consistent 12px baseline grid for all spacing
   - Improved typography with Poppins, Inter, and Roboto Mono
   - Better card designs with consistent padding and shadows
   - Refined color palettes for both themes
   - Subtle microinteractions and hover effects

4. **Accessibility Improvements**
   - Keyboard navigation support
   - Visual focus indicators
   - Proper contrast ratios for text
   - Accessible text for all interactive elements

### Color Palettes

**Light Theme**:
- PRIMARY = #0066CC (balanced indigo-blue)
- SECONDARY = #00A896 (teal)
- BACKGROUND = #F7FAFC (very light neutral)
- CARD_BG = #FFFFFF
- TEXT_PRIMARY = #0F1724 (dark slate)
- TEXT_SECONDARY = #6B7280 (muted gray)
- ALERT = #DC2626 (error red)
- SUCCESS = #16A34A (green)
- HOVER = rgba(0,102,204,0.08)

**Dark Theme**:
- PRIMARY = #4F8EF7
- SECONDARY = #20C6B6
- BACKGROUND = #0B1220
- CARD_BG = #0F1724
- TEXT_PRIMARY = #E6EEF5
- TEXT_SECONDARY = #9AA5B1
- ALERT = #EF4444
- SUCCESS = #34D399
- HOVER = rgba(79,142,247,0.08)

### Manual Verification Steps

1. **Sidebar Functionality**:
   - [ ] Sidebar expands to 260px when expanded
   - [ ] Sidebar collapses to 72px when collapsed
   - [ ] Width animation is smooth (300ms)
   - [ ] Labels fade in/out during collapse/expand
   - [ ] Navigation items are properly aligned
   - [ ] Icons are vertically centered with labels
   - [ ] Selected item has proper highlight
   - [ ] Hover effects work correctly
   - [ ] Keyboard navigation functions (Arrow keys, Enter)

2. **Theme Switching**:
   - [ ] Light theme is applied by default
   - [ ] Theme toggle button switches between themes
   - [ ] Crossfade transition is smooth
   - [ ] All UI elements update to new theme colors
   - [ ] Fonts remain consistent across themes

3. **Visual Design**:
   - [ ] Cards have consistent padding and shadows
   - [ ] 12px baseline grid is maintained
   - [ ] Typography is consistent and readable
   - [ ] Microinteractions work properly
   - [ ] Hover effects are subtle and smooth

4. **Accessibility**:
   - [ ] Keyboard navigation works for all interactive elements
   - [ ] Visual focus indicators are clearly visible
   - [ ] Text contrast meets accessibility standards
   - [ ] Accessible text is present for all controls

## Future Implementation Notes

### Controllers
Future controllers will be placed in `src/main/java/com/filex/controller/` package.

### Services
Backend services will be placed in `src/main/java/com/filex/service/` package:
- `MonitorService` - File system monitoring
- `HashService` - File hashing for integrity verification
- `AlertService` - Security alert detection and notification
- `DatabaseService` - Data persistence

### Theme Management
The `ThemeManager` class will be used by controllers to apply themes dynamically.

### Navigation
Additional FXML files will be loaded into the center `StackPane` for navigation in Phase 2.

## Acceptance Criteria

- [x] Project compiles with `./gradlew build`
- [x] Running `./gradlew run` opens the FileX window with the Dashboard layout
- [x] CSS fonts and color palette are visible
- [x] Cards display with white background on light background
- [x] Primary accent color is applied to UI elements
- [x] TableView shows with proper styling
- [x] Dashboard loads mock data on startup
- [x] Live event simulation works with animations
- [x] Suspicious events are visually distinct
- [x] Alert pane appears for suspicious events with "View" functionality
- [x] Sidebar expands/collapses with smooth animations
- [x] Light and Dark themes are available with toggle button
- [x] Theme switching has smooth crossfade transition
- [x] Keyboard navigation works in sidebar
- [x] All UI elements follow 12px baseline grid
- [x] Blank screen issue during navigation is resolved