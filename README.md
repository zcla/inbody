# inbody
InBody: Lugar para guardar os resultados de bioimpedância.

# Testando localmente

* `mvn clean package`
* `java -jar target/inbody-0.0.1-SNAPSHOT.jar`

# Exportando par ao DockerHub

* Num terminal fora do devcontainer
* `docker build -t zeclaudio/inbody .`
* `docker push zeclaudio/inbody`

# Rodando no Docker Desktop

* Achar a imagem `zeclaudio/inbody` em `Images`
* Apertar o "play"
* Abrir `Optional settings` e preencher:
  * Container name: `inbody`
  * Ports: `6001`
  * Volumes:
    * Host path: `D:\dados\ze\docker\inbody`
    * Container path: `/app/data`
