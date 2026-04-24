package apresentacao;

import modelo.Controle;
import javax.swing.*;
import java.awt.*;

/**
 * Tela de exibição de uma obra (rover/missão marciana).
 * Exibe número, título, imagem placeholder, descrição e opção de modelo 3D.
 * Progresso visual com barra de obras no topo.
 */
public class fmrObra extends JDialog {

    private final Controle controle;
    private final int indice;

    public fmrObra(JFrame pai, Controle controle, int indice) {
        super(pai, true);
        this.controle = controle;
        this.indice   = indice;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = criarFundo();
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;

        // ── Barra de progresso ────────────────────────────────────────────
        JPanel barraProgress = criarBarraProgresso(tela.width);
        barraProgress.setBounds(0, 0, tela.width, 44);
        fundo.add(barraProgress);

        // ── Número da obra ────────────────────────────────────────────────
        JLabel lblNumero = new JLabel("OBRA " + (indice + 1) + " DE 10", SwingConstants.CENTER);
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNumero.setForeground(EstiloBase.COR_ACENTO);
        lblNumero.setBounds(0, 54, tela.width, 26);
        fundo.add(lblNumero);

        // ── Título da obra ────────────────────────────────────────────────
        JLabel lblTitulo = EstiloBase.criarLabel(
                controle.getTituloObra(indice),
                EstiloBase.FONTE_SECAO, EstiloBase.COR_DESTAQUE
        );
        lblTitulo.setBounds(0, 86, tela.width, 50);
        fundo.add(lblTitulo);

        // ── Layout principal: imagem + texto ──────────────────────────────
        int cardY = 148;
        int cardH = tela.height - cardY - 120;

        // Painel imagem (placeholder estilizado)
        JPanel painelImagem = criarPlaceholderImagem(indice);
        painelImagem.setBounds(cx - 580, cardY, 420, cardH);
        fundo.add(painelImagem);

        // Card de descrição
        JPanel cardDesc = EstiloBase.criarCard();
        cardDesc.setLayout(new BorderLayout(0, 0));
        cardDesc.setBounds(cx - 130, cardY, 700, cardH);
        fundo.add(cardDesc);

        JTextArea txtDesc = new JTextArea(controle.getDescricaoObra(indice));
        txtDesc.setFont(EstiloBase.FONTE_CORPO);
        txtDesc.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        txtDesc.setBackground(EstiloBase.COR_CARD);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        txtDesc.setOpaque(false);
        cardDesc.add(new JScrollPane(txtDesc) {{
            setOpaque(false);
            getViewport().setOpaque(false);
            setBorder(null);
        }}, BorderLayout.CENTER);

        // ── Botão Modelo 3D (a partir da obra 3, índice 2) ────────────────
        if (controle.deveExibirModelo3D(indice)) {
            JButton btn3D = EstiloBase.criarBotaoSecundario("🌐  Ver Modelo 3D do Rover");
            btn3D.setPreferredSize(new Dimension(280, 52));
            btn3D.setBounds(cx + 390, cardY + 20, 290, 52);
            btn3D.addActionListener(e -> abrirModelo3D());
            fundo.add(btn3D);
        }

        // ── Botão Próximo ─────────────────────────────────────────────────
        JButton btnProximo = EstiloBase.criarBotaoPrimario("PRÓXIMO  →");
        int bw = 300, bh = 70;
        btnProximo.setBounds(cx - bw / 2, tela.height - 110, bw, bh);
        btnProximo.addActionListener(e -> {
            dispose();
            controle.proximaEtapaAposObra(indice);
        });
        fundo.add(btnProximo);

        setContentPane(fundo);
    }

    /** Placeholder visual estilizado que simula uma imagem do rover. */
    private JPanel criarPlaceholderImagem(int idx) {
        Color[] cores = {
                new Color(180, 80, 30),  new Color(60, 120, 200), new Color(200, 140, 30),
                new Color(80, 180, 100), new Color(200, 60, 80),  new Color(60, 160, 180),
                new Color(140, 80, 200), new Color(200, 120, 60), new Color(80, 200, 160),
                new Color(220, 180, 40)
        };
        String[] emojis = {"🛸","🚀","🤖","🔭","🛰️","🌋","🔬","📡","🚜","🚁"};

        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fundo com cor temática
                Color c = cores[idx % cores.length];
                g2.setColor(new Color(c.getRed()/4, c.getGreen()/4, c.getBlue()/4));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                // Grade tipo sensor
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 25));
                for (int x = 0; x < getWidth(); x += 30)
                    g2.drawLine(x, 0, x, getHeight());
                for (int y = 0; y < getHeight(); y += 30)
                    g2.drawLine(0, y, getWidth(), y);
                // Borda
                g2.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 120));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
                g2.dispose();
            }
        };
        p.setOpaque(false);

        JLabel lblEmoji = new JLabel(emojis[idx % emojis.length], SwingConstants.CENTER);
        lblEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        p.add(lblEmoji, BorderLayout.CENTER);

        JLabel lblLegenda = new JLabel(
                "[ Imagem: " + controle.getTituloObra(idx) + " ]", SwingConstants.CENTER
        );
        lblLegenda.setFont(EstiloBase.FONTE_PEQUENA);
        lblLegenda.setForeground(EstiloBase.COR_TEXTO_FRACO);
        lblLegenda.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        p.add(lblLegenda, BorderLayout.SOUTH);

        return p;
    }

    /** Abre simulação de modelo 3D (placeholder NASA). */
    private void abrirModelo3D() {
        JDialog dlg = new JDialog(this, "Modelo 3D — " + controle.getTituloObra(indice), true);
        dlg.setSize(600, 400);
        dlg.setLocationRelativeTo(this);
        dlg.setLayout(new BorderLayout());
        dlg.getContentPane().setBackground(EstiloBase.COR_FUNDO_PAINEL);

        JLabel msg = new JLabel(
                "<html><div style='text-align:center; color:#60B4DC; padding:40px;'>"
                        + "<b style='font-size:18px;'>🌐 Modelo 3D do Rover</b><br><br>"
                        + "Integração com NASA 3D Resources<br>"
                        + "<span style='color:#808080; font-size:13px;'>https://nasa3d.arc.nasa.gov/models</span><br><br>"
                        + "<span style='color:#FF7820;'>[Placeholder — conexão com visualizador 3D]</span>"
                        + "</div></html>", SwingConstants.CENTER
        );
        msg.setFont(EstiloBase.FONTE_CORPO);
        dlg.add(msg, BorderLayout.CENTER);

        JButton btnFechar = EstiloBase.criarBotaoPrimario("Fechar");
        btnFechar.addActionListener(e -> dlg.dispose());
        JPanel p = new JPanel();
        p.setBackground(EstiloBase.COR_FUNDO_PAINEL);
        p.add(btnFechar);
        dlg.add(p, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    private JPanel criarBarraProgresso(int largura) {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(15, 25, 55));
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Marcadores de obras
                int total = 10;
                int passo = largura / (total + 2);
                for (int i = 0; i < total; i++) {
                    int x = passo + i * passo;
                    Color c = i <= indice ? EstiloBase.COR_DESTAQUE : EstiloBase.COR_CARD_BORDA;
                    g2.setColor(c);
                    g2.fillRoundRect(x, 12, passo - 6, 20, 8, 8);
                }
                g2.dispose();
            }
        };
        p.setOpaque(false);
        return p;
    }

    private JPanel criarFundo() {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(5, 8, 20),
                        getWidth(), getHeight(), new Color(8, 18, 45)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                EstiloBase.desenharEstrelas(g2, getWidth(), getHeight(), indice * 13L + 5L);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        return p;
    }
}