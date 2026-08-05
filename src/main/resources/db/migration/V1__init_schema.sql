CREATE TABLE suppliers
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    contact_email VARCHAR(255) NOT NULL
);

CREATE TABLE products
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    category    VARCHAR(255)   NOT NULL,
    unit_price  NUMERIC(12, 2) NOT NULL,
    stock       INTEGER        NOT NULL,
    min_stock   INTEGER        NOT NULL,
    supplier_id BIGINT         NOT NULL REFERENCES suppliers (id)
);

CREATE TABLE purchase_orders
(
    id          BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT      NOT NULL REFERENCES suppliers (id),
    status      VARCHAR(20) NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL
);

CREATE TABLE purchase_order_lines
(
    id                  BIGSERIAL PRIMARY KEY,
    purchase_order_id   BIGINT         NOT NULL REFERENCES purchase_orders (id),
    product_id          BIGINT         NOT NULL REFERENCES products (id),
    quantity            INTEGER        NOT NULL,
    unit_price_at_order NUMERIC(12, 2) NOT NULL
);

CREATE TABLE invoices
(
    id                BIGSERIAL PRIMARY KEY,
    purchase_order_id BIGINT         NOT NULL UNIQUE REFERENCES purchase_orders (id),
    amount            NUMERIC(12, 2) NOT NULL,
    status            VARCHAR(20)    NOT NULL,
    issued_at         TIMESTAMPTZ    NOT NULL
);
