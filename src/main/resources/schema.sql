create table if not exists Ingredient(
    id varchar(4) primary key not null,
    name varchar(25) not null,
    type varchar(10) not null
);

create table if not exists Taco(
    id serial primary key not null,
    name varchar(100) not null,
    createdAt timestamp without time zone
);

create table if not exists Taco_Ingredient(
    tacoId bigint not null references Taco,
    ingredientId varchar(4) not null references Ingredient
);

create table if not exists TacoOrder(
    id serial primary key not null,
    placedAt timestamp without time zone not null,
    deliveryName varchar(50) not null,
    deliveryStreet varchar(100) not null,
    deliveryCity varchar(100) not null,
    deliveryState varchar(100) not null,
    deliveryZip varchar(10) not null,
    ccNumber varchar(16) not null,
    ccExpiration varchar(5) not null,
    ccCVV varchar(3) not null
);

create table if not exists TacoOrder_Taco(
    orderId bigint not null references TacoOrder,
    tacoId bigint not null references Taco
);