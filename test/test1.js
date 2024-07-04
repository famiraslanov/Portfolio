import { assert, expect } from "chai";
import BrowserUtils from "../framework/utils/BrowserUtils.js";
import { CONFIG } from "./resources/config.js";
import MainPage from "../pageobjects/MainPage.js";
import GamePage from "../pageobjects/GamePage.js";

describe('It should open main page and do some interesting stuff', () => {
    before(async () => {
        await BrowserUtils.navigate(CONFIG.baseWebUrL)
    })
    
    it('Should open main page and navigate to Game page.', async () => {
        
        assert.isTrue(await MainPage.waitForDisplayed(), 'Main page is not open')

        await MainPage.clickHereLink()

        assert.isTrue(await GamePage.waitForDisplayed(), 'Game page is not open')

        assert.isTrue(await GamePage.isHelpResponseDisplayed(), 'Help form is unavaiable on the page')
    });

});