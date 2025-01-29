import java.util.List;

/**
 * La clase Individuo representa un individuo en un algoritmo genético.
 * Cada individuo tiene una selección de alimentos y un valor de fitness
 * asociado.
 */
/**
 * La clase Individuo representa un individuo en un algoritmo genético que
 * selecciona alimentos
 * y calcula su valor de fitness basado en varios factores nutricionales y
 * restricciones.
 */
public class Individuo {
    // Lista de alimentos seleccionados por el individuo.
    private List<Alimento> seleccion;

    // Valor de fitness del individuo.
    private double fitness;

    // Calorías recomendadas diarias.

    private static int calorias_recomendadas = 2000;

    // Peso máximo permitido para la selección de alimentos.
    private static double MAX_PESO = calorias_recomendadas;

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
     * Calcula el valor de fitness del individuo basado en la selección de
     * alimentos.
     * Se considera el peso total, valor nutricional, calorías totales y posibles
     * penalizaciones.
     */
    public void calcularFitness() {
        double pesoTotal = getPesoTotal();
        double valorNutricionalTotal = getValorNutricionalTotal();
        double caloriasTotal = getCaloriasTotales();
        double proteinaTotal = getProteinaTotal();
        double carbohidratosTotal = getCarbohidratosTotal();
        double sodioTotal = getSodioTotal();
        double grasasTotal = getGrasasTotal();
        double azucaresTotal = getAzucaresTotal();

        if (pesoTotal > MAX_PESO) {
            // Penalización proporcional al exceso de peso.
            fitness = (MAX_PESO / pesoTotal) * valorNutricionalTotal;
        } else {
            // Calcular el fitness basado en valor nutricional y calorías.
            fitness = (15 * valorNutricionalTotal)
                    + (10* bonificacionPorCaloriasAdecuadas(caloriasTotal))
                    + (2 * bonificacionPorProteinaAdecuada(proteinaTotal))
                    - (2 * penalizacionPorExcesoDeSodio(sodioTotal))
                    - penalizacionPorExcesoDeGrasas(grasasTotal)
                    - penalizacionPorExcesoDeCarbohidratos(carbohidratosTotal)
                    - penalizacionPorExcesoDeAzucares(azucaresTotal);
        }
    }

    private double bonificacionPorProteinaAdecuada(double proteinaTotal) {
        if (proteinaTotal >= 50 && proteinaTotal <= 70) {
            return 10; // Bonificación máxima si la proteína está en el rango adecuado.
        }
        return 0; // Sin bonificación si está fuera del rango.
    }

    private double bonificacionPorCaloriasAdecuadas(double caloriasTotal) {
        if (caloriasTotal >= 1800 && caloriasTotal <= 2200) {
            return 5; // Bonificación si las calorías están en el rango adecuado.
        }
        return 0; // Sin bonificación si está fuera del rango.
    }

    private double penalizacionPorExcesoDeSodio(double sodioTotal) {
        if (sodioTotal > 2300) {
            return (sodioTotal - 2300) * 0.1; // Penalización por exceso de sodio.
        }
        return 0; // Sin penalización si está dentro del límite.
    }

    private double penalizacionPorExcesoDeGrasas(double grasasTotal) {
        if (grasasTotal > 70) {
            return (grasasTotal - 70) * 0.1; // Penalización por exceso de grasas.
        }
        return 0; // Sin penalización si está dentro del límite.
    }

    private double penalizacionPorExcesoDeCarbohidratos(double carbohidratosTotal) {
        if (carbohidratosTotal > 300) {
            return (carbohidratosTotal - 300) * 0.1; // Penalización por exceso de carbohidratos.
        }
        return 0; // Sin penalización si está dentro del límite.
    }

    private double penalizacionPorExcesoDeAzucares(double azucaresTotal) {
        if (azucaresTotal > 50) {
            return (azucaresTotal - 50) * 0.1; // Penalización por exceso de azúcares.
        }
        return 0; // Sin penalización si está dentro del límite.
    }

    public double getProteinaTotal() {
        return seleccion.stream().mapToDouble(Alimento::getProteina).sum();
    }

    public double getCarbohidratosTotal() {
        return seleccion.stream().mapToDouble(Alimento::getCarbohidratos).sum();
    }

    public double getSodioTotal() {
        return seleccion.stream().mapToDouble(Alimento::getSodio).sum();
    }

    public double getGrasasTotal() {
        return seleccion.stream().mapToDouble(Alimento::getGrasas).sum();
    }

    public double getAzucaresTotal() {
        return seleccion.stream().mapToDouble(Alimento::getAzucares).sum();
    }

    /**
     * Calcula el peso total de los alimentos seleccionados.
     *
     * @return Peso total.
     */
    public double getPesoTotal() {
        return seleccion.stream().mapToDouble(Alimento::getCantidadEnGramos).sum();
    } // <-- Cierre de llave añadido

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

}
