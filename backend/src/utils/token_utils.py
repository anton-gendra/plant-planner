import secrets

class SessionCache:
    storage = {}

    def new_session(self, user_id):
        token = generate_token(user_id)
        self.storage[token] = user_id
        return token

    def get_logged_user(self, token):
        return self.storage.get(token)
        

def generate_token(id):
    return str(id) + secrets.token_urlsafe()