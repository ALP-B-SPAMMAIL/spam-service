az acr login --name team04registry

docker build -t team04registry.azurecr.io/spam-service:latest .
docker push team04registry.azurecr.io/spam-service:latest
kubectl rollout restart deployment spam-service