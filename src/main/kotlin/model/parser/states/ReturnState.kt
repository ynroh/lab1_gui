package src.main.kotlin.model.parser.states


import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.model.parser.intarfaces.State
import src.main.kotlin.viewModel.ScannerViewModel

class ReturnState :State(){
    override fun Handle(viewModel: ScannerViewModel) {
        var skippedLexemes = arrayListOf<Lexeme>()
        var startIndex = viewModel.currentLexemeIndex
        if(viewModel.lexemes[viewModel.currentLexemeIndex].getType() != LexemeType.KEY_WORD_RETURN) {
            skippedLexemes.add(viewModel.lexemes[viewModel.currentLexemeIndex])
            for (i in startIndex until viewModel.lexemes.size) {
                if (IsBoundaryLexeme(viewModel) || isNextLexeme(viewModel)) {
                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes)+"Ожидалось ключевое слово return",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex(),
                            "return",
                            viewModel.lexemes[startIndex+1]
                        )
                    )
                    viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                    break
                } else {

                    viewModel.parserErrors.add(
                        ParserError(
                            whiskers(skippedLexemes) + "Ожидалось ключевое слово return",
                            skippedLexemes[0].getStartIndex(),
                            skippedLexemes.last().getEndIndex(),
                            "return",
                            viewModel.lexemes[startIndex+1]
                        )
                    )
                    viewModel.currentLexemeIndex++
                    viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                    break
                    /*viewModel.currentLexemeIndex++
                    if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
                        if (viewModel.lexemes[viewModel.currentLexemeIndex].getType() == LexemeType.KEY_WORD_RETURN) {
                            viewModel.parserErrors.add(
                                ParserError(
                                    whiskers(skippedLexemes) + "Ожидалось ключевое слово return",
                                    skippedLexemes[0].getStartIndex(),
                                    skippedLexemes.last().getEndIndex(),
                                    "return",
                                    viewModel.lexemes[startIndex+1]
                                )
                            )
                            viewModel.errorLexemes.add(viewModel.lexemes[startIndex])
                            break
                        }
                    }*/
                }
            }
        }
        else{
            viewModel.currentLexemeIndex++
        }

        viewModel.expectedLexeme = LexemeType.IDENTIFIER

        if(viewModel.currentLexemeIndex<viewModel.lexemes.size) {
            viewModel.currentState = VarNameState()
            viewModel.currentState.Handle(viewModel)
        }
    }
}
