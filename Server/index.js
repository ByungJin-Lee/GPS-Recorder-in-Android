require('dotenv').config();
require('date-utils');
//index.js
var express = require('express');
const app = express();
const bodyParser = require('body-parser');
const port = 80;

app.use(bodyParser.urlencoded({extended:false}));
app.use(bodyParser.json());

//Mysql
var mysql = require('mysql');
var db = mysql.createConnection({
    host: process.env.ADDRESS,
    user:process.env.IDENTIFICATION,
    password:process.env.PASSWORD,
    database: process.env.DATABASE,
    port: process.env.PORT
});

db.connect();

//Mysql
app.get('/:id', (req,res)=>{
    let date = new Date();    
    let data = {
        IDENTIFICATION : req.params.id,
        TIME : date.toFormat('YYYY-MM-DD HH24:MI:SS'),
        LATITUDE : req.query.latitude,
        LONGITUDE : req.query.longitude
    }
    db.query(`NSERT INTO gps_record (MAC, TIME, Latitude, Longitude) VALUES ('${data.IDENTIFICATION}', '${data.TIME}', '${data.LATITUDE}', '${data.LONGITUDE}')`, function(error, result){
        if(error){
            console.log(error);
        }else{
            console.log(`[${data.TIME}] GPS (Latitude-${data.LATITUDE} Longitude-${data.LONGITUDE}) From ${data.IDENTIFICATION}`);
        }
        res.send("Okay");
    });    
});
//rounting
app.get('/get/:id', (req,res)=>{              
    db.query(`SELECT * From gps_record where MAC='${mac}'`, function(error, result){
        if(error) {
            res.log(error);
        }else{                
            res.send(result);
        }    
    });    
});

app.listen(port, function(){
    console.log("server on! http://localhost:"+port);
});