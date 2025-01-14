COMPOSE_FILE=docker/docker-compose.yml
DC=docker compose -f $(COMPOSE_FILE)
PROJECT_ROOT := $(shell pwd)

help: ## Display this help message
	@echo "List of available commands:"
	@echo
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[32m%-20s\033[0m %s\n", $$1, $$2}'
	@echo
	@echo "Usage example:"
	@echo "  make up                 # Start services"
	@echo "  make build              # Build services"

build-jar: ## Build JAR files for all services
	@echo "Building JAR for gateway"
	cd $(PROJECT_ROOT)/gateway && ./gradlew build
	@echo "Building JAR for auth service"
	cd $(PROJECT_ROOT)/auth-service && ./gradlew build
	@echo "Building JAR for school service"
	cd $(PROJECT_ROOT)/school-service && ./gradlew build
	@echo "Building JAR for student service"
	cd $(PROJECT_ROOT)/student-service && ./gradlew build

up: ## Start services
	$(DC) up -d
	@echo "Services are up and running 🚀!"

down: ## Stop services
	$(DC) down
	@echo "Services are down 🛑!"

build: build-jar ## Build or rebuild services
	$(DC) build
	@echo "Services are built 🧩!"

no-cache: build-jar ## Build services without using cache
	$(DC) build --no-cache
	@echo "Services are built without cache 🧩!"

restart: ## Restart services
	$(DC) restart
	@echo "Services are restarted 🔄!"

start: ## Start services if stopped
	$(DC) start
	@echo "Services are started 🚀!"

stop: ## Stop services
	$(DC) stop
	@echo "Services are stopped 🛑!"

logs: ## View logs for services
	$(DC) logs -f
	@echo "Streaming logs for services 📜!"
