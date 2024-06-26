DROP TABLE IF EXISTS rental;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS author;

CREATE TABLE rental (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  rental_date TIMESTAMP,
  return_date TIMESTAMP,
  book_id BIGINT REFERENCES book(id) ON DELETE CASCADE,
  renter_name VARCHAR(300)
);

CREATE TABLE author (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(300) NOT NULL,
    biography VARCHAR(200) NOT NULL
);

CREATE TABLE book (
     id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
     title VARCHAR(300) NOT NULL,
     author_id BIGINT REFERENCES author(id) ON DELETE CASCADE,
     available BOOLEAN NOT NULL,
     publication_year DATE
);
