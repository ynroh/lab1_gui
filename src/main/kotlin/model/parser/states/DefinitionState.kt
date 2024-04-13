package src.main.kotlin.model.parser.states

import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel

class DefinitionState: IState{
    override fun Handle(viewModel: ScannerViewModel){
        var checkedIndex: Int = 0
        if (viewModel.lexemes[0].getType() != viewModel.expectedInput[0]) {
            viewModel.skipIncorrectLexemes(0, 0)
            checkedIndex=viewModel.checkedLexeme
        }
        else {
            viewModel.checkedLexeme +=1
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            viewModel.currentState = FunNameState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}