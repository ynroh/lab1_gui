package src.main.kotlin.model.parser.states

//import src.main.kotlin.model.parser.states.OpenArgState
import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel
class FunNameState: State() {
    /*override fun Handle(viewModel: ScannerViewModel){
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
    }*/

    override fun Handle(viewModel: ScannerViewModel){
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.IDENTIFIER) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                if (IsBoundaryLexeme(viewModel)) {
                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes)+"Ожидался идентификатор",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex()
                        )
                    )
                    break
                } else {
                    viewModel.currentLexemeIndex++
                    if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.IDENTIFIER) {
                            viewModel.parserErrors.add(
                                ParserError(
                                    whiskers(skippedLexemes) + "Ожидался идентификатор",
                                    skippedLexemes[0].getStartIndex(),
                                    skippedLexemes.last().getEndIndex()
                                )
                            )
                            break
                        }
                    }
                }
            }
        }
        else{
            viewModel.currentLexemeIndex++
        }
        viewModel.expectedLexeme = LexemeType.OPEN_C_SCOPE

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
            viewModel.currentState = OpenArgState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}
