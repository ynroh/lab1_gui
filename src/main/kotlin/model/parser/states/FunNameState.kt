package src.main.kotlin.model.parser.states

//import src.main.kotlin.model.parser.states.OpenArgState
import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel
class FunNameState: State() {
    override fun Handle(viewModel: ScannerViewModel){
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.IDENTIFIER) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                if (IsBoundaryLexeme(viewModel, skippedLexemes) || isNextLexeme(viewModel)) {
                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes)+"Ожидался идентификатор",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex(),
                            "A",
                            viewModel.lexemes[startIndex+1]
                        )
                    )
                    viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                    break
                } else {
                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes) + "Ожидался идентификатор",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex(),
                            "A",
                            viewModel.lexemes[startIndex+1]
                        )
                    )
                    viewModel.currentLexemeIndex++
                    viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                    break
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
