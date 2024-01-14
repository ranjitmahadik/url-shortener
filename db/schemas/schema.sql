CREATE TABLE url_shortener(
    short_url varchar(10) primary key,
    full_url varchar(265),
    created_at bigint default extract(epoch from now())
);