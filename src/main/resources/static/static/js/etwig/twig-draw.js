
var img;
var twig = undefined;
var assetCollection = new Map();

// Page orientation: true landscape, false portrait.
var pageOrientation = true;

// Is the setting panel opened
var settingOpened = false;

function preload(){

    // Browser check.
    if(typeof window.chrome !== "object"){
        infoPopup("For the best experience, Chromium-based browsers are recommended", "However, your web browser is " + getBrowserName(navigator.userAgent));
    }
    
    pageOrientation = windowWidth > windowHeight;
   
    
   
    var setting = new TwigSettings();
    setting.setPortfolio(1);

    // Don't use var !!!!!!!
    twig = new TWIG();
    
    // The tree may failed to be created.
    if(!twig.createTree(setting)){
		twig = undefined;
		return;
	}
	
	twig.root.printTree()

    // Create the canvas first !!!!!
    //
    
    //return;

  

    // Iterate all nodes via DFS, but only get the assets.
    var iterator = new TwigNodeIterator(twig.root);
    
	while(iterator.hasNext()){
		var { value, done } = iterator.next();
		var widget = value.node.widget;

		if(widget.type == "IMAGE"){
			var assetUrl = "/assets/getPublicAsset?assetId=" + widget.assetId;
			
			// Key: assetId, Value: content
			assetCollection.set(
				widget.assetId, 
				loadImage(
					assetUrl,       // The image location
					function(){},   // Success callback, do nothing here
					function(){     // Failure callback.
						 warningPopup("Failed to load the following resource", assetUrl);
					}
				)
			);
		}
    }

    
}

function setup(){
	
	if(twig == undefined){
		return;
	}
	
    frameRate(10);
    
    var mainCanvas = twig.root.widget;
    createCanvas(mainCanvas.width, mainCanvas.height);
   // image(img, 0, 0);
    //background(255, 0, 0)
    
    console.log(assetCollection)

    $('#logo').removeClass('beating-logo').addClass('shrinking-logo');
    //$('#logo-container').hide();
}

function draw() {

	if(twig == undefined){
		return;
	}
   
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

        /*
        if(depthChange > 0){
            translate(parent.widget.posX, parent.widget.posY);
            push();
            pushNum++;
        }else if(depthChange < 0){
            pop();
            push();
            popNum++;
            pushNum++;
        }
        */

        // Current widget
        var widget = value.node.widget

       // console.log(depth - lastDepth, widget.type, parent.widget)

       //console.log(0)
        switch (widget.type){
   //         case "TEMPLATE":
   //             //noFill();   strokeWeight(5);    stroke(0);
    //            //rect(widget.posX, widget.posY, widget.width, widget.height);
    //            break;

            case "IMAGE":
				var originalImg = assetCollection.get(widget.assetId);
				
				// Calculate the new height. (Aspect ratio not changes)
                var newHeight = originalImg.height * (widget.width / originalImg.width);
				image(originalImg, widget.posX, widget.posY, widget.width, newHeight)
				break;

            case "EVENT_TABLES":
            	fill(255, 0, 0);    noStroke();
                //rect(widget.posX, widget.posY, widget.width, widget.height);

                var ev = [
                    {eventId:1, time:'09:00', duration:60, allDayEvent:false},
                    {eventId:2, time:'10:00', duration:70, allDayEvent:false},
                    {eventId:3, time:'11:00', duration:120, allDayEvent:false},
                    {eventId:5, time:null, duration:null, allDayEvent:true},
                    {eventId:6, time:'22:30', duration:60, allDayEvent:false},
                    {eventId:7, time:'13:00', duration:60, allDayEvent:false},
                    {eventId:8, time:'17:20', duration:60, allDayEvent:false},
                    {eventId:9, time:'19:20', duration:60, allDayEvent:false},
                
                ]
                
                // **Weekday** events, N=13 (9:00 - 21:00)
                const WEEKDAY_HOURS = [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21];
                
                // **Weekend** events, N=3 (morning 9:00-12:00, afternoon 13:00-18:00, evening 19:00-21:00),
                const WEEKEND_HOURS = [9, 15, 21];
                
                //var eventTableMonday = new TwigNode();
                //var m = new EventTableWidget(); m.setValues(100, 120, 220, 650, true);
                
                
                var taa = new TAA(ev, widget, null, WEEKDAY_HOURS);
                var b = taa.exec();
                //console.log(b)

                for (const [key, value] of b) {
                    //console.log();

                    if(value != null){
                        rect(value.posX, value.posY, 100, 20);

                    }

                }

                
                
                //var dayStart = constrain(widget.dayStart, 0, 11)
               // var dayEnd = constrain(widget.dayEnd, 12, 23)
                
                // Num of (end-start) is end-start+1, there has also a top slot and a bottom slot respectively. 
                //var timeSlotNum = dayEnd - dayStart + 3;
                
                //var 
                
                //console.log(widget.dayStart, widget.dayEnd)
               // console.log(widget.posX, widget.posY, widget.width, widget.width);
           //     assetCollection.set(widget.assetId, loadImage("/assets/getPublicAsset?assetId=" + widget.assetId));
                //p=( loadImage("/assets/getPublicAsset?assetId=" + widget.assetId))
                break;
        }

        lastDepth = depth;
    }

    //console.log(pushNum, popNum);

    // Pop all remaining transformations 
    for(var i=0; i<pushNum-popNum; i++){
        pop();
    }

    if(settingOpened){
        settingMenu();
    }
    

}

function mouseClicked(fxn){
	console.log(mouseX, mouseY);
}

function settingMenu(){

    var mainCanvas = twig.root.widget;

    push();

    // The bottom area (one-third of the canvas height) is the setting panel.
    translate(0, mainCanvas.height * 0.667);
    fill(255);  noStroke();
    rect(0, 0, mainCanvas.width, mainCanvas.height * 0.333);

    textSize(16);
    text('🌈', 0, 100);


    pop();
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