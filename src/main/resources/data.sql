------------------------------------------------------------
-- Bloque 1: suppliers
------------------------------------------------------------
INSERT INTO suppliers (id, name, contact_email)
VALUES (1, 'Andes Green Coffee Import', 'origen@andesgreen.com'),
       (2, 'Tostadores del Norte', 'ventas@tostadoresnorte.es'),
       (3, 'Lácteos Cantabria Fresca', 'pedidos@cantabriafresca.es'),
       (4, 'Pack&Serve Solutions', 'contacto@packserve.eu'),
       (5, 'BaristaTools Pro', 'info@baristatoolspro.com'),
       (6, 'Dulcinea Azúcares y Siropes', 'comercial@dulcineafoods.es'),
       (7, 'Tea Origins Europa', 'sales@teaorigins.eu');

ALTER TABLE suppliers
    ALTER COLUMN id RESTART WITH 100;

------------------------------------------------------------
-- Bloque 2: products
------------------------------------------------------------
INSERT INTO products (id, name, category, unit_price, stock, min_stock, supplier_id)
VALUES (1, 'Café verde Colombia Supremo', 'COFFEE', 12.50, 40, 20, 1),
       (2, 'Café verde Etiopía Yirgacheffe', 'COFFEE', 14.20, 0, 20, 1),
       (3, 'Café verde Brasil Santos', 'COFFEE', 10.80, 15, 20, 1),

       (4, 'Café tostado mezcla 80/20', 'COFFEE', 16.90, 50, 25, 2),
       (5, 'Café tostado natural 100% arábica', 'COFFEE', 22.00, 10, 20, 2),
       (6, 'Café tostado descafeinado', 'COFFEE', 18.50, 30, 15, 2),

       (7, 'Leche entera 1L', 'DAIRY', 0.95, 120, 60, 3),
       (8, 'Leche semidesnatada 1L', 'DAIRY', 1.05, 0, 50, 3),
       (9, 'Nata líquida 500ml', 'DAIRY', 1.80, 25, 20, 3),

       (10, 'Vasos de cartón 12oz (pack 50)', 'PACKAGING', 6.50, 80, 40, 4),
       (11, 'Tapas para vasos 12oz (pack 50)', 'PACKAGING', 4.20, 35, 30, 4),
       (12, 'Filtros de café tamaño 2 (pack 100)', 'PACKAGING', 3.90, 12, 20, 4),

       (13, 'Taza cerámica barista 250ml', 'EQUIPMENT', 4.50, 60, 20, 5),
       (14, 'Cucharilla barista acero', 'EQUIPMENT', 2.20, 100, 30, 5),
       (15, 'Molinillo profesional Titan X', 'EQUIPMENT', 450.00, 5, 2, 5),

       (16, 'Sirope de vainilla premium', 'SWEETENERS', 7.50, 18, 10, 6),
       (17, 'Sirope de caramelo clásico', 'SWEETENERS', 6.90, 8, 10, 6),

       (18, 'Té verde Sencha', 'TEA', 28.00, 25, 30, 7),
       (19, 'Té negro Assam', 'TEA', 24.00, 30, 15, 7);

ALTER TABLE products
    ALTER COLUMN id RESTART WITH 100;

------------------------------------------------------------
-- Bloque 3: purchase_orders
------------------------------------------------------------
INSERT INTO purchase_orders (id, supplier_id, status, created_at)
VALUES (1, 1, 'RECEIVED', TIMESTAMP WITH TIME ZONE '2026-07-10 09:15:00+00'),
       (2, 3, 'RECEIVED', TIMESTAMP WITH TIME ZONE '2026-07-20 11:45:00+00'),
       (3, 4, 'SENT', TIMESTAMP WITH TIME ZONE '2026-07-24 14:20:00+00'),
       (4, 7, 'DRAFT', TIMESTAMP WITH TIME ZONE '2026-07-28 08:00:00+00'),
       (5, 2, 'CANCELLED', TIMESTAMP WITH TIME ZONE '2026-07-15 16:10:00+00');

ALTER TABLE purchase_orders
    ALTER COLUMN id RESTART WITH 100;

------------------------------------------------------------
-- Bloque 4: purchase_order_lines
------------------------------------------------------------
INSERT INTO purchase_order_lines (id, purchase_order_id, product_id, quantity, unit_price_at_order)
VALUES (1, 1, 1, 50, 12.00),
       (2, 1, 2, 40, 13.80),
       (3, 1, 3, 60, 10.80),

       (4, 2, 7, 150, 0.95),
       (5, 2, 8, 120, 1.05),
       (6, 2, 9, 60, 1.80),

       (7, 3, 10, 40, 6.50),
       (8, 3, 11, 35, 4.20),
       (9, 3, 12, 50, 3.90),

       (10, 4, 18, 20, 28.00),
       (11, 4, 19, 15, 24.00),

       (12, 5, 4, 30, 16.90),
       (13, 5, 6, 20, 18.50);

ALTER TABLE purchase_order_lines
    ALTER COLUMN id RESTART WITH 100;

------------------------------------------------------------
-- Bloque 5: invoices
------------------------------------------------------------
INSERT INTO invoices (id, purchase_order_id, amount, status, issued_at)
VALUES (1, 1, 1800.00, 'PAID', TIMESTAMP WITH TIME ZONE '2026-07-12 10:00:00+00'),
       (2, 2, 376.50, 'PENDING', TIMESTAMP WITH TIME ZONE '2026-07-22 09:30:00+00');

ALTER TABLE invoices
    ALTER COLUMN id RESTART WITH 100;
