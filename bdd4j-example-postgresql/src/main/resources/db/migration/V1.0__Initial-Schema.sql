create table "authors"
(
    "id"   serial,
    "name" varchar(128) not null,
    PRIMARY KEY ("id")
);

create table "books"
(
    "id"     serial,
    "name"   varchar(128) not null,
    "author" int          not null references "authors" ("id"),
    PRIMARY KEY ("id")
);
