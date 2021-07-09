FROM node:alpine3.11 AS frontBuild
WORKDIR /usr/src/app
RUN apk update && apk add bash
COPY . .
RUN npm install -g @angular/cli
COPY . .
RUN npm install
COPY . .
RUN npm run build
