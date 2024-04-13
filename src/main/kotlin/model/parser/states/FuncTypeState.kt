package src.main.kotlin.model.parser.states

import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel

class FuncTypeState:IState {
    override fun Handle(viewModel: ScannerViewModel) {
        if(viewModel.lexemes[viewModel.checkedLexeme].getType() != LexemeType.COLON) {
            viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 11)
        }
        else{
            viewModel.checkedLexeme ++
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != LexemeType.KEY_WORD_TYPE) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 12)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != LexemeType.OPEN_F_SCOPE) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 13)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }

       if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            viewModel.currentState = ReturnState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}