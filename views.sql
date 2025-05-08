
create materialized view books_per_author as
select b.author_id as author_id, count(b.id) as num_books
from book b
group by b.author_id;

create materialized view authors_per_country as
select a.country_id as country_id, count(a.id) as num_authors
from author a
group by a.country_id;