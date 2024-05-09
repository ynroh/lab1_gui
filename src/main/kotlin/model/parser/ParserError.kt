package src.main.kotlin.model.parser

import src.main.kotlin.model.Lexeme

sealed class ErrorType {
    object Default : ErrorType()
    object UnfinishedExpression : ErrorType()
}
class ParserError(
var value: String,
var startIndex: Int,
var endIndex: Int,
var expected: String,
var nextLexeme: Lexeme
) {
    val position: String
    get() = "с $startIndex по $endIndex символы"

}