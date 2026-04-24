package apresentacao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Teclado virtual próprio para uso em touchscreen.
 *
 * Características:
 *  - Estende JDialog: pode ser exibido com setVisible(true)
 *  - Recebe qualquer JDialog como janela pai e um JTextField como alvo
 *  - Suporta letras (maiúsculas/minúsculas), números e backspace
 *  - Botão Shift para alternar maiúsculas/minúsculas
 *  - Botão Limpar (CLR) para apagar tudo
 *  - Botão OK confirma a entrada e fecha o teclado
 *  - Design escuro consistente com o restante do sistema
 */
public class TecladoVirtual extends JDialog {

    // ── Campo que receberá o texto digitado ───────────────────────────────
    private final JTextField campoAlvo;

    // ── Estado ────────────────────────────────────────────────────────────
    private boolean maiusculas = true;

    // ── Layout do teclado QWERTY ──────────────────────────────────────────
    private static final String[][] LINHAS = {
            {"1","2","3","4","5","6","7","8","9","0"},
            {"Q","W","E","R","T","Y","U","I","O","P"},
            {"A","S","D","F","G","H","J","K","L","Ç"},
            {"Z","X","C","V","B","N","M",",",".","'"}
    };

    // Painel que contém os botões de letras (para atualizar ao Shift)
    private JPanel painelTeclas;

    // =========================================================================
    // CONSTRUTOR
    // =========================================================================

    /**
     * @param pai       JDialog que originou a chamada (ex: fmrCadastroVisitante)
     * @param campoAlvo JTextField onde o texto será inserido
     */
    public TecladoVirtual(JDialog pai, JTextField campoAlvo) {
        super(pai, "Teclado Virtual", true);
        this.campoAlvo = campoAlvo;
        configurarJanela();
        construirInterface();
    }

    // =========================================================================
    // CONFIGURAÇÃO DA JANELA
    // =========================================================================

    private void configurarJanela() {
        setUndecorated(true);
        setSize(900, 440);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(8, 14, 38));
    }

    // =========================================================================
    // INTERFACE
    // =========================================================================

    private void construirInterface() {
        JPanel fundo = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(8, 14, 38));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(40, 70, 140));
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
                g2.dispose();
            }
        };
        fundo.setOpaque(false);
        fundo.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        // ── Preview do texto ─────────────────────────────────────────────
        JLabel lblPreview = new JLabel(" ", SwingConstants.LEFT);
        lblPreview.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        lblPreview.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblPreview.setOpaque(true);
        lblPreview.setBackground(new Color(5, 8, 20));
        lblPreview.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloBase.COR_CARD_BORDA, 1, true),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)
        ));
        // Sincroniza preview com o campo alvo
        lblPreview.setText(campoAlvo.getText().isBlank() ? " " : campoAlvo.getText());
        fundo.add(lblPreview, BorderLayout.NORTH);

        // ── Painel de teclas ─────────────────────────────────────────────
        painelTeclas = new JPanel(new GridLayout(4, 10, 6, 6));
        painelTeclas.setOpaque(false);
        construirTeclas(painelTeclas, lblPreview);
        fundo.add(painelTeclas, BorderLayout.CENTER);

        // ── Barra inferior: Shift | Espaço | ⌫ | CLR | OK ───────────────
        JPanel barraInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 0));
        barraInferior.setOpaque(false);

        JButton btnShift = criarTeclaEspecial("⇧ Shift", 110, 56);
        btnShift.addActionListener(e -> {
            maiusculas = !maiusculas;
            btnShift.setText(maiusculas ? "⇧ Shift" : "⇩ shift");
            construirTeclas(painelTeclas, lblPreview);
            painelTeclas.revalidate();
            painelTeclas.repaint();
        });

        JButton btnEspaco = criarTeclaEspecial("ESPAÇO", 300, 56);
        btnEspaco.addActionListener(e -> {
            campoAlvo.setText(campoAlvo.getText() + " ");
            lblPreview.setText(campoAlvo.getText());
        });

        JButton btnBackspace = criarTeclaEspecial("⌫", 80, 56);
        btnBackspace.addActionListener(e -> {
            String atual = campoAlvo.getText();
            if (!atual.isEmpty()) {
                campoAlvo.setText(atual.substring(0, atual.length() - 1));
                lblPreview.setText(campoAlvo.getText().isBlank() ? " " : campoAlvo.getText());
            }
        });

        JButton btnLimpar = criarTeclaEspecial("CLR", 80, 56);
        btnLimpar.addActionListener(e -> {
            campoAlvo.setText("");
            lblPreview.setText(" ");
        });

        JButton btnOK = criarTeclaEspecial("✓ OK", 110, 56);
        btnOK.setForeground(EstiloBase.COR_SUCESSO);
        btnOK.addActionListener(e -> dispose());

        barraInferior.add(btnShift);
        barraInferior.add(btnEspaco);
        barraInferior.add(btnBackspace);
        barraInferior.add(btnLimpar);
        barraInferior.add(btnOK);

        fundo.add(barraInferior, BorderLayout.SOUTH);
        setContentPane(fundo);
    }

    /**
     * Popula o painel de teclas com base no estado maiusculas/minúsculas.
     * É chamado novamente ao pressionar Shift para atualizar os rótulos.
     */
    private void construirTeclas(JPanel painel, JLabel preview) {
        painel.removeAll();
        for (String[] linha : LINHAS) {
            for (String tecla : linha) {
                String label = maiusculas ? tecla : tecla.toLowerCase();
                JButton btn  = criarTeclaLetra(label);
                btn.addActionListener(e -> {
                    campoAlvo.setText(campoAlvo.getText() + label);
                    preview.setText(campoAlvo.getText());
                });
                painel.add(btn);
            }
        }
    }

    // =========================================================================
    // FÁBRICA DE BOTÕES
    // =========================================================================

    /** Botão de letra/número com estilo escuro e hover azulado. */
    private JButton criarTeclaLetra(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed()  ? new Color(60, 90, 180) :
                        getModel().isRollover() ? new Color(30, 50, 120) :
                        new Color(18, 28, 65);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(new Color(40, 70, 140));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
                g2.setColor(EstiloBase.COR_TEXTO_PRIMARIO);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 16));
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(78, 52));
        return btn;
    }

    /** Botão especial (Shift, Espaço, ⌫, CLR, OK) com largura customizável. */
    private JButton criarTeclaEspecial(String texto, int largura, int altura) {
        JButton btn = criarTeclaLetra(texto);
        btn.setPreferredSize(new Dimension(largura, altura));
        return btn;
    }
}