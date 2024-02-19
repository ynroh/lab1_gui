import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue


object Singletone {
    var currentFilePath by mutableStateOf("")
    var currentContent by mutableStateOf("")
    var isChangesSaved by mutableStateOf(false)
    val undoRedoState by mutableStateOf(UndoRedoState())
}