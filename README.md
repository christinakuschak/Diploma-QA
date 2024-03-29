# Дипломный проект по профессии «Тестировщик ПО»

## Документация

+ [План автоматизации](https://github.com/chrisemenova/Diploma-QA/blob/main/docs/Plan.md)
+ [Отчет по итогам тестирования](https://github.com/chrisemenova/Diploma-QA/blob/main/docs/Report.md)
+ [Отчет по итогам автоматизации](https://github.com/chrisemenova/Diploma-QA/blob/main/docs/Summary.md)

## Описание приложения
Приложение — это веб-сервис "Путешествие дня", который предлагает купить тур по определённой цене двумя способами:

1. Обычная оплата по дебетовой карте.
2. Уникальная технология: выдача кредита по данным банковской карты.

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

+ сервису платежей (далее - Payment Gate)
+ кредитному сервису (далее - Credit Gate)

Приложение должно в собственной СУБД сохранять информацию о том, каким способом был совершён платёж и успешно ли он был совершён (при этом данные карт сохранять не допускается).

## Начало работы

+ склонировать репозиторий 
```
git clone https://github.com/chrisemenova/Diploma-QA.git
```

+ для запуска контейнеров с MySql, PostgreSQL и Node.js использовать команду 
```
docker-compose up -d
```

+ запуск приложения:

для запуска под MySQL
```  
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar
```
  для запуска под PostgreSQL
```  
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar
```
+ запуск тестов:

для запуска под MySQL
```
./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"
```
   для запуска под PostgreSQL
    
```
./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"
```  
  
*По умолчанию тесты запускаются для http://localhost:8080/, чтобы изменить адрес, необходимо дополнительно указать `-Dsut.url=...`
  
*Чтобы использовать для подключения к БД логин и пароль отличные от указанных по умолчанию, необходимо дополнительно указать `-Ddb.user=...` и `-Ddb.password=...`

+ для получения отчета (Allure) использовать команду 
```
./gradlew allureServe
```

+ после окончания тестов завершить работу приложения (Ctrl+C), остановить контейнеры командой 
```
docker-compose down
```
