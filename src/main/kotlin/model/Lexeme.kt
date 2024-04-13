package src.main.kotlin.model
enum class LexemeType{
    KEY_WORD_FUN,
    OPEN_C_SCOPE,
    CLOSE_C_SCOPE,
    COLON,
    KEY_WORD_TYPE,
    COMMA,
    OPEN_F_SCOPE,
    CLOSE_F_SCOPE,
    KEY_WORD_RETURN,
    IDENTIFIER,
    OPERATOR,
    INVALID_LEXEME,
    SEPARATOR
}
public class Lexeme(
    private val conditionalCode: Int,
    private val type: LexemeType,
    private val value: String,
    private val startIndex: Int,
    private val endIndex: Int
){

    private val _conditionalCode: Int = conditionalCode
    private val _type: LexemeType = type
    private val _value: String = value
    private val _startIndex: Int = startIndex
    private val _endIndex: Int = endIndex

    init{
        if(conditionalCode <=0 || conditionalCode > 20)
        {
            throw IllegalArgumentException("Incorrect conditional code")
        }
        if(startIndex <0 )
        {
            throw IllegalArgumentException("Incorrect conditional start index")
        }
        if(endIndex <0 )
        {
            throw IllegalArgumentException("Incorrect conditional end index")
        }
    }

    fun getConditionalCode() = _conditionalCode
    fun getType() = _type
    fun getValue() = _value
    fun getStartIndex() = _startIndex
    fun getEndIndex() = _endIndex

}