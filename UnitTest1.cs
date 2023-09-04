using Level2RESTAPI.Models;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using RestSharp;
using System.Security.Cryptography.X509Certificates;
using System.Text.Json;

namespace Level2RESTAPI
{
    public class Tests
    {
        [SetUp]
        public void Setup()
        {
        }

        [Test]
        public void Test1()
        {
            var response = PlaceholderAPI.GetPosts();

            var jsonString = response.Content.ToString();
            var jsonToken = JToken.Parse(response.Content);
            List<Post> posts = JsonConvert.DeserializeObject<List<Post>>(jsonString);

            Assert.That((int)response.StatusCode, Is.EqualTo(200), "Status code is not equal to 200");
            Assert.That(posts, Is.Ordered.Ascending.By("id"), "Posts are not sorted by ascending");
            Assert.IsTrue(jsonToken is JArray, "The list in response body is not json.");
        }

        [Test]
        public void Test2()
        {
            var response = PlaceholderAPI.GetPosts("99");
            var jsonString = response.Content.ToString();

            var post = JsonConvert.DeserializeObject<Post>(jsonString);

            Assert.That((int)response.StatusCode, Is.EqualTo(200), "Status code is not equal to 200");
            Assert.Multiple(() =>
                {
                    Assert.That(post.userId, Is.EqualTo(10), "userId is not eqial to 10");
                    Assert.That(post.id, Is.EqualTo(99), "id is not equal to 99");
                    Assert.IsTrue(!string.IsNullOrEmpty(post.title), "The title is empty");
                    Assert.IsTrue(!string.IsNullOrEmpty(post.body), "The body is not empty");
                });
        }
        [Test]
        public void Test3()
        {
            var response = PlaceholderAPI.GetPosts("150");
            var jsonString = response.Content.ToString();

            Assert.That((int)response.StatusCode, Is.EqualTo(404), "Status code is not equal to 404");
            Assert.That(jsonString.Equals("{}"), "Json string is not empty");
        }

        [Test]
        public void Test4 ()
        {
            var response = PlaceholderAPI.PostPostsWithBody(new Post()
            {
                userId = 1,
                id = 101,
                body = "RandomBody",
                title = "RandomTitle"
            });

            var post = JsonConvert.DeserializeObject<Post>(response.Content.ToString());
            Assert.Multiple(() =>
            {
                Assert.That((int)response.StatusCode, Is.EqualTo(201), "Status code is not equal to 201");
                Assert.That(post.id,Is.EqualTo(101), "id is not equal to 101");
                Assert.That(post.body, Is.EqualTo("RandomBody"), "Body is not equal to RandomBody");
                Assert.That(post.userId, Is.EqualTo(1), "userId is not equal to 1");
                Assert.That(post.title, Is.EqualTo("RandomTitle"), "title is not equal to RandomTitle");
            });
        }

        [Test]
        public void Test5()
        {
            var response = PlaceholderAPI.GetUsers();

            var jsonToken = JToken.Parse(response.Content);
            var users = JsonConvert.DeserializeObject<List<User>>(response.Content.ToString());

            var user = users.FirstOrDefault(user => user.id  == 5);

            var stringFileText = File.ReadAllText("expectedRes.json");
            var jsonDes = JsonConvert.DeserializeObject<User>(stringFileText);

            #region Asserting each filed of the object
            //Assert.Multiple(() =>
            //{
            //    Assert.That((int)response.StatusCode, Is.EqualTo(200), "Response code is not equal to 200");
            //    Assert.That(jsonToken is JArray, "The list in response body is not json.");
            //    Assert.That(user.id, Is.EqualTo(5));
            //    Assert.That(user.name, Is.EqualTo("Chelsey Dietrich"), "User's name is not equal to Chelsey Dietrich");
            //    Assert.That(user.username, Is.EqualTo("Kamren"), "User's username is not equal to  Kamren");
            //    Assert.That(user.email, Is.EqualTo("Lucio_Hettinger@annie.ca"), "User's email is not equal to  Lucio_Hettinger@annie.ca");
            //    Assert.That(user.address.street, Is.EqualTo("Skiles Walks"), "User's street is not equal to  Skiles Walks");
            //    Assert.That(user.address.suite, Is.EqualTo("Suite 351"), "User's suite is not equal to  Suite 351");
            //    Assert.That(user.address.city, Is.EqualTo("Roscoeview"), "User's city is not equal to  Skiles Roscoeview");
            //    Assert.That(user.address.zipcode, Is.EqualTo("33263"), "User's zipcode is not equal to  33263");
            //    Assert.That(user.address.geo.lat, Is.EqualTo("-31.8129"), "User's lat is not equal to -31.8129");
            //    Assert.That(user.address.geo.lng, Is.EqualTo("62.5342"), "User's lng is not equal to -62.5342");
            //    Assert.That(user.website, Is.EqualTo("demarco.info"), "User's website is not equal to demarco.info");
            //    Assert.That(user.company.name, Is.EqualTo("Keebler LLC"), "User's company name  is not equal to Keebler LLC");
            //    Assert.That(user.company.catchPhrase, Is.EqualTo("User-centric fault-tolerant solution"), "User's catch phrase name  is not equal to User-centric fault-tolerant solution");
            //    Assert.That(user.company.bs, Is.EqualTo("revolutionize end-to-end systems"), "User's catch phrase name  is not equal to revolutionize end-to-end systems");
            //});
            #endregion

            Assert.That(user, Is.EqualTo(jsonDes));
            Assert.That((int)response.StatusCode, Is.EqualTo(200), "Response code is not equal to 200");
            Assert.That(jsonToken is JArray, "The list in response body is not json.");
        }

        [Test]
        public void Test6()
        {
            var firstResponse = PlaceholderAPI.GetUserById("5");
            var secondResponse = PlaceholderAPI.GetUsers();

            var firstUser = JsonConvert.DeserializeObject<User>(firstResponse.Content.ToString());
            var users = JsonConvert.DeserializeObject<List<User>>(secondResponse.Content.ToString());

            var secondUser = users.FirstOrDefault(user => user.id == 5);

            Assert.That((int)firstResponse.StatusCode, Is.EqualTo(200));
            Assert.That(firstUser.ToString(), Is.EqualTo(secondUser.ToString())) ;
        }

    }
}