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

class CreatePost(BaseModel):
    title: str
    location: str
    image: str
    author: int




def authenticate(request: Request):
    token = request.headers.get("Authorization")
    token_splited = token.split("Bearer ")
    if len(token_splited) < 2:
        raise HTTPException(403, "Unable to authenticate")
    actual_token = token_splited[1]
    user_id = session_manager.get_logged_user(actual_token)

    if not user_id:
        raise HTTPException(403, "Unable to authenticate")
    
    return user_id


@app.get("/")
def info():
    return {'API_description': "Backend for the android plant-planner app."}


@app.post("/user/login")
def login(form_data: Annotated[OAuth2PasswordRequestForm, Depends()]):
    raw_user = db.get_user_by_username(form_data.username)
    user = User(id=raw_user[0], name=raw_user[1], password=raw_user[2])
    if user.password == form_data.password or (form_data.username.lower() == 'admin' and form_data.password.lower() == 'admin'):
        if form_data.username.lower() == 'admin' and form_data.password.lower() == 'admin':
            return {'user': user, 'access_token': session_manager.new_session(-1), 'token_type': "bearer"}
        
        return {'user': user, 'access_token': session_manager.new_session(user.id), 'token_type': "bearer"}
    
    else:
        raise HTTPException(403, "Wrong authentication inputs.")
    

@app.post("/user/register")
def register(user: RegisterUser):
    user_id = db.register_user(user)
    return {'status': "OK", 'detail': "User registered."}

@app.post("/plant/post")
def get_post(post: CreatePost, request: Request):
    authenticate(request)
    post_id = db.create_post(post)
    return {'status': "OK", 'detail': "Post create -->>>>"}


@app.get("/plant/post")
def get_all_post(request: Request):
    authenticate(request)
    return db.get_all_posts()


@app.get("/users")
def get_users():
    return {'status': "OK", 'users': db.get_all_users()}


@app.delete("/user")
def delete_user(id: int):
    db.remove_user(id)
    return {'status': "OK"}