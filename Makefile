#debug flags:
#a for all debugging (same as make -d and make --debug).
#b for basic debugging.
#v for slightly more verbose basic debugging.
#i for implicit rules.
#j for invocation information.
#m for information during makefile remakes.
MAKEFLAGS += --no-print-directory
#MAKEFLAGS += --debug=v
# MAKEFLAGS += -s
include .env
export $(shell sed 's/=.*//' .env)
#default: help
.DEFAULT_GOAL := help
#.PHONY: all

pt: ## Run Posting with the project request collection
	posting --collection ./request-collection

back-run: ## Run the backend app
	cd back && mvn clean install spring-boot:run -DskipTests

# back-tests: ## Run the backend tests
# 	cd back && mvn clean test && open target/site/jacoco/index.html

back-tests: ## Run the backend tests
ifdef class
ifdef method
	cd back && mvn clean test -Dtest=$(class)#$(method) && open target/site/jacoco/index.html
else
	cd back && mvn clean test -Dtest=$(class) && open target/site/jacoco/index.html
endif
else
	cd back && mvn clean test && open target/site/jacoco/index.html
endif

back-open-tests-coverage: ## Open Jacoco tests coverage results
	cd back && open back/target/site/jacoco/index.html

front-run: ## run the frontend app
	export SERVER_ADDRESS="${address}"
	cd front && npm install && ng serve --host 0.0.0.0

run: ## Run all services
	make -j2 back-run front-run

help: ## This menu
	@echo "Usage: make [target]"
	@echo
	@echo "Available targets:"
	@echo
	@echo "---------- $(PRIMARY_COLOR)App commands$(END_COLOR)"
	@awk -F ':|##' '/^app-.*?:.*?##/ && !/##hidden/ {printf "$(SUCCESS_COLOR)%-30s$(END_COLOR) %s\n", $$1, $$NF}' $(MAKEFILE_LIST) | sort
	@echo
	@echo "---------- $(PRIMARY_COLOR)Backend commands$(END_COLOR)"
	@awk -F ':|##' '/^back-.*?:.*?##/ && !/##hidden/ {printf "$(SUCCESS_COLOR)%-30s$(END_COLOR) %s\n", $$1, $$NF}' $(MAKEFILE_LIST) | sort
	@echo
	@echo "---------- $(PRIMARY_COLOR)Frontend commands$(END_COLOR)"
	@awk -F ':|##' '/^front-.*?:.*?##/ && !/##hidden/ {printf "$(SUCCESS_COLOR)%-30s$(END_COLOR) %s\n", $$1, $$NF}' $(MAKEFILE_LIST) | sort
