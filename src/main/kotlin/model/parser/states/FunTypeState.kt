package src.main.kotlin.model.parser.states

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel
import srs.main.kotlin.model.parser.states.ArgSeparatorState

class FunTypeState: State() {
    override fun Handle(viewModel: ScannerViewModel) {
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.KEY_WORD_TYPE) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                if (IsBoundaryLexeme(viewModel) || isNextLexeme(viewModel)) {
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
                    viewModel.currentLexemeIndex++
                    if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.KEY_WORD_TYPE) {
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
                            break
                        }
                    }
                }
            }
        }
        else{
            viewModel.currentLexemeIndex++
        }

        viewModel.expectedLexeme = LexemeType.OPEN_F_SCOPE

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
            viewModel.currentState = OpenBodyState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}