using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class Attachment
{
    public long Id { get; set; }

    public byte[] Content { get; set; } = null!;

    public string ContentType { get; set; } = null!;

    public long TestId { get; set; }

    public virtual Test Test { get; set; } = null!;
}
