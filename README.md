# Query API

This project implements queries filter in generic way to REST APIs developed with Spring Data.

## How to install

This project is currently in developing mode. We don`t release stable version yet. Watch this project to follow its development.

## How to use

First step you need to add @RequestParam Map<String, String> at Controller method to collect all request parameters. After that, you need to create instance of QuerySpecification and pass this parameter to repository query, like this:

```java
@RestController
public class GrettingController {

    @Autowired
    private GreetingRepository repository;

    @GetMapping("/greeting")
    public Page<Greeting> greeting(@RequestParam Map<String, String> parameters, Pageable pageable) {
        return repository.findAll(new QuerySpecification(paramters), pageable);
    }
}
```

> You need extend JpaSpecificationExecutor at Repository to support Specification


Now, you will now be able to filter the data by any field of the entity.

## Queries

To filter data you need pass parameter with name "query". The value of query is the filter. Check some examples:

```
GET http://localhost:8080/greeting?query={"content": "Some text"}
```

In above example **content** is a field of Greeting model. You can use other operators:

```
GET http://localhost:8080/greeting?query={"content": {"$like": "Some%"}}
```

You can pass one or more filters criteria:

```
GET http://localhost:8080/greeting?query={"content": "Some text", "quantity": 10}
```

At moment the Query API supports only AND logical operators between filters criteria.

## Operatos

The Query Api supports these operators:

| Operator | Description           | Example                                      |
|----------|-----------------------|----------------------------------------------|
| $eq      | Equal operator        | {"content": {"$eq": "Some%"}}                |
| $like    | Like operator         | {"content": {"$eq": "Some%"}}                |
| $gt      | Greater than          | {"content": {"$gt": 10.9}}                   |
| $lt      | Less than             | {"content": {"$lt": 11}}                     |
| $ge      | Greater or equal than | {"content": {"$ge": "2022-07-11T00:00:00Z"}} |
| $le      | Less or equal than    | {"content": {"$le": "2021-07-11"}}           |

Query API supports Date filter in ISO format. You can use full timestamp or only Date.

## Filtering related data

Query API supports filters by some related data. Consider you have Entity Greeting related with other Entity in One to One relation.

```java
public class Greeting {

    public Greeting() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "other_id", referencedColumnName = "id")
    private SomeOther other;
    
}
```

and Some Other:

```java
public class SomeOther {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    public SomeOther() {
    }

}
```

In this example, you can filter Greeting by SomeOther text like this:

```
GET http://localhost:8080/greeting?query={"other.text": "Some other text"}
```

All operators are also available at this mode.