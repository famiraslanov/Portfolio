export default class BrowserUtils{

    static async navigate(url){
        return await browser.url(url)
    }
}