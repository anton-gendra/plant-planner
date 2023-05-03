from functools import wraps

def is_logged(fun):
    @wraps(fun)
    def wrapper(*args, **kwargs):
        # TODO: Aquí se comprobará que el tokenes válido y si eso se retorna el id del usuario o el usuario y si no se lanza excepción o algo
        # Tengo que pensarlo de todas formas porque quizás hacer un decorador para esto puede dar problemas
        xd = 0
        return 0
    
    return wrapper