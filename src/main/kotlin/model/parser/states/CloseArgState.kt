package src.main.kotlin.model.parser.states

import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel

class CloseArgState: IState {
    override fun Handle(viewModel: ScannerViewModel){
        if(viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.checkedLexeme]) {
            viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 7)
        }
        else{
            viewModel.checkedLexeme ++
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.checkedLexeme]) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 8)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }

        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.checkedLexeme]) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 9)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }

        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.checkedLexeme]) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 10)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }

        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            viewModel.currentState = FuncTypeState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}