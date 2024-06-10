package com.kingmang.lazurite.lexer

class Lexer(private val input : String) {
    companion object{
        private const val OPERATOR_CHARS = "+-*/()+"
        private val OPERATOR_TOKENS = arrayOf(
            TokenKind.PLUS,
            TokenKind.MINUS,
            TokenKind.STAR,
            TokenKind.SLASH,
            TokenKind.LPAREN,
            TokenKind.RPAREN,
            TokenKind.EQ
        )
    }

    private val length = input.length
    private val tokens = mutableListOf<Token>()
    private var pos = 0

    fun tokenize() : List<Token>{
        while(pos < length){
            val current = peek(0)
            when{
                current.isDigit() -> tokenizeNumber()
                current == '#' ->{
                    next()
                    tokenizeHexNumber()
                }
                OPERATOR_CHARS.indexOf(current) != -1 -> tokenizeOperator()
                    else -> next()
            }
        }
        return tokens
    }
    private fun tokenizeNumber(){
        val buffer = StringBuffer()
        var current = peek(0)
        while(current.isDigit()){
            buffer.append(current)
            current = next()
        }
        addToken(TokenKind.NUMBER, buffer.toString())
    }
    private fun tokenizeHexNumber(){
        val buffer = StringBuffer()
        var current = peek(0)
        while(current.isDigit() || isHexNumber(current)){
           buffer.append(current)
           current = next()
        }
        addToken(TokenKind.HEX_NUMBER, buffer.toString())
    }
    private fun isHexNumber(current : Char) : Boolean{
        return "abcdef".indexOf(current.lowercaseChar()) != -1
    }
    private fun tokenizeOperator(){
        val position = OPERATOR_CHARS.indexOf(peek(0))
        addToken(OPERATOR_TOKENS[position])
        next()

    }

    private fun next() : Char{
        pos++
        return peek(0)
    }
    private fun peek(relativePosition : Int) : Char{
        val position = pos + relativePosition
        return if (position >= length) '\u0000' else input[position]
    }

    private fun addToken(type : TokenKind, text : String = ""){
        tokens.add(Token(type, text))
    }
}