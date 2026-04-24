package apresentacao;

import javax.swing.*;
import java.awt.*;

/**
 * Classe utilitária com estilos, cores, fontes e componentes visuais compartilhados.
 * Garante identidade visual consistente em todas as telas do sistema.
 * Todos os membros são estáticos — não deve ser instanciada.
 */
public final class EstiloBase {

    private EstiloBase() { /* utilitária — não instanciar */ }

    // =========================================================================
    // PALETA DE CORES
    // =========================================================================

    public static final Color COR_FUNDO            = new Color(5,   8,  20);
    public static final Color COR_FUNDO_PAINEL     = new Color(10,  16,  38);
    public static final Color COR_CARD             = new Color(18,  28,  60);
    public static final Color COR_CARD_BORDA       = new Color(40,  70, 140);
    public static final Color COR_DESTAQUE         = new Color(255, 120,  30);
    public static final Color COR_DESTAQUE_HOVER   = new Color(255, 160,  70);
    public static final Color COR_ACENTO           = new Color( 60, 180, 220);
    public static final Color COR_ACENTO2          = new Color(180,  60, 255);
    public static final Color COR_TEXTO_PRIMARIO   = new Color(220, 230, 255);
    public static final Color COR_TEXTO_SECUNDARIO = new Color(140, 160, 200);
    public static final Color COR_TEXTO_FRACO      = new Color( 80, 100, 150);
    public static final Color COR_SUCESSO          = new Color( 60, 200, 100);
    public static final Color COR_ERRO             = new Color(220,  60,  80);

    // =========================================================================
    // FONTES
    // =========================================================================

    public static final Font FONTE_TITULO    = new Font("Segoe UI", Font.BOLD,  42);
    public static final Font FONTE_SUBTITULO = new Font("Segoe UI", Font.PLAIN, 22);
    public static final Font FONTE_SECAO     = new Font("Segoe UI", Font.BOLD,  26);
    public static final Font FONTE_CORPO     = new Font("Segoe UI", Font.PLAIN, 18);
    public static final Font FONTE_LABEL     = new Font("Segoe UI", Font.BOLD,  16);
    public static final Font FONTE_BOTAO     = new Font("Segoe UI", Font.BOLD,  20);
    public static final Font FONTE_PEQUENA   = new Font("Segoe UI", Font.PLAIN, 14);

    // =========================================================================
    // CONFIGURAÇÃO DE DIALOG FULLSCREEN
    // =========================================================================

    /**
     * Configura um JDialog para modo fullscreen adaptado a touchscreen.
     * Remove decorações nativas e ocupa toda a tela.
     *
     * @param dialog JDialog a ser configurado
     */
    public static void configurarDialogFullscreen(JDialog dialog) {
        dialog.setUndecorated(true);
        dialog.setBackground(COR_FUNDO);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        dialog.setSize(tela);
        dialog.setLocation(0, 0);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    // =========================================================================
    // FÁBRICA DE COMPONENTES
    // =========================================================================

    /**
     * Cria um JLabel centralizado com fonte e cor definidas.
     *
     * @param texto  Texto do label
     * @param fonte  Fonte a aplicar
     * @param cor    Cor do texto
     * @return JLabel configurado
     */
    public static JLabel criarLabel(String texto, Font fonte, Color cor) {
        JLabel label = new JLabel(texto, SwingConstants.CENTER);
        label.setFont(fonte);
        label.setForeground(cor);
        return label;
    }

    /**
     * Cria um botão primário touch-friendly com gradiente laranja.
     * Tamanho padrão: 280 x 68 px.
     *
     * @param texto Texto exibido no botão
     * @return JButton estilizado
     */
    public static JButton criarBotaoPrimario(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Color base = getModel().isPressed()  ? COR_DESTAQUE.darker() :
                        getModel().isRollover() ? COR_DESTAQUE_HOVER   : COR_DESTAQUE;

                GradientPaint gp = new GradientPaint(
                        0, 0, base, 0, getHeight(), base.darker()
                );
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                // Brilho superior
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillRoundRect(2, 2, getWidth() - 4, getHeight() / 2, 14, 14);

                // Texto
                g2.setColor(Color.WHITE);
                g2.setFont(FONTE_BOTAO);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(280, 68));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Cria um botão secundário (outline) touch-friendly.
     * Tamanho padrão: 240 x 60 px.
     *
     * @param texto Texto exibido no botão
     * @return JButton estilizado
     */
    public static JButton criarBotaoSecundario(String texto) {
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = getModel().isRollover() || getModel().isPressed();
                g2.setColor(hover ? new Color(30, 50, 100) : new Color(15, 25, 60));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);

                g2.setColor(hover ? COR_ACENTO : COR_CARD_BORDA);
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 16, 16);

                g2.setColor(hover ? COR_TEXTO_PRIMARIO : COR_TEXTO_SECUNDARIO);
                g2.setFont(FONTE_BOTAO.deriveFont(18f));
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth()  - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setPreferredSize(new Dimension(240, 60));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Cria um JTextField touch-friendly com estilo espacial escuro.
     *
     * @param placeholder Texto de dica (não usado nativamente — apenas tamanho)
     * @param colunas     Número de colunas do campo
     * @return JTextField estilizado
     */
    public static JTextField criarCampoTexto(String placeholder, int colunas) {
        JTextField campo = new JTextField(colunas) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(8, 14, 35));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                super.paintComponent(g);
                g2.dispose();
            }
        };
        campo.setFont(FONTE_CORPO);
        campo.setForeground(COR_TEXTO_PRIMARIO);
        campo.setBackground(new Color(8, 14, 35));
        campo.setCaretColor(COR_DESTAQUE);
        campo.setOpaque(false);
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_CARD_BORDA, 2, true),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        campo.setPreferredSize(new Dimension(380, 54));

        // Borda muda ao receber foco
        campo.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_ACENTO, 2, true),
                        BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                campo.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(COR_CARD_BORDA, 2, true),
                        BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
            }
        });
        return campo;
    }

    /**
     * Cria um painel card com fundo escuro e borda arredondada.
     * Use setLayout() após criar para definir o gerenciador de layout.
     *
     * @return JPanel estilizado como card
     */
    public static JPanel criarCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(COR_CARD);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(COR_CARD_BORDA);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        return card;
    }

    /**
     * Desenha um campo estelar decorativo no painel.
     * Deve ser chamado dentro de paintComponent() de um JPanel.
     *
     * @param g2     Contexto gráfico 2D já criado
     * @param largura Largura do painel
     * @param altura  Altura do painel
     * @param seed    Semente para posicionamento determinístico das estrelas
     */
    public static void desenharEstrelas(Graphics2D g2, int largura, int altura, long seed) {
        java.util.Random rng = new java.util.Random(seed);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (int i = 0; i < 180; i++) {
            int x     = rng.nextInt(Math.max(1, largura));
            int y     = rng.nextInt(Math.max(1, altura));
            int r     = rng.nextInt(3);
            int alpha = 60 + rng.nextInt(180);
            g2.setColor(new Color(180, 200, 255, alpha));
            g2.fillOval(x, y, r + 1, r + 1);
        }
    }
}