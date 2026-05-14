package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Tela do questionario — fluxo em dois passos:
 * 1) Selecionar opcao (destaque laranja, botao "Verificar Resposta" habilitado)
 * 2) Verificar resposta (verde/vermelho + explicacao inline, botao vira "Proxima Pergunta")
 */
public class fmrQuestionario extends JDialog {

    // Explicacoes exibidas dentro da alternativa correta apos verificacao
    private static final String[] EXPLICACOES = {
        "A Mars 2 foi a primeira sonda sovietica a entrar na orbita de Marte, em 1971, embora seu modulo de pouso tenha falhado ao aterrissar.",
        "A Mars 3 conseguiu o primeiro pouso suave em Marte em dezembro de 1971, mas transmitiu dados por apenas 20 segundos antes de perder contato.",
        "O Sojourner foi o primeiro robo movel a operar em Marte, parte da missao Mars Pathfinder em 1997, percorrendo cerca de 100 metros ao longo de 83 dias.",
        "O Spirit (MER-A) pousou em Janeiro de 2004 e operou por mais de 6 anos, superando em muito sua vida util prevista de 90 dias.",
        "A missao Mars 2020 trouxe tanto o Perseverance quanto o helicóptero Ingenuity, que realizou o primeiro voo motorizado em outro planeta."
    };

    private final Controle controle;
    private int perguntaAtual = 0;
    private boolean verificado = false;

    private JPanel barraProgresso;
    private JLabel lblNumero;
    private JTextArea txtPergunta;
    private JPanel painelOpcoes;
    private JButton[] botoesOpcao;
    private int opcaoSelecionada = -1;
    private JButton btnAcao;
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

        // ETAPA 5 — badge "Questionario final" removido conforme especificacao

        lblNumero = EstiloBase.criarLabel(
                "PERGUNTA 1 DE 5",
                EstiloBase.FONTE_LABEL.deriveFont(14f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        // Subindo elementos: lblNumero agora em Y=50 (era 92)
        lblNumero.setBounds(0, 50, tela.width, 24);
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
        // Era Y=124, agora Y=82
        barraProgresso.setBounds(cx - 260, 82, 520, 16);
        fundo.add(barraProgresso);

        JPanel cardPergunta = EstiloBase.criarCard();
        cardPergunta.setLayout(new BorderLayout());
        int cw = Math.min(1120, tela.width - 240);
        larguraTextoOpcao = Math.max(300, ((cw - 18) / 2) - 72);
        // Era Y=176, agora Y=118
        cardPergunta.setBounds(cx - cw / 2, 118, cw, 148);

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
        // Era Y=352, agora Y=290
        painelOpcoes.setBounds(cx - cw / 2, 290, cw, 306);
        fundo.add(painelOpcoes);

        // Botao de acao: inicia como "Verificar Resposta" desabilitado + translucido
        btnAcao = criarBotaoAcao();
        int footerX = cx - cw / 2;
        // Era Y=706, agora Y=620
        btnAcao.setBounds(footerX + cw - 290, 620, 290, 64);
        btnAcao.setEnabled(false);
        btnAcao.putClientProperty("translucido", true);
        btnAcao.addActionListener(e -> acionarBotao());
        fundo.add(btnAcao);

        setContentPane(fundo);
    }

    /**
     * Cria o botao de acao com suporte a estado translucido (desabilitado sem opcao)
     * e estado solido (com opcao selecionada ou apos verificacao).
     */
    private JButton criarBotaoAcao() {
        JButton btn = new JButton("Verificar Resposta") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                boolean translucido = Boolean.TRUE.equals(getClientProperty("translucido"));
                boolean hover = getModel().isRollover() && isEnabled();
                int arco = 28;

                if (translucido) {
                    // Desabilitado: fundo muito escuro + borda apagada
                    g2.setColor(new Color(255, 115, 54, 40));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), arco, arco);
                    g2.setPaint(new GradientPaint(
                            0, 0, new Color(255, 115, 54, 60),
                            getWidth(), getHeight(), new Color(255, 200, 36, 60)
                    ));
                } else {
                    // Habilitado: gradiente solido
                    Color inicio = hover ? EstiloBase.COR_DESTAQUE_HOVER : EstiloBase.COR_DESTAQUE;
                    Color fim = hover ? new Color(255, 204, 28) : EstiloBase.COR_DESTAQUE_2;
                    g2.setPaint(new GradientPaint(0, 0, inicio, getWidth(), getHeight(), fim));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), arco, arco);
                    g2.setColor(new Color(255, 255, 255, 58));
                }
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arco, arco);

                String texto = getText();
                Font fonte = getFont();
                FontMetrics fm = g2.getFontMetrics(fonte);
                // Ajusta fonte se texto nao couber
                while (fm.stringWidth(texto) > getWidth() - 26 && fonte.getSize2D() > 12f) {
                    fonte = fonte.deriveFont(fonte.getSize2D() - 1f);
                    fm = g2.getFontMetrics(fonte);
                }
                g2.setFont(fonte);
                g2.setColor(translucido
                        ? new Color(255, 255, 255, 100)
                        : EstiloBase.COR_TEXTO_PRIMARIO);
                int tx = (getWidth() - fm.stringWidth(texto)) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(texto, tx, ty);
                g2.dispose();
            }
        };
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setOpaque(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setMargin(new Insets(0, 0, 0, 0));
        btn.setFont(EstiloBase.fontePoppins(17f));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Acao do botao principal:
     * - Se ainda nao verificado: verifica a resposta e transiciona para "Proxima Pergunta"
     * - Se ja verificado: avanca para proxima pergunta ou exibe resultado final
     */
    private void acionarBotao() {
        if (!verificado) {
            verificarResposta();
        } else {
            avancar();
        }
    }

    private void carregarPergunta(int idx) {
        limparSelecao();
        perguntaAtual = idx;
        opcaoSelecionada = -1;
        verificado = false;

        // Botao volta para "Verificar Resposta" desabilitado e translucido
        btnAcao.setText("Verificar Resposta");
        btnAcao.setEnabled(false);
        btnAcao.putClientProperty("translucido", true);
        btnAcao.repaint();

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

    private void limparSelecao() {
        if (botoesOpcao == null) return;
        for (JButton botao : botoesOpcao) {
            if (botao != null) {
                botao.putClientProperty("selecionado", false);
                botao.putClientProperty("correto", false);
                botao.putClientProperty("errado", false);
                botao.repaint();
            }
        }
    }

    private JButton criarBotaoOpcao(String texto, int indicePergunta) {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                boolean selecionado = Boolean.TRUE.equals(getClientProperty("selecionado"));
                boolean correto    = Boolean.TRUE.equals(getClientProperty("correto"));
                boolean errado     = Boolean.TRUE.equals(getClientProperty("errado"));

                Color corFundo;
                if (correto) {
                    corFundo = new Color(34, 197, 94, 60);   // verde translucido
                } else if (errado) {
                    corFundo = new Color(239, 68, 68, 60);   // vermelho translucido
                } else if (selecionado) {
                    corFundo = new Color(255, 115, 54, 52);  // laranja selecionado
                } else {
                    corFundo = getModel().isRollover()
                            ? new Color(255, 255, 255, 16)
                            : new Color(255, 255, 255, 10);
                }
                g2.setColor(corFundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

                Color corBorda1, corBorda2;
                if (correto) {
                    corBorda1 = new Color(34, 197, 94);
                    corBorda2 = new Color(74, 222, 128);
                } else if (errado) {
                    corBorda1 = new Color(239, 68, 68);
                    corBorda2 = new Color(252, 165, 165);
                } else if (selecionado) {
                    corBorda1 = EstiloBase.COR_DESTAQUE;
                    corBorda2 = EstiloBase.COR_DESTAQUE_2;
                } else {
                    corBorda1 = EstiloBase.COR_CARD_BORDA;
                    corBorda2 = EstiloBase.COR_CARD_GLOW;
                }
                g2.setPaint(new GradientPaint(0, 0, corBorda1, getWidth(), getHeight(), corBorda2));
                g2.setStroke(new BasicStroke((correto || errado || selecionado) ? 2f : 1.2f));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                g2.dispose();

                Component textoOpcao = getComponentCount() > 0 ? getComponent(0) : null;
                if (textoOpcao != null) {
                    Color corTexto = (correto || selecionado)
                            ? EstiloBase.COR_TEXTO_PRIMARIO
                            : EstiloBase.COR_TEXTO_SECUNDARIO;
                    textoOpcao.setForeground(corTexto);
                    if (textoOpcao instanceof JTextArea) {
                        ((JTextArea) textoOpcao).setDisabledTextColor(corTexto);
                    }
                }
            }
        };
        btn.setLayout(new BorderLayout());
        JTextArea areaTexto = EstiloBase.criarTextoQuebravel(
                texto,
                EstiloBase.fonteInter(indicePergunta == 4 ? 16f : 17f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        areaTexto.setBorder(BorderFactory.createEmptyBorder(18, 22, 18, 22));
        areaTexto.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        areaTexto.addMouseListener(criarEncaminhadorCliqueOpcao(btn));
        btn.add(areaTexto, BorderLayout.CENTER);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setRolloverEnabled(true);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private MouseAdapter criarEncaminhadorCliqueOpcao(JButton botao) {
        return new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e)  { botao.getModel().setRollover(true);  botao.repaint(); }
            @Override public void mouseExited(MouseEvent e)   { botao.getModel().setRollover(false); botao.getModel().setPressed(false); botao.getModel().setArmed(false); botao.repaint(); }
            @Override public void mousePressed(MouseEvent e)  { botao.getModel().setArmed(true); botao.getModel().setPressed(true); botao.repaint(); }
            @Override public void mouseReleased(MouseEvent e) {
                Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), botao);
                botao.getModel().setPressed(false); botao.getModel().setArmed(false); botao.repaint();
                if (botao.contains(p)) botao.doClick();
            }
        };
    }

    private void selecionarOpcao(int idx) {
        if (verificado) return; // impede selecao apos verificacao
        opcaoSelecionada = idx;
        for (int i = 0; i < botoesOpcao.length; i++) {
            botoesOpcao[i].putClientProperty("selecionado", i == idx);
            botoesOpcao[i].repaint();
        }
        // Habilita e deixa botao solido
        btnAcao.setEnabled(true);
        btnAcao.putClientProperty("translucido", false);
        btnAcao.repaint();
    }

    /**
     * Verifica a resposta selecionada:
     * - Pinta opcao correta em verde e erradas em vermelho
     * - Exibe explicacao textual dentro do card da alternativa correta
     * - Troca botao para "Proxima Pergunta"
     */
    private void verificarResposta() {
        if (opcaoSelecionada < 0) return;

        controle.registrarResposta(perguntaAtual, opcaoSelecionada);
        int gabarito = controle.getGabaritos()[perguntaAtual];
        verificado = true;

        for (int i = 0; i < botoesOpcao.length; i++) {
            botoesOpcao[i].putClientProperty("selecionado", false);
            if (i == gabarito) {
                botoesOpcao[i].putClientProperty("correto", true);
                botoesOpcao[i].putClientProperty("errado", false);
                // Adiciona explicacao abaixo do texto da opcao correta
                adicionarExplicacao(botoesOpcao[i], perguntaAtual);
            } else {
                botoesOpcao[i].putClientProperty("correto", false);
                botoesOpcao[i].putClientProperty("errado", true);
            }
            botoesOpcao[i].setEnabled(false);
            botoesOpcao[i].repaint();
        }

        // Troca botao para "Proxima Pergunta" (solido, habilitado)
        btnAcao.setText("Proxima Pergunta");
        btnAcao.setEnabled(true);
        btnAcao.putClientProperty("translucido", false);
        btnAcao.repaint();
    }

    /**
     * Adiciona um JLabel com a explicacao dentro do card da alternativa correta,
     * abaixo do texto principal da opcao.
     */
    private void adicionarExplicacao(JButton botaoCorreto, int indicePergunta) {
        String explicacao = indicePergunta < EXPLICACOES.length
                ? EXPLICACOES[indicePergunta]
                : "Esta e a resposta correta para esta questao.";

        JTextArea lblExplicacao = new JTextArea(explicacao);
        lblExplicacao.setEditable(false);
        lblExplicacao.setFocusable(false);
        lblExplicacao.setOpaque(false);
        lblExplicacao.setLineWrap(true);
        lblExplicacao.setWrapStyleWord(true);
        lblExplicacao.setFont(EstiloBase.fonteInter(13f).deriveFont(Font.ITALIC));
        lblExplicacao.setForeground(new Color(180, 255, 180, 220));
        lblExplicacao.setBorder(BorderFactory.createEmptyBorder(0, 22, 12, 22));
        lblExplicacao.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        botaoCorreto.setLayout(new BorderLayout());
        // Garante que o componente de texto existente continue no CENTER
        // e adiciona a explicacao no SOUTH
        botaoCorreto.add(lblExplicacao, BorderLayout.SOUTH);
        botaoCorreto.revalidate();
        botaoCorreto.repaint();
    }

    private void avancar() {
        int proxima = perguntaAtual + 1;
        if (proxima < controle.getTotalPerguntas()) {
            carregarPergunta(proxima);
        } else {
            exibirResultado();
        }
    }

    private void exibirResultado() {
        int pontos = controle.calcularPontuacao();
        int total  = controle.getTotalPerguntas();
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
