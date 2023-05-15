import compilerTools.TextColor;
import java.awt.Color;

%%
%class LexerColor
%type TextColor
%char
%{
    private TextColor textColor(long start, int size, Color color){
        return new TextColor((int) start, size, color);
    }
%}
/* Variables básicas de comentarios y espacios */
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
{Comentario} { return textColor(yychar, yylength(), new Color(146, 146, 146)); }
{EspacioEnBlanco} { /*Ignorar*/ }

{Numero} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

{For} { return textColor(yychar, yylength(), new Color(0, 0, 200)); }
{If} { return textColor(yychar, yylength(), new Color(0, 0, 200)); }
{While} { return textColor(yychar, yylength(), new Color(0, 0, 200)); }
{Do} { return textColor(yychar, yylength(), new Color(0, 0, 200)); }

{Int_dato} { return textColor(yychar, yylength(), new Color(0, 200, 0)); }
{Float_dato} { return textColor(yychar, yylength(), new Color(0, 200, 0)); }
{Double_dato} { return textColor(yychar, yylength(), new Color(0, 200, 0)); }
{Long_dato} { return textColor(yychar, yylength(), new Color(0, 200, 0)); }

{Bool_dato} { return textColor(yychar, yylength(), new Color(0, 200, 0)); }
{Tipo_bool} { return textColor(yychar, yylength(), new Color(50, 200, 50)); }

{String_dato} { return textColor(yychar, yylength(), new Color(0, 200, 0)); }
{Char_dato} { return textColor(yychar, yylength(), new Color(0, 200, 0)); }

{Igual} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Puntocoma} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Parentesis_abrir} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Parentesis_cerrar} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Bracket_abrir} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Bracket_cerrar} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Corchete_abrir} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Corchete_cerrar} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Pesos} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

{Suma_op} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Resta_op} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Mult_op} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Division_op} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Aumento_op} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Decremento_op} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Relacionales_op} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }
{Logicos_op} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }


/* Simbolo desconocido antes de identificador */
({Simbolos} | {Numero}){Identificador} { return textColor(yychar, yylength(), new Color(255, 0, 0)); }

/* 0 antes de un numero */
0{Numero} { return textColor(yychar, yylength(), new Color(255, 0, 0)); }


/* Palabras */
{Autor} { return textColor(yychar, yylength(), new Color(0, 255, 255)); }
{Identificador} { return textColor(yychar, yylength(), new Color(0, 0, 0)); }

. { return textColor(yychar, yylength(), new Color(255, 0, 0)); }