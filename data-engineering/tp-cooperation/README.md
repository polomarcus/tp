## Coding a service 
Your service displays the latest tv news about drought (contained inside the file `drought-tv-news.csv`)


## Organize your repo and team work
### Issue and Pull request
You've received some Issues on your github repo, but they were messy and you had to clarify multiples things before understanding the problem.

Thanks to this, you have decided to create a template to structure issues received on your repo, and at the same time a pull requests :
* Create a **issue template** on your repo
* create a **pull request template** on your repo
Find out how to make this here : https://docs.github.com/en/communities/using-templates-to-encourage-useful-issues-and-pull-requests/about-issue-and-pull-request-templates

After you've pushed your changes, [create an issue](https://docs.github.com/en/issues/tracking-your-work-with-issues/creating-an-issue) on your repo or somebody else's repo.

Make some changes and [create a pull request](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request) on somebody else's repo.

### Add someone to your repo
It's not fun to code alone, add one or 2 persons as collaborators of your repo.

You can use a git shell or [github desktop](https://desktop.github.com/)

## Dockerize your project
Some of your colleagues use a different Operating System than yours, or does not have Scala Build Tool (SBT) installed, so you decide to use Docker.

### Step 1 - Docker
As many co-workers use different systems, you want to avoid errors such as "it works on my machine, but not yours" and you decide to use [Docker](https://docs.docker.com/build/) :
* Create a dockerfile
* Start your service with docker

### Step 2 - Compose
As it's more convenient and easy to use [Compose](https://docs.docker.com/compose/), we are going to use it
* Create a docker-compose
* start your service with your docker compose

## Step 3 - Coding some functions
Follow the instruction inside the file "Main"

You should be able to start the project with Docker
```bash
docker compose run YOUR_SERVICE_NAME bash # connect to your container to access to Scala Build Tool (sbt)
> sbt test
# or 
> sbt run
```

### Step 4 - a test coverage
How can you be sure that you have not forgotten some parts of the code to test ? 

You know that you can generate those kind of reports thanks to [sbt coverage](https://github.com/scoverage/sbt-scoverage#usage)

```bash
sbt clean coverage test
sbt coverageReport
```

Coverage reports will be in your target/scala-<scala-version>/scoverage-report directory. There are HTML and XML reports. The XML is useful if you need to programatically use the results, or if you're writing a tool.

Open in your web browser the report, and see what you can do better and what is your code coverage percentage :
* tp/data-engineering/tp-cooperation/target/scala-2.13/scoverage-report/index.html

### Step 5 - Docker on the CI
Update your CI to use your service `my-scala-app` with docker

Every commit on a branch should start a CI job to run test : `sbt test`

You can find [help for this step here](https://github.com/polomarcus/tp/issues/2#issuecomment-1562833864)

### Step 6 - code coverage on the CI
Add the coverage information on your CI and pull request meta data.

### Step 7 - log management - système de log
Use a free account from NewRelic, Datadog or Sentry to set up a log management system to be alerted when something goes wrong for your application.

## Closer to a real project

### Create a website
Your website showing some tv news should be an accesible from your localhost, example : "http://locahost:8080"

You are free to use your favorite framework and language.

### Add a database
You would like to save some data, so you decide to use a database. Instead of downloading the ZIP file, you will use your docker-compose file
* Start [PostgresSQL](https://hub.docker.com/_/postgres)
* Code a function that save a news
* Write a test for this function and run it locally
* Run this test on the CI

### To explore your database, use a Business Intelligence Tool
Using your docker compose, you can use Metabase instead of DBBeaver.

An example of a Docker metabase configuration can be found here : https://github.com/dataforgoodfr/quotaclimat/blob/18710ccbe77a66e438fe608f4cce54a51a6cfac5/docker-compose.yml#L155-L173

## Unit tests
Functions that parse, transform, filter data should be unit tested.

If a function is to hard to test you should decompose it.

## Functional tests
After you've watched [this video](https://www.youtube.com/watch?v=0GypdsJulKE), you have decided to write functional tests (end to end).
You've heard of [Cypress](https://www.cypress.io/) (Javascript) or [Selenium](https://www.selenium.dev/) (Python/java), that simulate a browser and users actions to test a website.

## How to test a lot of different devices ?
Your service is now used by a lot of users, and by a lot of different devices. The thing is you can not test all devices, or by wasting a lot of time.
You've heard to the service [SauceLabs](https://saucelabs.com/), you can integrate with Github actions to test on multiple devices.

## An external service
Your service depends now on an external service (a 3rd party) to get data from news that you cannot control. How can you test their response ?

What about [mocking them](https://en.wikipedia.org/wiki/Mock_object) ?

## Continuous Deployment (CD)
From Github Actions, you will deploy your service on [Scalingo](https://scalingo.com/) (if you need a DB - no credit card required) or [Github Pages](https://pages.github.com/) (for static web pages without databases) after every commit on the branch `main`

