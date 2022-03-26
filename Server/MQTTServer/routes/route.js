var express = require('express');
var router = express.Router();
var board = require('../public/js/node/boardApi.js');
var login = require('../public/js/node/loginApi.js');

router.get('/',(req,res)=>{
    res.redirect('/login');
})
router.get('/login',login.getLogin);
router.post('/login',login.postLogin);
router.post('/generate',login.postGen);
router.get('/searchData/:clientID',board.getSearchData);

module.exports = router;
