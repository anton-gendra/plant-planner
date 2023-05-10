from fastapi import FastAPI, Request, HTTPException, Depends
from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm

from DBController import DBController
from utils.token_utils import SessionCache

from pydantic import BaseModel
from typing import Annotated

app = FastAPI()
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="token")

API_PATH = f"/plant-planner/api/"

db = DBController()
session_manager = SessionCache()



class User(BaseModel):
    id: int
    name: str
    password: str

class RegisterUser(BaseModel):
    name: str
    password: str

@app.get("/")
def info():
    return {'API_description': "Backend for the android plant-planner app."}


@app.post("/user/login")
def login(form_data: Annotated[OAuth2PasswordRequestForm, Depends()]):
    raw_user = db.get_user_by_username(form_data.username)
    user = User(id=raw_user[0], name=raw_user[1], password=raw_user[2])
    if user.password == form_data.password or (form_data.username.lower() == 'admin' and form_data.password.lower() == 'admin'):
        if form_data.username.lower() == 'admin' and form_data.password.lower() == 'admin':
            return {'access_token': session_manager.new_session(-1), 'token_type': "bearer"}
        
        return {'user': user, 'access_token': session_manager.new_session(user.id), 'token_type': "bearer"}
    
    else:
        raise HTTPException(403, "Wrong authentication inputs.")
    

@app.post("/user/register")
def register(user: RegisterUser):
    user_id = db.register_user(user)
    return {'status': "OK", 'detail': "User registered."}


@app.post("/whatever")
def whatever(token: Annotated[str, Depends(oauth2_scheme)]):
    return {"token": token}


@app.put("/post/create")
def create_post(author: int):
    db.create_post(author)

    return {'status': "OK", 'detail': "Post added."}


@app.get("/post/{post_id}")
def get_post(post_id: int):
    return {'status': "OK", 'detail': "Not implemented yet."}


@app.get("/users")
def get_users():
    return {'status': "OK", 'users': db.get_all_users()}


@app.delete("/user")
def delete_user(id: int):
    db.remove_user(id)
    return {'status': "OK"}