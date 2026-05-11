package apresentacao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * Teclado virtual interno para uso em touchscreen.
 */
public class TecladoVirtual extends JDialog {

    private final JTextField campoAlvo;
    private boolean maiusculas = true;

    private JPanel painelTeclas;

    private static final String[][] LINHAS = {
            {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"},
            {"Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P"},
            {"A", "S", "D", "F", "G", "H", "J", "K", "L", "Ç"},
            {"Z", "X", "C", "V", "B", "N", "M", ",", ".", "'"}
    };

    public TecladoVirtual(JDialog pai, JTextField campoAlvo) {
        super(pai, "Teclado Virtual", true);
        this.campoAlvo = campoAlvo;
        configurarJanela();
        construirInterface();
    }

    private void configurarJanela() {
        setUndecorated(true);

        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int largura = Math.min(940, tela.width - 40);
        int altura = Math.min(470, tela.height - 40);

        setSize(largura, altura);

        int x = (tela.width - largura) / 2;
        int y = tela.height - altura - 20;
        setLocation(x, y);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
    }

    private void construirInterface() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int margem = EstiloBase.escalar(20, tela);

        JPanel fundo = new JPanel(new BorderLayout(0, EstiloBase.escalar(14, tela)));
        fundo.setOpaque(false);
        fundo.setBorder(BorderFactory.createEmptyBorder(margem, margem, margem, margem));

        JPanel card = EstiloBase.criarCardDestaque();
        card.setLayout(new BorderLayout(0, EstiloBase.escalar(14, tela)));
        card.setBorder(BorderFactory.createEmptyBorder(
                EstiloBase.escalar(18, tela),
                EstiloBase.escalar(18, tela),
                EstiloBase.escalar(18, tela),
                EstiloBase.escalar(18, tela)
        ));
        fundo.add(card, BorderLayout.CENTER);

        JPanel topo = new JPanel(new BorderLayout(0, 12));
        topo.setOpaque(false);

        JPanel barraTitulo = new JPanel(new BorderLayout());
        barraTitulo.setOpaque(false);

        JLabel lblTitulo = EstiloBase.criarLabel(
                "Teclado virtual",
                EstiloBase.fonteResponsiva(24f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);

        JButton btnFechar = new JButton("x");
        btnFechar.setFont(EstiloBase.fonteResponsiva(20f, tela));
        btnFechar.setForeground(Color.WHITE);
        btnFechar.setFocusPainted(false);
        btnFechar.setBorderPainted(false);
        btnFechar.setContentAreaFilled(false);
        btnFechar.setOpaque(false);
        btnFechar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnFechar.setMargin(new Insets(0, 0, 0, 0));
        btnFechar.setHorizontalAlignment(SwingConstants.CENTER);
        btnFechar.setVerticalAlignment(SwingConstants.CENTER);
        btnFechar.setPreferredSize(new Dimension(EstiloBase.escalar(56, tela), EstiloBase.escalar(44, tela)));
        btnFechar.setToolTipText("Fechar teclado");
        btnFechar.addActionListener(e -> dispose());

        barraTitulo.add(lblTitulo, BorderLayout.WEST);
        barraTitulo.add(btnFechar, BorderLayout.EAST);

        topo.add(barraTitulo, BorderLayout.NORTH);

        JLabel lblPreview = new JLabel(" ", SwingConstants.LEFT);
        lblPreview.setFont(EstiloBase.fonteResponsiva(22f, tela));
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

        painelTeclas = new JPanel(new GridLayout(4, 10, EstiloBase.escalar(8, tela), EstiloBase.escalar(8, tela)));
        painelTeclas.setOpaque(false);
        construirTeclas(painelTeclas, lblPreview);
        card.add(painelTeclas, BorderLayout.CENTER);

        JPanel barraInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, EstiloBase.escalar(10, tela), 0));
        barraInferior.setOpaque(false);

        JButton btnShift = criarTeclaEspecial("Shift", 118, 58);
        btnShift.addActionListener(e -> {
            maiusculas = !maiusculas;
            btnShift.setText(maiusculas ? "Shift" : "shift");
            construirTeclas(painelTeclas, lblPreview);
            painelTeclas.revalidate();
            painelTeclas.repaint();
        });

        JButton btnEspaco = criarTeclaEspecial("Espaço", 300, 58);
        btnEspaco.addActionListener(e -> {
            campoAlvo.setText(campoAlvo.getText() + " ");
            lblPreview.setText(campoAlvo.getText().isBlank() ? " " : campoAlvo.getText());
        });

        JButton btnBackspace = criarTeclaEspecial("Apagar", 104, 58);
        btnBackspace.addActionListener(e -> {
            String atual = campoAlvo.getText();
            if (!atual.isEmpty()) {
                campoAlvo.setText(atual.substring(0, atual.length() - 1));
                String novoTexto = campoAlvo.getText();

                lblPreview.setText(novoTexto.isBlank() ? " " : novoTexto);

                if (novoTexto.isBlank()) {
                    maiusculas = true;
                    btnShift.setText("Shift");
                    construirTeclas(painelTeclas, lblPreview);
                    painelTeclas.revalidate();
                    painelTeclas.repaint();
                }
            }
        });

        JButton btnLimpar = criarTeclaEspecial("Limpar", 104, 58);
        btnLimpar.addActionListener(e -> {
            campoAlvo.setText("");
            lblPreview.setText(" ");
            maiusculas = true;
            btnShift.setText("Shift");
            construirTeclas(painelTeclas, lblPreview);
            painelTeclas.revalidate();
            painelTeclas.repaint();
        });

        JButton btnOK = EstiloBase.criarBotaoPrimario("Confirmar");
        btnOK.setFont(EstiloBase.fonteResponsiva(18f, tela));
        btnOK.setPreferredSize(new Dimension(EstiloBase.escalar(156, tela), EstiloBase.escalar(58, tela)));
        btnOK.addActionListener(e -> dispose());

        barraInferior.add(btnShift);
        barraInferior.add(btnEspaco);
        barraInferior.add(btnBackspace);
        barraInferior.add(btnLimpar);
        barraInferior.add(btnOK);

        card.add(barraInferior, BorderLayout.SOUTH);
        setContentPane(fundo);
        getRootPane().setOpaque(false);
    }

    private void construirTeclas(JPanel painel, JLabel preview) {
        painel.removeAll();

        for (String[] linha : LINHAS) {
            for (String tecla : linha) {
                String label = maiusculas ? tecla : tecla.toLowerCase();

                JButton btn = criarTeclaLetra(label);
                btn.addActionListener(e -> {
                    campoAlvo.setText(campoAlvo.getText() + label);
                    preview.setText(campoAlvo.getText().isBlank() ? " " : campoAlvo.getText());

                    if (maiusculas && tecla.matches("[A-ZÇ]")) {
                        maiusculas = false;
                        construirTeclas(painelTeclas, preview);
                        painelTeclas.revalidate();
                        painelTeclas.repaint();
                    }
                });

                painel.add(btn);
            }
        }
    }

    private JButton criarTeclaLetra(String texto) {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color bg = getModel().isPressed()
                        ? new Color(255, 115, 54, 180)
                        : getModel().isRollover()
                        ? new Color(255, 255, 255, 16)
                        : new Color(255, 255, 255, 10);

                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                GradientPaint borda = new GradientPaint(
                        0, 0, EstiloBase.COR_CARD_BORDA,
                        getWidth(), getHeight(), EstiloBase.COR_CARD_GLOW
                );
                g2.setPaint(borda);
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.setFont(EstiloBase.fonteResponsiva(17f, tela));
        btn.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        btn.setMargin(new Insets(4, 4, 6, 4));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(EstiloBase.escalar(78, tela), EstiloBase.escalar(58, tela)));

        return btn;
    }

    private JButton criarTeclaEspecial(String texto, int largura, int altura) {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        JButton btn = EstiloBase.criarBotaoSecundario(texto);
        btn.setFont(EstiloBase.fonteResponsiva(17f, tela));
        btn.setPreferredSize(new Dimension(EstiloBase.escalar(largura, tela), EstiloBase.escalar(altura, tela)));
        return btn;
    }
}