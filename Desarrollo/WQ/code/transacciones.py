from blockchain import Blockchain
import datetime
import json
import os

os.makedirs("data", exist_ok=True)

mi_bloque = Blockchain()
base_time = datetime.datetime.now()

transacciones = [
    "Venta de S/150 a cliente 001",
    "Venta de S/280 a cliente 002",
    "Venta de S/320 a cliente 003",
    "Venta de S/200 a cliente 004",
    "Venta de S/175 a cliente 005",
    "Venta de S/220 a cliente 006",
    "Venta de S/310 a cliente 007",
    "Venta de S/145 a cliente 008",
    "Venta de S/260 a cliente 009",
    "Venta de S/400 a cliente 010"
]

for i, descripcion in enumerate(transacciones, start=1):
    tiempo = base_time + datetime.timedelta(minutes=i * 5)
    nuevo_bloque = mi_bloque.chain[-1].__class__(
        i,
        descripcion,
        tiempo,
        mi_bloque.chain[-1].hash
    )
    mi_bloque.chain.append(nuevo_bloque)

data_serializable = []
for block in mi_bloque.chain:
    data_serializable.append({
        "index": block.index,
        "data": block.data,
        "timestamp": block.timestamp.isoformat(),
        "previous_hash": block.previous_hash,
        "hash": block.hash
    })

with open("data/blockchain.json", "w", encoding="utf-8") as f:
    json.dump(data_serializable, f, indent=2)