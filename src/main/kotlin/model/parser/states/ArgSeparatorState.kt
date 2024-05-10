package srs.main.kotlin.model.parser.states

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.model.parser.states.ArgColonState
import src.main.kotlin.model.parser.states.ArgNameState
import src.main.kotlin.model.parser.states.ColonState
import src.main.kotlin.viewModel.ScannerViewModel

class ArgSeparatorState: State() {
    override fun Handle(viewModel: ScannerViewModel) {
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.CLOSE_C_SCOPE
            && viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.COMMA) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                if (IsBoundaryLexeme(viewModel) || isNextLexeme(viewModel)) {
                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes)+"Ожидалось ')'",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex(),
                            ")",
                            viewModel.lexemes[startIndex+1]
                        )
                    )
                    viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                    break
                } else {
                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes) + "Ожидалось ')'",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex(),
                            ")",
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

        viewModel.expectedLexeme = LexemeType.COLON

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size){
            if(viewModel.lexemes[startIndex].getType() == LexemeType.COMMA) {
                viewModel.expectedLexeme = LexemeType.IDENTIFIER
                viewModel.currentState = ArgNameState()
            }
            else{
                viewModel.currentState = ArgColonState()
            }
            viewModel.currentState.Handle(viewModel)
        }
    }
}