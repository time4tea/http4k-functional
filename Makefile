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
FULL_NAME=$(DOCKER_HUB_ORG)/$(LOCAL_NAME)

IMAGE_NAME_JIB=$(IMAGE_NAME)-jib
LOCAL_NAME_JIB=$(IMAGE_NAME_JIB):$(SERVICE_VERSION)
FULL_NAME_JIB=$(DOCKER_HUB_ORG)/$(LOCAL_NAME_JIB)


build/native/nativeCompile/http4k-functional:
	./gradlew nativeCompile

build/.docker-image: build/native/nativeCompile/http4k-functional
	docker build -t $(LOCAL_NAME) -t $(FULL_NAME) .
	touch $@

.PHONY: build-native
build-native: build/.docker-image

.PHONY: run-native
run-native: build-native
	docker run --name http4k-functional -p 8081:8081 $(LOCAL_NAME)

.PHONY: push-native
push-native: build-native
	docker push $(FULL_NAME)


.PHONY: build-jib
build-jib:
	./gradlew check jibDockerBuild
	docker tag $(IMAGE_NAME_JIB):latest $(FULL_NAME_JIB)

push-jib: build-jib
	docker push $(FULL_NAME_JIB)

run-jib: build-jib
	docker run --name http4k-functional -p 8081:8081 $(FULL_NAME_JIB)