FROM gradle:8-jdk21 AS builder

WORKDIR /app

COPY . .

RUN gradle build --no-daemon -x test

FROM eclipse-temurin:21-jre

RUN apt-get update && \
    apt-get install -y python3 python3-pip && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

COPY scripts /app/scripts

RUN pip3 install --break-system-packages -r /app/scripts/requirements.txt

EXPOSE 8001
EXPOSE 8002

CMD sh -c "cd /app/scripts/groqs && uvicorn app:app --host 0.0.0.0 --port 8002 & cd /app && java -jar app.jar"
