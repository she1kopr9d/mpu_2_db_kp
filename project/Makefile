COMPOSE_FILE = deploy/docker-compose.yml

.PHONY: help db-up db-down db-logs db-reset db-clean

help:
	@echo "Доступные команды:"
	@echo "  make db-up     - Запустить БД и MyPHPAdmin (в фоне)"
	@echo "  make db-down   - Остановить контейнеры (данные сохранятся)"
	@echo "  make db-logs   - Посмотреть логи MySQL"
	@echo "  make db-reset  - Перезапустить БД с нуля (удалит все данные)"
	@echo "  make db-clean  - Полностью удалить контейнеры и папку с данными"

db-up:
	docker-compose -f $(COMPOSE_FILE) up -d
	@echo "========================================="
	@echo "MySQL доступен на порту 3306"
	@echo "MyPHPAdmin доступен на http://localhost:8080"
	@echo "  Система: MySQL"
	@echo "  Сервер: mysql-db"
	@echo "  Пользователь: root"
	@echo "  Пароль: rootpassword"
	@echo "  База данных: kafedra_db"
	@echo "========================================="

db-down:
	docker-compose -f $(COMPOSE_FILE) down

db-logs:
	docker-compose -f $(COMPOSE_FILE) logs -f mysql-db

db-reset:
	docker-compose -f $(COMPOSE_FILE) down
	sudo rm -rf database/data/*
	docker-compose -f $(COMPOSE_FILE) up -d
	@echo "БД полностью пересоздана."

db-clean:
	docker-compose -f $(COMPOSE_FILE) down -v
	sudo rm -rf database/data/*
	@echo "Все контейнеры и данные удалены."