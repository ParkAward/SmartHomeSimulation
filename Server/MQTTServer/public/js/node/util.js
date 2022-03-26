const mysql = require('mysql');
const path = require('path');
const fs = require('fs');
var config = JSON.parse(fs.readFileSync(path.join(__dirname ,'../../../', '/config.json')));
module.exports ={
  makeid(){
    var text = "";
    var possible = "abcdefghijklmnopqrstuvwxyz123456789";
  
    for (var i = 0; i < 11; i++)
      text += possible.charAt(Math.floor(Math.random() * possible.length));
  
    return text;
  },
  getMySQLConnetion(){
    return mysql.createConnection({
      host: config.mysql_addr,
      user: config.mysql_id,
      password: config.mysql_passwd,
      database: config.mysql_db,
      dateStrings: true
  });
  },
  getConfig(){
    return config
  }
};
