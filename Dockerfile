FROM gcr.io/distroless/static-debian11
EXPOSE 8080
COPY build/native/nativeCompile/http4k-functional /app
ENTRYPOINT ["/app"]
