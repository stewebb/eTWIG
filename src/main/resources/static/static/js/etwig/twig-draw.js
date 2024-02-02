
var img;
var twig = undefined;
var assetCollection = new Map();

//p5.disableFriendlyErrors = false; // disables FES

function preload(){
    
    //createCanvas(400, 400);
    
   
    var setting = new TwigSettings();
    //setting.setPortfolio(1);

    // Don't use var !!!!!!!
    twig = new TWIG();
    
    // The tree may failed to be created,.
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
					assetUrl,					// The image location
					function(){},				// Success callback, do nothing here
					function(){				// Failure callback.
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
}

function draw() {

	if(twig == undefined){
		return;
	}
    //console.log(1);
  //  console.log(twig)

    // Track the depth of last iteration!
    var lastDepth = 0;

    // Iterate the tree again to draw it. 
    const iterator = new TwigNodeIterator(twig.root);
    while (iterator.hasNext()) {
        const { value, done } = iterator.next();

        // The current depth
        const { node, depth } = value;

       
        // * Get the parent node
        

        // The path from root to current node.
        var path = twig.findPath(value.node);

        // The path contains at least 2 nodes, the penultimate node is parent, or the parent is itself.
        var parent = (path.length > 1) ? path[path.length-2] : path[0];

        // Depth change value, 1 means deeper, 0 means same depth, -1 means shallower.
        var depthChange = depth - lastDepth;

        if(depthChange > 0){
            //console.log(parent.widget.posX, parent.widget.posY)
            translate(parent.widget.posX, parent.widget.posY);
            push();
        }else if(depthChange < 0){
            pop();
            push();
        }

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
                var newHeight = originalImg.height * (widget.width / originalImg.width);
				image(originalImg, widget.posX, widget.posY, widget.width, newHeight)
				break;
            case "EVENT_TABLES":
            	fill(255, 0, 0);    noStroke();
                rect(widget.posX, widget.posY, widget.width, widget.height);
               // console.log(widget.posX, widget.posY, widget.width, widget.width);
           //     assetCollection.set(widget.assetId, loadImage("/assets/getPublicAsset?assetId=" + widget.assetId));
                //p=( loadImage("/assets/getPublicAsset?assetId=" + widget.assetId))
                break;
        }

        lastDepth = depth;
   }

}

function mouseClicked(fxn){
	console.log(mouseX, mouseY);
}