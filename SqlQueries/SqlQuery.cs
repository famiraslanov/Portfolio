using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using TaskWithDbEgor.Constants;
using TaskWithDbEgor.Models;
using TaskWithDbEgor.Utils;

namespace TaskWithDbEgor.SqlQueries
{
    internal class SqlQuery
    {
        private static SqlQueryModel _sqlQueryModel => JsonUtilities<SqlQueryModel>.Parse(File.ReadAllText(PathToFiles.SqlQueries));

        public static string SelectAllAuthors => _sqlQueryModel.SelectAllAuthors;

    }
}
