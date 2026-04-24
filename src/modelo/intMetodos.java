package modelo;

/**
 * Interface principal do sistema Totem Interativo.
 * Define os contratos obrigatórios para todas as classes do modelo.
 */
public interface intMetodos {

    /** Método principal — chamado automaticamente após inicialização. */
    void Executar();

    /**
     * Valida os dados básicos do visitante.
     * @param nome  Nome digitado
     * @param idade Idade digitada (String — vem do teclado virtual)
     */
    boolean validarVisitante(String nome, String idade);

    /**
     * Registra a resposta do questionário.
     * @param pergunta Índice da pergunta (0 a 4)
     * @param opcao    Índice da opção escolhida (0 a 3)
     */
    void registrarResposta(int pergunta, int opcao);

    /**
     * Registra a nota de satisfação.
     * @param estrelas Nota de 0 a 5
     */
    void registrarSatisfacao(int estrelas);

    /** Calcula e retorna o número de acertos no questionário (0 a 5). */
    int calcularPontuacao();

    /** Avança para a próxima etapa do fluxo. */
    void avancar();

    /** Retorna para a etapa anterior do fluxo. */
    void voltar();
}
