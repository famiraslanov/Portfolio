import { assert } from "chai";
import BrowserUtils from "../framework/utils/BrowserUtils.js";
import MainPage from "../pageobjects/MainPage.js";
import { CONFIG } from "./resources/config.js";
import GamePage from "../pageobjects/GamePage.js";
import SecondCardPage from "../pageobjects/SecondCardPage.js";

describe('It should open main page, enter valid email and invalid password', () => {
    before(async ()=>{
        await BrowserUtils.navigate(CONFIG.baseWebUrL)
    })

    it('It should open main page, enter valid email and invalid password', async () => {
        
        assert.isTrue(await MainPage.waitForDisplayed(), 'Main page is not opened')

        await MainPage.clickHereLink()

        assert.isTrue(await GamePage.waitForDisplayed(), 'Game page is not opened')

        await GamePage.inputData('123,', 'aaaaa1111aA', 'aaaaa1111aA', '.org' ) 

        assert.isFalse(await SecondCardPage.isDisplayed(), 'The second card is opened')

    });
});