package com.kingmang.lazurite.lexer

data class Token(
    var type : TokenKind? = null,
    var text : String? = null

){
    override fun toString() : String{
        return "$type $text"
    }
}

