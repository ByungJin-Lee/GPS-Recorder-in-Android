require('dotenv').config();
require('date-utils');
//index.js
var express = require('express');
var date = new Date();
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
    console.log("연결!");
    res.send("okay");
    // const mac = req.query.mac;
    // if(mac.length != 17){
    //     res.send(null);        
    // }else{
    //     const time = date.toFormat('YYYY-MM-DD HH24:MI:SS');
        
    // }    
});

//rounting

app.post('/gps/:id', (req,res)=>{
    console.log(req.params.id);
    console.log(req.body);
})

app.get('/gps_d', (req,res)=>{      
    const mac = req.query.mac;
    if(mac.length == 17){
        db.query(`SELECT * From gps_record where MAC='${mac}'`, function(error, result){
            if(error) {
                res.log(error);
            }else{                
                res.send(result);
            }    
        });
    }else{
        res.send("Not Right MAC");
    }
});

app.listen(port, function(){
    console.log("server on! http://localhost:"+port);
});

let users = [
    {
        id : 1,
        name : "Hyne",
    },
    {
        id : 2,
        name : "ne",
    },
    {   
        id : 3,
        name : "sdfsfd",
    }
]