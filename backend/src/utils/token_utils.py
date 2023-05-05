import secrets

class SessionCache:
    storage = {}

    def new_session(self, user_id):
        token = self._search_token(user_id)
        if token == -1:
            token = _generate_token(user_id)
            self.storage[token] = user_id
        
        return token

    def get_logged_user(self, token):
        return self.storage.get(token)
    
    def _search_token(self, user_id):
        for token in self.storage:
            if self.storage[token] == user_id:
                return token
            
        return -1
        

def _generate_token(id):
    return str(id) + secrets.token_urlsafe()