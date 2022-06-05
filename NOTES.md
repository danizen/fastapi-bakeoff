### fastapi asyncpg

It used to work, but now that phone and email are separate, probably doesn't.

### fastapi_orm_asynpg

Works, but doesn't do the "selectinload" thing for phones and emails

### django

Basically works with both WSGI and ASGI (NOTE that flexibility).
The basics wrote fast, but now would have to deal with
whitenoise, drf_yasg, etc. Very declarative and django-silk
could be integrated for easy debugging - debugging queries and
stuff is something I'll need to deal with for whichever architecture is fastest.

So fastapi will be much, much faster to an OpenAPI API browser, and an async deployment.  With asyncpg, the startup was pretty
fast to me, but I *already* know SQL.  The layer in the middle with SQL Alchemy seems pretty good, once you *know* it, and the SQL is closer, but there will still be a mental step translating
the SQL expression language.

### fastapi aiopg

Not started - should test with pypy3 on localhost

### golang_bun

Not started - what microframework to use for Go?

