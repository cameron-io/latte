.PHONY: dev
dev: env
	./mvnw spring-boot:run

.PHONY: env
env:
	cp .env.template .env
	docker compose up -d db

.PHONY: clean
clean:
	docker compose down
