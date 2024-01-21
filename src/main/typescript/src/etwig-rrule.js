"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.EtwigRRule = void 0;
const rrule_1 = require("rrule");
// Create a rule:
const rule = new rrule_1.RRule({
    freq: rrule_1.RRule.WEEKLY,
    interval: 5,
    byweekday: [rrule_1.RRule.MO, rrule_1.RRule.FR],
    dtstart: (0, rrule_1.datetime)(2012, 2, 1, 10, 30),
    until: (0, rrule_1.datetime)(2012, 12, 31)
});
//var txt = rule.toText();
//console.log(txt)
class EtwigRRule {
    constructor(ruleStr) {
        this.ruleStr = ruleStr;
        try {
            this.ruleObj = (0, rrule_1.rrulestr)(ruleStr);
        }
        catch (error) {
            this.ruleObj = undefined;
        }
    }
    getRuleStr() {
        return this.ruleStr;
    }
    getRuleObj() {
        return this.ruleObj;
    }
}
exports.EtwigRRule = EtwigRRule;
