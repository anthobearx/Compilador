import compilerTools.Token;

%%
%class Lexer
%type Token
%line
%column
%{
    private Token token(String lexeme, String lexicalComp, int line, int column){
        return new Token(lexeme, lexicalComp, line+1, column+1);
    }
%}
/* Variables básicas de comentarios y espacios 2 */
TerminadorDeLinea = \r|\n|\r\n
EntradaDeCaracter = [^\r\n]
EspacioEnBlanco = {TerminadorDeLinea} | [ \t\f]
ComentarioTradicional = "/*" [^*] ~"*/" | "/*" "*"+ "/"
FinDeLineaComentario = "//" {EntradaDeCaracter}* {TerminadorDeLinea}?
ContenidoComentario = ( [^*] | \*+ [^/*] )*
ComentarioDeDocumentacion = "/**" {ContenidoComentario} "*"+ "/"

/* Comentario */
Comentario = {ComentarioTradicional} | {FinDeLineaComentario} | {ComentarioDeDocumentacion}

/* Letras definidas */
Letra = [A-Za-zÑñ_ÁÉÍÓÚáéíóúÜü]


/* Autor */
Autor = "vega" | "Vega" | "VEga" | "VEGa" | "VEGA" | "vEGA" | "veGA" | "vegA" | "vEGA" | "VeGA" | "VEgA" | "VEGa" | "VegA" | "vEGa" 


/* Digitos validos */
Digito0 = [0-9]
Digito1 = [1-9]

/* Numero: La razón por la que lo divido asi, es porque no es correcto numeros que inicien con 0, esto hara que si se digita por ejemplo "09", los vea como numeros separados "0" y "9"  */
Numero = 0 | {Digito1}{Digito0}*

/* Identificador: Cualquier palabra no reservada que inicie con una Letra */
Identificador = {Letra}({Letra}|{Numero})*

/* Operadores */
Suma_op = "+"
Resta_op = "-"
Mult_op = "*"
Division_op = "/"
Aumento_op = "++"
Decremento_op = "--"
Relacionales_op = "<" | ">" | ">=" | "<=" | "==" | "!="
Logicos_op = "&&" | "||" | "!" | "&" | "|"

/* Signos */
Igual = "="
Puntocoma = ";"
Pesos = "$"

Parentesis_abrir = "("
Parentesis_cerrar = ")"

Bracket_abrir = "{"
Bracket_cerrar = "}"

Corchete_abrir = "["
Corchete_cerrar = "]"

/* Tipos de dato numerico */
Int_dato = "int" | "Int" | "INT"
Double_dato = "double" | "Double" | "DOUBLE"
Float_dato = "float" | "Float" | "FLOAT"
Long_dato = "long" | "Long" | "LONG"

Bool_dato = "boolean" | "Boolean" | "BOOLEAN"
String_dato = "string" | "String" | "STRING"
Char_dato = "char" | "Char" | "CHAR"

/* ciclos */
For = "for" | "For" | "FOR"
If = "if" | "If" | "IF"
While = "while" | "While" | "WHILE"
Do = "do" | "Do" | "DO"

/* Todos los simbolos */

Simbolos = "!" | "°" | "¬" | "@" | "#" | "$" | "%" | "?" | "|" | \" | "~" | "[" | "]" | "&" | "¿" | "¡" | "(" | ")" | "{" | "}" | "=" | ";" | ":" | "." | "," | "-" | "_" | "`" | "+" | "-" | "*" | "/" 
Tipo_bool = "true" | "false"

%%

/* Comentarios o espacios en blanco */
{Comentario}|{EspacioEnBlanco} { /*Ignorar*/ }

/* Identificadores (Imprimir) */
{Numero} { return token(yytext(), "Numero", yyline, yycolumn); }

{For} { return token(yytext(), "For", yyline, yycolumn); }
{If} { return token(yytext(), "If", yyline, yycolumn); }
{While} { return token(yytext(), "While", yyline, yycolumn); }
{Do} { return token(yytext(), "DO", yyline, yycolumn); }

{Int_dato} { return token(yytext(), "Tipo_dato_int", yyline, yycolumn); }
{Float_dato} { return token(yytext(), "Tipo_dato_float", yyline, yycolumn); }
{Double_dato} { return token(yytext(), "Tipo_dato_double", yyline, yycolumn); }
{Long_dato} { return token(yytext(), "Tipo_dato_long", yyline, yycolumn); }

{Bool_dato} { return token(yytext(), "Tipo_dato_logico", yyline, yycolumn); }
{Tipo_bool} { return token(yytext(), "True_false", yyline, yycolumn); }
{String_dato} { return token(yytext(), "Tipo_dato_string", yyline, yycolumn); }
{Char_dato} { return token(yytext(), "Tipo_dato_char", yyline, yycolumn); }

{Igual} { return token(yytext(), "Igual", yyline, yycolumn); }
{Puntocoma} { return token(yytext(), "Punto_y_coma", yyline, yycolumn); }
{Parentesis_abrir} { return token(yytext(), "Parentesis_abierto", yyline, yycolumn); }
{Parentesis_cerrar} { return token(yytext(), "Parentesis_cerrado", yyline, yycolumn); }
{Bracket_abrir} { return token(yytext(), "Bracket_abierto", yyline, yycolumn); }
{Bracket_cerrar} { return token(yytext(), "Bracket_cerrado", yyline, yycolumn); }
{Corchete_abrir} { return token(yytext(), "Corchete_abierto", yyline, yycolumn); }
{Corchete_cerrar} { return token(yytext(), "Corchete_cerrado", yyline, yycolumn); }
{Pesos} { return token(yytext(), "Pesos", yyline, yycolumn); }

{Suma_op} { return token(yytext(), "Operador_suma", yyline, yycolumn); }
{Resta_op} { return token(yytext(), "Operador_resta", yyline, yycolumn); }
{Mult_op} { return token(yytext(), "Operador_multiplicar", yyline, yycolumn); }
{Division_op} { return token(yytext(), "Operador_division", yyline, yycolumn); }
{Aumento_op} { return token(yytext(), "Operador_aumento", yyline, yycolumn); }
{Decremento_op} { return token(yytext(), "Operador_decremento", yyline, yycolumn); }
{Relacionales_op} { return token(yytext(), "Operador_relacional", yyline, yycolumn); }
{Logicos_op} { return token(yytext(), "Operador_logico", yyline, yycolumn); }

/* Simbolo desconocido antes de identificador */
({Simbolos} | {Numero}){Identificador} { return token(yytext(), "ERROR_1", yyline, yycolumn); }

/* 0 antes de un numero */
0{Numero} { return token(yytext(), "ERROR_2", yyline, yycolumn); }


/* Palabras */
{Autor} { return token(yytext(), "Autor", yyline, yycolumn); }
{Identificador} { return token(yytext(), "Identificador", yyline, yycolumn); }

/* Cualquier tipo de dato no definido por ejemplo $,%,@ */

. { return token(yytext(), "ERROR_0", yyline, yycolumn); }