package apresentacao;

import modelo.Controle;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Tela inicial do totem — splash screen com título, subtítulo e botão Iniciar.
 * Otimizada para touchscreen; usa animação de pulso no botão.
 */
public class fmrInicio extends JDialog {

    private final Controle controle;
    private float alphaAnima = 0f;
    private Timer timerEntrada;
    private Timer timerPulso;
    private float pulso = 1.0f;
    private boolean pulsoSubindo = false;

    public fmrInicio(JFrame pai, Controle controle) {
        super(pai, true);
        this.controle = controle;
        EstiloBase.configurarDialogFullscreen(this);
        construirInterface();
        iniciarAnimacaoEntrada();
    }

    private void construirInterface() {
        JPanel painel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                // Fundo degradê espacial
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(5, 8, 20),
                        0, getHeight(), new Color(15, 5, 40)
                );
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                // Campo estelar
                EstiloBase.desenharEstrelas(g2, getWidth(), getHeight(), 42L);
                // Planeta Marte decorativo
                int cx = getWidth() - 220, cy = 180, r = 140;
                RadialGradientPaint marte = new RadialGradientPaint(
                        new Point(cx - 30, cy - 30), r,
                        new float[]{0f, 0.5f, 1f},
                        new Color[]{new Color(220, 100, 60), new Color(180, 60, 30), new Color(80, 20, 10)}
                );
                g2.setPaint(marte);
                g2.fillOval(cx - r, cy - r, r * 2, r * 2);
                g2.setColor(new Color(0, 0, 0, 80));
                g2.fillOval(cx - r + 40, cy - r + 20, r * 2, r * 2);
                g2.dispose();
            }
        };
        painel.setOpaque(false);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();

        // ── Logo / Ícone ──────────────────────────────────────────────────
        JLabel lblIcone = new JLabel("🤖", SwingConstants.CENTER);
        lblIcone.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 96));
        lblIcone.setBounds(tela.width / 2 - 80, 80, 160, 120);
        painel.add(lblIcone);

        // ── Título ────────────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel(
                "<html><div style='text-align:center;'>"
                        + "<span style='color:#FF7820;'>ROBÔS EXPLORADORES</span>"
                        + "</div></html>", SwingConstants.CENTER
        );
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 56));
        lblTitulo.setForeground(EstiloBase.COR_DESTAQUE);
        lblTitulo.setBounds(0, 210, tela.width, 80);
        painel.add(lblTitulo);

        // ── Subtítulo ─────────────────────────────────────────────────────
        JLabel lblSub = new JLabel(
                "Uma jornada pela conquista de Marte", SwingConstants.CENTER
        );
        lblSub.setFont(EstiloBase.FONTE_SUBTITULO);
        lblSub.setForeground(EstiloBase.COR_ACENTO);
        lblSub.setBounds(0, 305, tela.width, 40);
        painel.add(lblSub);

        // ── Linha decorativa ──────────────────────────────────────────────
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(40, 70, 140));
        sep.setBounds(tela.width / 2 - 300, 370, 600, 2);
        painel.add(sep);

        // ── Botão Iniciar ─────────────────────────────────────────────────
        JButton btnIniciar = EstiloBase.criarBotaoPrimario("  TOQUE PARA INICIAR  ");
        btnIniciar.setPreferredSize(new Dimension(420, 80));
        btnIniciar.setFont(new Font("Segoe UI", Font.BOLD, 26));
        int bx = tela.width / 2 - 210;
        btnIniciar.setBounds(bx, 430, 420, 80);
        btnIniciar.addActionListener(e -> {
            dispose();
            controle.exibirCadastro();
        });
        painel.add(btnIniciar);

        // ── Rodapé ────────────────────────────────────────────────────────
        JLabel lblRodape = new JLabel(
                "Museu de Ciência e Tecnologia  •  Toque na tela para começar",
                SwingConstants.CENTER
        );
        lblRodape.setFont(EstiloBase.FONTE_PEQUENA);
        lblRodape.setForeground(EstiloBase.COR_TEXTO_FRACO);
        lblRodape.setBounds(0, tela.height - 60, tela.width, 30);
        painel.add(lblRodape);

        // Animação de pulso no botão
        timerPulso = new Timer(40, e -> {
            pulso += pulsoSubindo ? 0.008f : -0.008f;
            if (pulso >= 1.06f) pulsoSubindo = false;
            if (pulso <= 0.94f) pulsoSubindo = true;
            int delta = (int) ((pulso - 1.0f) * 20);
            btnIniciar.setBounds(bx - delta / 2, 430 - delta / 2, 420 + delta, 80 + delta);
            painel.repaint();
        });
        timerPulso.start();

        setContentPane(painel);
    }

    private void iniciarAnimacaoEntrada() {
        setOpacity(0f);
        timerEntrada = new Timer(20, null);
        timerEntrada.addActionListener(e -> {
            alphaAnima = Math.min(1f, alphaAnima + 0.04f);
            setOpacity(alphaAnima);
            if (alphaAnima >= 1f) timerEntrada.stop();
        });
        timerEntrada.start();
    }

    @Override
    public void dispose() {
        if (timerEntrada != null) timerEntrada.stop();
        if (timerPulso  != null) timerPulso.stop();
        super.dispose();
    }
}