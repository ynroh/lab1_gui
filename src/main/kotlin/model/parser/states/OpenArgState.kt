package src.main.kotlin.model.parser.states

//import model.parser.states.ArgNameState
import src.main.kotlin.model.parser.states.ArgNameState
import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel
import java.util.LinkedList

class OpenArgState:State(){
   /* override fun Handle(viewModel: ScannerViewModel){
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
            if (viewModel.lexemes[viewModel.checkedLexeme].getType() != viewModel.expectedInput[viewModel.expectedLexeme]) {
                viewModel.skipIncorrectLexemes(viewModel.checkedLexeme, viewModel.expectedLexeme)
            }
            else{
                viewModel.checkedLexeme ++
                viewModel.expectedLexeme++

            }
        }
    }*/

    override fun Handle(viewModel: ScannerViewModel){
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.OPEN_C_SCOPE) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                if (IsBoundaryLexeme(viewModel)) {
                    viewModel.parserErrors.add(
                        ParserError(
                            "Ожидалось '('",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex()
                        )
                    )
                    break
                } else {
                    skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
                    viewModel.currentLexemeIndex++
                }
            }
        }
        else{
            viewModel.currentLexemeIndex++
        }

        viewModel.expectedLexeme = LexemeType.IDENTIFIER

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
            viewModel.currentState = ArgNameState()
            viewModel.currentState.Handle(viewModel)
        }
    }

}