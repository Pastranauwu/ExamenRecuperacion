import java.io.FileWriter;
import java.io.IOException;
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
    private static final int TAMANO_POBLACION = 700;
    private static int probabilidadMutacion = 70;
    private static double variedaPorGeneracion = 0;

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
        Individuo.setCalorias_recomendadas(2000); // Calorías recomendadas por defecto

        for (int generacion = 0; generacion < GENERACIONES; generacion++) {
            poblacion = evolucionar(poblacion);
            mejorIndividuoAux = obtenerMejorIndividuo(poblacion);

            if (mejorIndividuoAux.getFitness() > mejorFitness) {
                mejorFitness = mejorIndividuoAux.getFitness();
                mejorIndividuo = mejorIndividuoAux;
            }

            // Monitoreo y ajuste cada 100 generaciones
            if (generacion % 100 == 0) {
                diversidadPorGeneracion.add(calcularDiversidad(poblacion));

                if (!diversidadPorGeneracion.isEmpty()) {
                    double aux = diversidadPorGeneracion.stream().mapToDouble(Double::doubleValue).average().orElse(0);
                    if (aux <= variedaPorGeneracion) {
                        variedaPorGeneracion = aux;
                        probabilidadMutacion += 10;
                    } else {
                        variedaPorGeneracion = aux;
                        probabilidadMutacion -= 10;
                    }

                    // Ajustar probabilidad de mutación dentro del rango permitido
                    probabilidadMutacion = ajustarProbabilidadMutacion(probabilidadMutacion);

                    System.out.printf(
                            "Generación: %d | Mejor Fitness: %.2f | Diversidad: %.3f | Probabilidad Mutación: %d%%%n",
                            generacion, mejorFitness, aux, probabilidadMutacion);
                }
                registrarEstadisticas(poblacion, generacion);
            }
        }
    }

    /**
     * Ajusta la probabilidad de mutación para que esté dentro del rango 0-100.
     *
     * @param probabilidad Valor actual de la probabilidad.
     * @return Valor ajustado dentro del rango permitido.
     */
    private int ajustarProbabilidadMutacion(int probabilidad) {
        return Math.max(0, Math.min(probabilidad, 100));
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
        poblacion = seleccionarPoblacion(poblacion);
        poblacion = mutarPoblacion(poblacion);
        poblacion = cruzarPoblacion(poblacion);
        return poblacion;
    }

    private List<Individuo> seleccionarPoblacion(List<Individuo> poblacion) {
        // Implementación de selección de población (por ejemplo, selección por torneo)
        List<Individuo> seleccionados = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < poblacion.size(); i++) {
            Individuo individuo1 = poblacion.get(random.nextInt(poblacion.size()));
            Individuo individuo2 = poblacion.get(random.nextInt(poblacion.size()));
            seleccionados.add(individuo1.getFitness() > individuo2.getFitness() ? individuo1 : individuo2);
        }
        return seleccionados;
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
            if (random.nextDouble() < probabilidadMutacion / 100) {
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
        double promedioFitness = poblacion.stream().mapToDouble(Individuo::getFitness).average().orElse(0);
        promedioFitnessPorGeneracion.add(promedioFitness);

        try (FileWriter writer = new FileWriter("estadisticas.txt", true)) {
            writer.write(String.format("Generación %d: Promedio Fitness: %.2f - Hilo: %s%n", generacion,
                    promedioFitness, Thread.currentThread().getName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
