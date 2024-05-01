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
                        || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.CLOSE_F_SCOPE
                            || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPERATOR)
                    return true
            LexemeType.OPEN_C_SCOPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COLON)
                    return true
            LexemeType.CLOSE_C_SCOPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COLON
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.KEY_WORD_TYPE)
                    return true
            LexemeType.COLON ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.KEY_WORD_TYPE
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COMMA
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPEN_F_SCOPE)
                    return true
            LexemeType.KEY_WORD_TYPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.CLOSE_C_SCOPE
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COMMA
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPEN_F_SCOPE)
                    return true
            LexemeType.COMMA ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COLON)
                    return true
            LexemeType.OPEN_F_SCOPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.KEY_WORD_RETURN)
                    return true
            LexemeType.KEY_WORD_RETURN ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPERATOR)
                    return true
            LexemeType.OPERATOR ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.CLOSE_F_SCOPE)
                    return true
            else -> {return false}
        }
        return false
    }

    final fun whiskers(skippedLexeme: ArrayList<Lexeme>): String{
        var result: String = "Ошибка: "
        for(lexeme in skippedLexeme){
            result += lexeme.getValue()
        }
        result += ". "
        return result
    }
}