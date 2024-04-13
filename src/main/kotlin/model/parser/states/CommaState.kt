package src.main.kotlin.model.parser.states

import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel

class CommaState : IState{
    override fun Handle(viewModel: ScannerViewModel) {
        if (viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.checkedLexeme]) {
            viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 6)
        }
        else {
            viewModel.checkedLexeme +=1
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            viewModel.currentState = CloseArgState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}