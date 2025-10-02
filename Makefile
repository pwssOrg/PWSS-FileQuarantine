# Define the pom directory path
POM_DIR := fq

# Default target
.PHONY: all
all:
	@echo "Please use 'make build' to build the project."

# Build target
.PHONY: build
build:
	cd $(POM_DIR) && mvn clean install

# Test target
.PHONY: test
test:
	cd $(POM_DIR) && mvn clean test

# Clean target
.PHONY: clean
clean:
	cd $(POM_DIR) && mvn clean


# Help target
.PHONY: help
help:
	@echo "Available targets:"
	@echo "  make build	- Build the project using Maven"
	@echo "  make test	- Test the project using Maven"
	@echo "  make clean	- Clean the project using Maven"
	@echo "  make help	- Display this help message"