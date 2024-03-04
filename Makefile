.PHONY: dev
dev: env
	./mvnw spring-boot:run

.PHONY: env
env:
	sudo docker compose up -d phpmyadmin

.PHONY: clean
clean:
	sudo docker compose down
