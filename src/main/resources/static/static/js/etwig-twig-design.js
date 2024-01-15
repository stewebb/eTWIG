function constrainNumber(num, min, max){
	if(num < min)	return min;
	if(num > max)	return max;
	return num;
}

class TemplateBackground{
	
	#enabled;
	#mode;
	#value;
	
	#setEnabled(enabled){
		
		// The default value is "false" if the input is not a boolean value.
		if(enabled != true && enabled != false){
			this.#enabled = false;
		}	
		this.#enabled = enabled;
	}
	
	#setMode(mode){
		if(mode == "color" || mode == "image"){
			this.#mode = mode;
		}
		
		// The default value is "color" if the input is not in the acceptable range. [color, image]
		else{
			this.#mode = "color";
		}
	}
	
	#setValue(value){
		if(this.#mode == "color"){
			this.#value = value;
		}
		
		// Number check for the assetId.
		else{
			this.#value = (value % 1 === 0) ? value : -1;
		}
	}
	
	
	
	set(enabled, mode, value){
		this.#setEnabled(enabled);
		this.#setMode(mode);
		this.#setValue(value);
	}
	
	getEnabled(){
		return this.#enabled;
	}
	
	getMode(){
		return this.#mode;
	}
	
	getValue(){
		return this.#value;
	}
}

class TemplateImage{
	
	#enabled;
	#image;
	#imageInfo;
	#size;
	#posX;
	#posY;
	
	#MIN_SIZE;
	#MAX_SIZE;
	
	#MIN_POS;
	#MAX_POS;
	
	constructor(minSize, maxSize, minPos, maxPos){
		this.#MIN_SIZE = minSize;
		this.#MAX_SIZE = maxSize;
		this.#MIN_POS = minPos;
		this.#MAX_POS = maxPos;
	}
	
	#setEnabled(enabled){
		
		if(enabled != true && enabled != false){
			this.#enabled = false;
		}	
		this.#enabled = enabled;
	}
	
	#setImage(assetId){
		this.#image = (assetId % 1 === 0) ? assetId : -1;
	}
	
	#setImageInfo(imageInfo){
		this.#imageInfo = imageInfo;
	}
	
	#setSize(size){
		
		// Num check
		size = parseInt(size, 10);
		if(size % 1 === 0){
			this.#size = constrainNumber(size, this.#MIN_SIZE, this.#MAX_SIZE);
		}
		
		// The default logo size is the MIN_SIZE, if the input is not a number.
		else{
			this.#size = this.#MIN_SIZE;
		}
	}
	
	#setPosition(pos){
		
		// Use RegEx tho match the string pattern
		const regex = /\((\d+),(\d+)\)/;
		var match = pos.match(regex);
		
		// If well-formed, number check.
		if (match) {
        	var posX = parseInt(match[1], 10);
        	var posY = parseInt(match[2], 10);

        	// Check if either parsed value is NaN, and set to 0 if so
        	if (isNaN(posX)) {
				posX = 0;	
			}
			
       		if (isNaN(posY)) {
				posY = 0;
			}
			
       		this.#posX = constrainNumber(posX, this.#MIN_POS, this.#MAX_POS);
       		this.#posY = constrainNumber(posY, this.#MIN_POS, this.#MAX_POS);
    	}
    	
    	// If not well-formed, set both posX and posY to 0;
    	else{
			this.#posX = 0;
			this.#posY = 0;
		}
	}
	
	set(enabled, image, imageInfo, size, position){
		this.#setEnabled(enabled);
		this.#setImage(image);
		this.#setImageInfo(imageInfo),
		this.#setSize(size);
		this.#setPosition(position);
	}
	
	getEnabled(){
		return this.#enabled;
	}
	
	getImage(){
		return this.#image;
	}
	
	getImageInfo(){
		return this.#imageInfo;
	}
	
	getPosition(){
		return "(" + pad(this.#posX) + "," + pad(this.#posY) + ")";
	}
	
	getPosX(){
		return this.#posX;
	}
	
	getPosY(){
		return this.#posY;
	}
	
	getSize(){
		return this.#size;
	}
}