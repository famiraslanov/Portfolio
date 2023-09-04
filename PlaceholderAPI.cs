using AngleSharp.Dom;
using Level2RESTAPI.Models;
using Newtonsoft.Json;
using Newtonsoft.Json.Bson;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Level2RESTAPI
{
    internal static class PlaceholderAPI
    {
        public static RestClient Client = new RestClient("https://jsonplaceholder.typicode.com");
        public static RestResponse GetPosts()
        {
            var request = new RestRequest("/posts");
            return Client.Get(request);
        }

        public static RestResponse GetPosts(string postId)
        {
            var request = new RestRequest($"/posts/{postId}");
            return Client.Get(request);
        }

        public static RestResponse PostPostsWithBody(Post body)
        {
            var request = new RestRequest("/posts");
            request.AddJsonBody(body);
            return Client.Post(request);
        }

        public static RestResponse GetUsers ()
        {
            var request = new RestRequest("/users");
            return Client.Get(request);
        }

        public static RestResponse GetUserById(string userId)
        {
            var request = new RestRequest($"/users/{userId}");
            return Client.Get(request);
        }
    }
}
