
drop table saleitem;
drop table product;
drop table sale;
drop table customer;

create table Product (
  productId number primary key,
  productName varchar not null,
  description varchar,
  category varchar not null,
  priceList number not null constraint price check (priceList >=0),
 quantityInStock number not null constraint quanity check (quantityInStock >=0),
);
insert into product(productid, productname, description, category, pricelist, quantityinstock)
values(1, 'hi', 'hello', 'buy', 23, 43);
insert into product(productid, productname, description, category, pricelist, quantityinstock)
values(12, 'bye', 'good bye', 'sell', 243, 3);
insert into product(productid, productname, description, category, pricelist, quantityinstock)
values(13, 'hello', 'hello', 'buy', 2, 433);
create table customer (
  customerid number primary key auto_increment(0),
  username varchar not null unique,
  password varchar not null,
  firstname varchar not null,
  lastname varchar not null,
  email varchar not null,
  address varchar not null,
  creditcard varchar not null,
);
insert into customer (username, password, firstname, lastname, email, address, creditcard) 
values('boris','a', 'boris', 'l', 'e', 'a', 'c');
insert into customer (username, password, firstname, lastname, email, address, creditcard) 
values('hi','a', 'boris', 'l', 'e', 'a', 'c');

create table sale(
saleid number primary key auto_increment(0),
saleDate timestamp not null,
status varchar,
customerid number,
CONSTRAINT FK_PersonOrder foreign key (customerid) references customer(customerid),
);

create table saleItem (
quantity number not null,
price number not null,
productid number,
saleid number,
constraint fkp foreign key (productid) references product(productid),
constraint fks foreign key (saleid) REFERENCES sale(saleid),
constraint pk primary key (productid, saleid),
);
