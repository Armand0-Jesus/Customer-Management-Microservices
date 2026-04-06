import os
from datetime import date, timedelta
from uuid import uuid4

from locust import HttpUser, between


class AuthenticatedUser(HttpUser):
    wait_time = between(1, 3)
    host = os.getenv("LOCUST_HOST", "http://localhost:4004")

    def on_start(self):
        self.token = None
        self.login()

    def login(self):
        payload = {
            "email": os.getenv("LOCUST_LOGIN_EMAIL", "testuser@test.com"),
            "password": os.getenv("LOCUST_LOGIN_PASSWORD", "password123"),
        }

        with self.client.post("/auth/login", json=payload, catch_response=True, name="POST /auth/login") as response:
            if response.status_code != 200:
                response.failure(f"Login failed with status {response.status_code}: {response.text}")
                return

            token = response.json().get("token")
            if not token:
                response.failure("Login response did not contain a token")
                return

            self.token = token
            response.success()

    def auth_headers(self):
        if not self.token:
            self.login()
        return {"Authorization": f"Bearer {self.token}"}

    def new_customer_payload(self):
        suffix = uuid4().hex[:8]
        return {
            "fullName": f"Locust User {suffix}",
            "email": f"locust.{suffix}@example.com",
            "shippingAddress": "100 Performance Test Ave, San Juan, Puerto Rico 00901",
            "memberSince": date.today().isoformat(),
            "preferredDropDate": (date.today() + timedelta(days=7)).isoformat(),
        }
