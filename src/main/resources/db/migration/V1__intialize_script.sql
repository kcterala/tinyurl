CREATE TABLE users (
    id serial NOT NULL,
    created_at timestamp(6) without time zone NULL,
    email character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    sessions jsonb NULL,
    updated_at timestamp(6) without time zone NULL
  );

ALTER TABLE
  users
ADD
  CONSTRAINT users_pkey PRIMARY KEY (id)