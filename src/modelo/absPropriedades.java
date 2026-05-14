package modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstrata base — declara e inicializa TODAS as variáveis do sistema.
 * Chama Executar() automaticamente após a inicialização (padrão Template Method).
 */
public abstract class absPropriedades implements intMetodos {

    // ── Dados do Visitante ────────────────────────────────────────────────────────────────────────────
    protected String nomeVisitante;
    protected String sobrenomeVisitante;
    protected String faixaEtariaVisitante;
    protected String[] dadosVisitante;
    protected int    idadeVisitante;

    // ── Controle de Fluxo ───────────────────────────────────────────────────────────────────────────
    protected int     etapaAtual;
    protected int     obraAtual;

    // ── Obras (10 rovers/missões marcianas) ──────────────────────────────────────────────────────────
    protected String[]  titulosObras;
    protected String[]  descricoesObras;
    protected String[]  codigosObras;
    protected String[]  anosObras;
    protected String[]  imagensObras;
    // exibirModelo3D removido — funcionalidade de modelo 3D eliminada na Etapa 1

    // ── Questionário ─────────────────────────────────────────────────────────────────────────────
    protected String[]   perguntas;
    protected String[][] opcoes;
    protected int[]      gabaritos;
    protected int[]      respostasVisitante;

    // ── Satisfação ───────────────────────────────────────────────────────────────────────────────
    protected int notaSatisfacao;

    // ── Histórico in-memory (sem banco de dados) ───────────────────────────────────────────────────────
    protected List<String>  historicoNomes;
    protected List<String>  historicoSobrenomes;
    protected List<String>  historicoFaixasEtarias;
    protected List<Integer> historicoIdades;
    protected List<Integer> historicoPontuacoes;
    protected List<Integer> historicoSatisfacoes;

    public absPropriedades() {
        inicializarVariaveis();
        Executar();
    }

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
                "O Rob\u00f4 Fantasma da Mars 2",
                "A Tempestade da Mars 3",
                "Sojourner \u2014 O Primeiro Explorador",
                "Spirit \u2014 Al\u00e9m do Esperado",
                "Opportunity \u2014 Uma Longa Jornada",
                "Curiosity \u2014 O Ge\u00f3logo Rob\u00f3tico",
                "Perseverance \u2014 Mensagens em Garrafas",
                "Ingenuity \u2014 O Primeiro Voo em Outro Mundo",
                "Zhurong \u2014 O Passo da China em Marte",
                "Rosalind Franklin \u2014 Em Busca do Invis\u00edvel"
        };

        descricoesObras = new String[]{

            // Obra 1 — O Robô Fantasma da Mars 2
            "<b>[ Miss\u00e3o: Mars 2 | Pa\u00eds: URSS | Ano: 1971 ]</b>"
            + "<p>A miss\u00e3o Mars 2 foi enviada pela Uni\u00e3o Sovi\u00e9tica em 1971 como parte de um grande esfor\u00e7o para explorar Marte "
            + "com orbitador, m\u00f3dulo de pouso e um pequeno rover chamado PrOP&#8209;11. O orbitador conseguiu entrar em \u00f3rbita do planeta, "
            + "mas o m\u00f3dulo de pouso entrou na atmosfera em um \u00e2ngulo muito \u00edngreme, o sistema de descida n\u00e3o funcionou como planejado "
            + "e a sonda acabou se chocando violentamente contra o solo marciano. Com o impacto, o m\u00f3dulo foi danificado de forma "
            + "irrevers\u00edvel e o PrOP&#8209;11, que viajava preso a ele, nunca chegou a ser ligado na superf\u00edcie.</p>"
            + "<p>O PrOP&#8209;11 era um rob\u00f4 de cerca de 15 kg, em formato de caixa met\u00e1lica montada sobre dois esquis, preso ao m\u00f3dulo de "
            + "pouso por um cabo de aproximadamente 15 metros. Ele havia sido projetado para se mover de maneira semiaut\u00f4noma: usaria "
            + "hastes mec\u00e2nicas para tatear o terreno, detectar pedras e buracos e decidir pequenos desvios de rota sozinho.</p>"
            + "<p><b>Curiosidades</b></p>"
            + "<ul>"
            + "<li>A Mars 2 foi o primeiro artefato humano a atingir a superf\u00edcie de Marte, mesmo que por um impacto n\u00e3o controlado.</li>"
            + "<li>O PrOP&#8209;11 \u00e9 lembrado como um dos primeiros rovers semiaut\u00f4nomos j\u00e1 constru\u00eddos.</li>"
            + "<li>Boa parte do que se sabe sobre o PrOP&#8209;11 vem de documenta\u00e7\u00e3o t\u00e9cnica e reconstru\u00e7\u00f5es hist\u00f3ricas.</li>"
            + "</ul>"
            + "<p><b>Sobre esta obra</b></p>"
            + "<p>O PrOP&#8209;11 aparece junto aos destro\u00e7os do m\u00f3dulo de pouso em um cen\u00e1rio de rochas e poeira avermelhada. "
            + "O \"rob\u00f4 fantasma\" simboliza tentativas corajosas que n\u00e3o deram certo, mas abriram caminho para miss\u00f5es "
            + "futuras aprenderem com os erros e aperfei\u00e7oarem t\u00e9cnicas de pouso.</p>",

            // Obra 2 — A Tempestade da Mars 3
            "<b>[ Miss\u00e3o: Mars 3 | Pa\u00eds: URSS | Ano: 1971 ]</b>"
            + "<p>A miss\u00e3o Mars 3 foi a g\u00eamea da Mars 2 e tamb\u00e9m levou um rover PrOP&#8209;11 preso ao seu m\u00f3dulo de pouso. Em 2 de "
            + "dezembro de 1971, a Mars 3 entrou para a hist\u00f3ria ao realizar o primeiro pouso suave bem&#8209;sucedido em Marte. "
            + "Logo ap\u00f3s tocar o solo, o m\u00f3dulo come\u00e7ou a transmitir dados para a Terra, mas a comunica\u00e7\u00e3o durou apenas "
            + "entre 14 e 20 segundos antes de ser interrompida definitivamente.</p>"
            + "<p>Na \u00e9poca, Marte passava por uma grande tempestade global de poeira, e muitos pesquisadores acreditam que essa "
            + "tempestade foi respons\u00e1vel pela perda s\u00fabita do sinal. O PrOP&#8209;11 tinha o mesmo conceito do seu irm\u00e3o da Mars 2: "
            + "um pequeno rob\u00f4 em forma de caixa com esquis, preso por cabo, pensado para saltar curtas dist\u00e2ncias e medir "
            + "propriedades f\u00edsicas do solo. Como a comunica\u00e7\u00e3o foi perdida quase imediatamente, entende&#8209;se que o rover "
            + "nunca chegou a descer pela rampa do m\u00f3dulo.</p>"
            + "<p><b>Curiosidades</b></p>"
            + "<ul>"
            + "<li>Apesar de ser vista como fracasso, a Mars 3 realizou o primeiro pouso suave bem-sucedido em Marte.</li>"
            + "<li>A tempestade de poeira serve at\u00e9 hoje como exemplo dos riscos ambientais do planeta para naves.</li>"
            + "<li>O PrOP&#8209;11 da Mars 3 \u00e9 s\u00edmbolo de tecnologia pronta, impedida pelos extremos do clima marciano.</li>"
            + "</ul>"
            + "<p><b>Sobre esta obra</b></p>"
            + "<p>A obra mostra o PrOP&#8209;11 ainda sobre a plataforma da Mars 3, enquanto o c\u00e9u ao redor escurece sob uma "
            + "gigantesca tempestade de poeira. A imagem fala sobre a fragilidade humana diante de um planeta duro, "
            + "mas tamb\u00e9m sobre a coragem de tentar inovar mesmo sabendo dos riscos.</p>",

            // Obra 3 — Sojourner
            "<b>[ Miss\u00e3o: Mars Pathfinder | Pa\u00eds: EUA/NASA | Ano: 1997 ]</b>"
            + "<p>A miss\u00e3o Mars Pathfinder foi lan\u00e7ada pela NASA em dezembro de 1996 com o objetivo de testar uma nova forma "
            + "de pousar em Marte usando airbags e demonstrar que um pequeno rob\u00f4 m\u00f3vel poderia fazer ci\u00eancia de qualidade "
            + "com baixo custo. Em 4 de julho de 1997, o m\u00f3dulo pousou em Ares Vallis, uma regi\u00e3o moldada por enchentes antigas.</p>"
            + "<p>O rover Sojourner, com cerca de 11 kg e seis rodas, foi o primeiro ve\u00edculo a deslocar&#8209;se com sucesso pela "
            + "superf\u00edcie de Marte. Ele levava c\u00e2meras e o espectr\u00f4metro Alpha Proton X&#8209;Ray (APXS), que analisava a "
            + "composi\u00e7\u00e3o qu\u00edmica de rochas e solos ao redor do lander. A miss\u00e3o foi planejada para poucos dias, mas o "
            + "conjunto lander + rover funcionou por cerca de tr\u00eas meses, enviando milhares de fotos e medi\u00e7\u00f5es.</p>"
            + "<p><b>Curiosidades</b></p>"
            + "<ul>"
            + "<li>O nome \"Sojourner\" homenageia Sojourner Truth, ativista contra a escravid\u00e3o e pelos direitos civis.</li>"
            + "<li>O uso de airbags para amortecer o pouso foi uma inova\u00e7\u00e3o marcante da miss\u00e3o.</li>"
            + "<li>As imagens do pequeno rob\u00f4 andando entre as rochas em Marte tiveram grande impacto na m\u00eddia.</li>"
            + "</ul>"
            + "<p><b>Sobre esta obra</b></p>"
            + "<p>A obra mostra o Sojourner como um pequeno explorador diante de um grande campo de rochas. Essa composi\u00e7\u00e3o "
            + "convida o visitante a pensar em como grandes avan\u00e7os cient\u00edficos podem come\u00e7ar com experimentos "
            + "modestos, mas bem planejados.</p>",

            // Obra 4 — Spirit
            "<b>[ Miss\u00e3o: MER-A | Pa\u00eds: EUA/NASA | Ano: 2004 ]</b>"
            + "<p>A Spirit foi um dos dois rovers da miss\u00e3o Mars Exploration Rover, lan\u00e7ados em 2003 para estudar diferentes "
            + "regi\u00f5es de Marte. Em janeiro de 2004, a Spirit pousou na cratera Gusev, escolhida por mostrar sinais de "
            + "poss\u00edvel antigo lago. A miss\u00e3o tinha plano inicial de 90 dias marcianos, mas a Spirit continuou operando "
            + "por mais de seis anos, at\u00e9 2010.</p>"
            + "<p>Durante esse tempo, a Spirit percorreu cerca de 7,7 km, examinando rochas vulc\u00e2nicas, solos e camadas de "
            + "materiais modificados por \u00e1gua. Encontrou dep\u00f3sitos ricos em s\u00edlica, que sugerem a atua\u00e7\u00e3o de fontes "
            + "termais ou \u00e1gua aquecida circulando pelo subsolo. Em 2009, a Spirit ficou presa em uma regi\u00e3o de solo "
            + "muito fofo, batizada de Troy, onde suas rodas afundaram e o rover n\u00e3o conseguiu sair.</p>"
            + "<p><b>Curiosidades</b></p>"
            + "<ul>"
            + "<li>A Spirit sobreviveu a tempestades de poeira e invernos marcianos durante anos.</li>"
            + "<li>O atolamento em Troy virou um caso cl\u00e1ssico de limite operacional de um rover em terreno desconhecido.</li>"
            + "<li>As Columbia Hills revelaram camadas de rochas formadas em diferentes ambientes ao longo da hist\u00f3ria de Marte.</li>"
            + "</ul>"
            + "<p><b>Sobre esta obra</b></p>"
            + "<p>Na obra, a Spirit aparece inclinada e presa em uma \u00e1rea de areia clara. A cena resume a trajet\u00f3ria da "
            + "miss\u00e3o: um rob\u00f4 que superou em muito o tempo previsto e, no fim, foi detido pelo pr\u00f3prio terreno "
            + "que tentava estudar.</p>",

            // Obra 5 — Opportunity
            "<b>[ Miss\u00e3o: MER-B | Pa\u00eds: EUA/NASA | Ano: 2004 ]</b>"
            + "<p>A Opportunity foi a segunda sonda da miss\u00e3o Mars Exploration Rover, pousando em janeiro de 2004 na "
            + "regi\u00e3o de Meridiani Planum, escolhida por apresentar min\u00e9rios de ferro como a hematita, frequentemente "
            + "associados \u00e0 presen\u00e7a de \u00e1gua em ambientes antigos. Tamb\u00e9m planejada para 90 dias, a Opportunity "
            + "continuou ativa at\u00e9 2018, operando por quase 15 anos.</p>"
            + "<p>Ao longo desse tempo, o rover percorreu mais de 45 km, explorando v\u00e1rias crateras como Endurance, "
            + "Victoria e Endeavour. Em 2018, uma grande tempestade global de poeira envolveu Marte, escurecendo o "
            + "c\u00e9u e impedindo que os pain\u00e9is solares recebessem luz suficiente, encerrando definitivamente a miss\u00e3o.</p>"
            + "<p><b>Curiosidades</b></p>"
            + "<ul>"
            + "<li>A Opportunity operou cerca de 57 vezes mais tempo do que o planejado originalmente.</li>"
            + "<li>A frase \"my battery is low and it's getting dark\" ficou famosa como met\u00e1fora do fim da miss\u00e3o.</li>"
            + "<li>A dist\u00e2ncia percorrida fez dela um dos ve\u00eddculos com maior quilometragem em outro corpo celeste.</li>"
            + "</ul>"
            + "<p><b>Sobre esta obra</b></p>"
            + "<p>Na obra, a Opportunity aparece na borda de uma grande cratera com o c\u00e9u escurecido por poeira. "
            + "A pe\u00e7a transmite a ideia de persist\u00eancia e resist\u00eancia, lembrando que at\u00e9 as miss\u00f5es mais "
            + "bem-sucedidas t\u00eam um ponto final.</p>",

            // Obra 6 — Curiosity
            "<b>[ Miss\u00e3o: Mars Science Laboratory | Pa\u00eds: EUA/NASA | Ano: 2012 ]</b>"
            + "<p>O Curiosity \u00e9 o rover da miss\u00e3o Mars Science Laboratory, lan\u00e7ado em 2011 e pousado em agosto de 2012 "
            + "na cratera Gale utilizando a t\u00e9cnica de pouso sky crane. A cratera Gale foi escolhida por abrigar o "
            + "Monte Sharp, com muitas camadas sedimentares que registram diferentes fases da hist\u00f3ria de Marte.</p>"
            + "<p>Com cerca de 900 kg, o Curiosity carrega 10 instrumentos cient\u00edficos, incluindo c\u00e2meras de alta "
            + "resolu\u00e7\u00e3o, o laser ChemCam, uma broca para perfurar rochas e laborat\u00f3rios internos (SAM, CheMin) "
            + "capazes de analisar gases, minerais e mol\u00e9culas org\u00e2nicas. Os resultados mostram que a cratera Gale "
            + "j\u00e1 abrigou lagos calmos e est\u00e1veis com \u00e1gua e elementos essenciais para a vida.</p>"
            + "<p><b>Curiosidades</b></p>"
            + "<ul>"
            + "<li>O pouso com sky crane foi t\u00e3o complexo que os engenheiros chamaram o momento de \"sete minutos de terror\".</li>"
            + "<li>O Curiosity ainda est\u00e1 ativo, subindo o Monte Sharp e estudando novas camadas de rocha.</li>"
            + "<li>Suas imagens s\u00e3o amplamente usadas em materiais educativos e document\u00e1rios sobre Marte.</li>"
            + "</ul>"
            + "<p><b>Sobre esta obra</b></p>"
            + "<p>A obra mostra o Curiosity em meio a rochas perfuradas, com o Monte Sharp ao fundo exibindo suas "
            + "camadas sedimentares. A cena convida o visitante a pensar em como cada furo na roc