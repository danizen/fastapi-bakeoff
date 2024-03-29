FROM python:3.10.5-slim-bullseye AS build

# update operating system packages
RUN apt-get update && \
    apt-get install -y \
        --no-install-recommends build-essential gcc

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

WORKDIR /usr/app

COPY --chown=app:app --from=build /usr/app/venv ./venv
COPY --chown=app:app backend ./backend
COPY --chown=app:app manage.py .
EXPOSE 8080
USER app
CMD . venv/bin/activate && python manage.py runserver 0.0.0.0:8080
