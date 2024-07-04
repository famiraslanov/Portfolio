import BaseElement from "./BaseElement.js"

export default class Dropdown extends BaseElement{
    constructor(locator, name){
        super(locator, name)
    }

    async setDomainForRegistrationForm(domainName){
        var dropdown = await this.getElements()
        // let selectedDomain = await dropdown.selectByVisibleText(domainName)
        // return await selectedDomain.click()
        for (let i = 0; i < dropdown.length; i++) {
            const text = await dropdown[i].getText();
            if (text === domainName) {
                await dropdown[i].click();
                break;
            }
            else{
                console.log('No such element was found !!!_!_!_!_!__!_!_!_!_!_!_!_!_!!__!_!__!')
            }
        }
    }

    
}