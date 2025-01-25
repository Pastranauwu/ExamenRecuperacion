import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La clase AlgoritmoGenetico implementa un algoritmo genético para optimizar una solución.
 * Implementa la interfaz Runnable para permitir la ejecución en un hilo separado.
 */
public class AlgoritmoGenetico implements Runnable {
    /**
     * Lista de alimentos disponibles para la selección.
     */
    private List<Alimento> alimentos;

    /**
     * El mejor individuo encontrado después de todas las generaciones.
     */
    private Individuo mejorIndividuo;

    /**
     * Número de generaciones a ejecutar.
     */
    private static final int GENERACIONES = 100;

    /**
     * Tamaño de la población inicial.
     */
    private static final int TAMANO_POBLACION = 50;

    /**
     * Constructor que inicializa la lista de alimentos.
     *
     * @param alimentos Lista de alimentos disponibles.
     */
    public AlgoritmoGenetico(List<Alimento> alimentos) {
        this.alimentos = alimentos;
    }

    /**
     * Método que ejecuta el algoritmo genético.
     * Genera la población inicial y la evoluciona a través de varias generaciones.
     */
    @Override
    public void run() {
        List<Individuo> poblacion = generarPoblacionInicial();
        for (int i = 0; i < GENERACIONES; i++) {
            poblacion = evolucionar(poblacion);
        }
        mejorIndividuo = obtenerMejorIndividuo(poblacion);
    }

    /**
     * Genera la población inicial de individuos.
     *
     * @return Lista de individuos que conforman la población inicial.
     */
    private List<Individuo> generarPoblacionInicial() {
        List<Individuo> poblacion = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < TAMANO_POBLACION; i++) {
            List<Alimento> seleccion = new ArrayList<>();
            for (Alimento alimento : alimentos) {
                if (random.nextBoolean()) {
                    seleccion.add(alimento);
                }
            }
            poblacion.add(new Individuo(seleccion));
        }
        return poblacion;
    }

    /**
     * Evoluciona la población a través de cruces, mutaciones y selección.
     *
     * @param poblacion La población actual.
     * @return La nueva generación de la población.
     */
    private List<Individuo> evolucionar(List<Individuo> poblacion) {
        // Implementar cruces, mutaciones y selección aquí.
        poblacion = cruzarPoblacion(poblacion);

        return poblacion; // Devuelve la nueva generación.
    }

    /**
     * Cruza la población actual para generar una nueva generación.
     *
     * @param poblacion La población actual.
     * @return La nueva generación de la población.
     */
    private List<Individuo> cruzarPoblacion(List<Individuo> poblacion) {
        // Implementar lógica de cruce aquí.
        return poblacion;
    }

    public List<Individuo> mutarPoblacion(List<Individuo> poblacion) {
        // Implementar lógica de mutación aquí.
        return poblacion;
    }

    /**
     * Obtiene el mejor individuo de la población actual basado en su fitness.
     *
     * @param poblacion La población actual.
     * @return El mejor individuo de la población.
     */
    private Individuo obtenerMejorIndividuo(List<Individuo> poblacion) {
        return poblacion.stream().max((i1, i2) -> Double.compare(i1.getFitness(), i2.getFitness())).orElse(null);
    }

    /**
     * Devuelve el mejor individuo encontrado después de todas las generaciones.
     *
     * @return El mejor individuo.
     */
    public Individuo getMejorIndividuo() {
        return mejorIndividuo;
    }
}
