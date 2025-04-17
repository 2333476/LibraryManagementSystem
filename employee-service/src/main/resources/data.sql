-- Insertion des départements
INSERT INTO departments(department_id, name, head_count) VALUES
                                                             ('1048b354-c18f-4109-8282-2a85485bfa5a', 'sales and marketing', 2),
                                                             ('cb346554-8526-4569-849d-6abf41bb7f76', 'customer relations', 3);

-- Insertion des employés
INSERT INTO employees (employee_id, first_name, last_name, email_address, phone_number, salary, commission_rate, department_id, street_address, city, province, country, postal_code) VALUES
                                                                                                                                                                                          ('e5913a79-9b1e-4516-9ffd-06578e7af261', 'Vilma', 'Chawner', 'vchawner0@phoca.cz', '185-415-8773', 34000, 0.0, 1, '8452 Anhalt Park', 'Chambly', 'Québec', 'Canada', 'J3L 5Y6'),
                                                                                                                                                                                          ('6d8e5f5b-8350-40ed-ac06-e484498f4f41', 'Dorry', 'Gepp', 'dgepp1@stanford.edu', '964-472-9806', 75000, 3.5, 1, '23320 Pankratz Park', 'Barrhead', 'Alberta', 'Canada', 'T7N 1S3'),
                                                                                                                                                                                          ('bfe6b6f3-9316-4886-b150-f42eb8d91ef0', 'Hunfredo', 'Fellgett', 'hfellgett2@biblegateway.com', '190-551-7108', 60000, 0.0, 1, '275 Bellgrove Circle', 'Roberval', 'Québec', 'Canada', 'G8H 0H0'),
                                                                                                                                                                                          ('f8af8ab3-0b63-4e11-a470-52ae284e451b', 'Yelena', 'Tomasicchio', 'ytomasicchio3@theguardian.com', '357-608-7142', 90000, 7.4, 1, '73737 Brentwood Point', 'Mont-Laurier', 'Québec', 'Canada', 'J9L 4T6'),
                                                                                                                                                                                          ('d0fa10e3-9cd3-446c-b152-2da729a9cead', 'Frank', 'Egginton', 'fegginton4@mit.edu', '507-646-2047', 55000, 0.0, 2, '3 Victoria Terrace', 'Merritt', 'British Columbia', 'Canada', 'V1K 8L5'),
                                                                                                                                                                                          ('3b8afd72-75eb-4feb-bdb6-2471550cdf47', 'Gayelord', 'Wrenn', 'gwrenn5@tripod.com', '750-752-4453', 85000, 0.0, 2, '191 Mendota Alley', 'Kingsey Falls', 'Québec', 'Canada', 'T8H 7N4'),
                                                                                                                                                                                          ('efaa2e89-9e09-4183-a509-a8a65eb55495', 'Vikki', 'Wymer', 'vwymer6@webnode.com', '821-812-7847', 86000, 8.4, 1, '0 Farwell Circle', 'Richmond', 'British Columbia', 'Canada', 'V6Y 4D6'),
                                                                                                                                                                                          ('5bea2f5a-8a87-4eb6-bf79-671ad838044f', 'Craig', 'Winslade', 'cwinslade7@desdev.cn', '406-751-0038', 45000, 0.0, 1, '6 Nova Way', 'Lac La Biche', 'Alberta', 'Canada', 'E1W 3C5'),
                                                                                                                                                                                          ('890fd34c-598d-470a-b4f1-f1906e1fc5a5', 'Lonna', 'Jannaway', 'ljannaway8@china.com.cn', '671-843-8353', 59000, 4.8, 1, '474 Brown Point', 'Edson', 'Alberta', 'Canada', 'T7E 2H7');

-- Insertion des numéros de téléphone
INSERT INTO employee_phonenumbers(employee_id, type, number) VALUES
                                                                 (1, 'WORK', '515-555-5555'),
                                                                 (1, 'MOBILE', '514-555-4444'),
                                                                 (2, 'WORK', '515-555-5555'),
                                                                 (2, 'MOBILE', '514-555-1234');

-- Insertion des postes dans les départements
INSERT INTO department_positions(department_id, title, code) VALUES
                                                                 (1, 'manager', 'samm1'),
                                                                 (1, 'associate', 'sama1');
