using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class Token
{
    public int Id { get; set; }

    public string Value { get; set; } = null!;

    public int VariantId { get; set; }

    public DateTime CreationTime { get; set; }
}
