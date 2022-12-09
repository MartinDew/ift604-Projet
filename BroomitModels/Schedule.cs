using System.Text.Json.Serialization;

namespace BroomitModels;

public class Schedule
{
    public enum ScheduleType
    {
        Daily, Weekly, Yearly,
    };

    /// <summary>
    /// Initial scheduled date time
    /// </summary>
    [JsonPropertyName("due")]
    public string DueDateTime { get; set; }

    /// <summary>
    /// Last done date time
    /// </summary>
    [JsonPropertyName("last_done")]
    public string LastDoneDateTime { get; set; }

    /// <summary>
    /// Recurrence of the schedule "every n days" for example
    /// </summary>
    [JsonPropertyName("every_n")]
    public uint EveryN { get; set; } = 0;

    /// <summary>
    /// Type of the recurrence of the schedule
    /// </summary>
    [JsonPropertyName("type")]
    public string Type { get; set; }
}
