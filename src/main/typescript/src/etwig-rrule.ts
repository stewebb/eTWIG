import { datetime, RRule} from 'rrule'

/**@class
 * The class to receive a RFC 5545 rRule and convert it into an object.
 */

export class EtwigRRule {
	
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
	
	getOccuranceBetween(startDate: Date, endDate: Date){
		
		// Null check.
		if(this.ruleObj == undefined || startDate == undefined || endDate == undefined){
			return undefined;
		}
		
		var startDateTime = datetime(startDate.getFullYear(), startDate.getMonth() + 1, startDate.getDate());
		var endDateTime = datetime(endDate.getFullYear(), endDate.getMonth() + 1, endDate.getDate());
		return this.ruleObj.between(startDateTime, endDateTime);
	}

   
}
