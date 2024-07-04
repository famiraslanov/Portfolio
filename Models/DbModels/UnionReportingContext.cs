//using System;
//using System.Collections.Generic;

//namespace TaskWithDbEgor.Models;

//public partial class UnionReportingContext : DbContext
//{
//    public UnionReportingContext()
//    {
//    }

//    public UnionReportingContext(DbContextOptions<UnionReportingContext> options)
//        : base(options)
//    {
//    }

//    public virtual DbSet<ApiKey> ApiKeys { get; set; }

//    public virtual DbSet<Attachment> Attachments { get; set; }

//    public virtual DbSet<Author> Authors { get; set; }

//    public virtual DbSet<DevInfo> DevInfos { get; set; }

//    public virtual DbSet<FailReason> FailReasons { get; set; }

//    public virtual DbSet<Log> Logs { get; set; }

//    public virtual DbSet<Project> Projects { get; set; }

//    public virtual DbSet<RelFailReasonTest> RelFailReasonTests { get; set; }

//    public virtual DbSet<Session> Sessions { get; set; }

//    public virtual DbSet<Status> Statuses { get; set; }

//    public virtual DbSet<Test> Tests { get; set; }

//    public virtual DbSet<Token> Tokens { get; set; }

//    public virtual DbSet<Variant> Variants { get; set; }

//    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
//#warning To protect potentially sensitive information in your connection string, you should move it out of source code. You can avoid scaffolding the connection string by using the Name= syntax to read it from configuration - see https://go.microsoft.com/fwlink/?linkid=2131148. For more guidance on storing connection strings, see https://go.microsoft.com/fwlink/?LinkId=723263.
//        => optionsBuilder.UseSqlServer("Data Source=CMDB-218522\\SQLEXPRESS;Initial Catalog=unionReporting;Integrated Security=True;Encrypt=True;Trust Server Certificate=True");

//    protected override void OnModelCreating(ModelBuilder modelBuilder)
//    {
//        modelBuilder.Entity<ApiKey>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__api_key__3213E83FE11B2921");

//            entity.ToTable("api_key");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.Key)
//                .HasMaxLength(255)
//                .HasColumnName("key");
//            entity.Property(e => e.KeyInfo)
//                .HasMaxLength(1000)
//                .HasColumnName("key_info");
//        });

//        modelBuilder.Entity<Attachment>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__attachme__3213E83FC3BAED4D");

//            entity.ToTable("attachment");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.Content).HasColumnName("content");
//            entity.Property(e => e.ContentType)
//                .HasMaxLength(255)
//                .HasColumnName("content_type");
//            entity.Property(e => e.TestId).HasColumnName("test_id");

//            entity.HasOne(d => d.Test).WithMany(p => p.Attachments)
//                .HasForeignKey(d => d.TestId)
//                .HasConstraintName("FK__attachmen__test___48CFD27E");
//        });

//        modelBuilder.Entity<Author>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__author__3213E83F97ECA4B3");

//            entity.ToTable("author");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.Email)
//                .HasMaxLength(1000)
//                .HasColumnName("email");
//            entity.Property(e => e.Login)
//                .HasMaxLength(1000)
//                .HasColumnName("login");
//            entity.Property(e => e.Name)
//                .HasMaxLength(1000)
//                .HasColumnName("name");
//        });

//        modelBuilder.Entity<DevInfo>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__dev_info__3213E83F1F47B052");

//            entity.ToTable("dev_info");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.DevTime).HasColumnName("dev_time");
//            entity.Property(e => e.TestId).HasColumnName("test_id");

//            entity.HasOne(d => d.Test).WithMany(p => p.DevInfos)
//                .HasForeignKey(d => d.TestId)
//                .OnDelete(DeleteBehavior.ClientSetNull)
//                .HasConstraintName("FK__dev_info__test_i__4BAC3F29");
//        });

//        modelBuilder.Entity<FailReason>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__fail_rea__3213E83F91B7276F");

//            entity.ToTable("fail_reason");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.IsGlobal).HasColumnName("is_global");
//            entity.Property(e => e.IsSession).HasColumnName("is_session");
//            entity.Property(e => e.IsStatsIgnored).HasColumnName("is_stats_ignored");
//            entity.Property(e => e.IsTest).HasColumnName("is_test");
//            entity.Property(e => e.IsUnchangeable).HasColumnName("is_unchangeable");
//            entity.Property(e => e.IsUnremovable).HasColumnName("is_unremovable");
//            entity.Property(e => e.Name)
//                .HasMaxLength(1000)
//                .HasColumnName("name");
//        });

//        modelBuilder.Entity<Log>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__log__3213E83F3DCC41AF");

//            entity.ToTable("log");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.Content).HasColumnName("content");
//            entity.Property(e => e.IsException).HasColumnName("is_exception");
//            entity.Property(e => e.TestId).HasColumnName("test_id");

//            entity.HasOne(d => d.Test).WithMany(p => p.Logs)
//                .HasForeignKey(d => d.TestId)
//                .HasConstraintName("FK__log__test_id__571DF1D5");
//        });

//        modelBuilder.Entity<Project>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__project__3213E83F4E161AAF");

//            entity.ToTable("project");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.Name)
//                .HasMaxLength(1000)
//                .HasColumnName("name");
//        });

//        modelBuilder.Entity<RelFailReasonTest>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__rel_fail__3213E83F7F44376A");

//            entity.ToTable("rel_fail_reason_test");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.Comment)
//                .HasMaxLength(1000)
//                .HasColumnName("comment");
//            entity.Property(e => e.FailReasonId).HasColumnName("fail_reason_id");
//            entity.Property(e => e.TestId).HasColumnName("test_id");

//            entity.HasOne(d => d.FailReason).WithMany(p => p.RelFailReasonTests)
//                .HasForeignKey(d => d.FailReasonId)
//                .OnDelete(DeleteBehavior.ClientSetNull)
//                .HasConstraintName("FK__rel_fail___fail___59FA5E80");

//            entity.HasOne(d => d.Test).WithMany(p => p.RelFailReasonTests)
//                .HasForeignKey(d => d.TestId)
//                .OnDelete(DeleteBehavior.ClientSetNull)
//                .HasConstraintName("FK__rel_fail___test___5AEE82B9");
//        });

//        modelBuilder.Entity<Session>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__session__3213E83FB0220EAF");

//            entity.ToTable("session");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.BuildNumber).HasColumnName("build_number");
//            entity.Property(e => e.CreatedTime)
//                .HasDefaultValueSql("(getdate())")
//                .HasColumnType("datetime")
//                .HasColumnName("created_time");
//            entity.Property(e => e.SessionKey)
//                .HasMaxLength(1000)
//                .HasColumnName("session_key");
//        });

//        modelBuilder.Entity<Status>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__status__3213E83F2FA14D22");

//            entity.ToTable("status");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.Name)
//                .HasMaxLength(255)
//                .HasColumnName("name");
//        });

//        modelBuilder.Entity<Test>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__test__3213E83F5725AB2A");

//            entity.ToTable("test");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.AuthorId).HasColumnName("author_id");
//            entity.Property(e => e.Browser)
//                .HasMaxLength(255)
//                .HasColumnName("browser");
//            entity.Property(e => e.EndTime)
//                .HasColumnType("datetime")
//                .HasColumnName("end_time");
//            entity.Property(e => e.Env)
//                .HasMaxLength(255)
//                .HasColumnName("env");
//            entity.Property(e => e.MethodName)
//                .HasMaxLength(1000)
//                .HasColumnName("method_name");
//            entity.Property(e => e.Name)
//                .HasMaxLength(1000)
//                .HasColumnName("name");
//            entity.Property(e => e.ProjectId).HasColumnName("project_id");
//            entity.Property(e => e.SessionId).HasColumnName("session_id");
//            entity.Property(e => e.StartTime)
//                .HasDefaultValueSql("(getdate())")
//                .HasColumnType("datetime")
//                .HasColumnName("start_time");
//            entity.Property(e => e.StatusId).HasColumnName("status_id");

//            entity.HasOne(d => d.Author).WithMany(p => p.Tests)
//                .HasForeignKey(d => d.AuthorId)
//                .HasConstraintName("FK__test__author_id__4316F928");

//            entity.HasOne(d => d.Project).WithMany(p => p.Tests)
//                .HasForeignKey(d => d.ProjectId)
//                .HasConstraintName("FK__test__project_id__412EB0B6");

//            entity.HasOne(d => d.Session).WithMany(p => p.Tests)
//                .HasForeignKey(d => d.SessionId)
//                .HasConstraintName("FK__test__session_id__4222D4EF");

//            entity.HasOne(d => d.Status).WithMany(p => p.Tests)
//                .HasForeignKey(d => d.StatusId)
//                .HasConstraintName("FK__test__status_id__440B1D61");
//        });

//        modelBuilder.Entity<Token>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__token__3213E83F19FE7608");

//            entity.ToTable("token");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.CreationTime)
//                .HasDefaultValueSql("(getdate())")
//                .HasColumnType("datetime")
//                .HasColumnName("creation_time");
//            entity.Property(e => e.Value)
//                .HasMaxLength(32)
//                .HasColumnName("value");
//            entity.Property(e => e.VariantId).HasColumnName("variant_id");
//        });

//        modelBuilder.Entity<Variant>(entity =>
//        {
//            entity.HasKey(e => e.Id).HasName("PK__variant__3213E83FFAB7442B");

//            entity.ToTable("variant");

//            entity.Property(e => e.Id)
//                .ValueGeneratedNever()
//                .HasColumnName("id");
//            entity.Property(e => e.GrammarMistakeOnSaveProject).HasColumnName("grammar_mistake_on_save_project");
//            entity.Property(e => e.GrammarMistakeOnSaveTest).HasColumnName("grammar_mistake_on_save_test");
//            entity.Property(e => e.IsDynamicVersionInFooter).HasColumnName("is_dynamic_version_in_footer");
//            entity.Property(e => e.UseAjaxForTestsPage).HasColumnName("use_ajax_for_tests_page");
//            entity.Property(e => e.UseAlertForNewProject).HasColumnName("use_alert_for_new_project");
//            entity.Property(e => e.UseFrameForNewProject).HasColumnName("use_frame_for_new_project");
//            entity.Property(e => e.UseGeolocationForNewProject).HasColumnName("use_geolocation_for_new_project");
//            entity.Property(e => e.UseNewTabForNewProject).HasColumnName("use_new_tab_for_new_project");
//        });

//        OnModelCreatingPartial(modelBuilder);
//    }

//    partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
//}
