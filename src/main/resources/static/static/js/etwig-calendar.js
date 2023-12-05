/**
 * The eTWIG event calendar for public submodule.
*/
  
var publicCalendarElem = document.getElementById('etwig-public-calendar');
var publicCalendarProperties = {
	view: 'timeGridWeek',
    headerToolbar: {
		start: 'prev,next today',
		center: 'title',
		end: 'dayGridMonth,timeGridWeek,timeGridDay,listWeek'
	},
        
	scrollTime: '09:00:00',
    events: getEventListByWeek("2023-12-04"),
    eventClick: function (info) {alert(info)},
    dayMaxEvents: true,
    nowIndicator: true,
    selectable: false,
    eventStartEditable: false
};
  
const publicCalendar = new EventCalendar(publicCalendarElem, publicCalendarProperties);
//getEventListByWeek("2023-12-04");
createEvents();

    function createEvents() {
        let days = [];
        for (let i = 0; i < 7; ++i) {
            let day = new Date();
            let diff = i - day.getDay();
            day.setDate(day.getDate() + diff);
            days[i] = day.getFullYear() + "-" + _pad(day.getMonth()+1) + "-" + _pad(day.getDate());
        }

        var a =  [
            {start: days[0] + " 00:00", end: days[0] + " 09:00", resourceId: 1, display: "background"},
            {start: days[1] + " 12:00", end: days[1] + " 14:00", resourceId: 2, display: "background"},
            {start: days[2] + " 17:00", end: days[2] + " 24:00", resourceId: 1, display: "background"},
            {start: days[0] + " 10:00", end: days[0] + " 14:00", resourceId: 1, title: "The calendar can display background and regular events", color: "#FE6B64"},
            {start: days[1] + " 16:00", end: days[2] + " 08:00", resourceId: 2, title: "An event may span to another day", color: "#B29DD9"},
            {start: days[2] + " 09:00", end: days[2] + " 13:00", resourceId: 2, title: "Events can be assigned to resources and the calendar has the resources view built-in", color: "#779ECB"},
            {start: days[3] + " 14:00", end: days[3] + " 20:00", resourceId: 1, title: "", color: "#FE6B64"},
            {start: days[3] + " 15:00", end: days[3] + " 18:00", resourceId: 1, title: "Overlapping events are positioned properly", color: "#779ECB"},
            {start: days[5] + " 10:00", end: days[5] + " 16:00", resourceId: 2, title: {html: "You have complete control over the <i><b>display</b></i> of events…"}, color: "#779ECB"},
            {start: days[5] + " 14:00", end: days[5] + " 19:00", resourceId: 2, title: "…and you can drag and drop the events!", color: "#FE6B64"},
            {start: days[5] + " 18:00", end: days[5] + " 21:00", resourceId: 2, title: "", color: "#B29DD9"},
            {start: days[1], end: days[3], resourceId: 1, title: "All-day events can be displayed at the top", color: "#B29DD9", allDay: true}
        ];
        console.log(a);
        console.log(typeof(a))
        return a;
    }

    function _pad(num) {
        let norm = Math.floor(Math.abs(num));
        return (norm < 10 ? '0' : '') + norm;
    }

class Event{
	constructor(start, end, title, color, allDay){
		this.start = start;
		this.end = end;
		this.title = title;
		this.color = color;
		this.allDay = allDay;
		this.resourceId = 1;
	}
}

function formatDate(date) {
    const pad = (num) => (num < 10 ? '0' + num : num);

    let year = date.getFullYear();
    let month = pad(date.getMonth() + 1); // Months are zero-based
    let day = pad(date.getDate());
    let hours = pad(date.getHours());
    let minutes = pad(date.getMinutes());
    let seconds = pad(date.getSeconds());

    return `${year}-${month}-${day} ${hours}:${minutes}`;
}


function getEventListByWeek(weekStr){
	var eventList = []; 
	var url = '/public/_getEventsByWeek?dateStr=' + weekStr;
	$.ajax({ 
		type: 'GET', 
    	url: url, 
    	async: false,
    	data: { 
			get_param: 'value' 
		}, 
    	dataType: 'json',
		success: function(json) {
			if(json.error > 0){
				$('#errorReason').html(json.msg);
    			$("#errorAlert").slideDown(500).delay(10000).slideUp(500);
			}else{
				jQuery.each(json.events, function() {
					//console.log(this);
					var eventStartDateTime = new Date(this.eventStartTime);
					var eventEndDateTime = new Date(eventStartDateTime.getTime() + this.eventDuration*60000);
					//console.log(formatDate(eventStartDateTime));
  					//console.log(this.portfolioColor);
  					//eventList.push(new Event(formatDate(eventStartDateTime), formatDate(eventEndDateTime), this.eventName, "#"+this.portfolioColor, false));
  					
  					eventList.push({
						  start: formatDate(eventStartDateTime), 
						  end: formatDate(eventEndDateTime), 
						  title: this.eventName + " @ " + this.eventLocation, 
						  color: "#"+this.portfolioColor}
					); 
  					//eventList.push({start: "2023-12-07 16:00", end: "2023-12-07 18:00", title: "ts", color: "#AE1B64"});
  					//eventList.push({start: "2023-12-05 10:00", end: "2023-12-05 14:00", resourceId: 1, title: "ts", color: "#FE6B64"});			
				})
			}
			
          	//console.log(json);   // needs to match the payload (i.e. json must have {value: "foo"}
          	//value = json.value;
        },
    	error: function(jqXHR, exception) {   		
    		$('#errorReason').html("HTTP Status " + jqXHR.status + " when attempt ro access " + url);
    		$("#errorAlert").slideDown(500).delay(10000).slideUp(500);
    		
		}
	});
	
	console.log(eventList);
	console.log(typeof(eventList))
	return eventList;
}