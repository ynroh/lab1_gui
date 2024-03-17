package src.main.kotlin.model

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable


class ScannerViewModel(undoRedoState: UndoRedoState) {
    public var currentFilePath by mutableStateOf("")
    public var currentContent by mutableStateOf("")
    public var isChangesSaved by mutableStateOf(false)
    public val undoRedoState by mutableStateOf(undoRedoState)
    public var scanResultText by mutableStateOf("")
}