-- Insertion d'un client
INSERT INTO customers (customer_id, last_name, first_name, email_address, street_address, postal_code, city, province, username, password)
VALUES ('cust-001', 'Lefebvre', 'Marie', 'marie.lefebvre@email.com', '123 Rue Sainte-Catherine', 'H2X3L9', 'Montréal', 'QC', 'marielef', 'pwd123');

-- Insertion d'un autre client
INSERT INTO customers (customer_id, last_name, first_name, email_address, street_address, postal_code, city, province, username, password)
VALUES ('cust-002', 'Nguyen', 'Thanh', 'thanh.nguyen@email.com', '456 Avenue Laurier', 'H2Y4R5', 'Québec', 'QC', 'thanhn', 'pwd456');

-- Insertion de numéros de téléphone
INSERT INTO customer_phonenumbers (customer_id, type, number)
VALUES (1, 'WORK', '514-111-2222'),
       (1, 'MOBILE', '514-333-4444'),
       (2, 'MOBILE', '418-555-6666');
