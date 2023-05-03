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


    def create_multiple_tables(self, queries):
        with self.connection.cursor() as cur:
            for query in queries:
                try:
                    cur.execute(query)

                except psycopg2.Error as e:
                    print(f"\033[91mError happened while creating table:\033[0m\n\nQuery:\n{query}\n\n{e}")

        self.connection.commit()


    def create_post(self, author_id):
        query = """
            INSERT INTO post(author, likes)
            VALUES (%s, %s) RETURNING id;
        """
        with self.connection.cursor() as cur:
            try: 
                cur.execute(query, (author_id, 0))

            except psycopg2.Error as e:
                print(f"\033[91mError happened while creating table:\033[0m\n\nQuery:\n{query}\n\n{e}")

        self.connection.commit()

    def execute_single_query(self, query, args):
        with self.connection.cursor() as cur:
            try:
                cur.execute(query, args)
                return cur.fetchone()

            except psycopg2.Error as e:
                print(f"\033[91mError happened while executing query:\033[0m\n\nQuery:\n{query}\n\n{e}")


    def get_user_by_username(self, user_name: str):
        query = """
            SELECT id, name, password
            FROM user_profile
            WHERE name = %s;
        """

        return self.execute_single_query(query, [user_name])