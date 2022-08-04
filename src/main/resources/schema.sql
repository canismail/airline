CREATE TABLE AIRLINE (
    id number,
    name VARCHAR(250),
    created_date TIMESTAMP default CURRENT_TIMESTAMP
);

CREATE TABLE AIRCRAFT (
  id number,
  name VARCHAR(250),
  airline_id number,
  created_date TIMESTAMP default CURRENT_TIMESTAMP
);

CREATE TABLE AIRPORT (
  id number,
  name VARCHAR(250),
  created_date TIMESTAMP default CURRENT_TIMESTAMP
);