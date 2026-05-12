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
    private ButtonGroup grupoFaixaEtaria;
    private JRadioButton[] radiosFaixaEtaria;
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

        int p = EstiloBase.escalar(38, tela);
        int gap = EstiloBase.escalar(18, tela);
        int cardW = Math.min(EstiloBase.escalar(820, tela), tela.width - EstiloBase.escalar(80, tela));
        int cardH = Math.min(EstiloBase.escalar(700, tela), tela.height - EstiloBase.escalar(84, tela));
        int cardX = (tela.width - cardW) / 2;
        int cardY = (tela.height - cardH) / 2;

        JLabel lblTagPagina = EstiloBase.criarTag("Primeiro passo");
        lblTagPagina.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblTagPagina.setBounds(cardX, Math.max(EstiloBase.escalar(20, tela), cardY - EstiloBase.escalar(48, tela)),
                EstiloBase.escalar(154, tela), EstiloBase.escalar(34, tela));
        fundo.add(lblTagPagina);

        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        card.setBounds(cardX, cardY, cardW, cardH);
        fundo.add(card);

        JLabel lblCardTag = EstiloBase.criarTag("Cadastro do visitante");
        lblCardTag.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblCardTag.setBounds(p, EstiloBase.escalar(32, tela), EstiloBase.escalar(206, tela), EstiloBase.escalar(34, tela));
        card.add(lblCardTag);

        JLabel lblTitulo = EstiloBase.criarLabel(
                "Identificacao da visita",
                EstiloBase.fonteResponsiva(36f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(p, EstiloBase.escalar(84, tela), cardW - (p * 2), EstiloBase.escalar(44, tela));
        card.add(lblTitulo);

        JTextArea lblSub = EstiloBase.criarTextoQuebravel(
                "Informe os dados basicos para iniciar a experiencia. Nenhuma chave, email ou dado sensivel e solicitado.",
                EstiloBase.fonteResponsiva(15f, tela),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblSub.setBounds(p, EstiloBase.escalar(136, tela), cardW - (p * 2), EstiloBase.escalar(54, tela));
        card.add(lblSub);

        int campoW = (cardW - (p * 2) - gap) / 2;
        int yPrimeiraLinha = EstiloBase.escalar(218, tela);
        int labelH = EstiloBase.escalar(24, tela);
        int campoH = EstiloBase.escalar(58, tela);
        int erroH = EstiloBase.escalar(22, tela);

        JLabel lblNome = criarLabelCampo("Nome");
        lblNome.setFont(EstiloBase.fonteResponsiva(16f, tela));
        lblNome.setBounds(p, yPrimeiraLinha, campoW, labelH);
        card.add(lblNome);

        campoNome = EstiloBase.criarCampoTexto("Digite seu nome", 18);
        campoNome.setFont(EstiloBase.fonteResponsiva(19f, tela));
        campoNome.setBounds(p, yPrimeiraLinha + EstiloBase.escalar(30, tela), campoW, campoH);
        campoNome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirTeclado(campoNome);
            }
        });
        card.add(campoNome);

        lblErroNome = criarLabelErro();
        lblErroNome.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblErroNome.setBounds(p, yPrimeiraLinha + EstiloBase.escalar(92, tela), campoW, erroH);
        card.add(lblErroNome);

        JLabel lblSobrenome = criarLabelCampo("Sobrenome");
        lblSobrenome.setFont(EstiloBase.fonteResponsiva(16f, tela));
        lblSobrenome.setBounds(p + campoW + gap, yPrimeiraLinha, campoW, labelH);
        card.add(lblSobrenome);

        campoSobrenome = EstiloBase.criarCampoTexto("Digite seu sobrenome", 18);
        campoSobrenome.setFont(EstiloBase.fonteResponsiva(19f, tela));
        campoSobrenome.setBounds(p + campoW + gap, yPrimeiraLinha + EstiloBase.escalar(30, tela), campoW, campoH);
        campoSobrenome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                abrirTeclado(campoSobrenome);
            }
        });
        card.add(campoSobrenome);

        lblErroSobrenome = criarLabelErro();
        lblErroSobrenome.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblErroSobrenome.setBounds(p + campoW + gap, yPrimeiraLinha + EstiloBase.escalar(92, tela), campoW, erroH);
        card.add(lblErroSobrenome);

        int yIdade = yPrimeiraLinha + EstiloBase.escalar(138, tela);
        JLabel lblFaixaEtaria = criarLabelCampo("Faixa etária");
        lblFaixaEtaria.setFont(EstiloBase.fonteResponsiva(16f, tela));
        lblFaixaEtaria.setBounds(p, yIdade, campoW, labelH);
        card.add(lblFaixaEtaria);

        JPanel painelFaixaEtaria = new JPanel(new GridLayout(3, 2, gap, gap));
        painelFaixaEtaria.setOpaque(false);
        painelFaixaEtaria.setBounds(p, yIdade + EstiloBase.escalar(30, tela), cardW - (p * 2), EstiloBase.escalar(140, tela));
        card.add(painelFaixaEtaria);

        grupoFaixaEtaria = new ButtonGroup();
        radiosFaixaEtaria = new JRadioButton[modelo.Validacao.FAIXAS_ETARIAS_VALIDAS.length];
        for (int i = 0; i < modelo.Validacao.FAIXAS_ETARIAS_VALIDAS.length; i++) {
            JRadioButton radio = criarRadioFaixaEtaria(modelo.Validacao.FAIXAS_ETARIAS_VALIDAS[i], tela);
            radiosFaixaEtaria[i] = radio;
            grupoFaixaEtaria.add(radio);
            painelFaixaEtaria.add(radio);
        }

        lblErroIdade = criarLabelErro();
        lblErroIdade.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblErroIdade.setBounds(p, yIdade + EstiloBase.escalar(92, tela), cardW - (p * 2), erroH);
        card.add(lblErroIdade);

   

        int botoesY = cardH - EstiloBase.escalar(98, tela);
        int btnVoltarW = EstiloBase.escalar(180, tela);
        int btnContinuarW = EstiloBase.escalar(230, tela);
        JButton btnContinuar = EstiloBase.criarBotaoPrimario("Continuar");
        btnContinuar.setFont(EstiloBase.fonteResponsiva(19f, tela));
        btnContinuar.setBounds(cardW - p - btnContinuarW, botoesY, btnContinuarW, EstiloBase.escalar(58, tela));
        btnContinuar.addActionListener(e -> validarEAvancar());
        card.add(btnContinuar);

        JButton btnVoltar = EstiloBase.criarBotaoSecundario("Voltar");
        btnVoltar.setFont(EstiloBase.fonteResponsiva(17f, tela));
        btnVoltar.setBounds(p, botoesY, btnVoltarW, EstiloBase.escalar(58, tela));
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

    private JRadioButton criarRadioFaixaEtaria(String texto, Dimension tela) {
        JRadioButton radio = new JRadioButton(texto);
        radio.setOpaque(false);
        radio.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        radio.setFont(EstiloBase.fonteResponsiva(17f, tela));
        radio.setFocusPainted(false);
        radio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        radio.setIconTextGap(10);
        radio.addActionListener(e -> {
            lblErroIdade.setText("");
            lblErroIdade.repaint();
        });
        return radio;
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

        JTextArea lblTexto = new JTextArea(texto);
        lblTexto.setEditable(false);
        lblTexto.setFocusable(false);
        lblTexto.setOpaque(false);
        lblTexto.setLineWrap(true);
        lblTexto.setWrapStyleWord(true);
        lblTexto.setFont(EstiloBase.FONTE_PEQUENA.deriveFont(14f));
        lblTexto.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblTexto.setBounds(20, 40, 660, 34);
        bloco.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                lblTexto.setBounds(20, 40, Math.max(1, bloco.getWidth() - 40), Math.max(28, bloco.getHeight() - 48));
            }
        });
        bloco.add(lblTexto);

        return bloco;
    }

    private void validarEAvancar() {
        String nome = campoNome.getText().trim();
        String sobrenome = campoSobrenome.getText().trim();
        String faixaEtaria = obterFaixaEtariaSelecionada();
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

        String erroS = controle.erroSobrenome(sobrenome);
        if (!erroS.isEmpty()) {
            lblErroSobrenome.setText("- " + erroS);
            EstiloBase.marcarCampoComErro(campoSobrenome);
            ok = false;
        } else {
            lblErroSobrenome.setText("");
            EstiloBase.restaurarCampo(campoSobrenome);
        }

        String erroI = controle.erroFaixaEtaria(faixaEtaria);
        if (!erroI.isEmpty()) {
            lblErroIdade.setText("- " + erroI);
            ok = false;
        } else {
            lblErroIdade.setText("");
        }

        if (ok && controle.salvarDadosVisitante(nome, sobrenome, faixaEtaria)) {
            dispose();
            controle.exibirObra(0);
        }
    }

    private String obterFaixaEtariaSelecionada() {
        if (grupoFaixaEtaria == null) {
            return null;
        }

        for (JRadioButton radio : radiosFaixaEtaria) {
            if (radio != null && radio.isSelected()) {
                return radio.getText();
            }
        }
        return null;
    }

    private void abrirTeclado(JTextField campo) {
        TecladoVirtual teclado = new TecladoVirtual(this, campo);
        teclado.setVisible(true);
    }
}
