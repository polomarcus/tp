# Docker and Compose 
You are going to start a [jupyter lab notebook](https://jupyter.org/install) via Docker, to explore some data ([news from french TV of 2022](https://github.com/polomarcus/television-news-analyser/blob/main/data-news-csv/year%3D2022/part-00000-d964c139-19ed-47cf-b389-49b1f624aa7c.c000.csv.gz)) and save it to a Postgresql database.

## Dockerize your project - Why are we doing this ?
Some of your colleagues use a **different Operating System** than yours, or does not have your dev utilities installed, so you decide to use Docker to be able to work with them. How smart of you !

To be sure to have well understood why Docker, you have read [this article](https://www.epauler.fr/article/simplify-your-tests-and-development-with-docker-and-docker-compose/).

No joke, read [this article](https://www.epauler.fr/article/simplify-your-tests-and-development-with-docker-and-docker-compose/). It lists a lot of useful commands and you might need.

Inside the article you've just read (i hope), you find this article [10 docker-compose and docker commands that are useful for active development](https://dev.to/aduranil/10-docker-compose-and-docker-commands-that-are-useful-for-active-development-22f9) and it's going to be useful for what comes next.

### Step 0 - Install Docker 
As many co-workers use different systems, you want to avoid errors such as "it works on my machine, but not yours" and you decide to use [Docker (click to see install link)](https://docs.docker.com/get-started/get-docker/).

**Windows users**, you might have to [install WSL and follow this guide.](https://forums.docker.com/t/an-unexpected-error-was-encountered-while-executing-a-wsl-command/137525/40) and [enabled Virtualisation in your BIOS](https://lecrabeinfo.net/activer-la-virtualisation-intel-vt-x-amd-v-dans-le-bios-uefi.html) (restart your computer to enter your BIOS (using F2 or F10 depending on your computer brand). BIOS stands for basic input/output system.

### Step 1 - Use Jupyterlab using Docker
So you will **NOT** install jupyter via `pip install jupyterlab` (or you **will not** use the installed one if you have it already, for example on Visual Studio), but you are going to follow this blog post to **use Docker**, [Jupyter notebook development workspace using Docker, Docker Compose and Git](https://nezhar.com/blog/jupyter-notebook-development-workspace-using-docker-and-git), to :

* Create a `docker-compose.yml` file on a local folder on your computer based on what you have seen on the blog post "Minimal Setup" (you can also find one inside this repo's folder called "docker-compose.yml")
* Add this line to your docker-compose.yml you've just created :
```
    container_name: jupyter_notebook # this line should already be there
    environment:
      - DOCKER=congrats_you_are_inside_a_docker_container
```
* Using your terminal, go to your local folder and start your service with `docker compose up`

> Make sure to copy the token that will be output in the console when running docker-compose up, as it is required to log in :
```
jupyter_notebook  |     To access the server, open this file in a browser:
jupyter_notebook  |         file:///home/jovyan/.local/share/jupyter/runtime/jpserver-7-open.html
jupyter_notebook  |     Or copy and paste one of these URLs:
jupyter_notebook  |         http://ce18db785c02:8888/lab?token=56a390f9bfb64302791f920a8f7a994531fc020f5b75bb0a
jupyter_notebook  |         http://127.0.0.1:8888/lab?token=56a390f9bfb64302791f920a8f7a994531fc020f5b75bb0a
```
* Go to "http://127.0.0.1:8888/lab?token=56a390f9bfb64302791f920a8f7a994531fc020f5b75bb0a" using your web browser

### Step 2 - Check
* once started, use `docker ps` (or your docker client) to get your container name
* now we are going to connect to it using `docker exec -ti jupyter_notebook bash`, the normal output should be :
```
docker exec -ti jupyter_notebook bash                                                                                                   17:50:26
(base) jovyan@5e4cb923583f:~$ ls
```

## Step 3 - Coding some functions
Using a new notebook, or the one already created inside the "work" folder and the file "explore-tv-news.ipynb", explore how many items we have inside the CSV "tv-news-2022-french.csv", the different type of media, etc. using Pandas.

1. Execute the first cells until this cell included `print(os.environ.get("DOCKER", "not started with docker :("))` 
2. Try to import `import pandas as pd`, you **should have an error**
3. As advanced data scientists, you know that you should install pandas, but if you do so you will lose the reproductibility of docker, and you want it install on every computer that use this notebook. What can you do ?
4. Still from this blog post,[Jupyter notebook development workspace using Docker, Docker Compose and Git](https://nezhar.com/blog/jupyter-notebook-development-workspace-using-docker-and-git), you are going to use a Dockerfile that is going to run `pip install pandas` for you (and your teammates). Side note, you can alternatively use [this technic](https://stackoverflow.com/a/54557047/3535853).
5. Create your Dockerfile (as on the blog post)
6. Adapt your docker-compose.yml file (as on the blog post)
7. re run your container : `docker compose down` and `docker compose up`
8. re try to import your pandas cell now and see if it works.
9. **Bonus only**: Explore "tv-news-2022-french.csv" data. 
10. **Bonus only**: Using [CountVectorizer](https://scikit-learn.org/stable/modules/generated/sklearn.feature_extraction.text.CountVectorizer.html), count the main subjects of 2022.
Help: 
```
from sklearn.feature_extraction.text import CountVectorizer
```

### Step 4 - Use PostgreSQL
To save our CSV data to a SQL database, we are going to add a service to our docker-compose.yml file.

1. Try a postgresql image on [the Docker Hub](https://hub.docker.com/_/postgres)
2. Modify your docker-compose.yml file
3. Configure it with environment variables (such as POSTGRES_PASSWORD, POSTGRES_USER, etc. available on the docker hub page)
4. Using Pandas save your data into Postgresql modifying your `requirements.txt` file to add Postgresql dependencies
5. To be sure our container knows it has to add a new dependencies we have to do `docker compose down` and `docker compose up --build`, the `--build` part is mandatory.
6. Check on your notebook that you can save your dataframe into Postgresql without errors

### Step 5 - Publish an image
You can make your image public by using the docker hub : https://docs.docker.com/guides/walkthroughs/publish-your-image/

1. Create your account
2. Publish your image
3. **Bonus:** Download it and use it locally. Find some useful information about [how to use or build an image here as well.](https://docs.docker.com/reference/compose-file/build/#using-build-and-image)

### Step 6 - Continuous Integration (CI)
If it works on your machine, congrats ! But remember, engineers have to work as team and to be sure it works on others' machines, you have to do something more.

Test it on a remote servers now thanks to a Continuous Integration (CI) system such as [GitHub Actions](https://github.com/features/actions) :

1. Have a look to the `.github/workflows` folder and files
2. Something weird ? Have a look to their documentation : https://github.com/features/actions
3. Ready to run a CI job ? Go on your Github's Fork/Clone of this repository (or create a new repo on your github) and find the "Action" tab - you may have to activate it on your repo's Settings
4. Create your first Github Actions thanks to a file inside `.github/workflows`
5. Find your CI job running

Every commit on a branch should start a CI job to start the containers and display the logs.

You can find [help for this step here](https://github.com/polomarcus/tp/issues/2#issue-1669333101)

### Step 7 : HuggingFace
Use [HuggingFace](https://huggingface.co/) to deploy a [JupyterLab application on a HuggingFace Space using Docker in a new Space](https://huggingface.co/new-space)
