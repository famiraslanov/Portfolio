using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class Status
{
    public int Id { get; set; }

    public string Name { get; set; } = null!;

    public virtual ICollection<Test> Tests { get; set; } = new List<Test>();
}
