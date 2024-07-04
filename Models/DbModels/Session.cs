using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class Session
{
    public long Id { get; set; }

    public string SessionKey { get; set; } = null!;

    public DateTime CreatedTime { get; set; }

    public long BuildNumber { get; set; }

    public virtual ICollection<Test> Tests { get; set; } = new List<Test>();
}
