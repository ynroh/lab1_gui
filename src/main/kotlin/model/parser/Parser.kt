package src.main.kotlin.model.parser

import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.states.DefinitionState
import src.main.kotlin.viewModel.ScannerViewModel

class Parser {
    fun parseCode(viewModel: ScannerViewModel){
        viewModel.parserErrors.clear()
        viewModel.currentState = DefinitionState()
        viewModel.currentLexemeIndex = 0
        viewModel.expectedLexeme = LexemeType.KEY_WORD_FUN
        viewModel.scanResultText = ""
        viewModel.errorLexemes.clear()

        if(viewModel.lexemes.size !=0){
            viewModel.currentState.Handle(viewModel)
        }

        if(viewModel.parserErrors.size > 0) {
            for (i in viewModel.parserErrors) {
                viewModel.scanResultText += "${i.value} ${i.position}\n"
            }
        }
        else {
            viewModel.scanResultText = "Ошибок не найдено"
        }
    }
}