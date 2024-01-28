

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
     * @param {*} indent 
     */

    printTree(indent = 0, depth = 0) {
        console.log(' '.repeat(indent) + `${depth}. ${this.widget.toString()}`);
        this.children.forEach(child => child.printTree(indent + 4, depth + 1));
    }
    
}

class Template {

    constructor(){
        this.type = "TEMPLATE";
    }

    /**
     * **The Template object** is a group of widgets with a rectangle area. 
     * **The widget of the root node should be a template.** 
     * It has the following properties:
     * @param {int} posX The X coordinate of the starting point of a template.
     * @param {int} posY The Y coordinate of the starting point of a template.
     * @param {int} width The width of the template area.
     * @param {int} height The height of the template area
     */

    setValues(posX, posY, width, height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    toString(){
        return "Template(" + this.posX + ", " + this.posY + ", " + this.width + ", " + this.height + ")"; 
    }

    //toJson(){
    //    return {type: "TEMPLATE", posX: this.posX, posY: this.posY, width:this.width, height: this.height};
    //}
}

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
        return "Image(" + this.assetId + ", " + this.posX + ", " + this.posY + ", " + this.width + ")"; 
    }

    //toJson(){
    //    return {type: "IMAGE"};
    //}
}

class EventTables {

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
        return `EventTables(${this.posX}, ${this.posY}, ${this.width}, ${this.height}, ${this.dayStart}, ${this.dayEnd})`; 
    }

    //toJson(){
    //    return {type: "EVENT_TABLES"};
    //}
}


// A 1920x1080 canvas
var root = new TwigNode();

var template = new Template();
template.setValues(0, 0, 1920, 1080);
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
    console.log(widgetJson);
    switch (widgetJson.type) {
        case 'IMAGE':
            return new Image(widgetJson);
        case 'EVENT_TABLES':
            return new EventTables(widgetJson);
        case 'TEMPLATE':
            return new Template(widgetJson);
        default:
            throw new Error('Unknown widget type');
    }
}

function deserializeTwigNode(jsonObject) {
    if (!jsonObject) return null;

    let widget = jsonObject.widget ? createWidgetFromJson(jsonObject.widget) : null;
    let node = new TwigNode(widget);

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

//let jsonObject = JSON.parse(jsonTree);

//let rootNode = deserializeTwigNode(jsonObject);
//console.log(rootNode);

/*
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