import BasePage from "../framework/basePage/BasePage.js";
import Button from "../framework/elements/Button.js";
import Field from "../framework/elements/Field.js";
import Text from "../framework/elements/Text.js";
import Checkbox from "../framework/elements/Checkbox.js"
import Dropdown from "../framework/elements/Dropdown.js";

class GamePage extends BasePage{

    constructor(){
        super("//a[contains(@class, 'help-button')]", 'A Help button')
    }

    get helpButton (){return new Button("//a[contains(@class, 'help-button')]", 'A Help button')}
    get timersText (){return new Text("//div[contains(@class, 'timer')]", 'Timer on the game page')}
    get passwordField (){return new Field("//input[contains(@placeholder, 'Password')]", 'Password field')}
    get emailField(){return new Field("//input[contains(@placeholder, 'email')]", 'Email field')}
    get domainField (){return new Field("//input[contains(@placeholder, 'Domain')]", 'Domain field')}
    get termsCheckbox (){return new Checkbox("//span[contains(@class, 'checkbox__box')]", 'Terms and conditions checkbox')}
    get domainsDropdownButton (){return new Dropdown("//div[contains(@class, 'dropdown__fie')]", 'Domains drop down')}
    get domainsDropdownList (){return new Dropdown("//div[contains(@class, 'dropdown__list')]", 'Domains drop down')}
    get nextButton (){return new Button("//a[contains(text(), 'Next')]", 'Next Button')}

    async timerStartsFrom(){
        return this.timersText.getElementsText()
    }

    async isHelpResponseDisplayed(){
        return this.helpButton.isDisplayed()
    }

    async inputData(passwordValue, emailValue, domainValue, domainExtension){

        await this.passwordField.setValue(passwordValue)
        await this.emailField.setValue(emailValue)
        await this.domainField.setValue(domainValue)

        await this.termsCheckbox.click()
        await this.domainsDropdownButton.click()
        await this.domainsDropdownList.setDomainForRegistrationForm(domainExtension)

        await this.nextButton.click()
    }

}

export default new GamePage