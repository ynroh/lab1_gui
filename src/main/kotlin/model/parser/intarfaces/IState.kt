package src.main.kotlin.model.parser.intarfaces

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.viewModel.ScannerViewModel

abstract class State {
    abstract fun Handle(viewModel: ScannerViewModel)
    final fun IsBoundaryLexeme(viewModel: ScannerViewModel): Boolean{
        when(viewModel.expectedLexeme){
            LexemeType.KEY_WORD_FUN ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPEN_C_SCOPE)
                    return true
            LexemeType.IDENTIFIER ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPEN_C_SCOPE
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COLON
                        || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPEN_F_SCOPE
                            || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPERATOR)
                    return true
            LexemeType.OPEN_C_SCOPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COLON)
                    return true
            LexemeType.CLOSE_C_SCOPE -> TODO()
            LexemeType.COLON -> TODO()
            LexemeType.KEY_WORD_TYPE -> TODO()
            LexemeType.COMMA -> TODO()
            LexemeType.OPEN_F_SCOPE -> TODO()
            LexemeType.CLOSE_F_SCOPE -> TODO()
            LexemeType.KEY_WORD_RETURN -> TODO()
            LexemeType.OPERATOR -> TODO()
            LexemeType.INVALID_LEXEME -> TODO()
            LexemeType.SEPARATOR -> TODO()
        }
        return false
    }

}