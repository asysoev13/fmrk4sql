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
final Params params = new FmParams(Collections.EMPTY_LIST);
final Query query = new StrQuery("<#ftl encoding=\"utf-8\">\nselect count()");
query.parse(params);
```

There we have to use [FmParams.java](src%2Fmain%2Fjava%2Forg%2Ffmrk4sql%2FFmParams.java) is for 
pass params to freemarker template.
For example we can have single query where in some case we can switch off pagination.

So template will be look like this:
```xml
<#ftl encoding="utf-8">
<#-- @vtlvariable name="paginate" type="java.lang.Boolean" -->
select foo, bar from foobar_table
<#if paginate==true>
offset 1 limit 10
</#if>
```
```java
final Params params = new FmParams(List.of(new FmParam("paginate", true)));
```
The result of parsing will be
```sql
select foo, bar from foobar_table
offset 1 limit 10
```

```java
final Params params = new FmParams(Collections.EMPTY_LIST);
final Query query = new StrQuery("<#ftl encoding=\"utf-8\">\nselect count()");
query.parse(params);
```
