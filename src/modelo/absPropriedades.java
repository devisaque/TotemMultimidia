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
    protected String sobrenomeVisitante;
    protected String faixaEtariaVisitante;
    protected String[] dadosVisitante;
    protected int    idadeVisitante;

    // ── Controle de Fluxo ──────────────────────────────────────────────────
    protected int     etapaAtual;
    protected int     obraAtual;

    // ── Obras (10 rovers/missões marcianas) ────────────────────────────────
    protected String[]  titulosObras;
    protected String[]  descricoesObras;
    protected String[]  codigosObras;
    protected String[]  anosObras;
    protected String[]  imagensObras;
    // exibirModelo3D removido — funcionalidade de modelo 3D eliminada na Etapa 1

    // ── Questionário ───────────────────────────────────────────────────────
    protected String[]   perguntas;
    protected String[][] opcoes;         // opcoes[pergunta][opcao]
    protected int[]      gabaritos;      // índice da resposta correta por pergunta
    protected int[]      respostasVisitante; // -1 = não respondida

    // ── Satisfação ─────────────────────────────────────────────────────────
    protected int notaSatisfacao;        // -1 = não avaliado

    // ── Histórico in-memory (sem banco de dados) ───────────────────────────
    protected List<String>  historicoNomes;
    protected List<String>  historicoSobrenomes;
    protected List<String>  historicoFaixasEtarias;
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
        nomeVisitante         = "";
        sobrenomeVisitante    = "";
        faixaEtariaVisitante  = "";
        dadosVisitante        = new String[]{"", "", ""};
        idadeVisitante        = 0;
        etapaAtual            = 0;
        obraAtual             = 0;
        notaSatisfacao        = -1;
        respostasVisitante    = new int[]{-1, -1, -1, -1, -1};

        historicoNomes        = new ArrayList<>();
        historicoSobrenomes   = new ArrayList<>();
        historicoFaixasEtarias = new ArrayList<>();
        historicoIdades       = new ArrayList<>();
        historicoPontuacoes   = new ArrayList<>();
        historicoSatisfacoes  = new ArrayList<>();

        inicializarObras();
        inicializarQuestionario();
    }

    private void inicializarObras() {
        titulosObras = new String[]{
                "Obra 1 – PrOP-M (Mars 2, URSS, 1971)",
                "Obra 2 – PrOP-M (Mars 3, URSS, 1971)",
                "Obra 3 – Sojourner (Mars Pathfinder, NASA, 1997)",
                "Obra 4 – Spirit (Mars Exploration Rover A, NASA, 2004)",
                "Obra 5 – Opportunity (Mars Exploration Rover B, NASA, 2004–2018)",
                "Obra 6 – Curiosity (Mars Science Laboratory, NASA, 2012–)",
                "Obra 7 – Perseverance (Mars 2020, NASA, 2021–)",
                "Obra 8 – Ingenuity (helicóptero de Marte, NASA, 2021)",
                "Obra 9 – Zhurong (Tianwen-1, China, 2021)",
                "Obra 10 – Rosalind Franklin (ExoMars, ESA/NASA, lançamento previsto 2028)"
        };

        descricoesObras = new String[]{
                "O PrOP-M foi um pequeno rob\u00f4 sovi\u00e9tico desenvolvido para ser o primeiro rover a operar na superf\u00edcie de Marte, embarcado na miss\u00e3o Mars 2 em 1971. A sonda Mars 2 conseguiu entrar em \u00f3rbita marciana, mas o m\u00f3dulo de pouso entrou na atmosfera em um \u00e2ngulo muito \u00edngreme, o sistema de descida falhou e o lander se chocou contra o solo, destruindo todo o equipamento, incluindo o rover PrOP-M, que nunca foi ativado na superf\u00edcie.\n\n" +
                        "O PrOP-M tinha cerca de 15 kg, formato de caixa met\u00e1lica montada sobre dois esquis, em vez de rodas, e era preso ao lander por um cabo de aproximadamente 15 m, pensado para limitar a dist\u00e2ncia e facilitar as comunica\u00e7\u00f5es. Por causa do grande atraso de sinal entre Terra e Marte, o rover foi projetado para se movimentar de forma aut\u00f4noma, usando hastes frontais para detectar obst\u00e1culos e mudar de dire\u00e7\u00e3o sem comando direto humano. Ele carregava sensores como um densit\u00f4metro e um penetr\u00f4metro para medir densidade e resist\u00eancia mec\u00e2nica do solo, fornecendo dados importantes sobre a trafegabilidade marciana, ainda que, na pr\u00e1tica, nunca tenha operado devido ao fracasso do pouso.",

                "Um segundo rover id\u00eantico PrOP-M viajou na miss\u00e3o Mars 3, lan\u00e7ada tamb\u00e9m em 1971, como parte do mesmo programa sovi\u00e9tico. Diferentemente da Mars 2, o lander Mars 3 conseguiu realizar o primeiro pouso suave bem-sucedido na hist\u00f3ria em Marte, em 2 de dezembro de 1971. No entanto, ap\u00f3s cerca de 14 a 20 segundos de transmiss\u00e3o de dados, o contato com o m\u00f3dulo de pouso foi perdido, provavelmente devido a uma tempestade de poeira global que envolvia o planeta naquele per\u00edodo.\n\n" +
                        "O plano da miss\u00e3o previa que o PrOP-M descesse por rampas do lander e se movesse em pequenos saltos de aproximadamente 1,5 m, usando suas hastes met\u00e1licas para sentir o terreno \u00e0 frente e evitar obst\u00e1culos de forma aut\u00f4noma. Como a comunica\u00e7\u00e3o foi interrompida quase imediatamente ap\u00f3s o pouso, o rover provavelmente nunca chegou a deixar a plataforma, transformando-se em um exemplo de tecnologia pronta, mas silenciada pelas condi\u00e7\u00f5es extremas de Marte.",

                "O Sojourner foi o primeiro rover de fato a operar na superf\u00edcie de Marte, parte da miss\u00e3o Mars Pathfinder, lan\u00e7ada em dezembro de 1996 e pousada em 4 de julho de 1997 na regi\u00e3o de Ares Vallis, em Chryse Planitia. A miss\u00e3o teve grande impacto p\u00fablico e cient\u00edfico, demonstrando o conceito mais r\u00e1pido, melhor e mais barato da NASA, com um lander experimental e um pequeno ve\u00edculo m\u00f3vel realizando ci\u00eancia de qualidade em Marte.\n\n" +
                        "Com cerca de 11,5 kg, o Sojourner usava pain\u00e9is solares para gerar aproximadamente 13 W de pot\u00eancia e foi projetado para uma miss\u00e3o prim\u00e1ria de 7 dias, com expectativa de at\u00e9 30 dias, mas acabou operando por cerca de 83 dias de deslocamento, totalizando 92 sols de atividade do conjunto lander + rover. Ele possu\u00eda c\u00e2meras e o espectr\u00f4metro Alpha Proton X-Ray, permitindo analisar a composi\u00e7\u00e3o qu\u00edmica de rochas e solos ao redor do lander.",

                "A Spirit foi uma das duas sondas g\u00eameas do programa Mars Exploration Rover, tamb\u00e9m conhecida como MER-A, lan\u00e7ada em 2003 e pousada em janeiro de 2004 na cratera Gusev, local escolhido por ind\u00edcios de antigos processos ligados \u00e0 \u00e1gua. A miss\u00e3o prim\u00e1ria era de apenas 90 sols, mas a Spirit operou at\u00e9 2010, totalizando mais de 2200 sols e superando em mais de vinte vezes a vida \u00fatil planejada.\n\n" +
                        "Ao longo de aproximadamente 7,7 km percorridos, a Spirit investigou rochas vulc\u00e2nicas, dep\u00f3sitos alterados por fluidos e materiais ricos em s\u00edlica que indicam intera\u00e7\u00e3o \u00e1gua-rocha, contribuindo para reconstruir a hist\u00f3ria geol\u00f3gica de Gusev. Em 2009, o rover ficou preso em uma \u00e1rea de solo muito fofo, apelidada de Troy, tornando o epis\u00f3dio de atolamento um s\u00edmbolo de explora\u00e7\u00e3o at\u00e9 o limite.",

                "A Opportunity, ou MER-B, pousou em Marte cerca de tr\u00eas semanas ap\u00f3s a Spirit, em janeiro de 2004, na regi\u00e3o de Meridiani Planum, uma plan\u00edcie rica em hematita, mineral geralmente associado \u00e0 presen\u00e7a de \u00e1gua. Assim como sua g\u00eamea, foi projetada para 90 sols, mas permaneceu ativa at\u00e9 junho de 2018, quando uma tempestade global de poeira encobriu o planeta, bloqueando a luz solar e levando \u00e0 perda definitiva de contato.\n\n" +
                        "Durante quase 15 anos, a Opportunity percorreu mais de 45 km, um recorde para rovers em outro corpo celeste, explorando diversas crateras como Endurance, Victoria e Endeavour. Em Endeavour, encontrou veios de gesso e outras evid\u00eancias de ambientes de \u00e1gua com pH mais neutro, considerados mais amig\u00e1veis \u00e0 vida do que ambientes extremamente \u00e1cidos.",

                "O Curiosity \u00e9 o rover da miss\u00e3o Mars Science Laboratory, lan\u00e7ado em 2011 e pousado com a complexa manobra de sky crane em agosto de 2012 na cratera Gale. Na \u00e9poca do pouso, era o maior e mais sofisticado rover j\u00e1 enviado a Marte, pesando cerca de 900 kg e equipado com dez instrumentos cient\u00edficos, incluindo a c\u00e2mera-laser ChemCam, uma perfuradora de rochas, laborat\u00f3rios qu\u00edmicos internos e uma esta\u00e7\u00e3o meteorol\u00f3gica.\n\n" +
                        "O objetivo central da miss\u00e3o \u00e9 investigar se a regi\u00e3o de Gale j\u00e1 ofereceu condi\u00e7\u00f5es habit\u00e1veis para vida microbiana no passado, procurando sinais de \u00e1gua l\u00edquida est\u00e1vel, fontes de energia e elementos qu\u00edmicos essenciais. Estudos de rochas sedimentares no leito de antigos lagos e nas encostas do Monte Sharp mostraram camadas de lama solidificada, minerais de argila e sulfatos.",

                "O Perseverance \u00e9 o rover da miss\u00e3o Mars 2020, lan\u00e7ado em julho de 2020 e pousado na cratera Jezero em 18 de fevereiro de 2021. A cratera foi escolhida por abrigar um antigo delta de rio e um poss\u00edvel lago, considerados um dos locais mais promissores em Marte para a busca de sinais de vida passada.\n\n" +
                        "Entre os objetivos principais do Perseverance est\u00e3o procurar evid\u00eancias de vida microbiana antiga, caracterizar a geologia e o clima de Marte e realizar a coleta e o armazenamento de amostras de rocha e solo em tubos met\u00e1licos selados, para serem trazidos \u00e0 Terra por futuras miss\u00f5es de retorno de amostras. O rover possui sete instrumentos cient\u00edficos principais, 19 c\u00e2meras e dois microfones, al\u00e9m do experimento MOXIE.",

                "O Ingenuity \u00e9 um pequeno helic\u00f3ptero de aproximadamente 1,8 kg levado preso sob o Perseverance como um demonstrador tecnol\u00f3gico para testar o primeiro voo motorizado controlado em outro planeta. Ap\u00f3s ser depositado na superf\u00edcie marciana, em Jezero, realizou seu primeiro voo em 19 de abril de 2021, subindo cerca de 3 metros, pairando e pousando, marcando um momento hist\u00f3rico na explora\u00e7\u00e3o espacial.\n\n" +
                        "Inicialmente planejado para apenas cinco voos em cerca de 30 dias, o Ingenuity continuou operando por quase tr\u00eas anos, realizando 72 voos at\u00e9 janeiro de 2024, atuando como um batedor a\u00e9reo para o Perseverance. Seus voos demonstraram que aeronaves leves podem operar na atmosfera rarefeita de Marte.",

                "O Zhurong \u00e9 o primeiro rover marciano da China e parte da miss\u00e3o Tianwen-1, que inclui orbitador, m\u00f3dulo de descida e rover. Lan\u00e7ada em julho de 2020, a miss\u00e3o entrou em \u00f3rbita em fevereiro de 2021, e o m\u00f3dulo de descida pousou com sucesso em Utopia Planitia em 14 de maio de 2021, tornando a China o segundo pa\u00eds a operar um rover em Marte.\n\n" +
                        "Com cerca de 240 kg, seis rodas e alimenta\u00e7\u00e3o por pain\u00e9is solares, o Zhurong leva c\u00e2meras, radar de penetra\u00e7\u00e3o no solo, espectr\u00f4metros e instrumentos para estudar a estrutura do subsolo, a presen\u00e7a de gelo de \u00e1gua, a mineralogia e o ambiente local. O rover foi projetado para 90 sols, mas operou por mais de 347 sols e percorreu quase 2 km at\u00e9 entrar em hiberna\u00e7\u00e3o em maio de 2022.",

                "A d\u00e9cima obra corresponde ao rover Rosalind Franklin, da miss\u00e3o ExoMars, liderada pela Ag\u00eancia Espacial Europeia com suporte da NASA. Esse rover representa o pr\u00f3ximo grande passo da explora\u00e7\u00e3o marciana: a busca sistem\u00e1tica por sinais de vida no subsolo de Marte, combinando mobilidade com capacidade avan\u00e7ada de perfura\u00e7\u00e3o e an\u00e1lise de amostras.\n\n" +
                        "O Rosalind Franklin tem lan\u00e7amento previsto para 2028 em um foguete Falcon Heavy, com pouso estimado para 2030 em Oxia Planum, uma regi\u00e3o que registra uma longa hist\u00f3ria de intera\u00e7\u00e3o com \u00e1gua e dep\u00f3sitos sedimentares antigos. Ele ser\u00e1 o primeiro rover em Marte capaz de perfurar at\u00e9 cerca de 2 m de profundidade, coletando amostras protegidas da radia\u00e7\u00e3o e da oxida\u00e7\u00e3o da superf\u00edcie."
        };

        codigosObras = new String[]{
                "PM-02",
                "PM-03",
                "SJ-97",
                "SP-04",
                "OP-18",
                "CU-12",
                "PV-21",
                "IG-21",
                "ZH-21",
                "RF-28"
        };

        anosObras = new String[]{
                "1971", "1971", "1997", "2004", "2004\u20132018",
                "2012\u2013", "2021\u2013", "2021", "2021", "2028"
        };

        imagensObras = new String[]{
                "/assets/obras/obra1-prop-m-mars2.jpeg",
                "/assets/obras/obra2-prop-m-mars3.jpeg",
                "/assets/obras/obra3-sojourner.jpeg",
                "/assets/obras/obra4-spirit.jpeg",
                "/assets/obras/obra5-opportunity.jpeg",
                "/assets/obras/obra6-curiosity.jpeg",
                "/assets/obras/obra7-perseverance.jpeg",
                "/assets/obras/obra8-ingenuity.jpeg",
                "/assets/obras/obra9-zhurong.jpeg",
                "/assets/obras/obra10-rosalind-franklin.jpeg"
        };
        // exibirModelo3D removido — botão 3D eliminado na Etapa 1
    }

    private void inicializarQuestionario() {
        perguntas = new String[]{
                "Qual foi o primeiro rover que realmente funcionou na superf\u00edcie de Marte?",
                "Qual rover ficou famoso por voar em Marte?",
                "Qual rover da NASA pousou na cratera Gale em 2012?",
                "Qual rover chin\u00eas fez parte da miss\u00e3o Tianwen-1?",
                "Qual rover europeu da miss\u00e3o ExoMars tem lan\u00e7amento previsto para 2028?"
        };

        opcoes = new String[][]{
                {"PrOP-M", "Sojourner", "Curiosity", "Zhurong"},
                {"Opportunity", "Ingenuity", "Spirit", "Rosalind Franklin"},
                {"Curiosity", "Sojourner", "PrOP-M", "Zhurong"},
                {"Spirit", "Perseverance", "Zhurong", "Opportunity"},
                {"Sojourner", "Curiosity", "Rosalind Franklin", "Ingenuity"}
        };

        gabaritos = new int[]{1, 1, 0, 2, 2};
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
    public String    getNomeVisitante()           { return nomeVisitante; }
    public String    getSobrenomeVisitante()      { return sobrenomeVisitante; }
    public String    getFaixaEtariaVisitante()    { return faixaEtariaVisitante; }
    public String    getNomeCompletoVisitante()   { return (nomeVisitante + " " + sobrenomeVisitante).trim(); }
    public String[]  getDadosVisitante()          { return dadosVisitante; }
    public int       getIdadeVisitante()          { return idadeVisitante; }
    public int       getEtapaAtual()              { return etapaAtual; }
    public int       getObraAtual()               { return obraAtual; }
    public int       getNotaSatisfacao()          { return notaSatisfacao; }
    public String[]  getTitulosObras()            { return titulosObras; }
    public String[]  getDescricoesObras()         { return descricoesObras; }
    public String[]  getCodigosObras()            { return codigosObras; }
    public String[]  getAnosObras()               { return anosObras; }
    public String[]  getImagensObras()            { return imagensObras; }
    // getExibirModelo3D() removido — funcionalidade de modelo 3D eliminada na Etapa 1
    public String[]  getPerguntas()               { return perguntas; }
    public String[][] getOpcoes()                 { return opcoes; }
    public int[]     getGabaritos()               { return gabaritos; }
    public int[]     getRespostasVisitante()      { return respostasVisitante; }
    public List<String>  getHistoricoNomes()      { return historicoNomes; }
    public List<String>  getHistoricoSobrenomes() { return historicoSobrenomes; }
    public List<String>  getHistoricoFaixasEtarias() { return historicoFaixasEtarias; }
    public List<Integer> getHistoricoIdades()     { return historicoIdades; }
    public List<Integer> getHistoricoPontuacoes() { return historicoPontuacoes; }
    public List<Integer> getHistoricoSatisfacoes() { return historicoSatisfacoes; }
    public void setNomeVisitante(String n)        { this.nomeVisitante = n; }
    public void setSobrenomeVisitante(String s)   { this.sobrenomeVisitante = s; }
    public void setFaixaEtariaVisitante(String f) { this.faixaEtariaVisitante = f; }
    public void setIdadeVisitante(int i)          { this.idadeVisitante = i; }
    public void setObraAtual(int i)               { this.obraAtual = i; }
}
