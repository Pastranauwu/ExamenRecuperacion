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
            new Alimento("Manzana", 15, 5.0, 50.0, 0.8),   // Saludable
            new Alimento("Pan", 25, 3.0, 100.0, 0.6),     // Saludable
            new Alimento("Queso", 100, 8.0, 200.0, 0.9),  // Saludable
            new Alimento("Pollo", 80, 20.0, 300.0, 0.9),  // Saludable
            new Alimento("Pescado", 150, 22.0, 250.0, 0.95), // Saludable
            new Alimento("Arroz", 20, 15.0, 350.0, 0.7),  // Saludable
            new Alimento("Frijoles", 30, 14.0, 300.0, 0.75), // Saludable
            new Alimento("Leche", 22, 9.0, 150.0, 0.8),   // Saludable
            new Alimento("Huevos", 40, 6.0, 70.0, 0.9),   // Saludable
            new Alimento("Yogurt", 25, 5.5, 110.0, 0.85), // Saludable
            new Alimento("Carne de Res", 180, 25.0, 400.0, 0.88), // Saludable
            new Alimento("Plátano", 18, 10.0, 100.0, 0.8), // Saludable
            new Alimento("Zanahoria", 10, 4.0, 50.0, 0.75), // Saludable
            new Alimento("Espinaca", 12, 3.0, 30.0, 0.7), // Saludable
            new Alimento("Nueces", 120, 7.0, 200.0, 0.9), // Saludable
            new Alimento("Avena", 35, 12.0, 300.0, 0.8), // Saludable
            new Alimento("Chocolate", 60, 2.0, 250.0, 0.95), // Saludable
            new Alimento("Cereal", 45, 8.0, 180.0, 0.8), // Saludable
            new Alimento("Jamón", 90, 15.0, 120.0, 0.85), // Saludable
            new Alimento("Atún", 35, 18.0, 220.0, 0.9), // Saludable
            new Alimento("Brocoli", 25, 5.0, 40.0, 0.78), // Saludable
            new Alimento("Papa", 20, 13.0, 350.0, 0.82), // Saludable
            new Alimento("Aceite de Oliva", 150, 20.0, 500.0, 0.88), // Saludable
            new Alimento("Fresas", 60, 6.0, 80.0, 0.92), // Saludable
            new Alimento("Miel", 75, 5.0, 200.0, 0.9), // Saludable
            new Alimento("Pasta", 25, 14.0, 320.0, 0.8), // Saludable
            new Alimento("Galletas", 50, 4.0, 240.0, 0.87), // Saludable
        
            // Alimentos no saludables
            new Alimento("Pizza", 250, 1.0, 600.0, 0.95),  // No saludable
            new Alimento("Hamburguesa", 100, 1.5, 800.0, 0.9), // No saludable
            new Alimento("Hot Dog", 40, 1.0, 500.0, 0.85), // No saludable
            new Alimento("Refresco", 20, 0.1, 150.0, 0.9), // No saludable
            new Alimento("Papas Fritas", 20, 0.8, 400.0, 0.9), // No saludable
            new Alimento("Dona", 60, 0.2, 300.0, 0.8), // No saludable
            new Alimento("Pastel", 180, 1.0, 700.0, 0.92), // No saludable
            new Alimento("Tacos", 80, 2.0, 450.0, 0.88), // No saludable
            new Alimento("Nachos", 90, 1.2, 550.0, 0.85), // No saludable
            new Alimento("Chocolates Grandes", 200, 0.8, 800.0, 0.9), // No saludable
            new Alimento("Malteada", 100, 0.5, 450.0, 0.85), // No saludable
            new Alimento("Churros", 30, 0.3, 350.0, 0.9), // No saludable
            new Alimento("Caramelos", 50, 0.1, 250.0, 0.87),// No saludable
            new Alimento("Helado", 70, 0.9, 270.0, 0.85)// No aludable
        );
        


        int numHilos = 8;
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
                // System.out.println("Mejor solución encontrada en hilo: " + hilo.getMejorIndividuo());
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
