import java.util.*;
import java.util.stream.Collectors;

/**
 * La clase AlgoritmoGenetico implementa un algoritmo genético para optimizar
 * una solución.
 * Implementa la interfaz Runnable para permitir la ejecución en un hilo
 * separado.
 */
public class AlgoritmoGenetico implements Runnable {
    private List<Alimento> alimentos;
    private Individuo mejorIndividuo;
    private static final int GENERACIONES = 1200;
    private static final int TAMANO_POBLACION = 600;

    // Para monitoreo
    private List<Double> mejoresFitnessPorGeneracion = new ArrayList<>();
    private List<Double> promedioFitnessPorGeneracion = new ArrayList<>();
    private List<Double> diversidadPorGeneracion = new ArrayList<>();

    public AlgoritmoGenetico(List<Alimento> alimentos) {
        this.alimentos = alimentos;
    }

    @Override
    public void run() {
        List<Individuo> poblacion = generarPoblacionInicial();
        double mejorFitness = 0;
        Individuo mejorIndividuoAux;

        for (int generacion = 0; generacion < GENERACIONES; generacion++) {
            poblacion = evolucionar(poblacion);
            mejorIndividuoAux = obtenerMejorIndividuo(poblacion);
            if (mejorIndividuoAux.getFitness() > mejorFitness) {
                mejorFitness = mejorIndividuoAux.getFitness();
                mejorIndividuo = mejorIndividuoAux;
            }

            // Monitoreo de progreso
            // registrarEstadisticas(poblacion, generacion);
        }
    }

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

    private List<Individuo> evolucionar(List<Individuo> poblacion) {
        Random random = new Random();
        if (random.nextInt(4) == 0) {
            poblacion = mutarPoblacion(poblacion);
        } else {
            poblacion = cruzarPoblacion(poblacion);
        }
        return poblacion;
    }

    private List<Individuo> cruzarPoblacion(List<Individuo> poblacion) {
        List<Individuo> nuevaGeneracion = new ArrayList<>();
        Random random = new Random();

        while (nuevaGeneracion.size() < poblacion.size()) {
            Individuo padre1 = obtenerMejorIndividuo(poblacion);
            Individuo padre2 = poblacion.get(random.nextInt(poblacion.size()));

            List<Alimento> genesComunes = padre1.getSeleccion().stream()
                .filter(padre2.getSeleccion()::contains)
                .collect(Collectors.toList());

            int cambios = random.nextInt(3) + 1; // Realizar entre 1 y 2 cambios
            for (int i = 0; i < cambios; i++) {
                if (random.nextDouble() < 0.2 && !genesComunes.isEmpty()) {
                    genesComunes.remove(random.nextInt(genesComunes.size()));
                } else {
                    Alimento nuevoAlimento = alimentos.get(random.nextInt(alimentos.size()));
                    if (!genesComunes.contains(nuevoAlimento)) {
                        genesComunes.add(nuevoAlimento);
                    }
                }
            }

            nuevaGeneracion.add(new Individuo(genesComunes));
        }
        return nuevaGeneracion;
    }

    private List<Individuo> mutarPoblacion(List<Individuo> poblacion) {
        Random random = new Random();
        for (Individuo individuo : poblacion) {
            if (random.nextDouble() < 0.7) {
                List<Alimento> genes = new ArrayList<>(individuo.getSeleccion());
                if (!genes.isEmpty() && random.nextBoolean()) {
                    genes.remove(random.nextInt(genes.size()));
                } else {
                    Alimento nuevoAlimento = alimentos.get(random.nextInt(alimentos.size()));
                    if (!genes.contains(nuevoAlimento)) {
                            genes.add(nuevoAlimento);
                    }
                }
                individuo.setSeleccion(genes);
            }
        }
        return poblacion;
    }

    private Individuo obtenerMejorIndividuo(List<Individuo> poblacion) {
        return poblacion.stream().max(Comparator.comparingDouble(Individuo::getFitness)).orElse(null);
    }

    private void registrarEstadisticas(List<Individuo> poblacion, int generacion) {
        double mejorFitness = poblacion.stream().mapToDouble(Individuo::getFitness).max().orElse(0);
        double promedioFitness = poblacion.stream().mapToDouble(Individuo::getFitness).average().orElse(0);
        double diversidad = calcularDiversidad(poblacion);

        mejoresFitnessPorGeneracion.add(mejorFitness);
        promedioFitnessPorGeneracion.add(promedioFitness);
        diversidadPorGeneracion.add(diversidad);

        System.out.printf("Generación %d: Mejor Fitness: %.2f, Promedio Fitness: %.2f, Diversidad: %.2f%n",
            generacion, mejorFitness, promedioFitness, diversidad);
    }

    private double calcularDiversidad(List<Individuo> poblacion) {
        Set<List<Alimento>> combinacionesUnicas = poblacion.stream()
            .map(Individuo::getSeleccion)
            .collect(Collectors.toSet());
        return (double) combinacionesUnicas.size() / poblacion.size();
    }

    public List<Double> getMejoresFitnessPorGeneracion() {
        return mejoresFitnessPorGeneracion;
    }

    public List<Double> getPromedioFitnessPorGeneracion() {
        return promedioFitnessPorGeneracion;
    }

    public List<Double> getDiversidadPorGeneracion() {
        return diversidadPorGeneracion;
    }

    public Individuo getMejorIndividuo() {
        return mejorIndividuo;
    }
}
