// import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.Set;
import java.util.HashSet;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
        List<Alimento> alimentos = new ArrayList<>();
        JSONParser parser = new JSONParser();
        FileReader reader = new FileReader("src/alimentos_modificados.json");
        Object obj = parser.parse(reader);
        JSONObject pJsonObj = (JSONObject)obj;
        JSONArray array = (JSONArray)pJsonObj.get("alimentos");

        //Checar si no hay alimentos con nombre repetido
        // Usar un HashSet para verificar nombres duplicados
        Set<String> nombresSet = new HashSet<>();
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonAlimentos = (JSONObject) array.get(i);
            String nombre = (String) jsonAlimentos.get("nombre");
            if (!nombresSet.add(nombre)) {
                System.out.println(nombre);
                System.out.println("Error: Hay alimentos con nombres repetidos");
                return;
            }
        }
        
        //Generar lista de alimentos
        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonAlimentos = (JSONObject) array.get(i);
            
            String nombre = (String) jsonAlimentos.get("nombre");
            
            // Verificar si los valores no son nulos antes de convertirlos
            Double peso = jsonAlimentos.get("peso") != null ? ((Number) jsonAlimentos.get("peso")).doubleValue() : 0.0;
            Double valorNutricional = jsonAlimentos.get("valorNutricional") != null ? ((Number) jsonAlimentos.get("valorNutricional")).doubleValue() : 0.0;
            Double calorias = jsonAlimentos.get("calorias") != null ? ((Number) jsonAlimentos.get("calorias")).doubleValue() : 0.0;
            Double preferencia = jsonAlimentos.get("preferencia") != null ? ((Number) jsonAlimentos.get("preferencia")).doubleValue() : 0.0;
            Double gramos = jsonAlimentos.get("cantidadEnGramos") != null ? ((Number) jsonAlimentos.get("cantidadEnGramos")).doubleValue() : 0.0;
        
            // Crear un nuevo objeto Alimento y agregarlo a la lista
            // System.out.println( i +") Nombre: " + nombre + " Peso: " + peso + " Valor Nutricional: " + valorNutricional + " Calorias: " + calorias);
            alimentos.add(new Alimento(nombre, peso, valorNutricional, calorias, preferencia, gramos));
        }

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
            }
        }

        System.out.println("Mejor solución encontrada: " + mejorGlobal.getFitness() + " de fitness."
                + "\nAlimentos seleccionados:");
        double precioTotal = 0.0;
        for (Alimento alimento : mejorGlobal.getSeleccion()) {
            System.out.println(alimento.getNombre() + " - Precio: " + alimento.getPeso() + " MXN " + " Calorías: "
                    + alimento.getCalorias() + " kcal, Valor Nutricional: " + alimento.getValorNutricional() + ", Preferencia: " + alimento.getPreferencia() + " gramos: " + alimento.getGramos());
            precioTotal += alimento.getPeso();
        }
        System.out.println("Precio total: " + precioTotal + " MXN, Calorías totales: "
                + mejorGlobal.getCaloriasTotales() + " Valor nutricional " + mejorGlobal.getValorNutricionalTotal());
    }
}
