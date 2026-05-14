package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Painel administrativo com resumo das avaliacoes mantidas em memoria.
 */
public class fmrAdministracao extends JDialog {

    private final Controle controle;

    public fmrAdministracao(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    public fmrAdministracao(Dialog pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = EstiloBase.criarPainelFundo(880L);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

        int margem = Math.max(48, tela.width / 30);
        int topo = EstiloBase.escalar(48, tela);
        int larguraConteudo = tela.width - margem * 2;

        JLabel lblTag = EstiloBase.criarTag("Administração");
        lblTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblTag.setBounds(margem, topo, EstiloBase.escalar(168, tela), EstiloBase.escalar(34, tela));
        fundo.add(lblTag);

        JLabel lblTitulo = EstiloBase.criarLabel("Dashboard de avaliacoes", EstiloBase.fonteResponsiva(44f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(margem, EstiloBase.escalar(104, tela), larguraConteudo - EstiloBase.escalar(260, tela),
                EstiloBase.escalar(64, tela));
        fundo.add(lblTitulo);

        JButton btnFechar = EstiloBase.criarBotaoSecundario("Fechar");
        btnFechar.setFont(EstiloBase.fonteResponsiva(17f, tela));
        btnFechar.setBounds(tela.width - margem - EstiloBase.escalar(168, tela), topo,
                EstiloBase.escalar(168, tela), EstiloBase.escalar(48, tela));
        btnFechar.addActionListener(e -> dispose());
        fundo.add(btnFechar);

        int total = controle.getTotalAvaliacoes();
        double media = controle.getMediaAvaliacoes();
        int positivas = controle.getTotalAvaliacoesPositivas();
        int percentualPositivas = total == 0 ? 0 : Math.round((positivas * 100f) / total);
        double mediaQuiz = controle.getMediaPontuacaoHistorica();

        int cardsY = EstiloBase.escalar(190, tela);
        int gap = EstiloBase.escalar(18, tela);
        int cardW = (larguraConteudo - gap * 3) / 4;
        int cardH = EstiloBase.escalar(146, tela);

        adicionarCardIndicador(fundo, margem, cardsY, cardW, cardH, "Avaliacoes", String.valueOf(total), "total da sessao", tela);
        adicionarCardIndicador(fundo, margem + (cardW + gap), cardsY, cardW, cardH, "Media geral",
                formatarDecimal(media) + " / 5", "estrelas", tela);
        adicionarCardIndicador(fundo, margem + (cardW + gap) * 2, cardsY, cardW, cardH, "Notas 4 e 5",
                percentualPositivas + "%", positivas + " respostas", tela);
        adicionarCardIndicador(fundo, margem + (cardW + gap) * 3, cardsY, cardW, cardH, "Media quiz",
                formatarDecimal(mediaQuiz) + " / " + controle.getTotalPerguntas(), "acertos", tela);

        int painelY = cardsY + cardH + EstiloBase.escalar(28, tela);
        int painelH = tela.height - painelY - EstiloBase.escalar(74, tela);
        int distribuicaoW = (int) (larguraConteudo * 0.58);
        int historicoW = larguraConteudo - distribuicaoW - gap;

        JPanel cardDistribuicao = EstiloBase.criarCard();
        cardDistribuicao.setLayout(null);
        cardDistribuicao.setBounds(margem, painelY, distribuicaoW, painelH);
        fundo.add(cardDistribuicao);
        preencherDistribuicao(cardDistribuicao, tela);

        JPanel cardHistorico = EstiloBase.criarCard();
        cardHistorico.setLayout(null);
        cardHistorico.setBounds(margem + distribuicaoW + gap, painelY, historicoW, painelH);
        fundo.add(cardHistorico);
        preencherHistorico(cardHistorico, tela);

        setContentPane(fundo);
    }

    private void adicionarCardIndicador(JPanel pai, int x, int y, int w, int h, String titulo, String valor, String legenda, Dimension tela) {
        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        card.setBounds(x, y, w, h);
        pai.add(card);

        JLabel lblTitulo = EstiloBase.criarLabel(titulo, EstiloBase.fonteResponsiva(14f, tela), EstiloBase.COR_TEXTO_FRACO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(EstiloBase.escalar(22, tela), EstiloBase.escalar(20, tela), w - EstiloBase.escalar(44, tela),
                EstiloBase.escalar(24, tela));
        card.add(lblTitulo);

        JLabel lblValor = EstiloBase.criarLabel(valor, EstiloBase.fonteResponsiva(34f, tela), EstiloBase.COR_TEXTO_PRIMARIO);
        lblValor.setHorizontalAlignment(SwingConstants.LEFT);
        lblValor.setBounds(EstiloBase.escalar(22, tela), EstiloBase.escalar(50, tela), w - EstiloBase.escalar(44, tela),
                EstiloBase.escalar(48, tela));
        card.add(lblValor);

        JLabel lblLegenda = EstiloBase.criarLabel(legenda, EstiloBase.fonteResponsiva(14f, tela), EstiloBase.COR_TEXTO_SECUNDARIO);
        lblLegenda.setHorizontalAlignment(SwingConstants.LEFT);
        lblLegenda.setBounds(EstiloBase.escalar(22, tela), h - EstiloBase.escalar(42, tela), w - EstiloBase.escalar(44, tela),
                EstiloBase.escalar(22, tela));
        card.add(lblLegenda);
    }

    private void preencherDistribuicao(JPanel card, Dimension tela) {
        int pad = EstiloBase.escalar(30, tela);
        JLabel lblTag = EstiloBase.criarTag("Distribuicao");
        lblTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblTag.setBounds(pad, EstiloBase.escalar(28, tela), EstiloBase.escalar(138, tela), EstiloBase.escalar(32, tela));
        card.add(lblTag);

        JLabel lblTitulo = EstiloBase.criarLabel("Notas de 0 a 5", EstiloBase.fonteResponsiva(30f, tela), EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(pad, EstiloBase.escalar(78, tela), card.getWidth() - pad * 2, EstiloBase.escalar(42, tela));
        card.add(lblTitulo);

        int max = 1;
        for (int nota = 0; nota <= 5; nota++) {
            max = Math.max(max, controle.getQuantidadeAvaliacoesPorNota(nota));
        }

        int linhaY = EstiloBase.escalar(145, tela);
        int linhaH = EstiloBase.escalar(46, tela);
        int gap = EstiloBase.escalar(12, tela);
        for (int nota = 0; nota <= 5; nota++) {
            int quantidade = controle.getQuantidadeAvaliacoesPorNota(nota);
            adicionarLinhaNota(card, nota, quantidade, max, pad, linhaY + nota * (linhaH + gap), linhaH, tela);
        }
    }

    private void adicionarLinhaNota(JPanel card, int nota, int quantidade, int max, int x, int y, int h, Dimension tela) {
        int labelW = EstiloBase.escalar(78, tela);
        int countW = EstiloBase.escalar(64, tela);
        int barX = x + labelW;
        int barW = card.getWidth() - x * 2 - labelW - countW - EstiloBase.escalar(14, tela);

        JLabel lblNota = EstiloBase.criarLabel(nota + " estrela" + (nota == 1 ? "" : "s"),
                EstiloBase.fonteResponsiva(14f, tela), EstiloBase.COR_TEXTO_SECUNDARIO);
        lblNota.setHorizontalAlignment(SwingConstants.LEFT);
        lblNota.setBounds(x, y, labelW, h);
        card.add(lblNota);

        JPanel barra = new BarraNota(quantidade, max);
        barra.setBounds(barX, y + EstiloBase.escalar(9, tela), barW, h - EstiloBase.escalar(18, tela));
        card.add(barra);

        JLabel lblQuantidade = EstiloBase.criarLabel(String.valueOf(quantidade), EstiloBase.fonteResponsiva(16f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO);
        lblQuantidade.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuantidade.setBounds(barX + barW + EstiloBase.escalar(14, tela), y, countW, h);
        card.add(lblQuantidade);
    }

    private void preencherHistorico(JPanel card, Dimension tela) {
        int pad = EstiloBase.escalar(30, tela);
        JLabel lblTag = EstiloBase.criarTag("Sessao atual");
        lblTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        int tagW = Math.max(EstiloBase.escalar(134, tela), lblTag.getPreferredSize().width);
        lblTag.setBounds(pad, EstiloBase.escalar(28, tela), tagW, EstiloBase.escalar(32, tela));
        card.add(lblTag);

        JLabel lblTitulo = EstiloBase.criarLabel("Ultimas visitas", EstiloBase.fonteResponsiva(30f, tela), EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(pad, EstiloBase.escalar(78, tela), card.getWidth() - pad * 2, EstiloBase.escalar(42, tela));
        card.add(lblTitulo);

        List<Integer> satisfacoes = controle.getHistoricoSatisfacoes();
        List<Integer> pontuacoes = controle.getHistoricoPontuacoes();

        if (satisfacoes.isEmpty()) {
            JTextArea lblVazio = EstiloBase.criarTextoQuebravel(
                    "Nenhuma avaliacao registrada nesta execucao.",
                    EstiloBase.fonteResponsiva(18f, tela),
                    EstiloBase.COR_TEXTO_SECUNDARIO
            );
            lblVazio.setBounds(pad, EstiloBase.escalar(150, tela), card.getWidth() - pad * 2, EstiloBase.escalar(70, tela));
            card.add(lblVazio);
            return;
        }

        int linhaY = EstiloBase.escalar(144, tela);
        int linhaH = EstiloBase.escalar(56, tela);
        int inicio = Math.max(0, satisfacoes.size() - 6);
        int posicao = 0;
        for (int i = satisfacoes.size() - 1; i >= inicio; i--) {
            int nota = satisfacoes.get(i);
            int pontos = i < pontuacoes.size() ? pontuacoes.get(i) : 0;
            adicionarLinhaHistorico(card, satisfacoes.size() - i, nota, pontos, pad,
                    linhaY + posicao * (linhaH + EstiloBase.escalar(10, tela)), linhaH, tela);
            posicao++;
        }
    }

    private void adicionarLinhaHistorico(JPanel card, int ordem, int nota, int pontos, int x, int y, int h, Dimension tela) {
        JPanel linha = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(255, 255, 255, 22));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
                g2.dispose();
            }
        };
        linha.setOpaque(false);
        linha.setBounds(x, y, card.getWidth() - x * 2, h);
        card.add(linha);

        JLabel lblVisita = EstiloBase.criarLabel("Visita " + ordem, EstiloBase.fonteResponsiva(14f, tela),
                EstiloBase.COR_TEXTO_SECUNDARIO);
        lblVisita.setHorizontalAlignment(SwingConstants.LEFT);
        lblVisita.setBounds(EstiloBase.escalar(18, tela), 0, EstiloBase.escalar(110, tela), h);
        linha.add(lblVisita);

        JLabel lblNota = EstiloBase.criarLabel(nota + " / 5", EstiloBase.fonteResponsiva(20f, tela), EstiloBase.COR_DESTAQUE);
        lblNota.setHorizontalAlignment(SwingConstants.CENTER);
        lblNota.setBounds(linha.getWidth() - EstiloBase.escalar(176, tela), 0, EstiloBase.escalar(76, tela), h);
        linha.add(lblNota);

        JLabel lblQuiz = EstiloBase.criarLabel(pontos + " acertos", EstiloBase.fonteResponsiva(14f, tela),
                EstiloBase.COR_TEXTO_FRACO);
        lblQuiz.setHorizontalAlignment(SwingConstants.RIGHT);
        lblQuiz.setBounds(linha.getWidth() - EstiloBase.escalar(110, tela), 0,
                EstiloBase.escalar(88, tela), h);
        linha.add(lblQuiz);
    }

    private String formatarDecimal(double valor) {
        return String.format("%.1f", valor);
    }

    private static class BarraNota extends JPanel {
        private final int valor;
        private final int maximo;

        BarraNota(int valor, int maximo) {
            this.valor = valor;
            this.maximo = Math.max(1, maximo);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 14));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

            int largura = Math.round((valor / (float) maximo) * getWidth());
            if (largura > 0) {
                GradientPaint gradiente = new GradientPaint(0, 0, EstiloBase.COR_DESTAQUE,
                        getWidth(), 0, EstiloBase.COR_DESTAQUE_2);
                g2.setPaint(gradiente);
                g2.fillRoundRect(0, 0, largura, getHeight(), 18, 18);
            }

            g2.setColor(new Color(255, 255, 255, 24));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
            g2.dispose();
        }
    }
}
