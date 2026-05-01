package apresentacao;

import modelo.Controle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

/**
 * Tela de obra com composicao editorial:
 * arte em destaque na esquerda e descricao na direita.
 */
public class fmrObra extends JDialog {

    private static final String[] ANOS = {
            "1965", "1976", "1997", "2004", "2004",
            "2008", "2011", "2018", "2021", "2021"
    };

    private static final String[] CODIGOS = {
            "MR-04", "VK-01", "SJ-01", "SP-01", "OP-02",
            "PX-08", "CU-11", "IN-18", "PV-21", "IG-21"
    };

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

        JLabel lblColecao = EstiloBase.criarTag("Colecao Marte");
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

        JPanel cardArte = EstiloBase.criarCard();
        cardArte.setLayout(null);
        cardArte.setBounds(margem, conteudoY, arteW, arteH);
        fundo.add(cardArte);

        JPanel painelArte = criarPainelArte();
        painelArte.setBounds(20, 20, arteW - 40, arteH - 132);
        cardArte.add(painelArte);

        JLabel lblCodigo = EstiloBase.criarTag(CODIGOS[indice]);
        lblCodigo.setBounds(24, arteH - 98, 92, 32);
        cardArte.add(lblCodigo);

        JLabel lblAno = EstiloBase.criarLabel(
                ANOS[indice],
                EstiloBase.fontePoppins(34f),
                EstiloBase.COR_TEXTO_PRIMARIO
        );
        lblAno.setHorizontalAlignment(SwingConstants.LEFT);
        lblAno.setBounds(24, arteH - 66, 130, 30);
        cardArte.add(lblAno);

        JLabel lblLegenda = EstiloBase.criarLabel(
                "Painel visual conceitual da missao",
                EstiloBase.FONTE_PEQUENA,
                EstiloBase.COR_TEXTO_FRACO
        );
        lblLegenda.setHorizontalAlignment(SwingConstants.LEFT);
        lblLegenda.setBounds(24, arteH - 38, arteW - 48, 18);
        cardArte.add(lblLegenda);

        JPanel cardInfo = EstiloBase.criarCard();
        cardInfo.setLayout(null);
        cardInfo.setBounds(margem + arteW + gap, conteudoY, painelW, conteudoH);
        fundo.add(cardInfo);

        JLabel lblTema = EstiloBase.criarTag("Narrativa da obra");
        lblTema.setBounds(30, 26, 160, 34);
        cardInfo.add(lblTema);

        JLabel lblTitulo = new JLabel("<html><div style='width:" + (painelW - 70) + "px'>"
                + controle.getTituloObra(indice) + "</div></html>");
        lblTitulo.setFont(EstiloBase.fontePoppins(38f));
        lblTitulo.setForeground(EstiloBase.COR_TEXTO_PRIMARIO);
        lblTitulo.setBounds(30, 72, painelW - 60, 110);
        cardInfo.add(lblTitulo);

        JLabel lblSub = new JLabel("<html><div style='width:" + (painelW - 70) + "px'>"
                + "A exposicao apresenta marcos da exploracao marciana em uma leitura mais imersiva, com "
                + "tipografia forte, contraste alto e foco no conteudo.</div></html>");
        lblSub.setFont(EstiloBase.FONTE_CORPO.deriveFont(18f));
        lblSub.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblSub.setBounds(30, 182, painelW - 60, 68);
        cardInfo.add(lblSub);

        JLabel lblChipAno = EstiloBase.criarTag("ANO " + ANOS[indice]);
        lblChipAno.setBounds(30, 258, 110, 32);
        cardInfo.add(lblChipAno);

        JLabel lblChipTipo = EstiloBase.criarTag(controle.deveExibirModelo3D(indice) ? "COM EXPERIENCIA 3D" : "TEXTO CURATORIAL");
        lblChipTipo.setBounds(150, 258, 176, 32);
        cardInfo.add(lblChipTipo);

        JTextArea txtDesc = new JTextArea(controle.getDescricaoObra(indice));
        txtDesc.setFont(EstiloBase.fonteInter(19f));
        txtDesc.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        txtDesc.setBackground(new Color(0, 0, 0, 0));
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setEditable(false);
        txtDesc.setOpaque(false);
        txtDesc.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        JScrollPane scroll = EstiloBase.criarScrollPane(txtDesc);
        scroll.setBounds(30, 314, painelW - 60, Math.max(220, conteudoH - 470));
        cardInfo.add(scroll);

        JPanel faixaAcao = new JPanel(null) {
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
        faixaAcao.setOpaque(false);
        faixaAcao.setBounds(30, conteudoH - 148, painelW - 60, 102);
        cardInfo.add(faixaAcao);

        JLabel lblNota = EstiloBase.criarLabel(
                "Continue navegando pela linha do tempo das missoes para descobrir novas camadas da exploracao de Marte.",
                EstiloBase.FONTE_PEQUENA.deriveFont(15f),
                EstiloBase.COR_TEXTO_SECUNDARIO
        );
        lblNota.setHorizontalAlignment(SwingConstants.LEFT);
        lblNota.setBounds(22, 16, faixaAcao.getWidth() - 44, 22);
        faixaAcao.add(lblNota);

        if (controle.deveExibirModelo3D(indice)) {
            JButton btn3D = EstiloBase.criarBotaoSecundario("Explorar modelo 3D");
            btn3D.setBounds(22, 44, 230, 44);
            btn3D.addActionListener(e -> abrirModelo3D());
            faixaAcao.add(btn3D);
        }

        JButton btnProximo = EstiloBase.criarBotaoPrimario(indice == controle.getTotalObras() - 1
                ? "Ir para o questionario"
                : "Proxima obra");
        btnProximo.setBounds(faixaAcao.getWidth() - 250, 40, 228, 48);
        btnProximo.addActionListener(e -> {
            dispose();
            controle.proximaEtapaAposObra(indice);
        });
        faixaAcao.add(btnProximo);

        setContentPane(fundo);
    }

    private JPanel criarPainelArte() {
        String[] palavras = controle.getTituloObra(indice).split(" ");
        String destaque = palavras.length > 0 ? palavras[0].toUpperCase() : "MARTE";

        JPanel painel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setColor(new Color(10, 10, 14));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

                GradientPaint fundo = new GradientPaint(
                        0, 0, new Color(18, 11, 13),
                        getWidth(), getHeight(), new Color(10, 10, 14)
                );
                g2.setPaint(fundo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

                float raioPlaneta = Math.min(getWidth(), getHeight()) * 0.29f;
                float orbita = Math.min(getWidth(), getHeight()) * 0.37f;
                float centroX = (float) (getWidth() * 0.55);
                float centroY = (float) (getHeight() * 0.42);

                desenharOrb(g2, centroX, centroY, raioPlaneta, EstiloBase.COR_DESTAQUE, EstiloBase.COR_DESTAQUE_2);
                desenharOrb(g2, getWidth() * 0.18f, getHeight() * 0.74f, raioPlaneta * 0.35f, EstiloBase.COR_ACENTO, EstiloBase.COR_DESTAQUE);

                g2.setStroke(new BasicStroke(1.25f));
                g2.setColor(new Color(255, 255, 255, 28));
                g2.draw(new Ellipse2D.Float(centroX - orbita, centroY - (orbita * 0.55f), orbita * 2, orbita * 1.10f));
                g2.draw(new Ellipse2D.Float(centroX - (orbita * 0.78f), centroY - (orbita * 0.36f), orbita * 1.56f, orbita * 0.72f));

                g2.setColor(new Color(255, 255, 255, 12));
                for (int x = 24; x < getWidth(); x += 26) {
                    g2.drawLine(x, 0, x, getHeight());
                }
                for (int y = 24; y < getHeight(); y += 26) {
                    g2.drawLine(0, y, getWidth(), y);
                }

                g2.setColor(new Color(255, 255, 255, 22));
                g2.draw(new RoundRectangle2D.Float(16, 16, getWidth() - 32, getHeight() - 32, 20, 20));
                g2.dispose();
            }
        };
        painel.setOpaque(false);

        JLabel lblPalavra = new JLabel(destaque);
        lblPalavra.setFont(EstiloBase.fontePoppins(42f));
        lblPalavra.setForeground(new Color(255, 255, 255, 196));
        lblPalavra.setBounds(26, 26, 260, 48);
        painel.add(lblPalavra);

        JLabel lblCodigo = new JLabel("MUSEU INTERATIVO");
        lblCodigo.setFont(EstiloBase.FONTE_LABEL.deriveFont(12f));
        lblCodigo.setForeground(EstiloBase.COR_TEXTO_FRACO);
        lblCodigo.setBounds(28, 74, 200, 20);
        painel.add(lblCodigo);

        JLabel lblRodape = new JLabel("Mars archive " + CODIGOS[indice]);
        lblRodape.setFont(EstiloBase.FONTE_PEQUENA);
        lblRodape.setForeground(EstiloBase.COR_TEXTO_SECUNDARIO);
        lblRodape.setBounds(26, painel.getHeight() - 40, 200, 18);
        painel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                lblRodape.setBounds(26, painel.getHeight() - 40, 240, 18);
            }
        });
        painel.add(lblRodape);

        return painel;
    }

    private void abrirModelo3D() {
        EstiloBase.mostrarDialogoInformativo(
                this,
                "3D",
                "Visualizacao do rover",
                "A integracao real com um visualizador 3D ainda e um placeholder, mas a interface ja foi reposicionada "
                        + "para receber essa experiencia sem quebrar o fluxo principal do totem.",
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

    private void desenharOrb(Graphics2D g2, float x, float y, float raio, Color interna, Color externa) {
        RadialGradientPaint paint = new RadialGradientPaint(
                new Point((int) x, (int) y),
                raio,
                new float[]{0f, 0.55f, 1f},
                new Color[]{
                        new Color(interna.getRed(), interna.getGreen(), interna.getBlue(), 240),
                        new Color(externa.getRed(), externa.getGreen(), externa.getBlue(), 180),
                        new Color(externa.getRed(), externa.getGreen(), externa.getBlue(), 12)
                }
        );
        g2.setPaint(paint);
        g2.fill(new Ellipse2D.Float(x - raio, y - raio, raio * 2, raio * 2));
    }
}
