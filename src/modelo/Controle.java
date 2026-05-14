package modelo;

import apresentacao.*;
import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * Controlador central — interliga frontend e backend,
 * gerencia o fluxo de telas e a passagem de dados.
 */
public class Controle extends absPropriedades {

    private final JFrame framePai;
    private final Validacao validacao;

    public Controle() {
        framePai = new JFrame();
        framePai.setUndecorated(true);
        framePai.setExtendedState(JFrame.MAXIMIZED_BOTH);
        framePai.setVisible(true);
        framePai.toFront();
        framePai.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        validacao = new Validacao();
    }

    // ── Ponto de entrada ──────────────────────────────────────────────────────
    @Override
    public void Executar() {
        SwingUtilities.invokeLater(this::exibirTelaInicial);
    }

    // ── Implementação dos métodos abstratos de intMetodos ────────────────────────────

    @Override
    public void registrarResposta(int pergunta, int opcao) {
        if (pergunta >= 0 && pergunta < respostasVisitante.length) {
            respostasVisitante[pergunta] = opcao;
        }
    }

    @Override
    public void registrarSatisfacao(int estrelas) {
        this.notaSatisfacao = estrelas;
    }

    @Override
    public int calcularPontuacao() {
        int acertos = 0;
        for (int i = 0; i < gabaritos.length; i++) {
            if (i < respostasVisitante.length && respostasVisitante[i] == gabaritos[i]) {
                acertos++;
            }
        }
        return acertos;
    }

    @Override
    public void avancar() {
        etapaAtual++;
    }

    @Override
    public void voltar() {
        if (etapaAtual > 0) etapaAtual--;
    }

    // ── Navegação ────────────────────────────────────────────────────────────
    public void exibirTelaInicial()   { new fmrInicio(framePai, this).setVisible(true); }
    public void exibirCadastro()      { new fmrCadastroVisitante(framePai, this).setVisible(true); }
    public void exibirQuestionario()  { new fmrQuestionario(framePai, this).setVisible(true); }
    public void exibirSatisfacao()    { new fmrSatisfacao(framePai, this).setVisible(true); }
    public void exibirAdministracao() { new fmrAdministracao(framePai, this).setVisible(true); }

    public void exibirObra(int indice) {
        // Guarda a obra em foco para que as proximas telas saibam onde o visitante esta no percurso.
        obraAtual = indice;
        new fmrObra(framePai, this, indice).setVisible(true);
    }

    public void abrirObra(int indice) { exibirObra(indice); }

    public void voltarParaInicio() { exibirTelaInicial(); }

    // ── Lógica de fluxo ────────────────────────────────────────────────────────────

    public void proximaEtapaAposObra(int obraIdx) {
        int prox = obraIdx + 1;
        if (prox < titulosObras.length) exibirObra(prox);
        else exibirQuestionario();
    }

    public void aposQuestionario() {
        // Salva o desempenho da sessao antes de pedir a avaliacao final da experiencia.
        historicoPontuacoes.add(calcularPontuacao());
        exibirSatisfacao();
    }

    public void finalizarVisita(int notaFinal) {
        // Consolida os dados da sessao em memoria e reinicia o fluxo para o proximo visitante.
        registrarSatisfacao(notaFinal);
        historicoNomes.add(getNomeCompletoVisitanteAtual());
        historicoSobrenomes.add(sobrenomeVisitante);
        historicoFaixasEtarias.add(faixaEtariaVisitante);
        historicoSatisfacoes.add(notaFinal);
        reiniciarSessao();
        exibirTelaInicial();
    }

    private void reiniciarSessao() {
        nomeVisitante        = "";
        sobrenomeVisitante   = "";
        faixaEtariaVisitante = "";
        dadosVisitante[0]    = "";
        dadosVisitante[1]    = "";
        dadosVisitante[2]    = "";
        obraAtual            = 0;
        etapaAtual           = 0;
        notaSatisfacao       = -1;
        for (int i = 0; i < respostasVisitante.length; i++) respostasVisitante[i] = -1;
    }

    // ── Validação ──────────────────────────────────────────────────────────────
    @Override
    public boolean validarVisitante(String nome, String idade) {
        return validacao.validarNome(nome)
                && (validacao.validarFaixaEtaria(idade) || validacao.validarIdadeTexto(idade));
    }

    public boolean validarVisitante(String nome, String sobrenome, String faixaEtaria) {
        return validacao.validarNome(nome)
                && validacao.validarSobrenome(sobrenome)
                && validacao.validarFaixaEtaria(faixaEtaria);
    }

    public String erroNome(String nome)               { return validacao.mensagemErroNome(nome); }
    public String erroSobrenome(String sobrenome)     { return validacao.mensagemErroSobrenome(sobrenome); }
    public String erroIdade(String idade)             { return validacao.mensagemErroIdade(idade); }
    public String erroFaixaEtaria(String faixaEtaria) { return validacao.mensagemErroFaixaEtaria(faixaEtaria); }

    public boolean salvarDadosVisitante(String nome, String sobrenome, String faixaEtaria) {
        if (!validarVisitante(nome, sobrenome, faixaEtaria)) return false;
        this.nomeVisitante        = validacao.sanitizarNome(nome);
        this.sobrenomeVisitante   = validacao.sanitizarNome(sobrenome);
        this.faixaEtariaVisitante = faixaEtaria;
        this.dadosVisitante[0]    = this.nomeVisitante;
        this.dadosVisitante[1]    = this.sobrenomeVisitante;
        this.dadosVisitante[2]    = this.faixaEtariaVisitante;
        return true;
    }

    public boolean autenticarAdministracao(char[] senha) {
        char[] senhaCorreta = {'1', '2', '3', '4', '5', '6'};
        boolean autenticado = senha != null && Arrays.equals(senha, senhaCorreta);
        Arrays.fill(senhaCorreta, '\0');
        return autenticado;
    }

    // ── Estatísticas do histórico ──────────────────────────────────────────────────

    public int    getTotalAvaliacoes()                         { return historicoSatisfacoes.size(); }
    public double getMediaAvaliacoes()                         { if (historicoSatisfacoes.isEmpty()) return 0; int s=0; for(int v:historicoSatisfacoes) s+=v; return s/(double)historicoSatisfacoes.size(); }
    public int    getTotalAvaliacoesPositivas()                { int t=0; for(int v:historicoSatisfacoes) if(v>=4) t++; return t; }
    public double getMediaPontuacaoHistorica()                 { if (historicoPontuacoes.isEmpty()) return 0; int s=0; for(int v:historicoPontuacoes) s+=v; return s/(double)historicoPontuacoes.size(); }
    public int    getQuantidadeAvaliacoesPorNota(int nota)     { int t=0; for(int v:historicoSatisfacoes) if(v==nota) t++; return t; }

    /** Retorna a lista completa de satisfações da sessão — usado por fmrAdministracao. */
    public List<Integer> getHistoricoSatisfacoes()  { return historicoSatisfacoes; }

    /** Retorna a lista completa de pontuações do quiz — usado por fmrAdministracao. */
    public List<Integer> getHistoricoPontuacoes()   { return historicoPontuacoes; }

    // ── Getters do visitante atual ──────────────────────────────────────────────────

    public String   getNomeVisitanteAtual()        { return nomeVisitante; }
    /** Alias sem sufixo — chamado por fmrSatisfacao. */
    public String   getNomeVisitante()             { return nomeVisitante; }
    public String   getSobrenomeVisitanteAtual()   { return sobrenomeVisitante; }
    public String   getNomeCompletoVisitanteAtual(){ return (nomeVisitante + " " + sobrenomeVisitante).trim(); }
    public String   getFaixaEtariaVisitanteAtual() { return faixaEtariaVisitante; }
    public String[] getDadosVisitanteAtual()       { return dadosVisitante; }

    // ── Getters auxiliares para as telas ────────────────────────────────────────────
    public JFrame    getFramePai()             { return framePai; }
    public String    getTituloObra(int i)      { return titulosObras[i]; }
    public String    getDescricaoObra(int i)   { return descricoesObras[i]; }
    public String    getImagemObra(int i)      { return imagensObras[i]; }
    public String    getCodigoObra(int i)      { return codigosObras[i]; }
    public String    getAnoObra(int i)         { return anosObras[i]; }
    public String    getPergunta(int i)        { return perguntas[i]; }
    public String[]  getOpcoesPergunta(int i)  { return opcoes[i]; }
    public int       getTotalObras()           { return titulosObras.length; }
    public int       getTotalPerguntas()       { return perguntas.length; }
    /** Retorna o índice por item — compatível com chamadas pontuais. */
    public int       getGabarito(int i)        { return gabaritos[i]; }
    /** Retorna o array completo de gabaritos — usado por fmrQuestionario. */
    public int[]     getGabaritos()            { return gabaritos; }
    /** Retorna o array completo de respostas do visitante — usado por fmrQuestionario. */
    public int[]     getRespostasVisitante()   { return respostasVisitante; }
    /** Retorna a resposta de uma pergunta específica — compatível com chamadas pontuais. */
    public int       getRespostaVisitante(int i){ return respostasVisitante[i]; }
}
