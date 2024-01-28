

class TwigNode{
    constructor() {
        this.children = [];
        this.widget = null;
    }

    addChild(childNode){
        this.children.push(childNode);
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

    printTree(indent = 0) {
        console.log(' '.repeat(indent) + this.widget);
        this.children.forEach(child => child.printTree(indent + 2));
    }
    
}

class Template {

    /**
     * **The Template object** is a group of widgets with a rectangle area. 
     * **The widget of the root node should be a template.** It has the following properties:
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
}

class Image {

    constructor(assetId, posX, posY, width, originalAspectRatio) {
        this.assetId = assetId;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.originalAspectRatio = originalAspectRatio;
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
}

var root = new TwigNode();
root.setWidget(new Template(1,1,11,11));

var background = new TwigNode();
background.setWidget(new Image(1,2,3));
//var template2 = new TwigNode();

root.addChild(background);
//root.addChild(template2);
//root.printTree();

console.log(root)