function setup() {

    var setting = new TwigSettings();
    //setting.setPortfolio(1);

    // Don't use var !!!!!!!
    twig = new TWIG();
    twig.createTree(setting);


    
    twig.root.printTree();
    return;
    console.log(JSON.stringify(twig.serialize(), null, 2))

    background = twig.root.widget;
    //console.log(background)

    createCanvas(background.width, background.height);

    // Track the depth of last iteration!
    var lastDepth = 0;

    const iterator = new TwigNodeIterator(twig.root);
    
    while (iterator.hasNext()) {
        const { value, done } = iterator.next();

        // The current depth
        const { node, depth } = value;

        /**
         * Get the parent node
        */

        // The path from root to current node.
        var path = twig.findPath(value.node);

        // The path contains at least 2 nodes, the penultimate node is parent, or the parent is itself.
        var parent = (path.length > 1) ? path[path.length-2] : path[0];

        // Depth change value, 1 means deeper, 0 means same depth, -1 means shallower.
        var depthChange = depth - lastDepth;

        if(depthChange > 0){
            console.log(parent.widget.posX, parent.widget.posY)
            translate(parent.widget.posX, parent.widget.posY);
            push();
        }else if(depthChange < 0){
            pop();
            push();
        }

        // Current widget
        var widget = value.node.widget;

       // console.log(depth - lastDepth, widget.type, parent.widget)

        switch (widget.type){
            case "TEMPLATE":
                noFill();   strokeWeight(5);    stroke(0);
                rect(widget.posX, widget.posY, widget.width, widget.height);
                break;

            case "IMAGE":
            case "EVENT_TABLES":
                fill(random(255), random(255), random(255));    noStroke();
                rect(widget.posX, widget.posY, widget.width, widget.width);
                console.log(widget.posX, widget.posY, widget.width, widget.width);
                break;
        }

        lastDepth = depth;
    }

    /*
    while (iterator.hasNext()) {
        const { value, done } = iterator.next();
        const { node, depth } = value;

        console.log(value)
        
        

        switch (widget.type){
            case "TEMPLATE":

                console.log(widget)
                fill(random(255), random(255), random(255));

                //console.log(widget.posX, widget.posY, widget.width, widget.height);

                //rect(0,0,100,100)
                rect(widget.posX, widget.posY, widget.width, widget.height);
                break;
        }
            

    }

    */
}

function draw() {

 

    //background(220);
}