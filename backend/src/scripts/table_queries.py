QUERIES = [
    """DROP TABLE IF EXISTS comment CASCADE;""",
    """DROP TABLE IF EXISTS post CASCADE;""",
    """DROP TABLE IF EXISTS user_profile CASCADE;""",
    """CREATE TABLE IF NOT EXISTS user_profile (
        id SERIAL NOT NULL PRIMARY KEY,
        name VARCHAR(32) NOT NULL,
        password VARCHAR(64) NOT NULL
    );""",
    """CREATE TABLE IF NOT EXISTS post (
        id SERIAL NOT NULL PRIMARY KEY,
        title VARCHAR(32) NOT NULL,
        location VARCHAR(32) NOT NULL,
        image VARCHAR(32) NOT NULL,
        author BIGINT NOT NULL REFERENCES user_profile(id)
    );""",
    """CREATE TABLE IF NOT EXISTS comment (
        id SERIAL NOT NULL PRIMARY KEY,
        date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
        text VARCHAR(1024) NOT NULL,
        author BIGINT NOT NULL REFERENCES user_profile(id),
        replies_to BIGINT REFERENCES comment(id),
        post BIGINT NOT NULL REFERENCES post(id)
    );""",
]