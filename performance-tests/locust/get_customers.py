from locust import task

from authenticated_user import AuthenticatedUser


class CustomerReadUser(AuthenticatedUser):
    weight = 3

    @task(2)
    def get_customers(self):
        self.client.get("/api/customers", headers=self.auth_headers(), name="GET /api/customers")

    @task(5)
    def search_customers(self):
        self.client.get(
            "/api/customers/search",
            params={"query": "john"},
            headers=self.auth_headers(),
            name="GET /api/customers/search",
        )
