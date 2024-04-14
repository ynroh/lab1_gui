package src.main.kotlin.model.parser.states

import src.main.kotlin.model.parser.states.OpenArgState
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel
class FunNameState: IState {
    override fun Handle(viewModel: ScannerViewModel){
        if(viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.expectedLexeme]) {
            viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, viewModel.expectedLexeme)
        }
        else{
            viewModel.checkedLexeme ++
            viewModel.expectedLexeme++

        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.expectedLexeme]) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, viewModel.expectedLexeme)
            }
            else{
                viewModel.checkedLexeme ++
                viewModel.expectedLexeme++
            }
        }

        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            viewModel.currentState = OpenArgState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}