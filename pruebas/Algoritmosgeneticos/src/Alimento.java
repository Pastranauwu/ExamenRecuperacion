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
    private double calorias;

    /**
     * El valor nutricional del alimento.
     */
    private double valorNutricional;

    /**
     * Las calorías del alimento.
     */
    private double preferencia;

    /**
     * La preferencia del alimento.
     */
    private double cantidadEnGramos;

    private double proteina;

    private double carbohidratos;

    private double azuares;

    private double sodio;

    private double grasas;

    /**
     * Constructor para crear una instancia de Alimento.
     *
     * @param nombre El nombre del alimento.
     * @param peso El peso del alimento en gramos.
     * @param valorNutricional El valor nutricional del alimento.
     * @param calorias Las calorías del alimento.
     * @param preferencia La preferencia del alimento.
     */
    public Alimento(String nombre, double cantidadEnGramos, double valorNutricional, double calorias, double preferencia) {
        this.nombre = nombre;
        this.cantidadEnGramos = cantidadEnGramos;
        this.valorNutricional = valorNutricional;
        this.calorias = calorias;
        this.preferencia = preferencia;
    }

    public Alimento(String nombre, double cantidadEnGramos, double valorNutricional, double calorias, double preferencia, double proteina, double carbohidratos, double azuares, double sodio, double grasas) {
        this.nombre = nombre;
        this.cantidadEnGramos = cantidadEnGramos;
        this.valorNutricional = valorNutricional;
        this.calorias = calorias;
        this.preferencia = preferencia;
        this.proteina = proteina;
        this.carbohidratos = carbohidratos;
        this.azuares = azuares;
        this.sodio = sodio;
        this.grasas = grasas;
    }

    /**
     * Obtiene el nombre del alimento.
     *
     * @return El nombre del alimento.
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el valor nutricional del alimento.
     *
     * @return El valor nutricional del alimento.
     */
    public double getValorNutricional() {
        return valorNutricional;
    }

    public void setValorNutricional(double valorNutricional) {
        this.valorNutricional = valorNutricional;
    }

    /**
     * Obtiene las calorías del alimento.
     *
     * @return Las calorías del alimento.
     */
    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    /**
     * Obtiene la preferencia del alimento.
     *
     * @return La preferencia del alimento.
     */
    public double getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(double preferencia) {
        this.preferencia = preferencia;
    }

    public double getCantidadEnGramos() {
        return cantidadEnGramos;
    }

    public void setCantidadEnGramos(double cantidadEnGramos) {
        this.cantidadEnGramos = cantidadEnGramos;
    }

    public double getProteina() {
        return proteina;
    }

    public void setProteina(double proteina) {
        this.proteina = proteina;
    }

    public double getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }


    public void setAzuares(double azuares) {
        this.azuares = azuares;
    }

    public double getAzuares() {
        return azuares;
    }

    public double getSodio() {
        return sodio;
    }

    public void setSodio(double sodio) {
        this.sodio = sodio;
    }

    public double getGrasas() {
        return grasas;
    }

    public void setGrasas(double grasas) {
        this.grasas = grasas;
    }

    @Override
    public String toString() {
        return
            "\n  nombre='" + nombre + '\'' +
            ",\n  cantidadEnGramos=" + cantidadEnGramos +
            ",\n  valorNutricional=" + valorNutricional +
            ",\n  calorias=" + calorias +
            ",\n  preferencia=" + preferencia +
            ",\n  proteina=" + proteina +
            ",\n  carbohidratos=" + carbohidratos +
            ",\n  azuares=" + azuares +
            ",\n  sodio=" + sodio +
            ",\n  grasas=" + grasas;
    }
}
