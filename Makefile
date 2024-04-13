.PHONY: dev
dev: env
	./mvnw spring-boot:run

.PHONY: env
env:
	docker compose up -d mariadb

.PHONY: clean
clean:
	docker compose down
