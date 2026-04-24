package apresentacao;

import modelo.Controle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Mini-game interativo simples.
 * O visitante deve tocar nos meteoritos que aparecem na tela antes que
 * o tempo acabe. Exibido entre as obras conforme o fluxo.
 */
public class fmrMiniGame extends JDialog {

    private final Controle controle;
    private final int posicao; // qual dos 3 mini-games (0,1,2)

    private static final String[] TITULOS = {
            "DESVIO DE METEORITOS!",
            "COLETA DE AMOSTRAS!",
            "RECARGA DE ENERGIA!"
    };
    private static final String[] DESCRICOES = {
            "Toque nos meteoritos antes que atinjam o rover!",
            "Toque nas amostras de rocha marciana para coletá-las!",
            "Toque nos painéis solares para recarregar o rover!"
    };
    private static final String[] EMOJIS_ALVO = {"☄️", "🪨", "⚡"};

    // Estado do jogo
    private int pontos = 0;
    private int tentativas = 0;
    private static final int TOTAL_ALVOS = 8;
    private Timer timerAlvos;
    private JLabel[] alvos;
    private JLabel lblPontos;
    private JPanel painelJogo;
    private Random rng = new Random();

    public fmrMiniGame(JFrame pai, Controle controle, int posicao) {
        super(pai, true);
        this.controle = controle;
        this.posicao  = posicao;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = criarFundo();
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;

        // Cabeçalho
        JLabel lblTag = new JLabel("⚡ MINI-GAME", SwingConstants.CENTER);
        lblTag.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTag.setForeground(EstiloBase.COR_ACENTO);
        lblTag.setBounds(0, 30, tela.width, 28);
        fundo.add(lblTag);

        JLabel lblTitulo = EstiloBase.criarLabel(
                TITULOS[posicao % 3], EstiloBase.FONTE_SECAO, EstiloBase.COR_DESTAQUE
        );
        lblTitulo.setBounds(0, 64, tela.width, 50);
        fundo.add(lblTitulo);

        JLabel lblDesc = EstiloBase.criarLabel(
                DESCRICOES[posicao % 3], EstiloBase.FONTE_CORPO, EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblDesc.setBounds(0, 116, tela.width, 36);
        fundo.add(lblDesc);

        // Placar
        lblPontos = new JLabel("Toques: 0 / " + TOTAL_ALVOS, SwingConstants.CENTER);
        lblPontos.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblPontos.setForeground(EstiloBase.COR_ACENTO);
        lblPontos.setBounds(cx - 200, 160, 400, 40);
        fundo.add(lblPontos);

        // Painel do jogo
        painelJogo = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(8, 14, 35));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(30, 50, 100));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 20, 20);
                g2.dispose();
            }
        };
        painelJogo.setOpaque(false);
        int jw = tela.width - 200, jh = tela.height - 350;
        painelJogo.setBounds(100, 210, jw, jh);
        fundo.add(painelJogo);

        // Criar alvos (ocultos inicialmente)
        alvos = new JLabel[4];
        for (int i = 0; i < alvos.length; i++) {
            JLabel alvo = new JLabel(EMOJIS_ALVO[posicao % 3], SwingConstants.CENTER);
            alvo.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 56));
            alvo.setVisible(false);
            alvo.setSize(80, 80);
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

        // Botão pular
        JButton btnPular = EstiloBase.criarBotaoSecundario("Pular Mini-Game →");
        btnPular.setBounds(cx - 160, tela.height - 100, 320, 60);
        btnPular.addActionListener(e -> encerrarJogo());
        fundo.add(btnPular);

        setContentPane(fundo);

        // Inicia o jogo após exibição
        SwingUtilities.invokeLater(this::iniciarJogo);
    }

    private void iniciarJogo() {
        timerAlvos = new Timer(1200, e -> exibirProximoAlvo());
        timerAlvos.start();
    }

    private void exibirProximoAlvo() {
        if (tentativas >= TOTAL_ALVOS * 2) {
            encerrarJogo();
            return;
        }
        // Oculta todos
        for (JLabel a : alvos) a.setVisible(false);

        // Mostra 1-2 alvos em posições aleatórias
        int qtd = 1 + rng.nextInt(2);
        int jw = painelJogo.getWidth() - 80;
        int jh = painelJogo.getHeight() - 80;
        for (int i = 0; i < qtd && i < alvos.length; i++) {
            alvos[i].setLocation(20 + rng.nextInt(Math.max(1, jw)), 10 + rng.nextInt(Math.max(1, jh)));
            alvos[i].setVisible(true);
        }
        tentativas++;
    }

    private void registrarToque(int idx) {
        if (!alvos[idx].isVisible()) return;
        alvos[idx].setVisible(false);
        pontos++;
        lblPontos.setText("Toques: " + pontos + " / " + TOTAL_ALVOS);
        if (pontos >= TOTAL_ALVOS) encerrarJogo();
    }

    private void encerrarJogo() {
        if (timerAlvos != null) timerAlvos.stop();

        // Exibe resultado
        String msg = pontos >= TOTAL_ALVOS
                ? "🏆 Excelente! Você acertou todos os alvos!"
                : "✅ Mini-game concluído! Você acertou " + pontos + " de " + TOTAL_ALVOS + ".";

        JOptionPane.showMessageDialog(
                this, msg, "Resultado do Mini-Game",
                JOptionPane.INFORMATION_MESSAGE
        );
        dispose();
        controle.aposMinigame(controle.getObraAtual());
    }

    private JPanel criarFundo() {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(5, 8, 20),
                        getWidth(), getHeight(), new Color(20, 10, 50)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                EstiloBase.desenharEstrelas(g2, getWidth(), getHeight(), 99L + posicao);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        return p;
    }
}