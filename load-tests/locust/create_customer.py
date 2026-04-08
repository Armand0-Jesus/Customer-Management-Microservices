from locust import task

from authenticated_user import AuthenticatedUser


class CustomerWriteUser(AuthenticatedUser):
    weight = 1

    @task
    def create_customer(self):
        with self.client.post(
            "/api/customers",
            json=self.new_customer_payload(),
            headers=self.auth_headers(),
            catch_response=True,
            name="POST /api/customers",
        ) as response:
            if response.status_code >= 400:
                response.failure(
                    f"Create customer failed with status {response.status_code}: {response.text}"
                )
