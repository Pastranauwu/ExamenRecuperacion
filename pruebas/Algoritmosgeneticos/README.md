# Problema de la Mochila y Algoritmos Genéticos

## Introducción

El problema de la mochila es un problema clásico de optimización combinatoria. En este problema, se tiene una mochila con una capacidad limitada y un conjunto de objetos, cada uno con un peso y un valor. El objetivo es determinar la combinación de objetos que maximiza el valor total sin exceder la capacidad de la mochila.

## Algoritmos Genéticos

Los algoritmos genéticos son técnicas de búsqueda y optimización inspiradas en los principios de la evolución natural. Utilizan conceptos como selección, cruce y mutación para iterativamente mejorar una población de soluciones candidatas.

## Aplicación en el Proyecto

Este proyecto en Java implementa un algoritmo genético para resolver el problema de la mochila, aplicado directamente a un problema de maximizacion en una dieta segun gustos, preferencias y presupuesto.

### Estructura del Proyecto

- **src/**: Contiene el código fuente del proyecto.
    - **Main.java**: Punto de entrada del programa.
        - **AlgoritmoGenetico.java**: Implementación del algoritmo genético, encargado de las poblaciones, sus mutaciones y cruces.
        - **ProblemaMochila.java**: Genera los datos a partir de un JSON para el ArrayList e imprime la mejor solución.
        - **Individuo.java**: Representación de una solución dentro de su población.
        - **Alimento.java**: Estructura básica donde se guarda la información de cada alimento.

### Funcionamiento del Algoritmo

1. **Inicialización**: Se crea una población inicial de soluciones aleatorias.
2. **Evaluación**: Cada solución se evalúa en función de su valor total y peso.
3. **Selección**: Se seleccionan las mejores soluciones para reproducirse.
4. **Cruce**: Se combinan pares de soluciones para crear nuevas soluciones.
5. **Mutación**: Se aplican pequeñas modificaciones aleatorias a las nuevas soluciones.
6. **Reemplazo**: Las nuevas soluciones reemplazan a las antiguas en la población.
7. **Iteración**: El proceso se repite hasta alcanzar un criterio de parada.


## Optimización de Dieta

Este proyecto no solo se limita a resolver el problema de la mochila, sino que también se aplica a la optimización de una dieta. El algoritmo genético se utiliza para encontrar la mejor combinación de alimentos que maximice el valor nutricional, respete las preferencias personales, se ajuste al presupuesto disponible y mantenga las calorías dentro de un rango deseado.

### Parámetros Considerados

- **Presupuesto**: El costo total de los alimentos seleccionados no debe exceder el presupuesto disponible.
- **Valor Nutricional**: Se busca maximizar el valor nutricional total de la dieta.
- **Preferencias**: Se tienen en cuenta las preferencias personales para incluir o excluir ciertos alimentos.
- **Calorías**: La dieta debe cumplir con un rango específico de calorías diarias.

### Ejemplo de Uso

Para ejecutar el proyecto con un enfoque en la optimización de la dieta, compila y ejecuta el archivo `problemaMochila.java`. El programa imprimirá la mejor combinación de alimentos encontrada, su valor nutricional total, el costo y las calorías.

```sh
javac src/problemaMochila.java
java bin/Main
```

Es necesario importar la libreria para poder leer el formato JSON json-simple-1.1.1.jar

Este enfoque permite personalizar la dieta según las necesidades y restricciones individuales, proporcionando una herramienta poderosa para la planificación de dietas saludables y económicas.

## Conclusión

Este proyecto demuestra cómo los algoritmos genéticos pueden ser aplicados para resolver problemas complejos como el problema de la mochila. La implementación en Java proporciona una base sólida para experimentar con diferentes configuraciones y mejorar el rendimiento del algoritmo.

