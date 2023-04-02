FROM python:3.10-slim

RUN apt-get update && apt-get install -y libpq-dev && apt-get install -y python3-dev
RUN apt-get install -y gcc g++ build-essential

COPY ./requirements.txt /app/

RUN pip3 install -r /app/requirements.txt
RUN rm /app/requirements.txt

COPY src/ /app

EXPOSE 8000

CMD ["uvicorn", "app.api:app", "--host", "0.0.0.0", "--port", "8000"]