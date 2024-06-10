import com.kingmang.lazurite.lexer.Lexer

fun main() {
    val lexer = Lexer("34890 + #H2345")
    val result = lexer.tokenize()
    println(result)
}
