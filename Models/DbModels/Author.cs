using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class Author
{
    public long Id { get; set; }

    public string Name { get; set; } = null!;

    public string Login { get; set; } = null!;

    public string Email { get; set; } = null!;

    public virtual ICollection<Test> Tests { get; set; } = new List<Test>();
}
