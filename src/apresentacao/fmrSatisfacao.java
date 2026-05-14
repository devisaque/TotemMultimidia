package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;

/**
 * Tela final de satisfacao em sintonia com o novo visual.
 */
public class fmrSatisfacao extends JDialog {

    private final Controle controle;
    private int notaSelecionada = -1;
    private JLabel[] estrelas;
    private JLabel lblNota;
    private JButton btnEnviar;

    private static final String[] MENSAGENS = {
            "",
            "Que pena. Seu retorno ajuda a melhorar o percurso.",
            "Obrigado pelo retorno. Vamos evoluir a experiencia.",
            "Boa avaliacao. Estamos no caminho certo.",
            "Otimo. Ficamos felizes com a visita.",
            "Excelente. A experiencia marcou voce."
    };

    public fmrSatisfacao(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = EstiloBase.criarPainelFundo(33L);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;
        int margem = EstiloBase.escalar(40, tela);

        JLabel lblTag = EstiloBase.criarTag("Encerramento da visita");
        lblTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        int tagY = EstiloBase.escalar(52, tela);
        int tagH = EstiloBase.escalar(34, tela);
        lblTag.setBounds(cx - EstiloBase.escalar(108, tela), tagY,
                EstiloBase.escalar(216, tela), EstiloBase.escalar(34, tela));
        fundo.add(lblTag);

        int tituloW = Math.min(EstiloBase.escalar(900, tela), tela.width - EstiloBase.escalar(80, tela));
        int tituloY = tagY + tagH + EstiloBase.escalar(28, tela);
        int tituloH = Math.max(130, EstiloBase.escalar(150, tela));
        JTextArea lblTitulo = EstiloBase.criarTextoQuebravel(
                "Como foi a sua experiencia na exposição?",
                EstiloBase.fonteResponsiva(54f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitulo.setBounds(cx - tituloW / 2, tituloY, tituloW, tituloH);
        fundo.add(lblTitulo);

        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        int cardW = Math.min(EstiloBase.escalar(840, tela), tela.width - EstiloBase.escalar(80, tela));
        int cardH = Math.min(EstiloBase.escalar(320, tela), tela.height - EstiloBase.escalar(360, tela));
        cardH = Math.max(EstiloBase.escalar(250, tela), cardH);
        int cardY = tituloY + tituloH + EstiloBase.escalar(34, tela);
        card.setBounds(cx - cardW / 2, cardY, cardW, cardH);
        fundo.add(card);

        JLabel lblCardTag = EstiloBase.criarTag("Avalie de 1 a 5");
        lblCardTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblCardTag.setBounds(EstiloBase.escalar(30, tela), EstiloBase.escalar(28, tela),
                EstiloBase.escalar(132, tela), EstiloBase.escalar(32, tela));
        card.add(lblCardTag);

        JPanel painelEstrelas = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        painelEstrelas.setOpaque(false);
        painelEstrelas.setBounds(EstiloBase.escalar(44, tela), EstiloBase.escalar(92, tela),
                cardW - EstiloBase.escalar(88, tela), EstiloBase.escalar(96, tela));
        card.add(painelEstrelas);

        estrelas = new JLabel[5];
        for (int i = 0; i < 5; i++) {
            final int nota = i+1;
            JLabel estrela = new EstrelaAvaliacao();
            estrela.setFont(EstiloBase.fonteResponsiva(58f, tela));
            estrela.setForeground(EstiloBase.COR_TEXTO_FRACO);
            estrela.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            estrela.setPreferredSize(new Dimension(EstiloBase.escalar(92, tela), EstiloBase.escalar(92, tela)));
            estrela.setToolTipText(nota + " estrela(s)");

            estrela.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    selecionarNota(nota);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    destacarAte(nota);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    atualizarEstrelas(notaSelecionada);
                }
            });

            estrelas[i] = estrela;
            painelEstrelas.add(estrela);
        }

        lblNota = EstiloBase.criarLabel(
                "Toque em um valor para avaliar a experiencia",
                EstiloBase.fonteResponsiva(19f, tela),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNota.setBounds(EstiloBase.escalar(30, tela), EstiloBase.escalar(200, tela),
                cardW - EstiloBase.escalar(60, tela), EstiloBase.escalar(26, tela));
        card.add(lblNota);

        int pontos = controle.calcularPontuacao();
        JLabel lblQuiz = EstiloBase.criarLabel(
                "Desempenho no questionario: " + pontos + " de " + controle.getTotalPerguntas() + " acertos",
                EstiloBase.fonteResponsiva(15f, tela),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblQuiz.setBounds(EstiloBase.escalar(30, tela), EstiloBase.escalar(234, tela),
                cardW - EstiloBase.escalar(60, tela), EstiloBase.escalar(20, tela));
        card.add(lblQuiz);

        btnEnviar = EstiloBase.criarBotaoPrimario("Encerrar visita");
        btnEnviar.setEnabled(false);
        btnEnviar.setFont(EstiloBase.fonteResponsiva(18f, tela));
        btnEnviar.setBounds((cardW - EstiloBase.escalar(232, tela)) / 2, cardH - EstiloBase.escalar(58, tela),
                EstiloBase.escalar(232, tela), EstiloBase.escalar(42, tela));
        btnEnviar.addActionListener(e -> enviarAvaliacao());
        card.add(btnEnviar);

        JLabel lblNome = EstiloBase.criarLabel(
                "Obrigado, " + controle.getNomeVisitante() + ". Sua opiniao fecha a jornada e ajuda a melhorar o acervo digital.",
                EstiloBase.fonteResponsiva(20f, tela),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        int agradecimentoW = Math.min(EstiloBase.escalar(1120, tela), tela.width - (margem * 2));
        int agradecimentoY = cardY + cardH + EstiloBase.escalar(22, tela);
        lblNome.setHorizontalAlignment(SwingConstants.CENTER);
        lblNome.setBounds(cx - agradecimentoW / 2, agradecimentoY,
                agradecimentoW, EstiloBase.escalar(32, tela));
        fundo.add(lblNome);

        setContentPane(fundo);
    }

    private void selecionarNota(int nota) {
        notaSelecionada = nota;
        atualizarEstrelas(nota);
        lblNota.setText(MENSAGENS[nota]);
        lblNota.setForeground(nota >= 4 ? EstiloBase.COR_SUCESSO
                : nota >= 2 ? EstiloBase.COR_TEXTO_SECUNDARIO : EstiloBase.COR_ERRO);
        btnEnviar.setEnabled(true);
    }

    private void destacarAte(int nota) {
        for (int i = 0; i < 5; i++) {
            estrelas[i].setForeground(i < nota
                    ? EstiloBase.COR_DESTAQUE
                    : EstiloBase.COR_TEXTO_FRACO);
            estrelas[i].repaint();
        }
    }

    private void atualizarEstrelas(int nota) {
        for (int i = 0; i < 5; i++) {
            boolean ativo = i < nota;
            estrelas[i].setForeground(
                    ativo
                    ?EstiloBase.COR_DESTAQUE
                    :EstiloBase.COR_TEXTO_FRACO
            );

            estrelas[i].repaint();
        }
    }

    private void enviarAvaliacao() {
        dispose();
        controle.finalizarVisita(notaSelecionada);
    }

    private static class EstrelaAvaliacao extends JLabel {

        EstrelaAvaliacao() {
            super("", SwingConstants.CENTER);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

            int tamanho = Math.max(24, Math.min(getWidth(), getHeight()) - 16);
            double raioExterno = tamanho / 2.0;
            double raioInterno = raioExterno * 0.44;
            double centroX = getWidth() / 2.0;
            double centroY = getHeight() / 2.0 + 2;

            Path2D estrela = criarFormaEstrela(centroX, centroY, raioExterno, raioInterno);

            g2.setColor(getForeground());
            g2.fill(estrela);
            g2.setStroke(new BasicStroke(1.2f));
            g2.setColor(new Color(255, 255, 255, 48));
            g2.draw(estrela);
            g2.dispose();
        }

        private Path2D criarFormaEstrela(double centroX, double centroY, double raioExterno, double raioInterno) {
            Path2D caminho = new Path2D.Double();
            double anguloInicial = -Math.PI / 2;

            for (int i = 0; i < 10; i++) {
                double raio = i % 2 == 0 ? raioExterno : raioInterno;
                double angulo = anguloInicial + i * Math.PI / 5;
                double x = centroX + Math.cos(angulo) * raio;
                double y = centroY + Math.sin(angulo) * raio;

                if (i == 0) {
                    caminho.moveTo(x, y);
                } else {
                    caminho.lineTo(x, y);
                }
            }

            caminho.closePath();
            return caminho;
        }
    }
}
