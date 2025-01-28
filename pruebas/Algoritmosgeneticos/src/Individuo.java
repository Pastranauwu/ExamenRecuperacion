import java.util.List;

/**
 * La clase Individuo representa un individuo en un algoritmo genético.
 * Cada individuo tiene una selección de alimentos y un valor de fitness
 * asociado.
 */
public class Individuo {
    // Lista de alimentos seleccionados por el individuo.
    private List<Alimento> seleccion;

    // Valor de fitness del individuo.
    private double fitness;

    // Calorías recomendadas diarias.

    private static int calorias_recomendadas = 2000;

    // Penalización por calorías excedidas.
    // 0.9: calorías medianamente importantes, 0.2: poco importantes, 1.5: muy importantes.
    private static double penalizacionCalorias = 0.9;

    // Peso máximo permitido para la selección de alimentos.
    private static final double MAX_PESO = 1200;

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
    public Individuo() {

    }

    /**
     * Calcula el valor de fitness del individuo basado en la selección de alimentos.
     * Se considera el peso total, valor nutricional, calorías totales y posibles penalizaciones.
     */
    public void calcularFitness() {
        double pesoTotal = getPesoTotal();
        double valorNutricionalTotal = getValorNutricionalTotal();
        double caloriasTotal = getCaloriasTotales();

        if (pesoTotal > MAX_PESO) {
            // Penalización proporcional al exceso de peso.
            fitness = (MAX_PESO / pesoTotal) * valorNutricionalTotal;
        } else {
            // Calcular el fitness basado en valor nutricional y calorías.
            fitness = (10 * valorNutricionalTotal)
                    - penalizacionPorCalorias(caloriasTotal)
                    + bonificacionPorCaloriasAdecuadas(caloriasTotal);
        }
    }

    /**
     * Calcula la bonificación por mantener las calorías cercanas al objetivo.
     *
     * @param caloriasTotal Calorías totales de los alimentos seleccionados.
     * @return Bonificación calculada.
     */
    private double bonificacionPorCaloriasAdecuadas(double caloriasTotal) {
        double diferencia = Math.abs(caloriasTotal - calorias_recomendadas);
        if (diferencia <= 100) {
            return 10; // Bonificación máxima si está dentro de 100 calorías.
        } else if (diferencia <= 300) {
            return 5; // Bonificación reducida si está dentro de 300 calorías.
        }
        return 0; // Sin bonificación si está muy lejos.
    }

    /**
     * Calcula la penalización por exceder las calorías recomendadas.
     *
     * @param caloriasTotal Calorías totales de los alimentos seleccionados.
     * @return Penalización calculada.
     */
    private double penalizacionPorCalorias(double caloriasTotal) {
        if (caloriasTotal <= calorias_recomendadas) {
            return 0;
        }
        return (caloriasTotal - calorias_recomendadas) * penalizacionCalorias;
    }

    /**
     * Calcula el peso total de los alimentos seleccionados.
     *
     * @return Peso total.
     */
    public double getPesoTotal() {
        return seleccion.stream().mapToDouble(Alimento::getPeso).sum();
    }

    /**
     * Calcula el valor nutricional total de los alimentos seleccionados.
     *
     * @return Valor nutricional total.
     */
    public double getValorNutricionalTotal() {
        return seleccion.stream()
                .mapToDouble(alimento -> alimento.getValorNutricional() * alimento.getPreferencia())
                .sum();
    }

    /**
     * Calcula el total de calorías de los alimentos seleccionados.
     *
     * @return Calorías totales.
     */
    public double getCaloriasTotales() {
        return seleccion.stream().mapToDouble(Alimento::getCalorias).sum();
    }

    /**
     * Obtiene el valor de fitness del individuo.
     *
     * @return Valor de fitness.
     */
    public double getFitness() {
        return fitness;
    }

    /**
     * Obtiene la selección de alimentos del individuo.
     *
     * @return Lista de alimentos seleccionados.
     */
    public List<Alimento> getSeleccion() {
        return seleccion;
    }

    /**
     * Establece una nueva selección de alimentos y recalcula el fitness.
     *
     * @param seleccion Lista de alimentos seleccionados.
     */
    public void setSeleccion(List<Alimento> seleccion) {
        this.seleccion = seleccion;
        calcularFitness();
    }

    public static void setCalorias_recomendadas(int calorias) {
        calorias_recomendadas = calorias;
    }

    /**
     * Establece la penalización por calorías para el cálculo de fitness.
     *
     * @param penalizacion Nuevo valor de penalización.
     */
    public static void setPenalizacionCalorias(double penalizacion) {
        penalizacionCalorias = penalizacion;
    }
}
