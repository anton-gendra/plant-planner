import os

import psycopg2

class DBController:

    def __init__(self):
        self.connection = psycopg2.connect(
            user=os.environ['DB_USER'],
            password=os.environ['DB_PASSWORD'],
            database=os.environ['DB_NAME'],
            host=os.environ['DB_HOST'],
            port=os.environ['DB_PORT']
        )