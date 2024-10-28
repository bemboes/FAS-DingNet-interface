# DingNet - Fundamentals of Adaptive Software

DingNet exemplar for the 2024 Fundamentals of Adaptive Software course at VU Amsterdam.

### Instructions

To build the docker image:

```shell
docker build -t dingnet .
```

To run the image:

```shell
docker run -e PORT=<port> --publish <port>:<port> dingnet
```

If no port is specified in the environment variables, port `8080` will be used.
The HTTP server is now listening on port `<port>`.
