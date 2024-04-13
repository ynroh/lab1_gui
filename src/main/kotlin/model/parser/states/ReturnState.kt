package src.main.kotlin.model.parser.states

import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.intarfaces.IState
import src.main.kotlin.viewModel.ScannerViewModel

class ReturnState :IState{
    override fun Handle(viewModel: ScannerViewModel) {
        if(viewModel.lexemes[viewModel.checkedLexeme].getType() != LexemeType.KEY_WORD_RETURN) {
            viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 14)
        }
        else{
            viewModel.checkedLexeme ++
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != LexemeType.IDENTIFIER) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 15)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != LexemeType.OPERATOR) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 16)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != LexemeType.IDENTIFIER) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 17)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }
        if(viewModel.checkedLexeme<viewModel.lexemes.size) {
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != LexemeType.CLOSE_F_SCOPE) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, 18)
            }
            else{
                viewModel.checkedLexeme ++
            }
        }
    }
}