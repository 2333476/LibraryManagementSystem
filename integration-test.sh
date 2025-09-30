#!/bin/bash

API_URL="http://localhost:8080/api/v1"

echo "=== Starting System Integration Tests ==="

# Helper function to pretty-print JSON responses (requires jq)
function pretty_print() {
  if command -v jq &> /dev/null; then
    jq .
  else
    cat
  fi
}

# --------- LOAN SERVICE (Aggregator) ---------
echo -e "\n--- Testing LOAN SERVICE ---"

echo "GET all loans:"
curl -s "$API_URL/loans" | pretty_print

echo "POST new loan:"
curl -s -X POST "$API_URL/loans" \
  -H "Content-Type: application/json" \
  -d '{
        "bookId": "book123",
        "customerId": "cust123",
        "employeeId": "emp123",
        "loanDate": "2025-05-15",
        "returnDate": "2025-06-15"
      }' | pretty_print

# --------- CUSTOMER SERVICE ---------
echo -e "\n--- Testing CUSTOMER SERVICE ---"

echo "GET all customers:"
curl -s "$API_URL/customers" | pretty_print

echo "POST new customer:"
curl -s -X POST "$API_URL/customers" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Test",
        "lastName": "Customer",
        "emailAddress": "test.customer@example.com",
        "streetAddress": "123 Test St",
        "city": "Testville",
        "province": "TS",
        "country": "Testland",
        "postalCode": "T3S T1N",
        "phoneNumbers": [
          { "type": "MOBILE", "number": "555-0000" }
        ]
      }' | pretty_print

# --------- EMPLOYEE SERVICE ---------
echo -e "\n--- Testing EMPLOYEE SERVICE ---"

echo "GET all employees:"
curl -s "$API_URL/employees" | pretty_print

echo "POST new employee:"
curl -s -X POST "$API_URL/employees" \
  -H "Content-Type: application/json" \
  -d '{
        "firstName": "Test",
        "lastName": "Employee",
        "emailAddress": "test.employee@example.com",
        "phoneNumber": "555-1111",
        "salary": 50000,
        "commissionRate": 0.1,
        "department": "Sales",
        "streetAddress": "456 Employee Rd",
        "city": "Employeetown",
        "province": "ET",
        "country": "Employland",
        "postalCode": "E5M P1E"
      }' | pretty_print

# --------- BOOK SERVICE ---------
echo -e "\n--- Testing BOOK SERVICE ---"

echo "GET all books:"
curl -s "$API_URL/books" | pretty_print

echo "POST new book:"
curl -s -X POST "$API_URL/books" \
  -H "Content-Type: application/json" \
  -d '{
        "title": "Test Book",
        "author": "Author McAuthorface",
        "isbn": "123-4567890123",
        "price": 29.99,
        "publishedDate": "2025-01-01"
      }' | pretty_print

echo -e "\n=== System Integration Tests Completed ==="
