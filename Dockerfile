FROM mozilla/sbt:8u292_1.5.7

WORKDIR /usr/epf

COPY . .

CMD ["sbt", "run"]