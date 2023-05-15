
import com.formdev.flatlaf.FlatIntelliJLaf;
import compilerTools.CodeBlock;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import compilerTools.Directory;
import compilerTools.ErrorLSSL;
import compilerTools.Functions;
import compilerTools.Grammar;
import compilerTools.Production;
import compilerTools.TextColor;
import compilerTools.Token;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 *
 * @author yisus
 */
public class Compilador extends javax.swing.JFrame {

    private String title;
    private Directory directorio;
    private ArrayList<Token> tokens;
    private ArrayList<ErrorLSSL> errors;
    private ArrayList<TextColor> textsColor;//colores de los errores
    private Timer timerKeyReleased;//ejecitar funcion para colorear palabras
    private ArrayList<Production> identProd;//extraer identificadores
    private HashMap<String, String> identificadores;//guardar identificadores
    private boolean codeHasBeenCompiled = false;//fue compilado o no
    private int errores = 0;
    private ArrayList<String> tablaSimbolos = new ArrayList<>();

    /**
     * Creates new form Compilador
     */
    public Compilador() {
        initComponents();
        init();
    }

    private void init() {
        title = "Compilador - VMP";
        setLocationRelativeTo(null);//centrar ventana en el centro
        setTitle(title);//poner titulo
        btnEjecutar.setEnabled(false);
        directorio = new Directory(this, jtpCode, title, ".comp");
        addWindowListener(new WindowAdapter() {// Cuando presiona la "X" de la esquina superior derecha
            @Override
            public void windowClosing(WindowEvent e) {
                directorio.Exit();
                System.exit(0);
            }
        });
        Functions.setLineNumberOnJTextComponent(jtpCode);//mostrar numeros de linea
        timerKeyReleased = new Timer((int) (1000 * 0.3), (ActionEvent e) -> {//inicializar timer para colorear las palabras del editor de codigo
            timerKeyReleased.stop();
            colorAnalysis();
        });
        Functions.insertAsteriskInName(this, jtpCode, () -> {
            timerKeyReleased.restart();
        });//poner asterisco en la ventana cada que se edite un archivo en el editor

        //identificacion/creacion de elementos
        tokens = new ArrayList<>();
        errors = new ArrayList<>();
        textsColor = new ArrayList<>();
        identProd = new ArrayList<>();
        identificadores = new HashMap<>();
        //activar autocompletador ctrl + espacio
        Functions.setAutocompleterJTextComponent(new String[]{
            "int i = 0;",
            "double d = 0.0",
            "System.out.println('');",
            "if(i == 1 ){"
            + "<Statement>"
            + "}",
            "for(int i == 0; i <= 0; i++){"
            + "<Statement>"
            + " }"
        }, jtpCode, () -> {
            timerKeyReleased.restart();
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rootPanel = new javax.swing.JPanel();
        buttonsFilePanel = new javax.swing.JPanel();
        btnAbrir = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnGuardarC = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtpCode = new javax.swing.JTextPane();
        panelButtonCompilerExecute = new javax.swing.JPanel();
        btnCompilar = new javax.swing.JButton();
        btnEjecutar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jtaOutputConsole = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblTokens = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        btnAbrir.setText("Abrir");
        btnAbrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAbrirActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnGuardarC.setText("Guardar como");
        btnGuardarC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonsFilePanelLayout = new javax.swing.GroupLayout(buttonsFilePanel);
        buttonsFilePanel.setLayout(buttonsFilePanelLayout);
        buttonsFilePanelLayout.setHorizontalGroup(
            buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAbrir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGuardarC)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        buttonsFilePanelLayout.setVerticalGroup(
            buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonsFilePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonsFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAbrir)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnGuardarC))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jtpCode);

        btnCompilar.setText("Compilar");
        btnCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCompilarActionPerformed(evt);
            }
        });

        btnEjecutar.setText("Ejecutar");
        btnEjecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEjecutarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelButtonCompilerExecuteLayout = new javax.swing.GroupLayout(panelButtonCompilerExecute);
        panelButtonCompilerExecute.setLayout(panelButtonCompilerExecuteLayout);
        panelButtonCompilerExecuteLayout.setHorizontalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCompilar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEjecutar)
                .addContainerGap())
        );
        panelButtonCompilerExecuteLayout.setVerticalGroup(
            panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelButtonCompilerExecuteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelButtonCompilerExecuteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCompilar)
                    .addComponent(btnEjecutar))
                .addContainerGap(7, Short.MAX_VALUE))
        );

        jtaOutputConsole.setEditable(false);
        jtaOutputConsole.setBackground(new java.awt.Color(255, 255, 255));
        jtaOutputConsole.setColumns(20);
        jtaOutputConsole.setRows(5);
        jScrollPane2.setViewportView(jtaOutputConsole);

        tblTokens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Componente léxico", "Lexema", "[Línea, Columna]"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTokens.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(tblTokens);

        javax.swing.GroupLayout rootPanelLayout = new javax.swing.GroupLayout(rootPanel);
        rootPanel.setLayout(rootPanelLayout);
        rootPanelLayout.setHorizontalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, rootPanelLayout.createSequentialGroup()
                        .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 693, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );
        rootPanelLayout.setVerticalGroup(
            rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rootPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonsFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelButtonCompilerExecute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(rootPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rootPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        getContentPane().add(rootPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        directorio.New();
        clearFields();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnAbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirActionPerformed
        if (directorio.Open()) {
            colorAnalysis();
            clearFields();
        }
    }//GEN-LAST:event_btnAbrirActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        if (directorio.Save()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnGuardarCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCActionPerformed
        if (directorio.SaveAs()) {
            clearFields();
        }
    }//GEN-LAST:event_btnGuardarCActionPerformed
    //si hay un asterisco en el titulo, lo mandara a compilar, ara quitarle el asterisco, es decir que esta guardado
    private void btnCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCompilarActionPerformed
        if (getTitle().contains("*") || getTitle().equals(title)) {
            if (directorio.Save()) {
                compile();
            }
        } else {
            compile();
        }
    }//GEN-LAST:event_btnCompilarActionPerformed

    private void btnEjecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEjecutarActionPerformed
        btnCompilar.doClick();
        if (codeHasBeenCompiled) {
            if (!errors.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se puede ejecutar el código ya que se encontró uno o más errores",
                        "Error en la compilación", JOptionPane.ERROR_MESSAGE);
            } else {
                CodeBlock codeBlock = Functions.splitCodeInCodeBlocks(tokens, "{", "}", ";");
                System.out.println(codeBlock);
                ArrayList<String> blocksOfCode = codeBlock.getBlocksOfCodeInOrderOfExec();
                System.out.println(blocksOfCode);

            }
        }
    }//GEN-LAST:event_btnEjecutarActionPerformed

    private void compile() {
        clearFields();
        lexicalAnalysis();
        fillTableTokens();
        tablaSimbolosMetodo();
        if (errores > 0) {//si hay errores
            if (errores == 1) {
                jtaOutputConsole.append("Se ha encontrado " + errores + " error lexico, no se puede ejecutar el analisis sintatico...\n");
            } else if (errores > 1) {
                jtaOutputConsole.append("Se han encontrado " + errores + " errores lexicos, no se puede ejecutar el analisis sintatico...\n");
            }
            mostrarGramatica();
        } else {
            syntacticAnalysis();
            //semanticAnalysis(); 
        }
        printConsole();
        codeHasBeenCompiled = true;
    }

    private void lexicalAnalysis() {
        jtaOutputConsole.append("Iniciando analisis lexico...\n");
        errores = 0;
        if (!tablaSimbolos.isEmpty()) {//vaciar tabla simbolos
            tablaSimbolos.clear();
        }

        // Extraer tokens
        Lexer lexer;
        try {
            File codigo = new File("code.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = jtpCode.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexer = new Lexer(entrada);
            while (true) {
                Token token = lexer.yylex();
                if (token == null) {
                    break;
                }
                tokens.add(token);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado... " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }

        jtaOutputConsole.append("Analisis lexico terminado con exito...\n");
        jtaOutputConsole.append("\n");
    }

    private void syntacticAnalysis() {
        jtaOutputConsole.append("Iniciando analisis sintatico...\n");

        agrupacionSintatica();
    }

    private void semanticAnalysis() {
    }

    private void colorAnalysis() {
        /* Limpiar el arreglo de colores */
        textsColor.clear();
        /* Extraer rangos de colores */
        LexerColor lexerColor;
        try {
            File codigo = new File("color.encrypter");
            FileOutputStream output = new FileOutputStream(codigo);
            byte[] bytesText = jtpCode.getText().getBytes();
            output.write(bytesText);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(new FileInputStream(codigo), "UTF8"));
            lexerColor = new LexerColor(entrada);
            while (true) {
                TextColor textColor = lexerColor.yylex();
                if (textColor == null) {
                    break;
                }
                textsColor.add(textColor);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("El archivo no pudo ser encontrado.. " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Error al escribir en el archivo... " + ex.getMessage());
        }
        //palabras que no esten en el rango
        Functions.colorTextPane(textsColor, jtpCode, new Color(40, 40, 40));
    }

    //llenado de tabla
    private void fillTableTokens() {

        tokens.forEach(token -> {
            Object[] data = new Object[]{token.getLexicalComp(), token.getLexeme(), "[" + token.getLine() + ", " + token.getColumn() + "]"};
            //jtaOutputConsole.append(token.getLexicalComp() + "\n"); //escribir el componente lexico de cada componente
            Functions.addRowDataInTable(tblTokens, data);
            if (token.getLexicalComp().equals("ERROR_0") || token.getLexicalComp().equals("ERROR_1") || token.getLexicalComp().equals("ERROR_2")) {
                errores++;//errores lexicos
            }
        });
    }

    private void tablaSimbolosMetodo() {
        tokens.forEach(token -> {
            if (token.getLexicalComp().equals("Identificador")) {
                tablaSimbolos.add(token.getLexeme());
            }
        });

        if (!tablaSimbolos.isEmpty()) {
            //imprimir tabla de simbolos
            jtaOutputConsole.append("Tabla de simbolos:\n");
            tablaSimbolos.forEach(simbolo -> {
                jtaOutputConsole.append(simbolo + "\n");
            });
        }
    }

    private void mostrarGramatica() {
        Grammar gramatica = new Grammar(tokens, errors);
        gramatica.delete(new String[]{"ERROR_0", "ERROR_1", "ERROR_2"}, 1);
        gramatica.show();
    }

    private void agrupacionSintatica() {
        Grammar gramatica = new Grammar(tokens, errors);
        gramatica.delete(new String[]{"ERROR_0", "ERROR_1", "ERROR_2"}, 1);

        //siempre que una expresion regular se repita una vez, usar el true
        //esta agrupacion de tiponumerico utiliza cuatro componentes
        gramatica.group("TipoNumerico", "(Tipo_dato_int | Tipo_dato_float | Tipo_dato_double | Tipo_dato_long)", true);
        gramatica.group("ValorAsignable", "(Numero | Corchete_abierto Identificador Corchete_cerrado )", true);//puede ser int a = 2; o int a = [ a];

        //Asignacion tipo numerico
        gramatica.group("AsignacionNumerica", "TipoNumerico Identificador Igual ValorAsignable", true);
        gramatica.finalLineColumn();//mostrar el error al final de la produccion(en vez de 1,1, lo muestra al final)

        gramatica.group("AsignacionNumerica", "TipoNumerico Igual ValorAsignable", true,
                2, "××ERROR SINTATICO {}: Falta de identificador en la variable [#,%]");
        gramatica.group("AsignacionNumerica", "TipoNumerico Identificador Igual Identificador", true,
                2, "××ERROR SINTATICO {}: La variable a asignar debe estar rodeada de corchetes al final de '[]' [#,%]");
        gramatica.group("AsignacionNumerica", "TipoNumerico Identificador Igual Corchete_abierto Identificador", true,
                2, "××ERROR SINTATICO {}: Se esperaba un ']' al final de la linea [#,%]");
        gramatica.group("AsignacionNumerica", "TipoNumerico Identificador Igual Corchete_abierto Corchete_cerrado", true,
                2, "××ERROR SINTATICO {}: Se esperaba una variable dentro de los parentesis [#,(%-2)]");
        gramatica.group("AsignacionNumerica", "TipoNumerico Identificador Igual", true,
                2, "××ERROR SINTATICO {}: Se esperaba un valor asignable compatible despues del signo igual [#,%]");

        //Asignacion tipo boolean
        gramatica.group("AsignacionBool", "Tipo_dato_logico Identificador Igual True_false", true);
        gramatica.group("AsignacionBool", "Tipo_dato_logico Identificador Igual", true,
                3, "××ERROR SINTATICO {}: Se esperaba un True/False al final de la linea [#,%]");
        gramatica.group("AsignacionBool", "Tipo_dato_logico Igual True_false", true,
                3, "××ERROR SINTATICO {}: Falto el identificador despues de Boolean  [#,%]");

        //Asignacion tipo String
        gramatica.group("AsignacionString", "(Tipo_dato_string | Tipo_dato_char) Identificador Igual Pesos Identificador Pesos", true);
        gramatica.group("AsignacionString", "(Tipo_dato_string | Tipo_dato_char) Identificador Igual Identificador", true,
                4, "××ERROR SINTATICO {}: Se debe agregar $ al inicio y al final del valor a asignar  [#,%]");
        gramatica.group("AsignacionString", "(Tipo_dato_string | Tipo_dato_char) Identificador Igual", true,
                4, "××ERROR SINTATICO {}: Se debe agregar un valor valido despues del signo igual  [#,%]");
        gramatica.group("AsignacionString", "(Tipo_dato_string | Tipo_dato_char) Identificador Igual Identificador Pesos", true,
                4, "××ERROR SINTATICO {}: Se debe agregar $ al inicio del valor a asignar  [#,%]");
        gramatica.group("AsignacionString", "(Tipo_dato_string | Tipo_dato_char) Identificador Igual Pesos Identificador", true,
                4, "××ERROR SINTATICO {}: Se debe agregar $ al final del valor a asignar  [#,%]");
        gramatica.group("AsignacionString", "(Tipo_dato_string | Tipo_dato_char) Igual", true,
                4, "××ERROR SINTATICO {}: Falto el identificador despues de (String/Char)  [#,%]");

        //declaracion de variable
        gramatica.group("DeclaracionVariables", "(Tipo_dato_string | Tipo_dato_char | Tipo_dato_logico | TipoNumerico) Identificador", true);
        gramatica.group("DeclaracionVariables", "(Tipo_dato_string | Tipo_dato_char | Tipo_dato_logico | TipoNumerico)", true,
                5, "××ERROR SINTATICO {}: Falto agregar un identificador despues del tipo de dato  [#,%]");

        //gramatica.initialLineColumn();
        /* Enunciado (Statement) */
        gramatica.group("Enunciado", "AsignacionNumerica | AsignacionBool | AsignacionString | DeclaracionVariables | Estructura_For", true);
        /* Lista enunciados */
        gramatica.group("Lista_enunciados", "Enunciado Punto_y_coma ", true);
        gramatica.group("Lista_enunciados", "Enunciado", true,
                6, "××ERROR SINTATICO {}: Los enunciados deben ser separados por ;  [#,%]");


        /* for */
        gramatica.loopForFunExecUntilChangeNotDetected(() -> {
            gramatica.group("Estructura_For", ""
                    + "For "
                    + "Parentesis_abierto "
                    + "Parentesis_cerrado "
                    + "Bracket_abierto "
                    + "(Lista_enunciados)* "
                    + "Bracket_cerrado");
        });

        /* Programa */
        //gramatica.group("Programa", "(Lista_enunciados)*");
        gramatica.show();

    }

    private void printConsole() {
        int sizeErrors = errors.size();
        if (sizeErrors > 0) {
            //ordenar errores por linea y columna
            Functions.sortErrorsByLineAndColumn(errors);
            String strErrors = "\n";
            for (ErrorLSSL error : errors) {
                String strError = String.valueOf(error);
                strErrors += strError + "\n";
            }
            jtaOutputConsole.append("Compilación terminada...\n" + strErrors + "\nLa compilación terminó con errores...");
        } else {
            jtaOutputConsole.append("Compilación terminada...\n");
        }
        jtaOutputConsole.setCaretPosition(0);
    }

    private void clearFields() {//limpiar tabla
        Functions.clearDataInTable(tblTokens);
        jtaOutputConsole.setText("");
        tokens.clear();
        errors.clear();
        identProd.clear();
        identificadores.clear();
        codeHasBeenCompiled = false;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Compilador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Crear y mostrar ventana, el UIManager.setLookAndFeel añade el diseño a la ventana */
        java.awt.EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(new FlatIntelliJLaf());
            } catch (UnsupportedLookAndFeelException ex) {
                System.out.println("LookAndFeel no soportado: " + ex);
            }
            new Compilador().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnCompilar;
    private javax.swing.JButton btnEjecutar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardarC;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel buttonsFilePanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jtaOutputConsole;
    private javax.swing.JTextPane jtpCode;
    private javax.swing.JPanel panelButtonCompilerExecute;
    private javax.swing.JPanel rootPanel;
    private javax.swing.JTable tblTokens;
    // End of variables declaration//GEN-END:variables
}
