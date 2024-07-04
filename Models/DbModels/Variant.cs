using System;
using System.Collections.Generic;

namespace TaskWithDbEgor.Models.DbModels;

public partial class Variant
{
    public int Id { get; set; }

    public bool IsDynamicVersionInFooter { get; set; }

    public bool UseAjaxForTestsPage { get; set; }

    public bool UseFrameForNewProject { get; set; }

    public bool UseNewTabForNewProject { get; set; }

    public bool UseGeolocationForNewProject { get; set; }

    public bool UseAlertForNewProject { get; set; }

    public bool GrammarMistakeOnSaveProject { get; set; }

    public bool GrammarMistakeOnSaveTest { get; set; }
}
