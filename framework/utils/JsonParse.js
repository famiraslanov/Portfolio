import fs from "fs";
import  CONFIG  from "../../test/resources/config.js"

class JsonParse {
  encodingStandard = CONFIG.encodingStandard;
  
  parse(filePath) {
    let data = fs.readFileSync(filePath, this.encodingStandard);
    return JSON.parse(data);
  }
};

export default new JsonParse;