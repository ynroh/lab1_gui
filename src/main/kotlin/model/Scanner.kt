package src.main.kotlin.model


import src.main.kotlin.viewModel.ScannerViewModel

public class Scanner {
    //private  var Lexemes: MutableList<Lexeme> = mutableListOf()

    fun removeSpacesExceptAfterKeywords(strinput: String): String {
        val input = strinput.replace("\\s+".toRegex(), " ")
        val keywords = setOf("fun", "return")
        val builder = StringBuilder()
        var isSpaceAllowed = false

        for (char in input) {
            if (char.isWhitespace()) {
                if (isSpaceAllowed) {
                    builder.append(char)
                }
            } else {
                builder.append(char)
                isSpaceAllowed = keywords.any { builder.endsWith(it) }
            }
        }

        return builder.toString()
    }
    fun analyzeCode(viewModel: ScannerViewModel){
        val inputString = viewModel.currentContent
        println("AA " + inputString)
        viewModel.currentContent = inputString
        viewModel.lexemes.clear()
        var bitOfCode = String()
        var i = 0
        while (i < inputString.length){
            bitOfCode += inputString[i]
            if(inputString[i].isLetter())
            {
               val firstChar = i;

                while ((i + 1) < inputString.length && ((inputString[i + 1].isLetterOrDigit()) || inputString[i + 1] == '_'))
                {
                    i++;
                    bitOfCode += inputString[i];
                }

                when (bitOfCode)
                {
                    "fun" -> viewModel.lexemes.add(Lexeme(1, LexemeType.KEY_WORD, bitOfCode, firstChar, i))
                    "Int" -> viewModel.lexemes.add(Lexeme(2, LexemeType.KEY_WORD, bitOfCode, firstChar, i))
                    "return" -> viewModel.lexemes.add(Lexeme(3, LexemeType.KEY_WORD, bitOfCode, firstChar, i))
                    "Double" -> viewModel.lexemes.add(Lexeme(4, LexemeType.KEY_WORD, bitOfCode, firstChar, i))
                    "Float" -> viewModel.lexemes.add(Lexeme(5, LexemeType.KEY_WORD, bitOfCode, firstChar, i))
                    "Long" -> viewModel.lexemes.add(Lexeme(6, LexemeType.KEY_WORD, bitOfCode, firstChar, i))
                    else -> {
                        viewModel.lexemes.add(Lexeme(15, LexemeType.IDENTIFIER, bitOfCode, firstChar, i))
                    }
                }
                bitOfCode = String()
            }

            else if(inputString[i].isDigit()){
                val firstChar = i;

                while ((i + 1) < inputString.length && ((inputString[i + 1].isLetterOrDigit()) || inputString[i + 1] == '_'))
                {
                    i++;
                    bitOfCode += inputString[i];
                }

                viewModel.lexemes.add(Lexeme(20, LexemeType.INVALID_LEXEME, bitOfCode, firstChar, i))

                bitOfCode = String()
            }

            else if(isOperator(inputString[i])){
                val firstChar = i

                    when (inputString[i].toString())
                    {
                        "+" -> viewModel.lexemes.add(Lexeme(16, LexemeType.OPERATOR, bitOfCode, firstChar, i))
                        "-" -> viewModel.lexemes.add(Lexeme(17, LexemeType.OPERATOR, bitOfCode, firstChar, i))
                        "*" -> viewModel.lexemes.add(Lexeme(18, LexemeType.OPERATOR, bitOfCode, firstChar, i))
                        "/" -> viewModel.lexemes.add(Lexeme(19, LexemeType.OPERATOR, bitOfCode, firstChar, i))
                   }
                bitOfCode = String()
            }

            else if(isSeparator(inputString[i])){
                val firstChar = i;

                when(inputString[i].toString()) {
                    " " -> viewModel.lexemes.add(Lexeme(7, LexemeType.SEPARATOR, bitOfCode, firstChar, i))
                    "(" -> viewModel.lexemes.add(Lexeme(8, LexemeType.SEPARATOR, bitOfCode, firstChar, i))
                    ")" -> viewModel.lexemes.add(Lexeme(9, LexemeType.SEPARATOR, bitOfCode, firstChar, i))
                    "{" -> viewModel.lexemes.add(Lexeme(10, LexemeType.SEPARATOR, bitOfCode, firstChar, i))
                    "}" -> viewModel.lexemes.add(Lexeme(11, LexemeType.SEPARATOR, bitOfCode, firstChar, i))
                    ":" -> viewModel.lexemes.add(Lexeme(12, LexemeType.SEPARATOR, bitOfCode, firstChar, i))
                    "," -> viewModel.lexemes.add(Lexeme(14, LexemeType.SEPARATOR, bitOfCode, firstChar, i))

                }
                bitOfCode = String()
            }

            else if(inputString[i] == '/'){
                val firstChar = i

                while ((i + 1) < inputString.length && ((inputString[i + 1].isLetter())))
                {
                    i++;
                    bitOfCode += inputString[i];
                }

                if(bitOfCode == "\n"){
                    viewModel.lexemes.add(Lexeme(13, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))
                }
                else {
                    viewModel.lexemes.add(Lexeme(20, LexemeType.INVALID_LEXEME, bitOfCode, firstChar+1, i+1))

                }
                bitOfCode = String()
            }
            else {
                val firstChar = i
                while ((i + 1) < inputString.length && (!inputString[i + 1].isLetterOrDigit() && !isOperator(inputString[i + 1]) && !isSeparator(inputString[i + 1]))) {
                    i++
                    bitOfCode += inputString[i]
                }
                viewModel.lexemes.add(Lexeme(20, LexemeType.INVALID_LEXEME, bitOfCode, firstChar, i))
                bitOfCode = ""
            }
            i++
        }
        printResult(viewModel)
    }

    private fun isOperator(char: Char): Boolean{
        if(char == '+' || char=='-' || char=='*' || char=='/')
            return true
        else return false
    }

    private fun isSeparator(char: Char): Boolean{
        if(char == ' ' || char=='(' || char==')' || char=='{' || char=='}' || char==':' || char==',')
            return true
        else return false
    }

    private fun printResult(viewModel: ScannerViewModel){
        var result: String = String()
        for (lexeme in viewModel.lexemes) {
            when(lexeme.getType()){
                LexemeType.KEY_WORD -> result +=
                    "УСЛОВНЫЙ КОД: ${lexeme.getConditionalCode()}, " +
                        "ТИП ЛЕКСЕМЫ: ключевое слово," +
                        "ЛЕКСЕМА: ${lexeme.getValue()} " +
                            "С ${lexeme.getStartIndex()} ПО ${lexeme.getEndIndex()} СИМВОЛ\n"
                LexemeType.IDENTIFIER -> result +=
                    "УСЛОВНЫЙ КОД: ${lexeme.getConditionalCode()}, " +
                            "ТИП ЛЕКСЕМЫ: идентификатор," +
                            "ЛЕКСЕМА: ${lexeme.getValue()} " +
                            "С ${lexeme.getStartIndex()} ПО ${lexeme.getEndIndex()} СИМВОЛ\n"
                LexemeType.SEPARATOR -> result +=
                    "УСЛОВНЫЙ КОД: ${lexeme.getConditionalCode()}, " +
                            "ТИП ЛЕКСЕМЫ: разделитель," +
                            " ЛЕКСЕМА: ${lexeme.getValue()}, " +
                            "С ${lexeme.getStartIndex()} ПО ${lexeme.getEndIndex()} СИМВОЛ\n"
                LexemeType.OPERATOR -> result +=
                    "УСЛОВНЫЙ КОД: ${lexeme.getConditionalCode()}, " +
                            "ТИП ЛЕКСЕМЫ: оператор," +
                            " ЛЕКСЕМА: ${lexeme.getValue()}, " +
                            "С ${lexeme.getStartIndex()} ПО ${lexeme.getEndIndex()} СИМВОЛ\n"
                LexemeType.INVALID_LEXEME -> result +=
                    "УСЛОВНЫЙ КОД: ${lexeme.getConditionalCode()}, " +
                            "ТИП ЛЕКСЕМЫ: ошибка," +
                            " ЛЕКСЕМА: ${lexeme.getValue()}, " +
                            "С ${lexeme.getStartIndex()} ПО ${lexeme.getEndIndex()} СИМВОЛ\n"
            }

            viewModel.scanResultText = result
        }
    }

}
