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
    protected String[][] opcoes;
    protected int[]      gabaritos;
    protected int[]      respostasVisitante;

    // ── Satisfação ─────────────────────────────────────────────────────────
    protected int notaSatisfacao;

    // ── Histórico in-memory (sem banco de dados) ───────────────────────────
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
                "[ Miss\u00e3o: Mars 2 | Pa\u00eds: URSS | Ano: 1971 ]\n\n"
                + "A miss\u00e3o Mars 2 foi enviada pela Uni\u00e3o Sovi\u00e9tica em 1971 como parte de um grande esfor\u00e7o para explorar Marte "
                + "com orbitador, m\u00f3dulo de pouso e um pequeno rover chamado PrOP\u201111. O orbitador conseguiu entrar em \u00f3rbita do planeta, "
                + "mas o m\u00f3dulo de pouso entrou na atmosfera em um \u00e2ngulo muito \u00edngreme, o sistema de descida n\u00e3o funcionou como planejado "
                + "e a sonda acabou se chocando violentamente contra o solo marciano. Com o impacto, o m\u00f3dulo foi danificado de forma "
                + "irrevers\u00edvel e o PrOP\u201111, que viajava preso a ele, nunca chegou a ser ligado na superf\u00edcie.\n\n"
                + "O PrOP\u201111 era um rob\u00f4 de cerca de 15 kg, em formato de caixa met\u00e1lica montada sobre dois esquis, preso ao m\u00f3dulo de "
                + "pouso por um cabo de aproximadamente 15 metros. Ele havia sido projetado para se mover de maneira semiaut\u00f4noma: usaria "
                + "hastes mec\u00e2nicas para tatear o terreno, detectar pedras e buracos e decidir pequenos desvios de rota sozinho.\n\n"
                + "*** Curiosidades ***\n"
                + "- A Mars 2 foi o primeiro artefato humano a atingir a superf\u00edcie de Marte, mesmo que por um impacto n\u00e3o controlado.\n"
                + "- O PrOP\u201111 \u00e9 lembrado como um dos primeiros rovers semiaut\u00f4nomos j\u00e1 constru\u00eddos.\n"
                + "- Boa parte do que se sabe sobre o PrOP\u201111 vem de documenta\u00e7\u00e3o t\u00e9cnica e reconstru\u00e7\u00f5es hist\u00f3ricas.\n\n"
                + "--- Sobre esta obra ---\n"
                + "O PrOP\u201111 aparece junto aos destro\u00e7os do m\u00f3dulo de pouso em um cen\u00e1rio de rochas e poeira avermelhada. "
                + "O \"rob\u00f4 fantasma\" simboliza tentativas corajosas que n\u00e3o deram certo, mas abriram caminho para miss\u00f5es "
                + "futuras aprenderem com os erros e aperfei\u00e7oarem t\u00e9cnicas de pouso.",

                // Obra 2 — A Tempestade da Mars 3
                "[ Miss\u00e3o: Mars 3 | Pa\u00eds: URSS | Ano: 1971 ]\n\n"
                + "A miss\u00e3o Mars 3 foi a g\u00eamea da Mars 2 e tamb\u00e9m levou um rover PrOP\u201111 preso ao seu m\u00f3dulo de pouso. Em 2 de "
                + "dezembro de 1971, a Mars 3 entrou para a hist\u00f3ria ao realizar o primeiro pouso suave bem\u2011sucedido em Marte. "
                + "Logo ap\u00f3s tocar o solo, o m\u00f3dulo come\u00e7ou a transmitir dados para a Terra, mas a comunica\u00e7\u00e3o durou apenas "
                + "entre 14 e 20 segundos antes de ser interrompida definitivamente.\n\n"
                + "Na \u00e9poca, Marte passava por uma grande tempestade global de poeira, e muitos pesquisadores acreditam que essa "
                + "tempestade foi respons\u00e1vel pela perda s\u00fabita do sinal. O PrOP\u201111 tinha o mesmo conceito do seu irm\u00e3o da Mars 2: "
                + "um pequeno rob\u00f4 em forma de caixa com esquis, preso por cabo, pensado para saltar curtas dist\u00e2ncias e medir "
                + "propriedades f\u00edsicas do solo. Como a comunica\u00e7\u00e3o foi perdida quase imediatamente, entende\u2011se que o rover "
                + "nunca chegou a descer pela rampa do m\u00f3dulo.\n\n"
                + "*** Curiosidades ***\n"
                + "- Apesar de ser vista como fracasso, a Mars 3 realizou o primeiro pouso suave bem-sucedido em Marte.\n"
                + "- A tempestade de poeira serve at\u00e9 hoje como exemplo dos riscos ambientais do planeta para naves.\n"
                + "- O PrOP\u201111 da Mars 3 \u00e9 s\u00edmbolo de tecnologia pronta, impedida pelos extremos do clima marciano.\n\n"
                + "--- Sobre esta obra ---\n"
                + "A obra mostra o PrOP\u201111 ainda sobre a plataforma da Mars 3, enquanto o c\u00e9u ao redor escurece sob uma "
                + "gigantesca tempestade de poeira. A imagem fala sobre a fragilidade humana diante de um planeta duro, "
                + "mas tamb\u00e9m sobre a coragem de tentar inovar mesmo sabendo dos riscos.",

                // Obra 3 — Sojourner
                "[ Miss\u00e3o: Mars Pathfinder | Pa\u00eds: EUA/NASA | Ano: 1997 ]\n\n"
                + "A miss\u00e3o Mars Pathfinder foi lan\u00e7ada pela NASA em dezembro de 1996 com o objetivo de testar uma nova forma "
                + "de pousar em Marte usando airbags e demonstrar que um pequeno rob\u00f4 m\u00f3vel poderia fazer ci\u00eancia de qualidade "
                + "com baixo custo. Em 4 de julho de 1997, o m\u00f3dulo pousou em Ares Vallis, uma regi\u00e3o moldada por enchentes antigas.\n\n"
                + "O rover Sojourner, com cerca de 11 kg e seis rodas, foi o primeiro ve\u00edculo a deslocar\u2011se com sucesso pela "
                + "superf\u00edcie de Marte. Ele levava c\u00e2meras e o espectr\u00f4metro Alpha Proton X\u2011Ray (APXS), que analisava a "
                + "composi\u00e7\u00e3o qu\u00edmica de rochas e solos ao redor do lander. A miss\u00e3o foi planejada para poucos dias, mas o "
                + "conjunto lander + rover funcionou por cerca de tr\u00eas meses, enviando milhares de fotos e medi\u00e7\u00f5es.\n\n"
                + "*** Curiosidades ***\n"
                + "- O nome \"Sojourner\" homenageia Sojourner Truth, ativista contra a escravid\u00e3o e pelos direitos civis.\n"
                + "- O uso de airbags para amortecer o pouso foi uma inova\u00e7\u00e3o marcante da miss\u00e3o.\n"
                + "- As imagens do pequeno rob\u00f4 andando entre as rochas em Marte tiveram grande impacto na m\u00eddia.\n\n"
                + "--- Sobre esta obra ---\n"
                + "A obra mostra o Sojourner como um pequeno explorador diante de um grande campo de rochas. Essa composi\u00e7\u00e3o "
                + "convida o visitante a pensar em como grandes avan\u00e7os cient\u00edficos podem come\u00e7ar com experimentos "
                + "modestos, mas bem planejados.",

                // Obra 4 — Spirit
                "[ Miss\u00e3o: MER-A | Pa\u00eds: EUA/NASA | Ano: 2004 ]\n\n"
                + "A Spirit foi um dos dois rovers da miss\u00e3o Mars Exploration Rover, lan\u00e7ados em 2003 para estudar diferentes "
                + "regi\u00f5es de Marte. Em janeiro de 2004, a Spirit pousou na cratera Gusev, escolhida por mostrar sinais de "
                + "poss\u00edvel antigo lago. A miss\u00e3o tinha plano inicial de 90 dias marcianos, mas a Spirit continuou operando "
                + "por mais de seis anos, at\u00e9 2010.\n\n"
                + "Durante esse tempo, a Spirit percorreu cerca de 7,7 km, examinando rochas vulc\u00e2nicas, solos e camadas de "
                + "materiais modificados por \u00e1gua. Encontrou dep\u00f3sitos ricos em s\u00edlica, que sugerem a atua\u00e7\u00e3o de fontes "
                + "termais ou \u00e1gua aquecida circulando pelo subsolo. Em 2009, a Spirit ficou presa em uma regi\u00e3o de solo "
                + "muito fofo, batizada de Troy, onde suas rodas afundaram e o rover n\u00e3o conseguiu sair.\n\n"
                + "*** Curiosidades ***\n"
                + "- A Spirit sobreviveu a tempestades de poeira e invernos marcianos durante anos.\n"
                + "- O atolamento em Troy virou um caso cl\u00e1ssico de limite operacional de um rover em terreno desconhecido.\n"
                + "- As Columbia Hills revelaram camadas de rochas formadas em diferentes ambientes ao longo da hist\u00f3ria de Marte.\n\n"
                + "--- Sobre esta obra ---\n"
                + "Na obra, a Spirit aparece inclinada e presa em uma \u00e1rea de areia clara. A cena resume a trajet\u00f3ria da "
                + "miss\u00e3o: um rob\u00f4 que superou em muito o tempo previsto e, no fim, foi detido pelo pr\u00f3prio terreno "
                + "que tentava estudar.",

                // Obra 5 — Opportunity
                "[ Miss\u00e3o: MER-B | Pa\u00eds: EUA/NASA | Ano: 2004 ]\n\n"
                + "A Opportunity foi a segunda sonda da miss\u00e3o Mars Exploration Rover, pousando em janeiro de 2004 na "
                + "regi\u00e3o de Meridiani Planum, escolhida por apresentar min\u00e9rios de ferro como a hematita, frequentemente "
                + "associados \u00e0 presen\u00e7a de \u00e1gua em ambientes antigos. Tamb\u00e9m planejada para 90 dias, a Opportunity "
                + "continuou ativa at\u00e9 2018, operando por quase 15 anos.\n\n"
                + "Ao longo desse tempo, o rover percorreu mais de 45 km, explorando v\u00e1rias crateras como Endurance, "
                + "Victoria e Endeavour. Em 2018, uma grande tempestade global de poeira envolveu Marte, escurecendo o "
                + "c\u00e9u e impedindo que os pain\u00e9is solares recebessem luz suficiente, encerrando definitivamente a miss\u00e3o.\n\n"
                + "*** Curiosidades ***\n"
                + "- A Opportunity operou cerca de 57 vezes mais tempo do que o planejado originalmente.\n"
                + "- A frase \"my battery is low and it's getting dark\" ficou famosa como metafora do fim da miss\u00e3o.\n"
                + "- A dist\u00e2ncia percorrida fez dela um dos ve\u00edculos com maior quilometragem em outro corpo celeste.\n\n"
                + "--- Sobre esta obra ---\n"
                + "Na obra, a Opportunity aparece na borda de uma grande cratera com o c\u00e9u escurecido por poeira. "
                + "A pe\u00e7a transmite a ideia de persist\u00eancia e resist\u00eancia, lembrando que at\u00e9 as miss\u00f5es mais "
                + "bem-sucedidas t\u00eam um ponto final.",

                // Obra 6 — Curiosity
                "[ Miss\u00e3o: Mars Science Laboratory | Pa\u00eds: EUA/NASA | Ano: 2012 ]\n\n"
                + "O Curiosity \u00e9 o rover da miss\u00e3o Mars Science Laboratory, lan\u00e7ado em 2011 e pousado em agosto de 2012 "
                + "na cratera Gale utilizando a t\u00e9cnica de pouso sky crane. A cratera Gale foi escolhida por abrigar o "
                + "Monte Sharp, com muitas camadas sedimentares que registram diferentes fases da hist\u00f3ria de Marte.\n\n"
                + "Com cerca de 900 kg, o Curiosity carrega 10 instrumentos cient\u00edficos, incluindo c\u00e2meras de alta "
                + "resolu\u00e7\u00e3o, o laser ChemCam, uma broca para perfurar rochas e laborat\u00f3rios internos (SAM, CheMin) "
                + "capazes de analisar gases, minerais e mol\u00e9culas org\u00e2nicas. Os resultados mostram que a cratera Gale "
                + "j\u00e1 abrigou lagos calmos e est\u00e1veis com \u00e1gua e elementos essenciais para a vida.\n\n"
                + "*** Curiosidades ***\n"
                + "- O pouso com sky crane foi t\u00e3o complexo que os engenheiros chamaram o momento de \"sete minutos de terror\".\n"
                + "- O Curiosity ainda est\u00e1 ativo, subindo o Monte Sharp e estudando novas camadas de rocha.\n"
                + "- Suas imagens s\u00e3o amplamente usadas em materiais educativos e document\u00e1rios sobre Marte.\n\n"
                + "--- Sobre esta obra ---\n"
                + "A obra mostra o Curiosity em meio a rochas perfuradas, com o Monte Sharp ao fundo exibindo suas "
                + "camadas bem definidas. A imagem refor\u00e7a a ideia do rover como um ge\u00f3logo rob\u00f3tico que l\u00ea "
                + "a hist\u00f3ria do planeta nas rochas, camada por camada.",

                // Obra 7 — Perseverance
                "[ Miss\u00e3o: Mars 2020 | Pa\u00eds: EUA/NASA | Ano: 2021 ]\n\n"
                + "O Perseverance \u00e9 o rover da miss\u00e3o Mars 2020, lan\u00e7ado em julho de 2020 e pousado na cratera Jezero "
                + "em fevereiro de 2021. Jezero foi escolhida por abrigar um antigo delta de rio e dep\u00f3sitos que indicam "
                + "a exist\u00eancia de um lago no passado, condi\u00e7\u00f5es muito promissoras para a preserva\u00e7\u00e3o de sinais de vida microbiana.\n\n"
                + "O rover foi projetado com tr\u00eas objetivos principais: procurar sinais de vida antiga em rochas e sedimentos; "
                + "estudar a geologia e o clima atuais de Marte; e coletar e armazenar amostras em tubos met\u00e1licos selados "
                + "para serem buscados por futuras miss\u00f5es de retorno \u00e0 Terra. Conta com instrumentos avan\u00e7ados, incluindo "
                + "c\u00e2meras, espectr\u00f4metros, sistema de perfura\u00e7\u00e3o e o experimento MOXIE, que produz oxig\u00eanio da atmosfera.\n\n"
                + "*** Curiosidades ***\n"
                + "- O nome Perseverance foi sugerido por um estudante em um concurso.\n"
                + "- O rover j\u00e1 coletou dezenas de amostras catalogadas que dever\u00e3o ser devolvidas \u00e0 Terra.\n"
                + "- A miss\u00e3o Mars 2020 \u00e9 vista como passo essencial entre explora\u00e7\u00e3o rob\u00f3tica e futura explora\u00e7\u00e3o humana.\n\n"
                + "--- Sobre esta obra ---\n"
                + "A obra apresenta o Perseverance trabalhando no delta de Jezero, cercado por rochas estratificadas e "
                + "tubos de amostra. O rover \u00e9 mostrado como um colecionador de pistas, enchendo cada tubo como uma "
                + "garrafa com mensagem para o futuro.",

                // Obra 8 — Ingenuity
                "[ Miss\u00e3o: Mars 2020 | Pa\u00eds: EUA/NASA | Ano: 2021 ]\n\n"
                + "O Ingenuity \u00e9 um pequeno helic\u00f3ptero rob\u00f3tico que viajou preso \u00e0 parte inferior do rover Perseverance, "
                + "com a miss\u00e3o de provar que \u00e9 poss\u00edvel voar na atmosfera rarefeita de Marte. Ele foi pensado inicialmente "
                + "como experimento de curta dura\u00e7\u00e3o: apenas cinco voos curtos em cerca de 30 dias marcianos. O primeiro voo, "
                + "em 19 de abril de 2021, marcou o primeiro voo motorizado e controlado da hist\u00f3ria em outro planeta.\n\n"
                + "O helic\u00f3ptero pesa cerca de 1,8 kg e \u00e9 alimentado por um pequeno painel solar. Suas h\u00e9lices giram muito "
                + "mais r\u00e1pido do que as de um helic\u00f3ptero na Terra, justamente porque o ar marciano \u00e9 muito mais fino. "
                + "Gra\u00e7as ao bom desempenho, continuou operando muito al\u00e9m do planejado, realizando 72 voos em quase "
                + "tr\u00eas anos e ajudando o Perseverance a reconhecer terrenos e planejar rotas.\n\n"
                + "*** Curiosidades ***\n"
                + "- Ingenuity utilizou componentes de baixo custo, semelhantes aos de eletr\u00f4nicos comerciais.\n"
                + "- Os voos deram origem a v\u00eddeos e imagens que se tornaram \u00edcones da explora\u00e7\u00e3o do Sistema Solar.\n"
                + "- O sucesso incentiva o desenvolvimento de futuros drones cient\u00edficos dedicados para Marte.\n\n"
                + "--- Sobre esta obra ---\n"
                + "Na obra, Ingenuity aparece em voo, com sua sombra projetada no solo marciano e o Perseverance ao fundo. "
                + "A pe\u00e7a transmite leveza e inova\u00e7\u00e3o, mostrando que a explora\u00e7\u00e3o de Marte passou a ter tamb\u00e9m "
                + "uma dimens\u00e3o a\u00e9rea.",

                // Obra 9 — Zhurong
                "[ Miss\u00e3o: Tianwen-1 | Pa\u00eds: China/CNSA | Ano: 2021 ]\n\n"
                + "O Zhurong \u00e9 o primeiro rover marciano da China e faz parte da miss\u00e3o Tianwen\u20111, que inclui um orbitador, "
                + "um m\u00f3dulo de pouso e o pr\u00f3prio rover. A miss\u00e3o foi lan\u00e7ada em julho de 2020, entrou em \u00f3rbita de Marte "
                + "em fevereiro de 2021, e o m\u00f3dulo de pouso pousou em Utopia Planitia em 14 de maio de 2021, tornando a "
                + "China o segundo pa\u00eds a operar um rover na superf\u00edcie de Marte.\n\n"
                + "Com cerca de 240 kg e seis rodas movidos a energia solar, o Zhurong leva c\u00e2meras, radar de penetra\u00e7\u00e3o "
                + "no solo, espectr\u00f4metros e sensores para estudar o campo magn\u00e9tico e a composi\u00e7\u00e3o das rochas. Planejado "
                + "para 90 dias, operou por mais de 347 sols e percorreu quase 2 km antes de entrar em hiberna\u00e7\u00e3o "
                + "em maio de 2022.\n\n"
                + "*** Curiosidades ***\n"
                + "- O nome Zhurong vem de uma divindade do fogo na mitologia chinesa.\n"
                + "- O radar do rover permite observar camadas abaixo do solo, algo que poucos rovers possuem.\n"
                + "- A miss\u00e3o demonstra a capacidade da China de realizar miss\u00f5es interplanet\u00e1rias com m\u00faltiplos elementos.\n\n"
                + "--- Sobre esta obra ---\n"
                + "Na obra, o Zhurong segue em frente, afastando-se da plataforma de pouso que exibe a bandeira da China. "
                + "A composi\u00e7\u00e3o simboliza o primeiro passo do pa\u00eds sobre o solo marciano, mostrando que a explora\u00e7\u00e3o "
                + "de Marte se tornou um esfor\u00e7o verdadeiramente internacional.",

                // Obra 10 — Rosalind Franklin
                "[ Miss\u00e3o: ExoMars | Pa\u00eds: ESA/NASA | Lan\u00e7amento previsto: 2028 ]\n\n"
                + "O Rosalind Franklin \u00e9 o rover da miss\u00e3o ExoMars, um projeto europeu com participa\u00e7\u00e3o da NASA voltado "
                + "especificamente para a busca de sinais de vida passada em Marte. O lan\u00e7amento est\u00e1 previsto para 2028, "
                + "com pouso planejado em Oxia Planum, uma regi\u00e3o com grandes dep\u00f3sitos de argilas e sedimentos antigos "
                + "formados em presen\u00e7a de \u00e1gua.\n\n"
                + "Uma das grandes inova\u00e7\u00f5es do rover \u00e9 sua broca, capaz de perfurar at\u00e9 cerca de 2 metros abaixo da "
                + "superf\u00edcie, muito mais fundo do que os rovers anteriores. Em profundidades maiores, as amostras ficam "
                + "mais protegidas da radia\u00e7\u00e3o e da oxida\u00e7\u00e3o, o que aumenta a chance de preservar mol\u00e9culas org\u00e2nicas "
                + "fr\u00e1geis. O rover analisar\u00e1 essas amostras em um laborat\u00f3rio interno usando o espectr\u00f4metro MOMA, "
                + "RLS e Ma_MISS.\n\n"
                + "*** Curiosidades ***\n"
                + "- Ser\u00e1 o primeiro rover em Marte com foco t\u00e3o direto na biologia, usando broca profunda para procurar vida.\n"
                + "- Oxia Planum concentra rochas muito antigas, poss\u00edvelmente formadas quando Marte ainda tinha muita \u00e1gua.\n"
                + "- A miss\u00e3o \u00e9 vista como complementar \u00e0s miss\u00f5es da NASA, criando rede de investiga\u00e7\u00f5es sobre o planeta.\n\n"
                + "--- Sobre esta obra ---\n"
                + "Na obra, o Rover Rosalind Franklin aparece perfurando o solo de Oxia Planum. A pe\u00e7a transmite a imagem "
                + "de um detetive do subsolo, que procura pistas quase invis\u00edveis escondidas em camadas profundas e antigas. "
                + "Esta miss\u00e3o representa o pr\u00f3ximo cap\u00edtulo: depois de descobrir \u00e1gua passada, a humanidade investiga "
                + "se algum dia houve vida de fato naquele planeta."
        };

        codigosObras = new String[]{
                "PM-02", "PM-03", "SJ-97", "SP-04", "OP-18",
                "CU-12", "PV-21", "IG-21", "ZH-21", "RF-28"
        };

        anosObras = new String[]{
                "1971", "1971", "1997", "2004", "2004\u20132018",
                "2012\u2013", "2021\u2013", "2021", "2021", "2028"
        };

        // Imagens 7 e 8 invertidas conforme solicitado — Etapa 4
        imagensObras = new String[]{
                "/assets/obras/obra1-prop-m-mars2.jpeg",
                "/assets/obras/obra2-prop-m-mars3.jpeg",
                "/assets/obras/obra3-sojourner.jpeg",
                "/assets/obras/obra4-spirit.jpeg",
                "/assets/obras/obra5-opportunity.jpeg",
                "/assets/obras/obra6-curiosity.jpeg",
                "/assets/obras/obra8-ingenuity.jpeg",
                "/assets/obras/obra7-perseverance.jpeg",
                "/assets/obras/obra9-zhurong.jpeg",
                "/assets/obras/obra10-rosalind-franklin.jpeg"
        };
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

    @Override public void avancar() { etapaAtual++; }
    @Override public void voltar()  { if (etapaAtual > 0) etapaAtual--; }

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
