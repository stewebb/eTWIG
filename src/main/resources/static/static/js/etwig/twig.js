class TWIG{

    constructor(){
        this.root = new TwigNode();
    }

    serialize() {   
        var jsonObject = {};
        jsonObject.widget = this.root.widget;
    
        // Recursively serialize all children
        if (this.root.children.length > 0) {
            jsonObject.children = this.root.children.map(child => serializeTwigNode(child));
        }
    
        return jsonObject;
    }

    deserialize(jsonObject){
        this.root = this.#deserializeHelper(jsonObject);
    }

    #deserializeHelper(jsonObject) {

        //console.log(jsonObject)

        var widget = undefined;
        var widgetObj = jsonObject.widget;

        switch (widgetObj.type) {
            case 'IMAGE':
                widget= new Image(widgetObj);
            case 'EVENT_TABLES':
                widget= new EventTable(widgetObj);
            case 'TEMPLATE':
                widget= new Template();
                widget.fromJson(widgetObj);
            //default:
            //    throw new Error('Unknown widget type');
        }

        //console.log(widget)
        // createWidgetFromJson(jsonObject.widget);    
        let node = new TwigNode();
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

class TwigNode{

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

var twig = new TWIG();

var template = new Template();
template.setValues(0, 0, 1920, 1080);

twig.root.setWidget(template);

var a = twig.serialize();

console.log(a)
var twig2 = new TWIG();
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


function serializeTwigNode(node) {
    if (!node) return null;

    let jsonObject = {};

    // Assuming the widget has a method to convert itself to JSON
    // If not, you'll need to implement this part based on your Widget class structure
    if (node.widget) {
        jsonObject.widget = node.widget;
    }

    if (node.children.length > 0) {
        jsonObject.children = node.children.map(child => serializeTwigNode(child));
    }

    return jsonObject;
}

function createWidgetFromJson(widgetJson) {
    //console.log(widgetJson);
   
}

function deserializeTwigNode(jsonObject) {


    //console.log(jsonObject)

    //if (!jsonObject) return null;

    let widget = createWidgetFromJson(jsonObject.widget);
    //let widget = jsonObject.widget ? createWidgetFromJson(jsonObject.widget) : null;

    let node = new TwigNode();
    node.setWidget(widget)

    if (jsonObject.children && jsonObject.children.length > 0) {
        jsonObject.children.forEach(childJson => {
            node.children.push(deserializeTwigNode(childJson));
        });
    }

    return node;
}

let jsonTree = serializeTwigNode(root);
jsonTree = JSON.stringify(jsonTree, null, 4);
console.log(jsonTree);

let jsonObject = JSON.parse(jsonTree);

let rootNode = deserializeTwigNode(jsonObject);
console.log(JSON.stringify(rootNode, null, 4));


var json = JSON.stringify(root);

function deserializeTree(serializedNode) {
    let node = new TwigNode();
    node.setWidget(serializedNode.widget);

    if (serializedNode.children) {
        serializedNode.children.forEach(child => {
            node.addChild(deserializeTree(child));
        });
    }

    return node;
}

let parsedObject = JSON.parse(json);

let rootNode = deserializeTree(parsedObject);

rootNode.printTree();

*/