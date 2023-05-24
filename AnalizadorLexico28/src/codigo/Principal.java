/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codigo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author antho
 */
public class Principal {

    public static void main(String[] args) throws Exception {
        String ruta1 = "C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/src/codigo/Lexer.flex";
        String ruta2 = "C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/src/codigo/LexerCup.flex";
        String rutaColor = "C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/src/codigo/LexerColor.flex";
        String[] rutaS = {"-parser", "Sintax", "C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/src/codigo/Sintax.cup"};
        generar(ruta1, ruta2, rutaColor, rutaS);
    }

    public static void generar(String ruta1, String ruta2,String rutaColor, String[] rutaS) throws IOException, Exception {
        File archivo;
        //lexico lexico
        archivo = new File(ruta1);
        JFlex.Main.generate(archivo);
        
        //lexico sintatico
        archivo = new File(ruta2);
        JFlex.Main.generate(archivo);
        
        //color
        archivo = new File(rutaColor);
        JFlex.Main.generate(archivo);
        
        //sintatico
        java_cup.Main.main(rutaS);
           
        Path rutaSym = Paths.get("C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/src/codigo/sym.java");
        if (Files.exists(rutaSym)){
            Files.delete(rutaSym);
        }
        Files.move(
                Paths.get("C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/sym.java"),
                Paths.get("C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/src/codigo/sym.java")
        );
        Path rutaSin = Paths.get("C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/src/codigo/Sintax.java");
        if (Files.exists(rutaSin)){
            Files.delete(rutaSin);
        }
        Files.move(
                Paths.get("C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/Sintax.java"),
                Paths.get("C:/Users/antho/OneDrive/Documentos/NetBeansProjects/AnalizadorLexico28/src/codigo/Sintax.java")
        );

    }

}
