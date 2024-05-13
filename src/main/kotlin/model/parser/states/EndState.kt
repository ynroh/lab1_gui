package srs.main.kotlin.model.parser.states

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.model.parser.states.VarNameState
import src.main.kotlin.viewModel.ScannerViewModel

class EndState: State() {
    override fun Handle(viewModel: ScannerViewModel) {
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(startIndex == viewModel.lexemes.size-1){
            if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.END) {
                viewModel.parserErrors.add(
                    ParserError(
                        "Ошибка: ${viewModel.lexemes[viewModel.currentLexemeIndex].getValue().replace("\n","")}. " + "Ожидалось ';'",
                        viewModel.lexemes[viewModel.currentLexemeIndex].getStartIndex(),
                        viewModel.lexemes[viewModel.currentLexemeIndex].getEndIndex(),
                        ";",
                        nextLexeme = if(startIndex+1 < viewModel.lexemes.size) { viewModel.lexemes[startIndex + 1] } else{viewModel.lexemes[startIndex ]}

                    )
                )
                viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
            }
        }
        else {
            if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.END) {
                skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
                for (i in startIndex until viewModel.lexemes.size) {
                    if (IsBoundaryLexeme(viewModel,skippedLexemes) || isNextLexeme(viewModel)) {
                        viewModel.parserErrors.add(
                            ParserError(
                                whiskers(skippedLexemes) + "Ожидалось ';'",
                                skippedLexemes[0].getStartIndex(),
                                skippedLexemes.last().getEndIndex(),
                                ";",
                                nextLexeme = if(startIndex+1 < viewModel.lexemes.size) { viewModel.lexemes[startIndex + 1] } else{viewModel.lexemes[startIndex ]}

                            )
                        )
                        viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                        break
                    } else {
                        viewModel.parserErrors.add(
                            ParserError(
                                whiskers(skippedLexemes) + "Ожидалось ';'",
                                skippedLexemes[0].getStartIndex(),
                                skippedLexemes.last().getEndIndex(),
                                ";",
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
        }


        if(viewModel.lexemes[startIndex].getType() == LexemeType.END){
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
                        ";",
                        nextLexeme = if(startIndex+1 < viewModel.lexemes.size) { viewModel.lexemes[startIndex + 1] } else{viewModel.lexemes[startIndex ]}

                    )
                )
                viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
            }
        }
    }
}