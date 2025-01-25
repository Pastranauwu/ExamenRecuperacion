import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * La clase problemaMochila representa un programa que utiliza un algoritmo genético
 * para resolver el problema de la mochila, seleccionando una combinación óptima de alimentos
 * basada en su precio, peso, calorías y valor nutricional.
 * 
 * El programa crea una lista de alimentos con sus respectivos atributos y utiliza múltiples hilos
 * para ejecutar el algoritmo genético en paralelo. Luego, selecciona la mejor solución encontrada
 * entre todos los hilos y muestra los alimentos seleccionados junto con su precio total.
 * 
 */
public class problemaMochila {
    public static void main(String[] args) throws Exception {
        
        List<Alimento> alimentos = Arrays.asList(
    new Alimento("Manzana", 15, 5.0, 50.0, 0.8),   // 15 MXN/kg
    new Alimento("Pan", 25, 3.0, 100.0, 0.6),     // 25 MXN/paquete
    new Alimento("Queso", 100, 8.0, 200.0, 0.9),  // 100 MXN/500g
    new Alimento("Pollo", 80, 20.0, 300.0, 0.9),  // 80 MXN/kg
    new Alimento("Pescado", 150, 22.0, 250.0, 0.95), // 150 MXN/kg
    new Alimento("Arroz", 20, 15.0, 350.0, 0.7),  // 20 MXN/500g
    new Alimento("Frijoles", 30, 14.0, 300.0, 0.75), // 30 MXN/500g
    new Alimento("Leche", 22, 9.0, 150.0, 0.8),   // 22 MXN/litro
    new Alimento("Huevos", 40, 6.0, 70.0, 0.9),   // 40 MXN/docena
    new Alimento("Yogurt", 25, 5.5, 110.0, 0.85), // 25 MXN/vaso
    new Alimento("Carne de Res", 200, 25.0, 400.0, 0.88), // 200 MXN/kg
    new Alimento("Plátano", 18, 10.0, 100.0, 0.8), // 18 MXN/kg
    new Alimento("Zanahoria", 10, 4.0, 50.0, 0.75), // 10 MXN/kg
    new Alimento("Espinaca", 12, 3.0, 30.0, 0.7), // 12 MXN/atado
    new Alimento("Nueces", 120, 7.0, 200.0, 0.9), // 120 MXN/250g
    new Alimento("Avena", 35, 12.0, 300.0, 0.8), // 35 MXN/500g
    new Alimento("Chocolate", 60, 2.0, 250.0, 0.95), // 60 MXN/tabla
    new Alimento("Cereal", 45, 8.0, 180.0, 0.8), // 45 MXN/caja
    new Alimento("Jamón", 90, 15.0, 120.0, 0.85), // 90 MXN/500g
    new Alimento("Atún", 35, 18.0, 220.0, 0.9), // 35 MXN/lata
    new Alimento("Brocoli", 25, 5.0, 40.0, 0.78), // 25 MXN/kg
    new Alimento("Papa", 20, 13.0, 350.0, 0.82), // 20 MXN/kg
    new Alimento("Aceite de Oliva", 150, 20.0, 500.0, 0.88), // 150 MXN/botella
    new Alimento("Fresas", 60, 6.0, 80.0, 0.92), // 60 MXN/kg
    new Alimento("Miel", 75, 5.0, 300.0, 0.9), // 75 MXN/frasco
    new Alimento("Pasta", 25, 14.0, 320.0, 0.8), // 25 MXN/500g
    new Alimento("Helado", 70, 3.0, 270.0, 0.85), // 70 MXN/1L
    new Alimento("Galletas", 50, 4.0, 240.0, 0.87) // 50 MXN/paquete
);


        int numHilos = 4;
        ExecutorService executor = Executors.newFixedThreadPool(numHilos);
        AlgoritmoGenetico[] hilos = new AlgoritmoGenetico[numHilos];
        for (int i = 0; i < numHilos; i++) {
            hilos[i] = new AlgoritmoGenetico(alimentos);
        }

        Future<?>[] resultados = new Future<?>[numHilos];
        for (int i = 0; i < numHilos; i++) {
            resultados[i] = executor.submit(hilos[i]);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}

        Individuo mejorGlobal = null;
        for (AlgoritmoGenetico hilo : hilos) {
            if (mejorGlobal == null || (hilo.getMejorIndividuo() != null &&
                hilo.getMejorIndividuo().getFitness() > mejorGlobal.getFitness())) {
                mejorGlobal = hilo.getMejorIndividuo();
            }
        }

        System.out.println("Mejor solución encontrada: " + mejorGlobal.getFitness() + " de fitness." + "\nAlimentos seleccionados:");
        double precioTotal = 0.0;
        for (Alimento alimento : mejorGlobal.getSeleccion()) {
            System.out.println(alimento.getNombre() + " - Precio: " + alimento.getPeso() + " MXN " + " Calorías: " + alimento.getCalorias() + " kcal, Valor Nutricional: " + alimento.getValorNutricional());
            precioTotal += alimento.getPeso();
        }
        System.out.println("Precio total: " + precioTotal + " MXN");
    }
}
