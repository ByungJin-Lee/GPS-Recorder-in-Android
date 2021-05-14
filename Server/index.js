//index.js
var express = require('express');
var app = express();

const port = 80;


app.get('/', function(req,res){
    res.send("hello");
});

app.listen(port, function(){
    console.log("server on! http://localhost:"+port);
});