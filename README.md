# Python Webapp Performance Test

## Summary

Web applications are evolving away from serving templated HTML and towards being split into an API backend and a static HTML or JavaScript frontend. This repository studies the trade-offs between multiple python webapp architectures for the modern API backend.

## Implementations

Each of the sub-directories implements one combination of layers. Two of these implementations are control cases:

- django - combines djangorestframework, Django, and psycopg-binary.
- golang_bun - this combines golang and bun, a go-native PostgreSQL driver.

The following implementations are test cases:

- fastapi_asyncpg - Combines fastapi, starlette, pydantic, and asyncpg.
- fastapi_orm_asyncpg - Combines fastapi, starlette, pydantic, sqlalchemy, and asyncpg
- fastapi_aiopg - Combines fastapi, starlette, pydantic, aiopg, and psycopg-binary.


## API

- /version - tests basic response time
- /contacts/for - tests async for implementations in aiopg and asyncpg
- /contacts/fetch - tests the `connection.fetch(sql, *params)` API with asyncpg, is identical in to /contacts/for in other implementations.
- /types - returns the contact types.  This is a smaller amount of JSON, and so JSON rendering should be less important


## Database Drivers

Four Python database driver layers are involved:
* aiopg - uses psycopg-binary in asynchrnous mode
* asyncpg - Native Cython driver built for Asynchronous python
* Django - layers on top of psycopg2-binary

Within Golang, we use only the leading driver, bun, which is as a base case.

## Hypothesis

My thinking is based on an analysis of `aiopg` vs `asyncpg`.  With `aiopg`, the API calls for the use of an `async for ` to return rows from a cursor. `asyncpg` has a different architecture where the `connection.fetch` method hides all concurrency for a block of data behind a single asynchronous coroutine:

```python
records = await connection.fetch(sql, *params)
```

`asyncpg` may then gain performance in two ways:
- The coroutine yields only until the rows are ready, and then it can read and return multiple rows.
- There is less marshalling from the `Record` type to native Python types.

This however leads to a productivity issue for the working engineer, in that a list of records returned by asyncpg must be marshalled into the shape needed for the API response.

API calls which serve data from an RDBMS will be nearly as fast in async python as in golang. asyncpg will be faster than aiopg, but not dramatically. API calls which do significant computation will be much faster in golang.

### What about PyPy

PyPy is much, much faster than CPython. asyncpg is a Cython 
module, and may be slower in PyPy. aiopg should be faster,
but relies on psycopg-binary.  Probably a good idea to try
to run the fastapi_aiopg on PyPy as a test. 

Some questions about PyPy:
- How do I convince my DevOps to install PyPy?
- How do I convince DSS to install PyPy?
- How do I manage the availability of packages for 
  a particular developer?
- How do I deploy into AWS Lambda - using Docker?
