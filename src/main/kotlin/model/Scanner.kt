package src.main.kotlin.model


import src.main.kotlin.viewModel.ScannerViewModel

public class Scanner {
    //private  var Lexemes: MutableList<Lexeme> = mutableListOf()

    fun analyzeCode(viewModel: ScannerViewModel){
        var a = "\n"
        println(a[0])
        val inputString = viewModel.currentContent
        println("AA " + inputString)
        viewModel.lexemes.clear()
        var bitOfCode = String()
        var i = 0
        while (i < inputString.length){
            bitOfCode += inputString[i]
            if(inputString[i].isLetter())
            {
               val firstChar = i;

                /*while ((i + 1) < inputString.length && ((inputString[i + 1].isLetterOrDigit()) || inputString[i + 1] == '_'))
                {
                    i++;
                    bitOfCode += inputString[i];
                }*/

                while ((i + 1) < inputString.length && (!isSeparator(inputString[i + 1])) && inputString[i + 1]!='\n')
                {
                    i++;
                    bitOfCode += inputString[i];
                }

                when (bitOfCode)
                {
                    "fun" -> viewModel.lexemes.add(Lexeme(1, LexemeType.KEY_WORD_FUN, bitOfCode, firstChar, i))
                    "Int" -> viewModel.lexemes.add(Lexeme(2, LexemeType.KEY_WORD_TYPE, bitOfCode, firstChar, i))
                    "return" -> viewModel.lexemes.add(Lexeme(3, LexemeType.KEY_WORD_RETURN, bitOfCode, firstChar, i))
                    "Double" -> viewModel.lexemes.add(Lexeme(4, LexemeType.KEY_WORD_TYPE, bitOfCode, firstChar, i))
                    "Float" -> viewModel.lexemes.add(Lexeme(5, LexemeType.KEY_WORD_TYPE, bitOfCode, firstChar, i))
                    "Long" -> viewModel.lexemes.add(Lexeme(6, LexemeType.KEY_WORD_TYPE, bitOfCode, firstChar, i))
                    "String" -> viewModel.lexemes.add(Lexeme(6, LexemeType.KEY_WORD_TYPE, bitOfCode, firstChar, i))
                    else -> {
                        if(bitOfCode[0].isDigit()) {
                            viewModel.lexemes.add(Lexeme(20, LexemeType.INVALID_LEXEME, bitOfCode, firstChar, i))
                        }
                        else {
                            val pattern = Regex("^[a-zA-Z0-9_]*$")
                            if (!pattern.matches(bitOfCode)) {
                                viewModel.lexemes.add(Lexeme(20, LexemeType.INVALID_LEXEME, bitOfCode, firstChar, i))
                            }
                            else {
                                viewModel.lexemes.add(Lexeme(15, LexemeType.IDENTIFIER, bitOfCode, firstChar, i))
                            }
                        }
                    }
                }
                bitOfCode = String()
            }

            else if(inputString[i].isDigit()){
                val firstChar = i;

                /*while ((i + 1) < inputString.length && ((inputString[i + 1].isLetterOrDigit()) || inputString[i + 1] == '_'))
                {
                    i++;
                    bitOfCode += inputString[i];
                }*/
                while ((i + 1) < inputString.length && (!isSeparator(inputString[i + 1])) && inputString[i + 1]!='\n')
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
                    "(" -> viewModel.lexemes.add(Lexeme(8, LexemeType.OPEN_C_SCOPE, bitOfCode, firstChar, i))
                    ")" -> viewModel.lexemes.add(Lexeme(9, LexemeType.CLOSE_C_SCOPE, bitOfCode, firstChar, i))
                    "{" -> viewModel.lexemes.add(Lexeme(10, LexemeType.OPEN_F_SCOPE, bitOfCode, firstChar, i))
                    "}" -> viewModel.lexemes.add(Lexeme(11, LexemeType.CLOSE_F_SCOPE, bitOfCode, firstChar, i))
                    ":" -> viewModel.lexemes.add(Lexeme(12, LexemeType.COLON, bitOfCode, firstChar, i))
                    "," -> viewModel.lexemes.add(Lexeme(14, LexemeType.COMMA, bitOfCode, firstChar, i))

                }
                bitOfCode = String()
            }

            else if(inputString[i] == '\n' || inputString[i] == '\t'){
                val firstChar = i
                viewModel.lexemes.add(Lexeme(13, LexemeType.SEPARATOR, inputString[i].toString(), firstChar, i))
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
       skipSeparators(viewModel)
        //printResult(viewModel)
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

    private fun skipSeparators(viewModel: ScannerViewModel){
        var temp = mutableListOf<Lexeme>()
        for(lexeme in viewModel.lexemes){
            if(lexeme.getType() != LexemeType.SEPARATOR){
                temp.add(lexeme)
            }
        }
        viewModel.lexemes=temp.toMutableList()
    }
    private fun printResult(viewModel: ScannerViewModel){
        var result: String = String()
        for (lexeme in viewModel.lexemes) {
            result +=
                "УСЛОВНЫЙ КОД: ${lexeme.getConditionalCode()}, " +
                        "ТИП ЛЕКСЕМЫ: ${lexeme.getType()}, " +
                        "ЛЕКСЕМА: ${lexeme.getValue()} " +
                        "С ${lexeme.getStartIndex()} ПО ${lexeme.getEndIndex()} СИМВОЛ\n"

            viewModel.scanResultText = result
        }
    }

}
