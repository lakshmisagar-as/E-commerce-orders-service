CREATE TABLE address
(
    id            varchar(255) NOT NULL,
    created_date  timestamp NULL,
    modified_date timestamp NULL,
    address_line1 varchar(255) NULL,
    address_line2 varchar(255) NULL,
    city          varchar(255) NULL,
    customer_id   varchar(255) NULL,
    state         varchar(255) NULL,
    zip           varchar(255) NULL,
    CONSTRAINT address_pkey PRIMARY KEY (id)
);

CREATE TABLE order_details
(
    id                      varchar(255) NOT NULL,
    created_date            timestamp NULL,
    modified_date           timestamp NULL,
    customer_id             varchar(255) NULL,
    is_billing_address_same bool         NOT NULL,
    order_status            int4 NULL,
    sub_total               numeric(19, 2) NULL,
    tax                     numeric(19, 2) NULL,
    total_amount            numeric(19, 2) NULL,
    shipping_address_id     varchar(255) NULL,
    delivery_type           int4 NULL,
    CONSTRAINT order_details_pkey PRIMARY KEY (id),
    CONSTRAINT fklrxynuepkh3js64d8kthv641m FOREIGN KEY (shipping_address_id) REFERENCES address (id)
);

CREATE TABLE order_items
(
    id            varchar(255) NOT NULL,
    created_date  timestamp NULL,
    modified_date timestamp NULL,
    amount        numeric(19, 2) NULL,
    product_id    varchar(255) NULL,
    quantity      numeric(19, 2) NULL,
    order_id      varchar(255) NULL,
    CONSTRAINT order_items_pkey PRIMARY KEY (id),
    CONSTRAINT fkfaco7kgw6uoepp39m74cy7j6o FOREIGN KEY (order_id) REFERENCES order_details (id)
);

CREATE TABLE order_payment_details
(
    id                 varchar(255) NOT NULL,
    created_date       timestamp NULL,
    modified_date      timestamp NULL,
    paid_amount        numeric(19, 2) NULL,
    payment_mode       int4 NULL,
    billing_address_id varchar(255) NULL,
    order_id           varchar(255) NULL,
    CONSTRAINT order_payment_details_pkey PRIMARY KEY (id),
    CONSTRAINT fk69lm6fspkjjg08rbdjaw82kku FOREIGN KEY (billing_address_id) REFERENCES address (id),
    CONSTRAINT fklcijbt6gmaw43tynk4hlyruk7 FOREIGN KEY (order_id) REFERENCES order_details (id)
);