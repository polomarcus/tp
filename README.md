# Practical work 
* [Data engineering / distributed systems](https://github.com/polomarcus/tp/tree/main/data-engineering)
* [Ecodesign](https://github.com/polomarcus/tp/tree/main/ecodesign)

## Tools you need for data engineering
You should have Docker and a Scala IDE working properly on your computer.

### A Scala Integrated Development Environment (IDE) :
1. Install the IDE "[Intellij Idea Community](https://www.jetbrains.com/fr-fr/idea/download)": https://www.jetbrains.com/fr-fr/idea/download
2. Install [the Scala plugin](https://www.jetbrains.com/help/idea/run-for-the-first-time.html#additional_plugins), it will give you a SBT (Scala Build Tool) shell on the bottom of your IDE. 
3. [Fork this repo](https://docs.github.com/fr/pull-requests/collaborating-with-pull-requests/working-with-forks/fork-a-repo) to have your own copy.
4. Clone your fork on your machine
5. Open Intellij, go to "File" then "Open" and then only select the folder of the current exercice from your fork clone on your computer. Then, on IntelliJ [set up the scala SDK (Software Development Kit) and JDK (Java SDK)](https://www.jetbrains.com/help/idea/sdk.html)

#### Common mistakes
Do you have one of these errors ?
```
Extracting Structure Failed
```

```
Cannot determine Java VM executable in selected JDK
```

This will solve your problem: [do this](https://www.jetbrains.com/help/idea/sdk.html#add_global_sdk)
**Beware** : For Spark code, we have to use **Java 17** (JDK17) or inferior, otherwise you'll have [this error](https://stackoverflow.com/a/73349341/3535853)

Now, you can restart the SBT shell using [the button with the arrows.](https://www.jetbrains.com/help/idea/sbt-support.html#sbt_structure)
```
[success] Total time: 31 s
```
After a successful restart you can execute `run` or `test` commands.

### Docker and Compose
Take time to read and install

https://docs.docker.com/get-started/overview/
```
docker --version
Docker version XX.XX.XX
```

https://docs.docker.com/compose/
```
docker compose --version # Or docker compose --version
docker compose version X.XX.X
```

### Fork OR update the repo on your own Github account
#### Fork 
* Click on https://github.com/polomarcus/tp/fork

#### Update your fork
* https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/working-with-forks/syncing-a-fork
