
# WebSocket + Redis Pub/Sub

This project demonstrates how to use **WebSocket** with **Redis Pub/Sub** to broadcast messages between multiple application instances.

---

## 🔧 Verifying Redis Pub/Sub

To check whether Redis is correctly receiving and broadcasting WebSocket messages:

```bash
docker exec -it redis_server sh
redis-cli
AUTH <password>
SUBSCRIBE websocket-channel
```

You should see messages appear here whenever clients send WebSocket messages through the application.

---

## 🚀 Running the Application Locally

### 1. Start Redis

Ensure Redis is running locally or via Docker.
If your Redis instance requires authentication, set the password in `application.yml`:

```yaml
spring:
  redis:
    password: <your_password>
```

---

### 2. Build the Project

```bash
mvn clean package -DskipTests
```

---

### 3. Run Multiple Instances

Start at least two application instances to test cross-instance WebSocket messaging:

**Instance 1**

```bash
SERVER_PORT=8080 java -jar target/websocket-redis-0.0.1-SNAPSHOT.jar
```

**Instance 2**

```bash
SERVER_PORT=8081 java -jar target/websocket-redis-0.0.1-SNAPSHOT.jar
```

> ⚠️ Note: The original snippet contained a typo (`0.0.0-SNAPSHOT`). Both instances should run the same jar version.

---

### 4. Test WebSocket Messaging

Open `index.html` from each instance in two different browser tabs:

* [http://localhost:8080/index.html](http://localhost:8080/index.html)
* [http://localhost:8081/index.html](http://localhost:8081/index.html)

Send messages from either tab—messages should appear in all connected tabs regardless of which instance they’re connected to.