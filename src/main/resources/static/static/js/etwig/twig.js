class TWIG{

    /**
     * Initialize the root node by default?
     * @param {boolean} init True an instance of root TwigNode will be initialized.
     * False the instance will not be initialized. (Mostly followed by the deserialization)
     */

    constructor(init){
        this.root = init ? new TwigNode() : null;
    }

    /**
     * Serialize the TWIG Tree.  
     * @returns The json format of the tree.
     */

    serialize() {   
        var jsonObject = {};
        jsonObject.widget = this.root.widget;
    
        // Recursively serialize all children
        if (this.root.children.length > 0) {
            jsonObject.children = this.root.children.map(child => serializeTwigNode(child));
        }
    
        return jsonObject;
    }

    /**
     * Deserialize the TWIG Tree, read an json object, and convert to a TWIG tree.
     * @param {object} jsonObject 
     */

    deserialize(jsonObject){
        this.root = this.#deserializeHelper(jsonObject);
    }

    #deserializeHelper(jsonObject) {

        //console.log(jsonObject)

        var widget = null;
        var widgetObj = jsonObject.widget;
        let node = new TwigNode();

        switch (widgetObj.type) {
            case 'IMAGE':
                widget = new Image(widgetObj);
            case 'EVENT_TABLES':
                widget = new EventTable(widgetObj);
            case 'TEMPLATE':
                widget = new Template();
                widget.fromJson(widgetObj);
        }

        node.setWidget(widget);
    
        // Recursively deserialize all children
        if (jsonObject.children && jsonObject.children.length > 0) {
            jsonObject.children.forEach(childJson => {
                node.children.push(this.#deserializeHelper(childJson));
            });
        }
    
        return node;
    }

}

/**
 * Each node contains two fields: **children** and **widget**. 
 * Children is a **list** of the Node objects of other nodes, 
 * while widget is a object of Widget classes (one of Image, Text, Table and Template). 
 * 
 * The **layer** of the widgets are based on the height of the tree, 
 * the node of top-layer widgets always have a higher height than the node of bottom-layer widgets. 
 * 
 * The **position** of the widgets are depends on not the whole canvas, 
 * but the parent template. (i.e., relative position)
 */

class TwigNode{

    constructor() {
        this.children = [];
        this.widget = null;
    }

    addChild(childNode){

        // Only the node of template widget is allowed to add child.
        if(this.widget instanceof Template){
            this.children.push(childNode);
        }else{
            console.warn("Only the node of template widget is allowed to add child.");
        }
        
    }

    setWidget(widget){
        this.widget = widget;
    }

    /**
     * Whether this node is leaf or not.
     * @returns True if this node is a leaf.
     */

    isLeafNode(){
        return (this.children.length == 0);
    }

    /**
     * Print out the tree structure.
     * @param {int} indent The indent on each depth.
     */

    printTree(indent = 0, depth = 0) {
        console.log(' '.repeat(indent) + `${depth}. ${this.widget.toString()}`);
        this.children.forEach(child => child.printTree(indent + 4, depth + 1));
    }
    
}

/**
 * **The Template object** is a group of widgets with a rectangle area. 
 * **The widget of the root node should be a template.** 
 * It has the following properties:
 * 
 * - **posX**: The X coordinate of the starting point of a template.
 * - **posY**: The Y coordinate of the starting point of a template.
 * - **width**: The width of the template area.
 * - **height**: The height of the template area.
 * */

class Template {

    constructor(){
        this.type = "TEMPLATE";
    }

    setValues(posX, posY, width, height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    fromJson(jsonObject){
        this.posX = jsonObject.posX;
        this.posY = jsonObject.posY;
        this.width = jsonObject.width;
        this.height = jsonObject.height;
    }

    toString(){
        return `Template(${this.posX}, ${this.posY}, ${this.width}, ${this.height})`; 
    }
}

/**
 * **The Image object** is a reference of an asset in the **asset** table in the database. 
 * It is usually a **transparent** PNG image (unless the background, which is not transparent). 
 * The Image object contains the following properties:
 * 
 * - **assetId**: The identification number of the asset.
 * - **posX**: The X coordinate of the starting point of a template.
 * - **posY**: The Y coordinate of the starting point of a template.
 * - **width**: The width of the image. 
 * Please note that the height of the image is depends on the aspect ratio of the original image. (i.e., auto-inferred).
 */

class Image {

    constructor(){
        this.type = "IMAGE";
    }

    setValues(assetId, posX, posY, width) {
        this.assetId = assetId;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
    }

    toString(){
        return `Image(${this.assetId}, ${this.posX}, ${this.posY}, ${this.width})`; 
    }

}

/**
 * **The event tables object** is the collection of events. 
 * They are the core content of each TWIG, and also another kind of calendar.
 * Each event table actually is another "template" but it can only includes the event graphics and not other widgets. 
 * The Event Table object contains the following properties:
 * 
 * - **posX**: The X coordinate of the starting point of the table.
 * - **posY**: The Y coordinate of the starting point of the table.
 * - **width**: The width of the table.
 * - **height**: The height of the the table.
 * - **dayStart**: The start time of a day (e.g., 8 AM).
 * - **dayEnd**: The end time of a day.
 */

class EventTable {

    constructor(){
        this.type = "EVENT_TABLES";
    }

    setValues(posX, posY, width, height, dayStart, dayEnd) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
    }

    toString(){
        return `EventTable(${this.posX}, ${this.posY}, ${this.width}, ${this.height}, ${this.dayStart}, ${this.dayEnd})`; 
    }
}

var twig = new TWIG(true);

var template = new Template();
template.setValues(0, 0, 1920, 1080);

twig.root.setWidget(template);

var a = twig.serialize();

console.log(a)
var twig2 = new TWIG(false);
twig2.deserialize(a);

twig2.root.printTree();


//console.log(twig2)
/*
// A 1920x1080 canvas
var root = new TwigNode();

var template = new Template();
root.setWidget(template);

// A 1920x1080 background
var background = new TwigNode();

var b = new Image();
b.setValues(1, 0, 0, 1920);
background.setWidget(b);

// The title area
var titleArea = new TwigNode();

var title = new Template()
title.setValues(0, 0, 1920, 1080);
titleArea.setWidget(title);

var logo = new TwigNode();
var w = new Image();
w.setValues(2, 0, 0, 240);
logo.setWidget(w);
titleArea.addChild(logo)

root.addChild(background);
root.addChild(titleArea);

root.printTree();

*/