package newlang3;

public enum LexicalType {
    LITERAL, // literal�iexp�F �ghello�h�j
    INTVAL, // value of int�iexp�F�R�j
    DOUBLEVAL, // double�iexp�F 1.2�j
    NAME, // variable�iexp�F i�j
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
    NL, // ���s
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
