
Caveat
--------------
I have never used Mongodb before, so the entities I created have hardcoded attributes/properties.
Seems to work just fine.



Features 
--------------
* Each repository has 'exported = false'
  * This allows a Controller to still use the MongoRepository bulit in features.
  * However, it doesn't expose the build in REST calls to the outside world.
* More than one error is returned at a time.  Saving time during debugging. 
* A projection links Merchant and ExpenseReport as output.

* Date json output is human readable.
* Added Created_Date for grins

* Messages have three types ERROR, WARN, INFO

* Update shows 'No Changes' if there were no changes with an INFO message.
* Update does not write to the database if there were no changes, for speed.


* Dynamic queries (querydsl)
  * http://localhost:8080/expenses?merchant.name=xyx

* 