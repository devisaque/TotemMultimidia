package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de cadastro com composicao em duas colunas.
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
        JPanel fundo = EstiloBase.criarPainelFundo(77L);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

        int margem = Math.max(48, tela.width / 30);
        int colunaTextoW = (int) (tela.width * 0.42);
        int cardX = margem + colunaTextoW + 24;
        int cardW = tela.width - cardX - margem;

        JLabel lblTag = EstiloBase.criarTag("Primeiro passo");
        lblTag.setBounds(margem, 70, 136, 34);
        fundo.add(lblTag);

        JLabel lblTitulo = new JLabel("<html><div style='width:" + (colunaTextoW - 12) + "px'>"
                + "Antes da visita, queremos saber quem esta explorando com a gente.</div></html>");
        lblTitulo.setFont(EstiloBase.fontePoppins(50f));
        lblTitulo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setBounds(margem, 130, colunaTextoW, 250);
        fundo.add(lblTitulo);

        JLabel lblSub = new JLabel("<html><div style='width:" + (colunaTextoW - 20) + "px'>"
                + "Preencha os dados em uma interface otimizada para toque. O objetivo aqui e apenas personalizar a experiencia "
                + "e adaptar o fluxo do totem ao visitante atual.</div></html>");
        lblSub.setFont(EstiloBase.fonteInter(21f));
        lblSub.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblSub.setBounds(margem, 378, colunaTextoW - 20, 110);
        fundo.add(lblSub);

        JPanel blocoPrivacidade = criarBlocoInformacao(
                "Privacidade",
                "Nao coletamos dados sensiveis e nada e persistido em disco. A informacao serve apenas para esta sessao."
        );
        blocoPrivacidade.setBounds(margem, 520, colunaTextoW - 30, 118);
        fundo.add(blocoPrivacidade);

        JPanel blocoToque = criarBlocoInformacao(
                "Entrada por toque",
                "Ao tocar em um campo, o teclado virtual interno sera aberto para manter a experiencia dentro do totem."
        );
        blocoToque.setBounds(margem, 654, colunaTextoW - 30, 118);
        fundo.add(blocoToque);

        JPanel card = EstiloBase.criarCard();
        card.setLayout(null);
        card.setBounds(cardX, 92, cardW, tela.height - 184);
        fundo.add(card);

        JLabel lblCardTag = EstiloBase.criarTag("Cadastro do visitante");
        lblCardTag.setBounds(34, 28, 178, 34);
        card.add(lblCardTag);

        JLabel lblCardTitulo = EstiloBase.criarLabel(
                "Identificacao da visita",
                EstiloBase.fontePoppins(34f),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblCardTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblCardTitulo.setBounds(34, 78, cardW - 68, 44);
        card.add(lblCardTitulo);

        JLabel lblNome = EstiloBase.criarLabel("Nome", EstiloBase.FONTE_LABEL.deriveFont(17f), EstiloBase.COR_ACENTO_FRIO);
        lblNome.setHorizontalAlignment(SwingConstants.LEFT);
        lblNome.setBounds(34, 156, cardW - 68, 24);
        card.add(lblNome);

        campoNome = EstiloBase.criarCampoTexto("Digite seu nome", 20);
        campoNome.setBounds(34, 188, cardW - 68, 60);
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
        lblErroNome.setBounds(34, 254, cardW - 68, 20);
        card.add(lblErroNome);

        JLabel lblIdade = EstiloBase.criarLabel("Idade", EstiloBase.FONTE_LABEL.deriveFont(17f), EstiloBase.COR_ACENTO_FRIO);
        lblIdade.setHorizontalAlignment(SwingConstants.LEFT);
        lblIdade.setBounds(34, 308, cardW - 68, 24);
        card.add(lblIdade);

        campoIdade = EstiloBase.criarCampoTexto("Ex: 25", 4);
        campoIdade.setBounds(34, 340, cardW - 68, 60);
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
        lblErroIdade.setBounds(34, 406, cardW - 68, 20);
        card.add(lblErroIdade);

        JLabel lblAjuda = new JLabel("<html><div style='width:" + (cardW - 68) + "px'>"
                + "Dica: os campos foram ampliados e os botoes ficaram mais espaçados para melhorar o uso em tela cheia.</div></html>");
        lblAjuda.setFont(EstiloBase.FONTE_PEQUENA.deriveFont(15f));
        lblAjuda.setForeground(EstiloBase.COR_TEXTO_FRACO);
        lblAjuda.setBounds(34, 458, cardW - 68, 44);
        card.add(lblAjuda);

        JButton btnContinuar = EstiloBase.criarBotaoPrimario("Continuar");
        btnContinuar.setBounds(34, card.getHeight() - 116, 230, 60);
        btnContinuar.addActionListener(e -> validarEAvancar());
        card.add(btnContinuar);

        JButton btnVoltar = EstiloBase.criarBotaoSecundario("Voltar");
        btnVoltar.setBounds(280, card.getHeight() - 116, 180, 60);
        btnVoltar.addActionListener(e -> {
            dispose();
            controle.exibirTelaInicial();
        });
        card.add(btnVoltar);

        setContentPane(fundo);
    }

    private JPanel criarBlocoInformacao(String titulo, String texto) {
        JPanel bloco = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(255, 255, 255, 9));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 24, 24);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 24, 24);
                g2.dispose();
            }
        };
        bloco.setOpaque(false);

        JLabel lblTitulo = EstiloBase.criarLabel(titulo, EstiloBase.FONTE_LABEL.deriveFont(16f), EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setBounds(20, 16, 260, 18);
        bloco.add(lblTitulo);

        JLabel lblTexto = new JLabel("<html><div style='width:320px'>" + texto + "</div></html>");
        lblTexto.setFont(EstiloBase.FONTE_PEQUENA.deriveFont(15f));
        lblTexto.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblTexto.setBounds(20, 40, 330, 56);
        bloco.add(lblTexto);

        return bloco;
    }

    private void validarEAvancar() {
        String nome = campoNome.getText().trim();
        String idade = campoIdade.getText().trim();
        boolean ok = true;

        String erroN = controle.erroNome(nome);
        if (!erroN.isEmpty()) {
            lblErroNome.setText("• " + erroN);
            EstiloBase.marcarCampoComErro(campoNome);
            ok = false;
        } else {
            lblErroNome.setText("");
            EstiloBase.restaurarCampo(campoNome);
        }

        String erroI = controle.erroIdade(idade);
        if (!erroI.isEmpty()) {
            lblErroIdade.setText("• " + erroI);
            EstiloBase.marcarCampoComErro(campoIdade);
            ok = false;
        } else {
            lblErroIdade.setText("");
            EstiloBase.restaurarCampo(campoIdade);
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
}
