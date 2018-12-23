package newlang3;

public enum LexicalType {
    LITERAL, // literal（exp： “hello”）
    INTVAL, // value of int（exp：３）
    DOUBLEVAL, // double（exp： 1.2）
    NAME, // variable（exp： i）
    IF, // IF
    THEN, // THEN
    ELSE, // ELSE
    ELSEIF, // ELSEIF
    ENDIF, // ENDIF
    FOR, // FOR
    FORALL, // FORALL
    NEXT, // NEXT
    EQ, // =
    LT, // <
    GT, // >
    LE, // <=, =<
    GE, // >=, =>
    NE, // <>
    FUNC, // SUB
    DIM, // DIM
    AS, // AS
    END, // END
    NL, // 改行
    DOT, // .
    WHILE, // WHILE
    DO, // DO
    UNTIL, // UNTIL
    ADD, // +
    SUB, // -
    MUL, // *
    DIV, // /
    LP, // )
    RP, // (
    COMMA, // ,
    LOOP, // LOOP
    TO, // TO
    WEND, // WEND
    EOF, // end of file
}
