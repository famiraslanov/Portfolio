import BasePage from "../framework/basePage/BasePage.js";
import Button from "../framework/elements/Button.js";

class MainPage extends BasePage{
    constructor(){
        super('//a[@class="start__link"]', 'Here link')
    }

    get hereLink(){return new Button('//a[@class="start__link"]', 'Here link')}

    async clickHereLink(){
        return this.hereLink.click()
    }

}

export default new MainPage