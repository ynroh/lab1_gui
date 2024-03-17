package src.main.kotlin.model
import androidx.compose.runtime.*

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue

public class UndoRedoState {

    var input: TextFieldValue by mutableStateOf(TextFieldValue(""))
    var undoHistory = ArrayDeque<TextFieldValue?>()
    var redoHistory = ArrayDeque<TextFieldValue?>()

    init {
        undoHistory.add(input)
    }

    fun onInput(value: TextFieldValue) {
        val updatedValue =  value.copy(value.text, selection = TextRange(value.text.length))
        undoHistory.add(updatedValue)
        input = updatedValue
    }

    fun undo() {

        if (undoHistory.size > 1) {

            val pop = undoHistory.removeLastOrNull()
            pop?.let {
                if (it.text.isNotEmpty()) {
                    redoHistory.add(it)
                }
            }

            val peek = undoHistory.lastOrNull()
            peek?.let{
                input = it
            }
        }
    }

    fun redo() {
        val pop = redoHistory.removeLastOrNull()
        pop?.let {
            if (it.text.isNotEmpty()) {
                undoHistory.add(it)
                input = it
            }
        }
    }
}