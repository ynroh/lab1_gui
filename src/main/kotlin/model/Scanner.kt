package src.main.kotlin.model

public class Scanner {
    private  var Lexemes: MutableList<Lexeme> = mutableListOf()
    fun analyzeCode(viewModel: ScannerViewModel){
        var inputString = viewModel.currentContent
        Lexemes.clear()
        println("nnn")
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
                    "fun" -> Lexemes.add(Lexeme(1, LexemeType.KEY_WORD, bitOfCode, firstChar+1, i+1))
                    "Int" -> Lexemes.add(Lexeme(2, LexemeType.KEY_WORD, bitOfCode, firstChar+1, i+1))
                    "return" -> Lexemes.add(Lexeme(3, LexemeType.KEY_WORD, bitOfCode, firstChar+1, i+1))
                    "Double" -> Lexemes.add(Lexeme(4, LexemeType.KEY_WORD, bitOfCode, firstChar+1, i+1))
                    "Float" -> Lexemes.add(Lexeme(5, LexemeType.KEY_WORD, bitOfCode, firstChar+1, i+1))
                    "Long" -> Lexemes.add(Lexeme(6, LexemeType.KEY_WORD, bitOfCode, firstChar+1, i+1))
                    else -> {
                        Lexemes.add(Lexeme(15, LexemeType.IDENTIFIER, bitOfCode, firstChar+1, i+1))
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

                Lexemes.add(Lexeme(20, LexemeType.INVALID_LEXEME, bitOfCode, firstChar+1, i+1))

                bitOfCode = String()
            }

            else if(isOperator(inputString[i])){
                val firstChar = i

                    when (inputString[i].toString())
                    {
                        "+" -> Lexemes.add(Lexeme(16, LexemeType.OPERATOR, bitOfCode, firstChar+1, i+1))
                        "-" -> Lexemes.add(Lexeme(17, LexemeType.OPERATOR, bitOfCode, firstChar+1, i+1))
                        "*" -> Lexemes.add(Lexeme(18, LexemeType.OPERATOR, bitOfCode, firstChar+1, i+1))
                        "/" -> Lexemes.add(Lexeme(19, LexemeType.OPERATOR, bitOfCode, firstChar+1, i+1))
                   }
                bitOfCode = String()
            }

            else if(isSeparator(inputString[i])){
                val firstChar = i;

                when(inputString[i].toString()) {
                    " " -> Lexemes.add(Lexeme(7, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))
                    "(" -> Lexemes.add(Lexeme(8, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))
                    ")" -> Lexemes.add(Lexeme(9, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))
                    "{" -> Lexemes.add(Lexeme(10, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))
                    "}" -> Lexemes.add(Lexeme(11, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))
                    ":" -> Lexemes.add(Lexeme(12, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))
                    "," -> Lexemes.add(Lexeme(14, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))

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
                     Lexemes.add(Lexeme(13, LexemeType.SEPARATOR, bitOfCode, firstChar + 1, i + 1))
                }
                else {
                    Lexemes.add(Lexeme(20, LexemeType.INVALID_LEXEME, bitOfCode, firstChar+1, i+1))

                }
                bitOfCode = String()
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
        for (lexeme in Lexemes) {
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
