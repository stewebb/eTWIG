

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
        }

        // Display a warning
        else{
            console.warn("Only the node of template widget is allowed to add child.");
        }
        
    }

    setWidget(widget){

        // Template widget, internal node only.
        //if (widget instanceof Template && this.isLeafNode()){
        //    throw new Error("A template widget cannot be added to a leaf node!");
        //}

        //if (!(widget instanceof Template) && !this.isLeafNode()){
        //    throw new Error("A non-template widget cannot be added to an internal node!")
        //}
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

    /**
     * **The Template object** is a group of widgets with a rectangle area. 
     * **The widget of the root node should be a template.** 
     * It has the following properties:
     * @param {int} posX The X coordinate of the starting point of a template.
     * @param {int} posY The Y coordinate of the starting point of a template.
     * @param {int} width The width of the template area.
     * @param {int} height The height of the template area
     */

    constructor(posX, posY, width, height) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    toString(){
        return "Template(" + this.posX + ", " + this.posY + ", " + this.width + ", " + this.height + ")"; 
    }
}

class Image {

    constructor(assetId, posX, posY, width) {
        this.assetId = assetId;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
    }

    toString(){
        return "Image(" + this.assetId + ", " + this.posX + ", " + this.posY + ", " + this.width + ")"; 
    }

}

class EventTables {
    constructor(posX, posY, width, height, dayStart, dayEnd) {
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
}

// A 1920x1080 canvas
var root = new TwigNode();
root.setWidget(new Template(0, 0, 1920, 1080));

// A 1920x1080 background
var background = new TwigNode();
background.setWidget(new Image(1, 0, 0, 1920));

// The title area
var titleArea = new TwigNode();
titleArea.setWidget(new Template(0, 0, 1920, 1080));

var logo = new TwigNode();
logo.setWidget(new Image(2, 0, 0, 240));
titleArea.addChild(logo)

root.addChild(background);
root.addChild(titleArea);

root.printTree();