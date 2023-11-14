# Secret Santa Lottery

![example workflow](https://github.com/ebcFlagman/secretsantalottery/actions/workflows/gradle.yml/badge.svg)
![GitHub release (with filter)](https://img.shields.io/github/v/release/ebcFlagman/secretsantalottery)

Application to draw a Secret Santa to a gifted person.

## run

`java -jar SecretSantaLottery-1.0.0.jar ----spring.config.location=<path-to-your-application.yml>`


## application.yml

```
spring:
  mail:
    host: <mail-host>
    port: 587
    username: <username>
    password: <password>
    properties:
      mail:
        smtp:
          auth: true
          starttls.enable: true
  main:
    banner-mode: off

xmas:
  shuffleRounds: 10
  text: "Hi {0}\n\nFor the christams eve on 26.12.2023 at Santa Clause, {1} was drawn to you.\n\nYour Secret Santa Lottery"
  participants:
    - name: secret_santa_1
      email: secret_santa_1@xmas.com
    - name: secret_santa_2
      email: secret_santa_2@xmas.com
```

### parameters

| Parameter |        Description        |
|-----------|:-------------------------:|
| {0}       | Name of the Secret Santa  |
| {1}       | Name of the gifted person |
