package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Mini-game com palco visual renovado.
 */
public class fmrMiniGame extends JDialog {

    private final Controle controle;
    private final int posicao;

    private static final String[] TITULOS = {
            "Desvio de meteoritos",
            "Coleta de amostras",
            "Recarga de energia"
    };

    private static final String[] DESCRICOES = {
            "Toque nos meteoritos antes que atinjam o rover.",
            "Toque nas amostras para coletar dados da superficie marciana.",
            "Toque nos paineis solares para alimentar a missao."
    };

    private static final String[] EMOJIS_ALVO = {"☄", "⬢", "✦"};

    private int pontos = 0;
    private int tentativas = 0;
    private static final int TOTAL_ALVOS = 8;
    private Timer timerAlvos;
    private JLabel[] alvos;
    private JLabel lblPontos;
    private JPanel painelJogo;
    private final Random rng = new Random();

    public fmrMiniGame(JFrame pai, Controle controle, int posicao) {
        super(pai, true);
        this.controle = controle;
        this.posicao = posicao;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = EstiloBase.criarPainelFundo(99L + posicao);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;

        JLabel lblTag = EstiloBase.criarTag("Interacao");
        lblTag.setBounds(cx - 72, 40, 144, 34);
        fundo.add(lblTag);

        JLabel lblTitulo = EstiloBase.criarLabel(
                TITULOS[posicao % 3],
                EstiloBase.fontePoppins(42f),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblTitulo.setBounds(0, 92, tela.width, 52);
        fundo.add(lblTitulo);

        JLabel lblDesc = EstiloBase.criarLabel(
                DESCRICOES[posicao % 3],
                EstiloBase.fonteInter(20f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblDesc.setBounds(0, 148, tela.width, 36);
        fundo.add(lblDesc);

        JPanel cardPlacar = EstiloBase.criarCard();
        cardPlacar.setLayout(null);
        cardPlacar.setBounds(cx - 220, 208, 440, 92);
        fundo.add(cardPlacar);

        JLabel lblMeta = EstiloBase.criarTag("Meta " + TOTAL_ALVOS + " toques");
        lblMeta.setBounds(20, 18, 136, 30);
        cardPlacar.add(lblMeta);

        lblPontos = EstiloBase.criarLabel(
                "Toques 0 / " + TOTAL_ALVOS,
                EstiloBase.fontePoppins(28f),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblPontos.setHorizontalAlignment(SwingConstants.LEFT);
        lblPontos.setBounds(20, 48, 240, 28);
        cardPlacar.add(lblPontos);

        JLabel lblStatus = EstiloBase.criarLabel(
                "Campo preparado para toque rapido",
                EstiloBase.FONTE_PEQUENA.deriveFont(14f),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblStatus.setHorizontalAlignment(SwingConstants.RIGHT);
        lblStatus.setBounds(178, 18, 240, 18);
        cardPlacar.add(lblStatus);

        painelJogo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(8, 8, 12, 220));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 36, 36);
                g2.setColor(new Color(255, 255, 255, 16));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 36, 36);
                g2.setColor(new Color(255, 255, 255, 10));
                for (int x = 28; x < getWidth(); x += 28) {
                    g2.drawLine(x, 0, x, getHeight());
                }
                for (int y = 28; y < getHeight(); y += 28) {
                    g2.drawLine(0, y, getWidth(), y);
                }
                g2.dispose();
            }
        };
        painelJogo.setOpaque(false);
        int jw = tela.width - 180;
        int jh = tela.height - 430;
        painelJogo.setBounds(90, 332, jw, jh);
        fundo.add(painelJogo);

        alvos = new JLabel[4];
        for (int i = 0; i < alvos.length; i++) {
            JLabel alvo = criarAlvo();
            alvo.setVisible(false);
            alvo.setSize(94, 94);
            final int idx = i;
            alvo.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    registrarToque(idx);
                }
            });
            alvos[i] = alvo;
            painelJogo.add(alvo);
        }

        JButton btnPular = EstiloBase.criarBotaoSecundario("Pular interacao");
        btnPular.setBounds(cx - 130, tela.height - 76, 260, 44);
        btnPular.addActionListener(e -> encerrarJogo());
        fundo.add(btnPular);

        setContentPane(fundo);
        SwingUtilities.invokeLater(this::iniciarJogo);
    }

    private JLabel criarAlvo() {
        return new JLabel(EMOJIS_ALVO[posicao % 3], SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, EstiloBase.COR_DESTAQUE, getWidth(), getHeight(), EstiloBase.COR_ACENTO);
                g2.setPaint(gp);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 255, 255, 50));
                g2.fillOval(8, 8, getWidth() - 16, (getHeight() / 2) - 4);
                g2.setColor(new Color(255, 255, 255, 85));
                g2.drawOval(1, 1, getWidth() - 3, getHeight() - 3);
                g2.dispose();
                setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
                setFont(EstiloBase.fontePoppins(32f));
                super.paintComponent(g);
            }
        };
    }

    private void iniciarJogo() {
        timerAlvos = new Timer(1150, e -> exibirProximoAlvo());
        timerAlvos.start();
        exibirProximoAlvo();
    }

    private void exibirProximoAlvo() {
        if (tentativas >= TOTAL_ALVOS * 2) {
            encerrarJogo();
            return;
        }

        for (JLabel alvo : alvos) {
            alvo.setVisible(false);
        }

        int quantidade = 1 + rng.nextInt(2);
        int limiteX = painelJogo.getWidth() - 110;
        int limiteY = painelJogo.getHeight() - 110;

        for (int i = 0; i < quantidade && i < alvos.length; i++) {
            alvos[i].setLocation(24 + rng.nextInt(Math.max(1, limiteX)), 24 + rng.nextInt(Math.max(1, limiteY)));
            alvos[i].setVisible(true);
        }

        tentativas++;
    }

    private void registrarToque(int idx) {
        if (!alvos[idx].isVisible()) {
            return;
        }

        alvos[idx].setVisible(false);
        pontos++;
        lblPontos.setText("Toques " + pontos + " / " + TOTAL_ALVOS);

        if (pontos >= TOTAL_ALVOS) {
            encerrarJogo();
        }
    }

    private void encerrarJogo() {
        if (timerAlvos != null) {
            timerAlvos.stop();
        }

        String mensagem = pontos >= TOTAL_ALVOS
                ? "Desempenho maximo. Todos os alvos foram ativados com sucesso."
                : "Interacao concluida com " + pontos + " de " + TOTAL_ALVOS + " alvos acionados.";
        EstiloBase.mostrarDialogoInformativo(this, "Mini-game", "Interacao concluida", mensagem, "Continuar");
        dispose();
        controle.aposMinigame(controle.getObraAtual());
    }
}
