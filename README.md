# Python Webapp Performance Test

## Summary

Web applications are evolving away from serving templated HTML and towards being split into an API backend and a static HTML or JavaScript frontend. This repository studies the trade-offs between multiple python webapp architectures for the modern API backend. In trying fastapi, I am attempting to address three problems with Django WSGI:

* Because of the GIL, multi-threading is just a way to avoid blocking and achieve concurrency, and so operating systems threads do not lead to true parallelism.  Thus asyncio and coroutines, but using multiple process workers also happens in these architectures.
* Without asyncio, the problems of managing database connections are made worse because we parallelize though multiple processes – the drivers are not built so that multiple processes can share database connections, and so pursuing parallelism through multiple processes introduces issues with connection pooling.  I think AWS RDS Proxy (or pgbouncer) are good solutions here, but would like to improve things on the application side. There is no good solution at work for Oracle, but Oracle DRCP is designed for this.
* The Django ORM is great for developers who don’t want to think about the database and don’t know SQL, but every ORM is a “leaky abstraction”.  The best ORMs are aware of this, and strive to write very much like SQL to enable transfer learning. Django's ORM doesn’t fall into this camp.

Another nice to have which seems less essential to me is to have API schema generation and validation baked into the architecture. This is why fastapi is the async framework I am trying here. I know how to do this as well with Django, and so will not reproduce the steps in this repository, at least not initially.  All of these will seek to following the tenets of the twelve factor app.

## Implementations

Each of the sub-directories implements one combination of layers. Two of these implementations are control cases:

- django - combines djangorestframework, Django, and psycopg-binary.
- golang_bun - this combines golang and bun, a go-native PostgreSQL driver.

The following implementations will be or are test cases:

- fastapi_asyncpg - Combines fastapi, starlette, pydantic, and asyncpg.
- fastapi_orm_asyncpg - Combines fastapi, starlette, pydantic, sqlalchemy ORM, and asyncpg
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
