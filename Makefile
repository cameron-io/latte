.PHONY: dev
dev:
	sudo docker compose up -d phpmyadmin
	./mvnw spring-boot:run

.PHONY: clean
clean:
	sudo docker compose down
