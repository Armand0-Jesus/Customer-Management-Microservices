from locust import HttpUser, between, task


class AuthOnlyUser(HttpUser):
    wait_time = between(1, 3)

    @task
    def login(self):
        payload = {
            "email": "testuser@test.com",
            "password": "password123",
        }
        self.client.post("/auth/login", json=payload, name="POST /auth/login")
