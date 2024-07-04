export default class BaseElement{
    constructor(locator, name){
        this.locator = locator
        this.name = name
    }

    async click(){
        return ((await this.getElement()).click())
    }

    async getElement(){
        return $(this.locator)  
    }

    async getElements(){
        return $$(this.locator)
    }

    async waitForDisplayed(){
        let element = await this.getElement()
        return (await element.waitForDisplayed())
    }

    async getElementsText(){
        var el = await this.getElement()
        return await el.getText()
    }

    async isDisplayed(){
        var el = await this.getElement()
        return await el.isDisplayed()
    }

    async setValue(value){
        var el = await this.getElement()
        return await el.setValue(value)
    }
}