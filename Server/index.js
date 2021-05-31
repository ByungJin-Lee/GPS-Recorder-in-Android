require('dotenv').config();
require('date-utils');
//index.js
var express = require('express');

var fs = require('fs');
const app = express();
const port = 80;

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
    try{
        /**
         * @type {String}
         */
        let date = new Date();
        date = date.toFormat('YYYY-MM-DD HH24:MI:SS');
        date = `${date.substring(0,10)}T${date.substring(11)}Z`        
        let data = {
            IDENTIFICATION : req.params.id,
            TIME : date,
            LATITUDE : req.query.latitude,
            LONGITUDE : req.query.longitude
        }
        if(data.IDENTIFICATION.length == 16){
            if(data.LATITUDE.length == 10 && data.LONGITUDE.length == 11){
                db.query(`INSERT INTO gps_record (MAC, TIME, Latitude, Longitude) VALUES ('${data.IDENTIFICATION}', '${data.TIME}', '${data.LATITUDE}', '${data.LONGITUDE}')`, function(error, result){
                    if(error){
                        console.log(error);
                    }else{
                        console.log(`[${data.TIME}] GPS (Latitude-${data.LATITUDE} Longitude-${data.LONGITUDE}) From ${data.IDENTIFICATION}`);
                    }
                    res.send("Okay");
                }); 
            }else{
                console.log("not ac");
                res.send("not accu");
            }
               
        }else{
            res.send("NOT RIGHT");
        }
    }catch{
        res.send("Error");
    }    
});
//rounting
app.get('/get/:id/:all/:code', (req,res)=>{           
    if(req.params.code != process.env.NUMBER){
        res.send("CANT'T ACCESS!");        
    }else{
        const IDENTIFICATION = req.params.id;
        const all = req.params.all;
        db.query(`SELECT * From gps_record where MAC='${IDENTIFICATION}'`, function(error, result){
            if(error) {
                res.log(error);
            }else{
                console.log(`IP[${req.headers['x-forwarded-for'] ||  req.connection.remoteAddress}] - access (${IDENTIFICATION})`);
                if(all == "true"){
                    res.send(result);
                }else if(all == "gpx"){
                    res.set("content-type",'text/plain');
                    res.send(writeGPX(result));
                }else{
                    /**
                     * @type {Array}
                     */                    
                    res.send(result.pop());
                }
                
            }    
        });    
    }
});

app.listen(port, function(){
    console.log("server on! http://localhost:"+port);
});

//Method - gps
/**
 * 
 * @param {Array} resultList 
 */
function writeGPX(resultList){
    let gpx_data = "";
    for(i = 0; i < resultList.length; i++){
        gpx_data += `<trkpt lat="${resultList[i].Latitude}" lon="${resultList[i].Longitude}"><ele></ele><time>${resultList[i].TIME}</time></trkpt>`
    }
    return `<?xml version="1.0" encoding="UTF-8"?><gpx xmlns="http://www.topografix.com/GPX/1/1" xmlns:xalan="http://xml.apache.org/xalan" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" creator="MotionX Live" version="1.1"><trk><name>GPS_Temp</name><desc></desc><trkseg>${gpx_data}</trkseg></trk></gpx>`
}