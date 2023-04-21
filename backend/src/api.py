from fastapi import FastAPI, Request, HTTPException

from DBController import DBController

app = FastAPI()

API_PATH = f"/plant-planner/api/"

db = DBController()

@app.get("/")
def info():
    return {'API_description': "Backend for the android plant-planner app."}

@app.put("/post/create")
def create_post(author: int):
    db.create_post(author)

    return {'status': "OK", 'detail': "Post added."}

@app.get("/post/{post_id}")
def get_post(post_id: int):
    return {'status': "OK", 'detail': "Not implemented yet."}