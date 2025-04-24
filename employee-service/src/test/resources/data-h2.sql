-- Insertion des départements
INSERT INTO departments(department_id, name, head_count) VALUES
                                                             ('d1a1b1c1-dp01-4e00-9999-dept0001', 'Sales', 10),
                                                             ('d2b2c2d2-dp02-4e00-9999-dept0002', 'HumanResources', 4);

-- Insertion des employés
INSERT INTO employees (
    employee_id, first_name, last_name, email_address, phone_number,
    salary, commission_rate, department, department_id,
    street_address, city, province, country, postal_code
) VALUES
      ('e1111111-aaaa-bbbb-cccc-000000000001', 'Alice', 'Martin', 'alice.martin@example.com', '514-123-4567',
       60000, 0.0, 'Sales', 1, '123 Rue Principale', 'Montréal', 'Québec', 'Canada', 'H2X 1Y4'),

      ('e2222222-bbbb-cccc-dddd-000000000002', 'Bob', 'Lefebvre', 'bob.lefebvre@example.com', '418-987-6543',
       65000, 1.5, 'Sales', 1, '456 Rue des Lilas', 'Québec', 'Québec', 'Canada', 'G1K 7P4');

-- Insertion des numéros de téléphone
INSERT INTO employee_phonenumbers(employee_id, type, number) VALUES
                                                                 (1, 'WORK', '514-555-1212'),
                                                                 (1, 'MOBILE', '514-555-1313'),
                                                                 (2, 'WORK', '418-555-1414');

-- Insertion des postes dans les départements
INSERT INTO department_positions(department_id, title, code) VALUES
                                                                 (1, 'developer', 'eng-dev-1'),
                                                                 (1, 'lead', 'eng-lead-1'),
                                                                 (2, 'recruiter', 'hr-rc-1');
