INSERT INTO clientes (id, cuil, nombre, razon_social, email, telefono, direccion, cliente_id) VALUES (UUID_TO_BIN('11111111-1111-1111-1111-111111111111'), 20301234567, 'Maximiliano Flores', 'Maxi Flores', 'maxi@gmail.com', '3884123456', 'San Salvador de Jujuy', NULL);
INSERT INTO clientes (id, cuil, nombre, razon_social, email, telefono, direccion, cliente_id) VALUES (UUID_TO_BIN('22222222-2222-2222-2222-222222222222'), 20309876543, 'Juan Perez', 'Juan Perez', 'juan@gmail.com', '3884987654', 'Palpala', NULL);

INSERT INTO adherentes (id, nombre, apellido, dni, titular_id) VALUES (UUID_TO_BIN('aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa'), 'Maria', 'Flores', '40123456', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'));
INSERT INTO adherentes (id, nombre, apellido, dni, titular_id) VALUES (UUID_TO_BIN('bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb'), 'Lucas', 'Flores', '42123456', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'));

INSERT INTO cuentas_financieras (id, cbu, alias, saldo, estado_cuenta, cliente_id) VALUES (UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 285059094009041234, 'maxi.ahorro', 150000.00, 'ACTIVA', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'));
INSERT INTO cuentas_financieras (id, cbu, alias, saldo, estado_cuenta, cliente_id) VALUES (UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 285059094009041235, 'juan.ahorro', 200000.00, 'ACTIVA', UUID_TO_BIN('22222222-2222-2222-2222-222222222222'));
INSERT INTO cuentas_financieras (id, cbu, alias, saldo, estado_cuenta, cliente_id) VALUES (UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), 285059094009041236, 'maxi.corriente', 300000.00, 'ACTIVA', UUID_TO_BIN('11111111-1111-1111-1111-111111111111'));
INSERT INTO cuentas_financieras (id, cbu, alias, saldo, estado_cuenta, cliente_id) VALUES (UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), 285059094009041237, 'juan.corriente', 250000.00, 'ACTIVA', UUID_TO_BIN('22222222-2222-2222-2222-222222222222'));

INSERT INTO cajas_ahorros (id, margen_descuento, comision_mantenimiento_mensual) VALUES (UUID_TO_BIN('33333333-3333-3333-3333-333333333333'), 0.10, 1500.00);
INSERT INTO cajas_ahorros (id, margen_descuento, comision_mantenimiento_mensual) VALUES (UUID_TO_BIN('44444444-4444-4444-4444-444444444444'), 0.15, 2000.00);

INSERT INTO cuentas_corrientes (id, tasa_interes_anual, cupo_limite_mensual) VALUES (UUID_TO_BIN('55555555-5555-5555-5555-555555555555'), 12.50, 10);
INSERT INTO cuentas_corrientes (id, tasa_interes_anual, cupo_limite_mensual) VALUES (UUID_TO_BIN('66666666-6666-6666-6666-666666666666'), 15.00, 15);

INSERT INTO transacciones (id, fecha_hora, monto, tipo_transaccion, estado_transaccion, cuenta_financiera_id) VALUES (UUID_TO_BIN('77777777-7777-7777-7777-777777777777'), '2026-09-26 10:30:00', 50000.00, 'DEPOSITO', 'COMPLETADA', UUID_TO_BIN('33333333-3333-3333-3333-333333333333'));
INSERT INTO transacciones (id, fecha_hora, monto, tipo_transaccion, estado_transaccion, cuenta_financiera_id) VALUES (UUID_TO_BIN('88888888-8888-8888-8888-888888888888'), '2026-09-26 15:45:00', 20000.00, 'EXTRACCION', 'COMPLETADA', UUID_TO_BIN('55555555-5555-5555-5555-555555555555'));