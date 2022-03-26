
var util = require('./util.js');
exports.getLogin = (req,res)=>{res.render('login')};
exports.postLogin = function (req, res) {
    var connId = util.getMySQLConnetion();

    connId.connect();
    var responseData ={}
    var query = 'SELECT * FROM tbl_userID WHERE `client_id`=?';

    var clientID = req.body.clientID;
    topic = req.body.topic;
    console.log(clientID+"  "+topic);
    var params = [clientID];
    connId.query(query, params, (err,rows) =>{
        if (err) throw err
        console.log(rows[0]);
        if (rows[0]) {
            responseData.result = "S001"
            responseData.msg = "정상 로그인 되었습니다."
          }
          else {
            responseData.result = "E001"
            responseData.msg = "로그인 실패하였습니다."
          }
          console.log(responseData);
          res.json(responseData)
    })
    connId.end();
};


exports.postGen = function (req, res) {
    var responseData = {}
    var newID = req.body.newID
    var clientCode = util.makeid()
    var connGen = util.getMySQLConnetion();
    // connGen.connect();
    console.log(newID+" "+clientCode);
    var query = 'SELECT `client_id` FROM `tbl_userID` where `client_id` = ?'
    var params = [newID];
    connGen.query(query, params, (err, row, fields) => {
        if(err) throw err;
        if (!row[0]){
            query = "INSERT INTO `tbl_userID`(`client_id`, `client_code`) VALUES(?, ?)"
            params = [newID, clientCode]
            connGen.query(query, params, (err, row, fields) => {
                if (err) throw err
                else{
                    responseData.result = "S001"
                    responseData.msg = "정상적으로 등록되었습니다."
                    responseData.clientCode = clientCode
                    res.json(responseData)
                }
            });

        }
        else {
            responseData.result = "E001"
            responseData.msg = "로그인 실패하였습니다."
            res.json(responseData)
        }
        
    });
    // connGen.end();
};