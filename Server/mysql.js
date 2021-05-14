require('dotenv').config();

var mysql = require('mysql');
var connection = mysql.createConnection({
    host: process.env.ADDRESS,
    user:process.env.IDENTIFICATION,
    password:process.env.PASSWORD,
    database: process.env.DATABASE
});

connection.connect();

const query = "";

connection.query(query, function(error, result){
    if(error) throw error;
    console.log("The solution is: ", result[0].solution);
});


connection.end();