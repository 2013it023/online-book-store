--Create Keyspace for getir
CREATE KEYSPACE IF NOT EXISTS getir
WITH replication = {
	'class' : 'SimpleStrategy',
	'replication_factor' : 1
};

--Create Book table to store Book related information

CREATE TABLE IF NOT EXISTS getir.book_dto (
	userid bigint,
	id text,
	author_name text,
	description text,
	name text,
	price bigint,
	PRIMARY KEY (userid, id)
);

--Create Profile table to store Profile related information.

CREATE TABLE IF NOT EXISTS getir.profile_dto (
	mobilenumber bigint,
	created_at text,
	email_id text,
	first_name text,
	last_name text,
	PRIMARY KEY (mobilenumber)
);

--Create Order table to store Order related information.

CREATE TABLE IF NOT EXISTS getir.order_dto(
	userid bigint,
	orderid text,
	book_details LIST<FROZEN<book_details>>,
	month text,
	ordered_date timestamp,
	payment_information FROZEN<payment_information>,
	payment_type int,
	total_amount float,
	year int,
	PRIMARY KEY (userid, orderid)
);

--Create User Defined Types.

CREATE TYPE IF NOT EXISTS getir.book_details (
	book_id text,
	name text,
	quantity bigint
);

CREATE TYPE IF NOT EXISTS getir.payment_information (
	card_number text,
	nick_name text
);

--Create Secondary Indexes to support search operations.
CREATE INDEX IF NOT EXISTS order_details_by_ordered_date_idx ON getir.order_dto (ordered_date);

CREATE INDEX IF NOT EXISTS order_details_by_year_idx ON getir.order_dto (year);

--Create table to maintain the stock quantity

CREATE TABLE IF NOT EXISTS getir.book_stock_information_dto (
	userid bigint,
	id text,
	quantity bigint,
	PRIMARY KEY (userid, id)
);

-- Create Index tables

CREATE INDEX IF NOT EXISTS book_stock_details_by_id_idx ON getir.book_stock_information_dto (id);



