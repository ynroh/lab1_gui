package src.main.kotlin.viewModel

import androidx.compose.runtime.*
import src.main.kotlin.model.parser.states.DefinitionState
import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.UndoRedoState
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.IState


class ScannerViewModel(undoRedoState: UndoRedoState) {
    val expectedInput = listOf(LexemeType.KEY_WORD_FUN, LexemeType.IDENTIFIER, LexemeType.OPEN_C_SCOPE, LexemeType.IDENTIFIER, LexemeType.COLON,
        LexemeType.KEY_WORD_TYPE, LexemeType.COMMA, LexemeType.IDENTIFIER, LexemeType.COLON, LexemeType.KEY_WORD_TYPE, LexemeType.CLOSE_C_SCOPE, LexemeType.COLON,
        LexemeType.KEY_WORD_TYPE, LexemeType.OPEN_F_SCOPE, LexemeType.KEY_WORD_RETURN, LexemeType.IDENTIFIER, LexemeType.OPERATOR,
        LexemeType.IDENTIFIER, LexemeType.CLOSE_F_SCOPE)

    var currentFilePath by mutableStateOf("")
    var currentContent by mutableStateOf("")
    var isChangesSaved by mutableStateOf(false)
    val undoRedoState by mutableStateOf(undoRedoState)
    var scanResultText by mutableStateOf("")
    var lexemes = mutableListOf<Lexeme>()
    var parserErrors = mutableListOf<ParserError>()
    var currentState: IState = DefinitionState()
    var checkedLexeme = 0

    fun skipIncorrectLexemes(currentIndex: Int, expectedLexemeIndex: Int){

            var index1 = currentIndex //номер лексемы которая сейчас (из списка текущих лексем)
            var index2 = expectedLexemeIndex//номер лексемы которая должна быть (из списка ожидаемых лексем)
            var skippedLexemes = arrayListOf<Lexeme>()
            checkedLexeme = expectedLexemeIndex
            var flag = true

            while(index1 < lexemes.size && index2 < expectedInput.size){
                if (lexemes[index1].getType() == expectedInput[index2]) {
                    if(flag) checkedLexeme = index1 else checkedLexeme=index2+1
                    println("след.проверяемая: ${checkedLexeme}" )
                    for(i in skippedLexemes)
                        println(i.getType())
                    parserErrors.add(ParserError(value = "Ожидалось ${expectedInput[expectedLexemeIndex]}",lexemes[currentIndex].getStartIndex(),skippedLexemes.last().getEndIndex()))
                    println("Ожидалось ${expectedInput[expectedLexemeIndex]} c ${lexemes[currentIndex].getStartIndex()} по ${skippedLexemes.last().getEndIndex()}")
                    break
                }
                else{
                    skippedLexemes.add(lexemes[index1])
                }
                index1++
                if (index1 >= lexemes.size) {
                    index1 = currentIndex
                    flag = false
                    index2++
                }
            }

            if (index1 >= lexemes.size || index2 >= expectedInput.size) {
                parserErrors.add(ParserError(value = "Ожидалось ${expectedInput[expectedLexemeIndex]}", currentIndex, lexemes.last().getEndIndex()))
                println("Ожидалось ${expectedInput[expectedLexemeIndex]} c ${lexemes[currentIndex].getStartIndex()} по ${lexemes[currentIndex].getStartIndex()+lexemes[currentIndex].getValue().length}")
            }
    }

}