import json
from datetime import datetime

with open("data/blockchain.json", "r", encoding="utf-8") as f:
    blocks = json.load(f)

for block in blocks:
    print(f"Bloque #{block['index']}")
    print(f"  Data: {block['data']}")
    timestamp = datetime.fromisoformat(block['timestamp'])
    print(f"  Fecha: {timestamp.strftime('%d-%m-%Y %H:%M:%S')}")
    print(f"  Hash: {block['hash']}")
    print("-" * 40)