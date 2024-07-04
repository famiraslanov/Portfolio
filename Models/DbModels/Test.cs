using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class Test
{
    public long Id { get; set; }

    public string Name { get; set; } = null!;

    public int? StatusId { get; set; }

    public string MethodName { get; set; } = null!;

    public long ProjectId { get; set; }

    public long SessionId { get; set; }

    public DateTime? StartTime { get; set; }

    public DateTime? EndTime { get; set; }

    public string Env { get; set; } = null!;

    public string? Browser { get; set; }

    public long? AuthorId { get; set; }

    public virtual ICollection<Attachment> Attachments { get; set; } = new List<Attachment>();

    public virtual Author? Author { get; set; }

    public virtual ICollection<DevInfo> DevInfos { get; set; } = new List<DevInfo>();

    public virtual ICollection<Log> Logs { get; set; } = new List<Log>();

    public virtual Project Project { get; set; } = null!;

    public virtual ICollection<RelFailReasonTest> RelFailReasonTests { get; set; } = new List<RelFailReasonTest>();

    public virtual Session Session { get; set; } = null!;

    public virtual Status? Status { get; set; }
}
