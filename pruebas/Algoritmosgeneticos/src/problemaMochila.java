import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * La clase problemaMochila representa un programa que utiliza un algoritmo
 * genético
 * para resolver el problema de la mochila, seleccionando una combinación óptima
 * de alimentos
 * basada en su precio, peso, calorías y valor nutricional.
 * 
 * El programa crea una lista de alimentos con sus respectivos atributos y
 * utiliza múltiples hilos
 * para ejecutar el algoritmo genético en paralelo. Luego, selecciona la mejor
 * solución encontrada
 * entre todos los hilos y muestra los alimentos seleccionados junto con su
 * precio total.
 * 
 */
public class problemaMochila {
    public static void main(String[] args) throws Exception {

        List<Alimento> alimentos = Arrays.asList(
                new Alimento("Manzana", 15, 0.8, 52, 0.8), // Saludable, bajo en calorías
                new Alimento("Pan", 25, 0.4, 250, 0.6), // Moderado en calorías
                new Alimento("Queso", 100, 0.9, 402, 0.9), // Alto en calorías, alto valor
                new Alimento("Pollo", 80, 0.85, 165, 0.9), // Rico en proteínas, moderado en calorías
                new Alimento("Pescado", 150, 0.95, 206, 0.95), // Alta calidad nutricional
                new Alimento("Arroz", 20, 0.87, 360, 0.7), // Alto en carbohidratos
                new Alimento("Frijoles", 30, 0.75, 347, 0.75), // Rico en proteínas y fibra
                new Alimento("Leche", 22, 0.8, 42, 0.8), // Rico en calcio, bajo en calorías
                new Alimento("Huevos", 40, 0.85, 155, 0.9), // Completo nutricionalmente
                new Alimento("Yogurt", 25, 0.8, 59, 0.85), // Bajo en calorías
                new Alimento("Carne de Res", 200, 0.88, 250, 0.88), // Proteínas de calidad, alto en calorías
                new Alimento("Plátano", 18, 0.7, 89, 0.8), // Energético, bajo en calorías
                new Alimento("Zanahoria", 10, 0.95, 41, 0.75), // Saludable, muy bajo en calorías
                new Alimento("Espinaca", 12, 0.92, 23, 0.7), // Muy saludable, bajo en calorías
                new Alimento("Nueces", 120, 0.9, 607, 0.9), // Altas en grasas saludables y calorías
                new Alimento("Avena", 35, 0.85, 389, 0.8), // Excelente para energía
                new Alimento("Cereal", 45, 0.7, 379, 0.8), // Energético
                new Alimento("Jamón", 90, 0.85, 145, 0.85), // Proteínas moderadas
                new Alimento("Atún", 35, 0.95, 116, 0.9), // Rico en proteínas, bajo en calorías
                new Alimento("Brocoli", 25, 0.8, 35, 0.78), // Muy saludable
                new Alimento("Papa", 20, 0.7, 77, 0.82), // Energético, moderado en calorías
                new Alimento("Aceite de Oliva", 150, 0.9, 884, 0.88), // Alto en calorías, grasas saludables
                new Alimento("Fresas", 60, 0.9, 32, 0.92), // Bajo en calorías, alto en antioxidantes
                new Alimento("Miel", 75, 0.8, 304, 0.9), // Alto en calorías, alto valor energético
                new Alimento("Pasta", 25, 0.7, 370, 0.8), // Rica en carbohidratos
                new Alimento("Helado", 70, 0.5, 200, 0.85), // Alto en calorías, moderado valor nutricional

                new Alimento("Chocolate", 60, 0.3, 546, 0.95),
                new Alimento("Galletas", 50, 0.3, 502, 0.87),
                new Alimento("Helado", 70, 0.2, 200, 0.85),
                new Alimento("Papas Fritas", 35, 0.2, 545, 0.8),
                new Alimento("Pizza", 150, 0.3, 266, 0.9),
                new Alimento("Hamburguesa", 180, 0.5, 295, 0.88),
                new Alimento("Refresco", 18, 0.01, 140, 0.9),
                new Alimento("Dona", 25, 0.1, 452, 0.85),
                new Alimento("Pastel", 120, 0.3, 300, 0.9),
                new Alimento("Caramelos", 10, 0.01, 394, 0.75),
                new Alimento("Cereal Azucarado", 45, 0.04, 379, 0.8),
                new Alimento("Hot Dog", 100, 0.3, 250, 0.85),
                new Alimento("Tacos", 90, 0.5, 226, 0.88),
                new Alimento("Empanadas", 80, 0.2, 300, 0.85),
                new Alimento("Nachos", 60, 0.2, 400, 0.88),
                new Alimento("Croissant", 50, 0.2, 406, 0.87),
                new Alimento("Pollo Frito", 180, 0.5, 600, 0.9),
                new Alimento("Churros", 40, 0.1, 450, 0.85),
                new Alimento("Salchichas", 50, 0.4, 300, 0.82),
                new Alimento("Queso Amarillo", 80, 0.2, 402, 0.85),
                new Alimento("Mayonesa", 70, 0.3, 680, 0.75),
                new Alimento("Donas de Chocolate", 30, 0.1, 450, 0.88),
                new Alimento("Tarta de Fresa", 150, 0.2, 320, 0.9),
                new Alimento("Brownie", 100, 0.1, 467, 0.85));

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
        while (!executor.isTerminated()) {
        }

        Individuo mejorGlobal = null;
        for (AlgoritmoGenetico hilo : hilos) {
            if (mejorGlobal == null || (hilo.getMejorIndividuo() != null &&
                    hilo.getMejorIndividuo().getFitness() > mejorGlobal.getFitness())) {
                mejorGlobal = hilo.getMejorIndividuo();
                // System.out.println("Mejor solución encontrada en hilo: " +
                // hilo.getMejorIndividuo());
            }
        }

        System.out.println("Mejor solución encontrada: " + mejorGlobal.getFitness() + " de fitness."
                + "\nAlimentos seleccionados:");
        double precioTotal = 0.0;
        for (Alimento alimento : mejorGlobal.getSeleccion()) {
            System.out.println(alimento.getNombre() + " - Precio: " + alimento.getPeso() + " MXN " + " Calorías: "
                    + alimento.getCalorias() + " kcal, Valor Nutricional: " + alimento.getValorNutricional());
            precioTotal += alimento.getPeso();
        }
        System.out.println("Precio total: " + precioTotal + " MXN, Calorías totales: "
                + mejorGlobal.getCaloriasTotales() + " Valor nutricional " + mejorGlobal.getValorNutricionalTotal());
    }
}
