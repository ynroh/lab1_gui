package src.main.kotlin.model.parser

import model.SemanticChecker
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.states.DefinitionState
import src.main.kotlin.viewModel.ScannerViewModel

class Parser(
    val semanticChecker: SemanticChecker
) {
    fun parseCode(viewModel: ScannerViewModel){
        viewModel.parserErrors.clear()
        viewModel.semanticErrors.clear()
        viewModel.currentState = DefinitionState()
        viewModel.currentLexemeIndex = 0
        viewModel.expectedLexeme = LexemeType.KEY_WORD_FUN
        viewModel.scanResultText = ""
        viewModel.errorLexemes.clear()

        if(viewModel.lexemes.size !=0){
            viewModel.currentState.Handle(viewModel)
        }

        if(viewModel.lexemes.last().getValue()!=";" && (!viewModel.parserErrors.any { it.expected == ";" })){
            viewModel.parserErrors.add(
                ParserError(
                    "Ожидалось ;",
                    viewModel.lexemes.last().getEndIndex() + 1,
                    viewModel.lexemes.last().getEndIndex() + 1,
                    ";",
                    nextLexeme = viewModel.lexemes.last()
                )
            )
        }


        if(viewModel.parserErrors.size > 0) {
            for (i in viewModel.parserErrors) {
                viewModel.scanResultText += "${i.value} ${i.position}\n"
            }
        }
        else {
            viewModel.scanResultText = "Ошибок не найдено"
        }

        /*else {
            semanticChecker.checkSemantics(viewModel)
            if(viewModel.semanticErrors.size > 0) {
                for (i in viewModel.semanticErrors) {
                    viewModel.scanResultText += i + "\n"
                }
            }
            else {
                viewModel.scanResultText = "Ошибок не найдено"
            }
        }*/
    }


}

