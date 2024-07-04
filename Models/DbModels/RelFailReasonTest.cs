using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class RelFailReasonTest
{
    public long Id { get; set; }

    public long FailReasonId { get; set; }

    public long TestId { get; set; }

    public string? Comment { get; set; }

    public virtual FailReason FailReason { get; set; } = null!;

    public virtual Test Test { get; set; } = null!;
}
