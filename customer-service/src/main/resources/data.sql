-- Ensure the 'customer' table exists
CREATE TABLE IF NOT EXISTS customer
(
    id              UUID PRIMARY KEY,
    full_name            VARCHAR(255)        NOT NULL,
    email                VARCHAR(255) UNIQUE NOT NULL,
    shipping_address     VARCHAR(255)        NOT NULL,
    preferred_drop_date  DATE                NOT NULL,
    member_since         DATE                NOT NULL
);

-- Insert well-known UUIDs for specific customers
INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '123e4567-e89b-12d3-a456-426614174000',
       'John Doe',
       'john.doe@example.com',
       '123 Calle Fortaleza, San Juan, Puerto Rico 00901',
       '2026-04-03',
       '2024-01-10'
WHERE NOT EXISTS (SELECT 1
                  FROM customer
                  WHERE id = '123e4567-e89b-12d3-a456-426614174000');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '123e4567-e89b-12d3-a456-426614174001',
       'Jane Smith',
       'jane.smith@example.com',
       '456 Ave Ponce de Leon, Santurce, Puerto Rico 00907',
       '2026-04-06',
       '2023-12-01'
WHERE NOT EXISTS (SELECT 1
                  FROM customer
                  WHERE id = '123e4567-e89b-12d3-a456-426614174001');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '123e4567-e89b-12d3-a456-426614174002',
       'Alice Johnson',
       'alice.johnson@example.com',
       '789 Calle Marina, Ponce, Puerto Rico 00716',
       '2026-04-09',
       '2022-06-20'
WHERE NOT EXISTS (SELECT 1
                  FROM customer
                  WHERE id = '123e4567-e89b-12d3-a456-426614174002');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '123e4567-e89b-12d3-a456-426614174003',
       'Bob Brown',
       'bob.brown@example.com',
       '321 Carr 167, Bayamon, Puerto Rico 00959',
       '2026-04-12',
       '2023-05-14'
WHERE NOT EXISTS (SELECT 1
                  FROM customer
                  WHERE id = '123e4567-e89b-12d3-a456-426614174003');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '123e4567-e89b-12d3-a456-426614174004',
       'Emily Davis',
       'emily.davis@example.com',
       '654 Calle Fernandez Juncos, Carolina, Puerto Rico 00979',
       '2026-04-15',
       '2024-03-01'
WHERE NOT EXISTS (SELECT 1
                  FROM customer
                  WHERE id = '123e4567-e89b-12d3-a456-426614174004');

-- Insert well-known UUIDs for specific customers
INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174005',
       'Michael Green',
       'michael.green@example.com',
       '987 Ave Rafael Cordero, Caguas, Puerto Rico 00725',
       '2026-04-18',
       '2024-02-15'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174005');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174006',
       'Sarah Taylor',
       'sarah.taylor@example.com',
       '123 Calle Mendez Vigo, Mayaguez, Puerto Rico 00680',
       '2026-04-21',
       '2023-08-25'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174006');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174007',
       'David Wilson',
       'david.wilson@example.com',
       '456 Ave San Luis, Arecibo, Puerto Rico 00612',
       '2026-04-24',
       '2022-10-10'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174007');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174008',
       'Laura White',
       'laura.white@example.com',
       '789 Calle Esmeralda, Guaynabo, Puerto Rico 00969',
       '2026-04-27',
       '2024-04-20'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174008');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174009',
       'James Harris',
       'james.harris@example.com',
       '321 Calle Noya, Humacao, Puerto Rico 00791',
       '2026-04-30',
       '2023-06-30'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174009');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174010',
       'Emma Moore',
       'emma.moore@example.com',
       '654 Carr 107, Aguadilla, Puerto Rico 00603',
       '2026-05-03',
       '2023-01-22'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174010');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174011',
       'Ethan Martinez',
       'ethan.martinez@example.com',
       '987 Calle Betances, Cabo Rojo, Puerto Rico 00623',
       '2026-05-06',
       '2024-05-12'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174011');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174012',
       'Sophia Clark',
       'sophia.clark@example.com',
       '123 Calle Barbosa, Fajardo, Puerto Rico 00738',
       '2026-05-09',
       '2022-11-11'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174012');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174013',
       'Daniel Lewis',
       'daniel.lewis@example.com',
       '456 Ave Hostos, Yauco, Puerto Rico 00698',
       '2026-05-12',
       '2023-09-19'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174013');

INSERT INTO customer (id, full_name, email, shipping_address, preferred_drop_date, member_since)
SELECT '223e4567-e89b-12d3-a456-426614174014',
       'Isabella Walker',
       'isabella.walker@example.com',
       '789 Calle McKinley, Rio Piedras, Puerto Rico 00925',
       '2026-05-15',
       '2024-03-29'
WHERE NOT EXISTS (SELECT 1 FROM customer WHERE id = '223e4567-e89b-12d3-a456-426614174014');
