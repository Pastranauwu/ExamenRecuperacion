import java.util.List;

/**
 * La clase Individuo representa un individuo en un algoritmo genético.
 * Cada individuo tiene una selección de alimentos y un valor de fitness asociado.
 */
public class Individuo {
    // Lista de alimentos seleccionados por el individuo.
    private List<Alimento> seleccion;
    
    // Valor de fitness del individuo.
    private double fitness;

    // Calorías recomendadas diarias (valor de ejemplo).
    private static final double CALORIAS_RECOMENDADAS = 2000;
    
    // Penalización por calorías excedidas (valor de ejemplo).
    private static final double PENALIZACION_CALORIAS = 0.3;
    
    // Peso máximo permitido para la selección de alimentos (valor de ejemplo).
    private static final double MAX_PESO = 700;

    /**
     * Constructor de la clase Individuo.
     * Inicializa la selección de alimentos y calcula el valor de fitness.
     *
     * @param seleccion Lista de alimentos seleccionados.
     */
    public Individuo(List<Alimento> seleccion) {
        this.seleccion = seleccion;
        calcularFitness();
    }

    /**
     * Calcula el valor de fitness del individuo basado en la selección de alimentos.
     * El valor de fitness se calcula considerando el peso total, el valor nutricional
     * y las calorías totales de los alimentos seleccionados.
     * Si el peso total excede el máximo permitido, el valor de fitness se establece en 0.
     */
    public void calcularFitness() {
        double pesoTotal = 0;
        double valorNutricionalTotal = 0;
        double caloriasTotal = 0;
    
        for (Alimento alimento : seleccion) {
            pesoTotal += alimento.getPeso();
            valorNutricionalTotal += alimento.getValorNutricional() * alimento.getPreferencia();
            caloriasTotal += alimento.getCalorias();
        }
    
        if (pesoTotal > MAX_PESO || fitness < 0) {
            fitness = 0; // Penalización por exceder el presupuesto.
        } else {
            // Ajustar la fórmula para dar más relevancia al valor nutricional
            fitness = (5 * valorNutricionalTotal) - penalizacionPorCalorias(caloriasTotal);
        }
    }
    

    /**
     * Calcula la penalización por exceder las calorías recomendadas.
     *
     * @param caloriasTotal Calorías totales de los alimentos seleccionados.
     * @return Penalización calculada.
     */
    private double penalizacionPorCalorias(double caloriasTotal) {
        return Math.max(0, caloriasTotal - CALORIAS_RECOMENDADAS) * PENALIZACION_CALORIAS;
    }

    /**
     * Obtiene el valor de fitness del individuo.
     *
     * @return Valor de fitness.
     */
    public double getFitness() { return fitness; }

    /**
     * Obtiene la selección de alimentos del individuo.
     *
     * @return Lista de alimentos seleccionados.
     */
    public List<Alimento> getSeleccion() { return seleccion; }


    public void setSeleccion(List<Alimento> seleccion) { this.seleccion = seleccion; calcularFitness();}

    public double getCaloriasTotales() {

        double caloriasTotales = 0.0;

        for (Alimento alimento : getSeleccion()) {

            caloriasTotales += alimento.getCalorias();

        }

        return caloriasTotales;

    }

    public double getValorNutricionalTotal() {

        double valorNutricionalTotal = 0.0;

        for (Alimento alimento : getSeleccion()) {

            valorNutricionalTotal += alimento.getValorNutricional();

        }

        return valorNutricionalTotal;

    }
}
