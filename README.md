# Tiny URL
# Will use
### Liquibase or flyway
### Auth (eventually use Oauth 2.0)

# Don't know if they are useful 
### Redis 
### Messaging queue


## Development

### Deploy everything (with Docker)

One command to spin up the application

1. make a `./backup` directory where your database will store the backup
2. run `docker compose up`

### Run with hot reload

1. Spin up the database using `docker compose up -d postgres` 
2. Run the project in Intellij - Java 17
3. For Hot Reload, Open this [link](https://stackoverflow.com/a/69449906/15543981)
