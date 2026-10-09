# Stage 1: Build static assets
FROM node:18-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
ARG REACT_APP_BFF_URL=http://localhost:5000/api
ENV REACT_APP_BFF_URL=$REACT_APP_BFF_URL
RUN npm run build

# Stage 2: Nginx Web Server Runtime
FROM nginx:alpine
COPY --from=builder /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
