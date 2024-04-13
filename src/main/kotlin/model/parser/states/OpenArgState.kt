package src.main.kotlin.model.parser.states

import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel
import java.util.LinkedList

class OpenArgState:IState {
    override fun Handle(viewModel: ScannerViewModel){
        if(viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.checkedLexeme]) {
            viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 3)
        }
        else{
            viewModel.checkedLexeme ++
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.checkedLexeme]) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 4)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }

        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.checkedLexeme]) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 5)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }
        /*// -------

        var list = listOf(LexemeType.IDENTIFIER, LexemeType.COLON, LexemeType.KEY_WORD_TYPE)
        var beginExprectedLexemeIndex = 3;
        var expexctedLexemeIndex = beginExprectedLexemeIndex;
        while(viewModel.checkedLexeme<viewModel.lexemes.size && expexctedLexemeIndex < list.size){
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != list[expexctedLexemeIndex-beginExprectedLexemeIndex]) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, expexctedLexemeIndex)
            }
            else{
                viewModel.checkedLexeme ++
            }
            expexctedLexemeIndex++;
        }
        // -------*/
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            viewModel.currentState = CommaState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}