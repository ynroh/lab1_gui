package src.main.kotlin.model.parser

import src.main.kotlin.model.parser.states.DefinitionState
import src.main.kotlin.viewModel.ScannerViewModel

class Parser {
    fun parseCode(viewModel: ScannerViewModel){
        viewModel.parserErrors.clear()
        viewModel.currentState = DefinitionState()
        viewModel.checkedLexeme = 0
        viewModel.expectedLexeme = 0
        viewModel.scanResultText = ""

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