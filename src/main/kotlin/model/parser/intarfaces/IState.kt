package src.main.kotlin.model.parser.intarfaces

import src.main.kotlin.viewModel.ScannerViewModel

interface IState {
    fun Handle(viewModel: ScannerViewModel)
}