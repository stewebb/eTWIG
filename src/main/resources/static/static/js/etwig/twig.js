
/**
 * A **TWIG** (abbreviation of *This Week In Griffin*) is a graphic collection and 
 * summarization of Griffin Hall's weekly events.
 */

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
     * Create a TWIG (template) tree from the design from the DB.
     * @param {TwigSettings} setting The current settings.
     * @returns true the tree was created successfully, false otherwise.
     */

    createTree(setting){
        var jsonString = null;
        var jsonObj = undefined;

        // Get TWIG template from the DB and error check.
        $.ajax({ 
            type: 'GET', 
            url: '/api/public/getTwigTemplateByPortfolioAndDate', 
            async: false,
            data: setting.toAjaxParam(), 
            success: function(json){

                // Empty result check
                if(json == undefined || json == null || json.length == 0){
                    dangerPopup("The TWIG template cannot be found by the following parameters", setting.toString());
                    return;
                }

                // Get the template design in string.
                jsonString = json.design;
            },
            error: function(err) {   		
                dangerPopup("Failed to get the TWIG template due to a HTTP " + err.status + " error", err.responseJSON.exception);
            }
        });

        if(jsonString == undefined || jsonString == null || jsonString.length == 0){
            return false;
        }
        
        // Parse and error check
        try{
            jsonObj = JSON.parse(jsonString);
        }catch(error){
            dangerPopup("Failed to parse the TWIG design", error);
        }

        if(jsonObj == undefined || jsonObj == null){
            return false;
        }

        // Deserialize and error check
        try{
            this.deserialize(jsonObj);
            return true;
        }catch(error){
            dangerPopup("Failed to deserialize the TWIG design", error);
            return false;
        }    
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

        // Different widget types
        switch (widgetObj.type) {
            case 'IMAGE':
                widget = new ImageWidget();
                break;
            case 'EVENT_TABLES':
                widget = new EventTableWidget();
                break;
            case 'TEXT':
                widget = new TextWidget();
                break;
            default:
                widget = new TemplateWidget();
                break;
        }
        widget.fromJson(widgetObj);
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

class TwigSettings{

    /**
     * The default settings.
     * Portfolio is any (null, 0, or negative integers);
     * Current date.
     */

    constructor(){
        this.portfolio = -1;
        this.date = Date.today();
    }

    /**
     * Set portfolioId, it should be a positive integer.
     */

    setPortfolio(portfolio){
        this.portfolio = (portfolio != null && portfolio % 1 === 0) ? portfolio : -1;
    }

    setDate(dateStr){
        this.date = Date.parse(dateStr);
    }

    toString(){
        return `TwigSettings(portfolio=${this.portfolio}, date=${this.date.toString("yyyy-MM-dd")})`; 
    }

    /**
     * Return an object that can be accepted in jQuery Ajax.
     */

    toAjaxParam(){
        return {
            portfolioId: this.portfolio,
            date: this.date.toString("yyyy-MM-dd")
        }
    }

    toUrl(){
        return `/twig?portfolioId=${this.portfolio}&date=${this.date.toString("yyyy-MM-dd")}`; 
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
        if(this.widget instanceof TemplateWidget){
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

class TemplateWidget {

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

class ImageWidget {

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

class TextWidget {

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
 * - **timeSlotNum**: The number of time slots.
 */

class EventTableWidget {

    constructor(){
        this.type = "EVENT_TABLES";
    }

    setValues(posX, posY, width, height, timeSlotNum) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.timeSlotNum = timeSlotNum;
    }

    fromJson(jsonObject){
        this.posX = jsonObject.posX;
        this.posY = jsonObject.posY;
        this.width = jsonObject.width;
        this.height = jsonObject.height;
        this.timeSlotNum = jsonObject.timeSlotNum;
    }

    toString(){
        return `EventTable(${this.posX}, ${this.posY}, ${this.width}, ${this.height}, ${this.timeSlotNum})`; 
    }
}

/**
 * **TWIG Arranging Algorithm (TAA)** is an algorithm to arrange events on the TWIG.
 * Input:
 * - A **map** of all events on a certain day. (key: eventId, value: map)
 * - The object of the **event table widget**.
 * - The **range** of acceptable sizes.
 * - 
 * 
 * Output:
 * - A list of the positions of all event graphics.
 */

class TAA{

    constructor(eventMap, eventTable, sizeRange, hours){
        this.eventMap = eventMap;
        this.eventTable = eventTable;
        this.sizeRange = sizeRange;
        this.hours = hours;
    }

    /**
     * A simple helper method to calculate the longest possible duration of an event.
     * @param {String} startTime Event start time in hh:MM
     * @returns The longest duration time.
     */

    #longestDuration(startTime){
        var splitted = startTime.split(':');

        // "+" can convert the string-based int to an int!
        var minutes = (+splitted[0]) * 60 + (+splitted[1]);
        console.log(1440-minutes);

        // Ensure this number is NOT a negative number.
        return Math.max(0, 1440-minutes);
    }

    /**
     * Step 1: Initialize and assign the key of the time slot.
     */

    #initTimeSlot(){

        // Determine the length of the time slot map, which is **N+3**.
        var timeSlot = new Map();

        // **All-day events**
        timeSlot.set(NaN, null);

        // The events **before** and **after** the display starting time
        timeSlot.set(Number.NEGATIVE_INFINITY, null);
        timeSlot.set(Number.POSITIVE_INFINITY, null);

        // Set all "hours" as key.
        for(var i=0; i<this.hours.length; i++){
            timeSlot.set(this.hours[i], null);
        }

        this.timeSlot = timeSlot;
    }

    /**
     * Step 2: **Sort** and **regularize** events, and make them:
     * - Restrain the duration between [0, 1440-currentTimeInSeconds]
     * - Order by duration ASC
     */

    #regularizeEvents(){

        // Sort by duration ASC
       // this.eventMap.sort((a, b) => a.duration - b.duration);
        //console.log(this.eventMap);

        let modifiedAndSortedArr = this.eventMap.map(obj => ({
            ...obj,
            duration: Math.min(obj.duration, this.#longestDuration(obj.time))
          })).sort((a, b) => a.duration - b.duration);

          console.log(modifiedAndSortedArr)
    }

    #allocate(){

    }

    arrange(){

        this.#initTimeSlot();
        this.#regularizeEvents();

        //this.#allocate();

        return this.timeSlot;
    }
}
var ev = [
    {eventId:1, time:'08:00', duration:120},
    {eventId:2, time:'10:00', duration:60},
    {eventId:3, time:'23:00', duration:90},
]

// **Weekday** events, N=13 (9:00 - 21:00)
const WEEKDAY_HOURS = [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21];

// **Weekend** events, N=3 (morning 9:00-12:00, afternoon 13:00-18:00, evening 19:00-21:00),
const WEEKEND_HOURS = [9, 13, 18];

var taa = new TAA(ev, null, null, WEEKDAY_HOURS);
console.log(taa.arrange());

function defineTwigTreeManually(){


    var twig = new TWIG(true);

        // Place a 1920x1080 canvas, depth=0
        var t = new TemplateWidget(); t.setValues(0, 0, 1920, 1080);  twig.root.setWidget(t);

        // A 1920x1080 background, depth=0, child=0
        var background = new TwigNode();
        var b = new ImageWidget();    b.setValues(0, 0, 1920, 1);   background.setWidget(b);
    
        // The title area
        var title = new TwigNode();
        var t = new TemplateWidget(); t.setValues(0, 0, 1920, 180);  title.setWidget(t);

            // Logo
            var logo = new TwigNode();
            var l = new ImageWidget();    l.setValues(100, 50, 150, 3);   logo.setWidget(l); 

            title.addChild(logo);


        // The events area
        var events = new TwigNode();
        var e = new TemplateWidget(); e.setValues(0, 180, 1920, 900); events.setWidget(e);

            // Event table (Monday) (200x800)
            var eventTableMonday = new TwigNode();
            var m = new EventTableWidget(); m.setValues(100, 120, 220, 650, 0, 24);  eventTableMonday.setWidget(m);

 			var eventTableTuesday = new TwigNode();
            var t = new EventTableWidget(); t.setValues(350, 120, 220, 650, 0, 24);  eventTableTuesday.setWidget(t);


        events.addChild(eventTableMonday);
        events.addChild(eventTableTuesday);

    // The events area
    twig.root.addChild(background);
    twig.root.addChild(title);
    twig.root.addChild(events);

    return JSON.stringify(twig.serialize(), null, 2);

}

//console.log(defineTwigTreeManually());
