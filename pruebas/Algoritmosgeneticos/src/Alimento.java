/**
 * La clase Alimento representa un alimento con varias propiedades nutricionales.
 */
public class Alimento {
    /**
     * El nombre del alimento.
     */
    private String nombre;

    /**
     * El peso del alimento en gramos.
     */
    private double peso;

    /**
     * El valor nutricional del alimento.
     */
    private double valorNutricional;

    /**
     * Las calorías del alimento.
     */
    private double calorias;

    /**
     * La preferencia del alimento.
     */
    private double preferencia;

    /**
     * Constructor para crear una instancia de Alimento.
     *
     * @param nombre El nombre del alimento.
     * @param peso El peso del alimento en gramos.
     * @param valorNutricional El valor nutricional del alimento.
     * @param calorias Las calorías del alimento.
     * @param preferencia La preferencia del alimento.
     */
    public Alimento(String nombre, double peso, double valorNutricional, double calorias, double preferencia) {
        this.nombre = nombre;
        this.peso = peso;
        this.valorNutricional = valorNutricional;
        this.calorias = calorias;
        this.preferencia = preferencia;
    }

    /**
     * Obtiene el nombre del alimento.
     *
     * @return El nombre del alimento.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Obtiene el peso del alimento.
     *
     * @return El peso del alimento en gramos.
     */
    public double getPeso() {
        return peso;
    }

    /**
     * Obtiene el valor nutricional del alimento.
     *
     * @return El valor nutricional del alimento.
     */
    public double getValorNutricional() {
        return valorNutricional;
    }

    /**
     * Obtiene las calorías del alimento.
     *
     * @return Las calorías del alimento.
     */
    public double getCalorias() {
        return calorias;
    }

    /**
     * Obtiene la preferencia del alimento.
     *
     * @return La preferencia del alimento.
     */
    public double getPreferencia() {
        return preferencia;
    }
}
