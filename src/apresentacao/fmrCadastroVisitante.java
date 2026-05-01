package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de cadastro do visitante em card central, inspirada em um layout flex.
 */
public class fmrCadastroVisitante extends JDialog {

    private final Controle controle;
    private JTextField campoNome;
    private JTextField campoSobrenome;
    private JTextField campoIdade;
    private JLabel lblErroNome;
    private JLabel lblErroSobrenome;
    private JLabel lblErroIdade;

    public fmrCadastroVisitante(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = EstiloBase.criarPainelFundo(77L);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

        int cardW = Math.min(820, tela.width - 160);
        int cardH = Math.min(700, tela.height - 120);
        int cardX = (tela.width - cardW) / 2;
        int cardY = (tela.height - cardH) / 2;

        JLabel lblTagPagina = EstiloBase.criarTag("Primeiro passo");
        lblTagPagina.setBounds(cardX, Math.max(28, cardY - 48), 154, 34);
        fundo.add(lblTagPagina);

        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        card.setBounds(cardX, cardY, cardW, cardH);
        fundo.add(card);

        JLabel lblCardTag = EstiloBase.criarTag("Cadastro do visitante");
        lblCardTag.setBounds(38, 32, 206, 34);
        card.add(lblCardTag);

        JLabel lblTitulo = EstiloBase.criarLabel(
                "Identificacao da visita",
                EstiloBase.fontePoppins(36f),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(38, 84, cardW - 76, 44);
        card.add(lblTitulo);

        JLabel lblSub = new JLabel("<html><div style='width:" + (cardW - 86) + "px'>"
                + "Informe os dados basicos para iniciar a experiencia. Nenhuma chave, email ou dado sensivel e solicitado.</div></html>");
        lblSub.setFont(EstiloBase.FONTE_PEQUENA.deriveFont(15f));
        lblSub.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblSub.setBounds(38, 136, cardW - 76, 48);
        card.add(lblSub);

        int gap = 18;
        int campoW = (cardW - 76 - gap) / 2;
        int yPrimeiraLinha = 218;

        JLabel lblNome = criarLabelCampo("Nome");
        lblNome.setBounds(38, yPrimeiraLinha, campoW, 24);
        card.add(lblNome);

        campoNome = EstiloBase.criarCampoTexto("Digite seu nome", 18);
        campoNome.setBounds(38, yPrimeiraLinha + 30, campoW, 58);
        campoNome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirTeclado(campoNome);
            }
        });
        card.add(campoNome);

        lblErroNome = criarLabelErro();
        lblErroNome.setBounds(38, yPrimeiraLinha + 92, campoW, 22);
        card.add(lblErroNome);

        JLabel lblSobrenome = criarLabelCampo("Sobrenome");
        lblSobrenome.setBounds(38 + campoW + gap, yPrimeiraLinha, campoW, 24);
        card.add(lblSobrenome);

        campoSobrenome = EstiloBase.criarCampoTexto("Digite seu sobrenome", 18);
        campoSobrenome.setBounds(38 + campoW + gap, yPrimeiraLinha + 30, campoW, 58);
        campoSobrenome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirTeclado(campoSobrenome);
            }
        });
        card.add(campoSobrenome);

        lblErroSobrenome = criarLabelErro();
        lblErroSobrenome.setBounds(38 + campoW + gap, yPrimeiraLinha + 92, campoW, 22);
        card.add(lblErroSobrenome);

        int yIdade = yPrimeiraLinha + 138;
        JLabel lblIdade = criarLabelCampo("Idade");
        lblIdade.setBounds(38, yIdade, campoW, 24);
        card.add(lblIdade);

        campoIdade = EstiloBase.criarCampoTexto("Ex: 25", 4);
        campoIdade.setBounds(38, yIdade + 30, campoW, 58);
        campoIdade.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirTeclado(campoIdade);
            }
        });
        card.add(campoIdade);

        lblErroIdade = criarLabelErro();
        lblErroIdade.setBounds(38, yIdade + 92, cardW - 76, 22);
        card.add(lblErroIdade);

        JPanel aviso = criarBlocoInformacao(
                "Uso em totem",
                "Os campos sao grandes, clicaveis e preparados para o teclado virtual interno."
        );
        aviso.setBounds(38, yIdade + 136, cardW - 76, 88);
        card.add(aviso);

        int botoesY = cardH - 98;
        JButton btnContinuar = EstiloBase.criarBotaoPrimario("Continuar");
        btnContinuar.setBounds(38, botoesY, 230, 58);
        btnContinuar.addActionListener(e -> validarEAvancar());
        card.add(btnContinuar);

        JButton btnVoltar = EstiloBase.criarBotaoSecundario("Voltar");
        btnVoltar.setBounds(286, botoesY, 180, 58);
        btnVoltar.addActionListener(e -> {
            dispose();
            controle.exibirTelaInicial();
        });
        card.add(btnVoltar);

        setContentPane(fundo);
    }

    private JLabel criarLabelCampo(String texto) {
        JLabel label = EstiloBase.criarLabel(texto, EstiloBase.FONTE_LABEL.deriveFont(16f), EstiloBase.COR_ACENTO_FRIO);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    private JLabel criarLabelErro() {
        JLabel label = new JLabel("");
        label.setFont(EstiloBase.FONTE_PEQUENA.deriveFont(13f));
        label.setForeground(EstiloBase.COR_ERRO);
        return label;
    }

    private JPanel criarBlocoInformacao(String titulo, String texto) {
        JPanel bloco = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 9));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
            }
        };
        bloco.setOpaque(false);

        JLabel lblTitulo = EstiloBase.criarLabel(titulo, EstiloBase.FONTE_LABEL.deriveFont(15f), EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(20, 15, 260, 20);
        bloco.add(lblTitulo);

        JLabel lblTexto = new JLabel("<html><div style='width:620px'>" + texto + "</div></html>");
        lblTexto.setFont(EstiloBase.FONTE_PEQUENA.deriveFont(14f));
        lblTexto.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblTexto.setBounds(20, 40, 660, 34);
        bloco.add(lblTexto);

        return bloco;
    }

    private void validarEAvancar() {
        String nome = campoNome.getText().trim();
        String sobrenome = campoSobrenome.getText().trim();
        String idade = campoIdade.getText().trim();
        boolean ok = true;

        String erroN = controle.erroNome(nome);
        if (!erroN.isEmpty()) {
            lblErroNome.setText("- " + erroN);
            EstiloBase.marcarCampoComErro(campoNome);
            ok = false;
        } else {
            lblErroNome.setText("");
            EstiloBase.restaurarCampo(campoNome);
        }

        String erroS = controle.erroNome(sobrenome);
        if (!erroS.isEmpty()) {
            lblErroSobrenome.setText("- " + erroS);
            EstiloBase.marcarCampoComErro(campoSobrenome);
            ok = false;
        } else {
            lblErroSobrenome.setText("");
            EstiloBase.restaurarCampo(campoSobrenome);
        }

        String erroI = controle.erroIdade(idade);
        if (!erroI.isEmpty()) {
            lblErroIdade.setText("- " + erroI);
            EstiloBase.marcarCampoComErro(campoIdade);
            ok = false;
        } else {
            lblErroIdade.setText("");
            EstiloBase.restaurarCampo(campoIdade);
        }

        String nomeCompleto = nome + " " + sobrenome;
        if (ok && controle.salvarDadosVisitante(nomeCompleto, idade)) {
            dispose();
            controle.exibirObra(0);
        }
    }

    private void abrirTeclado(JTextField campo) {
        TecladoVirtual teclado = new TecladoVirtual(this, campo);
        teclado.setVisible(true);
    }
}
