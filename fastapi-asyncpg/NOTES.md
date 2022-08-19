I don't mind writing SQL by hand, but working with the asyncpg.Record objects is not ideal.  I wonder whether the performance benefits hold-up when we start using SQLAlchemy.

Now I have started using SQL Alchemy in another directory.
I have modified the schema to model multiple emails/phones,
and the code in this directory needs to change.
