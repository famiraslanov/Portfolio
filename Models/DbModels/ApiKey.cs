using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class ApiKey
{
    public long Id { get; set; }

    public string Key { get; set; } = null!;

    public string KeyInfo { get; set; } = null!;
}
