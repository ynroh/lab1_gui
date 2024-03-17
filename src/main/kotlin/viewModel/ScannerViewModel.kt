package viewModel

import androidx.compose.runtime.*
import src.main.kotlin.model.UndoRedoState


class ScannerViewModel(undoRedoState: UndoRedoState) {
    public var currentFilePath by mutableStateOf("")
    public var currentContent by mutableStateOf("")
    public var isChangesSaved by mutableStateOf(false)
    public val undoRedoState by mutableStateOf(undoRedoState)
    public var scanResultText by mutableStateOf("")
}