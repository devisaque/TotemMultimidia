package apresentacao;

import javax.swing.*;
import java.awt.*;

/**
 * Teclado virtual interno para uso em touchscreen.
 */
public class TecladoVirtual extends JDialog {

    private final JTextField campoAlvo;
    private boolean maiusculas = true;

    private static final String[][] LINHAS = {
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"},
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ç"},
            {"Z", "X", "C", "V", "B", "N", "M", ",", ".", "'"}
    };

    private JPanel painelTeclas;

    public TecladoVirtual(JDialog pai, JTextField campoAlvo) {
        super(pai, "Teclado Virtual", true);
        this.campoAlvo = campoAlvo;
        configurarJanela();
        construirInterface();
    }

    private void configurarJanela() {
        setUndecorated(true);
        setSize(940, 470);
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(EstiloBase.COR_FUNDO);
    }

    private void construirInterface() {
        JPanel fundo = EstiloBase.criarPainelFundo(915L);
        fundo.setLayout(new BorderLayout(0, 14));
        fundo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel card = EstiloBase.criarCard();
        card.setLayout(new BorderLayout(0, 14));
        card.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        fundo.add(card, BorderLayout.CENTER);

        JPanel topo = new JPanel(new BorderLayout(0, 12));
        topo.setOpaque(false);

        JLabel lblTitulo = EstiloBase.criarLabel("Teclado virtual", EstiloBase.fontePoppins(24f), EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        topo.add(lblTitulo, BorderLayout.NORTH);

        JLabel lblPreview = new JLabel(" ", SwingConstants.LEFT);
        lblPreview.setFont(EstiloBase.fonteInter(22f));
        lblPreview.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblPreview.setOpaque(true);
        lblPreview.setBackground(new Color(10, 10, 16));
        lblPreview.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 22), 1, true),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        ));
        lblPreview.setText(campoAlvo.getText().isBlank() ? " " : campoAlvo.getText());
        topo.add(lblPreview, BorderLayout.CENTER);

        card.add(topo, BorderLayout.NORTH);

        painelTeclas = new JPanel(new GridLayout(4, 10, 8, 8));
        painelTeclas.setOpaque(false);
        construirTeclas(painelTeclas, lblPreview);
        card.add(painelTeclas, BorderLayout.CENTER);

        JPanel barraInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        barraInferior.setOpaque(false);

        JButton btnShift = criarTeclaEspecial("Shift", 118, 58);
        btnShift.addActionListener(e -> {
            maiusculas = !maiusculas;
            btnShift.setText(maiusculas ? "Shift" : "shift");
            construirTeclas(painelTeclas, lblPreview);
            painelTeclas.revalidate();
            painelTeclas.repaint();
        });

        JButton btnEspaco = criarTeclaEspecial("Espaco", 300, 58);
        btnEspaco.addActionListener(e -> {
            campoAlvo.setText(campoAlvo.getText() + " ");
            lblPreview.setText(campoAlvo.getText());
        });

        JButton btnBackspace = criarTeclaEspecial("Apagar", 104, 58);
        btnBackspace.addActionListener(e -> {
            String atual = campoAlvo.getText();
            if (!atual.isEmpty()) {
                campoAlvo.setText(atual.substring(0, atual.length() - 1));
                lblPreview.setText(campoAlvo.getText().isBlank() ? " " : campoAlvo.getText());
            }
        });

        JButton btnLimpar = criarTeclaEspecial("Limpar", 104, 58);
        btnLimpar.addActionListener(e -> {
            campoAlvo.setText("");
            lblPreview.setText(" ");
        });

        JButton btnOK = EstiloBase.criarBotaoPrimario("Confirmar");
        btnOK.setPreferredSize(new Dimension(156, 58));
        btnOK.addActionListener(e -> dispose());

        barraInferior.add(btnShift);
        barraInferior.add(btnEspaco);
        barraInferior.add(btnBackspace);
        barraInferior.add(btnLimpar);
        barraInferior.add(btnOK);

        card.add(barraInferior, BorderLayout.SOUTH);
        setContentPane(fundo);
    }

    private void construirTeclas(JPanel painel, JLabel preview) {
        painel.removeAll();
        for (String[] linha : LINHAS) {
            for (String tecla : linha) {
                String label = maiusculas ? tecla : tecla.toLowerCase();
                JButton btn = criarTeclaLetra(label);
                btn.addActionListener(e -> {
                    campoAlvo.setText(campoAlvo.getText() + label);
                    preview.setText(campoAlvo.getText());
                });
                painel.add(btn);
            }
        }
    }

    private JButton criarTeclaLetra(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isPressed() ? new Color(255, 115, 54, 180)
                        : getModel().isRollover() ? new Color(255, 255, 255, 16)
                        : new Color(255, 255, 255, 10);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                GradientPaint borda = new GradientPaint(0, 0, EstiloBase.COR_CARD_BORDA,
                        getWidth(), getHeight(), EstiloBase.COR_CARD_GLOW);
                g2.setPaint(borda);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();

                setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
                setFont(EstiloBase.fontePoppins(18f));
                super.paintComponent(g);
            }
        };
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(78, 54));
        return btn;
    }

    private JButton criarTeclaEspecial(String texto, int largura, int altura) {
        JButton btn = EstiloBase.criarBotaoSecundario(texto);
        btn.setPreferredSize(new Dimension(largura, altura));
        return btn;
    }
}
