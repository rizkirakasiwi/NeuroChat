import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import id.co.appshared.SharedApp
import id.co.appshared.utils.initKoin

/**
 * Main function.
 * This function is used to start the desktop application.
 * It performs the following tasks:
 * 1. Initializes Koin for dependency injection.
 * 2. Creates a window state to manage the window's state.
 * 3. Creates a window with a specified title and close request handler.
 * 4. Calls `SharedApp()` to render the root composable of the application.
 *
 * @see application
 * @see rememberWindowState
 * @see Window
 * @see SharedApp
 */
fun main() {
    application {
        // Initializes the Koin dependency injection framework.
        initKoin()

        // Creates a window state to manage the window's state.
        val windowState = rememberWindowState()

        // Creates a window with a specified title and close request handler.
        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = "NeuroChat",
        ) {
            // Sets the content of the window.
            SharedApp()
        }
    }
}