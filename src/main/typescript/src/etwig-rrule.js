"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.RRuleFromForm = exports.RRuleFromStr = void 0;
const rrule_1 = require("rrule");
/**
 * The class to receive a RFC 5545 rRule and convert it into an object.
 */
class RRuleFromStr {
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
    getOccurrenceBetween(startDate, endDate) {
        // Null check.
        if (this.ruleObj == undefined || startDate == undefined || endDate == undefined) {
            return undefined;
        }
        // Re-format start and end date.
        var startDateTime = (0, rrule_1.datetime)(startDate.getFullYear(), startDate.getMonth() + 1, startDate.getDate());
        var endDateTime = (0, rrule_1.datetime)(endDate.getFullYear(), endDate.getMonth() + 1, endDate.getDate());
        return this.ruleObj.between(startDateTime, endDateTime);
    }
}
exports.RRuleFromStr = RRuleFromStr;
class RRuleFromForm {
    constructor(input) {
        this.input = input;
    }
    generateRRule() {
        try {
            this.rule = new rrule_1.RRule(this.input);
        }
        catch (error) {
            this.rule = undefined;
        }
    }
    toText() {
        var _a;
        return (_a = this.rule) === null || _a === void 0 ? void 0 : _a.toText();
    }
    toString() {
        var _a;
        return (_a = this.rule) === null || _a === void 0 ? void 0 : _a.toString();
    }
    all() {
        var _a;
        return (_a = this.rule) === null || _a === void 0 ? void 0 : _a.all();
    }
}
exports.RRuleFromForm = RRuleFromForm;
