package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;

/**
 * Tela do questionario com leitura ampla e feedback contextual.
 */
public class fmrQuestionario extends JDialog {

    private final Controle controle;
    private int perguntaAtual = 0;

    private JPanel barraProgresso;
    private JLabel lblNumero;
    private JTextArea txtPergunta;
    private JPanel painelOpcoes;
    private JButton[] botoesOpcao;
    private int opcaoSelecionada = -1;
    private JButton btnConfirmar;
    private JLabel lblFeedback;
    private int larguraTextoOpcao;

    public fmrQuestionario(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
        carregarPergunta(0);
    }

    private void construirInterface() {
        JPanel fundo = EstiloBase.criarPainelFundo(55L);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;

        JLabel lblTag = EstiloBase.criarTag("Questionario final");
        lblTag.setBounds(cx - 88, 40, 176, 34);
        fundo.add(lblTag);

        lblNumero = EstiloBase.criarLabel(
                "PERGUNTA 1 DE 5",
                EstiloBase.FONTE_LABEL.deriveFont(14f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNumero.setBounds(0, 92, tela.width, 24);
        fundo.add(lblNumero);

        barraProgresso = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int total = controle.getTotalPerguntas();
                int gap = 10;
                int segW = (getWidth() - ((total - 1) * gap)) / total;
                for (int i = 0; i < total; i++) {
                    int x = i * (segW + gap);
                    g2.setColor(new Color(255, 255, 255, 18));
                    g2.fillRoundRect(x, 3, segW, 10, 10, 10);
                    if (i <= perguntaAtual) {
                        GradientPaint gp = new GradientPaint(x, 0, EstiloBase.COR_DESTAQUE,
                                x + segW, 12, EstiloBase.COR_DESTAQUE_2);
                        g2.setPaint(gp);
                        g2.fillRoundRect(x, 3, segW, 10, 10, 10);
                    }
                }
                g2.dispose();
            }
        };
        barraProgresso.setOpaque(false);
        barraProgresso.setBounds(cx - 260, 124, 520, 16);
        fundo.add(barraProgresso);

        JPanel cardPergunta = EstiloBase.criarCard();
        cardPergunta.setLayout(new BorderLayout());
        int cw = Math.min(1120, tela.width - 240);
        larguraTextoOpcao = Math.max(300, ((cw - 18) / 2) - 72);
        cardPergunta.setBounds(cx - cw / 2, 176, cw, 148);

        txtPergunta = new JTextArea();
        txtPergunta.setEditable(false);
        txtPergunta.setFocusable(false);
        txtPergunta.setOpaque(false);
        txtPergunta.setLineWrap(true);
        txtPergunta.setWrapStyleWord(true);
        txtPergunta.setFont(EstiloBase.fontePoppins(28f));
        txtPergunta.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        txtPergunta.setBorder(BorderFactory.createEmptyBorder(22, 32, 20, 32));
        txtPergunta.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPergunta.add(txtPergunta, BorderLayout.CENTER);
        fundo.add(cardPergunta);

        painelOpcoes = new JPanel(new GridLayout(2, 2, 18, 18));
        painelOpcoes.setOpaque(false);
        painelOpcoes.setBounds(cx - cw / 2, 352, cw, 306);
        fundo.add(painelOpcoes);

        JPanel faixaFeedback = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                g2.setColor(new Color(255, 255, 255, 16));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                g2.dispose();
            }
        };
        faixaFeedback.setOpaque(false);
        faixaFeedback.setBounds(cx - 300, 694, 600, 88);
        fundo.add(faixaFeedback);

        lblFeedback = EstiloBase.criarLabel(
                "Escolha uma alternativa para continuar",
                EstiloBase.FONTE_CORPO,
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblFeedback.setBounds(20, 16, 560, 24);
        faixaFeedback.add(lblFeedback);

        btnConfirmar = EstiloBase.criarBotaoPrimario("Confirmar resposta");
        btnConfirmar.setBounds(170, 38, 260, 40);
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(e -> confirmarResposta());
        faixaFeedback.add(btnConfirmar);

        setContentPane(fundo);
    }

    private void carregarPergunta(int idx) {
        perguntaAtual = idx;
        opcaoSelecionada = -1;
        btnConfirmar.setEnabled(false);
        lblFeedback.setText("Escolha uma alternativa para continuar");
        lblFeedback.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        barraProgresso.repaint();

        lblNumero.setText("PERGUNTA " + (idx + 1) + " DE " + controle.getTotalPerguntas());
        txtPergunta.setText(controle.getPergunta(idx));
        txtPergunta.setCaretPosition(0);

        painelOpcoes.removeAll();
        String[] opcoes = controle.getOpcoesPergunta(idx);
        botoesOpcao = new JButton[opcoes.length];

        char letra = 'A';
        for (int i = 0; i < opcoes.length; i++) {
            int indiceOpcao = i;
            JButton botao = criarBotaoOpcao(letra + ". " + opcoes[i], idx);
            botao.addActionListener(e -> selecionarOpcao(indiceOpcao));
            botoesOpcao[i] = botao;
            painelOpcoes.add(botao);
            letra++;
        }

        painelOpcoes.revalidate();
        painelOpcoes.repaint();
    }

    private JButton criarBotaoOpcao(String texto, int indicePergunta) {
        JButton btn = new JButton("<html><div style='width:" + larguraTextoOpcao + "px'>" + texto + "</div></html>") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                boolean selecionado = Boolean.TRUE.equals(getClientProperty("selecionado"));
                Color fundo = selecionado ? new Color(255, 115, 54, 52)
                        : getModel().isRollover() ? new Color(255, 255, 255, 16)
                        : new Color(255, 255, 255, 10);
                g2.setColor(fundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                GradientPaint borda = new GradientPaint(
                        0, 0, selecionado ? EstiloBase.COR_DESTAQUE : EstiloBase.COR_CARD_BORDA,
                        getWidth(), getHeight(), selecionado ? EstiloBase.COR_DESTAQUE_2 : EstiloBase.COR_CARD_GLOW
                );
                g2.setPaint(borda);
                g2.setStroke(new BasicStroke(selecionado ? 2f : 1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                g2.dispose();

                setForeground(selecionado ? EstiloBase.COR_TEXTO_PRIMARIO : EstiloBase.COR_TEXTO_SECUNDARIO);
                super.paintComponent(g);
            }
        };
        btn.setFont(EstiloBase.fonteInter(indicePergunta == 4 ? 16f : 17f));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void selecionarOpcao(int idx) {
        opcaoSelecionada = idx;
        for (int i = 0; i < botoesOpcao.length; i++) {
            botoesOpcao[i].putClientProperty("selecionado", i == idx);
            botoesOpcao[i].repaint();
        }
        lblFeedback.setText("Opcao selecionada. Toque em confirmar para registrar.");
        lblFeedback.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        btnConfirmar.setEnabled(true);
    }

    private void confirmarResposta() {
        if (opcaoSelecionada < 0) {
            return;
        }

        controle.registrarResposta(perguntaAtual, opcaoSelecionada);
        boolean correto = controle.getGabaritos()[perguntaAtual] == opcaoSelecionada;
        lblFeedback.setText(correto ? "Resposta correta. Excelente." : "Resposta registrada. Vamos para a proxima.");
        lblFeedback.setForeground(correto ? EstiloBase.COR_SUCESSO : EstiloBase.COR_ERRO);

        Timer temporizador = new Timer(1200, e -> {
            int proxima = perguntaAtual + 1;
            if (proxima < controle.getTotalPerguntas()) {
                carregarPergunta(proxima);
            } else {
                exibirResultado();
            }
        });
        temporizador.setRepeats(false);
        temporizador.start();
    }

    private void exibirResultado() {
        int pontos = controle.calcularPontuacao();
        int total = controle.getTotalPerguntas();
        String mensagem = "Voce acertou " + pontos + " de " + total + " perguntas.\n\n"
                + (pontos == total
                ? "Leitura impecavel da exposicao. O visitante terminou o percurso com dominio total do tema."
                : pontos >= 3
                ? "Otimo desempenho. O percurso conseguiu transmitir os principais marcos da exploracao marciana."
                : "A jornada terminou com descobertas importantes. Vale revisitar as obras e continuar explorando.");
        EstiloBase.mostrarDialogoInformativo(this, "Resultado", "Questionario concluido", mensagem, "Continuar");
        dispose();
        controle.aposQuestionario();
    }
}
