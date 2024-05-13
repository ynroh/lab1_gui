package src.main.kotlin.model.parser.states

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel
import srs.main.kotlin.model.parser.states.EndState

class VarSeparatorState: State() {
    override fun Handle(viewModel: ScannerViewModel) {
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
            if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.CLOSE_F_SCOPE
                && viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.OPERATOR
            ) {
                skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
                for (i in startIndex until viewModel.lexemes.size) {
                    if (IsBoundaryLexeme(viewModel,skippedLexemes) || isNextLexeme(viewModel)) {
                        viewModel.parserErrors.add(
                            ParserError(
                                whiskers(skippedLexemes) + "Ожидалось '}'",
                                skippedLexemes[0].getStartIndex(),
                                skippedLexemes.last().getEndIndex(),
                                "}",
                                nextLexeme = if(startIndex+1 < viewModel.lexemes.size) { viewModel.lexemes[startIndex + 1] } else{viewModel.lexemes[startIndex ]}

                            )
                        )
                        viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                        break
                    } else {
                        viewModel.parserErrors.add(
                            ParserError(
                                whiskers(skippedLexemes) + "Ожидалось '}'",
                                skippedLexemes[0].getStartIndex(),
                                skippedLexemes.last().getEndIndex(),
                                "}",
                                nextLexeme = if(startIndex+1 < viewModel.lexemes.size) { viewModel.lexemes[startIndex + 1] } else{viewModel.lexemes[startIndex ]}

                            )
                        )
                        viewModel.currentLexemeIndex++
                        viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                        break
                    }
                }
            } else {
                viewModel.currentLexemeIndex++
            }

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size){
            if(viewModel.lexemes[startIndex].getType() == LexemeType.OPERATOR) {
                viewModel.expectedLexeme = LexemeType.IDENTIFIER
                viewModel.currentState = VarNameState()
                viewModel.currentState.Handle(viewModel)
            }
            else{
                viewModel.expectedLexeme = LexemeType.END
                viewModel.currentState = EndState()
                viewModel.currentState.Handle(viewModel)
            }
        }
    }
}