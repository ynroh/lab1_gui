package src.main.kotlin.model.parser.states

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel

class VarNameState: State() {
    override fun Handle(viewModel: ScannerViewModel) {
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
        viewModel.expectedLexeme = LexemeType.CLOSE_F_SCOPE

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
            viewModel.currentState = VarSeparatorState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}