import hashlib
import datetime

class Block:
    def __init__(self, index, data, timestamp, previous_hash):
        self.index = index
        self.data = data
        self.timestamp = timestamp
        self.previous_hash = previous_hash
        self.hash = self.calculate_hash()

    def calculate_hash(self):
        raw = str(self.index) + str(self.data) + str(self.timestamp) + str(self.previous_hash)
        return hashlib.sha256(raw.encode()).hexdigest()

class Blockchain:
    def __init__(self):
        self.chain = [self.create_genesis_block()]
    
    def create_genesis_block(self):
        return Block(0, "Inicio de registros", datetime.datetime.now(), "0")
