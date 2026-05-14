import modelo.Controle;
import apresentacao.EstiloBase;
import javax.swing.*;

/**
 * Ponto de entrada do sistema Totem Interativo.
 */
public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        // Aplica a identidade visual antes de abrir qualquer tela do fluxo.
        EstiloBase.aplicarFonteGlobal();

        // O controlador passa a gerenciar toda a navegacao a partir da tela inicial.
        SwingUtilities.invokeLater(Controle::new);
    }
}
