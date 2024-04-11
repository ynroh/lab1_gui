package src.main.kotlin.model.parser.states

import src.main.kotlin.model.parser.states.OpenArgState
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel
class FunNameState: IState {
    override fun Handle(viewModel: ScannerViewModel){
        var i = viewModel.expectedLexeme+1
        var flag = false
        if(viewModel.lexemes[viewModel.expectedLexeme].getType() != LexemeType.IDENTIFIER){
            while(i <  viewModel.lexemes.size){
                if(viewModel.lexemes[i].getType()==LexemeType.IDENTIFIER) {
                    flag = true
                    viewModel.parserErrors.add(ParserError(value = "Неожиданный ввод",viewModel.expectedLexeme,i))
                    println("Неожиданный ввод c ${viewModel.lexemes[viewModel.expectedLexeme].getStartIndex()} по ${viewModel.lexemes[i-1].getEndIndex()}")
                    viewModel.expectedLexeme = i+1
                    break
                }
                else i++
            }
        if(!flag)
            viewModel.skipIncorrectLexemes(viewModel.lexemes[viewModel.expectedLexeme].getEndIndex(), viewModel.expectedLexeme)
        }

        else {
            viewModel.expectedLexeme++
        }
        if(viewModel.lexemes.size > viewModel.expectedLexeme) {
            if (viewModel.lexemes[viewModel.expectedLexeme].getValue() != viewModel.expectedInput[3])
                viewModel.skipIncorrectLexemes(viewModel.lexemes[viewModel.expectedLexeme].getEndIndex(), 3)

            // viewModel.currentState = OpenArgState()
        }
    }
}