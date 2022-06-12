FROM python:3.10.5-slim-bullseye AS build

# update operating system packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
        build-essential gcc \
        postgresql-server-dev-all \
        postgresql-client-common postgresql-client-13


# install dependencies
WORKDIR /usr/app
COPY requirements.txt .

RUN python -m venv /usr/app/venv && \
    . venv/bin/activate && \
    pip install -U pip setuptools && \
    pip install -r requirements.txt

FROM python:3.10.5-slim-bullseye

# create a user to run the application
RUN groupadd -g 999 app && \
    useradd -u 999 -g app -d /usr/app app && \
    install -d -o app -g app /usr/app

# update operating system packages
RUN apt-get update && \
    apt-get install -y --no-install-recommends \
    

WORKDIR /usr/app

COPY --chown=app:app --from=build /usr/app/venv ./venv
COPY --chown=app:app djproj ./djproj
COPY --chown=app:app djapp ./djapp
COPY --chown=app:app manage.py .
EXPOSE 8000
USER app
CMD . venv/bin/activate && \
    uvicorn djproj.asgi:application --host 0.0.0.0 --workers 5
