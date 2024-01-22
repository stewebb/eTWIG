import { datetime, Frequency, RRule} from 'rrule'

/**
 * The class to receive a RFC 5545 rRule and convert it into an object.
 */

export class RRuleFromStr {
	
	ruleObj: RRule | undefined;
	
	/**@constructor
	 * Receive 
	 */
	
    constructor(public ruleStr: string) {
		try{
			this.ruleObj = RRule.fromString(ruleStr);
		}
		catch (error){
			this.ruleObj = undefined;
		}
		
	}
	
	getRuleStr(){
		return this.ruleStr;
	}
	
	getRuleObj(){
		return this.ruleObj;
	}
	
	getOccurrenceBetween(startDate: Date, endDate: Date){
		
		// Null check.
		if(this.ruleObj == undefined || startDate == undefined || endDate == undefined){
			return undefined;
		}
		
		// Re-format start and end date.
		var startDateTime = datetime(startDate.getFullYear(), startDate.getMonth() + 1, startDate.getDate());
		var endDateTime = datetime(endDate.getFullYear(), endDate.getMonth() + 1, endDate.getDate());
		return this.ruleObj.between(startDateTime, endDateTime);
	}

}

export class RRuleFromForm{

	input: object | undefined;
	rule: RRule | undefined;

	constructor(input: object){
		this.input = input;
	}

	generateRRule() {
		try{
			this.rule = new RRule(this.input);
		}
		catch (error){
			this.rule = undefined;
		}
		
	}

	toText(){
		return this.rule?.toText();
	}

	toString(){
		return this.rule?.toString();
	}

	all(){
		return this.rule?.all();
	}
}

