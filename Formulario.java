import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Fernanda Sr
 */
public class Formulario extends JOptionPane{

    private JTextField[] campos;
    private final int ANCHO_VENTANA = 20;
    private boolean ok;
    
    public static String[] mostrarFormulario(Component padre, String[] rotulos, 
                                  String[] valoresIniciales, String titulo){
        Formulario formulario = new Formulario(rotulos, valoresIniciales);
        JDialog dialogo = formulario.createDialog(padre, titulo);

        dialogo.pack();
        dialogo.setVisible(true);

        String[] resultado = new String[rotulos.length];

        for(int i = 0; i < rotulos.length; i++){
            //asignar valores de campos
            resultado[i] = formulario.campos[i].getText();
        }

        return resultado;
    }

    public static String[] mostrarFormulario(Component padre, String[] rotulos, 
                                      String[] valoresIniciales){
        return mostrarFormulario(padre, rotulos, valoresIniciales, null);
    }
         

    public static String[] mostrarFormulario(Component padre, String[] rotulos){
        return mostrarFormulario(padre, rotulos, null, null);

    }
    
    public static String[] mostrarFormulario(Component padre, String[] rotulos, String titulo){
        return mostrarFormulario(padre, rotulos, null, titulo);
    
    }

    private void crearGUI(String[] rotulos, String[] valoresIniciales){
        removeAll();
        
        setLayout(new GridLayout(rotulos.length+1,2,5,5));
        campos = new JTextField[rotulos.length];

        for(int i = 0; i < rotulos.length; i++){
           add(new JLabel(rotulos[i]));
           campos[i] = new JTextField(ANCHO_VENTANA);
           add(campos[i]);
           if(valoresIniciales != null && valoresIniciales[i] != null){
              campos[i].setText(valoresIniciales[i]);
           }
        }

        JPanel panelEnviar = new JPanel();
        JButton btnEnviar = new JButton("Enviar");
        panelEnviar.add(btnEnviar);

        add(panelEnviar);

        btnEnviar.addActionListener((ActionEvent event) -> {
            ok = true;
            getTopLevelAncestor().setVisible(!ok);
        });

        JPanel panelCancelar = new JPanel();
        JButton btnCancelar = new JButton("Cancelar");
        panelCancelar.add(btnCancelar);

        add(panelCancelar);
        
        btnCancelar.addActionListener((ActionEvent event) -> {
            ok = true;
            getTopLevelAncestor().setVisible(!ok);
        });
    }
    
    public Formulario(String[] rotulos, String[] valoresIniciales){
        super();
        crearGUI(rotulos, valoresIniciales);
    }
}
