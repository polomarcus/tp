# Practices - Data engineering

## Functional programming for data engineering
You're the new data engineer of a scientific team in charge of monitoring CO2 levels in atmosphere, [which are at their highest in 800,000 years.](https://www.weforum.org/agenda/2018/05/earth-just-hit-a-terrifying-milestone-for-the-first-time-in-more-than-800-000-years).

You have to give your best estimate of CO2 levels for 2050. 

Your engineering team is famous for taking a great care of **the developer experience: using Type, small functions (using `.map`, `.filter`, `.reduce`), tests and logs.**

Your goal is to map, parse, filter CO2 concentration levels in the atmosphere coming from an observatory in Hawaii from 1950 to 2022.

For convenience, CO2 concentration levels have been inserted inside this file `utils/ClimateService`.

![](https://assets.weforum.org/editor/large_EEYnarb17Mwon7wYfBZ_V6gUQ3hwp6_tpzpPzAMVLRw.png)

### How to write a Scala application ?
* Install a Scala compatible IDE, have a look at this documentation : https://github.com/polomarcus/tp/tree/main?tab=readme-ov-file#a-scala-ide-

### From IntelliJ Idea
Using the [sbt shell](https://www.google.com/search?channel=fs&client=ubuntu-sn&q=intellij+idea+sbt+shell)
```sbt
run # inside the sbt shell - or sbt run if you are not inside the sbt shell
```

Should give you `an implementation is missing` error :
```bash
(...)
2022-05-16 18:33:48 [run-main-0] INFO  com.github.polomarcus.main.Main$ - Starting the app
[error] (run-main-0) scala.NotImplementedError: an implementation is missing
```

Same for `sbt test`

#### You couldn't install SBT on your machine

**Tips:** having trouble to install Idea, SBT or scala? You can use Docker and Docker Compose to run this code and use your default IDE to code or a web IDE https://scastie.scala-lang.org/:

```bash
docker compose build my-scala-app
docker compose run my-scala-app bash # connect to your container to acces to SBT
> sbt test
# or 
> sbt run
```

### Continuous build and test
**Pro Tips** : https://www.scala-sbt.org/1.x/docs/Running.html#Continuous+build+and+test

Make a command run when one or more source files change by prefixing the command with ~. For example, in sbt shell try:
```bash 
sbt
> ~ testQuick
```

### Test Driven Development (TDD) - Write a function and its tests that detect climate related sentence
1. Look at and update the function called "isClimateRelated" to add one more test `test/scala/ClimateServiceTest`
2. Look at and update  the function called "isClimateRelated" inside `main/scala/com/github/polomarcus/utils/ClimateService`
3. To see if your code works, run `testOnly ClimateServiceTest -- -z isClimateRelated` (or directly `test` inside "sbt"

### Write a function that use `Option[T]` to handle CO2 Record
With data coming from Hawaii about CO2 concentration in the atmosphere (they are stored inside the function "getCO2RawDataFromHawaii()", iterate over it and find the difference between the max and the min value.

1. Look at and update "parseRawData" to add one more test `test/scala/ClimateService`
2. Look at and update "parseRawData" function inside `main/scala/com/github/polomarcus/utils/ClimateService`
3. Create your own function to find the min, max value. Write unit tests and run `sbt test`

**Tips**:
* Use scala API to get max and min from a list : https://www.w3resource.com/scala-exercises/list/scala-list-exercise-6.php
* You can also use "reduce functions" such as `foldLeft` : https://alvinalexander.com/scala/how-to-walk-scala-collections-reduceleft-foldright-cookbook/

4. Create your own function to find the min, max value for a specific year. Write unit tests
**Tips:** 
* Re use `getMinMax` to create this function :
5. Create your own function to difference between the max and the min. Write unit tests

Tips: 
* https://www.tutorialspoint.com/scala/scala_options.htm
* https://blog.engineering.publicissapient.fr/2012/03/19/les-types-monadiques-de-scala-le-type-option/

### Iteration - filter
1. Remove all data from december (12), *winter makes data unreliable there*, values with `filterDecemberData` inside `main/scala/com/github/polomarcus/utils/ClimateService`

### Iteration - map
1. implement `showCO2Data`  inside `main/scala/com/github/polomarcus/utils/ClimateService`
2. Make your Main program works using `sbt run`

### Bonus
Estimate CO2 levels for 2050 based on past data.

### How would you do if a continuous stream of data come ?
Tips: Batch processing / Stream processing ? 

### Continuous Integration (CI)
If it works on your machine, congrats ! But remember, engineers have to work as team and to be sure it works on others' machines, you have to do something more.

Test it on a remote servers now thanks to a Continuous Integration (CI) system such as [GitHub Actions](https://github.com/features/actions) :

1. Have a look to the `.github/workflows` folder and files
2. Something weird ? Have a look to their documentation : https://github.com/features/actions
3. Ready to run a CI job ? Go on your Github's Fork/Clone of this and find the "Action" tab 
4. Find your CI job running
5. Create a CI workflows using Docker to run the `sbt test` command (inspiration : https://github.com/polomarcus/television-news-analyser/blob/main/.github/workflows/docker-compose.yml#L7-L17)

