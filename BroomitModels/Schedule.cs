namespace BroomitModels;
public class Schedule
{
    public enum ScheduleType
    {
        Daily, Weekly, Yearly,
    };

    /// <summary>
    /// Initial scheduled date time
    /// Exception: if type == Weekly it should be the sunday of that week
    /// </summary>
    public DateTime StartDateTime { get; set; }

    /// <summary>
    /// Recurrence of the schedule "every n days" for example
    /// </summary>
    public uint EveryN { get; set; }

    /// <summary>
    /// Type of the recurrence of the schedule
    /// </summary>
    public ScheduleType Type { get; set; }

    /// <summary>
    /// List of days of the week active when type == Weekly
    /// length should be 0 if type is not Weekly else it should be between 1 and 7
    /// value 0 => sunday 
    /// value 7 => saturday
    /// </summary>
    public uint[] Days { get; set; }

    /// <summary>
    /// Gets the date from the StartTime
    /// </summary>
    public DateOnly Date => DateOnly.FromDateTime(StartDateTime);
    public string StringDate => Date.ToString();

    /// <summary>
    /// Gets the time from the StartTime
    /// </summary>
    public TimeOnly Time => TimeOnly.FromDateTime(StartDateTime);
    public string StringTime => Time.ToString("g");

    public bool IsScheduled(DateOnly date) => Type switch {
        ScheduleType.Daily => (date.DayNumber - Date.DayNumber) % EveryN == 0,
        ScheduleType.Yearly => (date.Year - Date.Year) % EveryN == 0 && date.Day == Date.Day && date.Month == Date.Month,
        ScheduleType.Weekly => isScheduledWeek(date),
        _ => false
    };
    private bool isScheduledWeek(DateOnly date)
    {
        int currentDayOfWeek = (int)date.DayOfWeek;

        // first day of the current week
        int currentWeekNumber = date.DayNumber - currentDayOfWeek / 7;
        int startWeekNumber = Date.Day / 7;

        int weekDiff = currentWeekNumber - startWeekNumber;

        return Days.Any(x => x == currentDayOfWeek) && weekDiff % EveryN == 0;
    }
}
