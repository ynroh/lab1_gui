package src.main.kotlin.model.parser.states

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel

class ArgNameState: State() {
    override fun Handle(viewModel: ScannerViewModel) {
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.IDENTIFIER) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                if (IsBoundaryLexeme(viewModel)) {
                    viewModel.parserErrors.add(
                        ParserError(
                            "Ожидался идентификатор",
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

        viewModel.expectedLexeme = LexemeType.COLON

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
            viewModel.currentState = ArgTypeState()
            //viewModel.currentState.Handle(viewModel)
        }
    }
}