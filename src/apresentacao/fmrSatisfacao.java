package apresentacao;

import modelo.Controle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Tela de pesquisa de satisfação — avaliação de 0 a 5 estrelas.
 * Exibe agradecimento personalizado e encerra a sessão.
 */
public class fmrSatisfacao extends JDialog {

    private final Controle controle;
    private int notaSelecionada = -1;
    private JLabel[] estrelas;
    private JLabel lblNota;
    private JButton btnEnviar;

    private static final String[] MENSAGENS = {
            "😔 Que pena! Ajude-nos a melhorar.",
            "😐 Obrigado pelo seu retorno.",
            "🙂 Bom! Sempre podemos melhorar.",
            "😊 Ótimo! Ficamos felizes.",
            "😄 Muito bom! Volte sempre.",
            "🚀 Incrível! Você adorou a visita!"
    };

    public fmrSatisfacao(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = criarFundo();
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;

        // Ícone
        JLabel lblIcone = new JLabel("🌟", SwingConstants.CENTER);
        lblIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 90));
        lblIcone.setBounds(cx - 80, 60, 160, 110);
        fundo.add(lblIcone);

        // Título
        JLabel lblTitulo = EstiloBase.criarLabel(
                "Como foi sua visita?", EstiloBase.FONTE_TITULO, EstiloBase.COR_DESTAQUE
        );
        lblTitulo.setBounds(0, 180, tela.width, 60);
        fundo.add(lblTitulo);

        // Nome do visitante
        JLabel lblNome = EstiloBase.criarLabel(
                "Obrigado, " + controle.getNomeVisitante() + "! Avalie sua experiência:",
                EstiloBase.FONTE_SUBTITULO, EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNome.setBounds(0, 250, tela.width, 40);
        fundo.add(lblNome);

        // Estrelas
        JPanel painelEstrelas = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 0));
        painelEstrelas.setOpaque(false);
        painelEstrelas.setBounds(cx - 340, 310, 680, 100);
        fundo.add(painelEstrelas);

        estrelas = new JLabel[6];
        for (int i = 0; i <= 5; i++) {
            final int nota = i;
            JLabel estrela = new JLabel(i == 0 ? "✕" : "★", SwingConstants.CENTER);
            estrela.setFont(new Font("Segoe UI", Font.BOLD, i == 0 ? 48 : 64));
            estrela.setForeground(EstiloBase.COR_TEXTO_FRACO);
            estrela.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            estrela.setPreferredSize(new Dimension(90, 90));
            estrela.setToolTipText(i == 0 ? "Sem nota" : nota + " estrela(s)");

            estrela.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) { selecionarNota(nota); }
                @Override
                public void mouseEntered(MouseEvent e) { destacarAte(nota); }
                @Override
                public void mouseExited(MouseEvent e)  { atualizarEstrelas(notaSelecionada); }
            });
            estrelas[i] = estrela;
            painelEstrelas.add(estrela);
        }

        // Rótulo da nota
        lblNota = new JLabel("Toque para avaliar", SwingConstants.CENTER);
        lblNota.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        lblNota.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblNota.setBounds(0, 420, tela.width, 40);
        fundo.add(lblNota);

        // Resultado do questionário
        int pontos = controle.calcularPontuacao();
        JLabel lblQuiz = new JLabel(
                "📋 Questionário: " + pontos + " de " + controle.getTotalPerguntas() + " acertos",
                SwingConstants.CENTER
        );
        lblQuiz.setFont(EstiloBase.FONTE_CORPO);
        lblQuiz.setForeground(EstiloBase.COR_ACENTO);
        lblQuiz.setBounds(0, 470, tela.width, 36);
        fundo.add(lblQuiz);

        // Botão Enviar
        btnEnviar = EstiloBase.criarBotaoPrimario("ENVIAR AVALIAÇÃO  ✓");
        btnEnviar.setEnabled(false);
        btnEnviar.setBounds(cx - 200, 530, 400, 74);
        btnEnviar.addActionListener(e -> enviarAvaliacao());
        fundo.add(btnEnviar);

        // Rodapé
        JLabel lblRodape = new JLabel(
                "Sua opinião nos ajuda a melhorar o museu para futuros visitantes.",
                SwingConstants.CENTER
        );
        lblRodape.setFont(EstiloBase.FONTE_PEQUENA);
        lblRodape.setForeground(EstiloBase.COR_TEXTO_FRACO);
        lblRodape.setBounds(0, tela.height - 50, tela.width, 28);
        fundo.add(lblRodape);

        setContentPane(fundo);
    }

    private void selecionarNota(int nota) {
        notaSelecionada = nota;
        atualizarEstrelas(nota);
        lblNota.setText(nota == 0 ? "Sem avaliação" : MENSAGENS[nota]);
        lblNota.setForeground(nota >= 4 ? EstiloBase.COR_SUCESSO :
                nota >= 2 ? EstiloBase.COR_ACENTO  : EstiloBase.COR_ERRO);
        btnEnviar.setEnabled(true);
    }

    private void destacarAte(int nota) {
        for (int i = 0; i <= 5; i++) {
            estrelas[i].setForeground(i <= nota && nota > 0
                    ? new Color(255, 200, 50)
                    : EstiloBase.COR_TEXTO_FRACO
            );
        }
    }

    private void atualizarEstrelas(int nota) {
        for (int i = 0; i <= 5; i++) {
            Color c = (nota >= 0 && i <= nota && nota > 0)
                    ? new Color(255, 180, 30) : EstiloBase.COR_TEXTO_FRACO;
            estrelas[i].setForeground(c);
        }
    }

    private void enviarAvaliacao() {
        dispose();
        controle.finalizarVisita(notaSelecionada);
    }

    private JPanel criarFundo() {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(5, 8, 20),
                        getWidth(), getHeight(), new Color(20, 5, 40)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                EstiloBase.desenharEstrelas(g2, getWidth(), getHeight(), 33L);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        return p;
    }
}