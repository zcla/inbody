# inbody
InBody: Lugar para guardar os resultados de bioimpedância.

# Testando localmente

* `mvn clean package`
* `java -jar target/inbody-0.0.1-SNAPSHOT.jar`

# Exportando par ao DockerHub

* Reopen folder locally
* `docker build -t zeclaudio/inbody .`
* `docker push zeclaudio/inbody`
