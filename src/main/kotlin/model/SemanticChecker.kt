package model

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType
import src.main.kotlin.model.parser.ParserError
import src.main.kotlin.viewModel.ScannerViewModel

class SemanticChecker {
    fun checkSemantics(viewModel: ScannerViewModel){
        checkArgsTypes(viewModel)
        checkFunTypes(viewModel)
        checkArgsIdentifiers(viewModel)
    }

    private fun checkArgsTypes(viewModel: ScannerViewModel) {
        val typeLexemes = (viewModel.lexemes.filter { it.getType() == LexemeType.KEY_WORD_TYPE })
        if(typeLexemes.any { it.getValue() == "String" }){
            if(typeLexemes.all { it.getValue() == "String" })
            {
                val operatorLexemes = (viewModel.lexemes.filter { it.getType() == LexemeType.OPERATOR })
                if(operatorLexemes.any { it.getValue() != "+" }){
                    viewModel.semanticErrors.add("Для типа String существует только операция сложения")
                }
            }
            else {
                viewModel.semanticErrors.add("Невозможно выполнять математические операции с типом String")
            }
        }
    }
    private fun checkFunTypes(viewModel: ScannerViewModel){
        val typeLexemes = (viewModel.lexemes.filter { it.getType() == LexemeType.KEY_WORD_TYPE }).toMutableList()
        val funType = typeLexemes.last()
        typeLexemes.removeAt(typeLexemes.lastIndex)

        when(funType.getValue()){
            "Int" ->
                if(typeLexemes.any { it.getValue() == "Double" } || typeLexemes.any { it.getValue() == "Float" } || typeLexemes.any { it.getValue() == "String" }){
                    viewModel.semanticErrors.add("Функция должна возвращать выражение типа Int")
                }
            "String" ->
                if(! typeLexemes.all { it.getValue() == "String" } ){
                    viewModel.semanticErrors.add("Функция должна возвращать выражение типа String")
                }
            "Float" ->
                if(typeLexemes.any { it.getValue() == "Double" } || ! typeLexemes.any { it.getValue() == "Float"} || typeLexemes.any { it.getValue() == "String" }){
                    viewModel.semanticErrors.add("Функция должна возвращать выражение типа Float")
                }
            "Long" ->
                if(typeLexemes.any { it.getValue() == "Double" } || typeLexemes.any { it.getValue() == "Float" } || typeLexemes.any { it.getValue() == "String" }){
                    viewModel.semanticErrors.add("Функция должна возвращать выражение типа Long или Int")
                }
        }
    }

    private fun checkArgsIdentifiers(viewModel: ScannerViewModel){
        val returnLexemeIndex = viewModel.lexemes.indexOfFirst{it.getType() == LexemeType.KEY_WORD_RETURN}
        val sublist = viewModel.lexemes.subList(0, returnLexemeIndex)
        val allIdentifiers = (sublist.filter { it.getType() == LexemeType.IDENTIFIER })

        val namesSet = mutableSetOf<String>()
        for (id in allIdentifiers) {
            if (!namesSet.add(id.getValue())) {
                viewModel.semanticErrors.add("Имя ${id.getValue()} уже используется")
            }
        }

        val argsIdentifiers = getArgsIdentifiers(viewModel)
        val varsIdentifiers = getValIdentifiers(viewModel)

        val missingElement = varsIdentifiers.firstOrNull { it !in argsIdentifiers }
        if(missingElement != null){
            viewModel.semanticErrors.add("Имя ${missingElement} не существует в данном контексте")
        }
    }

    private fun getArgsIdentifiers(viewModel: ScannerViewModel): MutableList<String>
    {
        val returnLexemeIndex = viewModel.lexemes.indexOfFirst{it.getType() == LexemeType.KEY_WORD_RETURN}
        val sublist = viewModel.lexemes.subList(0, returnLexemeIndex)
        var res = (sublist.filter { it.getType() == LexemeType.IDENTIFIER }.toMutableList())
        res.removeAt(0)
        var result = mutableListOf<String>()
        for (i in res){
            result.add(i.getValue())
        }
        return result
    }

    private fun getValIdentifiers(viewModel: ScannerViewModel): MutableList<String>
    {
        val returnLexemeIndex = viewModel.lexemes.indexOfFirst{it.getType() == LexemeType.KEY_WORD_RETURN}
        val sublist = viewModel.lexemes.subList(returnLexemeIndex+1, viewModel.lexemes.lastIndex)
        var res = (sublist.filter { it.getType() == LexemeType.IDENTIFIER }.toMutableList())
        var result = mutableListOf<String>()
        for (i in res){
            result.add(i.getValue())
        }
        return result
    }
}