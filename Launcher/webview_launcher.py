#!/usr/bin/env python3
"""
Borderless WebView Launcher
Opens a specified URL in a borderless, always-on-top webview window.
Automatically adjusts for taskbar, FRC DriverStation, and FRC Dashboard (bottom 200px).
"""

import sys
import subprocess
from PyQt6.QtWidgets import QApplication, QMainWindow
from PyQt6.QtWebEngineWidgets import QWebEngineView
from PyQt6.QtCore import Qt, QRect
from PyQt6.QtGui import QScreen


class BorderlessWebView(QMainWindow):
    """Borderless, always-on-top webview window for FRC dashboards."""
    
    def __init__(self, url: str):
        """
        Initialize the borderless webview.
        
        Args:
            url: The URL to load in the webview
        """
        super().__init__()
        
        # Create webview
        self.browser = QWebEngineView()
        self.browser.load(url if url.startswith(('http://', 'https://', 'file://')) else f'http://{url}')
        self.setCentralWidget(self.browser)
        
        # Configure window properties
        self.setWindowTitle("WildBoard")
        
        # Remove window decorations (titlebar, borders)
        self.setWindowFlags(
            Qt.WindowType.FramelessWindowHint |
            Qt.WindowType.WindowStaysOnTopHint |
            Qt.WindowType.Tool
        )
        
        # Set window geometry
        self._set_window_geometry()
    
    def _set_window_geometry(self):
        """Calculate and set window geometry accounting for taskbar and FRC dashboard."""
        screen = self.screen()
        if screen is None:
            # Fallback if no screen available
            screen = QApplication.primaryScreen()
        
        # Get screen geometry
        screen_geometry = screen.geometry()
        screen_width = screen_geometry.width()
        screen_height = screen_geometry.height()
        
        # Get available geometry (excludes taskbar)
        available_geometry = screen.availableGeometry()
        available_height = available_geometry.height()
        
        # Calculate taskbar height
        taskbar_height = screen_height - available_height
        
        # FRC Dashboard is at the bottom and takes 200px
        frc_dashboard_height = 200
        
        # Calculate window dimensions
        window_x = available_geometry.x()
        window_y = available_geometry.y()
        window_width = available_geometry.width()
        # Subtract dashboard height from available height
        window_height = available_height - frc_dashboard_height
        
        # Set geometry
        self.setGeometry(window_x, window_y, window_width, window_height)


def main():
    """Main entry point for the application."""
    # Default URL (can be changed or passed as argument)
    url = sys.argv[1] if len(sys.argv) > 1 else "http://localhost:5800"
    
    app = QApplication(sys.argv)
    window = BorderlessWebView(url)
    window.show()
    
    sys.exit(app.exec())


if __name__ == "__main__":
    main()
