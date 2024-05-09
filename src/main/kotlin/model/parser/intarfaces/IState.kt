package src.main.kotlin.model.parser.intarfaces

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.viewModel.ScannerViewModel

abstract class State {
    abstract fun Handle(viewModel: ScannerViewModel)
    final fun IsBoundaryLexeme(viewModel: ScannerViewModel): Boolean{
        var bound = 2;

        for(i in 1 until bound) {
            if(viewModel.currentLexemeIndex+i < viewModel.lexemes.size) {
                if(viewModel.lexemes[viewModel.currentLexemeIndex+i].getType() == viewModel.expectedLexeme) {
                    if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.OPEN_C_SCOPE) {
                        viewModel.currentLexemeIndex += i + 1
                    }
                    return true
                }
            }
        }

        /*when(viewModel.expectedLexeme){
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
            else -> {return false}*/
        //}
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

    final fun isNextLexeme(viewModel: ScannerViewModel):Boolean{
        when(viewModel.expectedLexeme){
            LexemeType.KEY_WORD_FUN ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.IDENTIFIER) {
                            viewModel.currentLexemeIndex++
                        }
                    }
                    return true
                }
            LexemeType.IDENTIFIER ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPEN_C_SCOPE
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COLON
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.CLOSE_F_SCOPE
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPERATOR){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.OPEN_C_SCOPE
                            || viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.COLON
                            || viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.CLOSE_F_SCOPE
                            || viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.OPERATOR
                        ) {
                            viewModel.currentLexemeIndex++
                        }
                    }
                    return true
                }
            LexemeType.OPEN_C_SCOPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.IDENTIFIER) {
                            viewModel.currentLexemeIndex++
                        }
                    }
                    return true
                }
            LexemeType.CLOSE_C_SCOPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COLON){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.COLON) {
                            viewModel.currentLexemeIndex++
                        }
                    }
                    return true
                }
            LexemeType.COLON ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.KEY_WORD_TYPE){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.KEY_WORD_TYPE) {
                            viewModel.currentLexemeIndex++
                        }
                    }
                    return true
                }
            LexemeType.KEY_WORD_TYPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.CLOSE_C_SCOPE
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COMMA
                    || viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.OPEN_F_SCOPE){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.CLOSE_C_SCOPE
                            || viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.COMMA
                            || viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.OPEN_F_SCOPE
                        ) {
                            viewModel.currentLexemeIndex++
                        }
                        return true
                    }
                }
            LexemeType.COMMA ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.IDENTIFIER) {
                            viewModel.currentLexemeIndex++
                        }
                        return true
                    }
                }
            LexemeType.OPEN_F_SCOPE ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.KEY_WORD_RETURN){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.KEY_WORD_RETURN) {
                            viewModel.currentLexemeIndex++
                        }
                    }
                    return true
                }
            LexemeType.KEY_WORD_RETURN ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER){
                    if(viewModel.lexemes[viewModel.currentLexemeIndex+1].getType()== LexemeType.IDENTIFIER) {
                        viewModel.currentLexemeIndex++
                    }
                    return true
                }
            LexemeType.OPERATOR ->
                if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER){
                    if(viewModel.currentLexemeIndex+1<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex + 1].getType() == LexemeType.IDENTIFIER) {
                            viewModel.currentLexemeIndex++
                        }
                    }
                    return true
                }
            else -> {return false}
            }
            return false
    }
}