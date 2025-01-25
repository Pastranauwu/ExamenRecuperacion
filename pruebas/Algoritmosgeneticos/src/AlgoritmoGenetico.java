import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La clase AlgoritmoGenetico implementa un algoritmo genético para optimizar
 * una solución.
 * Implementa la interfaz Runnable para permitir la ejecución en un hilo
 * separado.
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
    private static final int GENERACIONES = 1000;

    /**
     * Tamaño de la población inicial.
     */
    private static final int TAMANO_POBLACION = 300;

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
        double mejorFitness = 0;
        Individuo mejorIndividuoAux;

        for (int i = 0; i < GENERACIONES; i++) {
            poblacion = evolucionar(poblacion);

            mejorIndividuoAux = obtenerMejorIndividuo(poblacion);
            if (mejorIndividuoAux.getFitness() > mejorFitness) {
                mejorFitness = mejorIndividuoAux.getFitness();
                mejorIndividuo = mejorIndividuoAux;
            }
            // System.out.println("Generación " + i + ": Mejor fitness = " + obtenerMejorIndividuo(poblacion).getFitness());
        }
        // mejorIndividuo = obtenerMejorIndividuo(poblacion);
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
        Random random = new Random();
        // poblacion = mutarPoblacion(poblacion);

        if (random.nextInt(4) == 0) {
            poblacion = mutarPoblacion(poblacion);
        } else {
            poblacion = cruzarPoblacion(poblacion);
        }

        return poblacion; // Devuelve la nueva generación.
    }

    /**
     * Cruza la población actual para generar una nueva generación.
     *
     * @param poblacion La población actual.
     * @return La nueva generación de la población.
     */
    private List<Individuo> cruzarPoblacion(List<Individuo> poblacion) {
        List<Individuo> nuevaGeneracion = new ArrayList<>();
        Random random = new Random();

        // Generar hijos a partir de cruces
        while (nuevaGeneracion.size() < poblacion.size()) {
            // ver quienes son los 2 individuos con mejor fitness y hacer que sean los
            // padres
            Individuo padre1 = obtenerMejorIndividuo(poblacion);
            Individuo padre2 = poblacion.get(random.nextInt(poblacion.size()));

            // verificar que genes tienen en comun
            List<Alimento> genesComunes = new ArrayList<>();
            for (Alimento alimento : padre1.getSeleccion()) {
                if (padre2.getSeleccion().contains(alimento)) {
                    genesComunes.add(alimento);
                }
            }

            if (random.nextDouble() < 0.5) { // 50% de probabilidad de mutación
                if (!genesComunes.isEmpty() && random.nextBoolean()) {
                    // Eliminar un gen (alimento) aleatorio
                    genesComunes.remove(random.nextInt(genesComunes.size()));
                } else {
                    // Agregar un nuevo gen (alimento) aleatorio
                    Alimento nuevoAlimento = alimentos.get(random.nextInt(alimentos.size()));
                    if (!genesComunes.contains(nuevoAlimento)) {
                        genesComunes.add(nuevoAlimento);
                    }
                }
            } else {
                // Intercambiar dos genes aleatorios
                if (genesComunes.size() > 1) {
                    int index1 = random.nextInt(genesComunes.size());
                    int index2 = random.nextInt(genesComunes.size());
                    Alimento temp = genesComunes.get(index1);
                    genesComunes.set(index1, genesComunes.get(index2));
                    genesComunes.set(index2, temp);
                }
            }

            nuevaGeneracion.add(new Individuo(genesComunes));

        }
        return nuevaGeneracion;
    }

    public List<Individuo> mutarPoblacion(List<Individuo> poblacion) {
        Random random = new Random();

        for (Individuo individuo : poblacion) {
            if (individuo.getFitness() == 0) {
                    List<Alimento> genes = new ArrayList<>(individuo.getSeleccion());

                    if (!genes.isEmpty() && random.nextBoolean()) {
                        // Eliminar un gen (alimento) aleatorio
                        genes.remove(random.nextInt(genes.size()));
                    } else {
                        // Agregar un nuevo gen (alimento) aleatorio
                        Alimento nuevoAlimento = alimentos.get(random.nextInt(alimentos.size()));
                        if (!genes.contains(nuevoAlimento)) {
                            genes.add(nuevoAlimento);
                        }
                    }
                    // Actualizar el individuo con los nuevos genes
                    individuo.setSeleccion(genes);
            } else {
                if (random.nextDouble() < 0.80) { // 15% de probabilidad de mutación
                    List<Alimento> genes = new ArrayList<>(individuo.getSeleccion());

                    if (!genes.isEmpty() && random.nextBoolean()) {
                        // Eliminar un gen (alimento) aleatorio
                        genes.remove(random.nextInt(genes.size()));
                    } else {
                        // Agregar un nuevo gen (alimento) aleatorio
                        Alimento nuevoAlimento = alimentos.get(random.nextInt(alimentos.size()));
                        if (!genes.contains(nuevoAlimento)) {
                            genes.add(nuevoAlimento);
                        }
                    }

                    // Actualizar el individuo con los nuevos genes
                    individuo.setSeleccion(genes);
                }
            }
        }

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
