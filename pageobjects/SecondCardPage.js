import BasePage from "../framework/basePage/BasePage.js";
import Text from "../framework/elements/Text.js"

class SecondCardPage extends BasePage{
    constructor(){
        super("//h2[contains(@class, 'avatar')]", 'Avatars text')

    }
    
    get avatarButton (){return new Text("//h2[contains(@class, 'avatar')]", 'Avatars text')}
    
    async isDisplayed(){

        return this.avatarButton.isDisplayed()
    }

}
export default new SecondCardPage