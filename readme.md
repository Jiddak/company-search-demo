Project notes:
 
  For an application like this one I would expect that responses are cached rather than permanently saved to a db, 
since you would expect that the data is likely to change.  
  For an approach involving saving to the db I would expect to download all the data and store it there overwriting with
whichever interval made sense.  
  Caching the response makes it both simpler to implement and also controls the retention of data. 
As such I added a caching mechanism to the client just to show how maybe that could be done.

I also chose to allow spring to create/drop the DB and schema rather than manually creating the SQL,
although typically this wouldn't be done on production application, for POC or example projects like this I don't believe
it is necessary for that level of robustness.

Due to this, whilst typically I would handle duplicates within the db itself I think it's out of scope for this project,
so is not the most efficient and makes more calls than necessary ideally.

Whilst I have followed the brief I did find it strange to pass the active flag as a query parameter, when already making a post request, 
which already contains information in the body.

I also would normally expect the api-key for a third party to be set in the client within the application for the duration of it's use, 
rather than passed through for every request.

For the mappers, I know some people may use Static classes instead of Spring components, 
and it does seem it is the "right" thing to do, but time and again I have found that starting them off 
as components makes them far more flexible, extensible and easy to unit test/mock whilst having negligible overhead compared
to static method classes.

For tests, I have only included unit tests as these cover all use cases, and I don't believe that the app is complex
enough to warrant duplicating testing to check that basic spring components work correctly together.

Some of the design of this application may be excessive, such as separating out the client to its own package,
although I just wanted to show how I would and have structured similar apps. By separating out the client code completely(
ideally into it's own artifact),it makes it far more difficult for developers to accidentally start mixing it with business logic
and allows far easier updating or removing of the client code.

Code coverage not perfect due to models, which whilst I could write tests to include every single model field,
it did not seem necessary for this assignment.

Other potential improvement's would be to have specific TrueProxyExceptions per case, and also to handle
exceptions in the controller to return more useful HTTP responses to the end user. Although again for a POC this seems
out of scope.