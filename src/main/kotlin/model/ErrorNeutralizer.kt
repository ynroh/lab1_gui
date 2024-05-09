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
                    //val value = if(viewModel.currentContent[viewModel.parserErrors[i].startIndex] == ' ') {viewModel.parserErrors[i].expected}  else {viewModel.parserErrors[i].expected + " "}
                    if(viewModel.errorLexemes[i].getType() != LexemeType.INVALID_LEXEME ||IsBoundaryLexeme(viewModel.parserErrors[i]) ) {
                        fixedSymbol = viewModel.parserErrors[i].expected + " " + viewModel.errorLexemes[i].getValue()
                    }
                    else{
                        fixedSymbol = viewModel.parserErrors[i].expected
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
}