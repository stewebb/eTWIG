import { datetime, RRule, RRuleSet, rrulestr } from 'rrule'

// Create a rule:
const rule = new RRule({
  freq: RRule.WEEKLY,
  interval: 5,
  byweekday: [RRule.MO, RRule.FR],
  dtstart: datetime(2012, 2, 1, 10, 30),
  until: datetime(2012, 12, 31)
})

//var txt = rule.toText();
//console.log(txt)

export class EtwigRRule {
	
	ruleObj: object;
	
    constructor(public ruleStr: string) {
		this.ruleObj = rrulestr(ruleStr);
	}

	//setRuleStr(str: string){
	//	this.ruleStr = str;
	//}
	
	getRuleStr(){
		return this.ruleStr;
	}
	
	getRuleObj(){
		return this.ruleObj;
	}

   
}
