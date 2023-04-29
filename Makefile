check_defined = \
    $(strip $(foreach 1,$1, \
        $(call __check_defined,$1,$(strip $(value 2)))))
__check_defined = \
    $(if $(value $1),, \
      $(error Undefined $1$(if $2, ($2))))

$(call check_defined, DOCKER_HUB_ORG, dockerhub org name)


SERVICE_VERSION=0.02
IMAGE_NAME=http4k-functional

LOCAL_NAME=$(IMAGE_NAME):$(SERVICE_VERSION)
FULL_NAME=$(DOCKER_HUB_ORG)/$(IMAGE_NAME):$(SERVICE_VERSION)

build/native/nativeCompile/http4k-functional:
	./gradlew nativeCompile

build/.docker-image: build/native/nativeCompile/http4k-functional
	docker build -t $(LOCAL_NAME) -t $(FULL_NAME) .
	touch $@

.PHONY: build
build: build/.docker-image

.PHONY: run
run: build
	docker run --name http4k-functional -p 8081:8081 $(LOCAL_NAME)

.PHONY: push
push: build
	docker push $(FULL_NAME)
