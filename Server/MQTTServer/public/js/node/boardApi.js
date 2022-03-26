var util = require('./util.js');
exports.getSearchData = (req,res)=>{
     
    var conn = util.getMySQLConnetion();
    // conn.connect();
    var dataList = [];
    console.log('/searchData 이벤트 액션');
    var query = 'select client_code from tbl_userID where client_id = ?';
    var params = [req.params.clientID];
  
    conn.query(query, params, function(err, rows, fields){
        if(err){
            res.status(500).json({"status_code": 500,"status_message": "internal server error"});
            throw err;
        }
        else{
           query = 'select * from tbl_messages where clientID = ? order by time desc'
           params = [rows[0].client_code];
           conn.query(query, params, (err, rows, fields)=>{
               if(err)throw err;
               else{
                   if(rows[0]){
                        for(var i = 0; i < rows.length; i++){
                            var data = {
                                'clientID':rows[i].clientID,
                                'topic':rows[i].topic,
                                'message':rows[i].message,
                                'time':rows[i].time,
                                'updated':rows[i].updated
                            }
                            
                            dataList.push(data);
                        }
                   }
                   res.render('board',{"Topic":topic,"clientCode":params[0],"dataList":dataList});
               }
           })
        }
    });
    // conn.end();
};