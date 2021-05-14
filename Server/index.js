//index.js
var express = require('express');
const app = express();

const port = 3000;

app.get('/', (req,res)=>{
    res.send("hello");
});

app.get('/users', (req,res)=>{
    return res.json(users);
});

app.get('/gps_data/:mac', (req,res)=>{    
    const mac = req.params.mac;
    if(mac.length > 0){
        //ToDo
    }else{
        return "There is No Data by MAC("+mac+")";
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