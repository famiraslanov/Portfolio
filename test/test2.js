import { assert, expect } from "chai";
import BrowserUtils from "../framework/utils/BrowserUtils.js";
import { CONFIG } from "./resources/config.js";
import MainPage from "../pageobjects/MainPage.js";
import GamePage from "../pageobjects/GamePage.js";

describe('It should open main page and do some interesting stuff', () => {
    before(async () => {
        await BrowserUtils.navigate(CONFIG.baseWebUrL)
    })

    it('Should open a game page.', async () => {

        assert.isTrue(await MainPage.waitForDisplayed(), 'Main page is not open')

        await MainPage.clickHereLink()

        assert.isTrue(await GamePage.waitForDisplayed(), 'Game page is not open')
        
        assert.equal(await GamePage.timerStartsFrom(), '00:00:00', 'Timer does not start from 00:00:00')
    });
    
})