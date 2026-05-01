package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            "Sem avaliacao registrada.",
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

        JLabel lblTag = EstiloBase.criarTag("Encerramento da visita");
        lblTag.setBounds(cx - 108, 52, 216, 34);
        fundo.add(lblTag);

        JLabel lblTitulo = new JLabel("<html><div style='text-align:center;width:900px'>Como foi a sua experiencia no totem?</div></html>");
        lblTitulo.setFont(EstiloBase.fontePoppins(54f));
        lblTitulo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setBounds(cx - 450, 110, 900, 120);
        fundo.add(lblTitulo);

        JLabel lblNome = EstiloBase.criarLabel(
                "Obrigado, " + controle.getNomeVisitante() + ". Sua opiniao fecha a jornada e ajuda a melhorar o acervo digital.",
                EstiloBase.fonteInter(22f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNome.setBounds(0, 240, tela.width, 34);
        fundo.add(lblNome);

        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        card.setBounds(cx - 420, 314, 840, 320);
        fundo.add(card);

        JLabel lblCardTag = EstiloBase.criarTag("Avalie de 0 a 5");
        lblCardTag.setBounds(30, 28, 132, 32);
        card.add(lblCardTag);

        JPanel painelEstrelas = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        painelEstrelas.setOpaque(false);
        painelEstrelas.setBounds(44, 92, 752, 96);
        card.add(painelEstrelas);

        estrelas = new JLabel[6];
        for (int i = 0; i <= 5; i++) {
            final int nota = i;
            JLabel estrela = new JLabel(i == 0 ? "0" : "★", SwingConstants.CENTER);
            estrela.setFont(i == 0 ? EstiloBase.fontePoppins(36f) : EstiloBase.fontePoppins(58f));
            estrela.setForeground(EstiloBase.COR_TEXTO_FRACO);
            estrela.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            estrela.setPreferredSize(new Dimension(92, 92));
            estrela.setToolTipText(i == 0 ? "Sem nota" : nota + " estrela(s)");

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
                EstiloBase.fonteInter(19f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNota.setBounds(30, 200, 780, 26);
        card.add(lblNota);

        int pontos = controle.calcularPontuacao();
        JLabel lblQuiz = EstiloBase.criarLabel(
                "Desempenho no questionario: " + pontos + " de " + controle.getTotalPerguntas() + " acertos",
                EstiloBase.FONTE_PEQUENA.deriveFont(15f),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblQuiz.setBounds(30, 234, 780, 20);
        card.add(lblQuiz);

        btnEnviar = EstiloBase.criarBotaoPrimario("Encerrar visita");
        btnEnviar.setEnabled(false);
        btnEnviar.setBounds(304, 262, 232, 42);
        btnEnviar.addActionListener(e -> enviarAvaliacao());
        card.add(btnEnviar);

        JLabel lblRodape = EstiloBase.criarLabel(
                "Sua resposta fica apenas nesta sessao e orienta melhorias futuras da instalacao.",
                EstiloBase.FONTE_PEQUENA.deriveFont(14f),
                EstiloBase.COR_TEXTO_FRACO
        );
        lblRodape.setBounds(0, tela.height - 52, tela.width, 20);
        fundo.add(lblRodape);

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
        for (int i = 0; i <= 5; i++) {
            estrelas[i].setForeground(i <= nota && nota > 0
                    ? EstiloBase.COR_DESTAQUE_2
                    : i == 0 && nota == 0
                    ? EstiloBase.COR_DESTAQUE
                    : EstiloBase.COR_TEXTO_FRACO);
        }
    }

    private void atualizarEstrelas(int nota) {
        for (int i = 0; i <= 5; i++) {
            boolean ativo = nota >= 0 && i <= nota;
            if (i == 0 && nota == 0) {
                estrelas[i].setForeground(EstiloBase.COR_DESTAQUE);
            } else {
                estrelas[i].setForeground(ativo && nota > 0 ? EstiloBase.COR_DESTAQUE_2 : EstiloBase.COR_TEXTO_FRACO);
            }
        }
    }

    private void enviarAvaliacao() {
        dispose();
        controle.finalizarVisita(notaSelecionada);
    }
}
