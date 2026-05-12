package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Arrays;

/**
 * Tela inicial do totem com visual de abertura da exposicao.
 */
public class fmrInicio extends JDialog {

    private final Controle controle;
    private float alphaAnima = 0f;
    private Timer timerEntrada;

    public fmrInicio(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
        iniciarAnimacaoEntrada();
    }

    private void construirInterface() {
        JPanel painel = EstiloBase.criarPainelFundo(42L);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        float escala = EstiloBase.escalaTela(tela);

        int margem = Math.max(54, tela.width / 28);
        int colunaEsquerda = (int) (tela.width * 0.52);
        int cardDireitaX = colunaEsquerda + 18;
        int cardDireitaW = tela.width - cardDireitaX - margem;

        JLabel lblTag = EstiloBase.criarTag("Museu multimidia");
        lblTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblTag.setBounds(margem, EstiloBase.escalar(54, tela), EstiloBase.escalar(186, tela), EstiloBase.escalar(34, tela));
        painel.add(lblTag);

        JLabel lblTitulo = new JLabel("Robos Exploradores");
        lblTitulo.setFont(EstiloBase.fontePoppins((tela.width >= 1700 ? 78f : 66f) * escala));
        lblTitulo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setBounds(margem, EstiloBase.escalar(126, tela), colunaEsquerda - margem - 12, EstiloBase.escalar(104, tela));
        painel.add(lblTitulo);

        JLabel lblSub = new JLabel("<html><div style='width:" + (colunaEsquerda - margem - 20) + "px'>"
                + "Uma experiencia imersiva sobre as missoes que transformaram nossa visao de Marte. "
                + "Toque para iniciar a jornada, conhecer as obras e interagir com o acervo digital.</div></html>");
        lblSub.setFont(EstiloBase.fonteResponsiva(24f, tela));
        lblSub.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblSub.setBounds(margem, EstiloBase.escalar(270, tela), colunaEsquerda - margem - 20, EstiloBase.escalar(156, tela));
        painel.add(lblSub);

        JButton btnIniciar = EstiloBase.criarBotaoPrimario("Iniciar experiencia");
        btnIniciar.setFont(EstiloBase.fonteResponsiva(19f, tela));
        btnIniciar.setBounds(margem, EstiloBase.escalar(500, tela), EstiloBase.escalar(290, tela), EstiloBase.escalar(64, tela));
        btnIniciar.addActionListener(e -> {
            dispose();
            controle.exibirCadastro();
        });
        painel.add(btnIniciar);

        JButton btnAdmin = criarBotaoEngrenagem(tela);
        btnAdmin.setToolTipText("Administração");
        btnAdmin.setBounds(margem + EstiloBase.escalar(314, tela), EstiloBase.escalar(504, tela),
                EstiloBase.escalar(64, tela), EstiloBase.escalar(56, tela));
        btnAdmin.addActionListener(e -> abrirAcessoAdministracao());
        painel.add(btnAdmin);

        JLabel lblLinha = EstiloBase.criarLabel(
                "10 obras  •  linha do tempo completa  •  1 questionario final",
                EstiloBase.fonteResponsiva(15f, tela),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblLinha.setHorizontalAlignment(SwingConstants.LEFT);
        lblLinha.setBounds(margem, EstiloBase.escalar(590, tela), colunaEsquerda - margem, EstiloBase.escalar(24, tela));
        painel.add(lblLinha);

        JPanel cardResumo = EstiloBase.criarCard();
        cardResumo.setLayout(null);
        int cardResumoY = EstiloBase.escalar(86, tela);
        int cardResumoH = tela.height - (cardResumoY * 2);
        cardResumo.setBounds(cardDireitaX, cardResumoY, cardDireitaW, cardResumoH);
        painel.add(cardResumo);

        JPanel poster = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(12, 12, 18));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);

                GradientPaint gp = new GradientPaint(0, 0, new Color(18, 11, 13), getWidth(), getHeight(), new Color(12, 12, 18));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 32, 32);

                RadialGradientPaint planeta = new RadialGradientPaint(
                        new Point((int) (getWidth() * 0.65), (int) (getHeight() * 0.34)),
                        Math.min(getWidth(), getHeight()) * 0.28f,
                        new float[]{0f, 0.55f, 1f},
                        new Color[]{
                                new Color(255, 115, 54, 240),
                                new Color(255, 191, 58, 190),
                                new Color(255, 191, 58, 0)
                        }
                );
                g2.setPaint(planeta);
                g2.fill(new Ellipse2D.Float((float) (getWidth() * 0.39), (float) (getHeight() * 0.08), getWidth() * 0.52f, getWidth() * 0.52f));

                g2.setColor(new Color(255, 255, 255, 16));
                for (int x = 24; x < getWidth(); x += 24) {
                    g2.drawLine(x, 0, x, getHeight());
                }
                for (int y = 24; y < getHeight(); y += 24) {
                    g2.drawLine(0, y, getWidth(), y);
                }

                g2.setColor(new Color(255, 255, 255, 24));
                g2.drawOval((int) (getWidth() * 0.16), (int) (getHeight() * 0.16), (int) (getWidth() * 0.72), (int) (getHeight() * 0.28));
                g2.drawOval((int) (getWidth() * 0.26), (int) (getHeight() * 0.42), (int) (getWidth() * 0.48), (int) (getHeight() * 0.18));
                g2.dispose();
            }
        };
        poster.setOpaque(false);
        int posterX = EstiloBase.escalar(24, tela);
        int posterH = Math.max(EstiloBase.escalar(250, tela), cardResumoH - (posterX * 2));
        poster.setBounds(posterX, posterX, cardDireitaW - (posterX * 2), posterH);
        cardResumo.add(poster);

        JLabel lblResumoTag = EstiloBase.criarTag("Exposicao em destaque");
        lblResumoTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblResumoTag.setBounds(EstiloBase.escalar(34, tela), EstiloBase.escalar(34, tela), EstiloBase.escalar(170, tela), EstiloBase.escalar(32, tela));
        poster.add(lblResumoTag);

        JLabel lblResumoTitulo = new JLabel("<html><div style='width:" + (poster.getWidth() - 82) + "px'>Marte como palco da curiosidade humana</div></html>");
        lblResumoTitulo.setFont(EstiloBase.fonteResponsiva(24f, tela));
        lblResumoTitulo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblResumoTitulo.setBounds(EstiloBase.escalar(34, tela), poster.getHeight() - EstiloBase.escalar(184, tela),
                poster.getWidth() - EstiloBase.escalar(68, tela), EstiloBase.escalar(70, tela));
        poster.add(lblResumoTitulo);

        JLabel lblResumoTexto = new JLabel("<html><div style='width:" + (poster.getWidth() - 70) + "px'>"
                + "Descubra as sondas, rovers e experimentos que redefiniram a exploracao planetaria.</div></html>");
        lblResumoTexto.setFont(EstiloBase.fonteResponsiva(16f, tela));
        lblResumoTexto.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblResumoTexto.setBounds(EstiloBase.escalar(34, tela), poster.getHeight() - EstiloBase.escalar(104, tela),
                poster.getWidth() - EstiloBase.escalar(70, tela), EstiloBase.escalar(72, tela));
        poster.add(lblResumoTexto);

        setContentPane(painel);
    }

    private void abrirAcessoAdministracao() {
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int dialogW = Math.min(EstiloBase.escalar(560, tela), tela.width - EstiloBase.escalar(56, tela));
        int dialogH = Math.min(EstiloBase.escalar(360, tela), tela.height - EstiloBase.escalar(56, tela));

        JDialog dialogo = new JDialog(this, "Administração", true);
        dialogo.setUndecorated(true);
        dialogo.setBackground(new Color(0, 0, 0, 0));
        dialogo.setSize(dialogW, dialogH);
        dialogo.setLocationRelativeTo(this);

        JPanel fundo = new JPanel(null);
        fundo.setOpaque(false);

        int margem = EstiloBase.escalar(22, tela);
        JPanel card = EstiloBase.criarCardDestaque();
        card.setLayout(null);
        card.setBounds(margem, margem, dialogW - margem * 2, dialogH - margem * 2);
        fundo.add(card);

        JLabel lblTag = EstiloBase.criarTag("Acesso restrito");
        lblTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblTag.setBounds(EstiloBase.escalar(28, tela), EstiloBase.escalar(26, tela),
                EstiloBase.escalar(154, tela), EstiloBase.escalar(32, tela));
        card.add(lblTag);

        JLabel lblTitulo = EstiloBase.criarLabel("Administração", EstiloBase.fonteResponsiva(30f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(EstiloBase.escalar(28, tela), EstiloBase.escalar(78, tela),
                card.getWidth() - EstiloBase.escalar(56, tela), EstiloBase.escalar(42, tela));
        card.add(lblTitulo);

        JLabel lblSenha = EstiloBase.criarLabel("Senha", EstiloBase.fonteResponsiva(16f, tela),
                EstiloBase.COR_TEXTO_SECUNDARIO);
        lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
        lblSenha.setBounds(EstiloBase.escalar(28, tela), EstiloBase.escalar(134, tela),
                card.getWidth() - EstiloBase.escalar(56, tela), EstiloBase.escalar(24, tela));
        card.add(lblSenha);

        JPasswordField campoSenha = criarCampoSenha(tela);
        campoSenha.setBounds(EstiloBase.escalar(28, tela), EstiloBase.escalar(164, tela),
                card.getWidth() - EstiloBase.escalar(56, tela), EstiloBase.escalar(56, tela));
        card.add(campoSenha);

        JLabel lblErro = EstiloBase.criarLabel(" ", EstiloBase.fonteResponsiva(14f, tela), EstiloBase.COR_ERRO);
        lblErro.setHorizontalAlignment(SwingConstants.LEFT);
        lblErro.setBounds(EstiloBase.escalar(28, tela), EstiloBase.escalar(224, tela),
                card.getWidth() - EstiloBase.escalar(56, tela), EstiloBase.escalar(24, tela));
        card.add(lblErro);

        JButton btnCancelar = EstiloBase.criarBotaoSecundario("Voltar");
        btnCancelar.setFont(EstiloBase.fonteResponsiva(16f, tela));
        btnCancelar.setBounds(EstiloBase.escalar(28, tela), card.getHeight() - EstiloBase.escalar(72, tela),
                EstiloBase.escalar(154, tela), EstiloBase.escalar(48, tela));
        btnCancelar.addActionListener(e -> dialogo.dispose());
        card.add(btnCancelar);

        JButton btnEntrar = EstiloBase.criarBotaoPrimario("Entrar");
        btnEntrar.setFont(EstiloBase.fonteResponsiva(18f, tela));
        btnEntrar.setBounds(card.getWidth() - EstiloBase.escalar(214, tela), card.getHeight() - EstiloBase.escalar(76, tela),
                EstiloBase.escalar(176, tela), EstiloBase.escalar(54, tela));
        card.add(btnEntrar);

        btnEntrar.addActionListener(e -> {
            char[] senha = campoSenha.getPassword();
            if (controle.autenticarAdministracao(senha)) {
                Arrays.fill(senha, '\0');
                campoSenha.setText("");
                dialogo.dispose();
                new fmrAdministracao(this, controle).setVisible(true);
            } else {
                Arrays.fill(senha, '\0');
                campoSenha.setText("");
                lblErro.setText("Senha incorreta.");
                campoSenha.requestFocusInWindow();
            }
        });
        campoSenha.addActionListener(e -> btnEntrar.doClick());

        dialogo.setContentPane(fundo);
        dialogo.getRootPane().setOpaque(false);
        SwingUtilities.invokeLater(campoSenha::requestFocusInWindow);
        dialogo.setVisible(true);
    }

    private JPasswordField criarCampoSenha(Dimension tela) {
        JPasswordField campo = new JPasswordField();
        campo.setFont(EstiloBase.fonteResponsiva(20f, tela));
        campo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        campo.setBackground(new Color(10, 10, 16));
        campo.setCaretColor(EstiloBase.COR_DESTAQUE);
        campo.setEchoChar('*');
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(EstiloBase.COR_CARD_BORDA, 1, true),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
        return campo;
    }

    private JButton criarBotaoEngrenagem(Dimension tela) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean hover = getModel().isRollover() || getModel().isPressed();
                Color fundo = hover ? new Color(255, 255, 255, 20) : new Color(255, 255, 255, 10);
                g2.setColor(fundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

                GradientPaint borda = new GradientPaint(
                        0, 0, hover ? EstiloBase.COR_DESTAQUE : EstiloBase.COR_CARD_BORDA,
                        getWidth(), getHeight(), hover ? EstiloBase.COR_ACENTO : EstiloBase.COR_CARD_GLOW
                );
                g2.setPaint(borda);
                g2.setStroke(new BasicStroke(1.8f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 28, 28);

                desenharEngrenagem(g2, getWidth() / 2, getHeight() / 2, EstiloBase.escalar(12, tela), hover);
                g2.dispose();
            }
        };
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void desenharEngrenagem(Graphics2D g2, int cx, int cy, int raio, boolean destaque) {
        g2.setColor(destaque ? EstiloBase.COR_TEXTO_PRIMARIO : EstiloBase.COR_TEXTO_SECUNDARIO);
        g2.setStroke(new BasicStroke(Math.max(2f, raio * 0.16f), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        for (int i = 0; i < 8; i++) {
            double angulo = Math.PI * 2 * i / 8.0;
            int x1 = cx + (int) Math.round(Math.cos(angulo) * (raio + 1));
            int y1 = cy + (int) Math.round(Math.sin(angulo) * (raio + 1));
            int x2 = cx + (int) Math.round(Math.cos(angulo) * (raio + 6));
            int y2 = cy + (int) Math.round(Math.sin(angulo) * (raio + 6));
            g2.drawLine(x1, y1, x2, y2);
        }

        g2.setStroke(new BasicStroke(Math.max(3f, raio * 0.22f)));
        g2.drawOval(cx - raio, cy - raio, raio * 2, raio * 2);
        g2.fillOval(cx - Math.max(3, raio / 3), cy - Math.max(3, raio / 3),
                Math.max(6, raio * 2 / 3), Math.max(6, raio * 2 / 3));
    }

    private void iniciarAnimacaoEntrada() {
        setOpacity(0f);
        timerEntrada = new Timer(20, null);
        timerEntrada.addActionListener(e -> {
            alphaAnima = Math.min(1f, alphaAnima + 0.04f);
            setOpacity(alphaAnima);
            if (alphaAnima >= 1f) {
                timerEntrada.stop();
            }
        });
        timerEntrada.start();
    }

    @Override
    public void dispose() {
        if (timerEntrada != null) {
            timerEntrada.stop();
        }
        super.dispose();
    }
}
