package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.net.URL;

/**
 * Tela de obra com composição editorial:
 * imagem real da obra/missão na esquerda e descrição na direita.
 */
public class fmrObra extends JDialog {

    private final Controle controle;
    private final int indice;

    public fmrObra(JFrame pai, Controle controle, int indice) {
        super(pai, true);
        this.controle = controle;
        this.indice = indice;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
    }

    private void construirInterface() {
        JPanel fundo = EstiloBase.criarPainelFundo(220L + (indice * 31L));
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

        int margem = Math.max(42, tela.width / 34);
        int topo = 38;
        int gap = 34;
        int conteudoY = 124;
        int conteudoH = tela.height - conteudoY - 86;
        int arteW = Math.min(560, (int) (tela.width * 0.34));
        int painelW = tela.width - (margem * 2) - arteW - gap;
        int arteH = conteudoH;

        JLabel lblColecao = EstiloBase.criarTag("Coleção Marte");
        lblColecao.setBounds(margem, topo, 150, 34);
        fundo.add(lblColecao);

        JLabel lblEtapa = EstiloBase.criarLabel(
                String.format("OBRA %02d  DE  %02d", indice + 1, controle.getTotalObras()),
                EstiloBase.FONTE_LABEL.deriveFont(13f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblEtapa.setHorizontalAlignment(SwingConstants.RIGHT);
        lblEtapa.setBounds(tela.width - margem - 180, topo + 2, 180, 28);
        fundo.add(lblEtapa);

        JPanel barraProgress = criarBarraProgresso(tela.width - (margem * 2));
        barraProgress.setBounds(margem, topo + 46, tela.width - (margem * 2), 18);
        fundo.add(barraProgress);

        // ── Card da imagem ────────────────────────────────────────────────

        JPanel cardArte = EstiloBase.criarCard();
        cardArte.setLayout(null);
        cardArte.setBounds(margem, conteudoY, arteW, arteH);
        fundo.add(cardArte);

        String imageObra = controle.getImagemObra(indice);

        JPanel painelImagem = criarPainelImagemObra(imageObra);
        painelImagem.setBounds(20, 20, arteW - 40, arteH - 132);
        cardArte.add(painelImagem);

        JLabel lblCodigo = EstiloBase.criarTag(controle.getCodigoObra(indice));
        lblCodigo.setBounds(24, arteH - 98, 92, 32);
        cardArte.add(lblCodigo);

        JLabel lblAno = EstiloBase.criarLabel(
                controle.getAnoObra(indice),
                EstiloBase.fontePoppins(34f),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblAno.setHorizontalAlignment(SwingConstants.LEFT);
        lblAno.setBounds(24, arteH - 66, 180, 30);
        cardArte.add(lblAno);

        JLabel lblLegenda = EstiloBase.criarLabel(
                "Imagem da obra / missão",
                EstiloBase.FONTE_PEQUENA,
                EstiloBase.COR_TEXTO_FRACO
        );
        lblLegenda.setHorizontalAlignment(SwingConstants.LEFT);
        lblLegenda.setBounds(24, arteH - 38, arteW - 48, 18);
        cardArte.add(lblLegenda);

        // ── Card de informações ───────────────────────────────────────────

        JPanel cardInfo = EstiloBase.criarCard();
        cardInfo.setLayout(null);
        cardInfo.setBounds(margem + arteW + gap, conteudoY, painelW, conteudoH);
        fundo.add(cardInfo);

        JLabel lblTema = EstiloBase.criarTag("Detalhes da obra");
        lblTema.setBounds(30, 26, 160, 34);
        cardInfo.add(lblTema);

        JTextArea lblTitulo = EstiloBase.criarTextoQuebravel(
                controle.getTituloObra(indice),
                EstiloBase.fontePoppins(34f),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblTitulo.setBounds(30, 72, painelW - 60, 120);
        cardInfo.add(lblTitulo);

        JTextArea lblSub = EstiloBase.criarTextoQuebravel(
                "Conheça a história, os objetivos, os desafios e os impactos desta missão "
                        + "na exploração robótica de Marte.",
                EstiloBase.FONTE_CORPO.deriveFont(18f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblSub.setBounds(30, 196, painelW - 60, 62);
        cardInfo.add(lblSub);

        JLabel lblChipAno = EstiloBase.criarTag("ANO " + controle.getAnoObra(indice));
        lblChipAno.setBounds(30, 270, 150, 32);
        cardInfo.add(lblChipAno);

        JLabel lblChipTipo = EstiloBase.criarTag(
                controle.deveExibirModelo3D(indice) ? "COM EXPERIÊNCIA 3D" : "TEXTO CURATORIAL"
        );
        lblChipTipo.setBounds(190, 270, 176, 32);
        cardInfo.add(lblChipTipo);

        JTextArea txtDesc = new JTextArea(controle.getDescricaoObra(indice));
        txtDesc.setFont(EstiloBase.fonteInter(18f));
        txtDesc.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        txtDesc.setBackground(new Color(0, 0, 0, 0));
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setOpaque(false);
        txtDesc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JScrollPane scroll = EstiloBase.criarScrollPane(txtDesc);
        int scrollY = 326;
        int barraAcaoY = conteudoH - 148;
        int scrollH = Math.max(180, barraAcaoY - scrollY - 18);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(30, scrollY, painelW - 60, scrollH);
        cardInfo.add(scroll);

        JPanel faixaAcao = criarFaixaAcao();
        faixaAcao.setBounds(30, barraAcaoY, painelW - 60, 102);
        cardInfo.add(faixaAcao);

        JTextArea lblNota = EstiloBase.criarTextoQuebravel(
                "Continue navegando pela linha do tempo para descobrir novas camadas da exploração de Marte.",
                EstiloBase.FONTE_PEQUENA.deriveFont(15f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNota.setBounds(22, 12, faixaAcao.getWidth() - 44, 34);
        faixaAcao.add(lblNota);

        if (controle.deveExibirModelo3D(indice)) {
            JButton btn3D = EstiloBase.criarBotaoSecundario("Explorar modelo 3D");
            btn3D.setBounds(22, 44, 230, 44);
            btn3D.addActionListener(e -> abrirModelo3D());
            faixaAcao.add(btn3D);
        }

        JButton btnProximo = EstiloBase.criarBotaoPrimario(
                indice == controle.getTotalObras() - 1
                        ? "Ir para o questionário"
                        : "Próxima obra"
        );
        btnProximo.setBounds(faixaAcao.getWidth() - 250, 40, 228, 48);
        btnProximo.addActionListener(e -> {
            dispose();
            controle.proximaEtapaAposObra(indice);
        });
        faixaAcao.add(btnProximo);

        setContentPane(fundo);
    }

    private JPanel criarPainelImagemObra(String caminhoImagem) {
        return new JPanel(null) {
            private Image imagem;

            {
                imagem = carregarImagem(caminhoImagem);
                setOpaque(false);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                Shape forma = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 28, 28);
                g2.setClip(forma);

                if (imagem != null) {
                    desenharImagemCover(g2, imagem, getWidth(), getHeight());
                } else {
                    desenharFallbackImagem(g2);
                }

                g2.setClip(null);
                g2.setColor(new Color(255, 255, 255, 26));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);

                g2.dispose();
            }
        };
    }

    private Image carregarImagem(String caminhoImagem) {
        if (caminhoImagem == null || caminhoImagem.isBlank()) {
            return null;
        }

        try {
            URL url = getClass().getResource(caminhoImagem);
            if (url != null) {
                return new ImageIcon(url).getImage();
            }

            File arquivo = new File("src" + caminhoImagem);
            if (arquivo.exists()) {
                return new ImageIcon(arquivo.getAbsolutePath()).getImage();
            }

            File arquivoDireto = new File(caminhoImagem);
            if (arquivoDireto.exists()) {
                return new ImageIcon(arquivoDireto.getAbsolutePath()).getImage();
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }

    private void desenharImagemCover(Graphics2D g2, Image imagem, int larguraPainel, int alturaPainel) {
        int larguraImagem = imagem.getWidth(null);
        int alturaImagem = imagem.getHeight(null);

        if (larguraImagem <= 0 || alturaImagem <= 0) {
            desenharFallbackImagem(g2);
            return;
        }

        double escala = Math.max(
                (double) larguraPainel / larguraImagem,
                (double) alturaPainel / alturaImagem
        );

        int novaLargura = (int) Math.ceil(larguraImagem * escala);
        int novaAltura = (int) Math.ceil(alturaImagem * escala);

        int x = (larguraPainel - novaLargura) / 2;
        int y = (alturaPainel - novaAltura) / 2;

        g2.drawImage(imagem, x, y, novaLargura, novaAltura, null);

        GradientPaint sombra = new GradientPaint(
                0, 0, new Color(0, 0, 0, 20),
                0, alturaPainel, new Color(0, 0, 0, 150)
        );
        g2.setPaint(sombra);
        g2.fillRect(0, 0, larguraPainel, alturaPainel);
    }

    private void desenharFallbackImagem(Graphics2D g2) {
        GradientPaint fundo = new GradientPaint(
                0, 0, new Color(255, 98, 36, 54),
                getWidth(), getHeight(), new Color(233, 32, 97, 42)
        );
        g2.setPaint(fundo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

        g2.setColor(new Color(255, 255, 255, 26));
        for (int x = 24; x < getWidth(); x += 26) {
            g2.drawLine(x, 0, x, getHeight());
        }

        for (int y = 24; y < getHeight(); y += 26) {
            g2.drawLine(0, y, getWidth(), y);
        }

        JLabelHelper.drawCenteredText(
                g2,
                "Imagem não encontrada",
                getWidth(),
                getHeight(),
                EstiloBase.fontePoppins(24f),
                new Color(255, 255, 255, 190)
        );
    }

    private JPanel criarFaixaAcao() {
        return new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(255, 255, 255, 10));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                g2.dispose();
            }
        };
    }

    private void abrirModelo3D() {
        EstiloBase.mostrarDialogoInformativo(
                this,
                "3D",
                "Visualização do rover",
                "A integração real com um visualizador 3D ainda é um placeholder, mas a interface já está preparada "
                        + "para receber essa experiência sem quebrar o fluxo principal do totem.",
                "Fechar"
        );
    }

    private JPanel criarBarraProgresso(int largura) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int total = controle.getTotalObras();
                int gap = 8;
                int segW = (largura - ((total - 1) * gap)) / total;

                for (int i = 0; i < total; i++) {
                    int x = i * (segW + gap);

                    g2.setColor(new Color(255, 255, 255, 18));
                    g2.fillRoundRect(x, 4, segW, 10, 10, 10);

                    if (i <= indice) {
                        GradientPaint gp = new GradientPaint(
                                x, 0, EstiloBase.COR_DESTAQUE,
                                x + segW, 14, EstiloBase.COR_DESTAQUE_2
                        );
                        g2.setPaint(gp);
                        g2.fillRoundRect(x, 4, segW, 10, 10, 10);
                    }
                }

                g2.dispose();
            }
        };
    }

    /**
     * Helper interno para desenhar texto centralizado no fallback.
     */
    private static class JLabelHelper {
        static void drawCenteredText(
                Graphics2D g2,
                String texto,
                int largura,
                int altura,
                Font fonte,
                Color cor
        ) {
            Font fonteAnterior = g2.getFont();
            Color corAnterior = g2.getColor();

            g2.setFont(fonte);
            g2.setColor(cor);

            FontMetrics fm = g2.getFontMetrics();
            int x = (largura - fm.stringWidth(texto)) / 2;
            int y = (altura - fm.getHeight()) / 2 + fm.getAscent();

            g2.drawString(texto, x, y);

            g2.setFont(fonteAnterior);
            g2.setColor(corAnterior);
        }
    }
}
