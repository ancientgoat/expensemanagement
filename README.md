
## Caveat
I have never used Mongodb before, so the entities I created have hardcoded 
attributes/properties.  They seem to work just fine.

## Extra Credit 

#### Dynamic queries
* I implemented dynamic queries by using 'querydsl'.  Querydsl auto generates a few classes
    corresponding to each Entity.  And allows, if setup in the controller and repository 
    properly, a dynamic query filter.
  * Examples:            
    * http://localhost:8080/expenses?status=REIMBURSED
    * http://localhost:8080/expenses?merchant.name=xyx

#### Security - Basic Auth
I implemented Basic Authorization with spring.  The username, etc. are located in the application.properties.
 
I did something a little different I duplicated the Rest calls for both /** and /auth/**.  However, 
only the /auth/** path is locked down by security.  As an example, the above URLs in the previous section
are open to anyone, but the same URLs with the prefix /auth/ will need demo/demo as the basic authorization.   
  * Secured Examples:            
    * http://localhost:8080/auth/expenses?status=REIMBURSED
    * http://localhost:8080/auth/expenses?merchant.name=xyx

## Features 

* Each repository has 'exported = false'.  This allows a Controller to still use the 
    MongoRepository bulit in features.  However, it doesn't expose the build in REST 
    calls to the outside world.
* More than one error/warn/info message can be returned at a time.  Saving time during debugging. 
* Date json output is human readable in the java format "yyyy-MM-dd'T'HH:mm:ssZ". 
* Added Created_Date to the ExpenseReport for grins.
* Messages have three types ERROR, WARN, INFO
* Update shows 'No Changes' if there were no changes with an INFO message.
* Update does not write to the database if there were no changes, for speed.
* I implemented a 'Generic' class 'UpdateHelper' to show a bit of code reuse.
* I tend to use the builder pattern a lot.

### Examples:

#### Insert

```
POST
http://localhost:8080/expense
{
     "amount": 100.01
    , "expenseDateTime": "2016-02-01T01:02:03.000Z"
    , "comment" : "comment_001"
    , "merchant": { "name" : "Pete" }
}
```

#### Update

```
PUT
http://localhost:8080/expense/56dcd0f47d4c0450c2342448
{
    "comment" : "comment_002"
    , "merchant" : { "name" : "Frank" }
}
```
#### Delete

```
DELETE
http://localhost:8080/expense/56dcd0f47d4c0450c2342448
```
