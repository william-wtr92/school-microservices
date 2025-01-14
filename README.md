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

- [x] Service Discovery (Consul)
- [x] Load Balancing (Spring Cloud Loadbalancer)
- [x] Circuit Breaker (Resilience4j)
- [x] Gateway (Spring Cloud Gateway)
- [x] Monitoring and Logging (Prometheus, Grafana)

# 🚀 Grafana Dashboard (id: 4701)

### 🌐 Gateway Panel
![grafana-gateway.png](docs/grafana-gateway.png)

### 💻 Service Panel
![grafana-service.png](docs/grafana-service.png)

