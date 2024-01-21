"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.EtwigRRule = void 0;
const rrule_1 = require("rrule");
/**@class
 * The class to receive a RFC 5545 rRule and convert it into an object.
 */
class EtwigRRule {
    /**@constructor
     * Receive
     */
    constructor(ruleStr) {
        this.ruleStr = ruleStr;
        try {
            this.ruleObj = rrule_1.RRule.fromString(ruleStr);
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
    getOccuranceBetween(startDate, endDate) {
        // Null check.
        if (this.ruleObj == undefined || startDate == undefined || endDate == undefined) {
            return undefined;
        }
        var startDateTime = (0, rrule_1.datetime)(startDate.getFullYear(), startDate.getMonth() + 1, startDate.getDate());
        var endDateTime = (0, rrule_1.datetime)(endDate.getFullYear(), endDate.getMonth() + 1, endDate.getDate());
        return this.ruleObj.between(startDateTime, endDateTime);
    }
}
exports.EtwigRRule = EtwigRRule;
