import { assert, expect } from "chai";
import BrowserUtils from "../framework/utils/BrowserUtils.js";
import { CONFIG } from "./resources/config.js";
import MainPage from "../pageobjects/MainPage.js";
import GamePage from "../pageobjects/GamePage.js";
import SecondCardPage from '../pageobjects/SecondCardPage.js'

describe('It should open main page and do some interesting stuff', () => {
    before(async () => {
        await BrowserUtils.navigate(CONFIG.baseWebUrL)
    })

    it('Pass the registration form and proceed to the 3d card', async () => {
        
        assert.isTrue(await MainPage.waitForDisplayed(), 'Main page is not open')

        await MainPage.clickHereLink()

        assert.isTrue(await GamePage.waitForDisplayed(), 'Game page is not open')

        await GamePage.inputData('aaaaa1111aA', 'aaaaa1111aA', 'aaaaa1111aA', '.org')

        assert.isTrue(await SecondCardPage.waitForDisplayed(), 'The second card is not opened')
    });
})