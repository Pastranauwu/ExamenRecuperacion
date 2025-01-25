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
    private static final int GENERACIONES = 300;

    /**
     * Tamaño de la población inicial.
     */
    private static final int TAMANO_POBLACION = 200;

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
        // Random random = new Random();
        poblacion = mutarPoblacion(poblacion);

        // if(random.nextInt(2) == 0) {
        //     poblacion = mutarPoblacion(poblacion);
        // } else {
        //     // poblacion = cruzarPoblacion(poblacion);
        // }
        

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
            // Seleccionar dos padres aleatorios
            Individuo padre1 = poblacion.get(random.nextInt(poblacion.size()));
            Individuo padre2 = poblacion.get(random.nextInt(poblacion.size()));
    
            // Realizar un cruce de un punto
            List<Alimento> genesPadre1 = padre1.getSeleccion();
            List<Alimento> genesPadre2 = padre2.getSeleccion();
    
            int puntoCorte = random.nextInt(genesPadre1.size());
    
            // Crear hijos combinando los genes de ambos padres
            List<Alimento> genesHijo1 = new ArrayList<>(genesPadre1.subList(0, puntoCorte));
            genesHijo1.addAll(genesPadre2.subList(puntoCorte, genesPadre2.size()));
    
            List<Alimento> genesHijo2 = new ArrayList<>(genesPadre2.subList(0, puntoCorte));
            genesHijo2.addAll(genesPadre1.subList(puntoCorte, genesPadre1.size()));
    
            // Crear los nuevos individuos
            nuevaGeneracion.add(new Individuo(genesHijo1));
            if (nuevaGeneracion.size() < poblacion.size()) {
                nuevaGeneracion.add(new Individuo(genesHijo2));
            }
        }
        return nuevaGeneracion;
    }

    public List<Individuo> mutarPoblacion(List<Individuo> poblacion) {
        Random random = new Random();
    
        for (Individuo individuo : poblacion) {
            if (individuo.getFitness() == 0) {
                // System.out.println("Mutando individuo");
                // Probabilidad de mutar cada individuo
                if (random.nextDouble() < 1) { // 10% de probabilidad de mutación
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
