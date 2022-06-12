FROM python:3.10.5-slim-bullseye AS build

# install build issentials and postgresql headers
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

# install libpg.so.5, and a user to run the application
RUN apt-get update && \
    apt-get install -y --no-install-recommends libpq5 && \
    groupadd -g 999 app && \
    useradd -u 999 -g app -d /usr/app app && \
    install -d -o app -g app /usr/app

WORKDIR /usr/app

COPY --chown=app:app --from=build /usr/app/venv ./venv
COPY --chown=app:app djproj ./djproj
COPY --chown=app:app djapp ./djapp
COPY --chown=app:app manage.py .
EXPOSE 8000
USER app
CMD . venv/bin/activate && \
    uvicorn djproj.asgi:application --host 0.0.0.0 --workers 5
