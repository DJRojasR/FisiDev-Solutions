import json
import matplotlib.pyplot as plt
from datetime import datetime
import os

with open("data/blockchain.json", "r", encoding="utf-8") as f:
    blocks = json.load(f)

ventas = []
fechas = []

for block in blocks[1:]:
    if "Venta" in block["data"]:
        monto = int(block["data"].split("S/")[1].split()[0])
        ventas.append(monto)
        fecha = datetime.fromisoformat(block["timestamp"])
        fechas.append(fecha.strftime("%d-%m %H:%M"))

os.makedirs("output", exist_ok=True)

plt.figure(figsize=(12, 6))
plt.bar(fechas, ventas, color="skyblue")
plt.title("Ventas registradas en la blockchain")
plt.xlabel("Fecha y hora")
plt.ylabel("Monto (S/)")
plt.xticks(rotation=45, ha="right")
plt.tight_layout()
plt.savefig("output/grafico_barras_ventas.png")
plt.show()
