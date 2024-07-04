import BaseElement from "../elements/BaseElement.js"

export default class BasePage{
    uniqueElement

    constructor(locator, name){
        this.uniqueElement = new BaseElement(locator, name)
    }

    async waitForDisplayed(){
        return this.uniqueElement.waitForDisplayed()
    }
}