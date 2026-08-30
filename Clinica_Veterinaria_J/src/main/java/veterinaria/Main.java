package veterinaria;

import veterinaria.view.TelaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        // SwingUtilities.invokeLater() é meio que uma forma "correta" de abrir uma tela gráfica (Swing) em Java.
        // Ele garante que a interface gráfica seja criada e controlada por uma thread
        // (uma "linha de execução") especial, chamada Event Dispatch Thread (EDT),
        // que é responsável por desenhar a tela e reagir a cliques do usuário.
        SwingUtilities.invokeLater(() -> {
            
            new TelaPrincipal().setVisible(true);
        });
    }
}
