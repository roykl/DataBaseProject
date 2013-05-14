updates:

1. added function to parse YagoSimpleTypes: now we can get the movies, actors, directors
2. added function to parse YagFact: now we know the facts about actedIn, directed
3. added function to parse YagoLiteralFacts: now we now have CreatedOnDate and duration
4. *** currently the function that parses YagoLabels is deprecated because it doesn't add power to us.
5. added function to parse YagoWikipediaInfo to get the url to wikipedia for each movie.


to-do:

1. we may need to parse the date/duration format later on
2. delete the '<'  '>'