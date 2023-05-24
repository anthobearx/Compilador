package codigo;

import compilerTools.Directory;
import compilerTools.Functions;
import compilerTools.TextColor;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java_cup.runtime.Symbol;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author antho
 */
public class FrmPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form FrmPrincipal
     */
    private String titulo;
    private Directory directorio;
    private ArrayList<TextColor> textsColor;//colores de los errores
    private Timer timerKeyReleased;//ejecitar funcion para colorear palabras
    public ArrayList<String> listaIdentificadores = new ArrayList<>();
    public ArrayList<String> listaTipos = new ArrayList<>();
    public ArrayList<String> listaValores = new ArrayList<>();
    public int posicion;

    public FrmPrincipal() {
        initComponents();
        titulo = "Compilador - VMP";
        setTitle(titulo);
        directorio = new Directory(this, txtResultado2, titulo, ".txt");

        this.setLocationRelativeTo(null);
        Functions.setLineNumberOnJTextComponent(txtResultado2);

    }

    private void analizarLexico() throws IOException {
        int cont = 1;
        int col = 0;
        int vTabla = 0;
        int ultimoIndice = 0;
        boolean tipo = false;
        boolean iden = false;
        boolean igual = false;

        String expr = (String) txtResultado2.getText();
        Lexer lexer = new Lexer(new StringReader(expr));
        String resultado = "Componente Léxico\tLexema\t[Linea,Columna] \n";
        while (true) {
            Tokens token = lexer.yylex();
            if (token == null) {
                txtAnalizarLex.setText(resultado);
                return;
            }

            switch (token) {
                case Linea -> {
                    igual = false;
                    iden = false;
                    tipo = false;
                    resultado += "\n";
                    cont++;
                    col = 0;
                }
                case Comillas -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Comillas>\t\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Cadena -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case T_dato -> {
                    tipo = true;
                    igual = false;
                    iden = false;
                    resultado += "  <Tipo de dato>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                    listaTipos.add(lexer.lexeme);
                }
                case If -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Reservada if>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Else -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Reservada else>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Do -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Reservada do>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case While -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Reservada while>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case For -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Reservada for>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Igual -> {
                    if (!iden) {
                        tipo = false;//si no hay identificador antes de el, pon falso al tipo
                    } else {//si si hay identificador antes de el, pon el igual true
                        igual = true;//hay un igual, es el unico que no pone en false al identificador
                    }
                    resultado += "  <Operador igual>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Suma -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador suma>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Resta -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador resta>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Multiplicacion -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador multiplicacion>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Division -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador division>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Op_logico -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador logico>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Op_incremento -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador incremento>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Op_relacional -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador relacional>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Op_atribucion -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador atribucion>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Op_booleano -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Operador booleano>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Parentesis_a -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Parentesis de apertura>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Parentesis_c -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Parentesis de cierre>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Llave_a -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Llave de apertura>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Llave_c -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Llave de cierre>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Corchete_a -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Corchete de apertura>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Corchete_c -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Corchete de cierre>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Main -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Reservada main>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case P_coma -> {
                    tipo = false;
                    igual = false;
                    iden = false;
                    resultado += "  <Punto y coma>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                }
                case Identificador -> {
                    //no se ocupa falsear el tipo, ya que despues del tipo se acepta un identificador
                    iden = true;
                    igual = false;//para que no pongan un igual antes de un identificador 
                    resultado += "  <Identificador>\t\t" + analizarString(lexer.lexeme) + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                    //si el identificador existe, guarda su posicion, si no, regresa un -1
                    posicion = listaIdentificadores.indexOf(lexer.lexeme);
                    if (posicion == -1) {
                        //identificador aun no existe, se crea. Si ya existe, no se ocupa recrear
                        listaIdentificadores.add(lexer.lexeme);
                        //vTabla = variables en tabla = numero de variables agregadas correctamente
                        vTabla++;//al ser un nuevo elemento, se suma vTabla
                        listaValores.add("0");//cada que se crea un identificador, el valor va a ser 0
                    }
                }
                case Numero -> {
                    resultado += "  <Numero>\t\t" + analizarString(lexer.lexeme) + "\t         [" + cont + "," + col + "]\n";
                    col = col + lexer.lexeme.length();
                    //si hay un tipo, un identificador y un igual(true) antes de un numero, se asigne a la tabla de valores
                    if (iden && igual) {
                        //se ponen falsos de nuevos, para que se pongan otra vez para asignar numeros
                        igual = false;
                        iden = false;
                        //declaracion y asignacion (creacion por primera vez)
                        if (posicion == -1 && tipo) { //pos = -1 (no se encontro identificador y se asigno un tipo)
                            tipo = false;
                            //El valor se va a asignar por primera vez (se sustituye el 0)

                            listaValores.remove(listaValores.size() - 1);//se quita el 0
                            listaValores.add(lexer.lexeme);
                            //asignacion (de un identificador ya existente)
                        } else if (posicion != -1) {

                            //debe remover los ultimos dos de las otros dos arraylist
                            //el identificador se encontro (se le asigna un valor nuevo)
                            listaValores.remove(posicion);//remover el valor actual
                            listaValores.add(posicion, lexer.lexeme);//se añade nuevo valor asigando
                        }//si no es ni asignacion ni declaracion, no tiene porque ser añadido a la tabla de simbolos

                    }

                    //Comprobacion de que cada identificador tenga su tipo
                    //si no es asi... se quita la ultima fila de la tabla de valores
                    if (listaIdentificadores.size() > listaTipos.size()) {
                        ultimoIndice = listaIdentificadores.size() - 1;
                        listaIdentificadores.remove(ultimoIndice);
                    } else if (listaIdentificadores.size() < listaTipos.size()) {
                        ultimoIndice = listaTipos.size() - 1;
                        listaTipos.remove(ultimoIndice);
                    }
                    //comprobacion de que no haya mas numeros que tipos e identificadores
                    if (listaValores.size() > listaIdentificadores.size()) {

                        listaValores.remove(listaValores.size() - 1);//se quita el ultimo valor sin tipo y identificador

                    }
                }
                case ERROR -> {
                    resultado += "  <Simbolo no definido>\t" + lexer.lexeme + "\t         [" + cont + "," + col + "] <----ERROR\n";
                    col = col + lexer.lexeme.length();
                }
                default ->
                    resultado += "  < " + lexer.lexeme + " >\n";
            }
        }

    }

    public String analizarString(String input) {
        if (input.length() <= 10) {
            return input;
        } else {
            return input.substring(0, 8) + "...";
        }
    }

    public void validadorArregloTipos() {
        //puede existir el caso en el que el ultimo token para la tabla de simbolos haya sido un tipo de dato
        //como la comprobacion esta en el token de numeros, comprobaremos que no exista un tipo sin su
        //identificador
        if (listaTipos.size() > listaIdentificadores.size()) {
            listaTipos.remove(listaTipos.size() - 1);//añadir el ultimo valor
        }

    }

    public void tablaValores() {
        int columna;
        DefaultTableModel model = (DefaultTableModel) tableSimbols.getModel();//obtener modelo de la tabla
        model.setRowCount(0);//limpiar tabla valores
        model.setRowCount(3);//poner 3 filas de nuevo
        Object[] newRows = {"", "", ""};//objeto nuevas columnas

        //añadir valores de los arraylist a la tabla
        //identificadores
        columna = 0;
        for (int fila = 0; fila < listaIdentificadores.size(); fila++) {
            tableSimbols.setValueAt(listaIdentificadores.get(fila), fila, columna);
            model.addRow(newRows);
        }
        //tipos
        columna++;
        for (int fila = 0; fila < listaTipos.size(); fila++) {
            tableSimbols.setValueAt(listaTipos.get(fila), fila, columna);
        }
        //numeros
        columna++;
        for (int fila = 0; fila < listaValores.size(); fila++) {
            tableSimbols.setValueAt(listaValores.get(fila), fila, columna);
        }
        //añadir una fila para poder agregar mas datos en un futuro
        tableSimbols.repaint();//repintar tabla de valores
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnArchivo = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAnalizarLex = new javax.swing.JTextArea();
        btnAnalizarLex = new javax.swing.JButton();
        btnLimpiarLex = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtResultado2 = new javax.swing.JTextPane();
        lblTablaSimbolos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSimbols = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtAnalizarSin = new javax.swing.JTextArea();
        btnAnalizarSin = new javax.swing.JButton();
        btnLimpiarSin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Léxico", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        btnArchivo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnArchivo.setText("Abrir");
        btnArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnArchivoActionPerformed(evt);
            }
        });

        txtAnalizarLex.setEditable(false);
        txtAnalizarLex.setColumns(20);
        txtAnalizarLex.setRows(5);
        jScrollPane2.setViewportView(txtAnalizarLex);

        btnAnalizarLex.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAnalizarLex.setText("Analizar");
        btnAnalizarLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarLexActionPerformed(evt);
            }
        });

        btnLimpiarLex.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnLimpiarLex.setText("Limpiar");
        btnLimpiarLex.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarLexActionPerformed(evt);
            }
        });

        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnNuevo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        txtResultado2.setText("Escribe aqui o importa el .txt...");
        txtResultado2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtResultado2MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(txtResultado2);

        lblTablaSimbolos.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        lblTablaSimbolos.setText("Tabla de simbolos");

        tableSimbols.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Identificador", "Tipo", "Valor"
            }
        ));
        jScrollPane1.setViewportView(tableSimbols);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnArchivo)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addGap(86, 86, 86))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAnalizarLex)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiarLex))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(lblTablaSimbolos)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnNuevo)
                        .addComponent(btnArchivo)
                        .addComponent(btnGuardar)
                        .addComponent(btnAnalizarLex)
                        .addComponent(btnLimpiarLex))
                    .addComponent(lblTablaSimbolos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(186, 186, 186))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Sintatico", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

        txtAnalizarSin.setEditable(false);
        txtAnalizarSin.setColumns(20);
        txtAnalizarSin.setRows(5);
        jScrollPane3.setViewportView(txtAnalizarSin);

        btnAnalizarSin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnAnalizarSin.setText("Analizar");
        btnAnalizarSin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnalizarSinActionPerformed(evt);
            }
        });

        btnLimpiarSin.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnLimpiarSin.setText("Limpiar");
        btnLimpiarSin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarSinActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnAnalizarSin)
                        .addGap(1023, 1023, 1023)
                        .addComponent(btnLimpiarSin))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1201, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnalizarSin)
                    .addComponent(btnLimpiarSin))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarLexActionPerformed
        // TODO add your handling code here:
        txtAnalizarLex.setText(null);
    }//GEN-LAST:event_btnLimpiarLexActionPerformed

    private void btnAnalizarLexActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarLexActionPerformed
        try {
            listaIdentificadores.clear();
            listaTipos.clear();
            listaValores.clear();
            analizarLexico();
            validadorArregloTipos();
            tablaValores();

        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAnalizarLexActionPerformed

    private void btnArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnArchivoActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File archivo = new File(chooser.getSelectedFile().getAbsolutePath());

        try {
            String ST = new String(Files.readAllBytes(archivo.toPath()));
            txtResultado2.setText(ST);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FrmPrincipal.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(FrmPrincipal.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnArchivoActionPerformed

    private void btnLimpiarSinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarSinActionPerformed
        // TODO add your handling code here:
        txtAnalizarSin.setText(null);
    }//GEN-LAST:event_btnLimpiarSinActionPerformed

    private void btnAnalizarSinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnalizarSinActionPerformed
        // TODO add your handling code here:
        String ST = txtResultado2.getText();
        Sintax s = new Sintax(new codigo.LexerCup(new StringReader(ST)));
        ArrayList<String> errores = new ArrayList<>();//Lista de errores
        Symbol sym;

        try {
            s.parse();
            txtAnalizarSin.setText("Analisis correcto");
            txtAnalizarSin.setForeground(new Color(25, 111, 61));
        } catch (Exception ex) {
            //capturar todos los errores y guardarlos en el arraylist errores
            while (true) {

                // Intentar continuar el análisis
                try {
                    s.parse();
                } catch (Exception e) {
                    sym = s.getS();
                    errores.add("Error de sintaxis. Linea: " + (sym.right+1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"");
                    try {
                        s.parse();
                    } catch (Exception e2) {
                        sym = s.getS();
                        errores.add("Error de sintaxis. Linea: " + (sym.right+1) + " Columna: " + (sym.left + 1) + ", Texto: \"" + sym.value + "\"");

                        break; // Salir del bucle si no se puede continuar el análisis
                    }
                    break; // Salir del bucle si no se puede continuar el análisis
                }
            }
            // Mostrar todos los errores almacenados en la lista
            StringBuilder sb = new StringBuilder();
            int numerrores = 0;
            for (String error : errores) {
                numerrores++;
                sb.append(error).append("\n");
            }
            sb.append("Analisis terminado con ").append(numerrores).append(" errores");
            txtAnalizarSin.setText(sb.toString());
            txtAnalizarSin.setForeground(Color.red);
        }
    }//GEN-LAST:event_btnAnalizarSinActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        if (directorio.SaveAs()) {
            JOptionPane.showMessageDialog(null, "El archivo ha sido guardado exitosamente", "Guardado exitoso", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        // TODO add your handling code here:
        txtResultado2.setText(null);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void txtResultado2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtResultado2MouseClicked
        // TODO add your handling code here:
        if (txtResultado2.getText().equals("Escribe aqui o importa el .txt..."))
            txtResultado2.setText("");
    }//GEN-LAST:event_txtResultado2MouseClicked

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
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new FrmPrincipal().setVisible(true);
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnalizarLex;
    private javax.swing.JButton btnAnalizarSin;
    private javax.swing.JButton btnArchivo;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiarLex;
    private javax.swing.JButton btnLimpiarSin;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblTablaSimbolos;
    private javax.swing.JTable tableSimbols;
    private javax.swing.JTextArea txtAnalizarLex;
    private javax.swing.JTextArea txtAnalizarSin;
    private javax.swing.JTextPane txtResultado2;
    // End of variables declaration//GEN-END:variables
}
