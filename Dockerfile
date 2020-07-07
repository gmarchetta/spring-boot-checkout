FROM openjdk:13-jdk-alpine
RUN addgroup -S whee && adduser -S gmarchetta -G whee
USER gmarchetta:whee
ARG DEPENDENCY=build/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
ENTRYPOINT ["java","-cp","app:app/lib/*","com.ecomm.checkout.CheckoutApplication"]
