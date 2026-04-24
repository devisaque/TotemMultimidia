package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata base — declara e inicializa TODAS as variáveis do sistema.
 * Chama Executar() automaticamente após a inicialização (padrão Template Method).
 */
public abstract class absPropriedades implements intMetodos {

    // ── Dados do Visitante ─────────────────────────────────────────────────
    protected String nomeVisitante;
    protected int    idadeVisitante;

    // ── Controle de Fluxo ──────────────────────────────────────────────────
    protected int     etapaAtual;
    protected int     obraAtual;
    protected boolean miniGameConcluido;

    // ── Obras (10 rovers/missões marcianas) ────────────────────────────────
    protected String[]  titulosObras;
    protected String[]  descricoesObras;
    protected boolean[] exibirModelo3D;   // true = exibe botão 3D (a partir da obra 3)

    // ── Mini-Games ─────────────────────────────────────────────────────────
    protected int[] posicoesMinigame;    // índices: 1, 4, 8

    // ── Questionário ───────────────────────────────────────────────────────
    protected String[]   perguntas;
    protected String[][] opcoes;         // opcoes[pergunta][opcao]
    protected int[]      gabaritos;      // índice da resposta correta por pergunta
    protected int[]      respostasVisitante; // -1 = não respondida

    // ── Satisfação ─────────────────────────────────────────────────────────
    protected int notaSatisfacao;        // -1 = não avaliado

    // ── Histórico in-memory (sem banco de dados) ───────────────────────────
    protected List<String>  historicoNomes;
    protected List<Integer> historicoIdades;
    protected List<Integer> historicoPontuacoes;
    protected List<Integer> historicoSatisfacoes;

    // ── Construtor — garante ordem: variáveis ANTES de Executar() ──────────
    public absPropriedades() {
        inicializarVariaveis(); // passo 1
        Executar();             // passo 2 — Template Method
    }

    // ── Inicialização ──────────────────────────────────────────────────────
    private void inicializarVariaveis() {
        nomeVisitante      = "";
        idadeVisitante     = 0;
        etapaAtual         = 0;
        obraAtual          = 0;
        miniGameConcluido  = false;
        notaSatisfacao     = -1;
        respostasVisitante = new int[]{-1, -1, -1, -1, -1};

        historicoNomes       = new ArrayList<>();
        historicoIdades      = new ArrayList<>();
        historicoPontuacoes  = new ArrayList<>();
        historicoSatisfacoes = new ArrayList<>();

        inicializarObras();
        inicializarQuestionario();
        inicializarMinigames();
    }

    private void inicializarObras() {
        titulosObras = new String[]{
                "Mariner 4 \u2014 O Primeiro Olhar",
                "Viking 1 \u2014 Pouso Hist\u00f3rico",
                "Sojourner \u2014 O Pequeno Explorador",
                "Spirit \u2014 Esp\u00edrito de Aventura",
                "Opportunity \u2014 A Miss\u00e3o Eterna",
                "Phoenix \u2014 Das Cinzas de Marte",
                "Curiosity \u2014 A Ci\u00eancia em Movimento",
                "InSight \u2014 Ouvindo o Interior",
                "Perseverance \u2014 Perseverando no Desconhecido",
                "Ingenuity \u2014 O Primeiro Voo Marciano"
        };

        descricoesObras = new String[]{
                "Em 1965, a sonda Mariner 4 realizou o primeiro sobrevoo de Marte, capturando 22 fotografias " +
                        "que revelaram um planeta coberto de crateras e sem atmosfera densa, surpreendendo cientistas do mundo.",

                "O Viking 1 pousou em Marte em 20 de julho de 1976, sendo o primeiro objeto humano a pousar com sucesso " +
                        "no planeta. Seus instrumentos analisaram o solo em busca de sinais de vida microbiana.",

                "Lan\u00e7ado em 1996, o Sojourner foi o primeiro rover a explorar Marte. Com 63 cm e 11 kg, " +
                        "percorreu 100 metros em 83 dias, analisando rochas e expandindo nossa compreens\u00e3o da geologia marciana.",

                "Spirit chegou em 2004, previsto para 90 dias. Funcionou por mais de 6 anos, percorrendo 7 km " +
                        "e descobrindo evid\u00eancias concretas de que \u00e1gua l\u00edquida existiu no passado de Marte.",

                "Irm\u00e3o g\u00eameo do Spirit, o Opportunity operou por 15 anos percorrendo 45 km — um recorde absoluto. " +
                        "Descobriu minerais que s\u00f3 se formam na presen\u00e7a de \u00e1gua l\u00edquida.",

                "A sonda Phoenix (2008) pousou nas regi\u00f5es polares de Marte, confirmando a presen\u00e7a de gelo de " +
                        "\u00e1gua no solo e detectando percloratos que indicam condi\u00e7\u00f5es potencialmente habit\u00e1veis.",

                "O Curiosity (2011) \u00e9 um laborat\u00f3rio cient\u00edfico sobre rodas. Descobriu que a cratera Gale " +
                        "foi um lago com condi\u00e7\u00f5es prop\u00edcias \u00e0 vida microbiana e continua operando at\u00e9 hoje.",

                "A miss\u00e3o InSight (2018) detectou pela primeira vez abalos s\u00edsmicos marcianos — os 'marsquakes' — " +
                        "e mediu a espessura da crosta e do manto de Marte com sism\u00f3grafos ultra-sens\u00edveis.",

                "O Perseverance chegou em 2021 para buscar sinais de vida antiga, coletar amostras de rocha " +
                        "e testar o experimento MOXIE, que produziu oxig\u00eanio a partir da atmosfera marciana.",

                "O Ingenuity realizou em abril de 2021 o primeiro voo motorizado e controlado em outro planeta, " +
                        "voando 39 segundos a 3 metros de altitude \u2014 abrindo uma nova era da explora\u00e7\u00e3o a\u00e9rea marciana."
        };

        // Modelo 3D dispon\u00edvel a partir da obra de \u00edndice 2 (obra 3 para o visitante)
        exibirModelo3D = new boolean[]{
                false, false, true, true, true,
                true,  true,  true, true, true
        };
    }

    private void inicializarQuestionario() {
        perguntas = new String[]{
                "Qual foi o primeiro rover a explorar a superf\u00edcie de Marte?",
                "Qual miss\u00e3o confirmou a presen\u00e7a de gelo de \u00e1gua no solo marciano?",
                "Quantos quil\u00f4metros o rover Opportunity percorreu em Marte?",
                "Qual rover realizou o primeiro experimento de produ\u00e7\u00e3o de oxig\u00eanio em Marte?",
                "O que o helic\u00f3ptero Ingenuity realizou de hist\u00f3rico em Marte?"
        };

        opcoes = new String[][]{
                {"Viking 1", "Sojourner", "Spirit", "Curiosity"},
                {"Curiosity", "Viking 1", "Phoenix", "InSight"},
                {"10 km", "45 km", "100 km", "200 km"},
                {"Spirit", "Opportunity", "Curiosity", "Perseverance"},
                {"Primeiro pouso em Marte", "Primeiro voo motorizado em outro planeta",
                        "Primeira foto colorida", "Primeira coleta de amostras"}
        };

        gabaritos = new int[]{1, 2, 1, 3, 1};
    }

    private void inicializarMinigames() {
        posicoesMinigame = new int[]{1, 4, 8};
    }

    // ── Implementações padrão de intMetodos ────────────────────────────────

    @Override
    public void registrarResposta(int pergunta, int opcao) {
        if (pergunta >= 0 && pergunta < respostasVisitante.length
                && opcao >= 0 && opcao < opcoes[pergunta].length) {
            respostasVisitante[pergunta] = opcao;
        }
    }

    @Override
    public void registrarSatisfacao(int estrelas) {
        if (estrelas >= 0 && estrelas <= 5) notaSatisfacao = estrelas;
    }

    @Override
    public int calcularPontuacao() {
        int acertos = 0;
        for (int i = 0; i < gabaritos.length; i++) {
            if (respostasVisitante[i] == gabaritos[i]) acertos++;
        }
        return acertos;
    }

    @Override
    public void avancar() { etapaAtual++; }

    @Override
    public void voltar() { if (etapaAtual > 0) etapaAtual--; }

    // ── Getters e Setters ──────────────────────────────────────────────────
    public String    getNomeVisitante()          { return nomeVisitante; }
    public int       getIdadeVisitante()         { return idadeVisitante; }
    public int       getEtapaAtual()             { return etapaAtual; }
    public int       getObraAtual()              { return obraAtual; }
    public int       getNotaSatisfacao()         { return notaSatisfacao; }
    public String[]  getTitulosObras()           { return titulosObras; }
    public String[]  getDescricoesObras()        { return descricoesObras; }
    public boolean[] getExibirModelo3D()         { return exibirModelo3D; }
    public String[]  getPerguntas()              { return perguntas; }
    public String[][] getOpcoes()                { return opcoes; }
    public int[]     getGabaritos()              { return gabaritos; }
    public int[]     getRespostasVisitante()     { return respostasVisitante; }
    public int[]     getPosicoesMinigame()       { return posicoesMinigame; }
    public List<String>  getHistoricoNomes()     { return historicoNomes; }
    public List<Integer> getHistoricoIdades()    { return historicoIdades; }
    public List<Integer> getHistoricoPontuacoes(){ return historicoPontuacoes; }
    public List<Integer> getHistoricoSatisfacoes(){ return historicoSatisfacoes; }
    public void setNomeVisitante(String n)       { this.nomeVisitante = n; }
    public void setIdadeVisitante(int i)         { this.idadeVisitante = i; }
    public void setObraAtual(int i)              { this.obraAtual = i; }
}
