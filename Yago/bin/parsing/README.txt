updates:

parsing Yago:

1. added function to parse YagoSimpleTypes: now we can get the movies, actors, directors
2. added function to parse YagFact: now we know the facts about actedIn, directed
3. added function to parse YagoLiteralFacts: now we now have CreatedOnDate and duration
5. added function to parse YagoWikipediaInfo to get the url to wikipedia for each movie.

parsing Imdb:

1. added function to parse genres from imdb.
2. added function to parse plot from imdb.
3. added function to parse languae from  imdb.


How To Operate the operate everything:

Iparser parser = new Parser();
parser.parse(); \\ when we call this function it parses both Yago and IMDB
parser.getMoviesTable();
parser.getActorsTable();
parser.getDirectorsTable();


