.PHONY: build clean lint

build: lint
	go build -o app cmd/app.go

lint:
	go vet ./...
	staticcheck ./...

clean:
	-rm -f app

