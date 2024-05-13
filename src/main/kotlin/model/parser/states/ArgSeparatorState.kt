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
        var temp = arrayListOf<Lexeme>()
        var flag = false
        var wrongComma = false
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.CLOSE_C_SCOPE
            && viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.COMMA) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                for(i in 1 .. 2){
                    if(viewModel.lexemes[viewModel.currentLexemeIndex+i].getType() == LexemeType.COMMA){
                        viewModel.currentLexemeIndex += i + 1
                        flag = true
                        for(i in temp){
                            skippedLexemes.add(i)
                        }
                        viewModel.parserErrors.add(
                            ParserError(
                                "Ожидалось ',' " + whiskers(skippedLexemes),
                                skippedLexemes[0].getStartIndex(),
                                skippedLexemes.last().getEndIndex(),
                                ",",
                                viewModel.lexemes[startIndex+1]
                            )
                        )
                        viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                        break
                    }
                    else{
                        temp.add(viewModel.lexemes[viewModel.currentLexemeIndex+i])
                    }
                }
                if(!flag) {
                    if (IsBoundaryLexeme(viewModel, skippedLexemes) || isNextLexeme(viewModel)) {
                        viewModel.parserErrors.add(
                            ParserError(
                                whiskers(skippedLexemes) + "Ожидалось ')'",
                                skippedLexemes[0].getStartIndex(),
                                skippedLexemes.last().getEndIndex(),
                                ")",
                                viewModel.lexemes[startIndex + 1]
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
                                viewModel.lexemes[startIndex + 1]
                            )
                        )
                        viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                        viewModel.currentLexemeIndex++
                        break
                    }
                }
            }
        }
        else{
            if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.COMMA){
                if(viewModel.currentLexemeIndex+1 < viewModel.lexemes.size){
                    if(viewModel.lexemes[viewModel.currentLexemeIndex+1].getType() == LexemeType.CLOSE_C_SCOPE) {
                        wrongComma = true
                        viewModel.parserErrors.add(
                            ParserError(
                                "Ошибка: " + viewModel.lexemes[viewModel.currentLexemeIndex].getValue() + ". Ожидалось ')'",
                                viewModel.lexemes[viewModel.currentLexemeIndex].getStartIndex(),
                                viewModel.lexemes[viewModel.currentLexemeIndex].getEndIndex(),
                                ")",
                                viewModel.lexemes[startIndex + 1]
                            )
                        )
                        viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                        viewModel.currentLexemeIndex += 2
                    }
                    else {
                        viewModel.currentLexemeIndex++
                    }
                }
            }
            else {
                viewModel.currentLexemeIndex++
            }
        }

        viewModel.expectedLexeme = LexemeType.COLON

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size){
            if((viewModel.lexemes[startIndex].getType() == LexemeType.COMMA && !wrongComma)
                || flag) {
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