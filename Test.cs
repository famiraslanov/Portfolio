using System.Data;
using Dapper;
using Microsoft.Data.SqlClient;
using TaskWithDbEgor.Constants;
using TaskWithDbEgor.Models.DbModels;
using TaskWithDbEgor.SqlQueries;
using TaskWithDbEgor.Utils;


namespace TaskWithDbEgor
{
    [TestFixture]
    internal class Test
    {
        private readonly string connectionString = JsonUtilities<string>.ReadJsonFile(PathToFiles.ConnectionString, "MyDatabaseConnection");

        [Test]
        public void Test1()
        {
            using (IDbConnection dbConnection = new SqlConnection(connectionString))
            {
                dbConnection.Open();

                var authors = dbConnection.Query<Author>(SqlQuery.SelectAllAuthors).ToList();

                Assert.IsNotNull(authors);
                Assert.IsTrue(authors.Count > 0);
            }

        }

    }
}
