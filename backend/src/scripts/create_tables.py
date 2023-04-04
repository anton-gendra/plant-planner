#!/usr/bin/env python3

from DBController import DBController
from scripts.table_queries import QUERIES

def create_tables():
    db = DBController()
    db.create_multiple_tables(QUERIES)

if __name__=='__main__':
    create_tables()