function formatState(state) {
	var option = $(state.element);
  	var color = option.data("color");
  	var icon = option.data("icon");
  	
  	if (!color) {
    	 return state.text;
  	}
  	
  	if(!icon){
		icon = 'square';
	}
  		
  		
  	return $(`<span style="color: ${color}"><i class="fa-solid fa-${icon}"></i>${state.text}</span>`);
};