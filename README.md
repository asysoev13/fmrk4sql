## Fmrk4sql - Freemarker for sql
**Fmrk4sql** - is true OOP library to easy way of use freemarker templates
that describe sql queries to databases. 

We need write sql queries in native way in cases when we do not use ORM
in our projects or when there is no ORM for database (clickhouse for example) or 
when we have complex queries that forces us to use native sql.

**Fmrk4sql** - developed with OOP principles, without any procedural way of coding.
All we need is to create objects and use objects with only they responsibilities. 
[yegor256](https://www.yegor256.com/2014/11/20/seven-virtues-of-good-object.html) 
can answer the question: "What is true OOP?"  

## Content


## Quick start
Just add dependency:

```xml
<dependency>
    <groupId>org.fmrk4sql</groupId>
    <artifactId>fmrk4sql</artifactId>
    <version>0.1.0</version>
</dependency>
```
[todo]: <> (replace version with placeholder)

## Query parser

There two ways of use query parser.
First is use [StrQuery.java](src%2Fmain%2Fjava%2Forg%2Ffmrk4sql%2FStrQuery.java) 
for parse freemarker template from String view:

```java
final Query query = new StrQuery("<#ftl encoding=\"utf-8\">\nselect count()");
query.parse(FmParams.EMPTY);
```

There we have to use [FmParams.java](src%2Fmain%2Fjava%2Forg%2Ffmrk4sql%2FFmParams.java) is for 
pass params to freemarker template.
For example we can have single query where in some case we can switch off pagination.

So template will be look like this:
```xml
<#ftl encoding="utf-8">
<#-- @vtlvariable name="union" type="java.lang.Boolean" -->
select foo, bar from foo_table
<#if union==true>
union all
select foo, bar from bar_table
</#if>
```
```java
final Params params = new FmParams(List.of(new FmParam("union", true)));
```
The result of parsing will be
```sql
select foo, bar from foo_table
union all
select foo, bar from bar_table
```

```java
final Query query = new StrQuery("<#ftl encoding=\"utf-8\">\nselect count()");
query.parse(FmParams.EMPTY);
```

## Pageable & order

The most frequently cases of use params in queries is pagination. So **fmrk4sql** has special class
for include pagination and order in queries. For Spring platform pagination functions uses 
**org.springframework.data.domain.Pageable** interface there implemented special class also.

Simple pagination and order when Spring controller (GET: api/get?page=0&size=10&sort=col1,asc) 
gets pagination (Pageable springPagination) and we can use pagination for query:

```xml
select col1, col2 from ${table_name}
<#if orders?has_content>
order by
<#list orders as ord>
${ord.col()} ${ord.direction()}
</#list>
</#if>
limit ${size} offset ${page}
```

```java
Params params = new PageParams(
    new FmParams(List.of(new FmParam("table_name", "orderable_table"))), 
    new SpringPage(springPagination)
);
Query query = new StrQuery(template);
query.parse(params);
```

The result of parsing will be:
```sql
select col1, col2 from orderable_table order by col1 ASC limit 10 offset 0
```
