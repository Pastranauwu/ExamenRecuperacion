import matplotlib.pyplot as plt
import re
from collections import defaultdict

# Leer el archivo
file_path = "estadisticas.txt"
with open(file_path, 'r') as file:
    data = file.read()

# Expresión regular para extraer datos
pattern = r"Generación (\d+): Promedio Fitness: ([\d\.\-]+) - Hilo: ([\w\-]+)"
matches = re.findall(pattern, data)
# Estructura para almacenar datos por hilo
hilos_data = defaultdict(lambda: {'generations': [], 'fitness': []})

# Parsear los datos y almacenarlos por hilo
for match in matches:
    generation = int(match[0])
    fitness = float(match[1])
    hilo = match[2]
    hilos_data[hilo]['generations'].append(generation)
    hilos_data[hilo]['fitness'].append(fitness)

# Configuración de la gráfica
fig, axes = plt.subplots(nrows=4,ncols=2, figsize=(16, 12), constrained_layout=True)
axes = axes.flatten()  # Aplanar los ejes para iterar más fácilmente

# Graficar cada hilo
for ax, (hilo, data) in zip(axes, hilos_data.items()):
    ax.plot(data['generations'], data['fitness'], label=f'Hilo: {hilo}', marker='o', linestyle='-')
    ax.set_title(f'Desempeño del {hilo}', fontsize=12)
    ax.set_xlabel('Generación', fontsize=10)
    ax.set_ylabel('Promedio Fitness', fontsize=10)
    ax.grid(True, linestyle='--', linewidth=0.5)
    ax.legend(fontsize=8)

# Ocultar ejes sobrantes si hay menos de 6 hilos
for ax in axes[len(hilos_data):]:
    ax.axis('off')

# Mostrar la gráfica
plt.suptitle('Desempeño del Algoritmo Genético por Hilo', fontsize=16)
plt.show()
