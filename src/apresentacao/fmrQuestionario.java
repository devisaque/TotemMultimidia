package apresentacao;

import modelo.Controle;
import javax.swing.*;
import java.awt.*;

/**
 * Tela do questionário — 5 perguntas de múltipla escolha.
 * Exibe uma pergunta por vez com botões de opção touch-friendly.
 * Calcula e exibe o resultado ao final.
 */
public class fmrQuestionario extends JDialog {

    private final Controle controle;
    private int perguntaAtual = 0;

    // Componentes dinâmicos
    private JLabel lblNumero;
    private JLabel lblPergunta;
    private JPanel painelOpcoes;
    private JButton[] botoesOpcao;
    private int opcaoSelecionada = -1;
    private JButton btnConfirmar;
    private JLabel lblFeedback;

    public fmrQuestionario(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
        carregarPergunta(0);
    }

    private void construirInterface() {
        JPanel fundo = criarFundo();
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;

        // Cabeçalho
        JLabel lblTag = new JLabel("📋 QUESTIONÁRIO", SwingConstants.CENTER);
        lblTag.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTag.setForeground(EstiloBase.COR_ACENTO);
        lblTag.setBounds(0, 30, tela.width, 28);
        fundo.add(lblTag);

        lblNumero = new JLabel("PERGUNTA 1 DE 5", SwingConstants.CENTER);
        lblNumero.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblNumero.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblNumero.setBounds(0, 64, tela.width, 32);
        fundo.add(lblNumero);

        // Card da pergunta
        JPanel cardPergunta = EstiloBase.criarCard();
        cardPergunta.setLayout(new BorderLayout());
        int cw = 900;
        cardPergunta.setBounds(cx - cw / 2, 106, cw, 100);

        lblPergunta = new JLabel("", SwingConstants.CENTER);
        lblPergunta.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblPergunta.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblPergunta.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        cardPergunta.add(lblPergunta, BorderLayout.CENTER);
        fundo.add(cardPergunta);

        // Painel de opções
        painelOpcoes = new JPanel(new GridLayout(2, 2, 16, 16));
        painelOpcoes.setOpaque(false);
        painelOpcoes.setBounds(cx - cw / 2, 226, cw, 280);
        fundo.add(painelOpcoes);

        // Feedback
        lblFeedback = new JLabel("", SwingConstants.CENTER);
        lblFeedback.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblFeedback.setBounds(0, 520, tela.width, 40);
        fundo.add(lblFeedback);

        // Botão Confirmar
        btnConfirmar = EstiloBase.criarBotaoPrimario("CONFIRMAR →");
        btnConfirmar.setBounds(cx - 180, 580, 360, 70);
        btnConfirmar.setEnabled(false);
        btnConfirmar.addActionListener(e -> confirmarResposta());
        fundo.add(btnConfirmar);

        setContentPane(fundo);
    }

    private void carregarPergunta(int idx) {
        perguntaAtual  = idx;
        opcaoSelecionada = -1;
        btnConfirmar.setEnabled(false);
        lblFeedback.setText("");

        lblNumero.setText("PERGUNTA " + (idx + 1) + " DE " + controle.getTotalPerguntas());
        lblPergunta.setText("<html><div style='text-align:center;'>" + controle.getPergunta(idx) + "</div></html>");

        painelOpcoes.removeAll();
        botoesOpcao = new JButton[controle.getOpcoesPergunta(idx).length];
        String[] opcoes = controle.getOpcoesPergunta(idx);
        char letra = 'A';
        for (int i = 0; i < opcoes.length; i++) {
            final int opcIdx = i;
            String label = letra + ")  " + opcoes[i];
            JButton btn = criarBotaoOpcao(label);
            btn.addActionListener(e -> selecionarOpcao(opcIdx));
            botoesOpcao[i] = btn;
            painelOpcoes.add(btn);
            letra++;
        }
        painelOpcoes.revalidate();
        painelOpcoes.repaint();
    }

    private JButton criarBotaoOpcao(String texto) {
        JButton btn = new JButton(texto) {
            boolean selecionado = false;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = selecionado ? new Color(255, 120, 30, 60) :
                        getModel().isRollover() ? new Color(30, 50, 100) :
                        EstiloBase.COR_CARD;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);
                Color borda = selecionado ? EstiloBase.COR_DESTAQUE : EstiloBase.COR_CARD_BORDA;
                g2.setColor(borda);
                g2.setStroke(new BasicStroke(selecionado ? 2.5f : 1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 14, 14);
                g2.setColor(selecionado ? EstiloBase.COR_DESTAQUE : EstiloBase.COR_TEXTO_PRIMARIO);
                g2.setFont(EstiloBase.FONTE_CORPO);
                FontMetrics fm = g2.getFontMetrics();
                int tx = 24;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }

            public void setSelecionado(boolean s) { this.selecionado = s; repaint(); }
        };
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @SuppressWarnings("unchecked")
    private void selecionarOpcao(int idx) {
        opcaoSelecionada = idx;
        // Desmarca todos
        for (JButton b : botoesOpcao) {
            try {
                b.getClass().getMethod("setSelecionado", boolean.class).invoke(b, false);
            } catch (Exception ignored) {}
        }
        // Marca o selecionado
        try {
            botoesOpcao[idx].getClass().getMethod("setSelecionado", boolean.class)
                    .invoke(botoesOpcao[idx], true);
        } catch (Exception ignored) {}
        btnConfirmar.setEnabled(true);
    }

    private void confirmarResposta() {
        if (opcaoSelecionada < 0) return;
        controle.registrarResposta(perguntaAtual, opcaoSelecionada);

        boolean correto = controle.getGabaritos()[perguntaAtual] == opcaoSelecionada;
        lblFeedback.setText(correto ? "✅  Resposta correta!" : "❌  Resposta incorreta. Não desanime!");
        lblFeedback.setForeground(correto ? EstiloBase.COR_SUCESSO : EstiloBase.COR_ERRO);

        // Aguarda 1s e avança
        Timer t = new Timer(1200, e -> {
            int proxima = perguntaAtual + 1;
            if (proxima < controle.getTotalPerguntas()) {
                carregarPergunta(proxima);
            } else {
                exibirResultado();
            }
        });
        t.setRepeats(false);
        t.start();
    }

    private void exibirResultado() {
        int pontos = controle.calcularPontuacao();
        int total  = controle.getTotalPerguntas();
        String msg = "🏆 Você acertou " + pontos + " de " + total + " perguntas!\n\n";
        msg += pontos == total ? "Incrível! Você é um verdadeiro especialista em Marte!" :
                pontos >= 3    ? "Muito bem! Você aprendeu bastante sobre a exploração marciana." :
                "Continue explorando — Marte tem muito mais a revelar!";
        JOptionPane.showMessageDialog(this, msg, "Resultado do Questionário", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        controle.aposQuestionario();
    }

    private JPanel criarFundo() {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(5, 8, 20),
                        getWidth(), getHeight(), new Color(10, 25, 55)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                EstiloBase.desenharEstrelas(g2, getWidth(), getHeight(), 55L);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        return p;
    }
}