from fastapi import FastAPI, Request, HTTPException

from DBController import DBController

app = FastAPI()

API_PATH = f"/plant-planner/api/"

@app.get("/")
def info():
    return {'API_description': "Backend for the android plant-planner app."}
