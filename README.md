# Learning microservices project

To run everything in a cluster
1. Start cluster
```bash
minikube start
```
2. Build docker images using minikube docker:
```bash
./buildimages
```
3. Deploy to minikube k8 cluster
```bash
cd deploy
kubectl apply -f .
```

4. Init-db job might show a crashloop error. This is because
it needs to wait for postgres to be Running. Then it will complete in
the next try

5. Expose Shoppingservice and Zipkin
```bash
minikube service shoppingservice-service
minikube service zipkin-svc
```