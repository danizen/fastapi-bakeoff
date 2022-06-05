Now that I am working with SQLAlchemy and see that the declarative
model created is so much more capable than Django's, I see the benefits.

I had thought that it would be "just another ORM", but it is able
to build queries much more capably than Django, and the statements
are easy to print.

I had also thought that like with Django, once you start using the ORM
you have to keep going - this is not the case at all.  You can
easily use the declarative ORM to define a schema and then query it
using the Core API. 
