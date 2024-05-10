package src.main.kotlin.model

import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.viewModel.ScannerViewModel

class ErrorNeutralizer {
    fun neutralizeErrors(viewModel: ScannerViewModel){
        var result = String()
        var fixedSymbol = String()
        var index = 0
        while (index<viewModel.currentContent.length){
            for(i in 0 until viewModel.parserErrors.size){
                if(index == viewModel.parserErrors[i].startIndex){
                    if((viewModel.errorLexemes[i].getType() != LexemeType.INVALID_LEXEME)
                        && IsNextLexeme(viewModel.parserErrors[i].expected,viewModel.errorLexemes[i] )) {
                        fixedSymbol = viewModel.parserErrors[i].expected + " " + viewModel.errorLexemes[i].getValue()
                    }
                    else{
                        val index = viewModel.lexemes.indexOf(viewModel.errorLexemes[i])
                        if(index>0) {
                            if (viewModel.lexemes[index - 1].getType() == viewModel.errorLexemes[i].getType())
                                fixedSymbol += ""
                            else{
                                fixedSymbol = viewModel.parserErrors[i].expected
                            }
                        }
                        else{
                            fixedSymbol = viewModel.parserErrors[i].expected
                        }
                    }
                    result += fixedSymbol
                    index = viewModel.parserErrors[i].endIndex + 1
                }
                else {
                    result += viewModel.currentContent[index]
                    index++
                }
            }
        }
        viewModel.currentContent = result
    }

   fun IsBoundaryLexeme(parserError: ParserError): Boolean{
        when(parserError.expected){
            "fun" ->
                if(parserError.nextLexeme.getType() == LexemeType.IDENTIFIER
                    || parserError.nextLexeme.getType() == LexemeType.OPEN_C_SCOPE)
                    return true
            "A" ->
                if(parserError.nextLexeme.getType() == LexemeType.OPEN_C_SCOPE
                    || parserError.nextLexeme.getType() == LexemeType.COLON
                    || parserError.nextLexeme.getType() == LexemeType.CLOSE_F_SCOPE
                    || parserError.nextLexeme.getType() == LexemeType.OPERATOR)
                    return true
            "(" ->
                if(parserError.nextLexeme.getType() == LexemeType.IDENTIFIER
                    || parserError.nextLexeme.getType() == LexemeType.COLON)
                    return true
            ")" ->
                if(parserError.nextLexeme.getType() == LexemeType.COLON
                    || parserError.nextLexeme.getType() == LexemeType.KEY_WORD_TYPE)
                    return true
            ":" ->
                if(parserError.nextLexeme.getType() == LexemeType.KEY_WORD_TYPE
                    || parserError.nextLexeme.getType() == LexemeType.COMMA
                    || parserError.nextLexeme.getType() == LexemeType.OPEN_F_SCOPE)
                    return true
            "Int" ->
                if(parserError.nextLexeme.getType() == LexemeType.CLOSE_C_SCOPE
                    || parserError.nextLexeme.getType() == LexemeType.COMMA
                    || parserError.nextLexeme.getType() == LexemeType.OPEN_F_SCOPE)
                    return true
            "," ->
                if(parserError.nextLexeme.getType() == LexemeType.IDENTIFIER
                    || parserError.nextLexeme.getType() == LexemeType.COLON)
                    return true
            "{" ->
                if(parserError.nextLexeme.getType() == LexemeType.IDENTIFIER
                    || parserError.nextLexeme.getType() == LexemeType.KEY_WORD_RETURN)
                    return true
            "return" ->
                if(parserError.nextLexeme.getType() == LexemeType.IDENTIFIER
                    || parserError.nextLexeme.getType() == LexemeType.OPERATOR)
                    return true
            "+" ->
                if(parserError.nextLexeme.getType() == LexemeType.IDENTIFIER
                    || parserError.nextLexeme.getType() == LexemeType.CLOSE_F_SCOPE)
                    return true
            else -> {return false}
        }
        return false
    }

    fun IsNextLexeme(expected: String, currentLexeme: Lexeme): Boolean{
        when(expected){
            "fun" ->
                if(currentLexeme.getType() == LexemeType.IDENTIFIER)
                    return true
            "A" ->
                if(currentLexeme.getType() == LexemeType.OPEN_C_SCOPE
                    || currentLexeme.getType() == LexemeType.COLON
                    || currentLexeme.getType() == LexemeType.CLOSE_F_SCOPE
                    || currentLexeme.getType() == LexemeType.OPERATOR)
                    return true
            ")" ->
                if(currentLexeme.getType() == LexemeType.COLON)
                    return true
            ":" ->
                if(currentLexeme.getType() == LexemeType.KEY_WORD_TYPE)
                    return true
            "Int" ->
                if(currentLexeme.getType() == LexemeType.CLOSE_C_SCOPE
                    || currentLexeme.getType() == LexemeType.COMMA
                    || currentLexeme.getType() == LexemeType.OPEN_F_SCOPE)
                    return true
            "," ->
                if(currentLexeme.getType() == LexemeType.IDENTIFIER)
                    return true
            "{" ->
                if(currentLexeme.getType() == LexemeType.IDENTIFIER)
                    return true
            "return" ->
                if(currentLexeme.getType() == LexemeType.IDENTIFIER
                    || currentLexeme.getType() == LexemeType.OPERATOR)
                    return true
            "+" ->
                if(currentLexeme.getType() == LexemeType.IDENTIFIER)
                    return true
            else -> {return false}
        }
        return false
    }
}