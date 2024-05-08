package src.main.kotlin.model.parser.states

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel

class VarSeparatorState: State() {
    override fun Handle(viewModel: ScannerViewModel) {
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(startIndex == viewModel.lexemes.size-1){
            if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.CLOSE_F_SCOPE) {
                viewModel.parserErrors.add(
                    ParserError(
                        "Ошибка: ${viewModel.lexemes[viewModel.currentLexemeIndex].getValue()}. " + "Ожидалось '}'",
                        viewModel.lexemes[viewModel.currentLexemeIndex].getStartIndex(),
                        viewModel.lexemes[viewModel.currentLexemeIndex].getEndIndex(),
                        "}"
                    )
                )
                viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
            }
        }
        else {
            if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.CLOSE_F_SCOPE
                && viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.OPERATOR
            ) {
                skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
                for (i in startIndex until viewModel.lexemes.size) {
                    if (IsBoundaryLexeme(viewModel)) {
                        viewModel.parserErrors.add(
                            ParserError(
                                whiskers(skippedLexemes) + "Ожидалось '}'",
                                skippedLexemes[0].getStartIndex(),
                                skippedLexemes.last().getEndIndex(),
                                "}"
                            )
                        )
                        viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                        break
                    } else {
                        viewModel.currentLexemeIndex++
                        if (viewModel.currentLexemeIndex < viewModel.lexemes.size) {
                            if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.CLOSE_F_SCOPE
                                && viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.OPERATOR
                            ) {
                                viewModel.parserErrors.add(
                                    ParserError(
                                        whiskers(skippedLexemes) + "Ожидалось '}'",
                                        skippedLexemes[0].getStartIndex(),
                                        skippedLexemes.last().getEndIndex(),
                                        "}"
                                    )
                                )
                                viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                                break
                            }
                        }
                    }
                }
            } else {
                viewModel.currentLexemeIndex++
            }
        }

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size){
            if(viewModel.lexemes[startIndex].getType() == LexemeType.OPERATOR) {
                viewModel.expectedLexeme = LexemeType.IDENTIFIER
                viewModel.currentState = VarNameState()
                viewModel.currentState.Handle(viewModel)
            }
            else{
                if(viewModel.lexemes[startIndex].getType() == LexemeType.CLOSE_F_SCOPE){
                    if(viewModel.lexemes.size-1>startIndex) {
                        var res: String = String()
                        for (i in startIndex + 1 until viewModel.lexemes.size) {
                            res += viewModel.lexemes[i].getValue()
                        }
                            viewModel.parserErrors.add(
                                ParserError(
                                "Неожиданный ввод: " + res,
                                viewModel.lexemes[startIndex+1].getStartIndex(),
                                viewModel.lexemes.last().getEndIndex(),
                                    "}"
                            )
                        )
                        viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                    }
                }
            }
        }
    }
}