package src.main.kotlin.model.parser

import src.main.kotlin.model.Lexeme
import src.main.kotlin.model.LexemeType

class ParserError(
var value: String,
var startIndex: Int,
var endIndex: Int,
var expected: String,
var nextLexeme: Lexeme
) {
    val position: String
    get() = "с $startIndex по $endIndex символы"

    fun expectedToLexemeType():LexemeType {
        when (expected) {
            "fun" ->
                return LexemeType.KEY_WORD_RETURN

            "A" ->
                return LexemeType.IDENTIFIER

            "(" ->
                return LexemeType.OPEN_C_SCOPE

            ")" ->
                return LexemeType.CLOSE_C_SCOPE

            ":" ->
                return LexemeType.COLON

            "Int" ->
                return LexemeType.KEY_WORD_TYPE

            "," ->
                return LexemeType.COMMA

            "{" ->
                return LexemeType.OPEN_F_SCOPE

            "return" ->
                return LexemeType.KEY_WORD_RETURN

            "+" ->
                return LexemeType.OPERATOR
            else -> throw IllegalArgumentException("Unknown lexeme type for '$expected'")
        }
    }
}