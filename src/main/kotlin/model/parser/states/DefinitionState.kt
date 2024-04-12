package src.main.kotlin.model.parser.states

import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel

class DefinitionState: IState{
    override fun Handle(viewModel: ScannerViewModel){
        var expectedIndex: Int = 0
        if (viewModel.lexemes[0].getValue() != viewModel.expectedInput[0]) {
            viewModel.skipIncorrectLexemes(0, 0)
            expectedIndex=viewModel.expectedLexeme
        }
        else {
            expectedIndex +=1
        }
        if(viewModel.lexemes.size > expectedIndex) {
            if (viewModel.lexemes[expectedIndex].getValue() != " ") {
                viewModel.skipIncorrectLexemes(expectedIndex, 1)
            } else {
                viewModel.expectedLexeme = 2
            }
           /* viewModel.currentState = FunNameState()
            viewModel.currentState.Handle(viewModel)*/
        }

    }
}