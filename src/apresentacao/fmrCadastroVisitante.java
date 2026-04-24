package apresentacao;

import modelo.Controle;
import javax.swing.*;
import java.awt.*;

/**
 * Tela de cadastro do visitante.
 * Coleta nome e idade; usa teclado virtual próprio para entrada touch.
 * Valida os dados antes de prosseguir.
 */
public class fmrCadastroVisitante extends JDialog {

    private final Controle controle;
    private JTextField campoNome;
    private JTextField campoIdade;
    private JLabel lblErroNome;
    private JLabel lblErroIdade;

    public fmrCadastroVisitante(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = criarFundo();
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int cx = tela.width / 2;

        // ── Título ────────────────────────────────────────────────────────
        JLabel lblTitulo = EstiloBase.criarLabel(
                "Bem-vindo, Explorador!", EstiloBase.FONTE_TITULO, EstiloBase.COR_DESTAQUE
        );
        lblTitulo.setBounds(0, 60, tela.width, 70);
        fundo.add(lblTitulo);

        JLabel lblSub = EstiloBase.criarLabel(
                "Antes de começar, nos diga quem você é",
                EstiloBase.FONTE_SUBTITULO, EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblSub.setBounds(0, 140, tela.width, 40);
        fundo.add(lblSub);

        // ── Card de formulário ────────────────────────────────────────────
        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        int cw = 560, ch = 420;
        card.setBounds(cx - cw / 2, 200, cw, ch);
        fundo.add(card);

        // Campo Nome
        JLabel lblNome = EstiloBase.criarLabel(
                "Seu Nome", EstiloBase.FONTE_LABEL, EstiloBase.COR_ACENTO
        );
        lblNome.setHorizontalAlignment(SwingConstants.LEFT);
        lblNome.setBounds(40, 40, 480, 28);
        card.add(lblNome);

        campoNome = EstiloBase.criarCampoTexto("Digite seu nome", 20);
        campoNome.setBounds(40, 74, 480, 54);
        campoNome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirTeclado(campoNome);
            }
        });
        card.add(campoNome);

        lblErroNome = new JLabel("");
        lblErroNome.setFont(EstiloBase.FONTE_PEQUENA);
        lblErroNome.setForeground(EstiloBase.COR_ERRO);
        lblErroNome.setBounds(40, 132, 480, 24);
        card.add(lblErroNome);

        // Campo Idade
        JLabel lblIdade = EstiloBase.criarLabel(
                "Sua Idade", EstiloBase.FONTE_LABEL, EstiloBase.COR_ACENTO
        );
        lblIdade.setHorizontalAlignment(SwingConstants.LEFT);
        lblIdade.setBounds(40, 168, 480, 28);
        card.add(lblIdade);

        campoIdade = EstiloBase.criarCampoTexto("Ex: 25", 4);
        campoIdade.setBounds(40, 200, 480, 54);
        campoIdade.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirTeclado(campoIdade);
            }
        });
        card.add(campoIdade);

        lblErroIdade = new JLabel("");
        lblErroIdade.setFont(EstiloBase.FONTE_PEQUENA);
        lblErroIdade.setForeground(EstiloBase.COR_ERRO);
        lblErroIdade.setBounds(40, 258, 480, 24);
        card.add(lblErroIdade);

        // Aviso LGPD
        JLabel lblLgpd = new JLabel(
                "⚠  Não coletamos dados sensíveis. Uso exclusivo para esta visita."
        );
        lblLgpd.setFont(EstiloBase.FONTE_PEQUENA);
        lblLgpd.setForeground(EstiloBase.COR_TEXTO_FRACO);
        lblLgpd.setBounds(40, 292, 480, 24);
        card.add(lblLgpd);

        // Botão Continuar
        JButton btnContinuar = EstiloBase.criarBotaoPrimario("CONTINUAR →");
        btnContinuar.setBounds(140, 342, 280, 64);
        btnContinuar.addActionListener(e -> validarEAvancar());
        card.add(btnContinuar);

        setContentPane(fundo);
    }

    private void validarEAvancar() {
        String nome  = campoNome.getText().trim();
        String idade = campoIdade.getText().trim();
        boolean ok   = true;

        String erroN = controle.erroNome(nome);
        if (!erroN.isEmpty()) {
            lblErroNome.setText("✗  " + erroN);
            campoNome.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(EstiloBase.COR_ERRO, 2, true),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
            ));
            ok = false;
        } else {
            lblErroNome.setText("");
        }

        String erroI = controle.erroIdade(idade);
        if (!erroI.isEmpty()) {
            lblErroIdade.setText("✗  " + erroI);
            campoIdade.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(EstiloBase.COR_ERRO, 2, true),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
            ));
            ok = false;
        } else {
            lblErroIdade.setText("");
        }

        if (ok && controle.salvarDadosVisitante(nome, idade)) {
            dispose();
            controle.exibirObra(0);
        }
    }

    private void abrirTeclado(JTextField campo) {
        TecladoVirtual teclado = new TecladoVirtual(this, campo);
        teclado.setVisible(true);
    }

    private JPanel criarFundo() {
        JPanel p = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(5, 8, 20),
                        getWidth(), getHeight(), new Color(10, 20, 50)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                EstiloBase.desenharEstrelas(g2, getWidth(), getHeight(), 77L);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        return p;
    }
}