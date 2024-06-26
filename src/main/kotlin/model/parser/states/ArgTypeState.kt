package src.main.kotlin.model.parser.states

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel
import srs.main.kotlin.model.parser.states.ArgSeparatorState

class ArgTypeState: State() {
    override fun Handle(viewModel: ScannerViewModel) {
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.KEY_WORD_TYPE) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                if (IsBoundaryLexeme(viewModel, skippedLexemes) || isNextLexeme(viewModel)) {
                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes)+"Ожидался тип",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex(),
                            "Int",
                            viewModel.lexemes[startIndex+1]
                        )
                    )
                    viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                    break
                } else {
                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes) + "Ожидался тип",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex(),
                            "Int",
                            viewModel.lexemes[startIndex+1]
                        )
                    )
                    viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                    viewModel.currentLexemeIndex++
                    break

                }
            }
        }
        else{
            viewModel.currentLexemeIndex++
        }

        viewModel.expectedLexeme = LexemeType.CLOSE_C_SCOPE

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
            viewModel.currentState = ArgSeparatorState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}