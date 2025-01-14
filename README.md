# 🎓 School Project Microservices

> Microservices project for school, using Spring Boot, Consul, Grafana, Prometheus and Docker.

# 🚧 Installation

### ⚙️ Local

- `brew install consul`
- `consul agent -dev`
- start all services (gateway, school, student)

### 🐳 Docker

- `make build` or `make no-cache`
- `make up`

# 🧩 Features 

- [x] Service Discovery
- [x] Load Balancing
- [x] Circuit Breaker
- [x] Gateway
- [x] Monitoring and Logging

# 🚀 Grafana Dashboard

### 🌐 Gateway Panel
![img.png](docs/img.png)

### 💻 Service Panel
![img_1.png](docs/img_1.png)

