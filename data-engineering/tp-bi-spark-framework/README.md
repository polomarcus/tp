# Practices - Data engineering

## Data processing with Spark with BI
Need help on Apache Spark ? 
* Read : https://spark.apache.org/docs/latest/sql-programming-guide.html

Need help for the Business Intelligence tool Metabase ? 
* Read https://www.metabase.com/learn/getting-started/getting-started.html

Need help for Postgres or JDBC ?
* Read https://spark.apache.org/docs/latest/sql-data-sources-jdbc.html

### Save data to a relational database, Postgres, using Spark
0. Check out the `docker-compose.yml` file to see what services you are going to use
1. Look at `src/main/scala/com.github.polomarcus/main/Main` 
2. Write code for `src/main/scala/com.github.polomarcus/utils/PostgresService`
3. Execute your code with `sbt run` and see if you have no errors

### BI : Use Metabase to explore data
#### Initialization 
1. To init Metabase on http://localhost:3000/ you need :
* configure an account with a hello world account
* configure PostgreSQL data source: (user/password - host : postgres - database name : metabase)
*  You're good to go : "Ask a simple question", then select your data source and the "News" table

#### Dashboards
Create a new dashboard where you can add these questions :

1. Display the last 10 news with these fields:  date, media, title, url
2. Display the last 10 news talking about climate change
3. Display the last 10 news **not** talking about climate change
4. You should have 3 questions now on your dashboard
5. Display the number of news talking about climate change by month and yeah
6. Display the number of news talking about climate change by month, year and by media
7. You can improvise your own queries

##### SQL Queries
8. What were the most trendy themes for each month of 2022 ?
Tips: a lot of of news title start with their main theme like this : 
* Climat : quelle sera la météo en 2100 ?
* Agriculture : la récolte du sel a débuté à Noirmoutier
* Sécheresse : la production nucléaire ralentie
* Climat : vague de chaleur sur la France, des conséquences pour l'agriculture
* Météo : sécheresse, chaleur… les questions des plus jeunes sur le climat

Do you see any pattern ?

Here's another tips to extract the first word :
```sql
SELECT SUBSTR("public"."news"."title", 0, position(':' in "public"."aa_news"."title")) AS theme
FROM "public"."news"
```

9. Calculate by percent the number of news talking about climate change by media by month and year
   Tips: use a SQL sub query

10. What were the number of news talking about the Ukraine and the COVID crisis ?
Tips: How could you use a CASE STATEMENT with SQL for a query ? https://www.w3schools.com/sql/sql_case.asp

Another tips : 
```sql
SELECT CASE 
    WHEN ((lower("public"."news"."description"))  LIKE '%covid%' OR (lower("public"."news"."title"))  LIKE '%covid%') THEN 'covid'
    ELSE 'others'
END AS group_theme,    
```