from flask import Flask, render_template, request
import folium

app = Flask(__name__)

# Datos de los departamentos y sus coordenadas
departamentos = {
    "Amazonas": (-6.231, -77.869),
    "Áncash": (-9.527, -77.528),
    "Apurímac": (-13.635, -72.878),
    "Arequipa": (-16.398, -71.536),
    "Ayacucho": (-13.158, -74.223),
    "Cajamarca": (-7.164, -78.505),
    "Callao": (-12.061, -77.133),
    "Cusco": (-13.525, -71.972),
    "Huancavelica": (-12.787, -74.972),
    "Huánuco": (-9.930, -76.242),
    "Ica": (-14.068, -75.728),
    "Junín": (-12.065, -75.205),
    "La Libertad": (-8.111, -79.028),
    "Lambayeque": (-6.772, -79.841),
    "Lima": (-12.046, -77.042),
    "Loreto": (-3.750, -73.253),
    "Madre de Dios": (-12.593, -69.185),
    "Moquegua": (-17.193, -70.937),
    "Pasco": (-10.683, -76.256),
    "Piura": (-5.194, -80.632),
    "Puno": (-15.840, -70.021),
    "San Martín": (-6.035, -76.974),
    "Tacna": (-18.013, -70.250),
    "Tumbes": (-3.566, -80.451),
    "Ucayali": (-8.379, -74.553),
}

# Conexiones entre los departamentos vecinos (distancias simuladas en km)
conexiones = [
    ("Lima", "Áncash", 300), ("Lima", "Junín", 200), ("Lima", "Ica", 300), 
    ("Lima", "Callao", 20), ("Áncash", "La Libertad", 300), ("Áncash", "Huánuco", 400),
    ("Cajamarca", "La Libertad", 200), ("Cajamarca", "Lambayeque", 150), 
    ("Cajamarca", "Amazonas", 300), ("Amazonas", "San Martín", 350),
    ("San Martín", "Loreto", 450), ("San Martín", "Huánuco", 400),
    ("Loreto", "Ucayali", 450), ("Ucayali", "Madre de Dios", 500),
    ("Huánuco", "Junín", 200), ("Huánuco", "Pasco", 150), ("Pasco", "Junín", 150),
    ("Junín", "Huancavelica", 150), ("Junín", "Ica", 300), ("Huancavelica", "Ayacucho", 200),
    ("Ayacucho", "Apurímac", 250), ("Ayacucho", "Ica", 300), ("Apurímac", "Cusco", 300),
    ("Cusco", "Puno", 300), ("Cusco", "Madre de Dios", 500), ("Puno", "Arequipa", 400),
    ("Arequipa", "Moquegua", 150), ("Moquegua", "Tacna", 200),
    ("Piura", "Tumbes", 300), ("Piura", "Lambayeque", 200), 
    ("Lambayeque", "La Libertad", 400)
]

# Convertir conexiones en un grafo como diccionario de diccionarios
grafo = {dep: {} for dep in departamentos}
for u, v, w in conexiones:
    grafo[u][v] = w
    grafo[v][u] = w

# Implementación del algoritmo de Dijkstra
def dijkstra(graph, start, end):
    import heapq
    distances = {node: float('inf') for node in graph}
    distances[start] = 0
    priority_queue = [(0, start)]
    previous_nodes = {node: None for node in graph}

    while priority_queue:
        current_distance, current_node = heapq.heappop(priority_queue)

        if current_distance > distances[current_node]:
            continue

        for neighbor, weight in graph[current_node].items():
            distance = current_distance + weight
            if distance < distances[neighbor]:
                distances[neighbor] = distance
                previous_nodes[neighbor] = current_node
                heapq.heappush(priority_queue, (distance, neighbor))

    # Reconstruir el camino
    path = []
    current = end
    while current:
        path.insert(0, current)
        current = previous_nodes[current]

    return path if distances[end] != float('inf') else None

# Generar el mapa con todas las rutas y resaltar la ruta mínima
def generar_mapa(ruta_minima=None):
    mapa = folium.Map(location=[-9.189967, -75.015152], zoom_start=6)

    for dep, coord in departamentos.items():
        folium.Marker(location=coord, popup=dep).add_to(mapa)

    for u, v, _ in conexiones:
        folium.PolyLine([departamentos[u], departamentos[v]], color="blue", weight=1.5).add_to(mapa)

    if ruta_minima:
        for i in range(len(ruta_minima) - 1):
            u, v = ruta_minima[i], ruta_minima[i + 1]
            folium.PolyLine([departamentos[u], departamentos[v]], color="red", weight=4.5).add_to(mapa)

    mapa.save("templates/mapa.html")

@app.route('/')
def index():
    return render_template("index.html", departamentos=list(departamentos.keys()))

@app.route('/ruta', methods=["POST"])
def calcular_ruta():
    origen = request.form["origen"]
    destino = request.form["destino"]

    ruta_minima = dijkstra(grafo, origen, destino)
    if ruta_minima:
        generar_mapa(ruta_minima)
        return render_template("mapa.html")
    else:
        return f"No existe una ruta válida entre {origen} y {destino}."

if __name__ == '__main__':
    app.run(debug=True)
