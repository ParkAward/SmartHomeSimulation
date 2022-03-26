/**********************************************************************************	*
*																					*
*FileCopyright :  https://gist.github.com/smching/ff414e868e80a6ee2fbc8261f8aebb8f	*
*																					*
*************************************************************************************/
var util = require('./public/js/node/util.js');
var mqtt = require('mqtt'); //https://www.npmjs.com/package/mqtt
var Topic = '#'; //subscribe to all topics
var Broker_URL = 'tcp://192.168.0.13';

var config = util.getConfig;

var options = {
	clientId: config.mqtt_clientId,
	port: config.mqtt_port,
	//username: config.mqtt_user,
	//password: config.mqtt_passwd,	
	keepalive : 60
};

var mysql = require('mysql'); //https://www.npmjs.com/package/mysql
//Create Connection
var connection = util.getMySQLConnetion();
var client;

class MqttHadler{
	connect(){
		client  = mqtt.connect(Broker_URL, options);
		connection.connect(function(err) {
			if (err) throw err;
			console.log("Database Connected!");
		});
		client.on('connect', mqtt_connect);
		client.on('reconnect', mqtt_reconnect);
		client.on('error', mqtt_error);
		client.on('message', mqtt_messsageReceived);
		client.on('close', mqtt_close);

	}

}


function mqtt_connect() {
    //console.log("Connecting MQTT");
    client.subscribe(Topic, mqtt_subscribe);
};

function mqtt_subscribe(err, granted) {
    console.log("Subscribed to " + Topic);
    if (err) {console.log(err);}
};

function mqtt_reconnect(err) {
    console.log("Reconnect MQTT");
    if (err) {console.log(err);}
	client  = mqtt.connect(Broker_URL, options);
};

function mqtt_error(err) {
    console.log("Error!");
	if (err) {console.log(err);}
};

function after_publish() {
	//do nothing
};

//receive a message from MQTT broker
function mqtt_messsageReceived(topic, message, packet) {
	try{
	var message_str = message.toString(); //convert byte array to string
	message_str = message_str.replace(/\n$/, ''); //remove new line
	// console.log(message_str);
	console.log(countInstances(message_str));

	//payload syntax: clientID,topic,message
	// message_str syntax clinentID|message
	if (countInstances(message_str) != 1) {
		console.log("Invalid payload");
		} else {	
		insert_message(topic, message_str, packet);
		// console.log(message_arr);
	}
	}catch(err){
		console.error(err);
	}
};

function mqtt_close() {
	console.log("Close MQTT");
};

////////////////////////////////////////////////////
///////////////////// MYSQL ////////////////////////
////////////////////////////////////////////////////

//insert a row into the tbl_messages table
function insert_message(topic, message_str, packet) {
	var message_arr = extract_string(message_str); //split a string into an array
	var clientID= message_arr[0];
	var message = message_arr[1];
	var sql = "INSERT INTO ?? (??,??,??) VALUES (?,?,?)";
	var params = ['tbl_messages', 'clientID', 'topic', 'message', clientID, topic, message];
	sql = mysql.format(sql, params);	
	
	connection.query(sql, function (error, results) {
		if (error) throw error;
		console.log("Message added: " + message_str);
	}); 
};

//count number of delimiters in a string
var delimiter = "|";

//split a string into an array of substrings
function extract_string(message_str) {
	var message_arr = message_str.split(delimiter); //convert to array	
	return message_arr;
};	


function countInstances(message_str) {
	var substrings = message_str.split(delimiter);
	return substrings.length - 1;
};

module.exports = MqttHadler;