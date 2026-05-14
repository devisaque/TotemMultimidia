package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de cadastro do visitante em card central.
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
        int cardW = Math.min(EstiloBase.escalar(880, tela), tela.width - EstiloBase.escalar(80, tela));
        int cardH = Math.min(EstiloBase.escalar(760, tela), tela.height - EstiloBase.escalar(84, tela));
        int cardX = (tela.width - cardW) / 2;
        int cardY = (tela.height - cardH) / 2;

        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        card.setBounds(cardX, cardY, cardW, cardH);
        fundo.add(card);

        // Título
        JLabel lblTitulo = EstiloBase.criarLabel(
                "Identificação da visita",
                EstiloBase.fonteResponsiva(36f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(p, EstiloBase.escalar(48, tela), cardW - (p * 2), EstiloBase.escalar(44, tela));
        card.add(lblTitulo);

        JTextArea lblSub = EstiloBase.criarTextoQuebravel(
                "Informe os dados básicos para iniciar a experiência. Nenhuma chave, email ou dado sensível é solicitado.",
                EstiloBase.fonteResponsiva(15f, tela),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblSub.setBounds(p, EstiloBase.escalar(102, tela), cardW - (p * 2), EstiloBase.escalar(54, tela));
        card.add(lblSub);

        int campoW = (cardW - (p * 2) - gap) / 2;
        int yPrimeiraLinha = EstiloBase.escalar(184, tela);
        int labelH = EstiloBase.escalar(24, tela);
        int campoH = EstiloBase.escalar(58, tela);
        int erroH  = EstiloBase.escalar(22, tela);

        // Campo Nome
        JLabel lblNome = criarLabelCampo("Nome");
        lblNome.setFont(EstiloBase.fonteResponsiva(16f, tela));
        lblNome.setBounds(p, yPrimeiraLinha, campoW, labelH);
        card.add(lblNome);

        campoNome = EstiloBase.criarCampoTexto("Digite seu nome", 18);
        campoNome.setFont(EstiloBase.fonteResponsiva(19f, tela));
        campoNome.setBounds(p, yPrimeiraLinha + EstiloBase.escalar(30, tela), campoW, campoH);
        campoNome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { abrirTeclado(campoNome); }
        });
        card.add(campoNome);

        lblErroNome = criarLabelErro();
        lblErroNome.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblErroNome.setBounds(p, yPrimeiraLinha + EstiloBase.escalar(92, tela), campoW, erroH);
        card.add(lblErroNome);

        // Campo Sobrenome
        JLabel lblSobrenome = criarLabelCampo("Sobrenome");
        lblSobrenome.setFont(EstiloBase.fonteResponsiva(16f, tela));
        lblSobrenome.setBounds(p + campoW + gap, yPrimeiraLinha, campoW, labelH);
        card.add(lblSobrenome);

        campoSobrenome = EstiloBase.criarCampoTexto("Digite seu sobrenome", 18);
        campoSobrenome.setFont(EstiloBase.fonteResponsiva(19f, tela));
        campoSobrenome.setBounds(p + campoW + gap, yPrimeiraLinha + EstiloBase.escalar(30, tela), campoW, campoH);
        campoSobrenome.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override public void mouseClicked(java.awt.event.MouseEvent e) { abrirTeclado(campoSobrenome); }
        });
        card.add(campoSobrenome);

        lblErroSobrenome = criarLabelErro();
        lblErroSobrenome.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblErroSobrenome.setBounds(p + campoW + gap, yPrimeiraLinha + EstiloBase.escalar(92, tela), campoW, erroH);
        card.add(lblErroSobrenome);

        // --- Faixa etária ---
        int yIdade = yPrimeiraLinha + EstiloBase.escalar(138, tela);

        // Fonte aumentada no label "Faixa etária"
        JLabel lblFaixaEtaria = criarLabelCampo("Faixa etária");
        lblFaixaEtaria.setFont(EstiloBase.fonteResponsiva(20f, tela));
        lblFaixaEtaria.setBounds(p, yIdade, cardW - (p * 2), EstiloBase.escalar(28, tela));
        card.add(lblFaixaEtaria);

        // Painel ocupa toda a largura util do card (igual aos campos de texto)
        int painelFaixaW = cardW - (p * 2);
        int painelFaixaH = EstiloBase.escalar(160, tela);
        int painelFaixaX = p;
        int painelFaixaY = yIdade + EstiloBase.escalar(34, tela);
        JPanel painelFaixaEtaria = new JPanel(new GridLayout(3, 2, gap, EstiloBase.escalar(8, tela)));
        painelFaixaEtaria.setOpaque(false);
        painelFaixaEtaria.setBounds(painelFaixaX, painelFaixaY, painelFaixaW, painelFaixaH);
        card.add(painelFaixaEtaria);

        grupoFaixaEtaria = new ButtonGroup();
        radiosFaixaEtaria = new JRadioButton[modelo.Validacao.FAIXAS_ETARIAS_VALIDAS.length];
        for (int i = 0; i < modelo.Validacao.FAIXAS_ETARIAS_VALIDAS.length; i++) {
            // Fonte dos radio buttons aumentada
            JRadioButton radio = criarRadioFaixaEtaria(modelo.Validacao.FAIXAS_ETARIAS_VALIDAS[i], tela);
            radiosFaixaEtaria[i] = radio;
            grupoFaixaEtaria.add(radio);
            painelFaixaEtaria.add(radio);
        }

        // Erro faixa etária logo abaixo do painel
        lblErroIdade = criarLabelErro();
        lblErroIdade.setFont(EstiloBase.fonteResponsiva(13f, tela));
        lblErroIdade.setBounds(painelFaixaX, painelFaixaY + painelFaixaH + EstiloBase.escalar(4, tela),
                painelFaixaW, erroH);
        card.add(lblErroIdade);

        // --- Botões: centralizados, fonte mantida, tamanho aumentado ---
        int botoesY      = cardH - EstiloBase.escalar(108, tela);
        int btnVoltarW   = EstiloBase.escalar(230, tela);
        int btnContinuarW= EstiloBase.escalar(280, tela);
        int btnH         = EstiloBase.escalar(72, tela);
        int gapBotoes    = EstiloBase.escalar(24, tela);
        int totalBotoes  = btnVoltarW + gapBotoes + btnContinuarW;
        int botoesX      = (cardW - totalBotoes) / 2;

        JButton btnVoltar = EstiloBase.criarBotaoSecundario("Voltar");
        btnVoltar.setFont(EstiloBase.fonteResponsiva(20f, tela));
        btnVoltar.setBounds(botoesX, botoesY, btnVoltarW, btnH);
        btnVoltar.addActionListener(e -> { dispose(); controle.exibirTelaInicial(); });
        card.add(btnVoltar);

        JButton btnContinuar = EstiloBase.criarBotaoPrimario("Continuar");
        btnContinuar.setFont(EstiloBase.fonteResponsiva(22f, tela));
        btnContinuar.setBounds(botoesX + btnVoltarW + gapBotoes, botoesY, btnContinuarW, btnH);
        btnContinuar.addActionListener(e -> validarEAvancar());
        card.add(btnContinuar);

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
        // Fonte aumentada nos radio buttons
        radio.setFont(EstiloBase.fonteResponsiva(20f, tela));
        radio.setFocusPainted(false);
        radio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        radio.setIconTextGap(12);
        radio.addActionListener(e -> { lblErroIdade.setText(""); lblErroIdade.repaint(); });
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
            @Override public void componentResized(java.awt.event.ComponentEvent e) {
                lblTexto.setBounds(20, 40, Math.max(1, bloco.getWidth() - 40), Math.max(28, bloco.getHeight() - 48));
            }
        });
        bloco.add(lblTexto);
        return bloco;
    }

    private void validarEAvancar() {
        // Valida cada campo separadamente para manter o feedback visual no proprio formulario.
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
        if (grupoFaixaEtaria == null) return null;
        for (JRadioButton radio : radiosFaixaEtaria) {
            if (radio != null && radio.isSelected()) return radio.getText();
        }
        return null;
    }

    private void abrirTeclado(JTextField campo) {
        new TecladoVirtual(this, campo).setVisible(true);
    }
}
