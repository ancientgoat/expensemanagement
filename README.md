


Features 
* Each repository has 'exported = false'
  * This allows a Controller to still use the MongoRepository bulit in features.
  * However, it doesn't expose the build in REST calls to the outside world.
* More than one error is returned at a time.  Saving time during debugging. 
* A projection links Merchant and ExpenseReport as output.