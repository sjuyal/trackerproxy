var process = require('process');
var querystring = require('querystring');
var https = require('https');
requestLogger(require('https'));

var number_of_events = process.argv[2];
var radius = process.argv[3];

var router_host = "router.project-osrm.org";
var nearest_path_endpoint = "/route/v1/driving/";

// var events_only_distant_rides = {0:1,1:0,2:0}
// var events_only_close_rides = {0:0,1:1,2:0}
// var events_more_distant_rides = {0:0.6,1:0.2,2:0.2}
// var events_more_close_rides = {0:0.2,1:0.6,2:0.2}

//Taking Ejipura as centre of Bangalore to start with
var city_centre = [12.938661, 77.630346];
var old_location = city_centre;
var new_location = randomPoint(radius, old_location[0], old_location[1]);

var event_path_map = {};
var event_path_map_size = 0;

for (var i=0; i<number_of_events;i++){
	lat1 = old_location[0].toString();
	lon1 = old_location[1].toString();
	lat2 = new_location[0].toString();
	lon2 = new_location[1].toString();
	console.log("Old Location: " + lat1 + ", " + lon1);
	console.log("New Location: " + lat2 + ", " + lon2);
	hit('GET', router_host, nearest_path_endpoint + lon1 + "," + lat1 + ";" + lon2 + "," + lat2, {
		'geometries': 'geojson'
	}, i, function(data,eventId){
		var coordinates = data.routes[0].geometry.coordinates;
		var distance = data.routes[0].distance;
		var time = data.routes[0].duration;
		addPath(eventId, coordinates, distance, time);
		// console.log("distance: " + distance);
		// console.log("time: " + time);
		// for(var i=0;i<coordinates.length;i++){
		// 	console.log(coordinates[i][1] + "," + coordinates[i][0]);
		// }
		// var time_per_coordinate = Math.trunc(time*1000/(coordinates.length-1));
		// for(var i = 0; i < coordinates.length; i++){
		// 	all_coordinates.push({
		// 		"id": 1,
		// 		"time": timestamp,
		// 		"latitude": coordinates[i][1],
		// 		"longitude": coordinates[i][0]
		// 	});
		// 	timestamp = timestamp + time_per_coordinate;
		// }
		// console.log(all_coordinates);
	})

	old_location = new_location;
	new_location = randomPoint(radius, city_centre[0], city_centre[1]);
}


function addPath(eventId, coordinates, distance, time){
	event_path_map[eventId] = {
		"coordinates": coordinates,
		"distance": distance,
		"time": time
	}
	//console.log(event_path_map);
	event_path_map_size++;
	if(event_path_map_size==number_of_events){
		var all_coordinates = [];
		var timestamp = (new Date).getTime();
		for(var i =0; i<number_of_events; i++){
			//console.log("Iteration: " + i)
			event = event_path_map['' + i];
			coordinates = event["coordinates"];
			distance = event["distance"];
			time = event["time"];
			for(var j1=0; j1<coordinates.length; j1++){
				console.log(coordinates[j1][1] + "," + coordinates[j1][0]);
			}
			console.log("Path " + i + " Distance: " + distance);
			console.log("Path " + i + " Time: " + time);
			var time_per_coordinate = Math.trunc(time*1000/(coordinates.length-1));
			console.log("number: " + coordinates.length);
			console.log("TPC: " + time_per_coordinate);
			for(var j2= 0; j2<coordinates.length; j2++){
				all_coordinates.push({
					"deviceId": 8,
					"time": timestamp,
					"latitude": coordinates[j2][1],
					"longitude": coordinates[j2][0]
				});
				timestamp = timestamp + time_per_coordinate;
			}
		}
		for(var j2= 0; j2<all_coordinates.length; j2++){
			console.log(all_coordinates[j2])
		}
		//console.log(all_coordinates);

	}
}

//data : an object with param fields and values
//success: callback function in case of success
function hit(method, host, endpoint, data, eventId, success){
	headers = {};
	dataString = "";
	if(method == "GET"){
		endpoint = endpoint + '?' + querystring.stringify(data);
	} else {
		dataString = JSON.stringify(data);
		headers = {
			'Content-Type': 'application/json',
			'Content-Length': dataString.length
		}
	}

	var request = https.request(
		{
			host:host,
			path:endpoint,
			method:method,
			headers:headers
		},
		function(res) {
			res.setEncoding('utf-8');
			var responseString = '';
			res.on('data', function(data) {
    			responseString += data;
  			});
  			res.on('end', function() {
			    //If response code is 200-299 call success function
			    if(res.statusCode/100==2){
			    	var responseObject = JSON.parse(responseString);
			    	success(responseObject, eventId);
				} else {
					console.log('Error: ' + responseString);
					process.exit();
				}
  			});

		}
	);

	request.on('error', function(e) {
  				console.log('Error: ' + e.message);
  				process.exit();
  			});
	request.write(dataString);
	request.end();
}

function randomPoint(radius, latitude, longitude){
	var x0 = latitude;
	var y0 = longitude;
	var radius0 = radius;

	var rand1 = Math.random();
	var rand2 = Math.random();
	var theta = 2*Math.PI*rand1;
	var radius1 = radius0*Math.sqrt(rand2);

	var x = radius1*Math.cos(theta);
	//Correcting x for shrinking east-west distances
	x = x / Math.cos(y0);
	var y = radius1*Math.sin(theta);

	//console.log(x + "," + y);

	var ONE_DEGREE_IN_METRES = 111320;

	//console.log(x/ONE_DEGREE_IN_METRES + "," + y/ONE_DEGREE_IN_METRES);

	var x1 = +x0 + +(x/ONE_DEGREE_IN_METRES);
	var y1 = +y0 + +(y/ONE_DEGREE_IN_METRES);
	//console.log("New location selected randomly: " + x1 + "," + y1);
	return [x1,y1];
}

function requestLogger(httpModule){
    var original = httpModule.request
    httpModule.request = function(options, callback){
      console.log(options.href||options.proto+"://"+options.host+options.path, options.method)
      return original(options, callback)
    }
}
