# myRatingApplication

## Building for production

### Packaging as jar

To build the final jar and optimize the myRatingApplication application for production, run:

```
./gradlew -Pprod clean bootJar
```

### Packaging as war

To package your application as a war in orderRequest to deploy it to an application server, run:

```
./gradlew -Pprod -Pwar clean bootWar
```