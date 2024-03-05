
/**
 * Constants
 */

// **Weekday** events (DoW=[1,2,3,4,5]), N=9 (9:00 - 18:00)
const WEEKDAY_HOURS = [10, 11, 12, 13, 14, 15, 16, 17, 18];
                
// **Weekend** events(DoW=[0,6]), N=3 (morning 9:00-12:00, afternoon 13:00-18:00, evening 19:00-21:00),
const WEEKEND_HOURS = [9, 15, 21];

const DEBUG_MODE = false;

var img;
var twig = undefined;
var assetCollection = new Map();

//var singleTimeEventList = undefined;
//var recurringEventList = undefined;
var eventMap = {};

// Page orientation: true landscape, false portrait.
var pageOrientation = true;

var setting = new TwigSettings();


// Is the setting panel opened
var settingOpened = false;

function preload(){
    //pageOrientation = windowWidth > windowHeight;

    // Initialize variable and elements.
    var datepicker = createDatePicker();
    

    var portfolioId = -1;
    var date = '';
    
    // Browser check.
    //if(!DEBUG_MODE && typeof window.chrome !== "object"){
    //    infoPopup("For the best experience, Chromium-based browsers are recommended", "However, your web browser is " + getBrowserName(navigator.userAgent));
    //}

    // Get portfolio from URL parameter.
    var searchParams = new URLSearchParams(window.location.search)
    if(searchParams.has('portfolioId')){
        portfolioId = searchParams.get('portfolioId');
        setting.setPortfolio(portfolioId);
        $('#twigPortfolio').val(portfolioId);
    }
    
    // Get date from URL parameter.
    if(searchParams.has('date')){
        date = searchParams.get('date');
        setting.setDate(date);
        datepicker.setDate(Date.parse(date))
    }
    
    // TWIG tree may failed to be created.
    twig = new TWIG();
    if(!twig.createTree(setting)){
		twig = undefined;
		return;
	}

    if(DEBUG_MODE){
        console.log('TWIG Layout Tree');
        twig.root.printTree();
    }
    

    // Get event list
    var singleTimeEventList = getSingleTimeEventList(setting);
    var recurringEventList = getRecurringEventList(setting);

    if(singleTimeEventList == undefined){
        return;
    }
  
    // Iterate all nodes via DFS, but only get the assets.
    var iterator = new TwigNodeIterator(twig.root);
    
	while(iterator.hasNext()){
		var { value, done } = iterator.next();
		var widget = value.node.widget;

		if(widget.type == "IMAGE"){
            readImage(assetCollection, widget.assetId);
		}
    }

    // Iterate all days over a week.
    
    //eventList = {... singleTimeEventList, ... recurringEventList};

    //var eventMap = {};
    for(var i=0; i<7; i++){
       eventMap[i] = [];
    }
    
    //console.log(eventList)
    
    
    $.each(singleTimeEventList, function(dayOfWeek, events) {
        for (var i=0; i<events.length; i++){
            eventMap[dayOfWeek].push(events[i])
        }
    });

    $.each(recurringEventList, function(dayOfWeek, events) {
        for (var i=0; i<events.length; i++){
            eventMap[dayOfWeek].push(events[i])
        }
    });

    $.each(eventMap, function(index, events) {
        
        // For each day, get asset of all events which has a TWIG component.
        for (var i = 0; i < events.length; i++) {
            if (events[i].assetId != null) {
                readImage(assetCollection, events[i].assetId);
            }
        }
    });
    //processEvents(singleTimeEventList);
    //processEvents(recurringEventList);

    //console.log(eventMap)
    //console.log(recurringEventList)
    
    
}

function setup(){
	
	if(twig == undefined && singleTimeEventList == undefined){
		return;
	}

    //$('#etwigSettingBox').modal('show');
    //frameRate(10);
	    
    // The main canvas.
    var mainCanvas = twig.root.widget;
    //var shortSide = Math.min(mainCanvas.width, mainCanvas.height);
    createCanvas(mainCanvas.width, mainCanvas.height);

    //alert($(window).width())
    //alert($(window).height())
    //alert(windowWidth / mainCanvas.width)

    $('main').css('transform', 'scale(' + ($(window).width() / mainCanvas.width) + ')');
    $(window).resize(function() {
        $('main').css('transform', 'scale(' + ($(window).width() / mainCanvas.width) + ')');
    });

    //$(window).on('resize orientationchange', function() {
    //    $('main').css('transform', 'scale(' + ($(window).width() / mainCanvas.width) + ')');
    //});

    
    var mainCanvas = twig.root.widget;
    var shortSide = Math.min(mainCanvas.width, mainCanvas.height);


    //scale(windowWid:th / mainCanvas.width);
    //var zoomLevel = windowWidth / mainCanvas.width;
    //console.log(zoomLevel)

    //translate(-windowWidth*(1-zoomLevel), 0);
    //$('main').css('transform', 'scale(' + (windowWidth / mainCanvas.width) + ')');
   
    // Track the number of push and pop
    var pushNum = 0;
    var popNum = 0;

    // Track the depth of last iteration!
    var lastDepth = 0;

    // Iterate the tree again to draw it. 
    const iterator = new TwigNodeIterator(twig.root);
    while (iterator.hasNext()) {
        const { value, done } = iterator.next();

        // The current depth
        const { node, depth } = value;

        /**
		 *  Get the parent node
		 */

        // The path from root to current node.
        var path = twig.findPath(value.node);

        // The path contains at least 2 nodes, the penultimate node is parent, or the parent is itself.
        var parent = (path.length > 1) ? path[path.length-2] : path[0];

        // Depth change value, 1 means deeper, 0 means same depth, -1 means shallower.
        var depthChange = depth - lastDepth;

        // Depth changes
        if(depthChange != 0){

            // Go deeper
            if(depthChange > 0){
                translate(parent.widget.posX, parent.widget.posY);
            }

            // Go shallower, canvas depth -- first.
            else{
                pop();
                popNum++;
            }

            // Canvas depth always ++
            push();
            pushNum++;
        }

        // Current widget
        var widget = value.node.widget

        switch (widget.type){
            case "TEMPLATE":

                if(DEBUG_MODE){
                    noFill();   strokeWeight(5);    stroke(255, 0, 0);
                    rect(widget.posX, widget.posY, widget.width, widget.height);
                }                
                break;

            case "IMAGE":
				var originalImg = assetCollection.get(widget.assetId);
				
				// Calculate the new height. (Aspect ratio not changes)
                var newHeight = originalImg.height * (widget.width / originalImg.width);
				image(originalImg, widget.posX, widget.posY, widget.width, newHeight)
				break;

            case "TEXT":
                console.log(widget)
                textSize(shortSide * widget.size * 0.001);    fill(widget.color);
                noStroke(); textStyle(NORMAL);

                // Special consideration 1: Current week (if applicable)
                var textContent = widget.content;
                var currentWeek = $('#calculatedWeek').text();
                if(currentWeek != null && currentWeek.length > 0){
                    textContent = textContent.replace('[WEEK]', currentWeek);
                }

                // Special consideration 2: Dates
                textContent = textContent.replace('[DAY_MON]', new Date(setting.date).monday().addDays(-7).toString('d MMM'));
                textContent = textContent.replace('[DAY_TUE]', new Date(setting.date).monday().addDays(-6).toString('d MMM'));
                textContent = textContent.replace('[DAY_WED]', new Date(setting.date).monday().addDays(-5).toString('d MMM'));
                textContent = textContent.replace('[DAY_THU]', new Date(setting.date).monday().addDays(-4).toString('d MMM'));
                textContent = textContent.replace('[DAY_FRI]', new Date(setting.date).monday().addDays(-3).toString('d MMM'));
                textContent = textContent.replace('[DAY_SAT]', new Date(setting.date).monday().addDays(-2).toString('d MMM'));
                textContent = textContent.replace('[DAY_SUN]', new Date(setting.date).monday().addDays(-1).toString('d MMM'));
                
                //console.log()
                //console.log(new Date(setting.date).tuesday().toString('d MMM'))
                //console.log(new Date(setting.date).wednesday().toString('d MMM'))

                //console.log(getWeekStartsMonday(setting.date))

                text(textContent, widget.posX, widget.posY)
                break;

            case "EVENT_TABLES":

                // Show the border in debug mode.
                if(DEBUG_MODE){
                    noFill();   strokeWeight(5);    stroke(0, 0, 255);
                    rect(widget.posX, widget.posY, widget.width, widget.height);
                }                
                

                // Match event table (template) and list (content)
                var ev = eventMap[widget.dayOfWeek];  
                var hours = (widget.dayOfWeek == 0 || widget.dayOfWeek == 6) ? WEEKEND_HOURS : WEEKDAY_HOURS;
                
                if(DEBUG_MODE){
                    console.log(ev);
                }

                // Execute TWIG Arrangement Algorithm.
                var taa = new TAA(ev, widget, hours);
                console.log(widget)
                var arrangements = taa.exec();
                var slotHeight = taa.getSlotHeight();

                //console.log(arrangements)

                // Place graphics to the allocated area.
                for (const [key, value] of arrangements) {

                    if(value != null){

                        // Find current event
                        var idx = ev.findIndex(object => object.eventId == value.eventId);
                        if(idx < 0){
                            continue;
                        }
                        var current = ev[idx];

                        // Display event time
                        textSize(shortSide * 0.012); fill(0);    noStroke();    textStyle(NORMAL);
                        if(current.allDayEvent){
                            text('All day', value.posX, value.posY);
                            //console.log(value.posY)
                        }
                        
                        // Start and end time
                        else{
                            var endTime = Date.parse(current.date + ' ' + current.time).addMinutes(current.duration).toString('HH:mm');
                            text(current.time + '-' + endTime, value.posX, value.posY);
                        }
                        
                        
                        if(DEBUG_MODE){
                            fill("#004AAD");    noStroke();
                            rect(value.posX, value.posY, widget.width, slotHeight);
                        }

                        else{
                            var originalImg = assetCollection.get(current.assetId);

                            // The event has a TWIG component.
                            if(originalImg != undefined && originalImg != null){
                                var newHeight = originalImg.height * (widget.width / originalImg.width);
                                image(originalImg, value.posX, value.posY, widget.width, newHeight)
                            }

                            // Otherwise, just show event name with portfolio color.
                            else{
                                textSize(shortSide * 0.016);    fill('#' + current.portfolioColor);
                                noStroke(); textStyle(NORMAL);
                                text(current.name, value.posX, value.posY + shortSide * 0.008, widget.width);
                            }

                        }

                    }

                }

                break;
        }

        lastDepth = depth;
    }

    //console.log(pushNum, popNum);

    // Pop all remaining transformations 
    for(var i=0; i<pushNum-popNum; i++){
        pop();
    }

    
    //$('#etwigSettingBox').modal('hide');
    //$('.modal').hide();
    //$('#etwigSettingBox').hide();
    //$('.modal').hide();

    // Stop the loading animation.
    $('#logo').removeClass('beating-logo').addClass('shrinking-logo');

}


function draw(){

}

//function mouseClicked(fxn){
//	console.log(mouseX, mouseY);
//}

function readImage(assets, assetId){
    var assetUrl = "/assets/getPublicAsset?assetId=" + assetId;
			
    // Key: assetId, Value: content
    assets.set(
        assetId, 
        loadImage(
            assetUrl,       // The image location
            function(){},   // Success callback, do nothing here
            function(){     // Failure callback.
                 warningPopup("Failed to load the following resource", assetUrl);
            }
        )
    );
}


/**
 * Get the event list based on the portfolio and date (stored in TwigSettings object).
 * @param {TwigSettings} setting 
 * @returns The event list object.
 */

function getSingleTimeEventList(setting){
    var eventList = undefined;

    $.ajax({ 
		type: 'GET', 
    	url: '/api/public/getTwigEventsForSingleTimeEvents', 
    	data: { 
			portfolioId: setting.getPortfolio(),
            date: setting.getDate()
		}, 
    	async: false,

		success: function(json) {
			eventList = json;
        },
        
    	error: function(err) {   		
			dangerPopup("Failed to get event information due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});

    return eventList;
}

function getRecurringEventList(setting){

    // Initialize the event map: Key is day of week and value is an empty array.
    var eventMap = {};
    for(var i=0; i<7; i++){
       eventMap[i] = [];
    }

    // The boundary of this week.
    var firstDay = Date.parse(setting.getDate()).monday().addDays(-7);
	var lastDay = Date.parse(setting.getDate()).monday();

	$.ajax({ 
		type: 'GET', 
    	url: '/api/public/getTwigEventsForRecurringEvents',
    	async: false,
    	data: { 
			portfolioId: setting.getPortfolio()
		}, 
    	dataType: 'json',
		success: function(json) {
			
			// Iterate all dates.
			jQuery.each(json, function(id, value) {			
								
				var rRule = new ETwig.RRuleFromStr(value.rrule);
				var rule = rRule.getRuleObj();
								
				// Failed to parse rRule, skip it.
				if(rule == undefined || rule == null){
					dangerPopup("Failed to parse Recurrence Rule.", value.rrule + " is not a valid iCalendar RFC 5545 Recurrence Rule.");
					return;
				}

                //console.log(value);
				
				// Get and iterate all occurrences in this week.
    			var occurrence = rRule.getOccurrenceBetween(firstDay, lastDay);
    			for(var i=0; i< occurrence.length; i++){
					
					// Get start and end time for each event.
					var occurrenceDate = new Date(occurrence[i].getTime() + occurrence[i].getTimezoneOffset() * 60000);

					if(value.excludedDates != null && value.excludedDates.includes(occurrenceDate.toString('yyyy-MM-dd'))){
						//console.log("1111111");
                        continue;
						
					}
					
                    //console.log(value.time)

					var eventStartDateTime = combineDateAndTime(occurrenceDate, value.time + ':00');
					//var eventEndDateTime = combineDateAndTime(occurrenceDate, value.eventTime).addMinutes(value.duration);
					
					// The event object
					var eventObj = {
                        eventId: value.eventId,
						graphicsId: value.graphicsId,
                        assetId: value.assetId,
						date: eventStartDateTime.toString('yyyy-MM-dd'),
                        time: eventStartDateTime.toString('HH:mm'),
                        duration: value.duration,
                        allDayEvent: value.allDayEvent
						//end: eventEndDateTime.toString('yyyy-MM-dd HH:mm'),
						//title: {html: `<span class="font-italic bold-text">${value.name}</span>`},
						//color: "#" + value.portfolioColor,
						
					}; 	

                    console.log(eventObj);

                    // Save event based on date of week.
                    eventMap[eventStartDateTime.getDay()].push(eventObj);
				}
			})
        },
        
        // Popup error info when it happens
    	error: function(err) {   		
			dangerPopup("Failed to get recurrent events due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
	return eventMap;
}

/**
 * Create a datepicker for selecting date.
 * @returns The datepicker object.
 */

function createDatePicker(){
    var datepicker = new tui.DatePicker("#weekWrapper", {
        date: Date.today(),
        type: 'date',
        input: {
            element: "#twigWeek",
            usageStatistics: false
        }
    });
        
    datepicker.on('change', () => {
        getWeekByDate(datepicker.getDate().toString("yyyy-MM-dd"));
    });
    
    return datepicker;
}

/**
 * Get the week name by a given date.
 * @param {string} date 
 */

function getWeekByDate(date){
	$.ajax({ 
		type: 'GET', 
    	url: '/api/public/getWeekByDate', 
    	data: { 
            date: date 
        }, 

		success: function(json) {
            $('#calculatedWeek').text((json == null || json.length == 0) ? 'N/A' : json.name);
        },

    	error: function(err) {   		
			dangerPopup("Failed to get week due to a HTTP " + err.status + " error.", err.responseJSON.exception);
		}
	});
}

function applyChanges(){
    window.location.href = '/twig?portfolioId=' + $('#twigPortfolio').val() + '&date=' + $('#twigWeek').val();
}

function copyLink(url){
    navigator.clipboard.writeText(url).then(function() {
        successPopup('URL copied.');
    }).catch(function(error) {
        dangerPopup('Error copying text: ', error);
    });
}

function downloadImg(){
    //console.log($('#imgFormat').val())
    saveCanvas('TWIG', $('#imgFormat').val());
}