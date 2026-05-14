package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.net.URL;

/**
 * Tela de obra com composicao editorial:
 * imagem real da obra/missao na esquerda e descricao na direita.
 * Abaixo da imagem: painel "Sobre esta obra" com scroll.
 */
public class fmrObra extends JDialog {

    private static final int ESPACO_CODIGO_ANO = 5;
    private static final Color COR_FAIXA_ACAO = new Color(7, 5, 7);

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

        int margem    = Math.max(30, EstiloBase.escalar(42, tela));
        int topo      = Math.max(26, EstiloBase.escalar(38, tela));
        int gap       = Math.max(24, EstiloBase.escalar(34, tela));
        int conteudoY = topo + Math.max(76, EstiloBase.escalar(86, tela));
        int conteudoH = tela.height - conteudoY - Math.max(54, EstiloBase.escalar(68, tela));
        int arteW     = Math.min(560, (int) (tela.width * 0.34));
        int painelW   = tela.width - (margem * 2) - arteW - gap;
        int arteH     = conteudoH;

        JLabel lblColecao = EstiloBase.criarTag("Cole\u00e7\u00e3o Marte");
        lblColecao.setBounds(margem, topo, Math.max(142, EstiloBase.escalar(150, tela)), Math.max(32, EstiloBase.escalar(34, tela)));
        fundo.add(lblColecao);

        JLabel lblEtapa = EstiloBase.criarLabel(
                String.format("OBRA %02d  DE  %02d", indice + 1, controle.getTotalObras()),
                EstiloBase.FONTE_LABEL.deriveFont(13f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblEtapa.setHorizontalAlignment(SwingConstants.RIGHT);
        int etapaW = Math.max(160, EstiloBase.escalar(180, tela));
        lblEtapa.setBounds(tela.width - margem - etapaW, topo + 2, etapaW, Math.max(26, EstiloBase.escalar(28, tela)));
        fundo.add(lblEtapa);

        JPanel barraProgress = criarBarraProgresso(tela.width - (margem * 2));
        barraProgress.setBounds(margem, topo + Math.max(40, EstiloBase.escalar(46, tela)), tela.width - (margem * 2), Math.max(14, EstiloBase.escalar(18, tela)));
        fundo.add(barraProgress);

        // ── Card da imagem ─────────────────────────────────────────────────────

        JPanel cardArte = EstiloBase.criarCard();
        cardArte.setLayout(null);
        cardArte.setBounds(margem, conteudoY, arteW, arteH);
        fundo.add(cardArte);

        String imageObra  = controle.getImagemObra(indice);

        // Margem interna do card
        int artePadding = Math.max(14, EstiloBase.escalar(16, tela));
        int seloX       = Math.max(20, EstiloBase.escalar(24, tela));

        // Alturas dos elementos inferiores (codigo, ano, legenda)
        int legendaH = Math.max(20, EstiloBase.escalar(22, tela));
        int seloH    = Math.max(32, EstiloBase.escalar(32, tela));
        int anoH     = Math.max(32, EstiloBase.escalar(34, tela));

        // Altura do painel "Sobre esta obra" (scroll) — fixado entre imagem e bloco inferior
        int sobreH   = Math.max(80, EstiloBase.escalar(100, tela));
        int sobreGap = Math.max(10, EstiloBase.escalar(12, tela));

        int espacoInternoInferior = artePadding + legendaH + anoH + seloH
                + ESPACO_CODIGO_ANO
                + sobreH + sobreGap
                + Math.max(10, EstiloBase.escalar(10, tela));

        // Altura disponivel para a imagem
        int imagemH = arteH - artePadding - espacoInternoInferior;
        imagemH = Math.max(120, imagemH);

        // Largura da imagem respeitando margem lateral interna
        int imagemW = arteW - (artePadding * 2);

        // Painel da imagem
        JPanel painelImagem = criarPainelImagemObra(imageObra);
        painelImagem.setBounds(artePadding, artePadding, imagemW, imagemH);
        painelImagem.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        painelImagem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirVisualizadorFullscreen(imageObra);
            }
        });
        cardArte.add(painelImagem);

        // ── Painel "Sobre esta obra" com scroll ────────────────────────────────

        int sobreY = artePadding + imagemH + sobreGap;

        String explicacao = controle.getExplicacaoObra(indice);

        String corSobre = String.format("#%02x%02x%02x",
                EstiloBase.COR_TEXTO_SECUNDARIO.getRed(),
                EstiloBase.COR_TEXTO_SECUNDARIO.getGreen(),
                EstiloBase.COR_TEXTO_SECUNDARIO.getBlue());
        int fonteSobrePx = Math.max(12, EstiloBase.escalar(14, tela));

        JTextPane txtSobre = new JTextPane();
        txtSobre.setContentType("text/html");
        txtSobre.setText("<html><body style=\""
                + "color:" + corSobre + ";"
                + "font-family:sans-serif;"
                + "font-size:" + fonteSobrePx + "px;"
                + "margin:4px 2px;padding:0;"
                + "\">" + explicacao + "</body></html>");
        txtSobre.setEditable(false);
        txtSobre.setOpaque(false);
        txtSobre.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JScrollPane scrollSobre = new JScrollPane(txtSobre);
        scrollSobre.setOpaque(false);
        scrollSobre.getViewport().setOpaque(false);
        scrollSobre.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(255, 255, 255, 28)));
        scrollSobre.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollSobre.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollSobre.getVerticalScrollBar().setUnitIncrement(12);
        // Barra de scroll discreta (sem aparencia padrao do SO)
        scrollSobre.getVerticalScrollBar().setPreferredSize(new Dimension(4, 0));
        scrollSobre.setBounds(artePadding, sobreY, imagemW, sobreH);
        cardArte.add(scrollSobre);

        SwingUtilities.invokeLater(() -> scrollSobre.getVerticalScrollBar().setValue(0));

        // ── Elementos abaixo do painel Sobre (codigo, ano, legenda) ───────────

        int seloY = sobreY + sobreH + Math.max(10, EstiloBase.escalar(10, tela));

        JLabel lblCodigo = EstiloBase.criarTag(controle.getCodigoObra(indice));
        lblCodigo.setBounds(seloX, seloY, Math.max(92, EstiloBase.escalar(92, tela)), seloH);
        cardArte.add(lblCodigo);

        JLabel lblAno = EstiloBase.criarLabel(
                controle.getAnoObra(indice),
                EstiloBase.fonteResponsiva(34f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblAno.setHorizontalAlignment(SwingConstants.LEFT);
        int anoY = seloY + seloH + ESPACO_CODIGO_ANO;
        lblAno.setBounds(seloX, anoY, Math.max(180, EstiloBase.escalar(180, tela)), anoH);
        cardArte.add(lblAno);

        JLabel lblLegenda = EstiloBase.criarLabel(
                "Clique na imagem para ampliar",
                EstiloBase.FONTE_PEQUENA,
                new Color(200, 160, 90)
        );
        lblLegenda.setHorizontalAlignment(SwingConstants.LEFT);
        lblLegenda.setBounds(seloX, anoY + anoH, arteW - (seloX * 2), legendaH);
        cardArte.add(lblLegenda);

        // ── Card de informacoes ───────────────────────────────────────────────

        JPanel cardInfo = EstiloBase.criarCard();
        cardInfo.setLayout(null);
        cardInfo.setBounds(margem + arteW + gap, conteudoY, painelW, conteudoH);
        fundo.add(cardInfo);

        int infoPad  = Math.max(24, EstiloBase.escalar(30, tela));
        int infoTagY = Math.max(22, EstiloBase.escalar(26, tela));
        int infoTagH = Math.max(32, EstiloBase.escalar(34, tela));

        JLabel lblTema = EstiloBase.criarTag("Detalhes da obra");
        lblTema.setBounds(infoPad, infoTagY, Math.max(150, EstiloBase.escalar(160, tela)), infoTagH);
        cardInfo.add(lblTema);

        // ── Conteudo scrollavel: TITULO + texto descritivo ────────────────────

        JPanel painelConteudo = new JPanel();
        painelConteudo.setLayout(new BoxLayout(painelConteudo, BoxLayout.Y_AXIS));
        painelConteudo.setOpaque(false);
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));

        JTextArea lblTitulo = EstiloBase.criarTextoQuebravel(
                controle.getTituloObra(indice),
                EstiloBase.fonteResponsiva(34f, tela),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTitulo.setMaximumSize(new Dimension(painelW - (infoPad * 2), Integer.MAX_VALUE));
        painelConteudo.add(lblTitulo);
        painelConteudo.add(Box.createVerticalStrut(Math.max(14, EstiloBase.escalar(16, tela))));

        String corHex = String.format("#%02x%02x%02x",
                EstiloBase.COR_TEXTO_SECUNDARIO.getRed(),
                EstiloBase.COR_TEXTO_SECUNDARIO.getGreen(),
                EstiloBase.COR_TEXTO_SECUNDARIO.getBlue());
        String fontePx = String.valueOf(Math.max(14, EstiloBase.escalar(18, tela)));
        String htmlDesc = "<html><body style=\""
                + "color:" + corHex + ";"
                + "font-family:sans-serif;"
                + "font-size:" + fontePx + "px;"
                + "margin:0;padding:0;"
                + "\">"
                + controle.getDescricaoObra(indice)
                + "</body></html>";

        JTextPane txtDesc = new JTextPane();
        txtDesc.setContentType("text/html");
        txtDesc.setText(htmlDesc);
        txtDesc.setEditable(false);
        txtDesc.setOpaque(false);
        txtDesc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        txtDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
        txtDesc.setMaximumSize(new Dimension(painelW - (infoPad * 2), Integer.MAX_VALUE));
        painelConteudo.add(txtDesc);

        JScrollPane scroll = EstiloBase.criarScrollPane(painelConteudo);
        int faixaH    = Math.max(86, EstiloBase.escalar(102, tela));
        int scrollY   = infoTagY + infoTagH + Math.max(16, EstiloBase.escalar(18, tela));
        int barraAcaoY = conteudoH - faixaH - Math.max(30, EstiloBase.escalar(46, tela));
        int scrollH   = Math.max(1, barraAcaoY - scrollY - Math.max(18, EstiloBase.escalar(24, tela)));
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(infoPad, scrollY, painelW - (infoPad * 2), scrollH);
        cardInfo.add(scroll);

        SwingUtilities.invokeLater(() -> scroll.getVerticalScrollBar().setValue(0));

        // ── Faixa de acao ───────────────────────────────────────────────────────────

        JPanel faixaAcao = criarFaixaAcao();
        faixaAcao.setBounds(infoPad, barraAcaoY, painelW - (infoPad * 2), faixaH);
        cardInfo.add(faixaAcao);

        int acaoPadding        = Math.max(18, EstiloBase.escalar(22, tela));
        int botaoH             = Math.max(46, EstiloBase.escalar(50, tela));
        int botaoY             = Math.max(24, (faixaAcao.getHeight() - botaoH) / 2);
        int larguraBotaoVoltar = Math.max(140, EstiloBase.escalar(150, tela));
        int larguraProximoDesejada = indice == controle.getTotalObras() - 1 ? 252 : 228;
        int espaco             = Math.max(10, EstiloBase.escalar(12, tela));
        int larguraProximo     = Math.min(
                Math.max(190, EstiloBase.escalar(larguraProximoDesejada, tela)),
                faixaAcao.getWidth() - (acaoPadding * 2) - larguraBotaoVoltar - espaco
        );

        JButton btnVoltar = criarBotaoAcaoObra("\u2190 Voltar", false);
        btnVoltar.setFont(EstiloBase.fonteResponsiva(17f, tela));
        btnVoltar.setBounds(acaoPadding, botaoY, larguraBotaoVoltar, botaoH);
        btnVoltar.addActionListener(e -> {
            btnVoltar.setEnabled(false);
            dispose();
            if (indice == 0) {
                controle.voltarParaInicio();
            } else {
                controle.abrirObra(indice - 1);
            }
        });
        faixaAcao.add(btnVoltar);

        JButton btnProximo = criarBotaoAcaoObra(
                indice == controle.getTotalObras() - 1
                        ? "Ir para o question\u00e1rio"
                        : "Pr\u00f3xima obra",
                true
        );
        btnProximo.setFont(EstiloBase.fonteResponsiva(18f, tela));
        btnProximo.setBounds(faixaAcao.getWidth() - acaoPadding - larguraProximo, botaoY, larguraProximo, botaoH);
        btnProximo.addActionListener(e -> {
            btnProximo.setEnabled(false);
            controle.proximaEtapaAposObra(indice);
            dispose();
        });
        faixaAcao.add(btnProximo);

        setContentPane(fundo);
    }

    // ── Visualizador fullscreen ────────────────────────────────────────────────

    private void abrirVisualizadorFullscreen(String caminhoImagem) {
        Image imagem = carregarImagem(caminhoImagem);

        JDialog viewer = new JDialog(this, true);
        viewer.setUndecorated(true);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        viewer.setSize(tela);
        viewer.setLocationRelativeTo(null);

        JPanel overlay = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setColor(new Color(0, 0, 0, 230));
                g2.fillRect(0, 0, getWidth(), getHeight());
                if (imagem != null) {
                    int imgW = imagem.getWidth(null);
                    int imgH = imagem.getHeight(null);
                    if (imgW > 0 && imgH > 0) {
                        double escala = Math.min(
                                (double)(getWidth() - 100) / imgW,
                                (double)(getHeight() - 100) / imgH
                        );
                        int novaLargura = (int)(imgW * escala);
                        int novaAltura  = (int)(imgH * escala);
                        int x = (getWidth() - novaLargura) / 2;
                        int y = (getHeight() - novaAltura) / 2;
                        g2.drawImage(imagem, x, y, novaLargura, novaAltura, null);
                    }
                }
                g2.dispose();
            }
        };
        overlay.setBackground(Color.BLACK);

        JLabel lblFechar = new JLabel("\u00d7  Clique em qualquer lugar para fechar");
        lblFechar.setFont(EstiloBase.FONTE_LABEL.deriveFont(Font.BOLD, 16f));
        lblFechar.setForeground(new Color(255, 255, 255, 180));
        lblFechar.setBounds(0, 18, tela.width, 30);
        lblFechar.setHorizontalAlignment(SwingConstants.CENTER);
        overlay.add(lblFechar);

        overlay.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                viewer.dispose();
            }
        });

        viewer.setContentPane(overlay);
        viewer.setVisible(true);
    }

    // ── Painel de imagem alinhado ao topo, com borda arredondada no tamanho real da imagem ──

    private JPanel criarPainelImagemObra(String caminhoImagem) {
        return new JPanel(null) {
            private final Image imagem = carregarImagem(caminhoImagem);

            { setOpaque(false); }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                int arco = 20;

                int px = 0, py = 0, pw = getWidth(), ph = getHeight();
                if (imagem != null) {
                    int imgW = imagem.getWidth(null);
                    int imgH = imagem.getHeight(null);
                    if (imgW > 0 && imgH > 0) {
                        double escala = Math.min((double) getWidth() / imgW, (double) getHeight() / imgH);
                        pw = (int) Math.round(imgW * escala);
                        ph = (int) Math.round(imgH * escala);
                        px = (getWidth() - pw) / 2;
                        py = 0;
                    }
                }

                Shape forma = new RoundRectangle2D.Float(px, py, pw, ph, arco, arco);
                g2.setClip(forma);

                if (imagem != null) {
                    g2.setColor(new Color(18, 14, 16));
                    g2.fillRect(px, py, pw, ph);

                    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                    g2.drawImage(imagem, px, py, pw, ph, null);

                    GradientPaint sombra = new GradientPaint(
                            0, py + ph * 0.7f, new Color(0, 0, 0, 0),
                            0, py + ph,        new Color(0, 0, 0, 100)
                    );
                    g2.setPaint(sombra);
                    g2.fillRect(px, py, pw, ph);
                } else {
                    desenharFallbackImagem(g2);
                }

                g2.setClip(null);

                g2.setColor(new Color(255, 255, 255, 40));
                g2.setStroke(new BasicStroke(1.5f));
                g2.draw(new RoundRectangle2D.Float(px, py, pw - 1, ph - 1, arco, arco));

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
            if (url != null) return new ImageIcon(url).getImage();

            File arquivo = new File("src" + caminhoImagem);
            if (arquivo.exists()) return new ImageIcon(arquivo.getAbsolutePath()).getImage();

            File arquivoDireto = new File(caminhoImagem);
            if (arquivoDireto.exists()) return new ImageIcon(arquivoDireto.getAbsolutePath()).getImage();
        } catch (Exception e) {
            return null;
        }
        return null;
    }

    private void desenharFallbackImagem(Graphics2D g2) {
        GradientPaint fundo = new GradientPaint(
                0, 0, new Color(255, 98, 36, 54),
                getWidth(), getHeight(), new Color(233, 32, 97, 42)
        );
        g2.setPaint(fundo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2.setColor(new Color(255, 255, 255, 26));
        for (int x = 24; x < getWidth(); x += 26) g2.drawLine(x, 0, x, getHeight());
        for (int y = 24; y < getHeight(); y += 26) g2.drawLine(0, y, getWidth(), y);
        JLabelHelper.drawCenteredText(g2, "Imagem n\u00e3o encontrada", getWidth(), getHeight(),
                EstiloBase.fontePoppins(24f), new Color(255, 255, 255, 190));
    }

    private JPanel criarFaixaAcao() {
        JPanel faixa = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                ativarQualidadeLocal(g2);
                g2.setColor(COR_FAIXA_ACAO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);
                g2.setColor(new Color(255, 255, 255, 18));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);
                g2.dispose();
            }
        };
        faixa.setOpaque(false);
        return faixa;
    }

    private JButton criarBotaoAcaoObra(String texto, boolean primario) {
        JButton botao = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                ativarQualidadeLocal(g2);
                g2.setColor(COR_FAIXA_ACAO);
                g2.fillRect(0, 0, getWidth(), getHeight());
                boolean hover = getModel().isRollover() || getModel().isPressed();
                int arco = 28;
                if (primario) {
                    Color inicio = hover ? EstiloBase.COR_DESTAQUE_HOVER : EstiloBase.COR_DESTAQUE;
                    Color fim    = hover ? new Color(255, 204, 28) : EstiloBase.COR_DESTAQUE_2;
                    g2.setPaint(new GradientPaint(0, 0, inicio, getWidth(), getHeight(), fim));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), arco, arco);
                    g2.setColor(new Color(255, 255, 255, 58));
                } else {
                    g2.setColor(hover ? new Color(30, 23, 25) : new Color(16, 12, 14));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), arco, arco);
                    g2.setPaint(new GradientPaint(
                            0, 0, hover ? EstiloBase.COR_DESTAQUE : EstiloBase.COR_CARD_BORDA,
                            getWidth(), getHeight(), hover ? EstiloBase.COR_DESTAQUE_2 : EstiloBase.COR_CARD_GLOW));
                }
                g2.setStroke(new BasicStroke(2f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arco, arco);
                Font fonte = ajustarFonteAoBotao(g2, getFont(), texto, getWidth() - 26);
                g2.setFont(fonte);
                g2.setColor(primario || hover ? EstiloBase.COR_TEXTO_PRIMARIO : EstiloBase.COR_TEXTO_SECUNDARIO);
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(texto)) / 2;
                int ty = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2.drawString(texto, tx, ty);
                g2.dispose();
            }
        };
        botao.setBorderPainted(false);
        botao.setContentAreaFilled(false);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBackground(COR_FAIXA_ACAO);
        botao.setBorder(BorderFactory.createEmptyBorder());
        botao.setMargin(new Insets(0, 0, 0, 0));
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private Font ajustarFonteAoBotao(Graphics2D g2, Font fonteBase, String texto, int larguraMaxima) {
        Font fonte = fonteBase;
        FontMetrics fm = g2.getFontMetrics(fonte);
        while (fm.stringWidth(texto) > larguraMaxima && fonte.getSize2D() > 12f) {
            fonte = fonte.deriveFont(fonte.getSize2D() - 1f);
            fm = g2.getFontMetrics(fonte);
        }
        return fonte;
    }

    private void ativarQualidadeLocal(Graphics2D g2) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }

    private JPanel criarBarraProgresso(int largura) {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int total  = controle.getTotalObras();
                int gapSeg = 8;
                int segW   = (largura - ((total - 1) * gapSeg)) / total;
                for (int i = 0; i < total; i++) {
                    int x = i * (segW + gapSeg);
                    g2.setColor(new Color(255, 255, 255, 18));
                    g2.fillRoundRect(x, 4, segW, 10, 10, 10);
                    if (i <= indice) {
                        GradientPaint gp = new GradientPaint(x, 0, EstiloBase.COR_DESTAQUE, x + segW, 14, EstiloBase.COR_DESTAQUE_2);
                        g2.setPaint(gp);
                        g2.fillRoundRect(x, 4, segW, 10, 10, 10);
                    }
                }
                g2.dispose();
            }
        };
    }

    private static class JLabelHelper {
        static void drawCenteredText(Graphics2D g2, String texto, int largura, int altura, Font fonte, Color cor) {
            Font fa = g2.getFont(); Color ca = g2.getColor();
            g2.setFont(fonte); g2.setColor(cor);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(texto, (largura - fm.stringWidth(texto)) / 2, (altura - fm.getHeight()) / 2 + fm.getAscent());
            g2.setFont(fa); g2.setColor(ca);
        }
    }
}
