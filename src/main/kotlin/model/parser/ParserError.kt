package src.main.kotlin.model.parser

sealed class ErrorType {
    object Default : ErrorType()
    object UnfinishedExpression : ErrorType()
}
class ParserError(
var value: String,
var startIndex: Int,
var endIndex: Int
) {
    val position: String
    get() = "с $startIndex по $endIndex символы"

}