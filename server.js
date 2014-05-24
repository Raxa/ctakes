var http = require("http");
var mysql = require("db-mysql");
var querystring = require("querystring");
var url = require("url");


function onRequest(request, response) {
  console.log("Request received.");

  var query = url.parse(request.url).query;
  var queryTerm = querystring.parse(query)['term'];
  console.log(query);
  console.log(queryTerm);

  response.writeHead(200, {
      'Content-Type' : 'x-application/json'
  });

  var json = '';
    
  new mysql.Database({
    hostname: 'localhost',
    user: 'root',
    password: 'r',
    database: 'Gsoc'
  }).connect(function(error) {
    if (error) {
        console.log("CONNECTION ERROR: " + error);
    }

  this.query('SELECT * FROM doctorModule WHERE Text LIKE ?',['%'+queryTerm+'%']).execute(function(error, rows) {
    if (error) {
      console.log('ERROR: ' + error);
    }
    console.log(rows.length + ' ROWS');
    json = JSON.stringify(rows.items);
    console.log('JSON-result:', json);
    response.end(json);
    });
  });

}

http.createServer(onRequest).listen(8888);
console.log("Server has started.");
