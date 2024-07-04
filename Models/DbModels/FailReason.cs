using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class FailReason
{
    public long Id { get; set; }

    public string Name { get; set; } = null!;

    public bool IsGlobal { get; set; }

    public bool IsUnremovable { get; set; }

    public bool IsUnchangeable { get; set; }

    public bool IsStatsIgnored { get; set; }

    public bool IsSession { get; set; }

    public bool IsTest { get; set; }

    public virtual ICollection<RelFailReasonTest> RelFailReasonTests { get; set; } = new List<RelFailReasonTest>();
}
