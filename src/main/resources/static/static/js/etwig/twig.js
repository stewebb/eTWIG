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
        return this.#serializeHelper(this.root);
    }

    #serializeHelper(node) {
      
        var jsonObject = {};
        jsonObject.widget = node.widget;
    
        // Recursively serialize all children
        if (node.children.length > 0) {
            jsonObject.children = node.children.map(child => this.#serializeHelper(child));
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
            case 'TEXT':
                widget = new Text();
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

    /**
     * Find the path from root node to target node.
     * @param {*} target The target node
     * @returns An array of all passed nodes
     */

    findPath(target){
        return this.#findPathHelper(this.root, target);
    }

    #findPathHelper(root, target, path = []) {
        if (root === target) {
            return [...path, root];
        }
    
        for (let child of root.children) {
            let newPath = this.#findPathHelper(child, target, [...path, root]);
            if (newPath) {
                return newPath;
            }
        }
    
        return null;
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
     * Iterator protocol implementation
     * @returns 
     */

    [Symbol.iterator]() {
        return new TwigNodeIterator(this);
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
 * Use Iterator design pattern to traverse the tree.
 */

class TwigNodeIterator {
    constructor(root) {
        this.stack = [{ node: root, depth: 0 }];
    }

    /**
     * Get the next value along with the current depth.
     * @returns The next value and the current depth.
     */

    next() {
        if (this.stack.length === 0) {
            return { done: true };
        }

        const { node, depth } = this.stack.pop();

        // Push children to stack in reverse order for depth-first traversal
        for (let i = node.children.length - 1; i >= 0; i--) {
            this.stack.push({ node: node.children[i], depth: depth + 1 });
        }

        return { value: { node, depth }, done: false };
    }

    /**
     * Check if there are more nodes to visit.
     * @returns True if there is at least 1 node to visit, False otherwise.
     */

    hasNext() {
        return this.stack.length > 0;
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
 * - **posX**: The X coordinate of the starting point of a template.
 * - **posY**: The Y coordinate of the starting point of a template.
 * - **width**: The width of the image. 
 * - **assetId**: The identification number of the asset.
 * 
 * Please note that the height of the image is depends on the aspect ratio of the original image. (i.e., auto-inferred).
 */

class Image {

    constructor(){
        this.type = "IMAGE";
    }

    setValues(posX, posY, width, assetId) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.assetId = assetId;
    }

    fromJson(jsonObject){
        this.posX = jsonObject.posX;
        this.posY = jsonObject.posY;
        this.width = jsonObject.width;  
        this.assetId = jsonObject.assetId;
    }

    toString(){
        return `Image(${this.posX}, ${this.posY}, ${this.width}, ${this.assetId})`; 
    }

}

/**
 * **The Text object** is a normal text that displays on the screen, which contains the following properties:
 * 
 * - **posX**: The X coordinate of the text.
 * - **posY**: The Y coordinate of the text.
 * - **content**: The content of the text.
 * - **color**: The color of the text in hexadecimal form. (e.g., #FF0000 means red).
 * - **size**: The font size.
 */

class Text {

    constructor(){
        this.type = "TEXT";
    }

    setValues(posX, posY, content, color, size) {
        this.posX = posX;
        this.posY = posY;
        this.content = content;
        this.color = color;
        this.size = size;
    }

    fromJson(jsonObject){
        this.posX = jsonObject.posX;
        this.posY = jsonObject.posY;
        this.content = jsonObject.content;
        this.color = jsonObject.color;
        this.size = jsonObject.size;
    }

    toString(){
        return `Template(${this.posX}, ${this.posY}, ${this.content}, ${this.color}, ${this.size})`; 
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

    fromJson(jsonObject){
        this.posX = jsonObject.posX;
        this.posY = jsonObject.posY;
        this.width = jsonObject.width;
        this.height = jsonObject.height;
        this.dayStart = jsonObject.dayStart;
        this.dayEnd = jsonObject.dayEnd;
    }

    toString(){
        return `EventTable(${this.posX}, ${this.posY}, ${this.width}, ${this.height}, ${this.dayStart}, ${this.dayEnd})`; 
    }
}


function initializeTwig(){

    var twig = new TWIG(true);

        // Place a 1920x1080 canvas, depth=0
        var t = new Template(); t.setValues(0, 0, 1920, 1080);  twig.root.setWidget(t);

        // A 1920x1080 background, depth=0, child=0
        var background = new TwigNode();
        var b = new Image();    b.setValues(0, 0, 1920, 1);   background.setWidget(b);
    
        // The title area
        var title = new TwigNode();
        var t = new Template(); t.setValues(0, 0, 1920, 180);  title.setWidget(t);

            // Logo
            var logo = new TwigNode();
            var l = new Image();    l.setValues(100, 50, 150, 1);   logo.setWidget(l); 

            title.addChild(logo);


        // The events area
        var events = new TwigNode();
        var e = new Template(); e.setValues(0, 180, 1920, 900); events.setWidget(e);

            // Event table (Monday)
            var eventTableMonday = new TwigNode();
            var m = new EventTable(); m.setValues(100, 100, 100, 100, 0, 24);  eventTableMonday.setWidget(m);

        events.addChild(eventTableMonday);

    // The events area
    twig.root.addChild(background);
    twig.root.addChild(title);
    twig.root.addChild(events);

    return twig;

}
