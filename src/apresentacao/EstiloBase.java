package apresentacao;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Fundacao visual compartilhada de toda a interface.
 * Centraliza paleta, tipografia, componentes e dialogos auxiliares.
 */
public final class EstiloBase {

    private static final Font BASE_POPPINS = carregarFonte("/fonts/Poppins-SemiBold.ttf", "Segoe UI");

    public static final Color COR_FUNDO = new Color(6, 6, 10);
    public static final Color COR_FUNDO_PAINEL = new Color(12, 12, 18);
    public static final Color COR_PRETO_60 = new Color(0, 0, 0, 153);
    public static final Color COR_CARD = COR_PRETO_60;
    public static final Color COR_CARD_BORDA = new Color(255, 255, 255, 30);
    public static final Color COR_CARD_GLOW = new Color(255, 98, 36, 96);
    public static final Color COR_DESTAQUE = new Color(255, 98, 36);
    public static final Color COR_DESTAQUE_HOVER = new Color(255, 126, 70);
    public static final Color COR_DESTAQUE_2 = new Color(255, 195, 5);
    public static final Color COR_ACENTO = new Color(233, 32, 97);
    public static final Color COR_ACENTO_FRIO = new Color(255, 220, 203);
    public static final Color COR_TEXTO_PRIMARIO = new Color(247, 243, 239);
    public static final Color COR_TEXTO_SECUNDARIO = new Color(198, 191, 188);
    public static final Color COR_TEXTO_FRACO = new Color(138, 132, 130);
    public static final Color COR_SUCESSO = new Color(132, 211, 147);
    public static final Color COR_ERRO = new Color(255, 107, 107);

    public static final Font FONTE_TITULO = fontePoppins(48f);
    public static final Font FONTE_SUBTITULO = fontePoppins(22f);
    public static final Font FONTE_SECAO = fontePoppins(28f);
    public static final Font FONTE_CORPO = fontePoppins(18f);
    public static final Font FONTE_LABEL = fontePoppins(15f);
    public static final Font FONTE_BOTAO = fontePoppins(19f);
    public static final Font FONTE_PEQUENA = fontePoppins(14f);

    private EstiloBase() {
    }

    public static void configurarDialogFullscreen(JDialog dialog) {
        dialog.setUndecorated(true);
        dialog.setBackground(COR_FUNDO);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setSize(tela);
        dialog.setLocation(0, 0);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public static void aplicarFonteGlobal() {
        Font fontePadrao = fontePoppins(14f);
        for (Object chave : UIManager.getDefaults().keySet().toArray()) {
            Object valor = UIManager.get(chave);
            if (valor instanceof Font) {
                UIManager.put(chave, fontePadrao);
            }
        }
    }

    public static JLabel criarLabel(String texto, Font fonte, Color cor) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(fonte);
        label.setForeground(cor);
        return label;
    }

    public static JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                ativarQualidade(g2);

                float alpha = isEnabled() ? 1f : 0.4f;
                g2.setComposite(AlphaComposite.SrcOver.derive(alpha));

                int sombra = getModel().isPressed() ? 2 : 6;
                g2.setColor(new Color(0, 0, 0, 90));
                g2.fillRoundRect(0, sombra, getWidth(), getHeight() - sombra, 30, 30);

                Color inicio = getModel().isPressed() ? COR_DESTAQUE.darker()
                        : getModel().isRollover() ? COR_DESTAQUE_HOVER : COR_DESTAQUE;
                Color fim = getModel().isPressed() ? COR_DESTAQUE_2.darker() : COR_DESTAQUE_2;
                GradientPaint gp = new GradientPaint(0, 0, inicio, getWidth(), getHeight(), fim);
                g2.setPaint(gp);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight() - sombra, 30, 30));

                g2.setColor(new Color(255, 255, 255, 55));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - sombra - 3, 30, 30);

                g2.setColor(COR_TEXTO_PRIMARIO);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = ((getHeight() - sombra) + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setFont(FONTE_BOTAO);
        btn.setPreferredSize(new Dimension(280, 70));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JButton criarBotaoSecundario(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                ativarQualidade(g2);

                float alpha = isEnabled() ? 1f : 0.4f;
                g2.setComposite(AlphaComposite.SrcOver.derive(alpha));

                boolean hover = getModel().isRollover() || getModel().isPressed();
                Color fundo = hover ? new Color(255, 255, 255, 20) : new Color(255, 255, 255, 10);
                g2.setColor(fundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

                GradientPaint borda = new GradientPaint(
                        0, 0, hover ? COR_DESTAQUE : COR_CARD_BORDA,
                        getWidth(), getHeight(), hover ? COR_ACENTO : COR_CARD_GLOW
                );
                g2.setPaint(borda);
                g2.setStroke(new BasicStroke(1.8f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 28, 28);

                g2.setColor(hover ? COR_TEXTO_PRIMARIO : COR_TEXTO_SECUNDARIO);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setFont(FONTE_BOTAO.deriveFont(17f));
        btn.setPreferredSize(new Dimension(240, 60));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public static JTextField criarCampoTexto(String placeholder, int colunas) {
        JTextField campo = new JTextField(colunas) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                ativarQualidade(g2);
                g2.setColor(new Color(10, 10, 16));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
                g2.setColor(new Color(255, 255, 255, 12));
                g2.fillRoundRect(1, 1, getWidth() - 2, getHeight() / 2, 20, 20);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        campo.setFont(FONTE_CORPO.deriveFont(19f));
        campo.setForeground(COR_TEXTO_PRIMARIO);
        campo.setBackground(new Color(10, 10, 16));
        campo.setCaretColor(COR_DESTAQUE);
        campo.setOpaque(false);
        campo.setBorder(criarBordaCampo(COR_CARD_BORDA));
        campo.setPreferredSize(new Dimension(380, 58));
        campo.setToolTipText(placeholder);

        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                campo.setBorder(criarBordaCampo(COR_DESTAQUE));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                campo.setBorder(criarBordaCampo(COR_CARD_BORDA));
            }
        });
        return campo;
    }

    public static JPanel criarCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                ativarQualidade(g2);

                g2.setColor(COR_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

                g2.setPaint(new GradientPaint(0, 0, COR_CARD_BORDA, getWidth(), getHeight(), COR_CARD_GLOW));
                g2.setStroke(new BasicStroke(1.4f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);

                g2.setColor(new Color(255, 255, 255, 16));
                g2.drawRoundRect(10, 10, getWidth() - 21, getHeight() - 21, 22, 22);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        return card;
    }

    public static JPanel criarPainelFundo(long seed) {
        JPanel painel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                desenharFundoGradiente(g2, getWidth(), getHeight(), seed);
                g2.dispose();
            }
        };
        painel.setOpaque(false);
        return painel;
    }

    public static JLabel criarTag(String texto) {
        JLabel tag = new JLabel(" " + texto.toUpperCase() + " ", SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                ativarQualidade(g2);
                g2.setColor(new Color(255, 255, 255, 12));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2.setColor(new Color(255, 255, 255, 30));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        tag.setFont(FONTE_LABEL.deriveFont(13f));
        tag.setForeground(COR_ACENTO_FRIO);
        tag.setBorder(BorderFactory.createEmptyBorder(7, 12, 7, 12));
        return tag;
    }

    public static JScrollPane criarScrollPane(Component conteudo) {
        JScrollPane scroll = new JScrollPane(conteudo);
        scroll.setBorder(null);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getVerticalScrollBar().setUnitIncrement(22);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scroll.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = new Color(255, 255, 255, 70);
                trackColor = new Color(255, 255, 255, 10);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return criarBotaoScroll();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return criarBotaoScroll();
            }

            private JButton criarBotaoScroll() {
                JButton botao = new JButton();
                botao.setPreferredSize(new Dimension(0, 0));
                botao.setOpaque(false);
                botao.setContentAreaFilled(false);
                botao.setBorderPainted(false);
                return botao;
            }
        });
        return scroll;
    }

    public static void marcarCampoComErro(JTextField campo) {
        campo.setBorder(criarBordaCampo(COR_ERRO));
    }

    public static void restaurarCampo(JTextField campo) {
        campo.setBorder(criarBordaCampo(COR_CARD_BORDA));
    }

    public static void mostrarDialogoInformativo(Window owner, String marcador, String titulo, String mensagem, String textoBotao) {
        JDialog dialogo = new JDialog(owner, titulo, Dialog.ModalityType.APPLICATION_MODAL);
        dialogo.setUndecorated(true);
        dialogo.setSize(560, 360);
        dialogo.setLocationRelativeTo(owner);

        JPanel fundo = criarPainelFundo(808L);
        fundo.setLayout(null);

        JPanel card = criarCard();
        card.setLayout(null);
        card.setBounds(24, 24, 512, 312);
        fundo.add(card);

        JLabel lblMarcador = criarTag(marcador);
        lblMarcador.setBounds(28, 24, 120, 32);
        card.add(lblMarcador);

        JLabel lblTitulo = criarLabel(titulo, FONTE_SECAO.deriveFont(30f), COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(28, 70, 456, 40);
        card.add(lblTitulo);

        JTextArea corpo = new JTextArea(mensagem);
        corpo.setEditable(false);
        corpo.setWrapStyleWord(true);
        corpo.setLineWrap(true);
        corpo.setOpaque(false);
        corpo.setForeground(COR_TEXTO_SECUNDARIO);
        corpo.setFont(FONTE_CORPO);
        corpo.setBounds(28, 124, 456, 108);
        card.add(corpo);

        JButton btnFechar = criarBotaoPrimario(textoBotao);
        btnFechar.setBounds(156, 242, 200, 58);
        btnFechar.addActionListener(e -> dialogo.dispose());
        card.add(btnFechar);

        dialogo.setContentPane(fundo);
        dialogo.setVisible(true);
    }

    public static void desenharEstrelas(Graphics2D g2, int largura, int altura, long seed) {
        Random rng = new Random(seed);
        ativarQualidade(g2);
        for (int i = 0; i < 180; i++) {
            int x = rng.nextInt(Math.max(1, largura));
            int y = rng.nextInt(Math.max(1, altura));
            int r = rng.nextInt(3);
            int alpha = 60 + rng.nextInt(180);
            g2.setColor(new Color(180, 200, 255, alpha));
            g2.fillOval(x, y, r + 1, r + 1);
        }
    }

    public static void desenharFundoGradiente(Graphics2D g2, int largura, int altura, long seed) {
        ativarQualidade(g2);
        g2.setColor(COR_FUNDO);
        g2.fillRect(0, 0, largura, altura);

        GradientPaint base = new GradientPaint(0, 0, new Color(0, 0, 0), largura, altura, new Color(8, 3, 6));
        g2.setPaint(base);
        g2.fillRect(0, 0, largura, altura);

        float raio = Math.max(largura, altura) * 0.72f;
        desenharGradienteCanto(g2, largura, altura, 0, 0, raio, COR_DESTAQUE, 0.45f);
        desenharGradienteCanto(g2, largura, altura, largura, 0, raio * 0.72f, COR_DESTAQUE_2, 0.32f);
        desenharGradienteCanto(g2, largura, altura, largura, altura, raio * 0.82f, COR_ACENTO, 0.38f);
        desenharGradienteCanto(g2, largura, altura, 0, altura, raio * 0.56f, new Color(255, 130, 38), 0.26f);

        g2.setColor(new Color(0, 0, 0, 102));
        g2.fillRect(0, 0, largura, altura);

        g2.setColor(new Color(255, 255, 255, 8));
        for (int i = 0; i < 5; i++) {
            int offset = i * 32;
            g2.drawRoundRect(40 + offset, 40 + offset, largura - (80 + offset * 2), altura - (80 + offset * 2), 42, 42);
        }

        Random rng = new Random(seed);
        for (int i = 0; i < 1500; i++) {
            int x = rng.nextInt(Math.max(1, largura));
            int y = rng.nextInt(Math.max(1, altura));
            int alpha = 6 + rng.nextInt(12);
            g2.setColor(new Color(255, 255, 255, alpha));
            g2.fillRect(x, y, 1, 1);
        }
    }

    public static Font fontePoppins(float size) {
        return BASE_POPPINS.deriveFont(size);
    }

    public static Font fonteInter(float size) {
        return BASE_POPPINS.deriveFont(size);
    }

    private static Font carregarFonte(String resourcePath, String fallbackName) {
        try (InputStream stream = EstiloBase.class.getResourceAsStream(resourcePath)) {
            if (stream != null) {
                Font fonte = Font.createFont(Font.TRUETYPE_FONT, stream);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fonte);
                return fonte;
            }
        } catch (FontFormatException | IOException ignored) {
        }
        return new Font(fallbackName, Font.PLAIN, 12);
    }

    private static Border criarBordaCampo(Color cor) {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(cor, 1, true),
                BorderFactory.createEmptyBorder(12, 16, 12, 16)
        );
    }

    private static void desenharBlob(Graphics2D g2, double cx, double cy, float raio, Color cor) {
        Color centro = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 190);
        Color meio = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 80);
        Color fim = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 0);
        RadialGradientPaint paint = new RadialGradientPaint(
                new Point((int) cx, (int) cy), raio,
                new float[]{0f, 0.45f, 1f},
                new Color[]{centro, meio, fim}
        );
        Composite antigo = g2.getComposite();
        g2.setComposite(AlphaComposite.SrcOver.derive(0.82f));
        g2.setPaint(paint);
        g2.fill(new Ellipse2D.Double(cx - raio, cy - raio, raio * 2, raio * 2));
        g2.setComposite(antigo);
    }

    private static void desenharGradienteCanto(Graphics2D g2, int largura, int altura, double cx, double cy, float raio, Color cor, float intensidade) {
        Color centro = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), Math.round(255 * intensidade));
        Color meio = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), Math.round(115 * intensidade));
        Color fim = new Color(cor.getRed(), cor.getGreen(), cor.getBlue(), 0);
        RadialGradientPaint paint = new RadialGradientPaint(
                new Point((int) cx, (int) cy), raio,
                new float[]{0f, 0.48f, 1f},
                new Color[]{centro, meio, fim}
        );
        g2.setPaint(paint);
        g2.fillRect(0, 0, largura, altura);
    }

    private static void ativarQualidade(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
    }
}
