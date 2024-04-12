package src.main.kotlin.viewModel

import androidx.compose.runtime.*
import src.main.kotlin.model.parser.states.DefinitionState
import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.UndoRedoState
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.IState


class ScannerViewModel(undoRedoState: UndoRedoState) {
    val expectedInput = listOf("fun", " ", "identifier", "(", "identifier", ":",
        "type", ",", "identifier", ":", "type", ")", ":", "type", "{", "return", " ", "identifier", "sign", "identifier")
    var currentFilePath by mutableStateOf("")
    var currentContent by mutableStateOf("")
    var isChangesSaved by mutableStateOf(false)
    val undoRedoState by mutableStateOf(undoRedoState)
    var scanResultText by mutableStateOf("")
    var lexemes = mutableListOf<Lexeme>()
    var parserErrors = mutableListOf<ParserError>()
    var currentState: IState = DefinitionState()
    var expectedLexeme = 0

    fun skipIncorrectLexemes(currentIndex: Int, expectedLexemeIndex: Int){

            var index1 = currentIndex
            var index2 = expectedLexemeIndex
            expectedLexeme = expectedLexemeIndex

            while (index1 < lexemes.size && index2 < expectedInput.size) {
                if (lexemes[index1].getValue() == expectedInput[index2]) {
                    expectedLexeme = index1+1
                    parserErrors.add(ParserError(value = "Ожидалось ${expectedInput[expectedLexemeIndex]}", currentIndex, lexemes[index1-1].getEndIndex()))
                    println("Ожидалось ${expectedInput[expectedLexemeIndex]} c ${lexemes[currentIndex].getStartIndex()} по ${lexemes[index1-1].getEndIndex()}")
                    break
                }
                index1++
                if (index1 >= lexemes.size) {
                    index1 = currentIndex
                    index2++
                }
            }

            if (index1 >= lexemes.size || index2 >= expectedInput.size) {
                parserErrors.add(ParserError(value = "Ожидалось ${expectedInput[expectedLexemeIndex]}", currentIndex, lexemes.last().getEndIndex()))
                println("Ожидалось ${expectedInput[expectedLexemeIndex]} c ${lexemes[currentIndex].getStartIndex()} по ${lexemes[currentIndex].getStartIndex()+lexemes[currentIndex].getValue().length}")
            }
    }
}