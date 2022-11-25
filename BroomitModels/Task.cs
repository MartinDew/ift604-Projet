using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;

namespace BroomitModels;

public class Task
{

    /// <summary>
    /// Id of the task
    /// </summary>
    [BsonId]
    [BsonRepresentation(BsonType.ObjectId)]
    public string? Id { get; set; }

    /// <summary>
    /// Name of the task at hand
    /// </summary>
    public string? Name { get; set; }

    /// <summary>
    /// Additionnal notes for usability purposes
    /// </summary>
    public string Notes { get; set; } = "";

    /// <summary>
    /// The Schedule of the task
    /// i.e. when it is scheduled for
    /// </summary>
    public Schedule? Schedule { get; set; }

    /// <summary>
    /// The location of the task
    /// </summary>
    public Location? Location { get; set; }

    /// <summary>
    /// Should the user be notified when task is due
    /// </summary>
    public bool NotifyOnDue { get; set; } = false;

    public bool IsScheduled(DateOnly date) => Schedule == null ? false : Schedule.IsScheduled(date);
}
