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