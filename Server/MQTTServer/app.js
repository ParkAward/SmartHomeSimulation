var createError = require('http-errors');
var express = require('express');
var path = require('path');
var cookieParser = require('cookie-parser');
var logger = require('morgan');
var mqttHandler = require('./mqtt_handler');
var route = require('./routes/route');
var getdata = require('./routes/getdata');
var app = express();

// view engine setup
app.set('views', path.join(__dirname, 'views'));
app.set('view engine', 'pug');

var mqttClient = new mqttHandler();
mqttClient.connect();
app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.use(cookieParser());
app.use("/",express.static(path.join(__dirname, 'public')));
app.use('/node_modules', express.static(path.join(__dirname, '/node_modules')));
app.use('/js', express.static(__dirname + '/node_modules/jquery/dist')); // redirect JS jQuery

app.use('/', route);
app.use('/getData',getdata);


// catch 404 and forward to error handler
app.use(function(req, res, next) {
  next(createError(404));
});

// error handler
app.use(function(err, req, res, next) {
  // set locals, only providing error in development
  res.locals.message = err.message;
  res.locals.error = req.app.get('env') === 'development' ? err : {};

  // render the error page
  res.status(err.status || 500);
  res.render('error');
});

module.exports = app;
