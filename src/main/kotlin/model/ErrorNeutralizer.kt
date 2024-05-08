package src.main.kotlin.model

import src.main.kotlin.viewModel.ScannerViewModel

class ErrorNeutralizer {
    fun neutralizeErrors(viewModel: ScannerViewModel){
        var result = String()
        var fixedSymbol = String()
        //идти по символвам если символ не ошибка добавляем в строку
        var index = 0
        while (index<viewModel.currentContent.length){
            for(i in 0 until viewModel.parserErrors.size){
                if(index == viewModel.parserErrors[i].startIndex){
                    val value = if(viewModel.currentContent[viewModel.parserErrors[i].startIndex] == ' ') {viewModel.parserErrors[i].value}  else {viewModel.parserErrors[i].value + " "}
                    if(viewModel.errorLexemes[i].getType() != LexemeType.INVALID_LEXEME) {
                        fixedSymbol = value + viewModel.errorLexemes[i].getValue()
                    }
                    else{
                        fixedSymbol = value
                    }
                    result += fixedSymbol
                    index = viewModel.parserErrors[i].startIndex + 1
                }
                else {
                    result += viewModel.currentContent[index]
                    index++
                }
            }
        }
/*
        for (i in 0 until viewModel.parserErrors.size){
            val value = if(viewModel.currentContent[viewModel.parserErrors[i].startIndex] == ' ') "fun" else "fun "
            when(viewModel.parserErrors[i].value){
                "fun" ->
                    if(viewModel.errorLexemes[i].getType() != LexemeType.INVALID_LEXEME) {
                        result = viewModel.currentContent.substring(0, viewModel.parserErrors[i].startIndex) + value + result.substring(viewModel.parserErrors[i].startIndex)
                    }
                else{
                    result = viewModel.currentContent.substring(0, viewModel.parserErrors[i].startIndex) + value + result.substring(viewModel.parserErrors[i].endIndex)
                }
            }
        }*/

        viewModel.currentContent = result
    }

}