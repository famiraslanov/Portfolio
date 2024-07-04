using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace TaskWithDbEgor.Utils
{
    internal static class JsonUtilities<T>
    {
        public static string ReadJsonFile(string filePath, string key)
        {
            dynamic jsonFile = JsonConvert.DeserializeObject(File.ReadAllText(filePath));
            return jsonFile[key];
        }

        public static T Parse(string text) => JsonConvert.DeserializeObject<T>(text);
    }
}
