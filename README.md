# interpreter for basic

## syntax rule

### `<program>` ::=
  * `<stmt_list>`

### `<stmt_list>` ::=
  * `<stmt>`
  * `<stmt_list>` `<stmt>` `<NL>`
  * `<block>`
  * `<block>` `<stmt_list>`

### `<block>` ::=
  * `<if_prefix>` `<stmt>` `<NL>`
  * `<if_prefix>` `<stmt>` `<ELSE>` `<stmt>` `<NL>`
  * `<if_prefix>` `<NL>` `<stmt_list>` `<else_block>` `<ENDIF>` `<NL>`
  * `<WHILE>` `<cond>` `<NL>` `<stmt_list>` `<WEND>` `<NL>`
  * `<DO>` `<WHILE>` `<cond>` `<NL>` `<stmt_list>` `<LOOP>` `<NL>`
  * `<DO>` `<UNTIL>` `<cond>` `<NL>` `<stmt_list>` `<LOOP>` `<NL>`
  * `<DO>` `<NL>` `<stmt_list>` `<LOOP>` `<WHILE>` `<cond>` `<NL>`
  * `<DO>` `<NL>` `<stmt_list>` `<LOOP>` `<UNTIL>` `<cond>` `<NL>`

### `<stmt>` ::=
 * `<subst>`
 * `<call_sub>`
 * `<FOR>` `<subst>` `<TO>` `<INTVAL>` `<NL>` `<stmt_list>` `<NEXT>` `<NAME>`
 * `<END>`

### `<varlist>` ::=
  * `<var>`
  * `<varlist>` `<COMMA>` `<var>`

### `<expr_list>` ::=
  * `<expr>`
  * `<expr_list>` `<COMMA>` `<expr>`

### `<if_prefix>`  ::=
  * `<IF>` `<cond>` `<THEN>`

### `<else_block>`  ::=
  * `<else_if_block>`
  * `<else_if_block>` `<ELSE>` `<NL>` `<stmt_list>`

### `<else_if_block>`   ::=
  * φ
  * `<else_if_block>` `<ELSEIF>` `<cond>` `<THEN>` `<NL>` `<stmt_list>`

### `<subst>` ::=
  * `<leftvar>` `<EQ>` `<expr>`

### `<cond>` ::=
 * `<expr>` `<EQ>` `<expr>`
 * `<expr>` `<GT>` `<expr>`
 * `<expr>` `<LT>` `<expr>`
 * `<expr>` `<GE>` `<expr>`
 * `<expr>` `<LE>` `<expr>`
 * `<expr>` `<NE>` `<expr>`

### `<expr>` ::= 
 * `<expr>` `<ADD>` `<expr>`
 * `<expr>` `<SUB>` `<expr>`
 * `<expr>` `<MUL>` `<expr>`
 * `<expr>` `<DIV>` `<expr>`
 * `<SUB>` `<expr>`
 * `<LP>` `<expr>` `<RP>`
 * `<NAME>`
 * `<INTVAL>`
 * `<DOUBLEVAL>`
 * `<LITERAL>`
 * `<call_func>`

### `<var>` ::=
  * `<NAME>` /* normal variable */

### `<leftvar>`	::=
  * `<NAME>` /* normal variable */

### `<call_func>`	::=
 * `<NAME>` `<LP>` `<expr_list>` `<RP>`

### `<call_sub>` ::=
 * `<NAME>` `<expr_list>`
