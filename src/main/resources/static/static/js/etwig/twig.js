

class TwigNode{

    constructor() {
        this.children = [];
        this.data = null;
    }

    addChild(childNode){
        this.children.push(childNode);
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
        console.log(' '.repeat(indent) + this.data);
        this.children.forEach(child => child.printTree(indent + 2));
    }
    
}


class Template {
    #posX; // Private field for the X coordinate
    #posY; // Private field for the Y coordinate
    #width; // Private field for the width
    #height; // Private field for the height

    constructor(posX, posY, width, height) {
        this.#posX = posX;
        this.#posY = posY;
        this.#width = width;
        this.#height = height;
    }

    // Getter methods
    getPosX() {
        return this.#posX;
    }

    getPosY() {
        return this.#posY;
    }

    getWidth() {
        return this.#width;
    }

    getHeight() {
        return this.#height;
    }

    // Setter methods
    setPosX(value) {
        this.#posX = value;
    }

    setPosY(value) {
        this.#posY = value;
    }

    setWidth(value) {
        this.#width = value;
    }

    setHeight(value) {
        this.#height = value;
    }

    // Method to display the properties of the template
    displayInfo() {
        console.log(`Template Position: (${this.#posX}, ${this.#posY}), Width: ${this.#width}, Height: ${this.#height}`);
    }
}

class Image {
    #assetId; // Private field for the asset identification number
    #posX;    // Private field for the X coordinate
    #posY;    // Private field for the Y coordinate
    #width;   // Private field for the width of the image
    #originalAspectRatio; // Private field for the aspect ratio of the original image

    constructor(assetId, posX, posY, width, originalAspectRatio) {
        this.#assetId = assetId;
        this.#posX = posX;
        this.#posY = posY;
        this.#width = width;
        this.#originalAspectRatio = originalAspectRatio;
    }

    // Getter methods
    getAssetId() {
        return this.#assetId;
    }

    getPosX() {
        return this.#posX;
    }

    getPosY() {
        return this.#posY;
    }

    getWidth() {
        return this.#width;
    }

    // The height is auto-inferred based on the aspect ratio
    getHeight() {
        return this.#width / this.#originalAspectRatio;
    }

    // Setter methods
    setAssetId(value) {
        this.#assetId = value;
    }

    setPosX(value) {
        this.#posX = value;
    }

    setPosY(value) {
        this.#posY = value;
    }

    setWidth(value) {
        this.#width = value;
    }

    // Method to display the properties of the image
    displayInfo() {
        console.log(`Image Asset ID: ${this.#assetId}, Position: (${this.#posX}, ${this.#posY}), Width: ${this.#width}, Height: ${this.getHeight()}`);
    }
}

class EventTables {
    #posX;     // Private field for the X coordinate of the starting point
    #posY;     // Private field for the Y coordinate of the starting point
    #width;    // Private field for the width of the table
    #height;   // Private field for the height of the table
    #dayStart; // Private field for the starting time of a day
    #dayEnd;   // Private field for the ending time of a day

    constructor(posX, posY, width, height, dayStart, dayEnd) {
        this.#posX = posX;
        this.#posY = posY;
        this.#width = width;
        this.#height = height;
        this.#dayStart = dayStart;
        this.#dayEnd = dayEnd;
    }

    // Getter methods
    getPosX() {
        return this.#posX;
    }

    getPosY() {
        return this.#posY;
    }

    getWidth() {
        return this.#width;
    }

    getHeight() {
        return this.#height;
    }

    getDayStart() {
        return this.#dayStart;
    }

    getDayEnd() {
        return this.#dayEnd;
    }

    // Setter methods
    setPosX(value) {
        this.#posX = value;
    }

    setPosY(value) {
        this.#posY = value;
    }

    setWidth(value) {
        this.#width = value;
    }

    setHeight(value) {
        this.#height = value;
    }

    setDayStart(value) {
        this.#dayStart = value;
    }

    setDayEnd(value) {
        this.#dayEnd = value;
    }

    // Method to display the properties of the event table
    displayInfo() {
        console.log(`Event Table Position: (${this.#posX}, ${this.#posY}), Width: ${this.#width}, Height: ${this.#height}, Day Start: ${this.#dayStart}, Day End: ${this.#dayEnd}`);
    }
}

var root = new TwigNode();
var template1 = new TwigNode();
var template2 = new TwigNode();

root.addChild(template1);
root.addChild(template2);




//console.log(JSON.stringify(root));